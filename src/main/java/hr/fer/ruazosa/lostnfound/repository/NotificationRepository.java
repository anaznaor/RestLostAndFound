package hr.fer.ruazosa.lostnfound.repository;

import hr.fer.ruazosa.lostnfound.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT u FROM Notification u WHERE notification_id = ?1")
    List<Notification> findNotifById(Long id);

}
