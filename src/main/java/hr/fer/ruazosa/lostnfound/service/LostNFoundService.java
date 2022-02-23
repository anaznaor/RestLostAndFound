package hr.fer.ruazosa.lostnfound.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import hr.fer.ruazosa.lostnfound.entity.Note;
import hr.fer.ruazosa.lostnfound.entity.Notification;
import hr.fer.ruazosa.lostnfound.entity.User;
import hr.fer.ruazosa.lostnfound.repository.NotificationRepository;
import hr.fer.ruazosa.lostnfound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class LostNFoundService implements ILostNFoundService {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
        return FirebaseMessaging.getInstance(app);
    }

    private FirebaseMessaging firebaseMessaging;

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

        List<User> loggedUser = userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword());
        if(loggedUser.isEmpty())
            return null;

        return loggedUser.get(0);
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

    @Override
    public Notification updateNotif(Long id, Notification notif) {
        List<Notification> notifList = notificationRepository.findNotifById(id);
        if(notifList.isEmpty())
            return null;

        Notification oldNotif = notifList.get(0);

        oldNotif.setAddress(notif.getAddress());
        oldNotif.setDescription(notif.getDescription());
        oldNotif.setTitle(notif.getTitle());
        oldNotif.setType(notif.getType());

        notificationRepository.save(oldNotif);
        return oldNotif;
    }

    @Override
    public boolean deleteNot(Long id) {
        List<Notification> notifList = notificationRepository.findNotifById(id);

        if(notifList.isEmpty())
            return false;

        notificationRepository.delete(notifList.get(0));
        return true;
    }

    @Override
    public List<Notification> getAllNotifications(User u) {
        List<Notification> notifRep = new LinkedList<>();

        for(User user : userRepository.findAll()){
            if(! user.equals(u)){
                for(Notification notif: user.getNotifications()){
                    notif.setUser(user);
                    notifRep.add(notif);
                }
            }
        }

        return notifRep;

    }

    @Override
    public Notification findNotification(Long id) {
        List<Notification> list = notificationRepository.findNotifById(id);
        if(list == null)
            return null;

        return list.get(0);
    }

    public String sendNotification(Note note, String token) throws FirebaseMessagingException {

        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        return this.firebaseMessaging.send(message);
    }
}
