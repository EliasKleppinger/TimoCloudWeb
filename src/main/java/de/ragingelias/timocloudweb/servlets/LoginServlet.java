/**
 * 
 */
package de.ragingelias.timocloudweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.ragingelias.timocloudweb.TimoCloudWeb;
import net.md_5.bungee.config.Configuration;

/**
 * @author Elias Kleppinger
 *
 */
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = session.getAttribute("authed");
		if((o instanceof Boolean) && ((boolean) o)) {
			resp.sendRedirect("/start");
		} else {
			resp.setContentType("text/html");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter()
					.println(TimoCloudWeb.style + "<form method='post'>\r\n" + 
							"<div class='login'>\r\n" + 
							"<h3>Anmeldung</h3>\r\n" + 
							"<input placeholder='Benutzername' name='name' required>\r\n" + 
							"<input type='password' placeholder='Passwort' name='password' required>\r\n" + 
							"<br><br>\r\n" + 
							"<button type='submit'>Anmelden</button>\r\n" + 
							"</div>\r\n" + 
							"</form>");
		}
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			Object o = session.getAttribute("authed");
			if((o instanceof Boolean) && ((boolean) o)) {
				resp.sendRedirect("/start");
			} else {
				resp.setContentType("text/html");
				Configuration conf = TimoCloudWeb.confProv.load(TimoCloudWeb.configFile);
				
				PrintWriter out = resp.getWriter();
				String name = req.getParameter("name");
				String password = req.getParameter("password");
				System.out.println(name);
				System.out.println(password);
				if(conf.getString("users." + name) != null) {
					if(password.equals(conf.getString("users." + name))) {
						session.setAttribute("authed", true);
						session.setAttribute("name", name);
						resp.sendRedirect("/start");
					} else {
						resp.getWriter()
						.println(TimoCloudWeb.style + "<div class='box'>Der Benutzername oder das Passwort sind falsch!</div><form method='post'>\r\n" + 
								"<div class='login'>\r\n" + 
								"<h3>Anmeldung</h3>\r\n" + 
								"<input placeholder='Benutzername' name='name' required>\r\n" + 
								"<input type='password' placeholder='Passwort' name='password' required>\r\n" + 
								"<br><br>\r\n" + 
								"<button type='submit'>Anmelden</button>\r\n" + 
								"</div>\r\n" + 
								"</form>");
					}
				} else {
					resp.getWriter()
					.println(TimoCloudWeb.style + "<div class='box'>Der Benutzername oder das Passwort sind falsch!</div><form method='post'>\r\n" + 
							"<div class='login'>\r\n" + 
							"<h3>Anmeldung</h3>\r\n" + 
							"<input placeholder='Benutzername' name='name' required>\r\n" + 
							"<input type='password' placeholder='Passwort' name='password' required>\r\n" + 
							"<br><br>\r\n" + 
							"<button type='submit'>Anmelden</button>\r\n" + 
							"</div>\r\n" + 
							"</form>");
				}
				out.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
