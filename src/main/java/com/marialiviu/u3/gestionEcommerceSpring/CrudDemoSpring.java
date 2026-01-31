package com.marialiviu.u3.gestionEcommerceSpring;

import com.marialiviu.u3.gestionEcommerceSpring.dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Clase principal del proyecto Frontend que actúa como cliente de la API REST.
 * * <p>Esta aplicación utiliza {@link RestTemplate} para comunicarse con el servidor Backend 
 * y realizar operaciones CRUD sobre los datos almacenados en Oracle. Se ha configurado 
 * para excluir la autoconfiguración de base de datos, ya que su única responsabilidad 
 * es la interacción remota.</p>
 * * Funcionalidades demostradas:
 * <ul>
 * <li><b>CREATE</b>: Envío de nuevos artículos y clientes al servidor mediante peticiones POST.</li>
 * <li><b>READ</b>: Recuperación y visualización del catálogo de productos mediante peticiones GET.</li>
 * <li><b>DELETE</b>: Eliminación de registros específicos mediante peticiones DELETE.</li>
 * </ul>
 * * @author Maria y Liviu
 * @version 1.0
 * @since 2026-01-31
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CrudDemoSpring implements CommandLineRunner {

    /** URL base donde se encuentra escuchando el servicio Backend. */
    private final String BASE_URL = "http://localhost:8080";

    /** Cliente HTTP para la ejecución de peticiones REST. */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Punto de entrada de la aplicación Spring Boot.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(CrudDemoSpring.class, args);
    }

    /**
     * Ejecución de la demo lógica una vez levantado el contexto de Spring.
     * * <p>El método simula un flujo de trabajo completo: crea registros, los consulta 
     * para verificar la persistencia en el servidor y finalmente realiza una limpieza 
     * de los datos de prueba.</p>
     * * @param args Argumentos pasados al ejecutar la aplicación.
     * @throws Exception En caso de errores en la comunicación o procesamiento.
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n--- INICIO DEMO CRUD LIGADO (FRONTEND -> BACKEND) ---\n");

        try {
            // ==========================================
            // 1. CREATE (Artículos y Clientes vía API)
            // ==========================================
            System.out.println("--- 1. CREATE ---");

            // Crear un Artículo en el Backend enviando un DTO
            ArticuloDTO a2 = restTemplate.postForObject(BASE_URL + "/articulos/nuevo", 
                new ArticuloDTO(null, "Pendrive 64GB", "USB 3.0 Rápido", 12.00, 100), ArticuloDTO.class);
            System.out.println("✅ Artículo '" + a2.getNombre() + "' creado en Oracle. ID: " + a2.getId());

            // Crear un Cliente en el Backend enviando un DTO
            ClienteDTO c1 = new ClienteDTO("99999999Z", "Alumno Demo", "alumno@ejemplo.com", "600111222", "C/ Java 1", null);
            restTemplate.postForObject(BASE_URL + "/clientes/nuevo", c1, ClienteDTO.class);
            System.out.println("✅ Cliente '" + c1.getNombreCompleto() + "' registrado.");

            // ==========================================
            // 2. READ (Lectura remota de Artículos)
            // ==========================================
            System.out.println("\n--- 2. READ ---");
            
            // Obtención de la lista de artículos como Array y conversión a lista para iterar
            ArticuloDTO[] articulos = restTemplate.getForObject(BASE_URL + "/articulos", ArticuloDTO[].class);
            System.out.println("--- Lista de Artículos recuperada de Oracle ---");
            Arrays.asList(articulos).forEach(a -> 
                System.out.println("ID: " + a.getId() + " | " + a.getNombre() + " | Stock: " + a.getStock())
            );

            // ==========================================
            // 3. DELETE (Limpieza remota de Cliente)
            // ==========================================
            System.out.println("\n--- 3. DELETE ---");
            
            // Borramos el cliente usando su NIF concatenado a la URL del endpoint
            restTemplate.delete(BASE_URL + "/clientes/eliminar/" + c1.getNifCif());
            System.out.println("✅ Cliente con NIF " + c1.getNifCif() + " eliminado correctamente.");

            System.out.println("\n--- FIN DEMO FRONTEND ---");

        } catch (Exception e) {
            System.err.println("❌ ERROR: No se pudo completar la comunicación con el Backend.");
            System.err.println("Detalle: " + e.getMessage());
        }
    }
}