package com.example.customer.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(of = {"id", "name", "zipCode"})
@EqualsAndHashCode(of = {"id"})
public class Address {

    private String id;

    private String name;

    @NotBlank
    private String zipCode;

    @NotBlank
    @Size(max = 100)
    private String streetName;

    @NotBlank
    @Size(min = 1, max = 5)
    private String streetNumber;

    @NotBlank
    @Size(max = 80)
    private String neighborhood;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{2}$")
    private String country;
}
