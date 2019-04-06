package com.cmcc.andedu.microservice.module.syncdata.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmcc.andedu.microservice.module.syncdata.domain.SyncRecord;

@Repository
public interface SyncRecordDao extends JpaRepository<SyncRecord, Long> {
	@Query("SELECT new SyncRecord( id,fileName, oracleTable, produceTable,status,xfsjc,sc_status) FROM SyncRecord  WHERE status= 0")
	public List<SyncRecord> getWaitSyncRecord();

	@Query("SELECT new SyncRecord( id,fileName, oracleTable, produceTable,status,xfsjc,sc_status) FROM SyncRecord  WHERE fileName =?1 and xfsjc=?2 ")
	public List<SyncRecord> getSyncRecordByFileNameAndTime(String fileName, String xfsjc);
}
