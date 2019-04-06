package com.cmcc.andedu.microservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现Date类型和String类型相互转换
 *
 * @author zhouzhihao 2014年4月29日
 */
public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 将Date类型数据转化为字符串 zhouzhihao
	 *
	 * @param date
	 *            日期类型数据
	 * @return
	 */
	public static String date2String(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 将Date类型数据转化为字符串 zhouzhihao
	 *
	 * @param date
	 *            日期类型数据
	 * @return
	 */
	public static String date3String(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	public static String date4String(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 将字符串转化为Date类型 zhouzhihao
	 *
	 * @param str
	 *            字符串 格式为 yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public static Date string2Date(String str) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(str);
	}

	/**
	 * 将字符串转化为Date类型 zhao
	 *
	 * @param str
	 *            字符串 格式为 yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public static Date string4Date(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(str);
		} catch (ParseException e) {
			logger.info("日期转换异常" + e);
		}
		return null;
	}

	/**
	 * 返回字符型日期
	 *
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 格式化输出日期
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return result;
	}

	/**
	 * 日期相减
	 *
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相减后的日期
	 */
	public static Date diffDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 返回毫秒
	 *
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 格式化日期
	 *
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
			}
			date = df.parse(dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取两个日期之间的时间间隔
	 *
	 * @param currentDate
	 * @param dateEnd
	 * @return 1秒 1分钟 1小时 1天
	 */
	public static String getTwoDay(Object currentDate, Object dateEnd) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		String days = "";
		try {
			java.util.Date dateS = null;
			if (currentDate instanceof String) {
				dateS = myFormatter.parse((String) currentDate);
			} else if (currentDate instanceof Date) {
				dateS = (Date) currentDate;
			} else {
				return "";
			}
			java.util.Date dateE = null;
			if (dateEnd instanceof String) {
				dateE = myFormatter.parse((String) dateEnd);
			} else if (dateEnd instanceof Date) {
				dateE = (Date) dateEnd;
			} else {
				return "";
			}
			day = (dateS.getTime() - dateE.getTime()) / (24 * 60 * 60 * 1000); // 天
			if (day == 0) {
				day = (dateS.getTime() - dateE.getTime()) / (60 * 60 * 1000); // 时
				if (day == 0) {
					day = (dateS.getTime() - dateE.getTime()) / (60 * 1000); // 分
					if (day == 0) {
						day = (dateS.getTime() - dateE.getTime()) / (1000); // 秒
						days = day + " 秒";
					} else {
						days = day + " 分钟";
					}
				} else {
					days = day + " 小时";
				}
			} else {
				days = day + " 天";
			}
		} catch (Exception e) {
			return "";
		}
		return days + "";
	}

	/**
	 * 返回年份
	 *
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 *
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 *
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 *
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 *
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	public static boolean compareTime(String lastDate) {
		boolean flag = false;
		try {
			java.util.Date nowdate = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			Date d = sdf.parse(lastDate);
			flag = d.before(nowdate);
			if (flag) {
				System.out.print("早于今天");
			} else {
				System.out.print("晚于今天");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static String getNextDay(String v_day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nextDay = null;
		try {
			Date temp = sdf.parse(v_day);
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(temp);
			c.add(Calendar.DATE, 1);
			temp = c.getTime();
			// 获得下一天日期字符串
			nextDay = sdf.format(temp);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nextDay;
	}
	
	public static Date string3Date(String str) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.parse(str);
	}
	//字符串格式化日期yyyy-MM-dd 转 yyyyMMdd
	public static String getFormatDay(String v_day) {
		try {
			return date3String(string2Date(v_day));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	public static void main(String[] args) {
		String nextDay = getFormatDay("2019-04-01");
		System.out.println(nextDay);
	}
}
