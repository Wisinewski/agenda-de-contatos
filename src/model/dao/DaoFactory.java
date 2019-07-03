package model.dao;

import db.DB;
import model.dao.impl.ContactDaoJDBC;

public class DaoFactory {

	public static ContactDao createContactDao() {
		return new ContactDaoJDBC(DB.getConnection());
	}
}
