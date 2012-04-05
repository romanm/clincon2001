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
	@Autowired @Qualifier("simpleJdbc3")	private	JdbcTemplate	johanisPlatzJdbc;
	@Autowired @Qualifier("simpleJdbc2")	private	JdbcTemplate	stGeorgJdbc;
	@Autowired @Qualifier("simpleJdbc")		private	JdbcTemplate	simpleJdbc;
	private final Log logger = LogFactory.getLog(getClass());
	String stationName = "johanisplatz";
	int idFolder=1048962;
	@Transactional
	public void updatePatienten() {
		logger.debug("-------BEGIN-------");
		Map stM=new HashMap();
//		int stgeorg_maxPatiantId = maxPatiantId("tmp",simpleJdbc);
		int praxisluisa_maxPatiantId = maxPatiantId("praxisluisa",simpleJdbc);
//		logger.debug("-------END-------"+stgeorg_maxPatiantId);
		logger.debug("-------END-------"+praxisluisa_maxPatiantId);
		copyPatientenWithoutUrl2tmp(stM);
		copyPatienten2tmp(stM,"praxisluisa",praxisluisa_maxPatiantId,johanisPlatzJdbc);
		copyNewPatientUrldb1to2();
//		copyNewPatientdb1to2();
//		String sourcePatienten=" SELECT patient, forename, sex, birthdate, idpatient FROM tmp.patient ";
		String dbschema = "tmp";
		String sourcePatienten=" SELECT tp.patient, forename, sex, birthdate, idpatient " +
				" FROM " +dbschema +".patient AS tp LEFT JOIN patientows1stgeorg  ON tp.idpatient=ows1stgeorg " +
				" WHERE ows1stgeorg IS NULL";
//		copyTmp2patienten(stM,sourcePatienten);
//		copyPatientenHistory2tmp(stM);
		logger.debug("-------END-------");
	}
	private void copyNewPatientUrldb1to2() {
		String sqlPatientenWithoutUrl="SELECT ps.idpatient AS idp, pj.idpatient AS idpOld FROM tmp.patient ps, praxisLuisa.patient  pj" +
				" WHERE" +
				" lower(pj.forename)=lower(ps.forename)  AND pj.patient=ps.patient  AND pj.sex=ps.sex " +
				" AND CAST(pj.birthdate AS date)=CAST(ps.birthdate AS date) " +
				" ORDER BY idp";
		logger.debug(sqlPatientenWithoutUrl);
		List<Map<String, Object>> newPatientsList = simpleJdbc.queryForList(sqlPatientenWithoutUrl);
		logger.debug(newPatientsList.size());
		int i=0;
		for (Map<String, Object> map : newPatientsList) {
			i++;
			logger.debug(map);
			Integer idpatient=(Integer) map.get("idp");
			Integer idpatientOld=(Integer) map.get("idpOld");
			logger.debug(idpatient+"/"+idpatientOld);
			addUrl2patient(idpatientOld, idpatient,stationName );
			if(i==50)
				break;
		}
	}
	private void addUrl2patient(Integer idpatientInOldDB, Integer idPatient, String stationName) {
		int patientUrlId=nextId(stGeorgJdbc);
		logger.debug("patientUrlId="+patientUrlId);
		stGeorgJdbc.update("INSERT INTO tree (id, tab_name, did, idclass,iddoc,sort)" +
				" VALUES (?,?,?,?,?,?) ",patientUrlId,"url",idPatient,patientUrlId,idPatient,patientUrlId);
		stGeorgJdbc.update("INSERT INTO url (idurl, url, text)" +
				" VALUES (?,?,?) ",patientUrlId,"http://localhost:8080/" +stationName +"/patient="+idpatientInOldDB,"link");
	}
	private void copyNewPatientdb1to2() {
		String sql="SELECT pj.*, ps.idpatient AS psidpatient FROM praxisLuisa.patient pj LEFT JOIN tmp.patient ps " +
				" ON (lower(pj.forename)=lower(ps.forename) " +
				" AND pj.patient=ps.patient " +
				" AND pj.sex=ps.sex " +
				" AND CAST(pj.birthdate AS date)=CAST(ps.birthdate AS date) " +
				" ) " +
				" WHERE " +
				" ps.forename IS NULL AND " +
				" pj.idfolder=?";
		logger.debug(sql+idFolder);
		List<Map<String, Object>> newPatientsList = simpleJdbc.queryForList(sql,idFolder);
		int i=0;
		for (Map<String, Object> map : newPatientsList) {
			i++;
			logger.debug(map);
			synchronizationNewPatient(map);
			if(i==10)
				break;
		}
		
//		if(newPatientsList.size()>0)
//		{
//			Map<String, Object> map = newPatientsList.get(0);
//			logger.debug(map);
//			synchronizationNewPatient(map);
//		}
	}
	
	private void synchronizationNewPatient(Map<String, Object> map) {
		Integer psidpatient=(Integer) map.get("psidpatient");
		Integer idpatientInOldDB=(Integer) map.get("idpatient");
		logger.debug("psidpatient="+psidpatient);
//		if(true)			return;
		Integer newPatientId=psidpatient;
		if(null==psidpatient||0==psidpatient)
			newPatientId = insertNewPatient(map);
		String sql = "SELECT * FROM tree WHERE tab_name='url' AND did=?";
		List<Map<String, Object>> patientUrlList = stGeorgJdbc.queryForList(sql,newPatientId);
		for (Map<String, Object> map2 : patientUrlList) {
			String url = (String) map2.get("url");
			if(stationName.contains(url))
				return;
		}
		//Insert url to other DB.
		addUrl2patient(idpatientInOldDB, newPatientId,stationName );
	}
	
	private int insertNewPatient(Map<String, Object> map) {
		int newPatientId;
		newPatientId=nextId(stGeorgJdbc);
		logger.debug("newPatientId="+newPatientId);
		stGeorgJdbc.update("INSERT INTO tree (id, tab_name, did, idclass,iddoc,sort)" +
				" VALUES (?,?,?,?,?,?) ",newPatientId,"patient",idFolder,newPatientId,newPatientId,newPatientId);
		Date mdate = Calendar.getInstance().getTime();
		stGeorgJdbc.update("INSERT INTO history (idhistory, mdate, idauthor)" +
				" VALUES (?,?,?) ",newPatientId,mdate,newPatientId);
		String patient=(String) map.get("patient");
		String forename=(String) map.get("forename");
		String sex=(String) map.get("sex");
		Date birthDate=(Date) map.get("birthdate");
		stGeorgJdbc.update("INSERT INTO patient (patient, forename, sex, birthdate,idpatient)" +
				" VALUES (?,?,?,?,?) ",patient,forename,sex,birthDate,newPatientId);
		return newPatientId;
	}
	private int nextId(JdbcTemplate simpleJdbc32) {
		return simpleJdbc32.queryForInt("SELECT * FROM nextval('dbid');");
	}
	private int maxPatiantId(String dbschema, JdbcTemplate simpleJdbc32) {
		return simpleJdbc32.queryForInt("SELECT max(idpatient) FROM " +dbschema +".patient");
	}
	private void copyTmp2patienten(Map stM, String sourcePatienten) {
		logger.debug("-------BEGIN-------");
		List<Map<String, Object>> tmpPatientenList = simpleJdbc.queryForList(sourcePatienten);
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
		List<Map<String, Object>> copyPatientenList = stGeorgJdbc.queryForList(patientenWithoutUrlSql);
		logger.debug("delete BEGIN");
		simpleJdbc.update("DELETE FROM tmp.patient");
		logger.debug("copy BEGIN");
		for (Map<String, Object> map : copyPatientenList)
			simpleJdbc.update("INSERT INTO tmp.patient (patient, forename, sex, birthdate,idpatient,idfolder)" +
					" VALUES (?,?,?,?,?,?) ",map.values().toArray());
		logger.debug("END");
	}
	private void copyPatienten2tmp(Map stM, String schemadb, int maxPatiantId, JdbcTemplate simpleJdbc32) {
		String sourcePatienten="SELECT patient, forename, sex, birthdate, idpatient, patientT.did AS idfolder " +
		" FROM patient,tree patientT " +
		" WHERE idpatient=patientT.id " +
		" AND birthdate IS NOT NULL " +
		" AND idpatient>? " +
//		" AND patientT.did="+idFolder +
		"";
		logger.debug(sourcePatienten);
		List<Map<String, Object>> copyPatientenList = simpleJdbc32.queryForList(sourcePatienten,maxPatiantId);
//		logger.debug("delete BEGIN");
//		simpleJdbc.update("DELETE FROM " +schemadb+".patient");
		logger.debug("copy BEGIN");
		for (Map<String, Object> map : copyPatientenList)
			simpleJdbc.update("INSERT INTO " +schemadb +".patient (patient, forename, sex, birthdate,idpatient,idfolder)" +
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
		simpleJdbc.update("DELETE FROM tmp.phtree");
		String sourcePatientenHistory=
				" SELECT " +
						" patientHistoryT.id ,patientHistoryT.tab_name ,patientHistoryT.did ,patientHistoryT.idclass" +
						" ,patientHistoryT.sort " +
						" FROM patient,tree patientHistoryT" +
						" WHERE idpatient=patientHistoryT.did" +
						" ORDER BY did,sort"+
						"";
		logger.debug(sourcePatientenHistory);
		List<Map<String, Object>> copyPatientenHistoryList = stGeorgJdbc.queryForList(sourcePatientenHistory);
		for (Map<String, Object> map : copyPatientenHistoryList)
			simpleJdbc.update("INSERT INTO tmp.phtree (id, tab_name, did, idclass,sort)" +
					" VALUES (?,?,?,?,?) ",map.values().toArray());
	}

}
