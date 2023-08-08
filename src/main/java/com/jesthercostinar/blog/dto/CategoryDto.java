package com.jesthercostinar.blog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private int id;
    @NotEmpty
    @Size(min = 4)
    private String title;

    @NotEmpty
    @Size(min = 10)
    private String description;
}
