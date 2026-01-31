package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una línea específica dentro de una compra.
 * * <p>Muestra la relación entre un artículo y una compra, incluyendo 
 * el nombre del producto y el precio al que se adquirió (precio congelado).</p>
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraDTO {
    /** ID de la línea en la tabla intermedia articulo_compra. */
    private int id;
    /** Identificador del artículo comprado. */
    private int idArticulo;
    /** Nombre del artículo en el momento de la venta. */
    private String nombreArticulo;
    /** Unidades adquiridas de este artículo. */
    private int unidades;
    /** Precio unitario aplicado en esta transacción. */
    private double precioCompra;
}