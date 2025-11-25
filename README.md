TechStore - Sistema de Gestión de Pedidos y Facturación


Este proyecto implementa una aplicación web completa de comercio electrónico (E-commerce) con una arquitectura Full Stack robusta, utilizando Spring Boot para el Backend y HTML/JavaScript para el Frontend.

El sistema simula el flujo completo de venta, desde el catálogo de productos hasta la generación de facturas y la gestión de perfiles de cliente.


Tecnologías Utilizadas


Backend: Java 17, Spring Boot 3, Spring Security, JPA/Hibernate.

Base de Datos: H2 Database (en memoria, para desarrollo).

Frontend: HTML5, JavaScript (Vanilla), TailwindCSS (para diseño responsive).

Seguridad: BCrypt para cifrado de contraseñas y autenticación por sesiones HTTP.


⚙️ Instrucciones de Configuración y Ejecución


Para que el proyecto funcione localmente, es necesario configurar la ruta de las imágenes en el sistema operativo.

1. Configuración de Imágenes Locales (CRÍTICO)

La aplicación está configurada para buscar las imágenes de los productos en la ruta absoluta de Windows. Si esta carpeta no existe o no contiene los archivos, el catálogo no cargará correctamente las imágenes.

Vaya a la carpeta RECURSOS_INSTALACION que se encuentra en la raíz de este proyecto (que contiene los archivos .avif).

Cree una carpeta en su disco local C: con el nombre exacto: imagenes_tienda.

Copie las imágenes de RECURSOS_INSTALACION y péguelas dentro de C:\imagenes_tienda.

2. Ejecución de la Aplicación

Asegúrese de tener JDK 17 instalado.

Abra el proyecto en IntelliJ IDEA.

Deje que Maven descargue todas las dependencias.

Ejecute la clase principal TiendaOnlineApplication.java.

Acceda a la aplicación en su navegador: http://localhost:8080


🔑 Credenciales de Prueba


La base de datos se inicializa automáticamente al arrancar con un usuario de prueba para facilitar la evaluación:

Usuario: cliente1

Contraseña: 123456


📋 Funcionalidades Principales


Autenticación: Login/Logout seguro con sesiones y contraseñas cifradas.

Catálogo: Visualización de productos con imágenes cargadas desde el sistema operativo (/images/**).

Carrito: Gestión de productos, cálculo de subtotales y total final.

Pedidos: Confirmación de compra con descuento de stock y cambio de estado a "COMPLETADO".

Facturación: Generación automática de una entidad Factura asociada al pedido.

Perfil: Visualización y edición de los datos personales del cliente
