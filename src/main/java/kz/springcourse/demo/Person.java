package kz.springcourse.demo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;
    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 15, message = "Name size should be between 2 and 15 characters")
    private String name;
    @NotNull(message = "Age shouldn't be empty")
    @Min(value = 0, message = "Age should be greater than 0")
    private Integer age;
}
