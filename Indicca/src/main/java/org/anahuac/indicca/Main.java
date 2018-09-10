package org.anahuac.indicca;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.sql.SQLException;

import org.anahuac.indicca.dao.TipoUsuario;
import org.anahuac.indicca.dao.Usuario;
import org.anahuac.indicca.login.LoginController;

import com.google.gson.Gson;

public class Main {
	
    public static void main(String[] args) {
    	
    	port(4567);
    	staticFiles.location("/public");
    	
    	Gson gson = new Gson();
    	
    	get("/getAllTipoUsuario", (req, res)-> { res.type("application/json"); return gson.toJson(TipoUsuario.class.newInstance().selectTodosTipoUsuario()); } );
    	get("/getTipoUsuarioId","application/json", (req, res)->{ res.type("application/json"); return gson.toJson(TipoUsuario.class.newInstance().selectTipoUsuarioXId(req.queryParams("idTipoUsuario"))); });
    	
    	
    	post("/createUsuario","application/json", (request, response) -> {
    	    response.type("application/json");
    	    try{
	    	    Usuario user = new Gson().fromJson(request.body(), Usuario.class);
	    	    user.insertUsuario();
	    	    response.status(201);
    	    } catch(SQLException e){
    	    	e.printStackTrace();
    	    	response.status(409);
    	    	ErrorHTTP errorHTTP = new ErrorHTTP();
    	    	errorHTTP.setMensaje("Error de BD");
    	    	return gson.toJson(errorHTTP);
    	    }
    	 
    	    return "";
    	});
    	

    	get("/getUsuarios","application/json", (req, res)-> { res.type("application/json"); return gson.toJson(Usuario.class.newInstance().selectUsuario(req.queryMap())); });
    	
//    	get("/login",LoginController.serveLoginPage);
    	before(LoginController.validaSesionUsuario);
    	post("/login","application/json",LoginController.handleLoginPost);
    	
    	get("/logout", LoginController.logout);
    }
}