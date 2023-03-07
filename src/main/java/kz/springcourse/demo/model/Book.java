package kz.springcourse.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotEmpty(message = "Book name can't be empty")
    @Size(min=2, max=15, message = "Book name should be between 2 and 15 char")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Author name can't be empty")
    @Size(min=2, max=15, message = "Author name should be between 2 and 15 char")
    @Column(name = "author")
    private String author;
    @NotNull(message = "Year shouldn't be empty")
    @Min(value = 1, message = "Year should be positive number")
    @Column(name = "year")
    private Integer year;

    @Column(name = "took_user")
    private Integer tookUser;

}
