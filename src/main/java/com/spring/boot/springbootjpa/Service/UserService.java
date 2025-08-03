package com.spring.boot.springbootjpa.Service;

import com.spring.boot.springbootjpa.Model.User;
import com.spring.boot.springbootjpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer userID) {
        return userRepository.getById(userID);
    }

    public Boolean updateUser(Integer userID, User user) {
        User oldUser = getUser(userID);

        if (oldUser == null) {
            return false;
        }

        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());
        oldUser.setFavoriteCategory(user.getFavoriteCategory());

        // orderHistory is set by the system

        userRepository.save(oldUser);
        return true;
    }

    public Boolean deleteUser(Integer userID) {
        User deleteUser = getUser(userID);

        if (deleteUser == null) {
            return false;
        }

        userRepository.delete(deleteUser);
        return true;
    }

    public Boolean checkAvailableUser(Integer userID) {
        User checkUser = getUser(userID);

        if (checkUser == null) {
            return false;
        }

        return checkUser.getId().equals(userID); // always true?
    }

    // to make the user balance stays private, another method is created to pay the amount
    public Boolean canPay(Integer userID, Double amount) {
        User user = getUser(userID);

        if (user.getBalance() >= amount) {
            return true; // can pay
        }

        return false; // user is poor :(
    }

    public Boolean pay(Integer userID, Double amount) {
        User user = getUser(userID);

        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            return true; // paid
        }

        return false; // payment error
    }

    // for extra variables update
    public void save(User user){
        userRepository.save(user);
    }
}
