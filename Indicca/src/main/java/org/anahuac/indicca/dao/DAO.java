package org.anahuac.indicca.dao;

import org.anahuac.indicca.db.DAOConnection;

import com.google.gson.Gson;

public abstract class DAO {
	
	protected static final Gson gson = new Gson();
	protected static final DAOConnection dao = new DAOConnection();
	
}
