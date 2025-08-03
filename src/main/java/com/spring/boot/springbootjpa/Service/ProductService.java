package com.spring.boot.springbootjpa.Service;

import com.spring.boot.springbootjpa.Model.Product;
import com.spring.boot.springbootjpa.Model.User;
import com.spring.boot.springbootjpa.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;
    private final UserService userService;

    public Boolean addProduct(Product product) {
        if (categoryService.checkAvailableCategory(product.getCategoryID())) {
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Boolean updateProduct(Integer productID, Product product) {
        if (!categoryService.checkAvailableCategory(product.getCategoryID())) {
            return false; // return false if the new category id does not exist
        }

        Product oldProduct = productRepository.getById(productID);

        if (oldProduct == null) {
            return false;
        }

        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategoryID(product.getCategoryID());
        // timesPurchased and score are not allowed to be updated by the user

        productRepository.save(oldProduct);
        return true;

    }

    public Boolean deleteProduct(Integer productID) {
        Product deleteProduct = productRepository.getById(productID);

        if (deleteProduct == null) {
            return false;
        }

        productRepository.delete(deleteProduct);
        return true;
    }

    public Boolean checkAvailableProduct(Integer productID) {
        Product checkProduct = productRepository.getById(productID);

        if (checkProduct == null) {
            return false;
        }

        return checkProduct.getId().equals(productID); // always true?
    }

    public Product getProduct(Integer productID) {
        return productRepository.getById(productID);
    }

    // TODO Extra methods:

    public List<Product> listBestSellingProducts() {
        List<Product> bestSellers = getProducts();
        Product swap;

        // sort based on max time of purchase
        for (int i = 0; i < bestSellers.size(); i++) {
            for (int j = i + 1; j < bestSellers.size() - 1; j++) {
                if (bestSellers.get(j).getTimesPurchased() > bestSellers.get(i).getTimesPurchased()) {

                    swap = bestSellers.get(i);
                    bestSellers.set(i, bestSellers.get(j));
                    bestSellers.set(j, swap);

                }
            }
        }

        return bestSellers;
    }

    // update the score by admin
    public Boolean updateProductScore(Integer userID, Integer productID, Double newScore) {
        User user = userService.getUser(userID);

        if (user == null) {
            return false; // user does not exist
        }

        if (!user.getRole().equals("Admin")) {
            return false; // only admin can add scores for advertisement purposes
        }

        Product updateScore = getProduct(productID);

        if (updateScore == null) {
            return false;
        }


        updateScore.setScore(newScore);
        productRepository.save(updateScore);

        return true; // score updated

    }

    // helper sort method:
    public void sortByScore(List<Product> products) {
        Product swap;

        // sort based on score
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size() - 1; j++) {
                if (products.get(j).getScore() > products.get(i).getScore()) {

                    swap = products.get(i);
                    products.set(i, products.get(j));
                    products.set(j, swap);

                }
            }
        }
    }

    // display advertisements based on highest score set by admin
    public List<Product> displayAdvertisement() {
        List<Product> bestScore = getProducts();

        // sort all products based on advertisement score
        sortByScore(bestScore);

        return bestScore;
    }

    // Display Similar products suggestions based on category and sort by score
    public List<Product> displaySimilarProducts(Integer productID) {
        List<Product> similarProducts = new ArrayList<>();

        Product similarTo = getProduct(productID); // the product we are comparing to, can be null

        for (Product p : getProducts()) { // add all products with the same category
            if (p.getCategoryID().equals(similarTo.getCategoryID())) {
                similarProducts.add(p);
            }
        }

        // sort similar products by score:
        sortByScore(similarProducts);

        return similarProducts;
    }

    // display user order history list
    public List<Product> displayUserOrderHistory(Integer userID) {

        List<Product> orderHistoryList = new ArrayList<>(); // [] empty list

        String orderHistory = userService.getUser(userID).getOrderHistory();

        // i.e. 100_2025-07-28, 200_2025-05-22 , . . .
        if (orderHistory != null) {
            String[] orderIDsWithDates = orderHistory.split(","); // [0] = 100_2025-07-28

            for (String order : orderIDsWithDates) { // 100_2025-07-28
                Integer productID = Integer.parseInt(order.split("_")[0]); // 100 which is the productID

                if (checkAvailableProduct(productID)) { // to prevent null pointer exception
                    orderHistoryList.add(getProduct(productID)); // getProduct can return null
                }
            }
        }
        return orderHistoryList; // or else return an empty list if user/product do not exist
    }

    // figure out user's favorite category
    public String favoriteUserCategory(Integer userID) {
        String favoriteCategory = ""; // initially nothing

        List<Product> userOrderHistory = displayUserOrderHistory(userID); // get user orders
        List<String> userCategories = new ArrayList<>(); // generate user categories

        for (Product p : userOrderHistory) {
            if (categoryService.checkAvailableCategory(p.getCategoryID())) { // if category exists, add it
                userCategories.add(categoryService.getCategory(p.getCategoryID()).getName());
            }
        }

        // check the most repeated category
        int currentCount;
        int maxCount = 0;

        for (String currentCategory : userCategories) {
            currentCount = 0;
            for (String counterCategory : userCategories) { // count how many times this category occurs
                if (currentCategory.equals(counterCategory)) {
                    currentCount++;
                }
            }

            if (currentCount > maxCount) {
                maxCount = currentCount;
                favoriteCategory = currentCategory;
            }
        }

        return favoriteCategory;

    }

}
