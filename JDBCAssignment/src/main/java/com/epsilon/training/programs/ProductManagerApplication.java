package com.epsilon.training.programs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;

import com.epsilon.training.dao.DaoException;
import com.epsilon.training.dao.DaoFactory;
import com.epsilon.training.dao.ProductDao;
import com.epsilon.training.entity.Product;
import com.epsilon.training.utils.DBUtil;
import com.epsilon.training.utils.KeyboardUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProductManagerApplication {

	ProductDao dao;

	void start() throws Exception {
		// tight coupling; must be avoided
		//dao = new DummyProductDao();//don't do
		dao = DaoFactory.getProductDao();

		while (true) {
			menu();
			try {
				int choice = KeyboardUtil.getInt("Enter your choice: ");
				if (choice == 0) {
					System.out.println("Thank you and have a nice day.");
					break;
				}

				switch (choice) {
				case 1:
					acceptAndAddProductDetails();
					break;
				case 2:
					acceptAndGetProductById();
					break;
				case 3:
					acceptAndupdateProduct();
					break;
				case 4:
					acceptAndDeleteProduct();
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}
	
	

	



	private void acceptAndDeleteProduct() {
		int id=KeyboardUtil.getInt("Enter Id");
//		try {
//			//Product p=dao.updateProduct(id);
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
		
	}







	private void acceptAndGetProductById() throws Exception {
		int id=KeyboardUtil.getInt("Enter Id");
		try {
			Product p=dao.getProduct(id);
			String sql="select * from products where id=?";
			
			try(
				Connection conn=DBUtil.createConnection();
					PreparedStatement stmt=conn.prepareStatement(sql);){
				stmt.setInt(1, id);
				
				try(ResultSet rs=stmt.executeQuery()){
					if(rs.next()) {
						
						
						System.out.println("Name	:"+rs.getString("name"));
						System.out.println("QPU	:"+rs.getString("quantity_per_unit"));
						System.out.println("Brand	:"+rs.getString("brand"));
						System.out.println("Category:"+rs.getString("category"));
						System.out.println("Price	:Rs."+rs.getString("unit_price"));
						System.out.println("Picture	:"+rs.getString("picture"));
						
					}
				}
				
			}
		
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}







	private void acceptAndupdateProduct() {
		
	
		
			// create variables for all product fields
			// accept value for each variable from the user
			//int id = KeyboardUtil.getInt("Enter id: ");
			//Product p1=dao.getProduct(id);
			int id = KeyboardUtil.getInt("Enter the product id: ");
			
			try {
				Product p = dao.getProduct(id);
				if (p == null) {
					System.out.println("No product data found for id " + id);
				} else {
					// display product fields and ask for modification
					String input;
					input = getUserInput("Name", p.getName());
					p.setName(input);
					
					input = getUserInput("Brand", p.getBrand());
					p.setBrand(input);
					
					input = getUserInput("Category", p.getCategory());
					p.setCategory(input);
					
					input = getUserInput("Description", p.getDescription());
					p.setDescription(input);
					
					input = getUserInput("Quantity per unit", p.getQuantityPerUnit());
					p.setQuantityPerUnit(input);
					
					input = getUserInput("Picture", p.getPicture());
					p.setPicture(input);
					
					input = getUserInput("Unit price", p.getUnitPrice());
					p.setUnitPrice(Double.parseDouble(input));
					
					input = getUserInput("Discount", p.getDiscount());
					p.setDiscount(Integer.parseInt(input));
					
					log.debug("Product is {}", p);
				}
				
			} catch (DaoException e) {
				log.warn("There was an error - {}", e.getMessage());
			}
	
	}
		private String getUserInput(String fieldTitle, Object currVal) {
			String input;
			input = KeyboardUtil.getString(String.format("Enter %s: (%s) ", fieldTitle, currVal)).trim();
			if (input.equals("")) {
				return String.valueOf(currVal);
			}
			return input;
		}


	

	void acceptAndAddProductDetails() {
		//create variable fields for all products
		 //accept all and then use AddProductDao
		// create a product object
			// use the addProduct function in dao object
		
			
			try {
				// create variables for all product fields
				// accept value for each variable from the user
				//int id = KeyboardUtil.getInt("Enter id: ");
				String name=KeyboardUtil.getString("Name			:");
				String brand=KeyboardUtil.getString("Brand			:");
				String category=KeyboardUtil.getString("Category		:");
				String quantityperunit=KeyboardUtil.getString("Quantity per unit	:");
				String description=KeyboardUtil.getString("Description		:");
				String picture=KeyboardUtil.getString("Picture			:");
				double unitprice=KeyboardUtil.getDouble("Price			:");
				int discount=KeyboardUtil.getInt("Discount		:");
				
				Product p = new Product();
				//p.setId(id);
				p.setName(name);
				p.setDescription(description);
				p.setBrand(brand);
				p.setCategory(category);
				p.setQuantityPerUnit(quantityperunit);
				p.setUnitPrice(unitprice);
				p.setDiscount(discount);
				p.setPicture(picture);
				

				
				dao.addProduct(p);
			System.out.println("New product details added to successfully!");
			}
			catch(Exception ex) {
				log.warn("There was an error while trying to add a product");
				log.warn(ex.getMessage());
			}
	}

	void menu() {
		System.out.println("*** Main Menu ***");
		System.out.println("0. Exit");
		System.out.println("1. Add a new product");
		System.out.println("2. Retrieve a product by id");
		System.out.println("3. Modify details of a product");
		System.out.println("4. Remove product details");
	}

	public static void main(String[] args) throws Exception {
		new ProductManagerApplication().start();
	}

}
