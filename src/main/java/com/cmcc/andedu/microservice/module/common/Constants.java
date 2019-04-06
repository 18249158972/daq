package com.cmcc.andedu.microservice.module.common;

public class Constants {

	/*********** 自动采集银行数据的配置参数 ***********************/
	// 银行FTP服务器配置信息
	// 注意区分大小写、空格
	// IP
	public static String ftp_host = "10.55.131.250";
	//public static String ftp_host = "112.33.21.147";
	// 端口
	public static String ftp_port = "22";
	//public static String ftp_port = "9999";
	// 用户
	public static String ftp_username = "root";
	//public static String ftp_username = "gansu-Online";
	// 密码
	public static String ftp_password = "fyyh123";
	//public static String ftp_password = "Q69Rh{lr";
	// FTP路径
	public static String ftp_remotePath = "/home/app/odsuser/ERDDDATA/341045/SOP/";
	//public static String ftp_remotePath = "/upload/datacenter/allOrder/";
	// FTP下载到本地服务器路径、待解压的压缩包的存放位置
	public static String ftp_downfilePath = "/home/syncData/downData";
	//public static String ftp_downfilePath = "D:\\syncData\\downData";
	// 银行FTP服务器配置信息

	// 需要导入的数据文件路径
	public static String impdataFileDest = "/home/syncData/impData";
	//public static String impdataFileDest = "D:\\syncData\\impData";
	// 待解压的压缩包第一层需要解压的文件名称的前缀
	//SOP_341045_20190402.jar
	public static String onefileName_pre = "SOP_341045_";
	// 待解压的压缩包第一层需要解压的文件名称的后缀
	//SOP_341045_20190402
	public static String onefileName_pos = ".jar";
	// 待解压的压缩包，第一层解压密码，如果不需要密码，则默认为空
	public static String onepassword = "";
	// 压缩包第二层需要解压的文件名称的前缀
	//SOP_341045_20190402.z
	public static String twofileName_pre = "SOP_341045_";
	// 待解压的压缩包第二层需要解压的文件名称的后缀
	//SOP_341045_20190402.z
	public static String twosfileName_pos = ".z";
	// 待解压的压缩包，第二层解压密码，如果不需要密码，则默认为空
	public static String twopassword = "541043";
	//数据多长时间间隔向银行FTP服务器发起请求1000为1秒  60000为一分钟
	public static long Threadsleep_time=60000;

	/*********** 自动采集银行数据的配置参数 ***********************/

}
