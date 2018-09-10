package org.anahuac.indicca.login;

import java.util.HashMap;
import java.util.Map;

import org.anahuac.indicca.dao.Usuario;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {
	
	public static Filter validaSesionUsuario = (Request request, Response response) -> {
		System.out.println("Entra a before");
		String usuario = request.session().attribute("usuarioSesion");
		if(usuario == null || usuario.isEmpty()){
			response.redirect("/login.html");
		}
	};
	
    public static Route handleLoginPost = (Request request, Response response) -> {
    	
        Map<String, Object> model = new HashMap<>();
		String usuario = request.queryParams("idIngreso");
		String contrasenia = request.queryParams("contrasenia");
        if (!Usuario.authenticate(usuario, contrasenia)) {
            request.session().attribute("authenticationFailed", true);
            return "/login.html";
//            response.redirect("/login.html");
        } else {
        	request.session().attribute("usuarioSesion", usuario);
        	return "/index.html";
//        	response.redirect("/index.html");
        }
//        return "";
//        model.put("authenticationSucceeded", true);
//        request.session().attribute("usuarioSesion", usuario);
//        if (getQueryLoginRedirect(request) != null) {
//            response.redirect(getQueryLoginRedirect(request));
//        }
//        
//        return ViewUtil.render(request, model, "/login");
    };
    
    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
//        model.put("loggedOut", removeSessionAttrLoggedOut(request));
//        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
//        return ViewUtil.render(request, model, Path.Template.LOGIN);
        response.redirect("/login.html");
		return request;
    };
    
    public static Route logout = (Request request, Response response) -> {
    	request.session().removeAttribute("usuarioSesion");
    	response.redirect("/login.html");
		return "";
    };
}
