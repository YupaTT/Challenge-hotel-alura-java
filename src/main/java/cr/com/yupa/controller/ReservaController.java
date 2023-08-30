package cr.com.yupa.controller;

import cr.com.yupa.dao.ReservaDao;
import cr.com.yupa.factory.ConnectionFactory;
import cr.com.yupa.modelo.Reserva;

import java.time.LocalDate;
import java.util.List;

public class ReservaController {
    private final ReservaDao reservaDao;

    public ReservaController() {
        var factory = new ConnectionFactory();
        this.reservaDao = new ReservaDao(factory.recuperarConexion());
    }

    public int guardar(Reserva reserva) {
        return reservaDao.guardar(reserva);
    }

    public List<Reserva> listar() {
        return reservaDao.listar();
    }

    public int eliminar(int id) {
        int rowsAffected = reservaDao.eliminar(id);
        imprimirResultado(rowsAffected, "eliminaron");
        return rowsAffected;
    }

    public List<Reserva> listarPorHuespedId(int huespedId) {
        return reservaDao.listarPorHuespedId(huespedId);
    }

    public int modificar(LocalDate fechaIngreso, LocalDate fechaEgreso, String formaDePago, int id) {
        int rowsAffected = reservaDao.modificar(fechaIngreso, fechaEgreso, formaDePago, id);
        imprimirResultado(rowsAffected, "modificaron");
        return rowsAffected;
    }

    private void imprimirResultado(int rowsAffected, String action) {
        if (rowsAffected > 0) {
            System.out.println(String.format("Se %s %d filas.", action, rowsAffected));
        }
    }
}
