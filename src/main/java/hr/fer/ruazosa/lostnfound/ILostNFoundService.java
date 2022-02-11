package hr.fer.ruazosa.lostnfound;

import hr.fer.ruazosa.lostnfound.entity.User;

public interface ILostNFoundService {
    User registerUser(User user);
    boolean checkUsernameUnique(User user);
    User loginUser(User user);
}
