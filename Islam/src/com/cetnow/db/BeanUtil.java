package com.cetnow.db;

import java.sql.SQLType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import com.cetnow.beans.BaseBean;

public class BeanUtil {
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	public static BeanExpression buildUpdateString(Object obj){
		BeanExpression result=new BeanExpression();
		ArrayList<Object> data=new ArrayList<Object>();
		ArrayList<Integer> types=new ArrayList<Integer>();
		
		try{
			BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(obj);
		
			String proNames[]=parameters.getReadablePropertyNames();
		
			String update="UPDATE "+getTableName(obj)+" SET";
			int c=0;
			for(int i=0; i<proNames.length;i++){
				String pName=proNames[i];
				char first = Character.toUpperCase(pName.charAt(0));
				pName = first + pName.substring(1);
				if(pName.equals("Id") || pName.equals("Class"))
					continue;
				update+=c==0?" "+pName+" = ?":" ,"+pName+" = ?";
				
				data.add(parameters.getValue(proNames[i]));
				types.add(parameters.getSqlType(proNames[i]));
				c++;
			}
			update+=" WHERE Id = ?";
			
			result.setUpdateQuery(update);
			data.add(getId(obj));
			types.add(4);
			result.setData(data.toArray());
			result.setSqlTypes(convertIntegers(types));
		}catch(Exception ex){}
		
		return result;
	}
	
	public static boolean isUpdateObject(Object obj){
		return getId(obj)>0;
	}
	

	public static int getId(Object obj){
		int val=0;
		try{
			val=Integer.parseInt(BeanUtils.describe(obj).get("id"));
		}catch(Exception ex){}
		return val;
	}
	
	public static String getTableName(Object obj){
		return obj.getClass().getSimpleName()+"s";
	}
	
	public static void PrintAtt(BaseBean base){
		Map<String,String> properties = null;
		try{
			properties=BeanUtils.describe(base);
		}catch(Exception ex){}
		System.out.println("--------------------");
		for(Entry<String, String> entry : properties.entrySet()){
			System.out.println(entry.getKey());
		}
		System.out.println("--------------------");
	}

}
