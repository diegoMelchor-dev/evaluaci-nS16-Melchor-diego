package vallegrande.edu.pe.tiendamascotas.dao;

import vallegrande.edu.pe.tiendamascotas.model.ConexionDB;
import vallegrande.edu.pe.tiendamascotas.model.Producto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    /** CREATE: registrar un producto nuevo. */
    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (nombre, categoria, marca, especie, precio, stock, fecha_vencimiento) "
                + "VALUES (?,?,?,?,?,?,?)";
        try (Connection c = ConexionDB.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            llenar(ps, p);
            ps.executeUpdate();
        }
    }

    /** READ: listar todos, o filtrar por texto (nombre, categoría, marca o especie). */
    public List<Producto> listar(String filtro) throws SQLException {
        boolean hayFiltro = filtro != null && !filtro.isBlank();
        String sql = "SELECT * FROM producto"
                + (hayFiltro ? " WHERE nombre LIKE ? OR categoria LIKE ? OR marca LIKE ? OR especie LIKE ?" : "")
                + " ORDER BY id_producto";
        List<Producto> lista = new ArrayList<>();
        try (Connection c = ConexionDB.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (hayFiltro) {
                String like = "%" + filtro.trim() + "%";
                for (int i = 1; i <= 4; i++) ps.setString(i, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date f = rs.getDate("fecha_vencimiento");
                    lista.add(new Producto(
                            rs.getInt("id_producto"), rs.getString("nombre"), rs.getString("categoria"),
                            rs.getString("marca"), rs.getString("especie"), rs.getDouble("precio"),
                            rs.getInt("stock"), f == null ? null : f.toLocalDate()));
                }
            }
        }
        return lista;
    }

    /** UPDATE: modificar un producto existente. */
    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre=?, categoria=?, marca=?, especie=?, precio=?, stock=?, "
                + "fecha_vencimiento=? WHERE id_producto=?";
        try (Connection c = ConexionDB.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            llenar(ps, p);
            ps.setInt(8, p.getIdProducto());
            ps.executeUpdate();
        }
    }

    /** DELETE: eliminar por id. */
    public void eliminar(int id) throws SQLException {
        try (Connection c = ConexionDB.conectar();
             PreparedStatement ps = c.prepareStatement("DELETE FROM producto WHERE id_producto=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void llenar(PreparedStatement ps, Producto p) throws SQLException {
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getCategoria());
        ps.setString(3, p.getMarca());
        ps.setString(4, p.getEspecie());
        ps.setDouble(5, p.getPrecio());
        ps.setInt(6, p.getStock());
        LocalDate f = p.getFechaVencimiento();
        if (f == null) ps.setNull(7, Types.DATE); else ps.setDate(7, Date.valueOf(f));
    }
}
