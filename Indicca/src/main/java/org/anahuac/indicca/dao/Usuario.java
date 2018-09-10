package org.anahuac.indicca.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mindrot.jbcrypt.BCrypt;

import spark.QueryParamsMap;

public class Usuario extends DAO {

	private static String insertUsuario = "INSERT INTO Indicca.Usuario(nombre,apellidoMaterno,apellidoPaterno,idTipoUsuario,idSeccion,idIngreso,contrasenia) VALUES (?,?,?,?,?,?,?);";
	private static String selectUsuario = "SELECT * FROM Indicca.Usuario WHERE (idUsuario = ? OR ? IS NULL) AND (nombre = ? OR ? IS NULL) AND (apellidoMaterno = ? OR ? IS NULL) AND (apellidoPaterno = ? OR ? IS NULL) AND (idTipoUsuario = ? OR ? IS NULL) AND (idSeccion = ? OR ? IS NULL);";
	private static String selectLogin = "SELECT * FROM Indicca.Usuario WHERE idIngreso = ?;";
	
	private Long idUsuario;
	private String nombre;
	private String apellidoMaterno;
	private String apellidoPaterno;
	private Long idTipoUsuario;
	private Long idSeccion;
	private String idIngreso;
	private String contrasenia;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("idUsuario", idUsuario)
				.append("nombre", nombre)
				.append("apellidoMaterno", apellidoMaterno)
				.append("apellidoPaterno", apellidoPaterno)
				.append("idTipoUsuario", idTipoUsuario)
				.append("idSeccion", idSeccion).append("idIngreso", idIngreso)
				.append("contrasenia", contrasenia).toString();
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public Long getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(Long idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public Long getIdSeccion() {
		return idSeccion;
	}
	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}
	
	public String getIdIngreso() {
		return idIngreso;
	}
	public void setIdIngreso(String idIngreso) {
		this.idIngreso = idIngreso;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public long insertUsuario() throws SQLException{
		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		String pass = BCrypt.hashpw(this.contrasenia, BCrypt.gensalt());
		return dao.update(insertUsuario, this.nombre,this.apellidoMaterno, this.apellidoPaterno,  this.idTipoUsuario, this.idSeccion, this.idIngreso, pass);
	}
	
	public List<Usuario> selectUsuario(QueryParamsMap queryParamsMap) throws SQLException{
		Usuario usuario = getUsuarioQueryParam(queryParamsMap);
		List<Object> listaSelect = new ArrayList<>();
		listaSelect.add(usuario.getIdUsuario());listaSelect.add(usuario.getIdUsuario());
		listaSelect.add(usuario.getNombre());listaSelect.add(usuario.getNombre());
		listaSelect.add(usuario.getApellidoMaterno());listaSelect.add(usuario.getApellidoMaterno());
		listaSelect.add(usuario.getApellidoPaterno());listaSelect.add(usuario.getApellidoPaterno());
		listaSelect.add(usuario.getIdTipoUsuario());listaSelect.add(usuario.getIdTipoUsuario());
		listaSelect.add(usuario.getIdSeccion());listaSelect.add(usuario.getIdSeccion());
		return dao.select(selectUsuario, listaSelect, Usuario.class);
	}
	
	public Usuario getUsuarioQueryParam(QueryParamsMap queryParamsMap) {
		Usuario usr = new Usuario();
		usr.setIdUsuario(queryParamsMap.get("idUsuario").longValue());
		usr.setNombre(queryParamsMap.get("nombre").value());
		usr.setApellidoMaterno(queryParamsMap.get("apellidoMaterno").value());
		usr.setApellidoPaterno(queryParamsMap.get("apellidoPaterno").value());
		usr.setIdTipoUsuario(queryParamsMap.get("idTipoUsuario").longValue());
		usr.setIdSeccion(queryParamsMap.get("idSeccion").longValue());
		return usr;
	}
	
	public static void main(String[] args) throws SQLException {
		authenticate("salvador", "salvador");
	}
	
	 public static boolean authenticate(String idIngreso, String contrasenia) throws SQLException {
		 if(idIngreso == null || idIngreso.isEmpty()){
			 return false;
		 }
		 if(contrasenia == null || contrasenia.isEmpty()){
			 return false;
		 }
		 
		 
		 List<Object> listaSelect = new ArrayList<>();
		 listaSelect.add(idIngreso);
		 List<Usuario> listUsuario = dao.select(selectLogin, listaSelect, Usuario.class);
		 
		 System.out.println(listUsuario);
		 
		 if(!listUsuario.isEmpty()){
			Usuario usr = listUsuario.get(0);
			System.out.println(BCrypt.checkpw(contrasenia, usr.getContrasenia()));
			return BCrypt.checkpw(contrasenia, usr.getContrasenia());
		 }
		 
		 return false;
	 }
	
}
