package application;

import java.sql.Connection;

import db.DB;
import entities.Contact;

public class Program {

	public static void main(String[] args) {
		Connection conn = DB.getConnection();
		DB.closeConnection();
		
		Contact con = new Contact("Gian","33633333");
		System.out.println(con);

	}

}
