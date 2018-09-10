package org.anahuac.indicca.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TipoUsuario extends DAO{

	private Long idTipoUsuario;
	private String descripcion;
	
	private static String selectTodos = "SELECT * FROM Indicca.TipoUsuario;";
	private static String selectXId = "SELECT * FROM Indicca.TipoUsuario WHERE idTipoUsuario = IFNULL(?, idTipoUsuario) ;";
	
	
	public Long getIdTipoUsuario() {
		return idTipoUsuario;
	}


	public void setIdTipoUsuario(Long idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<TipoUsuario> selectTodosTipoUsuario() throws SQLException{
		return dao.select(selectTodos, null, TipoUsuario.class);
	}
	
	public List<TipoUsuario> selectTipoUsuarioXId(String idTipoUsuario) throws SQLException{
		return dao.select(selectXId, Arrays.asList(idTipoUsuario), TipoUsuario.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("idTipoUsuario", idTipoUsuario)
				.append("descripcion", descripcion).toString();
	}
	
}
