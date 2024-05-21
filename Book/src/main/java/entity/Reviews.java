package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {
    private int rating;
    private String comment;
    @Id
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Reviews() {
    }

    public Reviews(int rating, String comment, Book book, Person person) {
        this.rating = rating;
        this.comment = comment;
        this.book = book;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "rating=" + rating +
                ", comment='" + comment  +
                '}';
    }
}