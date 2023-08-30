package cr.com.yupa.dao;

import cr.com.yupa.modelo.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservaDao {
    private Connection connection;

    public ReservaDao(Connection connection) {
        this.connection = connection;
    }

    public int guardar(Reserva reserva) {
        System.out.println("Guardando la reserva");
        String query = "INSERT INTO reservas (huesped_id, fecha_ingreso, fecha_egreso, valor_total, metodo_pago) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, reserva.getHuespedId());
            statement.setDate(2, java.sql.Date.valueOf(reserva.getFechaIngreso()));
            statement.setDate(3, java.sql.Date.valueOf(reserva.getFechaEgreso()));
            statement.setDouble(4, reserva.getValorTotal());
            statement.setString(5, reserva.getMetodoPago());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        reserva.setId(resultSet.getInt(1));
                        System.out.println(String.format("Fue insertada la reserva: %s", reserva));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reserva.getId();
    }

    public List<Reserva> listar() {
        List<Reserva> listaReservas = new ArrayList<>();
        String query = "SELECT * FROM reservas";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int huespedId = resultSet.getInt("huesped_id");
                    Date fechaIngreso = resultSet.getDate("fecha_ingreso");
                    Date fechaEgreso = resultSet.getDate("fecha_egreso");
                    Double valorTotal = resultSet.getDouble("valor_total");
                    String metodoPago = resultSet.getString("metodo_pago");

                    Reserva reserva = new Reserva(id, huespedId, fechaIngreso.toLocalDate(), fechaEgreso.toLocalDate(), valorTotal, metodoPago);
                    listaReservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaReservas;
    }

    public int eliminar(int id) {
        String query = "DELETE FROM reservas WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(String.format("Se eliminaron %d filas.", rowsAffected));
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> listarPorHuespedId(int huespedId) {
        List<Reserva> listaReservas = new ArrayList<>();
        String query = "SELECT * FROM reservas WHERE huesped_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, huespedId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date fechaIngreso = resultSet.getDate("fecha_ingreso");
                    Date fechaEgreso = resultSet.getDate("fecha_egreso");
                    Double valorTotal = resultSet.getDouble("valor_total");
                    String metodoPago = resultSet.getString("metodo_pago");

                    Reserva reserva = new Reserva(id, huespedId, fechaIngreso.toLocalDate(), fechaEgreso.toLocalDate(), valorTotal, metodoPago);
                    listaReservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaReservas;
    }

    public int modificar(LocalDate fechaIngreso, LocalDate fechaEgreso, String formaDePago, int id) {
        String query = "UPDATE reservas SET fecha_ingreso = ?, fecha_egreso = ?, valor_total = ?,metodo_pago = ? WHERE id = ?";
        int tarifaPorNoche = 100;
        int dias = (int) ChronoUnit.DAYS.between(fechaIngreso, fechaEgreso);
        double valorTotal = dias * tarifaPorNoche;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(fechaIngreso));
            statement.setDate(2, java.sql.Date.valueOf(fechaEgreso));
            statement.setDouble(3, valorTotal);
            statement.setString(4, formaDePago);
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();
            System.out.println(String.format("Se modificaron %d filas.", rowsAffected));
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}