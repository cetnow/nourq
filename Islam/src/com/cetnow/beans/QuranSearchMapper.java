package com.cetnow.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class QuranSearchMapper implements RowMapper<QuranSearch> {


	@Override
	public QuranSearch mapRow(ResultSet rs, int arg1) throws SQLException {
		QuranSearch temp=new QuranSearch();
		 
		 temp.setId(rs.getInt("Id"));
		 temp.setAya(rs.getInt("Aya"));
		 temp.setSura(rs.getInt("Sura"));
		 temp.setAyaIndex(rs.getInt("AyaIndex"));
		 temp.setAyaClean(rs.getString("AyaClean"));
		 temp.setAyaUthmani(rs.getString("AyaUthmani"));
		 temp.setAyaUthmaniMin(rs.getString("AyaUthmaniMin"));
		 temp.setSuraName(rs.getString("SuraName"));
		 
		 return temp;
	}
}
