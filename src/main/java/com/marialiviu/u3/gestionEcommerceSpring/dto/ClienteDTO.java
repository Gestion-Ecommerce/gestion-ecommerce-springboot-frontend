package com.marialiviu.u3.gestionEcommerceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Representa la información de un cliente para su uso en la capa de presentación.
 * * <p>Aglutina los datos básicos del cliente junto con su información fiscal 
 * (teléfono y dirección) para simplificar la respuesta del servidor.</p>
 * * @author Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    /** Identificador fiscal único (NIF o CIF). */
    private String nifCif;
    
    /** Nombre y apellidos. */
    private String nombreCompleto;
    
    /** Email de contacto. */
    private String email;
    
    /** * IMPORTANTE: Aquí metemos el objeto completo.
     * Esto arreglará el error del Backend y te permitirá usar setInformacionFiscal.
     */
    private InformacionFiscalDTO informacionFiscal;
    
    /** Fecha de creación. */
    private LocalDateTime fechaCreacion;
}