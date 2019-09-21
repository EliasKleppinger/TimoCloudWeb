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
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import cloud.timo.TimoCloud.core.TimoCloudCore;
import cloud.timo.TimoCloud.core.objects.Base;
import cloud.timo.TimoCloud.core.objects.Server;
import cloud.timo.TimoCloud.core.objects.ServerGroup;
import de.ragingelias.timocloudweb.TimoCloudWeb;

/**
 * @author Elias Kleppinger
 *
 */
public class PlayersServlet extends HttpServlet {

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
			for(ServerGroup group : TimoCloudCore.getInstance().getInstanceManager().getServerGroups()) {
				for(Server server : group.getServers()) {
					for(PlayerObject player : server.getOnlinePlayers()) {
						builder += "<div class='group'><p>Name: " + player.getName() + "</p>" + "<p>Server: " + player.getServer().getName()
						+ "</p>" + "<p>Online: " +player.isOnline() + "%</p>" + "<p>Host: "
						+ player.getIpAddress().getHostAddress() + "</p>" 
						+ "</div>";
					}
				}
			}
			resp.setContentType("text/html");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println(TimoCloudWeb.navbar + "<div class='box'><h2>Spieler</h2>" + builder
					+ "<br><br></div>");
		} else {
			resp.sendRedirect("/login");
		
		}
	}
}
