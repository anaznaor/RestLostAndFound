package hr.fer.ruazosa.lostnfound.repository;

import hr.fer.ruazosa.lostnfound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u where u.username = ?1 and u.password = ?2")
    List<User> findByUserNameAndPassword(String userName, String password);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);
}
