package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.DetalleFacade;
import ec.edu.ups.ejb.FacturaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Bodega;
import ec.edu.ups.entidad.Cliente;
import ec.edu.ups.entidad.Detalle;
import ec.edu.ups.entidad.Factura;
import ec.edu.ups.entidad.Producto;


@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class FacturaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    	
	@EJB
	private FacturaFacade ejbFacturaFacade;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	@EJB
	private DetalleFacade ejbDetalleFacade;
	
	@EJB
	private ClienteFacade ejbClienteFacade;
	
	private Detalle detalle;
	private List<Detalle> detalles;
	
	private List<Factura> list;
	
	private Factura factura;
	private Producto producto;
	private Date fecha;
	private double total;
	private List<Producto> productos;
	private double sumaTotal;
	
	private int newCantidad;
	
	private Cliente cliente;
	
	public FacturaBean() {
		// TODO Auto-generated constructor stub
		// listar(bodegaBean.getBodega().getId());
	}
	@PostConstruct
	public void init() {
		System.out.println("Ingreso");
		factura = new Factura();
		producto = new Producto();
		detalle = new Detalle();
		detalles = new ArrayList<>();
		productos = new ArrayList<Producto>();
		sumaTotal = 0 ;
		cliente = new Cliente();
		try {
			fecha = new Date();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String addDetalle(Producto p) {
		
		double total = p.getCosto()*this.newCantidad;
		this.sumaTotal = this.sumaTotal + total;
		
		this.detalle = new Detalle(this.newCantidad, p.getCosto(), total);
		this.detalles.add(this.detalle);
		return null;
	}
	
	public String save(Factura f) {
		
		System.out.println("Factura: "+f.toString());
		System.out.println("Guardando ");
		
		ejbFacturaFacade.create(new Factura(this.fecha,this.total,this.detalles));
		System.out.println("Guardado");
		
		return "Datos guardados";
	}
	public String restarStock(Producto p, int cantidad) {
		producto = new Producto();
		int newStock = 0;
		for (Producto pro : ejbProductoFacade.findAll()) {
			if (pro.getId()==p.getId()) {
				producto = pro;
				newStock = pro.getStock() - cantidad;
				producto.setStock(newStock);
				ejbProductoFacade.edit(producto);
				break;
			}
		}
		return "";
	}
	
	public String searchProd(Producto p) {
		producto = new Producto();
		for (Producto pro : ejbProductoFacade.findAll()) {
			if (pro.getId()==p.getId()) {
				producto=pro;
				break;
			}
			
		}
		return null;
	}
	
	public String delete(Factura p) {
		ejbFacturaFacade.remove(p);
		list = ejbFacturaFacade.findAll();
		return null;
	}
	
	public String edit(Producto p) {
		p.setEditable(true);
		return null;
	}
	public String update(Factura p) {
		ejbFacturaFacade.edit(p);
		p.setEditable(false);
		return null;
	}
	
	public FacturaFacade getEjbProductoFacade() {
		return ejbFacturaFacade;
	}
	public void setEjbFacturaFacade(FacturaFacade ejbFacturaFacade) {
		this.ejbFacturaFacade = ejbFacturaFacade;
	}
	public List<Factura> getList() {
		return list;
	}
	public void setList(List<Factura> list) {
		this.list = list;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public FacturaFacade getEjbFacturaFacade() {
		return ejbFacturaFacade;
	}
	public void setEjbProductoFacade(ProductoFacade ejbProductoFacade) {
		this.ejbProductoFacade = ejbProductoFacade;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getNewCantidad() {
		return newCantidad;
	}
	public void setNewCantidad(int newCantidad) {
		this.newCantidad = newCantidad;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public DetalleFacade getEjbDetalleFacade() {
		return ejbDetalleFacade;
	}
	public void setEjbDetalleFacade(DetalleFacade ejbDetalleFacade) {
		this.ejbDetalleFacade = ejbDetalleFacade;
	}
	public Detalle getDetalle() {
		return detalle;
	}
	public void setDetalle(Detalle detalle) {
		this.detalle = detalle;
	}
	public List<Detalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Detalle> detalles) {
		this.detalles = detalles;
	}
	public double getSumaTotal() {
		return sumaTotal;
	}
	public void setSumaTotal(double sumaTotal) {
		this.sumaTotal = sumaTotal;
	}
	public ClienteFacade getEjbClienteFacade() {
		return ejbClienteFacade;
	}
	public void setEjbClienteFacade(ClienteFacade ejbClienteFacade) {
		this.ejbClienteFacade = ejbClienteFacade;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
