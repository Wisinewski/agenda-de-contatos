package application;

import java.sql.Connection;
import java.util.List;

import db.DB;
import model.dao.ContactDao;
import model.dao.DaoFactory;
import model.entities.Contact;

public class Program {

	public static void main(String[] args) {
		Connection conn = DB.getConnection();
		
		ContactDao contactDao = DaoFactory.createContactDao();
		
		// INSERIR CONTATOS
		contactDao.insert( new Contact(null,"Thiago","88877777"));
		contactDao.insert( new Contact(null,"Joao","33333333"));
		contactDao.insert( new Contact(null,"Vitor","55555555"));
		//
		
		// ATUALIZAR DADOS
		Contact oldContact = contactDao.findById(1);
		oldContact.setName("Marcos");
		oldContact.setPhone("11111111");
		contactDao.update(oldContact);
		//
		
		// APAGAR CONTATO POR ID
		contactDao.deleteById(2);
		//
		
		// ACHAR CONTATO POR ID
		System.out.println(contactDao.findById(1));
		System.out.println();
		//
		
		// ACHAR TODOS OS CONTATOS
		List<Contact> list = contactDao.findAll();
		for(Contact contact : list) {
			System.out.println(contact);
		}
		//
	}
}