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
@Table(name = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 15, message = "Name size should be between 2 and 15 characters")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Age shouldn't be empty")
    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private Integer age;
}
