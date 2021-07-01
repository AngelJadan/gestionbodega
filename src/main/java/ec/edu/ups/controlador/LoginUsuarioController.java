package ec.edu.ups.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.entidad.Usuario;

/**
 * Servlet implementation class LoginUsuarioController
 */
@WebServlet("/LoginUsuarioController")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
public class LoginUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	@EJB
	private UsuarioFacade ejbUsuarioFacade;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUsuarioController() {
		super();
		usuario = new Usuario();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		System.out.println("Consulta. " + request);
		if (request.getParameter("logout") != null) {
			session.invalidate();
			response.sendRedirect("/gestionbodega/HTMLs/login.html");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = null;

		int num = 0;
		response.setContentType("text/html");

		try {

			String user = String.valueOf(request.getParameter("usuario"));
			String pass = String.valueOf(request.getParameter("pass"));

			System.out.println("Iniciando...");
			HttpSession session = request.getSession(true);
			List<Usuario> list = new ArrayList<Usuario>();

			System.out.println("Usuario: " + user + pass);
			list = ejbUsuarioFacade.findAll();
			if(list.size()>0) {
				for (Usuario usu : list) {
					if (usu.getNombre().equals(user) && usu.getPassword().equals(pass)) {
						System.out.println("Usuario: " + usu.toString());
						usuario.setId(usu.getId());
						usuario.setNombre(usu.getNombre());
						usuario.setTipo(usu.getTipo());
						usuario.setPassword(usu.getPassword());
						
						
						if (session.isNew()) {
							session.setAttribute("accesos", 1);
						} else {
							session.setAttribute("accesos", num + 1);
						}
						session.setAttribute("usuario", usuario);
						request.setAttribute("usuario", usuario);
						
						if (usu.getTipo().equals("administrador")) {
							url = "/JSFs/inicio_admin.xhtml";
							System.out.println(url);
							break;
						}
						if (usu.getTipo().equals("trabajador")) {
							url = "/JSFs/inicio_trabajador.xhtml";
							System.out.println(url);
							break;
						} else {
							url = "/JSFs/error.xhtml";
							System.out.println(url);
						}
					}else {
						url ="/JSFs/error.xhtml";
						System.out.println(url);
					}
				}
			}else {
				url= "/JSFs/error.xhtml";
				System.out.println(url);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			url = "/JSFs/error.xhtml";
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
