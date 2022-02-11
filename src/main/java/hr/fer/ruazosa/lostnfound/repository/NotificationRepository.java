package hr.fer.ruazosa.lostnfound.repository;

import hr.fer.ruazosa.lostnfound.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
