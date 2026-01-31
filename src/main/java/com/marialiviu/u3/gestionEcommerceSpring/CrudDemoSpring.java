package com.marialiviu.u3.gestionEcommerceSpring;

import com.marialiviu.u3.gestionEcommerceSpring.dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CrudDemoSpring implements CommandLineRunner {

    // URL donde escucha tu Backend
    private final String BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(CrudDemoSpring.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n--- INICIO DEMO E-COMMERCE (MODO CLIENTE REST) ---\n");

        try {
            // Usamos un timestamp para generar nombres únicos y evitar error de duplicados
            long time = System.currentTimeMillis();

            // ==========================================
            // 1. CREAR ARTÍCULOS (CATÁLOGO)
            // ==========================================
            System.out.println("--- 1. CREANDO ARTÍCULOS ---");

            // Creamos 4 artículos diferentes
            ArticuloDTO a1 = crearArticulo("Libro Java " + time, "Libro avanzado", 29.50, 20);
            ArticuloDTO a2 = crearArticulo("Pendrive " + time, "USB 3.0 Rápido", 12.00, 100);
            ArticuloDTO a3 = crearArticulo("Auriculares " + time, "Sin stock intencionado", 49.90, 0); // Stock 0
            ArticuloDTO a4 = crearArticulo("Raton Gaming " + time, "RGB y ergonómico", 19.95, 5);

            System.out.println("✅ Artículos creados en Backend. ID Ejemplo: " + a1.getId());


            // ==========================================
            // 2. CREAR CLIENTES
            // ==========================================
            System.out.println("\n--- 2. CREANDO CLIENTES ---");

            // --- Cliente 1 (Alumno) ---
            InformacionFiscalDTO info1 = new InformacionFiscalDTO("99999999Z", "600111222", "C/ Java 1");
            ClienteDTO c1 = new ClienteDTO(
                "99999999Z", "Alumno Demo", "alumno." + time + "@test.com", info1, null
            );
            crearOActualizarCliente(c1);

            // --- Cliente 2 (Comprador) ---
            InformacionFiscalDTO info2 = new InformacionFiscalDTO("88888888Y", "600333444", "C/ Spring 2");
            ClienteDTO c2 = new ClienteDTO(
                "88888888Y", "Cliente Comprador", "comprador." + time + "@test.com", info2, null
            );
            crearOActualizarCliente(c2);


            // ==========================================
            // 3. PROCESAR COMPRAS (LA PRUEBA DE FUEGO)
            // ==========================================
            System.out.println("\n--- 3. PROCESANDO COMPRAS ---");

            // --- CASO A: Compra Normal (Cliente 1 compra 3 Pendrives) ---
            System.out.println("-> Enviando Compra 1 (Normal)...");
            CompraDTO compra1 = new CompraDTO();
            compra1.setNifCliente(c1.getNifCif());
            
            // Creamos la lista de items
            List<DetalleCompraDTO> itemsC1 = new ArrayList<>();
            // Importante: Usamos a2.getId() que nos devolvió el backend al crear el artículo
            itemsC1.add(new DetalleCompraDTO(0, a2.getId(), a2.getNombre(), 3, a2.getPrecioActual()));
            compra1.setLineas(itemsC1);

            // Enviamos al endpoint nuevo
            CompraDTO r1 = restTemplate.postForObject(BASE_URL + "/compras/nueva", compra1, CompraDTO.class);
            System.out.println("✅ Compra 1 ÉXITO. ID: " + r1.getId() + " | Total: " + r1.getPrecioTotal() + "€");


            // --- CASO B: Compra Múltiple (Cliente 2 compra Libro y Ratón) ---
            System.out.println("\n-> Enviando Compra 2 (Múltiple)...");
            CompraDTO compra2 = new CompraDTO();
            compra2.setNifCliente(c2.getNifCif());

            List<DetalleCompraDTO> itemsC2 = new ArrayList<>();
            itemsC2.add(new DetalleCompraDTO(0, a1.getId(), a1.getNombre(), 2, a1.getPrecioActual())); // 2 Libros
            itemsC2.add(new DetalleCompraDTO(0, a4.getId(), a4.getNombre(), 1, a4.getPrecioActual())); // 1 Ratón
            compra2.setLineas(itemsC2);

            CompraDTO r2 = restTemplate.postForObject(BASE_URL + "/compras/nueva", compra2, CompraDTO.class);
            System.out.println("✅ Compra 2 ÉXITO. ID: " + r2.getId() + " | Total: " + r2.getPrecioTotal() + "€");


            // --- CASO C: Compra Fallida (Stock Insuficiente) ---
            System.out.println("\n-> Enviando Compra 3 (Debe fallar)...");
            try {
                CompraDTO compraFail = new CompraDTO();
                compraFail.setNifCliente(c1.getNifCif());
                List<DetalleCompraDTO> itemsFail = new ArrayList<>();
                itemsFail.add(new DetalleCompraDTO(0, a3.getId(), a3.getNombre(), 1, a3.getPrecioActual())); // Stock 0!
                compraFail.setLineas(itemsFail);

                restTemplate.postForObject(BASE_URL + "/compras/nueva", compraFail, CompraDTO.class);
                System.err.println("❌ ERROR: La compra debería haber fallado y no lo hizo.");
            } catch (HttpClientErrorException | org.springframework.web.client.HttpServerErrorException e) {
                System.out.println("✅ EXCEPCIÓN CONTROLADA: El backend rechazó la compra correctamente.");
                System.out.println("   Mensaje del servidor: " + e.getResponseBodyAsString());
            }


            // ==========================================
            // 4. LECTURA Y ACTUALIZACIÓN
            // ==========================================
            System.out.println("\n--- 4. READ & UPDATE ---");
            
            // Verificamos que el stock ha bajado
            ArticuloDTO[] stockActual = restTemplate.getForObject(BASE_URL + "/articulos", ArticuloDTO[].class);
            System.out.println("Estado del Stock en Backend:");
            for (ArticuloDTO a : stockActual) {
                // Filtramos solo los nuestros por nombre para no ensuciar la consola
                if (a.getNombre().contains(String.valueOf(time))) {
                    System.out.println(" > " + a.getNombre() + " | Stock: " + a.getStock());
                }
            }

            // Actualizar precio del Pendrive (a2)
            System.out.println("\nSubiendo precio del Pendrive...");
            a2.setPrecioActual(15.50);
            restTemplate.postForObject(BASE_URL + "/articulos/nuevo", a2, ArticuloDTO.class);
            System.out.println("✅ Precio actualizado.");


            // ==========================================
            // 5. DELETE
            // ==========================================
            System.out.println("\n--- 5. DELETE ---");
            // Borramos la Compra 2
            restTemplate.delete(BASE_URL + "/compras/eliminar/" + r2.getId());
            System.out.println("✅ Compra ID " + r2.getId() + " eliminada del historial.");

            System.out.println("\n--- FIN DEMO COMPLETA ---");

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO EN LA DEMO:");
            e.printStackTrace();
        }
    }

    // --- MÉTODOS AUXILIARES PARA NO REPETIR CÓDIGO ---

    private ArticuloDTO crearArticulo(String nombre, String desc, double precio, int stock) {
        ArticuloDTO nuevo = new ArticuloDTO(null, nombre, desc, precio, stock);
        return restTemplate.postForObject(BASE_URL + "/articulos/nuevo", nuevo, ArticuloDTO.class);
    }

    private void crearOActualizarCliente(ClienteDTO c) {
        try {
            restTemplate.postForObject(BASE_URL + "/clientes/nuevo", c, ClienteDTO.class);
            System.out.println("✅ Cliente registrado: " + c.getNombreCompleto());
        } catch (HttpClientErrorException e) {
            System.out.println("⚠️ Cliente " + c.getNifCif() + " ya existía o hubo conflicto: " + e.getMessage());
        }
    }
}