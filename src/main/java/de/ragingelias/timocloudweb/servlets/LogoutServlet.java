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

import cloud.timo.TimoCloud.core.TimoCloudCore;
import cloud.timo.TimoCloud.core.objects.Base;
import de.ragingelias.timocloudweb.TimoCloudWeb;

/**
 * @author Elias Kleppinger
 *
 */
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = session.getAttribute("authed");
		if((o instanceof Boolean) && ((boolean) o)) {
			session.invalidate();
			resp.sendRedirect("/start");
		} else {
			resp.sendRedirect("/login");
		}
	}

}
