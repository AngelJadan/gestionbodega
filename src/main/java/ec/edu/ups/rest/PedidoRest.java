package ec.edu.ups.rest;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.DetalleFacade;
import ec.edu.ups.ejb.FacturaFacade;
import ec.edu.ups.ejb.PedidoFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Cliente;
import ec.edu.ups.entidad.Detalle;
import ec.edu.ups.entidad.Factura;
import ec.edu.ups.entidad.Pedido;
import ec.edu.ups.entidad.Producto;

@Path("/pedidos")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class PedidoRest {
	@Inject
	private ProductoFacade ejbProducto;
	@Inject
	private PedidoFacade ejbPedido;
	@Inject
	private ClienteFacade ejbCliente;
	@Inject
	private DetalleFacade ejbDetalle;
	@Inject
	private FacturaFacade ejbFactura;

	private Mensaje sms;

	DecimalFormat df = new DecimalFormat("#.##");
	public PedidoRest() {
		// TODO Auto-generated constructor stub
		sms = new Mensaje();
	}

	/**
	 * Metodo para solicita un pedido que queda en estado Generado.
	 * @param pedidojson
	 * @return
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/solitar_pedido")
	public Mensaje solicitd(Pedido pedidojson) {
		//System.out.println("Pedido json " + pedidojson);
		double total = 0.00;

		// Jsonb jsonb = JsonbBuilder.create();
		// Pedido pedido = jsonb.fromJson(pedidojson, Pedido.class);
		// PedidoCabecera pc = jsonb.fromJson(jsonPedido, PedidoCabecera.class);
		// Persona per= personaFacade.buscarCliente(pc.getCedula());
		// System.out.println("Pedido "+pedido.toString());
		//System.out.println("Pedido estado " + pedidojson.getEstado());
		Cliente cli = new Cliente();
		cli = ejbCliente.searchClienteCedula(pedidojson.getCliente().getCedula());
		//System.out.println("Cedula-cliente: " + cli.toString());
		//System.out.println("Size " + pedidojson.getDetalle().size());
		//System.out.println("Detales " + pedidojson.getDetalle());
		pedidojson.setCliente(cli);
		try {

			for (int i = 0; i < pedidojson.getDetalle().size(); i++) {
				//System.out.println("Producto");
				Detalle detalle = pedidojson.getDetalle().get(i);
				// System.out.println(pedidojson.getDetalle().get(i));
				Producto prod = detalle.getProducto();
				//System.out.println("Producto: " + prod.toString());
				prod = ejbProducto.getProductoId(prod.getId());
				//System.out.println("Producto buscado "+prod.toString());
				if (prod.getId()>0) {
					detalle.setProducto(prod);
					//Es el costo del producto unitario.
					detalle.setCosto(prod.getCosto());
					//cTotal es el total del costo por la cantidad pedida.
					detalle.setcTotal(detalle.getCantidad()*prod.getCosto());
					//total, para la suma de todos los productos pedidos
					total = total + (prod.getCosto()*detalle.getCantidad());
					//System.out.println("producto encontrado "+prod.toString());
				}else {
					sms.setCodigo("500");
					sms.setMensaje("Codigo de producto no registrado.");
					break;
				}
			}
			pedidojson.setTotal(total);
			ejbPedido.create(pedidojson);
			sms.setCodigo("200");
			sms.setMensaje("Pedido solicitado. "+pedidojson.getId());
		} catch (Exception e) {
			//System.out.println("Error al consular producto " + e.getLocalizedMessage());
			sms.setCodigo("500");
			sms.setMensaje("Error: No existe un producto ingresado "+e.getMessage());
		}finally {
			return sms;
		}
	}

	/**
	 * Metodo para obtener los pedidos del cliente.
	 * @param cedula
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/listar_pedidos")
	public List<Pedido> listPedidos(@QueryParam("cedula")String cedula) {
		System.out.println("Cedula "+cedula);
		List<Pedido> list = new ArrayList<Pedido>();
		try {
			list = ejbPedido.listPedidoCliente(cedula);
			System.out.println("Pedidos "+list.toString());
		} catch (Exception e) {
			System.out.println("Error al listar pedidos "+e.getLocalizedMessage());
		}finally {
			return list;
		}
	}

	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/search_pedido")
	public Pedido getPedido() {
		Date fecha = new Date();
		List<Detalle> list = new ArrayList<Detalle>();

		Producto pro = new Producto();
		pro.setId(1);
		Detalle det = new Detalle(1, 1.50, 1.50);
		det.setProducto(pro);

		Producto pro1 = new Producto();
		pro1.setId(2);
		Detalle det1 = new Detalle(2, 1, 2);
		det1.setProducto(pro1);

		Producto pro2 = new Producto();
		pro2.setId(5);
		Detalle det2 = new Detalle(4, 0.50, 2);
		det2.setProducto(pro2);
		list.add(det);
		list.add(det1);
		list.add(det2);
		Cliente cli = new Cliente();// "Angel", "Jadan", "0106405236", "Jadan", "ajadanc@est.ups.edu.ec");
		cli.setCedula("0106405236");
		cli.setId(1);
		Pedido ped = new Pedido(fecha, "Generado", cli, list,20);
		return ped;
	}
	
	@GET
	@Path("/aprobarPedido")
	@Consumes("application/json")
	@Produces("application/json")
	public Mensaje aprobarPedido(@QueryParam("id")int id) {
		Mensaje sms = new Mensaje();
		Pedido ped = new Pedido();
		try {
			ped = ejbPedido.getPedido(id);
			ped.setEstado("Finalizado");
			ejbPedido.edit(ped);
			Date fecha = new Date();
			Factura factura = new Factura(fecha, ped.getTotal(), ped);
			factura.setCliente(ped.getCliente());
			ejbFactura.create(factura);
			sms.setCodigo("200");
			sms.setMensaje("El pedido numero "+id+" se genero en la factura numero "+factura.getId());
		} catch (Exception e) {
			sms.setCodigo("400");
			sms.setMensaje("Se ha generado un error");
		}finally {
			return sms;
		}
	}
}
