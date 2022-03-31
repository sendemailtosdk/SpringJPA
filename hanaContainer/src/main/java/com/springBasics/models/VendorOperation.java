package com.springBasics.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.springBasics.entities.Vendor;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.saj.InvalidSyntaxException;

@Component
public class VendorOperation {

public Connection conn;
public Statement stmt;
public ResultSet rs;
String url;
String user;
String password;

@PostConstruct
public void startConnection() {
this.url = "";
this.user = "";
this.password = "";

// Obtain the JSON Strig of VCAP Services
String vcapServices = System.getenv("VCAP_SERVICES");
System.out.println(vcapServices);
if(vcapServices != null && vcapServices.length() > 0) {
	
	try {
		JsonNode root = new JdomParser().parse(vcapServices);
		JsonNode serviceNode = root.getNode("hana");
		JsonNode credNode = serviceNode.getNode(0).getNode("credentials");
		
		this.url = credNode.getStringValue("url");
		this.user = credNode.getStringValue("user");
		this.password =  credNode.getStringValue("password");
		
	} catch (InvalidSyntaxException e) {
		// TODO Auto-generated catch block
		System.out.println("Error VCAP_SERVICES Parsing");
		e.printStackTrace();
	}
	
   try {
	conn =	DriverManager.getConnection(this.url,this.user,this.password);
	stmt = conn.createStatement();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	System.out.println("DB Connection failed");
	e.printStackTrace();
}
	
}




	
}
	
public List<Vendor> getAllVendors(){
	
	List<Vendor> vendorList = new ArrayList<Vendor>( );
	
	try {
		rs = stmt.executeQuery("select top 100 * from VENDOR");
		
		if(rs.wasNull()) {
			return vendorList ;
		}
		
		while(rs.next()) {
		
			vendorList.add(new Vendor(rs.getString("ID"),
					                  rs.getString("COMPANYNAME"),
					                  rs.getString("FIRSTNAME"),
					                  rs.getString("LASTNAME"),
					                  rs.getString("WEBSITE"),
					                  rs.getString("EMAIL"),
					                  rs.getString("STATUS"),
					                  rs.getString("GSTNO")
                                      ));
			
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Error in fetching the records");
		e.printStackTrace();
	}
	
	return vendorList;
}	


public Vendor getVendorById(String id){
	
	Vendor vendor = new Vendor( );
	List<Vendor> vendorList = new ArrayList<Vendor>( );
	
	try {
		if(rs.wasNull()) {
			return vendor ;
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	try {
		rs = stmt.executeQuery("select * from VENDOR where ID = " + id);
		
		while(rs.next()) {
		
			vendorList.add(new Vendor(rs.getString("ID"),
					                  rs.getString("COMPANYNAME"),
					                  rs.getString("FIRSTNAME"),
					                  rs.getString("LASTNAME"),
					                  rs.getString("WEBSITE"),
					                  rs.getString("EMAIL"),
					                  rs.getString("STATUS"),
					                  rs.getString("GSTNO")
                                      ));
			
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Error in fetching the record by id");
		e.printStackTrace();
	}
	
	return vendorList.get(0);
}	

public String getNextID() {
	String id = "";
	try {
		rs = stmt.executeQuery("SELECT MAX(ID) + 1 as ID FROM VENDOR");
		while(rs.next()) {
			
			id = rs.getString("ID");
	
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return id;
}

public Vendor createVendor(Vendor payload){
	

	
	try {

		
		String createStmt = "INSERT INTO VENDOR VALUES("
				+ "'"+this.getNextID()+"',"
				+ "'"+payload.getFirstName()+"',"
				+ "'"+payload.getLastName()+"',"
				+ "'"+payload.getCompanyName()+"',"
				+ "'"+payload.getWebsite()+"',"
				+ "'"+payload.getEmail()+"',"
				+ "'"+payload.getStatus()+"',"
				+ "'"+payload.getGstNo()+"'"
				+ ")";
		
		     stmt.executeUpdate(createStmt);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Error in fetching the record by id");
		e.printStackTrace();
		return new Vendor();
	}
	
	return payload;
}


@PreDestroy
public void endConnection() {
	try {
		rs.close();
		stmt.close();
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Error in ending connection");
		e.printStackTrace();
	}

	
}
	
}
