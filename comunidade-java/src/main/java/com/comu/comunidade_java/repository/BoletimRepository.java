package com.comu.comunidade_java.repository; // Pacote ajustado

import com.comu.comunidade_java.entity.Boletim; // Import ajustado
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletimRepository extends JpaRepository<Boletim, Long>, JpaSpecificationExecutor<Boletim> {
    Page<Boletim> findBySeverityIgnoreCase(String severity, Pageable pageable);
    Page<Boletim> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}