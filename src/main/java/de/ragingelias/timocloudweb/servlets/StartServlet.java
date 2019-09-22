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
import cloud.timo.TimoCloud.api.objects.ProxyGroupObject;
import cloud.timo.TimoCloud.api.objects.ProxyObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import cloud.timo.TimoCloud.core.TimoCloudCore;
import cloud.timo.TimoCloud.core.objects.Proxy;
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
			String builder = "";
			for(ProxyGroupObject group : TimoCloudAPI.getUniversalAPI().getProxyGroups()) {
				for(ProxyObject proxy : group.getProxies()) {
					builder += "<div class='group'><p>Name: " + proxy.getName() + "</p>" + "<p>Art: Proxy"
							+ "</p>" + "<p>Gruppe: " + proxy.getGroup().getName() + "</p>" + "<p>Spieler: "
							+ proxy.getOnlinePlayerCount() + "</p><p>BASE: " + proxy.getBase()
							+ "</p><p>Port: " + proxy.getPort() + "</p>" + "</div>";
				}
			}
			
			for(ServerGroupObject group : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
				for(ServerObject server : group.getServers()) {
					builder += "<div class='group'><p>Name: " + server.getName() + "</p>" + "<p>Art: Server"
							+ "</p>" + "<p>Gruppe: " + server.getGroup().getName() + "</p>" + "<p>Spieler: "
							+ server.getOnlinePlayerCount() + "</p><p>BASE: " + server.getBase()
							+ "</p><p>Port: " + server.getPort() + "</p>" + "</div>";
				}
			}
				
			
			resp.getWriter()
			.println(TimoCloudWeb.navbar + "<div class='box'><h3>Willkommen, " + session.getAttribute("name") + "!</h3><h1>Server</h1> " + builder + "</div>");
		} else {
			resp.sendRedirect("/login");
		}
	}

}
