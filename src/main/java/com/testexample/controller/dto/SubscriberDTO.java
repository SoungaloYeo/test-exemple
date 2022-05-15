package com.testexample.service.dto;

import com.testexample.controller.validators.EnumNamePattern;
import com.testexample.domain.enumeration.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Data
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
