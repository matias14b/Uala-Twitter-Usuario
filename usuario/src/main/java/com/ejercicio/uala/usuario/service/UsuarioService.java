package com.ejercicio.uala.usuario.service;

import com.ejercicio.uala.usuario.domain.Usuario;

public interface UsuarioService {
    Usuario validarExisteUsuario(String username);

    Usuario seguir(Long idUsuarioSeguidor, Long idUsuarioSeguido);

    Usuario buscarPorId(Long id);
}

