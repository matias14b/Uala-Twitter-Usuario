package com.ejercicio.uala.usuario.service;

import com.ejercicio.uala.usuario.domain.Usuario;
import com.ejercicio.uala.usuario.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO validarExisteUsuario(String username);

    Usuario seguir(Long idUsuarioSeguidor, Long idUsuarioSeguido);
}

