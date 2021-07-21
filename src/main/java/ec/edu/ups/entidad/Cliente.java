package ec.edu.ups.entidad;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "cli_id_seq", sequenceName = "cli_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cli_id_seq")
	@Column(name="cli_id",updatable = false, nullable = false, unique = true)
	private int id;

	@Column(name = "cli_nombre", length = 250)
	private String nombre;
	
	@Column(name = "cli_apellido", length = 250)
	private String apellido;
	
	@Column(name = "cli_cedula", length = 10, unique = true)
	private String cedula;
	
	@Column(name = "cli_direccion", length = 250)
	private String direccion;
	
	@Column(name = "cli_correo", length = 250)
	private String correo;
	
	@Column(name = "cli_estado")
	private String estado;
	
	@Transient
	private boolean editable;
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	public Cliente(String nombre, String apellido, String cedula, String direccion, String correo) {
		this.nombre=nombre;
		this.apellido= apellido;
		this.cedula = cedula;
		this.direccion= direccion;
		this.correo = correo;
		// TODO Auto-generated constructor stub
	}
	public Cliente(String nombre, String apellido, String cedula, String direccion, String correo,String estado) {
		this.nombre=nombre;
		this.apellido= apellido;
		this.cedula = cedula;
		this.direccion= direccion;
		this.correo = correo;
		this.estado = estado;
		// TODO Auto-generated constructor stub
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", cedula=" + cedula
				+ ", direccion=" + direccion + ", correo=" + correo + ", estado=" + estado + ", editable=" + editable
				+ "]";
	}
	
}
