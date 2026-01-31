package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Resumen detallado de una transacción de compra.
 * * <p>Incluye los datos del cliente, la fecha, el estado del pedido y 
 * el listado de todos los artículos adquiridos en dicha compra.</p>
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 * @see DetalleCompraDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    /** Identificador único de la transacción. */
    private int id;
    /** NIF/CIF del cliente que realizó la compra. */
    private String nifCliente;
    /** Nombre completo del cliente para visualización rápida. */
    private String nombreCliente;
    /** Sello de tiempo del momento de la compra. */
    private LocalDateTime fechaCompra;
    /** Estado actual del pedido (PENDIENTE, ENVIADO, etc.). */
    private String estado;
    /** Importe total acumulado de la compra. */
    private double precioTotal;
    /** Listado de artículos y unidades que componen la compra. */
    private List<DetalleCompraDTO> lineas;
}