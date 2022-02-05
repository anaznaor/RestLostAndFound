package hr.fer.ruazosa.lostnfound;

public interface ILostNFoundService {
    User registerUser(User user);
    boolean checkUsernameUnique(User user);
    User loginUser(User user);
}
