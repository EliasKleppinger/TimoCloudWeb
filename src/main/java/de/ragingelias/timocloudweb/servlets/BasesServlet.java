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

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.core.TimoCloudCore;
import cloud.timo.TimoCloud.core.objects.Base;
import de.ragingelias.timocloudweb.TimoCloudWeb;

/**
 * @author Elias Kleppinger
 *
 */
public class BasesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = session.getAttribute("authed");
		if((o instanceof Boolean) && ((boolean) o)) {
			String builder = "";
			for (Base base : TimoCloudCore.getInstance().getInstanceManager().getBases()) {
				builder += "<div class='group'><p>Name: " + base.getName() + "</p>" + "<p>Freier RAM: " + base.getAvailableRam()
						+ " MB</p>" + "<p>CPU: " + Math.round(base.getCpu()) + "%</p>" + "<p>Host: "
						+ base.getPublicAddress().getHostAddress() + "</p>" 
						+ "</div>";
			}
			resp.setContentType("text/html");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println(TimoCloudWeb.navbar + "<div class='box'><h2>BASE's</h2>" + builder
					+ "<br><br></div>");
		} else {
			resp.sendRedirect("/login");
		}
	}

}
