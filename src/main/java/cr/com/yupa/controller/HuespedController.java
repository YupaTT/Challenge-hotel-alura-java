package cr.com.yupa.controller;

import cr.com.yupa.dao.HuespedDao;
import cr.com.yupa.factory.ConnectionFactory;
import cr.com.yupa.modelo.Huesped;

import java.time.LocalDate;
import java.util.List;

public class HuespedController {
    private final HuespedDao huespedDao;

    public HuespedController() {
        var factory = new ConnectionFactory();
        this.huespedDao = new HuespedDao(factory.recuperarConexion());
    }

    public int guardar(Huesped huesped) {
        if (huespedDao.tieneDuplicados(huesped)) {
            System.out.println("El huésped ya existe.");
            return -1;
        }

        return huespedDao.guardar(huesped);
    }

    public List<Huesped> listar() {
        return huespedDao.listar();
    }

    public int eliminar(Integer id) {
        int rowsAffected = huespedDao.eliminar(id);
        if (rowsAffected > 0) {
            System.out.println(String.format("Se eliminaron %d filas.", rowsAffected));
        }
        return rowsAffected;
    }

    public List<Huesped> listarPorApellido(String apellido) {
        return huespedDao.listarPorApellido(apellido);
    }

    public int modificar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono, Integer id) {
        int rowsAffected = huespedDao.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id);
        if (rowsAffected > 0) {
            System.out.println(String.format("Se modificaron %d filas.", rowsAffected));
        }
        return rowsAffected;
    }
}
