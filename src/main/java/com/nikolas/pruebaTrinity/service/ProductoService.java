package com.nikolas.pruebaTrinity.service;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.ProductoDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.Enum.TipoProducto;
import com.nikolas.pruebaTrinity.IService.IProductoService;
import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.iRepository.IBaseRepository;
import com.nikolas.pruebaTrinity.iRepository.IClienteRepository;
import com.nikolas.pruebaTrinity.iRepository.IProductoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class ProductoService extends ABaseService<Producto> implements IProductoService {

    private final IProductoRepository productoRepository;
    private final IClienteRepository clienteRepository;

    public ProductoService(IProductoRepository productoRepository, IClienteRepository clienteRepository) {
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected IBaseRepository<Producto, Long> getRepository() {
        return productoRepository;
    }

    @Override
    public ApiResponseDto<Producto> crearProducto(ProductoDto productoDto) throws Exception {

        Cliente clienteEncontrado = clienteRepository.findById(productoDto.getClienteId())
                .orElseThrow(() -> new RuntimeException("La persona con ID " + productoDto.getClienteId() + " no existe"));

        String nombreCliente = clienteEncontrado.getNombres() + " " + clienteEncontrado.getApellidos();

        if (productoDto.getProducto().getSaldo() < 0) {
            throw new IllegalArgumentException("El producto saldo no puede ser negativo");
        }
        Producto nuevoProducto = new Producto();
        nuevoProducto.setEstadoProducto(EstadoProducto.ACTIVA);
        nuevoProducto.setTipoProducto(productoDto.getProducto().getTipoProducto());

        // Generar número de cuenta inicial
        if (productoDto.getProducto().getTipoProducto() == TipoProducto.CUENTA_AHORRO) {
            nuevoProducto.setNumeroCuenta(Long.parseLong(NumeroCuentaGenerador.generarNumeroCuenta("53")));
        } else {
            nuevoProducto.setNumeroCuenta(Long.parseLong(NumeroCuentaGenerador.generarNumeroCuenta("33")));
        }

        nuevoProducto.setNombreCliente(nombreCliente);
        nuevoProducto.setExentaGmf(productoDto.getProducto().getExentaGmf());
        nuevoProducto.setSaldo(productoDto.getProducto().getSaldo());

        boolean numeroUnicoGenerado = false;
        while (!numeroUnicoGenerado) {
            try {
                Producto productoGuardado = productoRepository.save(nuevoProducto);
                numeroUnicoGenerado = true;
                return new ApiResponseDto<>(productoGuardado, "Producto creado exitosamente", true);
            } catch (DataIntegrityViolationException e) {
                // Si el número de cuenta no es único, generar otro
                if (nuevoProducto.getTipoProducto() == TipoProducto.CUENTA_AHORRO) {
                    nuevoProducto.setNumeroCuenta(Long.parseLong(NumeroCuentaGenerador.generarNumeroCuenta("53")));
                } else {
                    nuevoProducto.setNumeroCuenta(Long.parseLong(NumeroCuentaGenerador.generarNumeroCuenta("33")));
                }
            }

        }

        throw new RuntimeException("No se puede crear el producto");
    }

    public ApiResponseDto<Producto> actualizarEstadoProducto(Long productoId, EstadoProducto nuevoEstado) throws Exception {
        // Buscar el producto por su ID
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + productoId + " no existe"));

        // Verificar que sea una cuenta de ahorros o corriente
        if (!(producto.getTipoProducto() == TipoProducto.CUENTA_AHORRO || producto.getTipoProducto() == TipoProducto.CUENTA_CORRIENTE)) {
            throw new IllegalArgumentException("Solo se pueden activar o inactivar cuentas de ahorro o corriente");
        }

        // Si el nuevo estado es INACTIVA o CANCELADA, manejarlo de forma diferente
        if (nuevoEstado == EstadoProducto.INACTIVA || nuevoEstado == EstadoProducto.CANCELADA) {
            // Si el estado actual es ACTIVO, cambiarlo a INACTIVA
            if (producto.getEstadoProducto() == EstadoProducto.ACTIVA && nuevoEstado == EstadoProducto.INACTIVA) {
                producto.setEstadoProducto(EstadoProducto.INACTIVA);
            }
            // Si el estado actual es INACTIVO y quieres cambiar a CANCELADO
            else if (producto.getEstadoProducto() == EstadoProducto.INACTIVA && nuevoEstado == EstadoProducto.CANCELADA) {
                // Verificar si el saldo es 0 antes de cambiar a "cancelada"
                if (producto.getSaldo() != 0) {
                    throw new IllegalArgumentException("El saldo debe ser 0 para cancelar el producto");
                }
                producto.setEstadoProducto(EstadoProducto.CANCELADA);
            }
        } else {
            // Si el nuevo estado es ACTIVO, cambiar el estado a ACTIVO
            producto.setEstadoProducto(EstadoProducto.ACTIVA);
        }
        // Actualizar el estado
        producto.setEstadoProducto(nuevoEstado);

        // Guardar los cambios
        Producto productoActualizado = productoRepository.save(producto);

        return new ApiResponseDto<>(productoActualizado, "El estado del producto se ha actualizado", true);
    }


}