package com.cetnow.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class SuraMapper implements RowMapper<Sura> {


	@Override
	public Sura mapRow(ResultSet rs, int arg1) throws SQLException {
		Sura temp=new Sura();
		 
		 temp.setId(rs.getInt("Id"));
		 temp.setArName(rs.getString("ArName"));
		 temp.setEnName(rs.getString("EnName"));
		 temp.setAyaCount(rs.getInt("AyaCount"));
		 temp.setPrevAyat(rs.getInt("PrevAyat"));
		 
		 return temp;
	}
}
