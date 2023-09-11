package com.testexample.controller.dto;

import com.testexample.controller.validators.EnumNamePattern;
import com.testexample.domain.enumeration.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDTO {

    private Long id;

    @NotBlank(message = "should not be null")
    private String name;

    @Email
    private String email;

    @EnumNamePattern(regexp = "CASH|CARD")
    @NotNull
    private Type type;
}
