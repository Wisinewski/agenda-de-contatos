package model.dao;

import java.util.List;

import model.entities.Contact;

public interface ContactDao {
	
	void insert(Contact obj);
	void update(Contact obj);
	void deleteById(Integer id);
	Contact findById(Integer id);
	List<Contact> findAll();
}