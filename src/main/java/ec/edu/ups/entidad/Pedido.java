package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class Pedido implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ped_id_seq", sequenceName = "ped_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ped_id_seq")
	@Column(name="ped_id",updatable = false, nullable = false, unique = true)
	private int id;
	
	@Column(name = "ped_fecha", nullable = false, length = 10)
	private Date fecha;
	
	@Column(name = "ped_estado", nullable = false, length = 10)
	private String estado;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ped_cliente")
	private Cliente cliente;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ped_detalle")
	private List<Detalle> detalle;
	
	@Column(name = "ped_total", nullable = false)
	private double total;

	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	public Pedido(Date fecha, String estado, Cliente cliente, List<Detalle> detalle, double total) {
		
		this.fecha = fecha;
		this.estado = estado;
		this.cliente = cliente;
		this.detalle = detalle;
		this.total = total; 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((detalle == null) ? 0 : detalle.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (detalle == null) {
			if (other.detalle != null)
				return false;
		} else if (!detalle.equals(other.detalle))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", cliente=" + cliente + ", detalle="
				+ detalle + ", total=" + total + "]";
	}
	
}
