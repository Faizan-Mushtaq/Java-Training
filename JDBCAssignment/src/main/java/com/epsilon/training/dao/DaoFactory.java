package com.epsilon.training.dao;

import java.util.ResourceBundle;

public final class DaoFactory {

	private static final String discriminator;//"JDBC";//="SERIALIZED";//"CSV";//"ARRAYLIST";//="ARRAY";//"DUMMY";
	
	private DaoFactory() {
		}
	static {
//		String envVal =System.getenv("DAO_IMPL");
//		if(envVal == null) {
//		//gets Executed only once when the class is loaded
//		ResourceBundle rb=ResourceBundle.getBundle("dao");
//		discriminator =rb.getString("dao.impl");
//		}else {
//			discriminator=envVal;
//		}
		
		ResourceBundle rb=ResourceBundle.getBundle("dao");
		discriminator =rb.getString("dao.impl");
	}
	public static ProductDao getProductDao() {
		switch(discriminator.toUpperCase()) {
		case "DUMMY":
			//return new DummyProductDao();
		case "ARRAY":
			//return new ArrayProductDao();
		case "ARRAYLIST":
			//return new ArrayListProductDao();
			
		case "JDBC":
			return new JdbcProductDao();
			
		case "MONGODB":
		case "CSV":
			//return new CsvProductDao();	
		case "SERIALIZED":
			//return new SerializedProductDao();
		default:
			throw new RuntimeException("Invalid discriminator"+" :"+discriminator);
		}
	}
}
