package com.ejercicio.uala.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioDTO implements Serializable {
    private Long id;
    private String username;
    private List<Long> seguidosId;

    public UsuarioDTO(String username, List<Long> seguidosId) {
        this.username = username;
        this.seguidosId = seguidosId;
    }

    public UsuarioDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
