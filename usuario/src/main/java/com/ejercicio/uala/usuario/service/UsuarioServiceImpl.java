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
        Usuario usuarioSeguido = usuarioRepository.findById(idUsuarioSeguido).orElse(null);
        Assert.notNull(usuarioSeguido, "El usuario al que está intentando seguir es inexistente.");

        Usuario usuarioSeguidor = usuarioRepository.findById(idUsuarioSeguidor).orElse(null);
        Assert.notNull(usuarioSeguidor, "El usuario es inexistente.");

        Assert.isTrue(usuarioSeguidor.getSeguidosId().stream().filter(seguidoId -> seguidoId.equals(usuarioSeguido.getId())).collect(Collectors.toList()).isEmpty(), "Ya esta siguiendo a este usuario.");
        usuarioSeguidor.getSeguidosId().add(idUsuarioSeguido);
        return usuarioRepository.save(usuarioSeguidor);
    }
}
