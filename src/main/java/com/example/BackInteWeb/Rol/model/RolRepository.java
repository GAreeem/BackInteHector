package com.example.BackInteWeb.Rol.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Rol findByRol(String rol);

    Optional<Rol> findById(Long id);
}
