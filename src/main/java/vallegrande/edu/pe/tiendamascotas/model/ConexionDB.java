package vallegrande.edu.pe.tiendamascotas.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Conexión JDBC a MySQL. Lee los datos de config.properties (o variables de entorno). */
public class ConexionDB {

    private static final Properties CONFIG = cargarConfig();

    private static Properties cargarConfig() {
        Properties p = new Properties();
        Path ruta = Path.of("config.properties");
        if (Files.isRegularFile(ruta)) {
            try (InputStream in = Files.newInputStream(ruta)) {
                p.load(in);
            } catch (IOException e) {
                System.err.println("No se pudo leer config.properties: " + e.getMessage());
            }
        }
        return p;
    }

    private static String valor(String clave, String porDefecto) {
        String v = System.getenv(clave);
        if (v == null || v.isBlank()) v = CONFIG.getProperty(clave);
        return (v == null || v.isBlank()) ? porDefecto : v.trim();
    }

    public static Connection conectar() throws SQLException {
        String host = valor("DB_HOST", "localhost");
        String puerto = valor("DB_PORT", "3306");
        String bd = valor("DB_NAME", "tienda_mascotas");
        String usuario = valor("DB_USER", "root");
        String password = valor("DB_PASSWORD", "");
        String ssl = valor("DB_SSL_MODE", "DISABLED");

        String url = "jdbc:mysql://" + host + ":" + puerto + "/" + bd + "?sslMode=" + ssl
                + "&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
        return DriverManager.getConnection(url, usuario, password);
    }
}
