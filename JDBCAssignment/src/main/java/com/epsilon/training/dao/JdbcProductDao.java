package com.epsilon.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epsilon.training.entity.Product;
import com.epsilon.training.utils.DBUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JdbcProductDao implements ProductDao {

	@Override
	public void addProduct(Product p) throws DaoException {
		String sql="insert into products values(null,?,?,?,?,?,?,?,?)";

		try(Connection conn =DBUtil.createConnection();
				PreparedStatement stmt=conn.prepareStatement(sql);){
			
				
				stmt.setString(1,p.getCategory());
				stmt.setString(2, p.getName());
				stmt.setString(3, p.getBrand());
				stmt.setString(4,p.getDescription());
				stmt.setString(5,p.getQuantityPerUnit());
				stmt.setDouble(6, p.getUnitPrice());
				stmt.setString(7, p.getPicture());
				stmt.setDouble(8, p.getDiscount());
				
				stmt.execute();
				log.debug("1 ({}) record inserted successfully!",p.getName());
				
			
			
		}catch(Exception ex) {
			log.warn( "Error - {} -- ",ex.getMessage());
			ex.printStackTrace();
		}
		
		

	}

	@Override
	public Product getProduct(int id) throws DaoException {
		
		String sql = "select * from products where id = ?";
		try (Connection conn = DBUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					return createProduct(rs);
				}
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
		return null;
		
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		
		int id=p.getId();
		String sql = "select * from products where id = ?";
		try (Connection conn = DBUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					createProduct(rs);
				}
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
	private Product createProduct(ResultSet rs) throws SQLException, DaoException{
		Product p = new Product();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setBrand(rs.getString("brand"));
		p.setCategory(rs.getString("category"));
		p.setDescription(rs.getString("description"));
		p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
		p.setPicture(rs.getString("picture"));
		p.setDiscount(rs.getInt("discount"));
		p.setUnitPrice(rs.getDouble("unit_price"));
		return p;
	}

	@Override
	public void deleteProduct(int id) throws DaoException {
		
		String sql = "delete from products where id = ?";
		
		try(Connection conn =DBUtil.createConnection();
				PreparedStatement stmt=conn.prepareStatement(sql);){
			stmt.setInt(1, id);
			
			stmt.execute();
			log.debug("1  record deleted successfully!");
		}catch(Exception e)

		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> getAll() throws DaoException {
		String sql="select * from products";
		List<Product>list =new ArrayList<>();

		try(
			Connection conn=DBUtil.createConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);){
			while(rs.next()) {
			Product p = new Product();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setBrand(rs.getString("brand"));
			p.setCategory(rs.getString("category"));
			p.setDescription(rs.getString("description"));
			p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
			p.setPicture(rs.getString("picture"));
			p.setDiscount(rs.getInt("discount"));
			p.setUnitPrice(rs.getDouble("unit_price"));
			
			list.add(p);
			}
//			while(rs.next()) {
//				int id=rs.getInt("id");
//				String name=rs.getString("name");
//				String qpu=rs.getString("quantity_per_unit");
//				String brand=rs.getString("brand");
//				double price=rs.getDouble("unit_price");
////				System.out.printf("%d, %-20s, %-40s,%-10s, %-10s\n",id,name,qpu,brand,price);
//				
//			}
			
		}catch(Exception e) {
			log.warn("Error --{}",e.getMessage());
		}
		return lis
	}

	@Override
	public List<Product> getByPriceRange(double min, double max) throws DaoException {
		// TODO Auto-generated method stub
		return ProductDao.super.getByPriceRange(min, max);
	}

	@Override
	public List<Product> getByBrand(String brand) throws DaoException {
		// TODO Auto-generated method stub
		return ProductDao.super.getByBrand(brand);
	}

	@Override
	public List<Product> getByCategory(String category) throws DaoException {
		// TODO Auto-generated method stub
		return ProductDao.super.getByCategory(category);
	}
	

}
