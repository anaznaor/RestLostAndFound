package hr.fer.ruazosa.lostnfound.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.ruazosa.lostnfound.entity.Note;
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
    Notification findNotification(Long id);

    public String sendNotification(Note note, String token) throws FirebaseMessagingException;
    User setToken(String username, String token);
}
