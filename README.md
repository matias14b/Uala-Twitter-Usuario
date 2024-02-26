
# Servicio Usuario

El objetivo de este servicio es el de consultar usuarios (Validar existencia de usuarios) en la Base de datos y el de ofrecer la habilidad de que un Usuario X pueda "Seguir" a un Usuario Y.

### DER Y UML
Para esto se ha modelado 2 tablas relacionadas, donde un Usuario con su id y username ejerce de dueño de una relacion de One(usuario) to many(Seguidos)

Esto permite que 1 usuario pueda tener 0 a N seguidos:

![image](https://github.com/matias14b/Uala-Twitter-Usuario/assets/127508318/5c4bb4ea-8599-498d-8400-1b2484730a04)

### Dominio
La clase de dominio esta formada por un Usuario que contiene una lista de seguidosId
![image](https://github.com/matias14b/Uala-Twitter-Usuario/assets/127508318/da479a4b-6a09-46b5-b9f2-d56989737a92)

### Diagrama de servicio

A nivel arquitectura se decidio por MVC 
![image](https://github.com/matias14b/Usuario-Uala-Twitter/assets/127508318/8f8e8e56-3cec-4c8b-b079-9fc16b814409)


## API Reference

El servicio Usuario expone los siguientes servicios:

#### Seguir usuario

```http
  PUT /api/usuario/${idUsuarioSeguidor}/seguidor/${idUsuarioSeguido}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id_usuario_seguidor` | `Long` | **Requerido**. ID del usuario que intenta realizar la accion |
| `id_usuario_seguido` | `Long` | **Requerido**. ID del usuario al que se intenta seguir |

###### Respuesta
```yaml
{
    "id": null,
    "username": "ichiban",
    "seguidosId": [
        2,
        3
    ]
}
```

#### Iniciar sesion (Valida si existe usuario)

```http
  GET /api/usuario/${username}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Requerido**. Username del usuario que se intenta validar |

###### Respuesta
```yaml
{
    "id": 1,
    "username": "ichiban",
    "seguidosId": null
} 
```

#### Buscar usuario con seguidores por id

```http
  GET/api/usuario/{id}/seguidos
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requerido**. id del usuario del que se intenta obtener sus usuarios seguidos |

###### Respuesta
```yaml
{
    "id": 1,
    "username": "ichiban",
    "seguidosId": [
        2,
        3,
        4,
        6
    ]
}
```

## Running Tests

Para correr los test, usar el siguiente comando:

```bash
  mvn clean test
```




## Tech Stack


**Server:** Java 17, Spring 3.2.2, MySQL

