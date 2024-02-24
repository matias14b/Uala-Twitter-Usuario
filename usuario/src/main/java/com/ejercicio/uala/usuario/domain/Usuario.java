package com.ejercicio.uala.usuario.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Usuario {
    public Usuario(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @ElementCollection
    @CollectionTable(
            name = "seguido",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "seguidos_id")
    private List<Long> seguidosId;
}
