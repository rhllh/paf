package vttp2022.paftest.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.paftest.models.User;
import vttp2022.paftest.repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    
    public Optional<User> findUserById(String userId) {
        return userRepo.findUserByIdInRepo(userId);
    }

    public String insertUser(User user) {
        // generate random user id
        String userId = UUID.randomUUID().toString().substring(0, 8);
        user.setUserId(userId);

        Integer inserted = userRepo.insertUserInRepo(user);

        if (inserted == 1) {
            return userId;
        } else {
            return "";
        }
        
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findUserByUsernameInRepo(username);
    }
}
