package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.ejb.AbstractFacade;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.entidad.Usuario;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    	
	@EJB
	private UsuarioFacade ejbUsuarioFacade;
	
	private String nombre;
	private String tipo;
	private String password;
	private List<Usuario> list;
	
	private Usuario usuario;
	
	public UsuarioBean() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		System.out.println("Ingreso");
		usuario = new Usuario();
		list = ejbUsuarioFacade.findAll();
	}
	
	public String save() {
		System.out.println("Guardando");
		
		ejbUsuarioFacade.create(new Usuario(this.nombre,this.tipo,this.password));
		System.out.println("Guardado");
		list = ejbUsuarioFacade.findAll();
		return "Datos guardados";
	}
	public Usuario search() {
		
		String nombre = this.nombre;
		String pass = this.password;
		
		Usuario usutemp = new Usuario();
		
		List<Usuario> lista = new ArrayList<Usuario>();
		lista = ejbUsuarioFacade.findAll();
		for (Usuario usu : lista) {
			if (usu.getNombre().equals(nombre) && usu.getPassword().equals(pass)){
				System.out.println("Usuario encontrado: "+usu.toString());
				return usutemp = usu;
			}
		}
		return usutemp;
	}
	public Usuario buscar(String nombre, String password) {
		
		Usuario usutemp = new Usuario();
		
		List<Usuario> lista = new ArrayList<Usuario>();
		System.out.println("Empezando a buscar");
		lista = ejbUsuarioFacade.findAll();
		for (Usuario usu : lista) {
			if (usu.getNombre().equals(nombre) && usu.getPassword().equals(password)){
				System.out.println("Usuario encontrado: "+usu.toString());
				this.usuario = usu;
				return usutemp = usu;
			}
		}
		return usutemp;
	}
	
	public UsuarioFacade getEjbUsuarioFacade() {
		return ejbUsuarioFacade;
	}
	public void setEjbUsuarioFacade(UsuarioFacade ejbUsuarioFacade) {
		this.ejbUsuarioFacade = ejbUsuarioFacade;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Usuario> getList() {
		return list;
	}
	public void setList(List<Usuario> list) {
		this.list = list;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
