package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Facturas")
public class Factura implements Serializable {

	@Id
	@SequenceGenerator(name = "fac_id_seq", sequenceName = "fac_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fac_id_seq")
	@Column(name = "fac_id", updatable = false, nullable = false, unique = true)
	private int id;

	@Column(name = "fac_fecha")
	private Date fecha;
	
	@Column(name="fac_total")
	private double total;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "factura")
	private List<Detalle> detalle;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Cliente cliente;

	@Transient
	private boolean editable;

	public Factura() {
		// TODO Auto-generated constructor stub
	}

	public Factura(Date fecha,double total, List<Detalle> detalle) {
		this.fecha = fecha;
		this.detalle = detalle;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", total=" + total + ", detalle=" + detalle + ", cliente="
				+ cliente + ", editable=" + editable + "]";
	}
	
}
