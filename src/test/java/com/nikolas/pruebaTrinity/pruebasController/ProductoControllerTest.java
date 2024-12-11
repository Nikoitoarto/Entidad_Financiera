package com.nikolas.pruebaTrinity.pruebasController;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.ProductoDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.Enum.TipoProducto;
import com.nikolas.pruebaTrinity.IService.IProductoService;
import com.nikolas.pruebaTrinity.controller.ProductoController;
import com.nikolas.pruebaTrinity.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)  // Solo carga el controlador
public class ProductoControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private IProductoService productoService;  // Se usa MockBean para simular el servicio

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductoController(productoService)).build();
    }

    @Test
    void testCrearProducto() throws Exception {

        ProductoDto productoDto = new ProductoDto();
        productoDto.setProducto(new Producto()); // Inicializa el objeto Producto
        productoDto.getProducto().setNumeroCuenta(531009239L);
        productoDto.getProducto().setSaldo(1000.0);
        productoDto.getProducto().setTipoProducto(TipoProducto.CUENTA_AHORRO);
        productoDto.getProducto().setEstadoProducto(EstadoProducto.ACTIVA);
        productoDto.getProducto().setNombreCliente("nikolas");
        productoDto.getProducto().setExentaGmf(true);


        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNumeroCuenta(531009239L);
        producto.setSaldo(1000.0);
        producto.setTipoProducto(TipoProducto.CUENTA_AHORRO);
        producto.setEstadoProducto(EstadoProducto.ACTIVA);
        producto.setNombreCliente("nikolas");
        producto.setExentaGmf(true);

        ApiResponseDto<Producto> apiResponse = new ApiResponseDto<>(producto, "Producto creado correctamente", true);

        when(productoService.crearProducto(any(ProductoDto.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/api/producto/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"producto\": {\"numeroCuenta\": 531009239, \"saldo\": 1000.0, \"tipoProducto\": \"CUENTA_AHORRO\", \"estadoProducto\": \"ACTIVA\", \"nombreCliente\": \"nikolas\", \"exentaGmf\": true}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.numeroCuenta").value(531009239))
                .andExpect(jsonPath("$.data.saldo").value(1000.0))
                .andExpect(jsonPath("$.data.nombreCliente").value("nikolas"))
                .andExpect(jsonPath("$.message").value("Producto creado correctamente"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void tesActualizarEstadoProducto() throws Exception{

        Long productoId = 1L;
        EstadoProducto nuevoEstado = EstadoProducto.ACTIVA;

        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setTipoProducto(TipoProducto.CUENTA_AHORRO);
        producto.setEstadoProducto(EstadoProducto.INACTIVA);

        ApiResponseDto<Producto> apiResponse = new ApiResponseDto<>(producto, "Estado actualizado correctamente", true);

        when(productoService.actualizarEstadoProducto(productoId, nuevoEstado)).thenReturn(apiResponse);

        mockMvc.perform(put("/api/producto/productos/{productoId}/estado", productoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"ACTIVA\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productoId))
                .andExpect(jsonPath("$.message").value("Estado actualizado correctamente"))
                .andExpect(jsonPath("$.status").value(true));



    }
}