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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.ejb.AbstractFacade;
import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.entidad.Bodega;
import ec.edu.ups.entidad.Usuario;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class BodegaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    	
	@EJB
	private BodegaFacade ejbBodeFacade;
	
	private String nombre;
	private List<Bodega> list;
	private Bodega bodega;
	
	public BodegaBean() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {
		System.out.println("Ingreso");
		bodega = new Bodega();
		list = ejbBodeFacade.findAll();
	}
	
	public String vistaProducto(Bodega b) {
		bodega = b;
		return "/JSFs/CrearProducto";
		//FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "");
	}
	
	public String save() {
		System.out.println("Guardando");
		ejbBodeFacade.create(new Bodega(this.nombre));
		System.out.println("Guardado");
		list = ejbBodeFacade.findAll();
		return "Datos guardados";
	}
	public String update(Bodega b) {
		System.out.println("Guardando");
		System.out.println("Bodega "+b.toString());
		ejbBodeFacade.edit(b);
		System.out.println("Guardado");
		b.setEditable(false);
		return null;
	}
	public String cancelar(Bodega b) {
		b.setEditable(false);
		list = ejbBodeFacade.findAll();
		return null;
	}
	public String edit(Bodega b ) {
		b.setEditable(true);
		return null;
	}
	public String delete(Bodega b) {
		System.out.println("Bodega a eliminar: "+b.toString());
		ejbBodeFacade.remove(b);
		list = ejbBodeFacade.findAll();
		return null;
	}
	public BodegaFacade getEjbBodeFacade() {
		return ejbBodeFacade;
	}
	public void setEjbBodeFacade(BodegaFacade ejbBodeFacade) {
		this.ejbBodeFacade = ejbBodeFacade;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Bodega> getList() {
		return list;
	}
	public void setList(List<Bodega> list) {
		this.list = list;
	}
	public Bodega getBodega() {
		return bodega;
	}
	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}
	
}
