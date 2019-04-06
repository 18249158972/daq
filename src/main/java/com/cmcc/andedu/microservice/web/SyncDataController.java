package com.cmcc.andedu.microservice.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.andedu.microservice.module.syncdata.service.DataSynThread;
import com.cmcc.andedu.microservice.module.syncdata.service.DownFileService;
import com.cmcc.andedu.microservice.module.syncdata.service.SyncDataService;
import com.cmcc.andedu.microservice.module.syncdata.service.UnZipFileService;
import com.cmcc.andedu.microservice.util.ResultMsg;

@Controller
@RequestMapping(value = "/sync")
public class SyncDataController {
	final Logger LOGGER = LoggerFactory.getLogger(SyncDataController.class);

	@Resource(name = "taskExecutor")
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	DownFileService downFileService;

	@Autowired
	private SyncDataService syncDataService;

	@Autowired
	private UnZipFileService unZipFileService;

	// 启动同步程序
	@RequestMapping(value = "/syncData")
	public @ResponseBody ResultMsg syncData(HttpServletRequest request) {
		taskExecutor.execute(new DataSynThread(downFileService,unZipFileService,syncDataService));
		return ResultMsg.msg(0l, "启动同步程序成功!");
		

	}

	// //启动定时同步程序
	// @RequestMapping(value = "/timerSyncData")
	// public @ResponseBody ResultMsg timerSyncData(HttpServletRequest request) {
	//
	// timerSyncData();
	// return ResultMsg.msg(0l, "成功!");
	//
	//
	// }

	// 定时同步数据
	// public void timerSyncData() {
	// LOGGER.info("定时同步数据开始工作");
	// Calendar calendar = Calendar.getInstance();
	// calendar.set(Calendar.HOUR_OF_DAY, 23); // 控制时
	// calendar.set(Calendar.MINUTE, 12); // 控制分
	// calendar.set(Calendar.SECOND, 0); // 控制秒
	//
	// Date time = calendar.getTime(); // 得出执行任务的时间,此处为今天的12：00：00
	//
	// Timer timer = new Timer();
	// timer.scheduleAtFixedRate(new TimerTask() {
	// public void run() {
	// LOGGER.info("定时同步程序调用数据同步进程");
	// Thread thread = new Thread(new
	// DataSynThread(downFileService,unZipFileService,syncDataService));
	// thread.start();
	// LOGGER.info("DataSynThread数据同步进程开始启动");
	//
	//
	// }
	// }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	// }

	public static void main(String[] args) {
		;

	}

}
