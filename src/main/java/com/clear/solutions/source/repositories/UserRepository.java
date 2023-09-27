package com.clear.solutions.source.repositories;

import com.clear.solutions.source.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where birthday between(?, ?) ", nativeQuery = true)
    Optional<User> findByBirthdateBetween(LocalDate dateStart, LocalDate dateEnd);
}
