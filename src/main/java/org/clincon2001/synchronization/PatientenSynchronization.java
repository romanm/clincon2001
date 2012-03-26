package org.clincon2001.synchronization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("patientenSynchronization")
public class PatientenSynchronization {
	@Autowired @Qualifier("simpleJdbc2")	private	JdbcTemplate	simpleJdbc2;
	@Autowired @Qualifier("simpleJdbc")		private	JdbcTemplate	simpleJdbc;
	private final Log logger = LogFactory.getLog(getClass());

	public void updatePatienten() {
		logger.debug("-------BEGIN-------");
		Map stM=new HashMap();
		copyPatienten2tmp(stM);
//		copyPatientenHistory2tmp(stM);
		logger.debug("-------END-------");
	}
	int idFolder=1048962;
	private void copyPatienten2tmp(Map stM) {
		String sourcePatienten="SELECT patient, forename, sex, birthdate, idpatient, patientT.did AS idfolder " +
		" FROM patient,tree patientT " +
		" WHERE idpatient=patientT.id " +
		"AND birthdate IS NOT NULL" +
//		" AND patientT.did="+idFolder +
		"";
		logger.debug(sourcePatienten);
		List<Map<String, Object>> copyPatientenList = simpleJdbc2.queryForList(sourcePatienten);
		logger.debug("delete BEGIN");
		simpleJdbc.update("DELETE FROM tmp.patient");
		logger.debug("copy BEGIN");
		for (Map<String, Object> map : copyPatientenList)
			simpleJdbc.update("INSERT INTO tmp.patient (patient, forename, sex, birthdate,idpatient,idfolder)" +
			" VALUES (?,?,?,?,?,?) ",map.values().toArray());
		
		logger.debug("END");
		String targetPatienten=" SELECT " +
" sp.patient, sp.forename, sp.sex, sp.birthdate, sp.idpatient AS ids, tp.idpatient AS idt " +
" FROM tmp.patient sp LEFT JOIN patient tp " +
" ON sp.patient=tp.patient AND sp.forename=tp.forename AND sp.sex=tp.sex " +
" AND CAST(sp.birthdate AS date)=CAST(tp.birthdate AS date) " +
"";
		logger.debug(targetPatienten);
//		setCopyPatientenList(simpleJdbc.queryForList(targetPatienten),stM);
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
		List<Map<String, Object>> copyPatientenHistoryList = simpleJdbc2.queryForList(sourcePatientenHistory);
		for (Map<String, Object> map : copyPatientenHistoryList)
			simpleJdbc.update("INSERT INTO tmp.phtree (id, tab_name, did, idclass,sort)" +
					" VALUES (?,?,?,?,?) ",map.values().toArray());
	}

}
