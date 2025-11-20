package com.techstore.tiendaonline.service;

import com.techstore.tiendaonline.dto.AgregarProductoRequest;
import com.techstore.tiendaonline.entity.*;
import com.techstore.tiendaonline.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Servicio central para la lógica de negocio de los Pedidos (Carrito de compras).
 * Versión Final: Tech Store.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final LineaPedidoRepository lineaPedidoRepository;
    private final FacturaRepository facturaRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         ProductoRepository productoRepository,
                         LineaPedidoRepository lineaPedidoRepository,
                         FacturaRepository facturaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.lineaPedidoRepository = lineaPedidoRepository;
        this.facturaRepository = facturaRepository;
    }

    // --- MÉTODOS PÚBLICOS ---

    /**
     * Añade un producto al carrito.
     */
    @Transactional
    public Pedido agregarProductoAlCarrito(Cliente cliente, AgregarProductoRequest request) {
        Pedido carrito = getOrCreateCarrito(cliente);

        // 1. Buscar Producto (Usando el ID correcto del Request)
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + request.getIdProducto()));

        // 2. Validar Stock
        if (producto.getStock() < request.getCantidad()) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // 3. Buscar si ya existe la línea en el carrito
        Optional<LineaPedido> lineaExistente = carrito.getLineasPedido().stream()
                .filter(lp -> lp.getProducto().getId().equals(request.getIdProducto()))
                .findFirst();

        if (lineaExistente.isPresent()) {
            // Actualizar cantidad si ya existe
            LineaPedido linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + request.getCantidad());
            // Recalcular subtotal
            linea.setSubtotal(producto.getPrecio().multiply(new BigDecimal(linea.getCantidad())));
        } else {
            // Crear nueva línea si no existe
            LineaPedido nuevaLinea = new LineaPedido();
            nuevaLinea.setPedido(carrito);
            nuevaLinea.setProducto(producto);
            nuevaLinea.setCantidad(request.getCantidad());
            nuevaLinea.setPrecioUnitario(producto.getPrecio());
            nuevaLinea.setSubtotal(producto.getPrecio().multiply(new BigDecimal(request.getCantidad())));

            carrito.getLineasPedido().add(nuevaLinea);
            lineaPedidoRepository.save(nuevaLinea);
        }

        recalcularTotal(carrito);
        return pedidoRepository.save(carrito);
    }

    /**
     * Confirma el pedido, descuenta stock y genera factura.
     */
    @Transactional
    public Pedido confirmarPedido(Cliente cliente) {
        Pedido pedido = getCarritoActivo(cliente)
                .orElseThrow(() -> new IllegalStateException("No hay un carrito activo para confirmar."));

        if (pedido.getLineasPedido().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        // 1. Validar y descontar Stock de cada línea
        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto producto = linea.getProducto();

            if (producto.getStock() < linea.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para: " + producto.getNombre());
            }

            // Actualizar el stock en la BD
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);
        }

        // 2. Actualizar estado del Pedido
        pedido.setEstado("COMPLETADO");
        pedido.setFechaPedido(LocalDateTime.now());
        pedidoRepository.save(pedido);

        // 3. Generar Factura
        Factura factura = new Factura(pedido, pedido.getTotal());
        facturaRepository.save(factura);

        return pedido;
    }

    /**
     * Obtiene el carrito actual.
     */
    public Pedido obtenerCarrito(Cliente cliente) {
        return getCarritoActivo(cliente)
                .orElseThrow(() -> new IllegalArgumentException("No hay un carrito activo."));
    }

    // --- MÉTODOS PRIVADOS ---

    private Optional<Pedido> getCarritoActivo(Cliente cliente) {
        return pedidoRepository.findByClienteAndEstado(cliente, "ACTIVO");
    }

    private Pedido getOrCreateCarrito(Cliente cliente) {
        return getCarritoActivo(cliente).orElseGet(() -> {
            Pedido p = new Pedido();
            p.setCliente(cliente);
            p.setEstado("ACTIVO");
            p.setTotal(BigDecimal.ZERO);
            p.setLineasPedido(new ArrayList<>());
            return pedidoRepository.save(p);
        });
    }

    private void recalcularTotal(Pedido pedido) {
        BigDecimal total = pedido.getLineasPedido().stream()
                .map(LineaPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(total);
    }
}