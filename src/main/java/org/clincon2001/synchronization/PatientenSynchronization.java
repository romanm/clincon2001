package org.clincon2001.synchronization;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.domain.Patient;
import org.clincon2001.domain.Patientows1stgeorg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientenSynchronization")
public class PatientenSynchronization {

	@Autowired @Qualifier("simpleJdbc3")	private		JdbcTemplate	db3ows1tuboJdbc;
	@Autowired @Qualifier("simpleJdbc2")	private		JdbcTemplate	db2johanisPlatzJdbc;
	@Autowired @Qualifier("simpleJdbc1")	private		JdbcTemplate	db1stGeorgJdbc;
	@Autowired @Qualifier("simpleJdbc")		private		JdbcTemplate	clinconJdbc;

	private final Log logger = LogFactory.getLog(getClass());
//	String stationName = "johanisplatz";
//	String stationName ;
	int idFolder=1048962;
//	AppUtils appUtils;

	@Transactional
	public void updatePatientenOws1tubo(AppUtils appUtils) {
		logger.debug("-------BEGIN-------");
		int ows1tubo_maxPatiantId=maxPatiantId("ows1tubo",  clinconJdbc);
		logger.debug(ows1tubo_maxPatiantId);
		copyPatienten2tmp("ows1tubo",ows1tubo_maxPatiantId, db3ows1tuboJdbc);
		int stgeorg_maxPatiantId = maxPatiantId("tmp",clinconJdbc);
		logger.debug(stgeorg_maxPatiantId);
		copyPatienten2tmp("tmp",stgeorg_maxPatiantId,db1stGeorgJdbc);
		logger.debug("copyNewPatientdb1to3 -- ows1tubo");
		copyNewPatientdb1to3(appUtils,"ows1tubo");
		logger.debug("-------END-------");
	}
	private void copyNewPatientdb1to3(AppUtils appUtils, String dbschema) {
		String sql=" SELECT pj.*, ps.idpatient AS psidpatient, pj.idfolder AS idfoldertmp " +
		" FROM " +dbschema +".patient pj LEFT JOIN tmp.patient ps " +
		" ON ( lower(pj.forename)=lower(ps.forename) " +
		" AND pj.patient=ps.patient " +
		" AND pj.sex=ps.sex " +
		" AND CAST(pj.birthdate AS date)=CAST(ps.birthdate AS date) " +
		" ) " +
		" WHERE " +
		" ps.forename IS NULL ";
		logger.debug(sql);
		List<Map<String, Object>> newPatientsList = clinconJdbc.queryForList(sql);
		logger.debug(newPatientsList.size());
		int i=0;
		for (Map<String, Object> map : newPatientsList) {
			i++;
			logger.debug(map);
			Integer idfoldertmp=(Integer) map.get("idfoldertmp");
			logger.debug("idfoldertmp="+idfoldertmp);
			synchronizationNewPatient(map, appUtils.getContextdb3(), appUtils.getUrldb3(),idfoldertmp);
//			if(true)	return;
//			if(i==10) break;
		}
	}
	@Transactional
	public void updatePatientenJohannisplatz(AppUtils appUtils) {
		logger.debug("-------BEGIN-------");
//		this.appUtils = appUtils;
//		stationName = appUtils.getContextdb2() ;
		int stgeorg_maxPatiantId = maxPatiantId("tmp",clinconJdbc);
//		int praxisluisa_maxPatiantId = maxPatiantId("praxisluisa",clinconJdbc);
//		int praxisluisa_maxPatiantId = 1488332;05.14
		int praxisluisa_maxPatiantId = 14892137;//05.23
		logger.debug("praxisluisa_maxPatiantId = "+praxisluisa_maxPatiantId);
		copyPatienten2tmp("tmp",stgeorg_maxPatiantId,db1stGeorgJdbc);
		copyPatienten2tmp("praxisluisa",praxisluisa_maxPatiantId,db2johanisPlatzJdbc);
//		copyPatientenWithoutUrl2tmp(stM);
		copyNewPatientdb1to2(appUtils);
		logger.debug("-------END-------"+stgeorg_maxPatiantId);
	}
	private void copyNewPatientdb1to2(AppUtils appUtils) {
		String sql=" SELECT pj.*, ps.idpatient AS psidpatient FROM praxisLuisa.patient pj LEFT JOIN tmp.patient ps " +
		" ON (lower(pj.forename)=lower(ps.forename) " +
		" AND pj.patient=ps.patient " +
		" AND pj.sex=ps.sex " +
		" AND CAST(pj.birthdate AS date)=CAST(ps.birthdate AS date) " +
		" ) " +
		" WHERE " +
		" ps.forename IS NULL AND " +
		" pj.idfolder=? ";
		logger.debug(sql+idFolder);
		List<Map<String, Object>> newPatientsList = clinconJdbc.queryForList(sql,idFolder);
		logger.debug(newPatientsList.size());
		int i=0;
		for (Map<String, Object> map : newPatientsList) {
			i++;
			logger.debug(map);
			synchronizationNewPatient(map,appUtils.getContextdb2(),appUtils.getUrldb2(),idFolder);
			if(true)	return;
			if(i==10)
				break;
		}
	}
	public void updatePatienten_old(AppUtils appUtils) {
		logger.debug("-------BEGIN-------");
//		this.appUtils = appUtils;
//		stationName = appUtils.getContextdb2() ;
		Map stM=new HashMap();
//		int stgeorg_maxPatiantId = maxPatiantId("tmp",simpleJdbc);
		int praxisluisa_maxPatiantId = maxPatiantId("praxisluisa",clinconJdbc);
//		logger.debug("-------END-------"+stgeorg_maxPatiantId);
		logger.debug("praxisluisa_maxPatiantId = "+praxisluisa_maxPatiantId);
		if(true) return;
//		copyPatientenWithoutUrl2tmp(stM);
		copyPatienten2tmp("praxisluisa",praxisluisa_maxPatiantId,db2johanisPlatzJdbc);
		copyNewPatientUrldb1to2();
//		copyNewPatientdb1to2();
//		String sourcePatienten=" SELECT patient, forename, sex, birthdate, idpatient FROM tmp.patient ";
		String dbschema = "tmp";
		String sourcePatienten=" SELECT tp.patient, forename, sex, birthdate, idpatient " +
		" FROM " +dbschema +".patient AS tp LEFT JOIN patientows1stgeorg  ON tp.idpatient=ows1stgeorg " +
		" WHERE ows1stgeorg IS NULL ";
//		copyTmp2patienten(stM,sourcePatienten);
//		copyPatientenHistory2tmp(stM);
		logger.debug("-------END-------");
	}
	private void copyNewPatientUrldb1to2() {
		String sqlPatientenWithoutUrl="SELECT ps.idpatient AS idp, pj.idpatient AS idpOld " +
		" FROM tmp.patient ps, praxisLuisa.patient  pj " +
		" WHERE " +
		" lower(pj.forename)=lower(ps.forename) AND pj.patient=ps.patient  AND pj.sex=ps.sex " +
		" AND CAST(pj.birthdate AS date)=CAST(ps.birthdate AS date) " +
		" ORDER BY idp ";
		logger.debug(sqlPatientenWithoutUrl);
		List<Map<String, Object>> newPatientsList = clinconJdbc.queryForList(sqlPatientenWithoutUrl);
		logger.debug(newPatientsList.size());
		int i=0;
		for (Map<String, Object> map : newPatientsList) {
			i++;
			logger.debug(map);
			Integer idpatient=(Integer) map.get("idp");
			Integer idpatientOld=(Integer) map.get("idpOld");
			logger.debug(idpatient+"/"+idpatientOld);
//			addUrl2patient(idpatientOld, idpatient,stationName );
//			addUrl2patient(idpatientOld, idpatient,appUtils.getUrldb2());
			if(i==50)
				break;
		}
	}
//	private void addUrl2patient(Integer idpatientInOldDB, Integer idPatient, String stationName) {
	private void addUrl2patient(Integer idpatientInOldDB, Integer idPatient,String urldb) {
		int patientUrlId=nextId(db1stGeorgJdbc);
		logger.debug("patientUrlId="+patientUrlId);
		db1stGeorgJdbc.update("INSERT INTO tree (id, tab_name, did, idclass,iddoc,sort) " +
				" VALUES (?,?,?,?,?,?) ",patientUrlId,"url",idPatient,patientUrlId,idPatient,patientUrlId);
		db1stGeorgJdbc.update("INSERT INTO url (idurl, url, text) " +
				" VALUES (?,?,?) ",patientUrlId, (String)urldb+idpatientInOldDB, "link");
//		" VALUES (?,?,?) ",patientUrlId,"http://localhost:8080/" +stationName +"/patient="+idpatientInOldDB,"link");
	}

