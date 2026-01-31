package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos para la entidad Artículo.
 * * <p>Se utiliza para enviar la información del catálogo desde el Backend 
 * hacia el Frontend, evitando exponer directamente la entidad JPA.</p>
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDTO {
    /** Identificador único del artículo. */
    private Integer id;
    /** Nombre comercial del producto. */
    private String nombre;
    /** Descripción detallada de las características del artículo. */
    private String descripcion;
    /** Precio de venta en el catálogo en formato double. */
    private double precioActual;
    /** Unidades disponibles en el almacén. */
    private int stock;
}