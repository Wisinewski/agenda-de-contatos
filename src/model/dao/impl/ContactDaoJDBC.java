package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ContactDao;
import model.entities.Contact;

public class ContactDaoJDBC implements ContactDao {

	private Connection conn;
	
	public ContactDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Contact obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO CONTATO "
					+ "(NAME, PHONE) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getPhone());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);;
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected");
			}
		}	
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}	

	@Override
	public void update(Contact obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE CONTATO "
					+ "SET Name = ?, Phone = ? "
					+ "WHERE id = ?");
					
			st.setString(1,  obj.getName());
			st.setString(2, obj.getPhone());
			st.setInt(3, obj.getId());
			
			st.executeUpdate();
		}	
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM CONTATO WHERE Id = ?");
					
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Contact findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT ID, NAME, PHONE FROM CONTATO "
					+ "WHERE ID = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Contact contact = instantiateContact(rs);
				return contact;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Contact> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM CONTATO "
					+ "ORDER BY ID");
			
			rs = st.executeQuery();
			
			List<Contact> list = new ArrayList<>();
			Map<Integer, Contact> map = new HashMap<>();
			while (rs.next()) {
				Contact contact = map.get(rs.getInt("ID"));
				
				if(contact == null) {
					contact = instantiateContact(rs);
					map.put(rs.getInt("ID"), contact);
				}
				
				Contact obj = instantiateContact(rs);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Contact instantiateContact(ResultSet rs) throws SQLException {
		Contact contact = new Contact();
		contact.setId(rs.getInt("ID"));
		contact.setName(rs.getString("NAME"));
		contact.setPhone(rs.getString("PHONE"));
		return contact;
	}
}