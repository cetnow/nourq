package com.cetnow.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class ReaderMapper implements RowMapper<Reader> {


	@Override
	public Reader mapRow(ResultSet rs, int arg1) throws SQLException {
		Reader temp=new Reader();
		 
		temp.setId(rs.getInt("Id"));
		 temp.setName(rs.getString("Name"));
		 temp.setPath(rs.getString("Path"));
		 temp.setQuality(rs.getString("Quality"));
		 temp.setArabic(rs.getBoolean("Arabic"));
		 temp.setIsActive(rs.getBoolean("IsActive"));
		 
		 return temp;
	}
}
