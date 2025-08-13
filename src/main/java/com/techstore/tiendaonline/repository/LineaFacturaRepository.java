package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Factura;
import com.techstore.tiendaonline.entity.LineaFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LineaFacturaRepository extends JpaRepository<LineaFactura, String> {
    
    List<LineaFactura> findByFactura(Factura factura);
}

