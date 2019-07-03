package model.dao;

import db.DB;

public class DaoFactory {

	public static ContactDao createContactDao() {
		return new ContactDaoJDBC(DB.getConnection());
	}
}
