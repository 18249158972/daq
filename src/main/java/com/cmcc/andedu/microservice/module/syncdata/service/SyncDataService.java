package com.cmcc.andedu.microservice.module.syncdata.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.andedu.microservice.module.syncdata.dao.SyncDataDao;
import com.cmcc.andedu.microservice.module.syncdata.dao.SyncRecordDao;
import com.cmcc.andedu.microservice.module.syncdata.domain.SyncRecord;
import com.cmcc.andedu.microservice.util.DateUtil;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class SyncDataService {
	Logger LOGGER = Logger.getLogger("SyncDataService");
	@Autowired
	private SyncDataDao syncDataDao;
	@Autowired
	private SyncRecordDao syncRecordDao;

	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String c6;
	private String c7;
	private String c8;
	private String c9;
	private String c10;
	private String c11;
	private String c12;
	private String c13;
	private String c14;
	private String c15;
	private String c16;
	private String c17;
	private String c18;
	private String c19;
	private String c20;
	private String c21;
	private String c22;
	private String c23;
	private String c24;
	private String c25;
	private String c26;
	private String c27;
	private String c28;
	private String c29;
	private String c30;

	public static void main(String[] args) throws IOException {

	}

	// 获取等待同步的文件信息
	public List<SyncRecord> getWaitSyncRecordList() {
		List<SyncRecord> WaitSyncRecordList = new ArrayList<SyncRecord>();
		WaitSyncRecordList = syncRecordDao.getWaitSyncRecord();
		return WaitSyncRecordList;
	}

	// 修改同步状态，并添加下一天同步记录
	public synchronized void syncRecord(long id, String xfsjc, String fileName) {
		SyncRecord syncRecord = syncRecordDao.getOne(id);
		String str_OracleTable = syncRecord.getOracleTable();
		String str_ProduceTable = syncRecord.getProduceTable();
		syncRecord.setStatus("5");
		syncRecordDao.save(syncRecord);
		LOGGER.info("---------修改本次任务同步状态为：已成功完成-----------");
		// 插入下一批次等待的同步的记录

		String str_xfsjc = DateUtil.getNextDay(syncRecord.getXfsjc());
		LOGGER.info("---------计算下一次任务同步时间-----------" + fileName + " " + str_xfsjc);
		// 判断是否已经插入，如果没有再插入
		List<SyncRecord> syncRecordList = syncRecordDao.getSyncRecordByFileNameAndTime(fileName, str_xfsjc);
		LOGGER.info("---------判断下一批次的任务是否已经插入到数据库-----------");
		if (syncRecordList.size() <= 0) {
			LOGGER.info("---------下一批次的任务没有插入到数据库-----------");
			SyncRecord nextSyncRecord = new SyncRecord();
			nextSyncRecord.setFileName(fileName);
			nextSyncRecord.setOracleTable(str_OracleTable);
			nextSyncRecord.setProduceTable(str_ProduceTable);
			nextSyncRecord.setStatus("0");
			nextSyncRecord.setXfsjc(str_xfsjc);
			nextSyncRecord.setOracleTime(new Date());
			nextSyncRecord.setProduceTime(new Date());
			nextSyncRecord.setSc_status(0);
			syncRecordDao.save(nextSyncRecord);
			LOGGER.info("---------插入下一批次等待的同步的记录-----------" + fileName + " " + str_xfsjc);
		}

	}

	/**
	 * @param localPath
	 *            待同步的数据文件存放路径
	 * @param fileName
	 *            待同步的数据文件名称（包括扩展名称）
	 */
	public synchronized void syncData(long id, File file, String syncTime, String fileName) {
		LOGGER.info("---------获取数据文件内容-----------");

		List<Object[]> oList = new ArrayList<Object[]>();
		Long i = 0L;
		if (file.isFile() && file.exists()) {// 判断文件是否存在
			InputStreamReader isr = null;
			try {
				// isr = new InputStreamReader(new FileInputStream(file), "UTF-8");

				FileInputStream in = new FileInputStream(file);
				byte[] b = new byte[3];
				in.read(b);
				String code = "GBK";
				if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
					code = "UTF-8";
				}
				isr = new InputStreamReader(in, code);

				BufferedReader br = new BufferedReader(isr);
				String lineText = null;
				while ((lineText = br.readLine()) != null) {
					String[] strData = lineText.split(",");

					if (strData.length >= 1 && strData[0] != null) {
						c1 = strData[0].toString().trim();
					}

					if (strData.length >= 2 && strData[1] != null) {
						c2 = strData[1].toString().trim();
					}
					if (strData.length >= 3 && strData[2] != null) {
						c3 = strData[2].toString().trim();
					}
					if (strData.length >= 4 && strData[3] != null) {
						c4 = strData[3].toString().trim();
					}
					if (strData.length >= 5 && strData[4] != null) {
						c5 = strData[4].toString().trim();
					}
					if (strData.length >= 6 && strData[5] != null) {
						c6 = strData[5].toString().trim();
					}
					if (strData.length >= 7 && strData[6] != null) {
						c7 = strData[6].toString().trim();
					}
					if (strData.length >= 8 && strData[7] != null) {
						c8 = strData[7].toString().trim();
					}
					if (strData.length >= 9 && strData[8] != null) {
						c9 = strData[8].toString().trim();
					}
					if (strData.length >= 10 && strData[9] != null) {
						c10 = strData[9].toString().trim();
					}
					if (strData.length >= 11 && strData[10] != null) {
						c11 = strData[10].toString().trim();
					}
					if (strData.length >= 12 && strData[11] != null) {
						c12 = strData[11].toString().trim();
					}
					if (strData.length >= 13 && strData[12] != null) {
						c13 = strData[12].toString().trim();
					}
					if (strData.length >= 14 && strData[13] != null) {
						c14 = strData[13].toString().trim();
					}
					if (strData.length >= 15 && strData[14] != null) {
						c15 = strData[14].toString().trim();
					}
					if (strData.length >= 16 && strData[15] != null) {
						c16 = strData[15].toString().trim();
					}
					if (strData.length >= 17 && strData[16] != null) {
						c17 = strData[16].toString().trim();
					}
					if (strData.length >= 18 && strData[17] != null) {
						c18 = strData[17].toString().trim();
					}
					if (strData.length >= 19 && strData[18] != null) {
						c19 = strData[18].toString().trim();
					}
					if (strData.length >= 20 && strData[19] != null) {
						c20 = strData[19].toString().trim();
					}
					if (strData.length >= 21 && strData[20] != null) {
						c21 = strData[20].toString().trim();
					}
					if (strData.length >= 22 && strData[21] != null) {
						c22 = strData[21].toString().trim();
					}
					if (strData.length >= 23 && strData[22] != null) {
						c23 = strData[22].toString().trim();
					}
					if (strData.length >= 24 && strData[23] != null) {
						c24 = strData[23].toString().trim();
					}
					if (strData.length >= 25 && strData[24] != null) {
						c25 = strData[24].toString().trim();
					}
					if (strData.length >= 26 && strData[25] != null) {
						c26 = strData[25].toString().trim();
					}
					if (strData.length >= 27 && strData[26] != null) {
						c27 = strData[26].toString().trim();
					}
					if (strData.length >= 28 && strData[27] != null) {
						c28 = strData[27].toString().trim();
					}
					if (strData.length >= 29 && strData[28] != null) {
						c29 = strData[28].toString().trim();
					}
					if (strData.length >= 30 && strData[29] != null) {
						c30 = strData[29].toString().trim();
					}

					Object[] o = new Object[] { c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16,
							c17, c18, c19, c20, c21, c22, c23, c24, c25, c26, c27, c28, c29, c30, syncTime, fileName };
					oList.add(o);
					i++;

					if (oList != null && oList.size() == 5000) {// 每5000条记录提交一次
						syncDataDao.insertIntoData(oList);
						LOGGER.info(" oracle入库成功  " + i + "条数据");
						oList.clear();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							LOGGER.info("数据采集同步线程休眠出现异常" + e.toString());
						}
					}

				}
				if (oList != null && oList.size() > 0) {// 处理剩余的数据
					syncDataDao.insertIntoData(oList);
					LOGGER.info(" oracle入库成功  " + i + "条数据");
					oList.clear();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						LOGGER.info("数据采集同步线程休眠出现异常" + e.toString());
					}
				}
				LOGGER.info("数据同步 ORACLE入库成功");
				// 修改同步状态，并添加下一天同步记录
				syncRecord(id, syncTime, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (isr != null) {
					try {
						isr.close(); // 关闭流
						LOGGER.info("数据同步完毕，删除本地文件");
						file.delete();// 删除本地文件
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
