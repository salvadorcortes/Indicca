package org.anahuac.indicca.db;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.anahuac.indicca.dao.TipoUsuario;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOConnection {
	
	private static MysqlDataSource dataSource;
	
	static {
        try {
        	dataSource = new MysqlDataSource();
        	dataSource.setUser("root");
        	dataSource.setPassword("Salvador");
        	dataSource.setServerName("localhost");
        	dataSource.setDatabaseName("Indicca");
        	dataSource.setServerTimezone("UTC");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        System.out.println("Conexion lista "+dataSource);
    }
	
	public static void main(String[] args) throws SQLException {
		DAOConnection dao = new DAOConnection();
		List<TipoUsuario> lista = dao.select("SELECT * FROM Indicca.TipoUsuario WHERE idTipoUsuario = IFNULL(?, idTipoUsuario) ;", Arrays.asList(3), TipoUsuario.class);
		System.out.println(lista);
	}
	
	public long update(String query, Object ... params) throws SQLException{
		QueryRunner run = new QueryRunner( dataSource );
		return run.update(query, params);
	}
	
	public <T> List<T> select(String query, List<Object> listaFiltro, Class<T> type) throws SQLException{
		QueryRunner run = new QueryRunner( dataSource );
		ResultSetHandler<List<T>> rh = new BeanListHandler<T>(type);
		if(listaFiltro == null || listaFiltro.isEmpty()){
			return run.query(query, rh);
		} else {
			return run.query(query, rh, listaFiltro.toArray());
		}
	}
}
