package vallegrande.edu.pe.tiendamascotas.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import vallegrande.edu.pe.tiendamascotas.dao.ProductoDAO;
import vallegrande.edu.pe.tiendamascotas.model.Producto;

import java.time.LocalDate;
import java.util.Locale;

public class VentanaProductos {

    private static final String VERDE = "#00897B";
    private static final String NARANJA = "#FB8C00";
    private static final String ROJO = "#E53935";
    private static final String GRIS = "#546E7A";

    private final ProductoDAO dao = new ProductoDAO();
    private final TableView<Producto> tabla = new TableView<>();

    private final TextField txtId = new TextField();
    private final TextField txtNombre = new TextField();
    private final ComboBox<String> cmbCategoria = new ComboBox<>();
    private final TextField txtMarca = new TextField();
    private final ComboBox<String> cmbEspecie = new ComboBox<>();
    private final TextField txtPrecio = new TextField();
    private final TextField txtStock = new TextField();
    private final DatePicker dpVencimiento = new DatePicker();
    private final TextField txtBuscar = new TextField();
    private final Label lblEstado = new Label("Listo");

    public void mostrar(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F7F8;");
        root.setTop(crearEncabezado());
        root.setLeft(crearFormulario());
        root.setCenter(crearPanelTabla());
        root.setBottom(crearBarraEstado());

        stage.setTitle("Tienda de Mascotas - CRUD de productos");
        stage.setScene(new Scene(root, 1150, 640));
        stage.setMinWidth(1000);
        stage.setMinHeight(560);
        stage.show();

        cargarTabla();
    }

    // ---------- Construcción de la interfaz ----------

    private VBox crearEncabezado() {
        Label titulo = new Label("🐾  Tienda de Mascotas");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        Label sub = new Label("Gestión de productos  •  JavaFX + MySQL");
        sub.setStyle("-fx-text-fill: #E0F2F1; -fx-font-size: 13px;");
        VBox box = new VBox(2, titulo, sub);
        box.setPadding(new Insets(14, 20, 14, 20));
        box.setStyle("-fx-background-color: " + VERDE + ";");
        return box;
    }

    private VBox crearFormulario() {
        txtId.setEditable(false);
        txtId.setPromptText("Automático");
        txtNombre.setPromptText("Ej. Alimento adulto 15 kg");
        txtMarca.setPromptText("Ej. Pro Plan");
        txtPrecio.setPromptText("0.00");
        txtStock.setPromptText("0");
        dpVencimiento.setPromptText("Opcional");

        cmbCategoria.setItems(FXCollections.observableArrayList(
                "Alimento", "Accesorios", "Higiene", "Juguetes", "Salud"));
        cmbCategoria.setEditable(true);
        cmbEspecie.setItems(FXCollections.observableArrayList(
                "Perro", "Gato", "Ave", "Pez", "Roedor", "Reptil", "Otros"));
        cmbEspecie.setEditable(true);

        txtId.setMaxWidth(Double.MAX_VALUE);
        txtNombre.setMaxWidth(Double.MAX_VALUE);
        cmbCategoria.setMaxWidth(Double.MAX_VALUE);
        txtMarca.setMaxWidth(Double.MAX_VALUE);
        cmbEspecie.setMaxWidth(Double.MAX_VALUE);
        txtPrecio.setMaxWidth(Double.MAX_VALUE);
        txtStock.setMaxWidth(Double.MAX_VALUE);
        dpVencimiento.setMaxWidth(Double.MAX_VALUE);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);
        int f = 0;
        agregarFila(g, f++, "ID", txtId);
        agregarFila(g, f++, "Nombre *", txtNombre);
        agregarFila(g, f++, "Categoría *", cmbCategoria);
        agregarFila(g, f++, "Marca *", txtMarca);
        agregarFila(g, f++, "Especie *", cmbEspecie);
        agregarFila(g, f++, "Precio (S/) *", txtPrecio);
        agregarFila(g, f++, "Stock *", txtStock);
        agregarFila(g, f, "Vencimiento", dpVencimiento);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints(190, 190, 250);
        c2.setHgrow(Priority.ALWAYS);
        g.getColumnConstraints().addAll(c1, c2);

        Button btnGuardar = boton("Guardar", NARANJA);
        Button btnActualizar = boton("Actualizar", VERDE);
        Button btnEliminar = boton("Eliminar", ROJO);
        Button btnLimpiar = boton("Limpiar", GRIS);
        btnGuardar.setOnAction(e -> guardar());
        btnActualizar.setOnAction(e -> actualizar());
        btnEliminar.setOnAction(e -> eliminar());
        btnLimpiar.setOnAction(e -> limpiar());

