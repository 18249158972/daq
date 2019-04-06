package com.cmcc.andedu.microservice.module.syncdata.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_SYNC_RECORD")
public class SyncRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SYNC_RECORD")
	@SequenceGenerator(name = "SEQ_T_SYNC_RECORD", sequenceName = "SEQ_T_SYNC_RECORD", allocationSize = 1)
	private Long id;

	@Column(name = "FILE_NAME")
	private String fileName;
	@Column(name = "ORACLE_TABLE")
	private String oracleTable;
	@Column(name = "PRODUCE_TABLE")
	private String produceTable;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "XFSJC")
	private String xfsjc;
	@Column(name = "ORACLE_TIME")
	private Date oracleTime;
	@Column(name = "PRODUCE_TIME")
	private Date produceTime;
	@Column(name = "DESCRIBE")
	private String describe;
	@Column(name = "SC_STATUS")
	private int sc_status;

	public SyncRecord() {
	}

	public SyncRecord(Long id, String fileName, String oracleTable, String produceTable, String status, String xfsjc,
			int sc_status) {
		this.id = id;
		this.fileName = fileName;
		this.oracleTable = oracleTable;
		this.produceTable = produceTable;
		this.status = status;
		this.xfsjc = xfsjc;
		this.sc_status = sc_status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOracleTable() {
		return oracleTable;
	}

	public void setOracleTable(String oracleTable) {
		this.oracleTable = oracleTable;
	}

	public String getProduceTable() {
		return produceTable;
	}

	public void setProduceTable(String produceTable) {
		this.produceTable = produceTable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXfsjc() {
		return xfsjc;
	}

	public void setXfsjc(String xfsjc) {
		this.xfsjc = xfsjc;
	}

	public Date getOracleTime() {
		return oracleTime;
	}

	public void setOracleTime(Date oracleTime) {
		this.oracleTime = oracleTime;
	}

	public Date getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getSc_status() {
		return sc_status;
	}

	public void setSc_status(int sc_status) {
		this.sc_status = sc_status;
	}
	
	

}
