package hr.fer.ruazosa.lostnfound.service;

import hr.fer.ruazosa.lostnfound.entity.Notification;
import hr.fer.ruazosa.lostnfound.entity.User;

import java.util.List;

public interface ILostNFoundService {
    User registerUser(User user);
    boolean checkUsernameUnique(User user);
    User loginUser(User user);
    User getUser(String username);

    Notification putNotification(Notification not);
    Notification updateNotif(Long id, Notification notif);
    boolean deleteNot(Long id);

    List<Notification> getAllNotifications(User u);
}
