package com.testexample.domain;

import com.testexample.domain.enumeration.Type;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Subscriber {
    @Id
    @SequenceGenerator(name = "subscriber_sequence", sequenceName = "subscriber_sequence", allocationSize = 1)
    @GeneratedValue(generator = "subscriber_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "should not be null")
    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
}
