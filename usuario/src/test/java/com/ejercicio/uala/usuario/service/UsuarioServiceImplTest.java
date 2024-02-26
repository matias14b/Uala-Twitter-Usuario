package com.ejercicio.uala.usuario.service;

import com.ejercicio.uala.usuario.builder.UsuarioBuilder;
import com.ejercicio.uala.usuario.domain.Usuario;
import com.ejercicio.uala.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void validarExisteUsuario_conIdUsuarioExistente_retornaUsuario() {
        Usuario usuario = UsuarioBuilder.base().conUsername("Ejemplo").build();
        persistirEnBase(usuario);
        Usuario usuarioValidado = usuarioServiceImpl.validarExisteUsuario(usuario.getUsername());
        assertThat(usuarioValidado.getUsername()).isEqualTo(usuario.getUsername());
    }

    @Test
    void validarExisteUsuario_conIdUsuarioInexistente_lanzaExcepcion() {
        assertThatExceptionOfType(IllegalAccessError.class)
                .isThrownBy((() -> usuarioServiceImpl.validarExisteUsuario("usuarioInvalido")))
                .withMessage("Credenciales invalidas.");
    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidoExistenteYUsuarioASeguirValido_retornaUsuarioSeguidorConIdDeUsuarioSeguidoYPersisteEnBase() {
        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conUsername("Seguidor")
                .conSeguidosId(new ArrayList<>())
                .build();
        persistirEnBase(usuarioSeguidor);

        Usuario usuarioASeguir = UsuarioBuilder
                .base()
                .conUsername("Seguido")
                .build();
        persistirEnBase(usuarioASeguir);

        Usuario usuarioSeguidorConIdDeUsuarioSeguido = usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioASeguir.getId());


        assertThat(usuarioSeguidorConIdDeUsuarioSeguido.getSeguidosId()).contains(usuarioASeguir.getId());
    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidoExistenteConUsuarioSeguidorPrevioValidoAgregaNuevoUsuarioSeguidorValido_retornaUsuarioSeguidorConIdDeUsuarioSeguidoYIdDeUsuarioASeguir() {
        Usuario usuarioSeguido = UsuarioBuilder
                .base()
                .conUsername("Seguido")
                .build();
        persistirEnBase(usuarioSeguido);

        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conUsername("Seguidor")
                .conSeguidosId(Arrays.asList(usuarioSeguido.getId()))
                .build();
        persistirEnBase(usuarioSeguidor);

        Usuario usuarioASeguir = UsuarioBuilder
                .base()
                .conUsername("UsuarioASeguir")
                .build();
        persistirEnBase(usuarioASeguir);

        Usuario usuarioSeguidorConIdDeUsuarioSeguido = usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioASeguir.getId());

        assertThat(usuarioSeguidorConIdDeUsuarioSeguido.getSeguidosId()).contains(usuarioSeguido.getId(), usuarioASeguir.getId());
    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidorValidoSigueAUsuarioSeguidoIntentaSeguirNuevamenteAUsuarioSeguido_lanzaExcepcion() {
        Usuario usuarioSeguido = UsuarioBuilder
                .base()
                .conUsername("Seguido")
                .build();
        persistirEnBase(usuarioSeguido);

        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conUsername("Seguidor")
                .conSeguidosId(Arrays.asList(usuarioSeguido.getId()))
                .build();
        persistirEnBase(usuarioSeguidor);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioSeguido.getId()))
                .withMessage("Ya esta siguiendo a este usuario.");

    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidorExistenteSigueNuevoUsuarioInvalido_lanzaExcepcion() {
        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conUsername("Seguidor")
                .build();
        persistirEnBase(usuarioSeguidor);

        Usuario usuarioSeguido = UsuarioBuilder
                .base()
                .conId(usuarioSeguidor.getId() + 1L)
                .conUsername("Seguido")
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioSeguido.getId()))
                .withMessage("El usuario al que está intentando seguir es inexistente.");

    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidorIntentaSeguirASiMismo_lanzaExcepcion() {

        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conUsername("Seguido")
                .build();
        persistirEnBase(usuarioSeguidor);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioSeguidor.getId()))
                .withMessage("El usuario a seguir no puede ser el mismo que el usuario seguidor");

    }

    @Test
    @Transactional
    void seguirUsuario_conUsuarioSeguidorInexistente_lanzaExcepcion() {
        Usuario usuarioSeguido = UsuarioBuilder
                .base()
                .conUsername("Seguidor")
                .build();
        persistirEnBase(usuarioSeguido);

        Usuario usuarioSeguidor = UsuarioBuilder
                .base()
                .conId(usuarioSeguido.getId() + 1)
                .conUsername("Seguido")
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioServiceImpl.seguir(usuarioSeguidor.getId(), usuarioSeguido.getId()))
                .withMessage("El usuario es inexistente.");

    }

    @Test
    @Transactional
    void buscarPorId_conIdUsuarioExistente_retornaUsuario() {
        Usuario usuario = UsuarioBuilder.base().conUsername("Ejemplo").build();
        persistirEnBase(usuario);

        Usuario usuarioValidado = usuarioServiceImpl.buscarUsuarioConSeguidosPorId(usuario.getId());
        assertThat(usuarioValidado.getUsername()).isEqualTo(usuario.getUsername());
    }

    @Test
    void buscarPorId_conIdUsuarioInexistente_lanzaExcepcion() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy((() -> usuarioServiceImpl.buscarUsuarioConSeguidosPorId(-1L)))
                .withMessage("El usuario es inexistente.");
    }

    private void persistirEnBase(Usuario usuario) {
        entityManager.persist(usuario);
        entityManager.flush();
        entityManager.detach(usuario);
    }
}