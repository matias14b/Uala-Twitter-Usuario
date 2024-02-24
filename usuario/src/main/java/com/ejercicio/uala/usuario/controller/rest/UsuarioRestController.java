package com.ejercicio.uala.usuario.controller.rest;

import com.ejercicio.uala.usuario.domain.Usuario;
import com.ejercicio.uala.usuario.dto.UsuarioDTO;
import com.ejercicio.uala.usuario.service.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @PutMapping("/api/usuario/{idUsuarioSeguidor}/seguidor/{idUsuarioSeguido}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsuarioDTO seguir(@PathVariable Long idUsuarioSeguidor, @PathVariable Long idUsuarioSeguido) {
        Usuario usuarioSeguidor = usuarioServiceImpl.seguir(idUsuarioSeguidor, idUsuarioSeguido);
        return new UsuarioDTO(usuarioSeguidor.getUsername(), usuarioSeguidor.getSeguidosId());
    }

    @GetMapping("/api/usuario/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsuarioDTO iniciarSesion(@PathVariable String username) throws IllegalAccessException {
        return usuarioServiceImpl.validarExisteUsuario(username);
    }
}
