package com.cmcc.andedu.microservice.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * 多文件压缩与解压
 * 
 * @author Supreme_Sir
 */

public class TarUtil {
	private final static int BUFFER = 1048576;

	/**
	 * 解压tar.gz文件
	 * 
	 * @param tar_gz
	 * @param sourceFolder
	 */
	public static List<String> decompress(File tar_gz, String sourceFolder) {
		List<String> fileNames = new ArrayList<String>();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		GZIPInputStream gzis = null;
		TarArchiveInputStream tais = null;
		OutputStream out = null;
		try {
			fis = new FileInputStream(tar_gz);
			bis = new BufferedInputStream(fis);
			gzis = new GZIPInputStream(bis);
			tais = new TarArchiveInputStream(gzis);
			TarArchiveEntry tae = null;
			boolean flag = false;
			while ((tae = tais.getNextTarEntry()) != null) {
				File tmpFile = new File(sourceFolder + tae.getName());
				fileNames.add(tae.getName());
				if (!flag) {
					// 使用 mkdirs 可避免因文件路径过多而导致的文件找不到的异常
					new File(tmpFile.getParent()).mkdirs();
					flag = true;
				}
				out = new FileOutputStream(tmpFile);
				int length = 0;
				byte[] b = new byte[BUFFER];
				while ((length = tais.read(b)) != -1) {
					out.write(b, 0, length);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (tais != null)
					tais.close();
				if (gzis != null)
					gzis.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileNames;
	}

	public static File pack(File[] sources, File target) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(target);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		TarArchiveOutputStream os = new TarArchiveOutputStream(out, "UTF-8");
		for (File file : sources) {
			try {
				TarArchiveEntry entry = new TarArchiveEntry(file.getName());
				entry.setSize(file.length());
				os.putArchiveEntry(entry);
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, os);
				fileInputStream.close();
				os.closeArchiveEntry();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	/**
	 *
	 * @Title: compress @Description: 将文件用gzip压缩 @param source 需要压缩的文件 @return
	 *         File 返回压缩后的文件 @throws
	 */
	public static File compress(File source) {
		File target = new File("d:\\" + source.getName() + ".gz");
		FileInputStream in = null;
		GZIPOutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new GZIPOutputStream(new FileOutputStream(target));
			byte[] array = new byte[1024];
			int number = -1;
			while ((number = in.read(array, 0, array.length)) != -1) {
				out.write(array, 0, number);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return target;
	}

	public static void main(String[] args) {

		File userOrderInfoFile = new File("d:\\", "201808_640000_USERORDERDATA.txt");
		File[] files = new File[] { userOrderInfoFile };
		String targzipFileName = "201808_640000.tar";
		File target = new File("d:\\" + targzipFileName);
		compress(pack(files, target));
	}
}
