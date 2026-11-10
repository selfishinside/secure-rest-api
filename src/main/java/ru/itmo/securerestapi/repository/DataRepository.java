package ru.itmo.securerestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.securerestapi.model.SecureData;

public interface DataRepository extends JpaRepository<SecureData, Long> {
}