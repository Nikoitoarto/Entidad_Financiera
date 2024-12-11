package com.nikolas.pruebaTrinity.pruebasService;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.ProductoDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.Enum.TipoProducto;

import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.iRepository.IClienteRepository;
import com.nikolas.pruebaTrinity.iRepository.IProductoRepository;
import com.nikolas.pruebaTrinity.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private IProductoRepository productoRepository;

    @Mock
    private IClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void crearProductoTest() throws Exception {

        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        ProductoDto productoDto = new ProductoDto();
        Producto producto = new Producto();
        producto.setSaldo(1000.0);
        producto.setTipoProducto(TipoProducto.CUENTA_AHORRO);
        productoDto.setProducto(producto);
        productoDto.setClienteId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ApiResponseDto<Producto> response = productoService.crearProducto(productoDto);

        assertNotNull(response);
        assertTrue(response.getStatus());
        assertEquals("Producto creado exitosamente", response.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, times(1)).save(any(Producto.class));

    }

    @Test
    void crearProducto_clienteNoEcontrado() {

        Long clienteId = 1L;
        ProductoDto productoDto = new ProductoDto();
        productoDto.setClienteId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productoService.crearProducto(productoDto));
        assertEquals("La persona con ID "+ clienteId +" no existe", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verifyNoInteractions(productoRepository);

    }

    @Test
    void actualizarEstadoProducto_exito() throws Exception{

        Long productoId = 1L;
        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setTipoProducto(TipoProducto.CUENTA_AHORRO);
        producto.setEstadoProducto(EstadoProducto.ACTIVA);
        producto.setSaldo(0.0);

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ApiResponseDto<Producto> response = productoService.actualizarEstadoProducto(productoId, EstadoProducto.INACTIVA);

        assertNotNull(response);
        assertTrue(response.getStatus());
        assertEquals("El estado del producto se ha actualizado exitosamente", response.getMessage());
        verify(productoRepository, times(1)).findById(productoId);
        verify(productoRepository, times(1)).save(any(Producto.class));

    }

    @Test
    void actualizarEstadoProducto_errorSaldoNoCero() {
        Long productoId = 1L;
        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setTipoProducto(TipoProducto.CUENTA_AHORRO);
        producto.setEstadoProducto(EstadoProducto.INACTIVA);
        producto.setSaldo(100.0);

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productoService.actualizarEstadoProducto(productoId, EstadoProducto.CANCELADA));
        assertEquals("El saldo debe ser 0 para cancelar el producto", exception.getMessage());
        verify(productoRepository, times(1)).findById(productoId);
        verify(productoRepository, times(0)).save(any(Producto.class));
    }

    @Test
    void actualizarEstadoProducto_productoNoEncontrado() {
        // Arrange
        Long productoId = 1L;

        when(productoRepository.findById(productoId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productoService.actualizarEstadoProducto(productoId, EstadoProducto.ACTIVA));
        assertEquals("El producto con ID " + productoId + " no existe", exception.getMessage());
        verify(productoRepository, times(1)).findById(productoId);
        verifyNoMoreInteractions(productoRepository);
    }


}
