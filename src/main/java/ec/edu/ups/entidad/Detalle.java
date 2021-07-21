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

import org.hibernate.annotations.JoinColumnOrFormula;

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
	@JoinColumn(name = "det_pedido")
	private Pedido pedido;
	
	public Detalle() {
		// TODO Auto-generated constructor stub
	}
	public Detalle(int cantidad, double costo, double total) {
		this.cantidad = cantidad;
		this.costo = costo;
		this.cTotal = total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + cantidad;
		temp = Double.doubleToLongBits(costo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Detalle other = (Detalle) obj;
		if (Double.doubleToLongBits(cTotal) != Double.doubleToLongBits(other.cTotal))
			return false;
		if (cantidad != other.cantidad)
			return false;
		if (Double.doubleToLongBits(costo) != Double.doubleToLongBits(other.costo))
			return false;
		if (id != other.id)
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
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
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	@Override
	public String toString() {
		return "Detalle [id=" + id + ", cantidad=" + cantidad + ", costo=" + costo + ", cTotal=" + cTotal
				+ ", producto=" + producto + ", pedido=" + pedido + "]";
	}
}
