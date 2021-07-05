package ec.edu.ups.entidad;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Detalles")
public class Detalle implements Serializable {
	@Id
	@SequenceGenerator(name = "det_id_seq", sequenceName = "det_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "det_id_seq")
	@Column(name="det_id",updatable = false, nullable = false, unique = true)
	private int id;

	@Column(name = "det_cantidad")
	private int cantidad;
	
	@Column(name = "det_costo")
	private double costo;
	
	@Column(name = "det_ctotal")
	private double cTotal;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "det_producto")
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "det_factura")
	private Factura factura;
	
	public Detalle() {
		// TODO Auto-generated constructor stub
	}
	public Detalle(int cantidad, double costo, double total) {
		this.cantidad = cantidad;
		this.costo = costo;
		this.cTotal = total;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getcTotal() {
		return cTotal;
	}

	public void setcTotal(double cTotal) {
		this.cTotal = cTotal;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	@Override
	public String toString() {
		return "Detalle [id=" + id + ", cantidad=" + cantidad + ", costo=" + costo + ", cTotal=" + cTotal
				+ ", producto=" + producto + ", factura=" + factura + "]";
	}
	
}
