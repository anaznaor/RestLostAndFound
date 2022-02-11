package hr.fer.ruazosa.lostnfound.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    @Column(name = "title")
    private String title;
    @NotBlank(message = "Subject cannot be empty")
    @Column(name = "subject")
    private String subject;
    @NotBlank(message = "Date cannot be empty")
    @Column(name = "date")
    private Date date;
    @NotBlank(message = "description cannot be empty")
    @Column(name = "description")
    private String description;
    @NotBlank(message = "address cannot be empty")
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Notification(Long id, String title, String type, String description, String address, User user) {
        this.id = id;
        this.title = title;
        this.subject = type;
        this.date = new Date();
        this.description = description;
        this.address = address;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return subject;
    }

    public void setType(String type) {
        this.subject = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(subject, that.subject) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subject, date, description, address, user);
    }
}
