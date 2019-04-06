package com.cmcc.andedu.microservice.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 * @ClassName: UnZipUtils
 * @Description: zip解压工具
 * @author gaochao
 * @date 2019年4月1日
 */
public class UnZipUtils {
	final static Logger LOGGER = LoggerFactory.getLogger(UnZipUtils.class);
	public static void main(String[] args) throws IOException {
		
	}

	/**
	 * @param dir
	 *            需要删除的文件目录
	 * @param doNotDelfileName
	 *            文件目录下不需要删除的文件
	 */
	public static void deleteFile(File dir, String doNotDelfileName) {
		if (dir.exists()) { // 判断传入的File对象是否存在
			File[] files = dir.listFiles(); // 得到File数组
			for (File file : files) { // 遍历所有的子目录和文件
				if (file.isDirectory()) {
					deleteFile(file, doNotDelfileName); // 如果是目录递归调用deleteFile()
				} else {
					if (!file.getName().equalsIgnoreCase(doNotDelfileName)) {// 排除不需要删除的文件
						file.delete(); // 如果是文件，直接删除
					}
				}
			}
			// dir.delete(); // 删除完一个目录的所有文件后，就删除这个目录
		} else {
		}

	}

	/**
	 * @param filename
	 *            删除的文件名，包括路径
	 */
	public static void delFile(String filename) {
		File file = new File(filename);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/**
	 * @param source
	 *            原始文件路径
	 * @param dest
	 *            目标地址
	 */
	public static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
		FileUtils.copyFile(source, dest);
	}

	/**
	 * @param source
	 *            原始文件路径
	 * @param dest
	 *            解压路径
	 * @param password
	 *            解压文件密码(可以为空)
	 */
	public void unZip(String source, String dest, String password) {
		try {
			File zipFile = new File(source);

			ZipFile zFile = new ZipFile(zipFile); // 首先创建ZipFile指向磁盘上的.zip文件

			zFile.setFileNameCharset("GBK");

			File destDir = new File(dest); // 解压目录
			if (!destDir.exists()) {// 目标目录不存在时，创建该文件夹
				destDir.mkdirs();
			}
			if (zFile.isEncrypted()) {
				if (password != null && password.length() > 0) {
					zFile.setPassword(password.toCharArray()); // 设置密码
				}
			}
			zFile.extractAll(dest); // 将文件抽出到解压目录(解压)

			List<net.lingala.zip4j.model.FileHeader> headerList = zFile.getFileHeaders();

			List<File> extractedFileList = new ArrayList<File>();

			for (FileHeader fileHeader : headerList) {

				if (!fileHeader.isDirectory()) {

					extractedFileList.add(new File(destDir, fileHeader.getFileName()));

				}

			}

			File[] extractedFiles = new File[extractedFileList.size()];

			extractedFileList.toArray(extractedFiles);

//			for (File f : extractedFileList) {
//
//				LOGGER.info(f.getAbsolutePath() + "文件解压成功!");
//
//			}

		} catch (ZipException e) {
			e.printStackTrace();
		}

	}
}