	private void synchronizationNewPatient(Map<String, Object> map, String stationName,String urldb,int idFolder4patient) {
		Integer psidpatient=(Integer) map.get("psidpatient");
		Integer idpatientInOldDB=(Integer) map.get("idpatient");
		logger.debug("psidpatient="+psidpatient);
		logger.debug("stationName="+stationName);
//		if(true)			return;
		Integer newPatientId=psidpatient;
		if(null==psidpatient||0==psidpatient)
			newPatientId = insertNewPatient(map,idFolder4patient);
		String sql = "SELECT * FROM tree WHERE tab_name='url' AND did=?";
		List<Map<String, Object>> patientUrlList = db1stGeorgJdbc.queryForList(sql,newPatientId);
		for (Map<String, Object> map2 : patientUrlList) {
			String url = (String) map2.get("url");
			logger.debug("url="+url+" contains "+stationName);
			if(stationName.contains(url))
				return;
		}
		//Insert url to other DB.
//		addUrl2patient(idpatientInOldDB, newPatientId,stationName );
//		addUrl2patient(idpatientInOldDB, newPatientId,appUtils.getUrldb2() );
		addUrl2patient(idpatientInOldDB, newPatientId,urldb);
	}
	
	private int insertNewPatient(Map<String, Object> map,int idFolder4patient) {
		int newPatientId;
		newPatientId=nextId(db1stGeorgJdbc);
		logger.debug("newPatientId="+newPatientId);
		db1stGeorgJdbc.update("INSERT INTO tree (id, tab_name, did, idclass,iddoc,sort)" +
				" VALUES (?,?,?,?,?,?) ",newPatientId,"patient",idFolder4patient,newPatientId,newPatientId,newPatientId);
		Date mdate = Calendar.getInstance().getTime();
		db1stGeorgJdbc.update("INSERT INTO history (idhistory, mdate, idauthor)" +
				" VALUES (?,?,?) ",newPatientId,mdate,newPatientId);
		String patient=(String) map.get("patient");
		String forename=(String) map.get("forename");
		String sex=(String) map.get("sex");
		Date birthDate=(Date) map.get("birthdate");
		db1stGeorgJdbc.update("INSERT INTO patient (patient, forename, sex, birthdate,idpatient)" +
				" VALUES (?,?,?,?,?) ",patient,forename,sex,birthDate,newPatientId);
		return newPatientId;
	}
	private int nextId(JdbcTemplate simpleJdbc32) {
		return simpleJdbc32.queryForInt("SELECT * FROM nextval('dbid');");
	}
	public int lastCopydPatiantId(String dbschema) {
		int lastCopydPatientId = maxPatiantId(dbschema, clinconJdbc);
		return lastCopydPatientId;
	}
	private int maxPatiantId(String dbschema, JdbcTemplate simpleJdbc32) {
		String sql = "SELECT max(idpatient) FROM " +dbschema +".patient";
		logger.debug(sql);
		int maxPatiantId = simpleJdbc32.queryForInt(sql);
		logger.debug("maxPatiantId="+maxPatiantId);
		return maxPatiantId;
	}
	private void copyTmp2patienten(Map stM, String sourcePatienten) {
		logger.debug("-------BEGIN-------");
		List<Map<String, Object>> tmpPatientenList = clinconJdbc.queryForList(sourcePatienten);
		logger.debug("-------BEGIN-------"+tmpPatientenList.size());
		for (Map patientMap : tmpPatientenList) {
			String patient=(String) patientMap.get("patient");
			String forename=(String) patientMap.get("forename");
			String sex=(String) patientMap.get("sex");
			Date birthDate=(Date) patientMap.get("birthdate");
			Integer idpatient=(Integer) patientMap.get("idpatient");
			
			Patient patientO = new Patient(forename,patient,sex,birthDate);
			patientO.persist();
			Patientows1stgeorg patientows1stgeorg = new Patientows1stgeorg();
			patientows1stgeorg.setPatient(patientO);
			patientows1stgeorg.setOws1stgeorg(idpatient.longValue());
			patientows1stgeorg.persist();
			
		}
		logger.debug("-------END-------");
	}
	
