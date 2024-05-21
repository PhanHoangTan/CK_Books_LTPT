package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.listRatedBooks", query = "SELECT b FROM Book b INNER JOIN b.reviews r " +
                "INNER JOIN b.authors a WHERE r.rating > :rating and a = :author"),
        @NamedQuery(name = "Book.listBooks", query = "SELECT b FROM Book b"),
})

@Inheritance(strategy = InheritanceType.JOINED)
public  class Book implements Serializable {
    @Id
    @Column(name = "ISBN")
    protected String ISBN;
    protected String name;
    @Column(name = "publish_year")
    protected int publishYear;
    @Column(name = "num_of_pages")
    protected int numberOfPages;
    protected double price;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    protected Publisher publisher;


    @ElementCollection
    @CollectionTable(name = "books_authors", joinColumns = @JoinColumn(name = "ISBN"))
    @Column(name = "author")

    protected Set<String> authors;

    @OneToMany
    @JoinColumn(name = "ISBN")
    protected Set<Reviews> reviews;







    public Book() {
    }

    public Book(String ISBN, String name, int publishYear, int numberOfPages, double price, Set<String> authors, Publisher publisher) {
        this.ISBN = ISBN;
        this.name = name;
        this.publishYear = publishYear;
        this.numberOfPages = numberOfPages;
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", publishYear=" + publishYear +
                ", numOfPages=" + numberOfPages +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", reviews=" + reviews +
                '}';
    }
}