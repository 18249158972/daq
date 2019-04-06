package com.cmcc.andedu.microservice.module.syncdata.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmcc.andedu.microservice.module.common.Constants;
import com.cmcc.andedu.microservice.util.SftpUtils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;

@Service
public class DownFileService {
	final Logger LOGGER = LoggerFactory.getLogger(DownFileService.class);

	// 连接sftp
	public ChannelSftp connect() {
		String host = Constants.ftp_host;
		String port = Constants.ftp_port;
		String username = Constants.ftp_username;
		String password = Constants.ftp_password;
		return SftpUtils.connect(host, Integer.parseInt(port), username, password);
	}

	// 下载文件
	public boolean downFile(String syncTime) {
		boolean flag = false;
		String remotePath = Constants.ftp_remotePath + syncTime;
		String localPath = Constants.ftp_downfilePath + File.separator + syncTime;// 文件下载存放目录，每天一个文件夹，按日期存放
		// 需要下载的文件名称
		String downfileName = Constants.onefileName_pre + syncTime + Constants.onefileName_pos;
		LOGGER.info("SFTP服务器下载远程路径:" + remotePath);
		LOGGER.info("SFTP服务器下载本地路径" + localPath);
		LOGGER.info("SFTP服务器下载文件的名称" + downfileName);
		// 连接sftp
		ChannelSftp sftp = connect();
		Vector<LsEntry> listFiles = SftpUtils.listFiles(remotePath, sftp);
		// 获取压缩包文件名
		List<String> listNames = new ArrayList<String>();
		if (listFiles != null && !listFiles.isEmpty()) {
			for (LsEntry lsEntry : listFiles) {
				String filename = lsEntry.getFilename();
				if (filename != null && filename.equalsIgnoreCase(downfileName)) {
					listNames.add(filename);
				}
			}
		} else {
			SftpUtils.disconnect(sftp);
			LOGGER.info("SFTP服务器没有这一天的数据：" + syncTime);
			flag = false;
			return flag;
		}
		if (listNames.size() > 0) {
			File file = new File(localPath);
			if (!file.exists()) {
				file.mkdirs();
				LOGGER.info("SFTP服务器下载文件保存到本地，根据日期创建本地目录" + localPath);
			}
			for (String fileName : listNames) {
				LOGGER.info("SFTP服务器下载开始下载：" + fileName);
				SftpUtils.download(remotePath, fileName, localPath, fileName, sftp);
				flag = true;
			}
		} else {
			SftpUtils.disconnect(sftp);
			LOGGER.info("SFTP服务器没有这一天的数据：" + syncTime);
			flag = false;
			return flag;
		}
		SftpUtils.disconnect(sftp);
		return flag;

	}

	public static void main(String[] args) throws IOException {

	}

}
