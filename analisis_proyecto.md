# Análisis del Proyecto de Aplicación Informática

## 1. Alcance del Proyecto

El objetivo de este proyecto es desarrollar una aplicación informática completa, incluyendo la documentación técnica, el diseño de la base de datos y la implementación de los programas. La aplicación se centrará en la gestión de pedidos y facturación para una pequeña tienda en línea, con funcionalidades mínimas que incluyen autenticación de usuarios, gestión de productos, carrito de compras, seguimiento de pedidos y consulta de facturas. Se generará la documentación necesaria para cada fase del proyecto, así como los archivos de código fuente y la exportación de la base de datos.

## 2. Requerimientos Detallados

### 2.1. Base de Datos

La base de datos debe contener al menos 6 tablas con 3 relaciones. Las tablas mínimas identificadas son: `clientes`, `productos`, `pedidos`, `linea_pedido`, `facturas` y `linea_factura`. Se añadirá una tabla `usuarios` para la autenticación.

### 2.2. Autenticación

La aplicación requerirá autenticación de usuario y contraseña contra la tabla `usuarios` antes de acceder a cualquier página o sección.

### 2.3. Formularios

Se incluirá al menos un formulario, específicamente para que el cliente pueda actualizar sus datos.

### 2.4. Páginas/Sectores

Además de la página de autenticación, la aplicación debe tener al menos 5 páginas o sectores:

*   **Página inicial/Productos:** Listado de productos con opción de añadir al carrito de compras (creación de `pedido` y `linea_pedido`).
*   **Página de Carrito/Pedido Actual:** Consulta y actualización del pedido actual y sus líneas. Al aceptar, se generará la `factura`.
*   **Página de Seguimiento de Pedidos:** Para consultar el estado de los pedidos (creado, en transporte, entregado).
*   **Página de Consulta de Facturas:** Para visualizar las facturas generadas.
*   **Página de Actualización de Datos del Cliente:** Formulario para que el cliente modifique su información personal.

### 2.5. Menú de Navegación

Se implementará un menú que permita el acceso a todas las páginas de la aplicación.

### 2.6. Gestión de Imágenes

Las imágenes asociadas a entidades (ej. fotos de productos) se guardarán en el sistema operativo, no en la base de datos. La base de datos almacenará la ruta y el nombre del archivo de la imagen.

### 2.7. Administración de Contenido (Fuera de Alcance Inicial)

La administración de productos y clientes (creación, modificación) no será parte de esta fase inicial del proyecto. Estas operaciones se realizarán directamente en la base de datos (ej. con MySQL Workbench).

## 3. Viabilidad

### 3.1. Viabilidad Técnica

El proyecto es técnicamente viable. Se pueden utilizar lenguajes de programación como Java para la lógica de negocio y MySQL para la gestión de la base de datos, que son tecnologías maduras y bien documentadas. La gestión de imágenes fuera de la base de datos es una práctica estándar. Los requisitos de autenticación, formularios y navegación son implementables con las tecnologías actuales.

### 3.2. Viabilidad Económica

(Se requiere más información para una evaluación económica detallada. Asumiendo recursos y tiempo adecuados, el proyecto es viable dentro de un presupuesto razonable para una aplicación de este alcance).

## 4. Fases del Proyecto

Las fases del proyecto se han definido de la siguiente manera:

1.  Análisis de requisitos y definición del proyecto.
2.  Diseño de la aplicación y arquitectura del sistema.
3.  Diseño de la base de datos.
4.  Desarrollo de la aplicación.
5.  Documentación técnica y manual de usuario.
6.  Entrega de resultados al usuario.

## 5. Diseño de la Aplicación

### 5.1. Casos de Uso

*   **CU-001: Autenticar Usuario:** El usuario introduce credenciales y el sistema verifica su validez.
*   **CU-002: Ver Lista de Productos:** El usuario visualiza los productos disponibles.
*   **CU-003: Añadir Producto al Carrito:** El usuario selecciona un producto y lo añade a su pedido actual.
*   **CU-004: Consultar/Modificar Pedido Actual:** El usuario revisa los ítems en su carrito y puede ajustar cantidades o eliminar productos.
*   **CU-005: Confirmar Pedido y Generar Factura:** El usuario finaliza su pedido, y el sistema genera una factura asociada.
*   **CU-006: Consultar Seguimiento de Pedidos:** El usuario verifica el estado de sus pedidos anteriores.
*   **CU-007: Consultar Facturas:** El usuario accede a un listado de sus facturas.
*   **CU-008: Actualizar Datos Personales:** El usuario modifica su información de perfil.

### 5.2. Mockups (Vistas)

Se crearán mockups para las siguientes vistas principales:

*   Página de Login
*   Página de Productos
*   Página de Carrito/Pedido Actual
*   Página de Seguimiento de Pedidos
*   Página de Facturas
*   Página de Actualización de Datos del Cliente

### 5.3. Idiomas

La aplicación se desarrollará inicialmente en español.

## 6. Base de Datos

Se detallará el diseño de la base de datos en una fase posterior, incluyendo el esquema de las 7 tablas (`clientes`, `productos`, `pedidos`, `linea_pedido`, `facturas`, `linea_factura`, `usuarios`) y sus relaciones.

## 7. Clases

Se definirán las clases principales de la aplicación en la fase de diseño, siguiendo un enfoque de programación orientada a objetos (ej. `Usuario`, `Cliente`, `Producto`, `Pedido`, `LineaPedido`, `Factura`, `LineaFactura`).

## 8. Seguridad

La seguridad se centrará en la autenticación de usuarios y la protección de datos sensibles. Se implementarán buenas prácticas de seguridad en el desarrollo de la aplicación.

