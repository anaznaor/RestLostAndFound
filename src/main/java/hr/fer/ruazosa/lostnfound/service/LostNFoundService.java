package hr.fer.ruazosa.lostnfound.service;

import hr.fer.ruazosa.lostnfound.entity.Notification;
import hr.fer.ruazosa.lostnfound.entity.User;
import hr.fer.ruazosa.lostnfound.repository.NotificationRepository;
import hr.fer.ruazosa.lostnfound.repository.UserRepository;
import hr.fer.ruazosa.lostnfound.service.ILostNFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class LostNFoundService implements ILostNFoundService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public User registerUser(User user) {
        boolean unique = checkUsernameUnique(user);
        if(unique)
            return userRepository.save(user);
        return null;
    }

    @Override
    public boolean checkUsernameUnique(User user) {
        List<User> users = userRepository.findAll();
        for(User u : users)
            if(u.getUsername().equals(user.getUsername()))
                return false;
        return true;
    }

    @Override
    public User loginUser(User user) {
            List<User> loggedUserList = userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword());
            if (loggedUserList.isEmpty()) {
                return null;
            }

            User loggedUser = userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword()).get(0);
        return loggedUser;
    }

    @Override
    public User getUser(String username) {
        List<User> user = userRepository.findByUsername(username);
        if(user != null && user.size() > 0)
            return user.get(0);
        return null;
    }

    @Override
    public Notification putNotification(Notification not) {
        Notification notif = notificationRepository.save(not);
        return notif;
    }
}
