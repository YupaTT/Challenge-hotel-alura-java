package cr.com.charly.views;

import cr.com.charly.controller.HuespedController;
import cr.com.charly.controller.ReservaController;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private final HuespedController huespedController = new HuespedController();
	private final ReservaController reservaController = new ReservaController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		tbReservas.getTableHeader().setReorderingAllowed(false);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);

		// Cargar las reservas en la tabla
		cargarTablaReservas();

		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		tbHuespedes.getTableHeader().setReorderingAllowed(false);
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
//		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		// Cargar los huespedes en la tabla
		cargarTablaHuespedes();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// mostrar en que ventana estamos, si en huespedes o reservas
				if (panel.getTitleAt(panel.getSelectedIndex()) == "Huéspedes") {
					System.out.println("Estamos en la pestaña de huespedes");	// debug

					if (!txtBuscar.getText().isEmpty()) {
						System.out.println("Se ingresó el nombre " + txtBuscar.getText());	// debug
						// cargar los huespedes filtrados
						cargarTablaHuespedesFiltrados();
					} else {
						System.out.println("No se ingresó un nombre");	// debug
						// cargar la tabla de huespedes
						cargarTablaHuespedes();
					}
				} else {
					System.out.println("Estamos en la pestaña de reservas");	// debug
					if (!txtBuscar.getText().isEmpty()) {
						System.out.println("Se ingresó el id " + txtBuscar.getText());	// debug
						// cargar las reservas filtradas
						cargarTablaReservasFiltrados();
					} else {
						System.out.println("No se ingresó un id");	// debug
						// cargar la tabla de reservas
						cargarTablaReservas();
					}
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// mostrar en que ventana estamos, si en huespedes o reservas
				if (panel.getTitleAt(panel.getSelectedIndex()) == "Huéspedes") {
					System.out.println("Estamos en la pestaña de huespedes");	// debug

					// Obtener el id del huesped seleccionado
					int id = (int) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0);
					System.out.println("Se seleccionó el huesped con id " + id);	// debug

					// Obtener los datos
					String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
					LocalDate fechaNacimiento = (LocalDate) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3);
					String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
					String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);

					huespedController.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id);
				} else {
					System.out.println("Estamos en la pestaña de reservas");	// debug
					// TO-DO abrir la ventana de edición de reservas
					int id = (int) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
					System.out.println("Se seleccionó la reserva con id " + id);	// debug

					// Obtener los datos (fechaIngreso, fechaEgreso, formaDePago)
					LocalDate fechaIngreso = (LocalDate) modelo.getValueAt(tbReservas.getSelectedRow(), 1);
					LocalDate fechaEgreso = (LocalDate) modelo.getValueAt(tbReservas.getSelectedRow(), 2);
					String formaDePago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);

					System.out.println("Se seleccionó la reserva con fecha de ingreso " + fechaIngreso);	// debug

					reservaController.modificar(fechaIngreso, fechaEgreso, formaDePago, id);
				}
			}
		});
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// mostrar en que ventana estamos, si en huespedes o reservas
				if (panel.getTitleAt(panel.getSelectedIndex()) == "Huéspedes") {
					System.out.println("Estamos en la pestaña de huespedes");	// debug

					// Obtener el id del huesped seleccionado
					int id = (int) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0);
					System.out.println("Se seleccionó el huesped con id " + id);	// debug

					// Eliminar el huesped
					huespedController.eliminar(id);

					// Cargar la tabla de huespedes
					cargarTablaHuespedes();
				} else {
					System.out.println("Estamos en la pestaña de reservas");	// debug
					// TO-DO eliminar la reserva seleccionada
				}
			}
		});
	}

	private void cargarTablaHuespedes() {
		var huespedes = this.huespedController.listar();

		// Limpiar la tabla
		modeloHuesped.setRowCount(0);
		// Agregar los huespedes a la tabla
		huespedes.forEach(huesped -> {
			modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
					huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono()});
		});
	}

	private void cargarTablaHuespedesFiltrados() {
		// Tomar el texto del campo de texto
		String apellido = txtBuscar.getText();
		// buscar los huespedes por apellido
		var huespedes = this.huespedController.listarPorApellido(apellido);

		// Limpiar la tabla
		modeloHuesped.setRowCount(0);
		// Agregar los huespedes a la tabla
		huespedes.forEach(huesped -> {
			modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
					huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono()});
		});

	}

	// Reserva
	private void cargarTablaReservas() {
		var reservas = this.reservaController.listar();

		// limpiar la tabla
		modelo.setRowCount(0);

		//agregar las reservas a la tabla
		reservas.forEach(reserva -> {
			modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaIngreso(), reserva.getFechaEgreso(),
					reserva.getValorTotal(), reserva.getMetodoPago()});
		});
	}

	private void cargarTablaReservasFiltrados() {
		int id = Integer.parseInt(txtBuscar.getText());
		var reservas = this.reservaController.listarPorHuespedId(id);

		// Limpiar la tabla
		modelo.setRowCount(0);

		// Agregar las reservas a la tabla
		reservas.forEach(reserva -> {
			modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaIngreso(), reserva.getFechaEgreso(),
					reserva.getValorTotal(), reserva.getMetodoPago()});
		});
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
