package com.cmcc.andedu.microservice.module.syncdata.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmcc.andedu.microservice.module.common.Constants;
import com.cmcc.andedu.microservice.module.syncdata.domain.SyncRecord;
import com.cmcc.andedu.microservice.util.DateUtil;

/**
 * @author gaochao
 * @Description: {从银行SFTP服务器采集数据到平台的ORACLE}
 * @date 2019-4-2
 */

public class DataSynThread implements Runnable {

	final static Logger LOGGER = LoggerFactory.getLogger(DataSynThread.class);

	private DownFileService downFileService;
	private UnZipFileService unZipFileService;
	private SyncDataService syncDataService;

	public DataSynThread() {
		super();
	}

	public DataSynThread(DownFileService downFileService, UnZipFileService unZipFileService,
			SyncDataService syncDataService) {
		this.downFileService = downFileService;
		this.unZipFileService = unZipFileService;
		this.syncDataService = syncDataService;
	}

	@Override
	public void run() {
		while (true) {
			LOGGER.info("---------数据同步线程开始进入工作-----------");
			List<SyncRecord> WaitSyncRecordList = new ArrayList<SyncRecord>();
			WaitSyncRecordList = syncDataService.getWaitSyncRecordList();
			LOGGER.info("---------发现等待同步的任务:-----------" + WaitSyncRecordList.size() + "个");
			for (SyncRecord syncRecord : WaitSyncRecordList) {
				Long id = syncRecord.getId();
				String syncTime = syncRecord.getXfsjc();
				String fileName = syncRecord.getFileName();
				// 下载数据
				LOGGER.info("---------开始下载数据文件-----------");
				if (downFileService.downFile(DateUtil.getFormatDay(syncTime))) {
					// 解压文件
					LOGGER.info("---------开始解压文件-----------");
					File syncFile = unZipFileService.unZipFile(DateUtil.getFormatDay(syncTime), fileName);
					// 同步数据
					LOGGER.info("---------开始同步数据-----------");
					LOGGER.info("---------开始同步文件名称:-----------" + fileName);
					if (syncFile != null) {
						syncDataService.syncData(id, syncFile, syncTime, fileName);
					}
					LOGGER.info("---------同步数据完毕-----------");
				}
			}
			LOGGER.info("---------没有等待同步的任务-----------");
			// 数据同步完毕，进入休眠，休眠时间可以根据实际需要进行调整
			try {
				LOGGER.info("---------数据同步线程开始进入休眠-----------");
				Thread.sleep(Constants.Threadsleep_time);// 1000表示一秒，可以根据实际业务需要进行调整
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LOGGER.info("数据采集同步线程休眠出现异常" + e.toString());
			}
		}

	}

	public void loadFile(String fileName, String localPath) {

	}

}
