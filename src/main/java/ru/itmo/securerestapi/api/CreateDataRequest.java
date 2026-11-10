package ru.itmo.securerestapi.api;

import jakarta.validation.constraints.NotBlank;

public record CreateDataRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}