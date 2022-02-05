package hr.fer.ruazosa.lostnfound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostNFoundService implements ILostNFoundService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        // TODO check username unique
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
        return userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword()).get(0);
    }
}
