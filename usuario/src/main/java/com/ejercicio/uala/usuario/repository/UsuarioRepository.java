package com.ejercicio.uala.usuario.repository;

import com.ejercicio.uala.usuario.domain.Usuario;
import com.ejercicio.uala.usuario.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