	private void copyPatientenWithoutUrl2tmp(Map stM) {
		String patientenWithoutUrlSql="SELECT patient, forename, sex, birthdate, idpatient, patientT.did  AS idfolder " +
				" FROM tree AS patientT, patient " +
				" LEFT JOIN (SELECT * FROM tree,url WHERE idclass=idurl AND url LIKE '%johanisplatz%' ) AS tu " +
				" ON tu.did=idpatient " +
				" WHERE patientT.id=idpatient AND tu.did IS NULL " +
				" AND birthdate IS NOT NULL";
		logger.debug(patientenWithoutUrlSql);
		List<Map<String, Object>> copyPatientenList = db1stGeorgJdbc.queryForList(patientenWithoutUrlSql);
		logger.debug("delete BEGIN");
		clinconJdbc.update("DELETE FROM tmp.patient");
		logger.debug("copy BEGIN");
		for (Map<String, Object> map : copyPatientenList)
			clinconJdbc.update("INSERT INTO tmp.patient (patient, forename, sex, birthdate,idpatient,idfolder)" +
					" VALUES (?,?,?,?,?,?) ",map.values().toArray());
		logger.debug("END");
	}
	private void copyPatienten2tmp(String schemadb, int maxPatiantId, JdbcTemplate simpleJdbc32) {
		String sourcePatienten=
		"SELECT patient, forename, sex, birthdate, idpatient, patientT.did AS idfolder " +
		" FROM patient,tree patientT " +
		" WHERE idpatient=patientT.id " +
		" AND birthdate IS NOT NULL " +
		" AND idpatient>? " +
//		" AND patientT.did="+idFolder +
		"";
		logger.debug(sourcePatienten+" -- "+maxPatiantId);
		List<Map<String, Object>> copyPatientenList = simpleJdbc32.queryForList(sourcePatienten,maxPatiantId);
//		logger.debug("delete BEGIN");
//		simpleJdbc.update("DELETE FROM " +schemadb+".patient");
		logger.debug("copy BEGIN "+copyPatientenList.size());
		for (Map<String, Object> map : copyPatientenList)
			clinconJdbc.update("INSERT INTO " +schemadb +".patient (patient, forename, sex, birthdate,idpatient,idfolder)" +
			" VALUES (?,?,?,?,?,?) ",map.values().toArray());
		
		logger.debug("END");
	}
	public void setCopyPatientenList(List<Map<String, Object>> stPatientenList, Map stM) {
		stM.put("stPatientenList", stPatientenList);
		Map<Integer, Map<String, Object>> copyPatientenMap = new HashMap<Integer, Map<String,Object>>();
		Map<Integer,Integer> t2s= new HashMap<Integer, Integer>();
		int cntCopiedPatient=0, cntNotCopiedPatient=0;
		for (Map<String, Object> map : stPatientenList) {
			Integer ids =(Integer) map.get("ids");
			Integer idt =(Integer) map.get("idt");
			if(null==idt){
				cntNotCopiedPatient++;
			}else{
				cntCopiedPatient++;
				t2s.put(idt, ids);
			}
			copyPatientenMap.put(ids, map);
		}
		stM.put("t2s", t2s);
		stM.put("copyPatientenMap", copyPatientenMap);
		stM.put("cntCopiedPatient", cntCopiedPatient);
		stM.put("cntNotCopiedPatient", cntNotCopiedPatient);
	}
	private void copyPatientenHistory2tmp(Map stM) {
		clinconJdbc.update("DELETE FROM tmp.phtree");
		String sourcePatientenHistory=
				" SELECT " +
						" patientHistoryT.id ,patientHistoryT.tab_name ,patientHistoryT.did ,patientHistoryT.idclass" +
						" ,patientHistoryT.sort " +
						" FROM patient,tree patientHistoryT" +
						" WHERE idpatient=patientHistoryT.did" +
						" ORDER BY did,sort"+
						"";
		logger.debug(sourcePatientenHistory);
		List<Map<String, Object>> copyPatientenHistoryList = db1stGeorgJdbc.queryForList(sourcePatientenHistory);
		for (Map<String, Object> map : copyPatientenHistoryList)
			clinconJdbc.update("INSERT INTO tmp.phtree (id, tab_name, did, idclass,sort)" +
					" VALUES (?,?,?,?,?) ",map.values().toArray());
	}

}
