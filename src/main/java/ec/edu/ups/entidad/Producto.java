package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Productos")
public class Producto implements Serializable {

	@Id
	@SequenceGenerator(name = "pro_id_seq", sequenceName = "pro_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pro_id_seq")
	@Column(name = "pro_id", updatable = false, nullable = false, unique = true)
	private int id;

	@Column(name = "pro_nombre")
	private String nombre;

	@Column(name = "pro_costo")
	private double costo;

	@Column(name = "pro_stock")
	private int stock;
	
	@Column(name = "pro_categoria", length = 20)
	private String categoria;
	
	@Transient
	private boolean editable;
	
	@Transient
	private boolean selected;

	public Producto() {
		// TODO Auto-generated constructor stub
	}
	public Producto(int id, String nombre, double costo) {
		this.id = id;
		this.nombre = nombre;
		this.costo = costo;
	}

	public Producto(String nombre, double costo, int stock) {
		this.nombre = nombre;
		this.costo = costo;
		this.stock = stock;
	}
	
	public Producto(String nombre, double costo, int stock, String categoria) {
		this.nombre = nombre;
		this.costo = costo;
		this.stock = stock;
		this.categoria = categoria;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		long temp;
		temp = Double.doubleToLongBits(costo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + stock;
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
		Producto other = (Producto) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (Double.doubleToLongBits(costo) != Double.doubleToLongBits(other.costo))
			return false;
		if (editable != other.editable)
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (selected != other.selected)
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", costo=" + costo + ", stock=" + stock + ", categoria="
				+ categoria + ", editable=" + editable + ", selected=" + selected + "]";
	}	
}