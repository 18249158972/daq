package com.cmcc.andedu.microservice.module.syncdata.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;
@Service
public class MergeFileService {
	public int i =0 ;

	public boolean  writeFile(String filePath, BufferedWriter bw) {
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			String s;
			s = br.readLine();
			while (s != null) {
				bw.write(s);
				s = br.readLine();
				bw.newLine();// ����
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true ;

	}

	public void mergeFile(String orgFilePath, String destFileName) {
		 i =0 ;
		try {
			File f = new File(orgFilePath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(destFileName));
			getFile(f, orgFilePath, bw);
			bw.close();
			System.out.println("共合并："+i+"个数据文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  void getFile(File f, String orgFilePath, BufferedWriter bw) {
		if (f != null) {
			if (f.isDirectory()) {
				File[] fileArray = f.listFiles();
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						getFile(fileArray[i], orgFilePath, bw);
					}
				}
			} else {
				String fileName = f.getName();
				
				if (fileName.indexOf("AVG_1")>=0) {
					System.out.println(f.getPath() + "开始进行文件合并");
					if (writeFile(f.getPath(), bw)) {
						i=i+1;
						System.out.println(f.getPath() + "成功合并");
					}
					

				}
			}
		}
	}

	public static void main(String[] args) {

//		String orgFilePath = "E:\\file" + File.separator;
//		String destFileName = "E:\\NEW_SOP_MIR_UTMCA_AVG_1.txt";
//		mergeFile(orgFilePath, destFileName);

	}

}
