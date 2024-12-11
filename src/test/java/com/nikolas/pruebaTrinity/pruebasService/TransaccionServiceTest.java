package com.nikolas.pruebaTrinity.pruebasService;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.TransaccionDto;
import com.nikolas.pruebaTrinity.Enum.TipoTransaccion;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.entity.Transaccion;
import com.nikolas.pruebaTrinity.iRepository.IProductoRepository;
import com.nikolas.pruebaTrinity.iRepository.ITransaccionRepository;
import com.nikolas.pruebaTrinity.service.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransaccionServiceTest {

    @Mock
    private ITransaccionRepository transaccionRepository;

    @Mock
    private IProductoRepository productoRepository;

    @Mock
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar mocks y dependencias
    }

    @Test
    void crearTransaccion_consignacionExito() throws Exception {
        Long productoId = 1L;
        Producto productoOrigen = new Producto();
        productoOrigen.setId(productoId);
        productoOrigen.setSaldo(100.0);

        TransaccionDto transaccionDto = new TransaccionDto();
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(500.0);
        transaccion.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        transaccionDto.setTransaccion(transaccion);
        transaccionDto.setProductoId(productoId);


        when(productoRepository.findById(productoId)).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoOrigen);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(new Transaccion());

        ApiResponseDto<Transaccion> response = transaccionService.crearTransaccion(transaccionDto);

        assertNotNull(response);
        assertTrue(response.getStatus());
        assertEquals(1500.0, productoOrigen.getSaldo());
        verify(productoRepository, times(1)).findById(productoId);
        verify(productoRepository, times(1)).save(productoOrigen);
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));

    }

    @Test
    void crearTransaccion_retiroExito() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSaldo(1000.0);

        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setProductoId(1L);
        transaccionDto.setTransaccion(new Transaccion());
        transaccionDto.getTransaccion().setTipoTransaccion(TipoTransaccion.RETIRO);
        transaccionDto.getTransaccion().setMonto(500.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(new Transaccion());

        ApiResponseDto<Transaccion> response = transaccionService.crearTransaccion(transaccionDto);

        assertNotNull(response);
        assertTrue(response.getStatus());
        assertEquals(500.0, producto.getSaldo());
    }

    @Test
    void crearTransaccion_retiroSaldoInsuficiente() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSaldo(100.0);

        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setProductoId(1L);
        transaccionDto.setTransaccion(new Transaccion());
        transaccionDto.getTransaccion().setTipoTransaccion(TipoTransaccion.RETIRO);
        transaccionDto.getTransaccion().setMonto(500.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transaccionService.crearTransaccion(transaccionDto);
        });

        assertEquals("Saldo insuficiente para realizar la el retiro", exception.getMessage());
    }

    @Test
    void crearTransaccion_transferenciaExito() throws Exception {
        Producto productoOrigen = new Producto();
        productoOrigen.setId(1L);
        productoOrigen.setSaldo(1000.0);

        Producto productoDestino = new Producto();
        productoDestino.setId(2L);
        productoDestino.setSaldo(500.0);

        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setProductoId(1L);
        transaccionDto.setProductoDestinoId(2L);
        transaccionDto.setTransaccion(new Transaccion());
        transaccionDto.getTransaccion().setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);
        transaccionDto.getTransaccion().setMonto(300.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(2L)).thenReturn(Optional.of(productoDestino));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoOrigen);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(new Transaccion());

        ApiResponseDto<Transaccion> response = transaccionService.crearTransaccion(transaccionDto);

        assertNotNull(response);
        assertTrue(response.getStatus());
        assertEquals(700.0, productoOrigen.getSaldo());
        assertEquals(800.0, productoDestino.getSaldo());

    }

}