        GridPane botones = new GridPane();
        botones.setHgap(8);
        botones.setVgap(8);
        botones.add(btnGuardar, 0, 0);
        botones.add(btnActualizar, 1, 0);
        botones.add(btnEliminar, 0, 1);
        botones.add(btnLimpiar, 1, 1);
        for (Button b : new Button[]{btnGuardar, btnActualizar, btnEliminar, btnLimpiar}) {
            b.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(b, Priority.ALWAYS);
        }

        Label t = new Label("Datos del producto");
        t.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + VERDE + ";");
        Label nota = new Label("* Campos obligatorios");
        nota.setStyle("-fx-text-fill: #78909C; -fx-font-size: 11px;");

        VBox panel = new VBox(14, t, g, nota, botones);
        panel.setPadding(new Insets(18));
        panel.setPrefWidth(380);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #CFD8DC; -fx-border-width: 0 1 0 0;");
        return panel;
    }

    private void agregarFila(GridPane g, int fila, String etiqueta, Control campo) {
        Label l = new Label(etiqueta);
        l.setStyle("-fx-font-weight: bold; -fx-text-fill: #37474F;");
        g.add(l, 0, fila);
        g.add(campo, 1, fila);
    }

    private Button boton(String texto, String color) {
        Button b = new Button(texto);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;"
                + "-fx-background-radius: 6; -fx-padding: 8 14 8 14; -fx-cursor: hand;");
        return b;
    }

    @SuppressWarnings("unchecked")
    private VBox crearPanelTabla() {
        txtBuscar.setPromptText("🔍 Buscar por nombre, categoría, marca o especie...");
        txtBuscar.setPrefWidth(420);
        txtBuscar.textProperty().addListener((obs, a, b) -> cargarTabla());
        Button btnRefrescar = boton("Refrescar", VERDE);
        btnRefrescar.setOnAction(e -> {
            txtBuscar.clear();
            cargarTabla();
        });
        HBox barra = new HBox(10, txtBuscar, btnRefrescar);
        barra.setAlignment(Pos.CENTER_LEFT);

        TableColumn<Producto, Integer> cId = new TableColumn<>("ID");
        cId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        cId.setPrefWidth(50);
        TableColumn<Producto, String> cNombre = new TableColumn<>("Nombre");
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cNombre.setPrefWidth(200);
        TableColumn<Producto, String> cCat = new TableColumn<>("Categoría");
        cCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        cCat.setPrefWidth(95);
        TableColumn<Producto, String> cMarca = new TableColumn<>("Marca");
        cMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        cMarca.setPrefWidth(95);
        TableColumn<Producto, String> cEsp = new TableColumn<>("Especie");
        cEsp.setCellValueFactory(new PropertyValueFactory<>("especie"));
        cEsp.setPrefWidth(75);
        TableColumn<Producto, Double> cPrecio = new TableColumn<>("Precio (S/)");
        cPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cPrecio.setPrefWidth(85);
        cPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double v, boolean vacio) {
                super.updateItem(v, vacio);
                setText(vacio || v == null ? null : String.format(Locale.US, "%.2f", v));
            }
        });
        TableColumn<Producto, Integer> cStock = new TableColumn<>("Stock");
        cStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cStock.setPrefWidth(60);
        TableColumn<Producto, LocalDate> cVence = new TableColumn<>("Vencimiento");
        cVence.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        cVence.setPrefWidth(100);
        cVence.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate v, boolean vacio) {
                super.updateItem(v, vacio);
                setText(vacio ? null : (v == null ? "—" : v.toString()));
            }
        });

        tabla.getColumns().addAll(cId, cNombre, cCat, cMarca, cEsp, cPrecio, cStock, cVence);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabla.setPlaceholder(new Label("No hay productos para mostrar"));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, a, p) -> {
            if (p != null) mostrarEnFormulario(p);
        });
        VBox.setVgrow(tabla, Priority.ALWAYS);

        VBox box = new VBox(10, barra, tabla);
        box.setPadding(new Insets(18));
        return box;
    }

    private HBox crearBarraEstado() {
        lblEstado.setStyle("-fx-text-fill: #455A64;");
        HBox b = new HBox(lblEstado);
        b.setPadding(new Insets(6, 20, 6, 20));
        b.setStyle("-fx-background-color: #ECEFF1; -fx-border-color: #CFD8DC; -fx-border-width: 1 0 0 0;");
        return b;
    }

    // ---------- Acciones CRUD ----------

    private void cargarTabla() {
        try {
            tabla.setItems(FXCollections.observableArrayList(dao.listar(txtBuscar.getText())));
            estado("✔ Conectado a MySQL  •  " + tabla.getItems().size() + " producto(s)");
        } catch (Exception e) {
            estado("✘ Sin conexión a MySQL");
            error("No se pudo conectar o consultar MySQL.\nRevisa config.properties y que MySQL esté encendido.\n\n"
                    + e.getMessage());
        }
    }

    private void guardar() {
        Producto p = leerFormulario(false);
        if (p == null) return;
        try {
            dao.insertar(p);
            limpiar();
            cargarTabla();
            info("Producto registrado correctamente.");
        } catch (Exception e) {
            error("No se pudo registrar:\n" + e.getMessage());
        }
    }

    private void actualizar() {
        if (txtId.getText().isBlank()) {
            aviso("Selecciona un producto de la tabla para actualizarlo.");
            return;
        }
        Producto p = leerFormulario(true);
        if (p == null) return;
        try {
            dao.actualizar(p);
            limpiar();
            cargarTabla();
            info("Producto actualizado correctamente.");
        } catch (Exception e) {
            error("No se pudo actualizar:\n" + e.getMessage());
        }
    }

    private void eliminar() {
        Producto sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            aviso("Selecciona un producto de la tabla para eliminarlo.");
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar \"" + sel.getNombre() + "\"? Esta acción no se puede deshacer.",
                ButtonType.YES, ButtonType.NO);
        a.setHeaderText("Confirmar eliminación");
        if (a.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) return;
        try {
            dao.eliminar(sel.getIdProducto());
            limpiar();
            cargarTabla();
            info("Producto eliminado.");
        } catch (Exception e) {
            error("No se pudo eliminar:\n" + e.getMessage());
        }
    }

    // ---------- Utilidades ----------

    private Producto leerFormulario(boolean conId) {
        String nombre = txtNombre.getText().trim();
        String categoria = textoCombo(cmbCategoria);
        String marca = txtMarca.getText().trim();
        String especie = textoCombo(cmbEspecie);
        if (nombre.isEmpty() || categoria.isEmpty() || marca.isEmpty() || especie.isEmpty()
                || txtPrecio.getText().isBlank() || txtStock.getText().isBlank()) {
            aviso("Completa todos los campos obligatorios (*).");
            return null;
        }
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim().replace(',', '.'));
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            aviso("El precio debe ser un número válido (ej. 25.50).");
            return null;
        }
        try {
            stock = Integer.parseInt(txtStock.getText().trim());
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            aviso("El stock debe ser un número entero mayor o igual a 0.");
            return null;
        }
        Producto p = new Producto(0, nombre, categoria, marca, especie, precio, stock, dpVencimiento.getValue());
        if (conId) p.setIdProducto(Integer.parseInt(txtId.getText()));
        return p;
    }

    private String textoCombo(ComboBox<String> c) {
        String t = c.getEditor().getText();
        return t == null ? "" : t.trim();
    }

    private void mostrarEnFormulario(Producto p) {
        txtId.setText(String.valueOf(p.getIdProducto()));
        txtNombre.setText(p.getNombre());
        cmbCategoria.getEditor().setText(p.getCategoria());
        txtMarca.setText(p.getMarca());
        cmbEspecie.getEditor().setText(p.getEspecie());
        txtPrecio.setText(String.format(Locale.US, "%.2f", p.getPrecio()));
        txtStock.setText(String.valueOf(p.getStock()));
        dpVencimiento.setValue(p.getFechaVencimiento());
    }

    private void limpiar() {
        txtId.clear();
        txtNombre.clear();
        cmbCategoria.getEditor().clear();
        cmbCategoria.setValue(null);
        txtMarca.clear();
        cmbEspecie.getEditor().clear();
        cmbEspecie.setValue(null);
        txtPrecio.clear();
        txtStock.clear();
        dpVencimiento.setValue(null);
        tabla.getSelectionModel().clearSelection();
    }

    private void estado(String t) { lblEstado.setText(t); }

    private void info(String m) { alerta(Alert.AlertType.INFORMATION, "Listo", m); }
    private void aviso(String m) { alerta(Alert.AlertType.WARNING, "Atención", m); }
    private void error(String m) { alerta(Alert.AlertType.ERROR, "Error", m); }

    private void alerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert a = new Alert(tipo, msg);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
