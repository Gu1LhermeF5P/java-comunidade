// src/main/java/com/comu/comunidade_java/repository/UserRepository.java (Exemplo Placeholder)
package com.comu.comunidade_java.repository;

import com.comu.comunidade_java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // Adicionado para exemplo
}