package com.peepl.peepl_codetest.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name must not exceed 250 characters")
    private String name;

    @Pattern(regexp = "^[01]$", message = "Is project must be '0' or '1'")
    private String isProject = "0";

    @Pattern(regexp = "^[01]$", message = "Is project must be '0' or '1'")
    private String selfCapture = "1";

    @NotBlank(message = "Client prefix is required")
    @Size(max = 4, message = "Client prefix must not exceed 4 characters")
    private String clientPrefix;

    private String address;

    @Size(max = 50, message = "Phone number must not exceed 50 characters")
    private String phoneNumber;

    @Size(max = 50, message = "City must not exceed 50 characters")
    private String City;
}
