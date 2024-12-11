package com.nikolas.pruebaTrinity.service;


import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.TransaccionDto;
import com.nikolas.pruebaTrinity.IService.ITransaccionService;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.entity.Transaccion;
import com.nikolas.pruebaTrinity.iRepository.IBaseRepository;
import com.nikolas.pruebaTrinity.iRepository.IProductoRepository;
import com.nikolas.pruebaTrinity.iRepository.ITransaccionRepository;
import org.springframework.stereotype.Service;

import java.lang.runtime.SwitchBootstraps;

import static com.nikolas.pruebaTrinity.Enum.TipoTransaccion.TRANSFERENCIA;


@Service
public class TransaccionService extends ABaseService<Transaccion> implements ITransaccionService{

    private final ITransaccionRepository transaccionRepository;
    private final IProductoRepository productoRepository;

    public TransaccionService(ITransaccionRepository transaccionRepository, IProductoRepository productoRepository){
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    protected IBaseRepository<Transaccion, Long> getRepository() {
        return transaccionRepository;
    }



    public ApiResponseDto<Transaccion> crearTransaccion(TransaccionDto transaccionDto) throws Exception{

        Producto productoOrigen = productoRepository.findById(transaccionDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + transaccionDto.getProductoId()));

        Producto productoDestino = null;

        if (TRANSFERENCIA.equals(transaccionDto.getTransaccion().getTipoTransaccion())){
            productoDestino = productoRepository.findById(transaccionDto.getProductoDestinoId())
                    .orElseThrow(() -> new RuntimeException("Producto destino no encontrado con ID: " + transaccionDto.getProductoDestinoId()));
        }

        switch (transaccionDto.getTransaccion().getTipoTransaccion()){
            case CONSIGNACION:
                productoOrigen.setSaldo(productoOrigen.getSaldo() + transaccionDto.getTransaccion().getMonto());
                break;

            case RETIRO:
                if (productoOrigen.getSaldo() < transaccionDto.getTransaccion().getMonto()){
                    throw new RuntimeException("Saldo insuficiente para realizar la el retiro");
                }
                productoOrigen.setSaldo(productoOrigen.getSaldo() - transaccionDto.getTransaccion().getMonto());
                break;

            case TRANSFERENCIA:
                //verificar que la transferencia es a una cuenta diferete
                if (productoOrigen.equals(productoDestino)){
                    throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
                }
                //verificar el saldo suficiente
                if (productoOrigen.getSaldo() < transaccionDto.getTransaccion().getMonto()){
                    throw new IllegalArgumentException("Saldo insuficiente para realizar la transaccion");
                }

                // Realizar la transferencia: restar el monto en la cuenta origen y sumarlo en la cuenta destino
                productoOrigen.setSaldo(productoOrigen.getSaldo() - transaccionDto.getTransaccion().getMonto());
                productoDestino.setSaldo(productoDestino.getSaldo() + transaccionDto.getTransaccion().getMonto());
                break;

            default:
                throw new IllegalArgumentException("Tipo de transaccion invalida");
        }

        //guardar los cambios en los productos
        productoRepository.save(productoOrigen);
        if (productoDestino != null){
            productoRepository.save(productoDestino);
        }

        Transaccion transaccion = new Transaccion();
        transaccion.setProducto(productoOrigen);
        transaccion.setTipoTransaccion(transaccionDto.getTransaccion().getTipoTransaccion());
        transaccion.setMonto(transaccionDto.getTransaccion().getMonto());
        transaccionRepository.save(transaccion);

        if (TRANSFERENCIA.equals(transaccionDto.getTransaccion().getTipoTransaccion())){
            Transaccion transaccionDestino = new Transaccion();
            transaccionDestino.setProducto(productoDestino);
            transaccionDestino.setTipoTransaccion(TRANSFERENCIA);
            transaccionDestino.setMonto(transaccionDto.getTransaccion().getMonto());
            transaccionRepository.save(transaccionDestino);
        }

        return new ApiResponseDto<>(transaccion, "Transferencia realizada exitosamente", true);
    }


}
