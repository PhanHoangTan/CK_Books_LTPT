package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "people")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "person_id")
    private String id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    private String email;
    @Column(name = "professional_title")
    private String professionalTitle;

    @OneToMany
    @JoinColumn(name = "person_id")
    private Set<Reviews> reviews;

    public Person() {
    }

    public Person(String id, String lastName, String firstName, String email, String professionalTitle) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.professionalTitle = professionalTitle;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", professionalTitle='" + professionalTitle + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}