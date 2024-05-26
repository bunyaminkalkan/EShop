package com.example.eshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class BaseEntity {
    private Optional<LocalDateTime> createdDate = Optional.empty();
    private Optional<LocalDateTime> updatedDate = Optional.empty();
}
