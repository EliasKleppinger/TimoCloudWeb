/**
 * 
 */
package de.ragingelias.timocloudweb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.ragingelias.timocloudweb.TimoCloudWeb;

/**
 * @author Elias Kleppinger
 *
 */
public class StartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = session.getAttribute("authed");
		if((o instanceof Boolean) && ((boolean) o)) {
			resp.getWriter()
			.println(TimoCloudWeb.navbar + "<div class='box'><h3>Willkommen, " + session.getAttribute("name") + "! Bitte treffe oben eine Auswahl.</h3></div>");
		} else {
			resp.sendRedirect("/login");
		}
	}

}
