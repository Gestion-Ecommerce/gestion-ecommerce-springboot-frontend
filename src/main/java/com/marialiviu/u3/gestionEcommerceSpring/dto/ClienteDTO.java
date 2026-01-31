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
    /** Nombre y apellidos o razón social del cliente. */
    private String nombreCompleto;
    /** Dirección de correo electrónico de contacto. */
    private String email;
    /** Número de teléfono recuperado de la información fiscal. */
    private String telefono;
    /** Dirección postal recuperada de la información fiscal. */
    private String direccion;
    /** Fecha y hora en la que se registró el cliente en el sistema. */
    private LocalDateTime fechaCreacion;
}