package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "book_translations")
@Getter
@Setter


public class BookTranslation extends Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String language;
    @Column(name = "translate_name")
    private String translator;

    public BookTranslation() {
    }

    public BookTranslation(String ISBN, String name, int publishYear, int numberOfPages, double price, Set<String> authors, Publisher publisher, String language, String translator) {
        super(ISBN, name, publishYear, numberOfPages, price, authors, publisher);
        this.language = language;
        this.translator = translator;
    }

//    @Override
//    public String toString() {
//        return "BookTranslation{" +
//                "language='" + language + '\'' +
//                ", translator='" + translator + '\'' +
//                ", ISBN='" + ISBN + '\'' +
//                ", name='" + name + '\'' +
//                ", publishYear=" + publishYear +
//                ", numberOfPages=" + numberOfPages +
//                ", price=" + price +
//                ", publisher=" + publisher +
//                ", authors=" + authors +
//                ", reviews=" + reviews +
//                '}';
//    }
}
