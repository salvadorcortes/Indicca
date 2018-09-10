package org.anahuac.indicca.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Seccion extends DAO {

	private Long idSeccion;
	private String descripcion;
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("idSeccion", idSeccion)
				.append("descripcion", descripcion).toString();
	}
	public Long getIdSeccion() {
		return idSeccion;
	}
	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
