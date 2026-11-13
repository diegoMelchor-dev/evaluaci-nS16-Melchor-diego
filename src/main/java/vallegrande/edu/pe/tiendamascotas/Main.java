package vallegrande.edu.pe.tiendamascotas;

import javafx.application.Application;
import javafx.stage.Stage;
import vallegrande.edu.pe.tiendamascotas.view.VentanaProductos;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        new VentanaProductos().mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
