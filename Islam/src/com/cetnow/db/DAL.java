package com.cetnow.db;

import java.awt.Point;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.cetnow.beans.*;
import com.cetnow.util.Html;
import com.cetnow.util.UserConfiguration;

public class DAL {
	
	@Autowired
	private Html html;
	
	private JdbcTemplate jdbc;

	public void setJdbc(JdbcTemplate j) {
		this.jdbc = j;
	}

	public <T> Map<String,Object> getIdForMap(Object id, Class<T> type){
		String query="select * from "+type.getSimpleName()+"s where Id=?";
		Map<String,Object> result=null;
		try{
			result=jdbc.queryForMap(query,id);
		}catch(Exception ex){}
		
		return result;
	}
	public <T> T getById(Object id, Class<T> type){
		Object result=null;
		Map<String,Object> res=getIdForMap(id, type);
		try{
			result= type.newInstance();
			Map<String, String> properties = BeanUtils.describe(result);
			Map<String,String> newp=new HashMap<String, String>();
			for(Entry<String, String> entry : properties.entrySet()){
				String pName=entry.getKey();
				char first = Character.toUpperCase(pName.charAt(0));
				pName = first + pName.substring(1);
				newp.put(entry.getKey(), res.get(pName)+"");
				
			}
			BeanUtils.populate(result, newp);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		return (T)result;
	}
	
	public JSONArray getAyaSearchresult(String aya){
		String query="select b1.* from QuranSearch as b1 where b1.AyaClean like ? order by Sura,Aya ASC";
		return new JSONArray(jdbc.queryForList(query, "%"+aya+"%"));
	}
	
	public JSONArray getAllSura(){
		String query="select * from Suras order by Id asc";
		return new JSONArray(jdbc.queryForList(query));
	}
	
	public int getAyaSearchresultrr(String aya){
		String query="select count(*) from QuranSearch as b1 where b1.AyaClean like ? order by Sura,Aya ASC";
		return jdbc.queryForObject(query, Integer.class, "%"+aya+"%");
	}
	
	public List<Book> getBooksByCat(String cat){
		String query="select b1.* from Books as b1 where Cat=?";
		return jdbc.query(query, new BookMapper(),cat);
	}
	
	public Reader getReader(Object id){
		String query="select * from QuranReaders where Id=?";
		return jdbc.queryForObject(query, new ReaderMapper(),id);
	}
	
	public List<Reader> getActiveReaders(){
		String query="select * from QuranReaders where IsActive=1 ";
		return jdbc.query(query, new ReaderMapper());
	}
	
	public JSONArray getLoadAyatResults(UserConfiguration uc,int Sura,int FromAya,int ToAya){
		JSONArray result=new JSONArray();
		Map<String,Object> rer=new HashMap<String,Object>();
		rer.put("Reader",  new JSONObject(getReader(uc.getReaderId())));
		result.put(rer);
		
		String query="select case when mi.Id is null then 0 else count(*) end as  'MediaCount',mp3.ReaderFK,mp3.Sura,mp3.Aya,mp3.FileName,mp3.FileLength,qs.AyaClean,qs.AyaUthmani,qs.AyaUthmaniMin,s.ArName 'suraname' from QuranMp3 as mp3 ";
		query+=" join QuranSearch as qs on qs.Sura=mp3.Sura and qs.Aya=mp3.Aya ";
		query+=" join Suras as s on s.Id=mp3.Sura ";
		query+=" left join MediaInfos as mi on mi.Sura=mp3.Sura and mp3.Aya BETWEEN mi.FromAya AND mi.ToAya and mi.IsApproved=1 and mi.IsDeleted=0 and mi.IsDone=1 ";
		query+=" where mp3.ReaderFK=? and mp3.Sura=? and mp3.Aya BETWEEN ? and ? ";
		query+=" group by mp3.ReaderFK,mp3.Sura,mp3.Aya,mp3.FileName,mp3.FileLength,qs.AyaClean,qs.AyaUthmani,qs.AyaUthmaniMin,s.ArName ";
		query+=" order by mp3.Aya asc ";
		
		Map<String,JSONArray> mr=new HashMap<String,JSONArray>();
		mr.put("Data", new JSONArray(jdbc.queryForList(query, uc.getReaderId(),Sura,FromAya,ToAya)));
		
		result.put(mr);
		
		return result;
	}
	
	public JSONArray getBookContentsResult(UserConfiguration uc,int Sura,int FromAya,int ToAya){
		String query="select * from BookContents where BookFK=? and Sura=? and Aya BETWEEN ? and ? order by Aya asc";
		return new JSONArray(jdbc.queryForList(query,uc.getBookId(),Sura,FromAya,ToAya));
	}
	
	public long getQuranMp3Length (int reader,int sura, int from, int to ){
		String query="select sum(FileSize) 'FileSize' from QuranMp3 where ReaderFK=? and Sura=? and Aya BETWEEN ? AND ? ";
		return jdbc.queryForObject(query, Long.class,reader,sura,from,to);
	}
	public List<String> getFilesBySura(UserConfiguration conf,int sura,int fromAya,int toAya){
		String query="select FilePath from QuranMp3 where ReaderFK=? and Sura=? and Aya BETWEEN ? AND ? order by Aya ASC";
		return jdbc.queryForList(query, String.class,conf.getReaderId(),sura,fromAya,toAya);
	}
	public String getFileName(UserConfiguration conf,int sura,int fromAya,int toAya){
		return jdbc.queryForObject("select EnName from Suras where Id=?", String.class,sura) + " "+fromAya+" - "+toAya+" "+".mp3";
	}
	
	public Sura getSura(Object id){
		String query="select * from Suras where Id=?";
		return jdbc.queryForObject(query, new SuraMapper(),id);
	}
	
	public Map<String,Object> getMp3Secs(UserConfiguration uc,int Sura,int FromAya){
		String query="select FLOOR(sum(FileLength)/60000000) 'secs',MAX(Aya) 'lastaya' from QuranMp3 where ReaderFK=? and Sura=? and Aya BETWEEN ? AND ?";
		return jdbc.queryForMap(query,uc.getReaderId(),Sura,FromAya,1000);
	}
	public int saveOrUpdate(BaseBean obj){
		return saveOrUpdate(obj,true);
	}
	public int saveOrUpdate(BaseBean obj,boolean writeAudit){
		int result=0;
		
		if(BeanUtil.isUpdateObject(obj)){
			BeanExpression be=BeanUtil.buildUpdateString(obj);
			jdbc.update(be.getUpdateQuery(),be.getData(),be.getSqlTypes());
			result=obj.getId();
		}else{
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(obj);
			SimpleJdbcInsert insert= new SimpleJdbcInsert(this.jdbc)
	                .withTableName(BeanUtil.getTableName(obj))
	                .usingGeneratedKeyColumns("Id");
			
			Number newId = insert.executeAndReturnKey(parameters);
			result=newId.intValue();
			obj.setId(result);
		}
		if(writeAudit){
			ObjectMapper mapper = new ObjectMapper();
			Audit audit=new Audit();
			audit.setTableFK(obj.tableId());
			audit.setIsUpdate(BeanUtil.isUpdateObject(obj));
			audit.setTimeStamp(new Date());
			audit.setUserFK(UserConfiguration.userId());
			audit.setElementFK(result);
			try {
				audit.setJsonData(mapper.writeValueAsString(obj));
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveOrUpdate(audit, false);
		}
		
		
		return result;
	}
	
	public Map<String, Object> queryToMap(String query){
		return jdbc.queryForMap(query);
	}
	
	public List<Map<String, Object>> queryToMapList(String query){
		return jdbc.queryForList(query);
	}
	
	public JSONArray getLookup(int CatFK,String q){
		String query="select Name as 'name',Id as 'value' from Lookups where IsApproved=1 and IsDeleted=0 and CatFK=? and Name like ? order by Id asc";
		return new JSONArray(jdbc.queryForList(query,CatFK,"%"+q+"%"));
	}
	
	public Lookup addNewLookups(String name,int catFK){
		Lookup l=new Lookup();
		l.setName(name);
		l.setCatFK(catFK);
		saveOrUpdate(l);
		return l;
	}
	
	public List<Map<String, Object>> getMediaInfoByAya(int sura,int aya){
		String query="select mf.Id,mi.FileName,te.Name 'AutherName',ty.Name 'TypeName',"+html.ar("querycont1")+" 'MediaDec'  ";
		query+= " from MediaInfos as mi ";
		query+=" left join Suras as su on su.Id=mi.Sura ";
		query+=" join MediaFiles as mf on mf.Id=mi.MediaFileFK ";
		query+=" join Lookups as te on te.Id=mi.AutherFK ";
		query+=" join Lookups as ty on ty.Id=mi.TypeFK ";
		query+=" where mi.Sura=? and  ? BETWEEN mi.FromAya AND mi.ToAya ";
		return jdbc.queryForList(query,sura,aya);
	}
	
	public MediaFile downloadFile(int id){
		MediaFile result=getById(id, MediaFile.class);
		result.setFileView(result.getFileView()+1);
		saveOrUpdate(result);
		return result;
	}
	
	public boolean isValidLookup(int id,int catFK){
		String query="select count(*) from Lookups where IsApproved=1 and IsDeleted=0 and CatFK=? and Id=?";
		return jdbc.queryForObject(query, Integer.class, id,catFK)>0;
	}
	
	public boolean isUnique(String table,String column,String val){
		String query="select count(*) from "+table+" where "+column+"=?";
		return jdbc.queryForObject(query, Integer.class, val)<1;
	}
	
	public JSONArray getMediaInfos(String where,boolean isApproved){
		String query="select mi.*,auth.Name 'Auther',type.Name 'Type',"+html.ar("sql2")+" 'SuraInfo',"+html.ar("querycont1")+" 'MediaDec' from MediaInfos as mi ";
		query+=" join Lookups as auth on auth.Id=mi.AutherFK ";
		query+=" join Lookups as type on type.Id=mi.TypeFK ";
		query+=" left join Suras as su on su.Id=mi.Sura ";
		query+=" where mi.IsApproved="+(isApproved?"1":"0")+" and mi.IsDeleted=0 "+where;
		
		query+="  ";
		return new JSONArray(jdbc.queryForList(query));
	}
	
	public JSONArray getMediaInfos(boolean isApproved){
		return getMediaInfos("",isApproved);
	}
	
	public int getAllApproval(){
		String query="select count(*) from MediaInfos where IsApproved=1 and IsDone=1 and IsDeleted=0 ";
		return jdbc.queryForObject(query, Integer.class);
	}
	
	public Point getCalcMP3(int reader,int sura, int from , int to){
		String query="select case when  sum(FileLength) >0 then sum(FileLength) /1000 else 0 end as 'ms' from QuranMp3 where ReaderFK=? and Sura=? and Aya <?";
		int From= jdbc.queryForObject(query, Integer.class,reader,sura,from);
		query="select case when  sum(FileLength) >0 then sum(FileLength) /1000 else 0 end as 'ms' from QuranMp3 where ReaderFK=? and Sura=? and Aya <=?";
		int To= jdbc.queryForObject(query, Integer.class,reader,sura,to);
		return new Point(From,To);
	}
}
