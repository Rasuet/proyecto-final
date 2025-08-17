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
import java.util.List;
import java.util.Optional;

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

    public List<Pedido> obtenerHistorial(Cliente cliente) {
        return pedidoRepository.findByClienteOrderByFechaPedidoDesc(cliente);
    }

    @Transactional
    public Pedido agregarProductoAlCarrito(Cliente cliente, AgregarProductoRequest request) {
        // 1. Obtener carrito (usando el método potente para asegurar que cargue líneas)
        Pedido carrito = getOrCreateCarrito(cliente);

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            throw new IllegalStateException("Stock insuficiente: " + producto.getNombre());
        }

        // 2. Buscar si ya existe la línea en memoria
        Optional<LineaPedido> lineaExistente = carrito.getLineasPedido().stream()
                .filter(lp -> lp.getProducto().getId().equals(request.getIdProducto()))
                .findFirst();

        if (lineaExistente.isPresent()) {
            LineaPedido linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + request.getCantidad());
            linea.setSubtotal(producto.getPrecio().multiply(new BigDecimal(linea.getCantidad())));
        } else {
            LineaPedido nuevaLinea = new LineaPedido();
            nuevaLinea.setPedido(carrito);
            nuevaLinea.setProducto(producto);
            nuevaLinea.setCantidad(request.getCantidad());
            nuevaLinea.setPrecioUnitario(producto.getPrecio());
            nuevaLinea.setSubtotal(producto.getPrecio().multiply(new BigDecimal(request.getCantidad())));

            carrito.getLineasPedido().add(nuevaLinea);
            // Guardamos explícitamente la línea
            lineaPedidoRepository.save(nuevaLinea);
        }

        recalcularTotal(carrito);
        pedidoRepository.saveAndFlush(carrito);

        // 3. CRÍTICO: Devolvemos el carrito recargado con FETCH para que el Controller vea las líneas
        // Esto soluciona el problema de que el carrito aparezca vacío tras añadir
        return pedidoRepository.findByClienteAndEstadoWithLineas(cliente, "ACTIVO")
                .orElse(carrito);
    }

    // Usamos el método FETCH para leer el carrito
    public Pedido obtenerCarrito(Cliente cliente) {
        return pedidoRepository.findByClienteAndEstadoWithLineas(cliente, "ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("Tu carrito está vacío (Técnicamente no existe)."));
    }

    // --- MÉTODOS PRIVADOS ---

    private Optional<Pedido> getCarritoActivo(Cliente cliente) {
        // Aquí usamos el método normal porque solo necesitamos el ID para operaciones internas
        return pedidoRepository.findByClienteAndEstado(cliente, "ACTIVO");
    }

    private Pedido getOrCreateCarrito(Cliente cliente) {
        // Intentamos buscar con FETCH primero
        return pedidoRepository.findByClienteAndEstadoWithLineas(cliente, "ACTIVO")
                .orElseGet(() -> {
                    Pedido p = new Pedido();
                    p.setCliente(cliente);
                    p.setEstado("ACTIVO");
                    p.setTotal(BigDecimal.ZERO);
                    p.setLineasPedido(new ArrayList<>());
                    return pedidoRepository.save(p);
                });
    }

    @Transactional
    public Pedido confirmarPedido(Cliente cliente) {
        Pedido pedido = obtenerCarrito(cliente); // Usamos obtenerCarrito para asegurar carga

        if (pedido.getLineasPedido().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        for (LineaPedido linea : pedido.getLineasPedido()) {
            Producto producto = linea.getProducto();
            if (producto.getStock() < linea.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setEstado("COMPLETADO");
        pedido.setFechaPedido(LocalDateTime.now());
        pedidoRepository.save(pedido);

        Factura factura = new Factura(pedido, pedido.getTotal());
        facturaRepository.save(factura);

        return pedido;
    }

    private void recalcularTotal(Pedido pedido) {
        BigDecimal total = pedido.getLineasPedido().stream()
                .map(LineaPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(total);
    }
}