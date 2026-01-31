package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para los datos fiscales y de contacto de un cliente.
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformacionFiscalDTO {
    /** Identificador fiscal del titular. */
    private String nifCif;
    /** Teléfono de contacto fiscal. */
    private String telefono;
    /** Dirección legal o de facturación. */
    private String direccion;
}