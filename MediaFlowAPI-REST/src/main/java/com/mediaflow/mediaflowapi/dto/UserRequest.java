package com.mediaflow.mediaflowapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name cannot be blank")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email format")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty("password")
    private String password;

    @NotNull(message = "Date of birth cannot be null")
    @JsonProperty("date birth")
    private LocalDate dateBirth;
}