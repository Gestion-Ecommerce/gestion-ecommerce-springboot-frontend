package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO simplificado para la relación técnica entre artículos y compras.
 * * <p>A diferencia del {@link DetalleCompraDTO}, este se centra en los IDs 
 * y valores numéricos crudos de la tabla intermedia.</p>
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloCompraDTO {
    /** Identificador de la línea. */
    private int id;
    /** Identificador de la compra vinculada. */
    private int idCompra;
    /** Identificador del artículo vinculado. */
    private int idArticulo;
    /** Unidades vendidas. */
    private int unidades;
    /** Precio al que se realizó la venta. */
    private double precioCompra;
}