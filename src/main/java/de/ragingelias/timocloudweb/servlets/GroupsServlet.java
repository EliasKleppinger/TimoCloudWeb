/**
 * 
 */
package de.ragingelias.timocloudweb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.TimoCloudCoreAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.core.TimoCloudCore;
import cloud.timo.TimoCloud.core.objects.ServerGroup;
import de.ragingelias.timocloudweb.TimoCloudWeb;

/**
 * @author Elias Kleppinger
 *
 */
public class GroupsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = session.getAttribute("authed");
		if ((o instanceof Boolean) && ((boolean) o)) {
			if (req.getParameter("edit") != null) {
				String editGroup = req.getParameter("edit");
				if (req.getParameter("ram") != null && req.getParameter("online") != null) {
					int ram = Integer.valueOf(req.getParameter("ram"));
					int online = Integer.valueOf(req.getParameter("online"));
					ServerGroupObject server = TimoCloudAPI.getUniversalAPI().getServerGroup(editGroup);
					ServerGroup group = TimoCloudCore.getInstance().getInstanceManager()
							.getServerGroupByName(server.getName());
					group.setRam(ram);
					group.setOnlineAmount(online);
					TimoCloudCore.getInstance().getInstanceManager().saveServerGroups();
					resp.sendRedirect("/groups?status=edit_success");

				} else {

					if (TimoCloudAPI.getUniversalAPI().getServerGroup(editGroup) != null) {
						ServerGroupObject server = TimoCloudAPI.getUniversalAPI().getServerGroup(editGroup);
						String form = "<form method='get'><input name='edit' type='hidden' value='" + server.getName()
								+ "'>RAM<input placeholder='" + "RAM" + "' name='ram' type='number' value='"
								+ server.getRam() + "'><br>"
								+ "OnlineAmount<input placeholder='OnlineAmount' name='online' type='number' value='"
								+ server.getOnlineAmount() + "'>" + "<br><button type='submit'>" + "Speichern"
								+ "</button></form>";
						resp.setContentType("text/html");
						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().println(TimoCloudWeb.navbar + "<div class='box'><h2>Gruppen</h2><h3>"
								+ server.getName() + "</h3>" + form + "</div>");
					} else {
						resp.sendRedirect("/groups");
					}
				}

			} else if (req.getParameter("status") != null) {
				String statusMessage = req.getParameter("status");
				if (statusMessage.contains("edit_success")) {
					String builder = "";
					for (ServerGroupObject server : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
						builder += "<div class='group'><p>Name: " + server.getName() + "</p>" + "<p>RAM: " + server.getRam()
						+ " MB</p>" + "<p>Online: " + server.getOnlineAmount() + "</p>" + "<p>Static: "
						+ server.isStatic() + "</p><a href='?edit=" + server.getName()
						+ "'><button>Editieren</button></a>" + "<a href='?delete=" + server.getName()
						+ "'><button>L�schen</button></a>" +"</div>";
					}

					resp.setContentType("text/html");
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().println(TimoCloudWeb.navbar
							+ "<div class='box'><h3 style='color: green;'>Die Gruppe wurde erfolgreich editiert!</h3><h2>Gruppen</h2>"
							+ builder + "<br><br><a href='?add=true'><button>Gruppe erstellen</button></a></div>");
				} else if (statusMessage.contains("add_success")) {
					String builder = "";
					for (ServerGroupObject server : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
						builder += "<div class='group'><p>Name: " + server.getName() + "</p>" + "<p>RAM: " + server.getRam()
						+ " MB</p>" + "<p>Online: " + server.getOnlineAmount() + "</p>" + "<p>Static: "
						+ server.isStatic() + "</p><a href='?edit=" + server.getName()
						+ "'><button>Editieren</button></a>" + "<a href='?delete=" + server.getName()
						+ "'><button>L�schen</button></a>" +"</div>";
					}

					resp.setContentType("text/html");
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().println(TimoCloudWeb.navbar
							+ "<div class='box'><h3 style='color: green;'>Die Gruppe wurde erstellt!</h3><h2>Gruppen</h2>"
							+ builder + "<br><br><a href='?add=true'><button>Gruppe erstellen</button></a></div>");
				} else if (statusMessage.contains("remove_success")) {
					String builder = "";
					for (ServerGroupObject server : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
						builder += "<div class='group'><p>Name: " + server.getName() + "</p>" + "<p>RAM: " + server.getRam()
						+ " MB</p>" + "<p>Online: " + server.getOnlineAmount() + "</p>" + "<p>Static: "
						+ server.isStatic() + "</p><a href='?edit=" + server.getName()
						+ "'><button>Editieren</button></a>" + "<a href='?delete=" + server.getName()
						+ "'><button>L�schen</button></a>" +"</div>";
					}

					resp.setContentType("text/html");
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().println(TimoCloudWeb.navbar
							+ "<div class='box'><h3 style='color: green;'>Die Gruppe wurde gel�scht!</h3><h2>Gruppen</h2>"
							+ builder + "<br><br><a href='?add=true'><button>Gruppe erstellen</button></a></div>");
				}
			} else if(req.getParameter("name") != null && req.getParameter("ram") != null && req.getParameter("online") != null) {
				String name = req.getParameter("name");
				int ram = Integer.valueOf(req.getParameter("ram"));
				int online = Integer.valueOf(req.getParameter("online"));
				boolean isStatic = false;
				if(req.getParameter("static") != null) {
					isStatic = req.getParameter("static").equalsIgnoreCase("on");
				}
				String base = req.getParameter("base");
				if(TimoCloudAPI.getUniversalAPI().getServerGroup(name) == null) {
					if(!isStatic) {
						Map<String, Object> properties = new HashMap<String, Object>();
						properties.put("name", name);
						properties.put("online-amount", online);
						properties.put("ram", ram);
						properties.put("static", false);
						TimoCloudCore.getInstance().getInstanceManager().addGroup(new ServerGroup(properties));
						TimoCloudCore.getInstance().getInstanceManager().saveServerGroups();
						resp.sendRedirect("/groups?status=add_success");
					} else {
						if(base != null && !base.trim().isEmpty()) {
							if(TimoCloudCore.getInstance().getInstanceManager().getBase(base) != null) {
								if(online == 1 || online == 0) {
									Map<String, Object> properties = new HashMap<String, Object>();
									properties.put("name", name);
									properties.put("online-amount", online);
									properties.put("ram", ram);
									properties.put("static", true);
									properties.put("base", base);
									TimoCloudCore.getInstance().getInstanceManager().addGroup(new ServerGroup(properties));
									TimoCloudCore.getInstance().getInstanceManager().saveServerGroups();
									resp.sendRedirect("/groups?status=add_success");
								} else {
									resp.sendRedirect("/groups?add=true&addstatus=amount_to_high");
								}
							} else {
								resp.sendRedirect("/groups?add=true&addstatus=base_not_found");
							}
						} else {
							resp.sendRedirect("/groups?add=true&addstatus=base_needed");
						}
					}
				} else {
					resp.sendRedirect("/groups?add=true&addstatus=group_exists");
				}
				
				
			} else if (req.getParameter("add") != null) {
				if(req.getParameter("addstatus") != null) {
					 String addstatus = req.getParameter("addstatus");
					 String statusmessage = "";
					 switch(addstatus) {
					 case "group_exists":
						 statusmessage = "Die Gruppe existiert bereits!";
						 break;
					 case "base_needed":
						 statusmessage = "Statische Gruppen m�ssen einer BASE zugeteilt werden!";
						 break;
					 case "amount_to_high":
						 statusmessage = "Statische Gruppen k�nnen keinen Online Amount h�her als 1 haben!";
						 break;
					 case "base_not_found":
						 statusmessage = "Die BASE ist nicht im Core registriert!";
						 break;
					 }
					 boolean adding = Boolean.valueOf(req.getParameter("add"));
						if (adding) {
							String form = "<form method='get'><input placeholder='" + "Name"
									+ "' name='name' required><br><input placeholder='" + "RAM"
									+ "' name='ram' type='number'required><br>"
									+ "<input placeholder='Online Amount' name='online' type='number' required><br>"
									+ "Static? <input placeholder='Static' name='static' type='checkbox'><br>"
									+ "<input placeholder='BASE' name='base'><br>"
									+ "<button type='submit'>"
									+ "Gruppe erstellen" + "</button></form>";
							resp.setContentType("text/html");
							resp.setStatus(HttpServletResponse.SC_OK);
							resp.getWriter().println(TimoCloudWeb.navbar
									+ "<div class='box'><h3 style='color: red;'>" + statusmessage + "</h3><h2>Gruppe Erstellen</h2>" + form + "</div>");
						} else {
							resp.sendRedirect("/groups");
						}
				} else {
					boolean adding = Boolean.valueOf(req.getParameter("add"));
					if (adding) {
						String form = "<form method='get'><input placeholder='" + "Name"
								+ "' name='name' required><br><input placeholder='" + "RAM"
								+ "' name='ram' type='number'required><br>"
								+ "<input placeholder='Online Amount' name='online' type='number' required><br>"
								+ "Static? <input placeholder='Static' name='static' type='checkbox'><br>"
								+ "<input placeholder='BASE' name='base'><br>"
								+ "<button type='submit'>"
								+ "Gruppe erstellen" + "</button></form>";
						resp.setContentType("text/html");
						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().println(TimoCloudWeb.navbar
								+ "<div class='box'><h2>Gruppe Erstellen</h2>" + form + "</div>");
					} else {
						resp.sendRedirect("/groups");
					}
				}
				
			} else if (req.getParameter("delete") != null) {
				String removeGroup = req.getParameter("delete");
				ServerGroupObject group = TimoCloudAPI.getUniversalAPI().getServerGroup(removeGroup);
				if(group != null) {
					TimoCloudCore.getInstance().getInstanceManager().removeServerGroup(TimoCloudCore.getInstance().getInstanceManager().getServerGroupByName(removeGroup));
					resp.sendRedirect("/groups?status=remove_success");
				} else {
					resp.sendRedirect("/groups");
				}
			} else {

				String builder = "";
				for (ServerGroupObject server : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
					builder += "<div class='group'><p>Name: " + server.getName() + "</p>" + "<p>RAM: " + server.getRam()
							+ " MB</p>" + "<p>Online: " + server.getOnlineAmount() + "</p>" + "<p>Static: "
							+ server.isStatic() + "</p><a href='?edit=" + server.getName()
							+ "'><button>Editieren</button></a>" + "<a href='?delete=" + server.getName()
							+ "'><button>L�schen</button></a>" +"</div>";
				}

				resp.setContentType("text/html");
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().println(TimoCloudWeb.navbar + "<div class='box'><h2>Gruppen</h2>" + builder
						+ "<br><br><a href='?add=true'><button>Gruppe erstellen</button></a></div>");
			}
		} else {
			resp.sendRedirect("/login");
		}

	}

}
