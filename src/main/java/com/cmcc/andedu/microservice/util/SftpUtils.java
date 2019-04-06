package com.cmcc.andedu.microservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Created by Shawn on 2015/4/23.
 */
public class SftpUtils {

	public static Logger LOG = LoggerFactory.getLogger(SftpUtils.class);

	public static ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect(1000 * 20);
			Channel channel = sshSession.openChannel("sftp");
			channel.connect(1000 * 20);
			sftp = (ChannelSftp) channel;
			LOG.info("Connected to " + host + ".");
		} catch (JSchException e) {
			LOG.error("error", e);
		}
		return sftp;
	}

	public static ChannelSftp connect1(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("aes128-ctr", "com.jcraft.jsch.jce.AES128CTR");
			config.put("aes192-ctr", "com.jcraft.jsch.jce.AES192CTR");
			config.put("aes256-ctr", "com.jcraft.jsch.jce.AES256CTR");
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			LOG.info("即将打开openChannel。。。。");
			Channel channel = session.openChannel("sftp");
			channel.connect();
			LOG.info("sftp服务器已连接。。。");
			sftp = (ChannelSftp) channel;
			Vector vector = sftp.ls("/upload/publish/");

			for (Object obj : vector) {
				if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
					String fileName = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getFilename();
					LOG.info(fileName);
				}
			}
		} catch (Exception e) {
			LOG.error("error", e);
		}
		return sftp;
	}

	/**
	 * @Description: 关闭连接
	 * @author lizq
	 * @date 2018年3月27日 下午7:17:51
	 * @param sftp
	 */
	public static void disconnect(ChannelSftp sftp) {
		if (null != sftp) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		}

	}

	public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
		FileInputStream in = null;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			in = new FileInputStream(file);
			sftp.put(in, file.getName());
			LOG.info("sftp文件  uploadFile 上传完成！");
		} catch (Exception e) {
			LOG.error("sftp文件上传upload", e);
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error("关闭文件时出错!", e);
			}

		}
	}

	public static void upload(String directory, String[] uploadFiles, ChannelSftp sftp) {
		FileInputStream in = null;
		try {
			sftp.cd(directory);

			for (String uploadFile : uploadFiles) {
				File file = new File(uploadFile);
				in = new FileInputStream(file);
				sftp.put(in, file.getName());
			}

		} catch (Exception e) {
			LOG.error("error", e);
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error("关闭文件时出错!", e);
			}

		}
	}

	/**
	 * 
	 * @Description: 单个文件下载
	 * @author lizq
	 * @date 2018年3月27日 下午6:47:36
	 * @param remotePath
	 *            远程文件路径
	 * @param remoteFileName
	 *            远程文件名
	 * @param localPath
	 *            本地文件
	 * @param localFileName
	 *            本地文件名
	 * @param sftp
	 *            数据连接
	 */
	public static boolean download(String remotePath, String remoteFileName, String localPath, String localFileName,
			ChannelSftp sftp) {
		boolean flag = false;
		FileOutputStream out = null;
		InputStream is = null;
		try {
			LOG.info("ftp远程当前目录：" + sftp.pwd());
			sftp.cd(remotePath);
			File file = new File(localPath + File.separator + localFileName);
			out = new FileOutputStream(file);
			is = sftp.get(remoteFileName);
			byte[] b = new byte[1024];
			StringBuffer sb = new StringBuffer();
			int len = 0;
			while ((len = is.read(b)) != -1) {
				sb.append(b);
				out.write(b, 0, len);
			}
			flag = true;

		} catch (Exception e) {
			LOG.error("error", e);
			flag = false;
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				flag = false;
				LOG.error("关闭文件时出错! download close error", e);
			}
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 删除文件
	 * @author lizq
	 * @date 2018年3月27日 下午5:22:16
	 * @param directory
	 *            文件目录
	 * @param deleteFile
	 *            要删除的文件名
	 * @param sftp
	 */
	public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
			LOG.info("/out目录下文件已删除");
		} catch (SftpException e) {
			LOG.error("error", e);
		}
	}

	/**
	 * 
	 * @Description: 获取文件列表
	 * @author lizq
	 * @date 2018年3月27日 下午7:06:35
	 * @param directory
	 * @param sftp
	 * @return
	 */
	public static Vector<ChannelSftp.LsEntry> listFiles(String directory, ChannelSftp sftp) {
		Vector<ChannelSftp.LsEntry> vector = null;
		try {
			vector = sftp.ls(directory);
			LOG.info(directory + "目录下的文件数量" + vector.size());
		} catch (SftpException e) {
			LOG.info("-------------没有查询到远程目录下的文件，请手动调整调整t_sync_record数据文件同步表的日期或联系FTP文件提供方------------");
		}
		return vector;
	}

	public static void rename(String oldpath, String newpath, ChannelSftp sftp) {
		try {
			sftp.rename(oldpath, newpath);
		} catch (SftpException e) {
			LOG.error("error", e);
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * ChannelSftp sftp = connect("112.35.8.5",22,"aeduftp","58*^sdfV");
		 * Vector<LsEntry> listFiles =
		 * SftpUtils.listFiles("/data01/ebosshome/eboss/fileshare/aedu/out", sftp);
		 * if(listFiles == null || listFiles.isEmpty()){ System.out.println("kong");
		 * return; } for (LsEntry lsEntry : listFiles) {
		 * System.out.println(lsEntry.getFilename()); }
		 */
	}
}
