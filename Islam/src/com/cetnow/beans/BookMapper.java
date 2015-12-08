package com.cetnow.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class BookMapper implements RowMapper<Book> {


	@Override
	public Book mapRow(ResultSet rs, int arg1) throws SQLException {
		 Book temp=new Book();
		 
		 temp.setId(rs.getInt("Id"));
		 temp.setName(rs.getString("Name"));
		 temp.setCat(rs.getString("Cat"));
		 temp.setArName(rs.getString("ArName"));
		 
		 return temp;
	}
}
