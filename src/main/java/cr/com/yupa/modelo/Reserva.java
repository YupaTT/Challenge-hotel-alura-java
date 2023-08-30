package cr.com.yupa.modelo;

import java.time.LocalDate;

public class Reserva {
    private Integer id;
    private Integer huespedId;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private Double valorTotal;
    private String metodoPago;

    private static final int TARIFA_POR_NOCHE = 100;

    public Reserva(Integer huespedId, LocalDate fechaIngreso, LocalDate fechaEgreso, String metodoPago) {
        this.huespedId = huespedId;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.valorTotal = (double) (fechaEgreso.getDayOfYear() - fechaIngreso.getDayOfYear()) * TARIFA_POR_NOCHE;
        this.metodoPago = metodoPago;
    }

    public Reserva(Integer id, Integer huespedId, LocalDate fechaIngreso, LocalDate fechaEgreso, Double valorTotal, String metodoPago) {
        this.id = id;
        this.huespedId = huespedId;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.valorTotal = valorTotal;
        this.metodoPago = metodoPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHuespedId() {
        return huespedId;
    }

    public void setHuespedId(Integer huespedId) {
        this.huespedId = huespedId;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return String.format("Reserva: id: %d, huespedId: %d, fechaIngreso: %s, fechaEgreso: %s, valorTotal: %.2f, metodoPago: %s",
                id, huespedId, fechaIngreso, fechaEgreso, valorTotal, metodoPago);
    }
}
