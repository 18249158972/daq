package com.cmcc.andedu.microservice.module.syncdata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SyncDataDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int[] insertIntoData(List<Object[]> oList) {
		String sql = "INSERT INTO T_SYNC_DATA"
				+ " (c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,c26,c27,c28,c29,c30,CREATE_TIME,SYNC_TIME,FILE_NAME)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)";
		return this.jdbcTemplate.batchUpdate(sql, oList);
	}


}
