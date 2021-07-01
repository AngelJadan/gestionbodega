package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.entidad.Bodega;
import ec.edu.ups.entidad.Cliente;



@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ClienteFacade ejbClienteFacade;
    
    private List<Cliente> list;
    private String nombre;
    private String apellido;
    private String cedula;
	private String direccion;
	private String correo;
    private String descripcion;
    private Cliente cliente;

    public ClienteBean() {

    }    
    
    @PostConstruct
    public void init() {
    	System.out.println("Iniciando");
    	cliente = new Cliente();
    	list = ejbClienteFacade.findAll();
    	System.out.println("Iniciado");
    }
    public void save() {
    	cliente = new Cliente(this.nombre,this.apellido, this.cedula, this.direccion,this.correo);
    	System.out.println("Cliente a guardar: "+cliente.toString());
    	ejbClienteFacade.create(cliente);
    	System.out.println("Guardado");
    	cliente = new Cliente();
    	list = ejbClienteFacade.findAll();
    }
    public String update(Cliente c) {
		System.out.println("Guardando");
		System.out.println("Cliente "+c.toString());
		ejbClienteFacade.edit(c);
		System.out.println("Guardado");
		c.setEditable(false);
		return null;
	}
	public String edit(Cliente c ) {
		c.setEditable(true);
		return null;
	}
	public String delete(Cliente c) {
		ejbClienteFacade.remove(c);
		list = ejbClienteFacade.findAll();
		return null;
	}

	public ClienteFacade getEjbClienteFacade() {
		return ejbClienteFacade;
	}

	public void setEjbClienteFacade(ClienteFacade ejbClienteFacade) {
		this.ejbClienteFacade = ejbClienteFacade;
	}

	public List<Cliente> getList() {
		return list;
	}

	public void setList(List<Cliente> list) {
		this.list = list;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}