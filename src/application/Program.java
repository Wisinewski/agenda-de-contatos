package application;

import java.sql.Connection;

import db.DB;
import model.dao.ContactDao;
import model.dao.DaoFactory;
import model.entities.Contact;

public class Program {

	public static void main(String[] args) {
		Connection conn = DB.getConnection();
		
		ContactDao contactDao = DaoFactory.createContactDao();
		
		Contact con = new Contact("Vitor","30304567");
		contactDao.insert(con);
		
		
		

	}

}
