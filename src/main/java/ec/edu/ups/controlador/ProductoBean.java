package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.ejb.AbstractFacade;
import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Bodega;
import ec.edu.ups.entidad.Producto;


@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class ProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    	
	@EJB
	private ProductoFacade ejbProductoFacade;
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	private String nombre;
	private double costo;
	private int stock;
	private List<Producto> list;
	private Producto producto;
	private Bodega bodega;
	
	public ProductoBean() {
		// TODO Auto-generated constructor stub
		// listar(bodegaBean.getBodega().getId());
	}
	@PostConstruct
	public void init() {
		System.out.println("Ingreso");
		producto = new Producto();
		listar();
		System.out.println("Listado de productos. "+list);
	}
	public String addProductsToBodeg(Bodega bodega) {
		System.out.println(list.toString());
		System.out.println("Datos de bodega"+bodega.toString());
		//ejbBodegaFacade.edit(bodega);
		return null;
	}
	public void listAll() {
		list=ejbProductoFacade.findAll();
	}
	
	public List<Producto> listar(){
		List<Producto> productos = new ArrayList<Producto>();
		try {
			for (Producto producto : ejbProductoFacade.findAll()) {
				productos.add(producto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		list = productos;
		return productos;
	}
	
	public String save(Bodega b) {
		
		System.out.println("Bodega: "+b.toString());
		System.out.println("Guardando ");
		
		ejbProductoFacade.create(new Producto(this.nombre,this.costo,this.stock));
		System.out.println("Guardado");
		try {
			list = listar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "Datos guardados";
	}
	public void search(Producto p) {
		try {
			producto = new Producto();
			for (Producto prod : ejbProductoFacade.findAll()) {
				if (prod.getId()==p.getId()) {
					producto = prod;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String delete(Producto p) {
		ejbProductoFacade.remove(p);
		list = ejbProductoFacade.findAll();
		return null;
	}
	
	public String edit(Producto p) {
		p.setEditable(true);
		return null;
	}
	public String update(Producto p) {
		ejbProductoFacade.edit(p);
		p.setEditable(false);
		return null;
	}
	
	public ProductoFacade getEjbProductoFacade() {
		return ejbProductoFacade;
	}
	public void setEjbProductoFacade(ProductoFacade ejbProductoFacade) {
		this.ejbProductoFacade = ejbProductoFacade;
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
	public List<Producto> getList() {
		return list;
	}
	public void setList(List<Producto> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
}