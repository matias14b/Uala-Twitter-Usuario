package com.ejercicio.uala.usuario.service;

import com.ejercicio.uala.usuario.domain.Usuario;
import com.ejercicio.uala.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario validarExisteUsuario(String username) throws IllegalAccessError {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new IllegalAccessError("Credenciales invalidas.");
        }
        return usuario;
    }

    @Override
    public Usuario seguir(Long idUsuarioSeguidor, Long idUsuarioSeguido) {
        Usuario usuarioSeguido = usuarioRepository.findById(idUsuarioSeguido)
                .orElseThrow(() -> new IllegalArgumentException("El usuario al que está intentando seguir es inexistente."));

        Usuario usuarioSeguidor = usuarioRepository.findById(idUsuarioSeguidor)
                .orElseThrow(() -> new IllegalArgumentException("El usuario es inexistente."));

        Assert.isTrue(usuarioSeguidor.getSeguidosId()
                .stream()
                .noneMatch(seguidoId -> seguidoId.equals(usuarioSeguido.getId())),"Ya esta siguiendo a este usuario.");

        usuarioSeguidor.getSeguidosId().add(idUsuarioSeguido);
        return usuarioRepository.save(usuarioSeguidor);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario es inexistente."));
    }
}
