/**
 * 
 */
package de.ragingelias.timocloudweb;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.core.commands.CommandHandler;
import cloud.timo.TimoCloud.api.core.commands.CommandSender;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import cloud.timo.TimoCloud.api.plugins.TimoCloudPlugin;
import de.ragingelias.timocloudweb.servlets.BasesServlet;
import de.ragingelias.timocloudweb.servlets.GroupsServlet;
import de.ragingelias.timocloudweb.servlets.LoginServlet;
import de.ragingelias.timocloudweb.servlets.LogoutServlet;
import de.ragingelias.timocloudweb.servlets.PlayersServlet;
import de.ragingelias.timocloudweb.servlets.ProxysServlet;
import de.ragingelias.timocloudweb.servlets.RootServlet;
import de.ragingelias.timocloudweb.servlets.StartServlet;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * @author Elias Kleppinger
 *
 */
public class TimoCloudWeb extends TimoCloudPlugin {

	public static Server server;
	public static ConfigurationProvider confProv = ConfigurationProvider.getProvider(YamlConfiguration.class);
	public static File configFile = new File("web.yml");
	@Override
	public void onLoad() {
		try {
			TimoCloudAPI.getCoreAPI().registerCommandHandler(new CommandHandler() {
				
				@Override
				public void onCommand(String cmd, CommandSender cs, String... args) {
					if(args.length == 2) {
						String user = args[0];
						String pass = args[1];
						try {
							Configuration conf = confProv.load(configFile);
							conf.set("users." + user, pass);
							confProv.save(conf, configFile);
							cs.sendMessage("Der Benutzer " + user + " wurde erstellt/geändert!");
						} catch (IOException e) {
							cs.sendError("Ein Fehler trat auf: " + e.getMessage());
							e.printStackTrace();
						}
					} else {
						cs.sendMessage("Bitte gebe einen Namen und ein Passwort an!");
					}
					
				}
			}, "login", "user", "createuser");
			if(!configFile.exists()) {
				configFile.createNewFile();
				Configuration conf = confProv.load(configFile);
				conf.set("web.port", 8080);
				conf.set("users.admin", "admin");
				confProv.save(conf, configFile);
			}
			Configuration conf = confProv.load(configFile);
			server = new Server(conf.getInt("web.port"));
			ServletHandler handler = new ServletHandler();
			server.setHandler(handler);
			HashSessionIdManager idmanager = new HashSessionIdManager();
			server.setSessionIdManager(idmanager);
			HashSessionManager manager = new HashSessionManager();
			SessionHandler sessions = new SessionHandler(manager);
			handler.setHandler(sessions);
			handler.addServletWithMapping(RootServlet.class, "/");
			handler.addServletWithMapping(LoginServlet.class, "/login");
			handler.addServletWithMapping(StartServlet.class, "/start");
			handler.addServletWithMapping(GroupsServlet.class, "/groups");
			handler.addServletWithMapping(BasesServlet.class, "/bases");
			handler.addServletWithMapping(LogoutServlet.class, "/logout");
			handler.addServletWithMapping(PlayersServlet.class, "/players");
			handler.addServletWithMapping(ProxysServlet.class, "/proxys");
			server.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onUnload() {
		super.onUnload();
	}
	
	
	public static String style = "<head><title>TimoCloud</title></head><style>@import url('https://fonts.googleapis.com/css?family=Roboto');\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"html, body {\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"::-webkit-input-placeholder { /* Chrome/Opera/Safari */\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"::-moz-placeholder { /* Firefox 19+ */\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			":-ms-input-placeholder { /* IE 10+ */\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			":-moz-placeholder { /* Firefox 18- */\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".box {\r\n" + 
			"\r\n" + 
			"    width: 60%;\r\n" + 
			"\r\n" + 
			"    border-top: 5px solid #489cdf;\r\n" + 
			"\r\n" + 
			"    padding-top: 20px;\r\n" + 
			"\r\n" + 
			"    padding-bottom: 20px;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"    margin: 3% auto;\r\n" + 
			"\r\n" + 
			"    background-color: #EFEFEF;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".gray {\r\n" + 
			"\r\n" + 
			"    color: #696969;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"hr {\r\n" + 
			"\r\n" + 
			"    width: 70%;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"textarea {\r\n" + 
			"\r\n" + 
			"    padding: 5px;\r\n" + 
			"\r\n" + 
			"    margin-bottom: 10px;\r\n" + 
			"\r\n" + 
			"    margin-top: 10px;\r\n" + 
			"\r\n" + 
			"    width: 50%;\r\n" + 
			"\r\n" + 
			"    height: 100px;\r\n" + 
			"\r\n" + 
			"    resize: none;\r\n" + 
			"\r\n" + 
			"    outline: none;\r\n" + 
			"\r\n" + 
			"    border: none;\r\n" + 
			"\r\n" + 
			"    border-bottom: #c1c1c1 solid 2px;\r\n" + 
			"\r\n" + 
			"    font-family: 'Roboto', sans-serif;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"textarea:focus {\r\n" + 
			"\r\n" + 
			"    border-bottom: #5193e1 solid 2px;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".login {\r\n" + 
			"\r\n" + 
			"    width: 350px;\r\n" + 
			"\r\n" + 
			"    border-top: 5px solid #489cdf;\r\n" + 
			"\r\n" + 
			"    padding-top: 20px;\r\n" + 
			"\r\n" + 
			"    padding-bottom: 20px;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"    margin: 3% auto;\r\n" + 
			"\r\n" + 
			"    background-color: #EFEFEF;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"input {\r\n" + 
			"\r\n" + 
			"    border: none;\r\n" + 
			"\r\n" + 
			"    font-size: 20px;\r\n" + 
			"\r\n" + 
			"    margin-top: 20px;\r\n" + 
			"\r\n" + 
			"    padding-right: 5px;\r\n" + 
			"\r\n" + 
			"    padding-left: 5px;\r\n" + 
			"\r\n" + 
			"    border-bottom: #c1c1c1 solid 2px;\r\n" + 
			"\r\n" + 
			"    outline: none;\r\n" + 
			"\r\n" + 
			"    display: inline-block;\r\n" + 
			"\r\n" + 
			"    background-color: #EFEFEF;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"input:focus {\r\n" + 
			"\r\n" + 
			"    border-bottom: #5193e1 solid 2px;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".ticket_item {\r\n" + 
			"\r\n" + 
			"    color: #333333;\r\n" + 
			"\r\n" + 
			"    text-decoration: none;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"    font-size: 18px;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".ticket_item:hover {\r\n" + 
			"\r\n" + 
			"    color: #131313;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"button {\r\n" + 
			"\r\n" + 
			"    text-decoration: none;\r\n" + 
			"\r\n" + 
			"    padding: 7px 10px;\r\n" + 
			"\r\n" + 
			"    margin: 10px 5px 10px 5px;\r\n" + 
			"\r\n" + 
			"    color: darkslategray;\r\n" + 
			"\r\n" + 
			"    font-size: 14px;\r\n" + 
			"\r\n" + 
			"    background-color: #DFDFDF;\r\n" + 
			"\r\n" + 
			"    border-radius: 2px;\r\n" + 
			"\r\n" + 
			"    font-weight: bolder;\r\n" + 
			"\r\n" + 
			"    border: 1px solid #D7D7D7;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"button:hover {\r\n" + 
			"\r\n" + 
			"    background-color: #dedede;\r\n" + 
			"\r\n" + 
			"    border: 1px solid #2c8ca9;\r\n" + 
			"\r\n" + 
			"    color: #162626;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".menu {\r\n" + 
			"\r\n" + 
			"    padding: 10px 17px;\r\n" + 
			"\r\n" + 
			"    margin: 10px 5px 10px 5px;\r\n" + 
			"\r\n" + 
			"    font-size: 17px;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".notice {\r\n" + 
			"\r\n" + 
			"    color: #4c4c4c;\r\n" + 
			"\r\n" + 
			"    text-decoration: none;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".notice:hover {\r\n" + 
			"\r\n" + 
			"    color: #191919;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"div.notice {\r\n" + 
			"\r\n" + 
			"    position: relative;\r\n" + 
			"\r\n" + 
			"    left: 50%;\r\n" + 
			"\r\n" + 
			"    bottom: 1px;\r\n" + 
			"\r\n" + 
			"    transform: translate(-50%, -50%);\r\n" + 
			"\r\n" + 
			"    margin: 0 auto;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".group {\r\n" + 
			"\r\n" + 
			"    width: 150px;\r\n" + 
			"\r\n" + 
			"    height: 240px;\r\n" + 
			"\r\n" + 
			"    border: 1px solid #2c8ca9;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"    margin: 10px;\r\n" + 
			"\r\n" + 
			"    background-color: #f9f9f9;\r\n" + 
			"\r\n" + 
			"    box-shadow: 2px 2px 10px #969696;\r\n" + 
			"\r\n" + 
			"    display: inline-block;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".group p {\r\n" + 
			"\r\n" + 
			"    margin-bottom: 1px;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"ul {\r\n" + 
			"\r\n" + 
			"    list-style-type: none;\r\n" + 
			"\r\n" + 
			"    margin: 0;\r\n" + 
			"\r\n" + 
			"    padding: 0;\r\n" + 
			"\r\n" + 
			"    overflow: hidden;\r\n" + 
			"\r\n" + 
			"    cursor: default;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".navbar li {\r\n" + 
			"\r\n" + 
			"    border-top: 3px solid #489cdf;\r\n" + 
			"\r\n" + 
			"    float: left;\r\n" + 
			"\r\n" + 
			"    cursor: pointer;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".navbar li a {\r\n" + 
			"\r\n" + 
			"    display: block;\r\n" + 
			"\r\n" + 
			"    color: rgb(213, 202, 217);\r\n" + 
			"\r\n" + 
			"    background-color: #252326;\r\n" + 
			"\r\n" + 
			"    text-align: center;\r\n" + 
			"\r\n" + 
			"    padding: 13px 15px;\r\n" + 
			"\r\n" + 
			"    text-decoration: none;\r\n" + 
			"\r\n" + 
			"    cursor: pointer;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".navbar li a:hover {\r\n" + 
			"\r\n" + 
			"    color: #fff;\r\n" + 
			"\r\n" + 
			"    background-color: #282529;\r\n" + 
			"\r\n" + 
			"    cursor: pointer;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".active {\r\n" + 
			"\r\n" + 
			"    background-color: #1d1b1e !important;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".nav-right {\r\n" + 
			"\r\n" + 
			"    float: right !important;\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			".nav-left {\r\n" + 
			"\r\n" + 
			"    float: left !important;\r\n" + 
			"\r\n" + 
			"}</style>";
	
	public static String navbar = style + "<ul class='navbar'><li><a href='/start'>TimoCloud</a></li>"
			+ "<li class='navbar'><a href='/groups'>Gruppen</a></li>"
			+ "<li class='navbar'><a href='/players'>Spieler</a></li>"
			+ "<li class='navbar'><a href='/bases'>BASE's</a></li>"
			+ "<li class='navbar'><a href='/proxys'>Proxy-Gruppen</a></li>"
			+ "<li class='navbar'><a href='/logout'>Logout</a>"
			+ "</li></ul>";

}
