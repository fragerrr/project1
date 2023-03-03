package kz.springcourse.demo;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    @NotEmpty(message = "Book name can't be empty")
    @Size(min=2, max=15, message = "Book name should be between 2 and 15 char")
    private String name;
    @NotEmpty(message = "Author name can't be empty")
    @Size(min=2, max=15, message = "Author name should be between 2 and 15 char")
    private String author;
    @Min(value = 0, message = "Year should be positive number")
    private Integer year;
    private Integer took_user;

}
