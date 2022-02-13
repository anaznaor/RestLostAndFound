package hr.fer.ruazosa.lostnfound.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @NotBlank(message = "First name cannot be empty")
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Column(name = "last_name")
    private String lastName;
    @Email(message = "Email not in correct format")
    @Column(name = "e_mail")
    private String email;
    @NotBlank(message = "username name cannot be empty")
    private String username;
    @NotBlank(message = "Password name cannot be empty")
    private String password;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Notification> notifications;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.notifications = new LinkedList<Notification>();
    }

    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Notification> getNotifications() { return notifications; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password);
    }
}
