package cr.com.yupa.dao;

import cr.com.yupa.modelo.Huesped;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HuespedDao {
    private Connection connection;

    public HuespedDao(Connection connection) {
        this.connection = connection;
    }

    public boolean tieneDuplicados(Huesped huesped) {
        String query = "SELECT COUNT(*) FROM huespedes WHERE nombre = ? AND apellido = ?";
        boolean tieneDuplicados = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int rows = resultSet.getInt(1);
                    tieneDuplicados = rows > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tieneDuplicados;
    }

    public int guardar(Huesped huesped) {
        if (tieneDuplicados(huesped)) {
            System.out.println("El huésped ya existe.");
            return -1;
        }

        System.out.println("Guardando el huesped " + huesped.toString());

        String query = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(huesped.getFechaNacimiento()));
            statement.setString(4, huesped.getNacionalidad());
            statement.setString(5, huesped.getTelefono());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        huesped.setId(resultSet.getInt(1));
                        System.out.println(String.format("Fue insertado el huesped: %s", huesped));
                    }
                }
            } else {
                System.out.println("No se pudo insertar el huésped.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return huesped.getId();
    }

    public List<Huesped> listar() {
        List<Huesped> listaHuespedes = new ArrayList<>();
        String query = "SELECT * FROM huespedes";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                    String nacionalidad = resultSet.getString("nacionalidad");
                    String telefono = resultSet.getString("telefono");

                    Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento.toLocalDate(), nacionalidad, telefono);
                    listaHuespedes.add(huesped);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaHuespedes;
    }

    // Métodos restantes...

// ... Código anterior ...

    public int eliminar(Integer id) {
        String query = "DELETE FROM huespedes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(String.format("Se eliminaron %d filas.", rowsAffected));
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Huesped> listarPorApellido(String apellido) {
        List<Huesped> listaHuespedes = new ArrayList<>();
        String query = "SELECT * FROM huespedes WHERE apellido = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, apellido);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido2 = resultSet.getString("apellido");
                    Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                    String nacionalidad = resultSet.getString("nacionalidad");
                    String telefono = resultSet.getString("telefono");

                    Huesped huesped = new Huesped(id, nombre, apellido2, fechaNacimiento.toLocalDate(), nacionalidad, telefono);
                    listaHuespedes.add(huesped);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaHuespedes;
    }

    public int modificar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono, Integer id) {
        String query = "UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setDate(3, java.sql.Date.valueOf(fechaNacimiento));
            statement.setString(4, nacionalidad);
            statement.setString(5, telefono);
            statement.setInt(6, id);

            int rowsAffected = statement.executeUpdate();
            System.out.println(String.format("Se modificaron %d filas.", rowsAffected));
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

