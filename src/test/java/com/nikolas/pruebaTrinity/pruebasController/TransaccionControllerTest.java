package com.nikolas.pruebaTrinity.pruebasController;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.TransaccionDto;
import com.nikolas.pruebaTrinity.Enum.TipoTransaccion;
import com.nikolas.pruebaTrinity.IService.ITransaccionService;
import com.nikolas.pruebaTrinity.controller.TransaccionController;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.entity.Transaccion;
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

@WebMvcTest(TransaccionController.class)  // Solo carga el controlador
public class TransaccionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ITransaccionService transaccionService;  // Se usa MockBean para simular el servicio

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TransaccionController(transaccionService)).build();
    }

    @Test
    void testCrearProducto() throws Exception {

        Producto producto = new Producto();
        Long productoId = 1L;

        Transaccion transaccion = new Transaccion();
        transaccion.setId(1L);
        transaccion.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        transaccion.setMonto(1000.0);


        ApiResponseDto<Transaccion> apiResponse = new ApiResponseDto<>(transaccion, "Transaccion creada correctamente", true);

        when(transaccionService.crearTransaccion(any(TransaccionDto.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/api/transaccion/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "productoId": 1,
                                     "transaccion": {
                                       "tipoTransaccion": "CONSIGNACION",
                                       "monto": 1000.0
                                     }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.tipoTransaccion").value("CONSIGNACION"))
                .andExpect(jsonPath("$.data.monto").value(1000.0))
                .andExpect(jsonPath("$.message").value("Transaccion creada correctamente"))
                .andExpect(jsonPath("$.status").value(true));
    }

}