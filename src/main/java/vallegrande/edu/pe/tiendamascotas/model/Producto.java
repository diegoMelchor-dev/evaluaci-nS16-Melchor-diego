package vallegrande.edu.pe.tiendamascotas.model;

import java.time.LocalDate;

public class Producto {
    private int idProducto;
    private String nombre;
    private String categoria;
    private String marca;
    private String especie;
    private double precio;
    private int stock;
    private LocalDate fechaVencimiento;

    public Producto() { }

    public Producto(int idProducto, String nombre, String categoria, String marca,
                    String especie, double precio, int stock, LocalDate fechaVencimiento) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.especie = especie;
        this.precio = precio;
        this.stock = stock;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
