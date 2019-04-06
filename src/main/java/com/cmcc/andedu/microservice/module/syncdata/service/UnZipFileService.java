package com.cmcc.andedu.microservice.module.syncdata.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmcc.andedu.microservice.module.common.Constants;
import com.cmcc.andedu.microservice.util.UnZipUtils;

@Service
public class UnZipFileService {
	final static Logger LOGGER = LoggerFactory.getLogger(UnZipFileService.class);

	public static void main(String args[]) {

	}

	public File unZipFile(String syncTime, String impdataFileName) {
		// 压缩包的存放位置
		String filePath = Constants.ftp_downfilePath + File.separator + syncTime;
		LOGGER.info("---------解压压缩包-----------" + filePath);
		// 压缩包第一层需要解压的文件名称
		// 文件名称拼接，文件前缀+文件中的日期+文件名称后缀，须区分大小写及空格

		String onefileName = Constants.onefileName_pre + syncTime + Constants.onefileName_pos;
		String onepassword = Constants.onepassword;
		// 压缩包第二层需要解压的文件名称
		String twofileName = Constants.twofileName_pre + syncTime + Constants.twosfileName_pos;
		String twopassword = Constants.twopassword;
		// 需要导入的数据文件名称地址
		String impdataFileDest = Constants.impdataFileDest + File.separator + syncTime;
		File file = new File(impdataFileDest);
		if (!file.exists()) {
			file.mkdirs();
			LOGGER.info("为数据导入创建本地目录" + impdataFileDest);
		}

		UnZipUtils z = new UnZipUtils();
		String source1 = filePath + File.separator + onefileName;
		String dest1 = filePath;
		// 第一层解压
		z.unZip(source1, dest1, onepassword);
		LOGGER.info("---------解压第一层文件夹-----------" + source1);
		// 第二层解压
		String source2 = filePath + File.separator + twofileName;
		String dest2 = filePath + File.separator;
		z.unZip(source2, dest2, twopassword);
		LOGGER.info("---------解压第二层文件夹-----------" + source2);
		// 复制文件到待同步到数据库的文件夹中
		File source = new File(filePath + File.separator + impdataFileName);
		String str_dest = impdataFileDest + File.separator + impdataFileName;
		File dest = new File(str_dest);
		try {
			z.copyFileUsingApacheCommonsIO(source, dest);
			LOGGER.info("---------复制文件到数据导入文件夹中-----------");
		} catch (IOException e) {
			dest = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 删除包下所有文件，排除第二层的压缩包文件
		File twofile = new File(filePath);
		z.deleteFile(twofile, twofileName);

		return dest;

	}

}
