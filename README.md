# TiendaMascotasCRUD
Evaluación final S16 – POO – CRUD de **Tienda de mascotas** con JavaFX + MySQL (JDBC).
Estudiante: Diego Alonso MELCHOR QUINTO

Tabla `producto`: id_producto, nombre, categoria, marca, especie, precio, stock, fecha_vencimiento

## Pasos
1. Ejecuta `sql/tienda_mascotas.sql` en MySQL Workbench (crea BD, tabla y 10 productos de prueba).
2. Copia `config.properties.example` a `config.properties` (raíz del proyecto) y pon tu usuario/clave.
3. Ejecuta: `mvn clean javafx:run` (o en IntelliJ, la clase `Main`). Requiere JDK 21.
