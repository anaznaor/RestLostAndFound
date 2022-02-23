package hr.fer.ruazosa.lostnfound;


import com.google.firebase.messaging.FirebaseMessagingException;
import hr.fer.ruazosa.lostnfound.entity.Note;
import hr.fer.ruazosa.lostnfound.entity.Notification;
import hr.fer.ruazosa.lostnfound.entity.User;
import hr.fer.ruazosa.lostnfound.service.ILostNFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@RestController
public class LostNFoundController {
    @Autowired
    private ILostNFoundService pointOfInterestService;

    @PostMapping("/registerUser")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        // validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Map<String, Object> body = new LinkedHashMap<>();
        for (ConstraintViolation<User> violation : violations) {
            body.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if (!body.isEmpty()) {
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        if (!pointOfInterestService.checkUsernameUnique(user))
            return new ResponseEntity<Object>(user, HttpStatus.FORBIDDEN);
        User ru = pointOfInterestService.registerUser(user);
        return new ResponseEntity<Object>(ru, HttpStatus.OK);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        // validation
        if (user == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "no user JSON object in body");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }
        else if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "username or password parameters are empty");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            User loggedUser = pointOfInterestService.loginUser(user);
            if (loggedUser != null) {
                return new ResponseEntity<Object>(loggedUser, HttpStatus.OK);
            }
            else {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("error", "no user found");
                return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
            }
        }

    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        User user = pointOfInterestService.getUser(username);
        return user;
    }

    @GetMapping("/{username}/notifications")
    public List<Notification> getUserNotifications(@PathVariable String username) {
        return this.getUser(username).getNotifications();
    }


    @PostMapping("/{username}/putNotification")
    public ResponseEntity<Object> putNotification(@PathVariable String username, @RequestBody Notification not) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Notification>> violations = validator.validate(not);

        Map<String, Object> body = new LinkedHashMap<>();
        for (ConstraintViolation<Notification> violation : violations) {
            body.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if (!body.isEmpty()) {
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }


        User u = pointOfInterestService.getUser(username);
        not.setUser(u);
        not.setUsername(username);
        pointOfInterestService.putNotification(not);
        return new ResponseEntity<>(not, HttpStatus.OK);
    }

    @PostMapping("/{id}/updateNotif")
    public ResponseEntity<Object> updateNotif(@PathVariable Long id, @RequestBody Notification notif){
        Notification not = pointOfInterestService.updateNotif(id, notif);

        if(not == null)
            return new ResponseEntity<>(not, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(not, HttpStatus.OK);
    }

    @PostMapping("/{id}/deleteNotif")
    public ResponseEntity<Object> deleteNotif(@PathVariable Long id){
        Map<String, Object> body = new HashMap<>();

        if(pointOfInterestService.deleteNot(id)){
            body.put("success", "Deleted");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

        body.put("error", "not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}/allNotifs")
    public List<Notification> getAllNotifs(@PathVariable String username){
        User u = pointOfInterestService.getUser(username);

        return pointOfInterestService.getAllNotifications(u);
    }

    @GetMapping("/notifications/{id}")
    public Notification getNotification(@PathVariable Long id){
        Notification notif = pointOfInterestService.findNotification(id);

        return notif;
    }

    @PostMapping("/send-notification")
    @ResponseBody
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return pointOfInterestService.sendNotification(note, token);
    }
}

