
package com.rkt.common.tools;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Tools {
    /**
     * this method is 判断是否是ASCII编码
     * @param string
     * @return
     * @createTime 2014年6月25日 下午2:14:50
     * @author yangjianjun
     */
    public static boolean isASCIIEncoded(String string) {
        if(string == null){
            string ="";
        }
        return !Pattern.compile("[^\\x00-\\xff]").matcher(string).find();
    }
    
     
    /**
     * this method is  获取字符串长度，一个中文占2个长度
     * @param string
     * @return
     * @createTime 2014年6月25日 下午2:14:33
     * @author yangjianjun
     */
    public static int getLength(String string) {
        if(string == null){
            string = "";
        }
       // String reg = "[^\\x00-\\x7f]";
         String reg = "[^\\x00-\\xff]";
        Pattern pattern =Pattern.compile(reg);
        Matcher mathcher = pattern.matcher(string);
        if(mathcher.find()){        
            return string.length()*2;
        }
        return string.length();
    }
    
	/**
	 * 时间格式化
	 * @author yangjianjun
	 * @creaetime Jul 18, 2012 7:22:29 PM
	 * @param date   date
	 * @param p		 parameter
	 * @return       dateFormat
	 */
	public static String formatDateTime(Date date, int p) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (p) {
		case 1:
			pattern = "yyyy-MM-dd";
			break;
		case 2:
			pattern = "yyyy-MM-dd HH:mm:ss";
			break;
		case 3:
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
			break;
		case 4:
			pattern = "yyyy-MM";
			break;
		case 5:
			pattern = "MM-dd";
			break;
		case 6:
			pattern = "HH:mm:ss";
			break;
		case 7:
			pattern = "HH:mm";
			break;
		case 8:
			pattern = "MM-dd HH:mm:ss";
			break;
		case 9:
			pattern = "MM-dd HH:mm";
			break;
		case 10:
			pattern = "yyyyMMddHHmmssSSS";
			break;
		case 11:
			pattern = "yyyy/MM/dd HH:mm:ss";
			break;
        default:
            break;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
    	
    /**
     * this method is  将字符串转化为日期格式
     * @param time
     * @param type
     * @return
     * @throws java.text.ParseException
     * @createTime 2014年6月25日 下午2:14:09
     * @author yangjianjun
     */
    public static Date stringToDate(String time,int type) throws Exception{
        SimpleDateFormat simple=null;
        switch (type) {
            case 1:
                simple=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 2:
                simple=new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case 4:
                simple=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                break;
            default:
                break;
        }
        if(simple == null ){
            throw new Exception("参数有误！");
        }
        Date date = simple.parse(time);
        return date;
    }

	/**
	 * this method is 验证某个对象是否为空
	 * @param obj
	 * @return
	 * @createTime 2014年6月25日 下午2:13:25
	 * @author yangjianjun
	 */
	public static boolean objectIsNotNull(Object obj) {
		return (null != obj) ? true : false;
	}
    
    /**
     * this method is 判断某一个集合对象是否为空
     * @param list
     * @return
     * @createTime 2014年6月25日 下午2:11:46
     * @author yangjianjun
     */
    public static <T> boolean checkListNotNull(List<T> list){
        if(list == null || list.size() < 1){
            return true;
        }
        return false;
    }
        
    /**
     * 判断一个字符串是否在另一个字符串中存在
     * @author yangjianjun
     * @creaetime Nov 26, 2012 4:01:19 PM
     * @param str0	            字符串规则
     * @param str1		  要检查的字符串	
     * @return            boolean
     */
    public static boolean isExistString(String str0,String str1){
    	int indexOf=str0.indexOf(str1);
    	if(indexOf>=0){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 计算某个时间到某个时间的时差等信息
     * @author yangjianjun
     * @creaetime Nov 28, 2014 3:16:21 PM
     * @param startTime            beginTime
     * @param endTime              endTime
     * @param format               temple
     * @param str                  小时
     * @return                     时差
     * @throws java.text.ParseException      ParseException
     */
    public static Long dateDiff(String startTime, String endTime,   
            String format, String str) throws ParseException {   
        // 按照传入的格式生成一个simpledateformate对象   
        SimpleDateFormat sd = new SimpleDateFormat(format);   
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数   
        long nh = 1000 * 60 * 60;// 一小时的毫秒数   
        long nm = 1000 * 60;// 一分钟的毫秒数   
        long ns = 1000;// 一秒钟的毫秒数   
        long diff;   
        long day = 0;   
        long hour = 0;   
        long min = 0;   
        long sec = 0;   
        // 获得两个时间的毫秒时间差异   
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();   
            day = diff / nd;// 计算差多少天   
            hour = diff % nd / nh + day * 24;// 计算差多少小时   
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟   
            sec = diff % nd % nh % nm / ns;// 计算差多少秒   
            // 输出结果   
//            System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"  
//                    + (min - day * 24 * 60) + "分钟" + sec + "秒。");   
//            System.out.println("hour=" + hour + ",min=" + min);  
            if (str.equalsIgnoreCase("h")) {
                return hour;
            } else {   
                return min;   
            }   
    }
    /**
     * 计算结束时间到开始时间的秒数
     * @author yangjianjun
     * @createtime 2014年6月25日 下午2:09:10
     * @param startTime
     * @param endTime
     * @param format
     * @return 
     */
    public static Long dateDiff (String startTime, String endTime,   
            String format){
    	  SimpleDateFormat sf = new SimpleDateFormat(format); 
    	 long time=-1;
    		try {
				 time=sf.parse(endTime).getTime()- sf.parse(startTime).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
    	return time/1000;
    }
   	/**
   	 * this method is 除法结果保留两位小数并按照第二位小数四舍五入
   	 * @param divisor		 除数
   	 * @param dividend		 被除数
   	 * @param k				 保留小数的位数及四舍五入的位数
   	 * @return			     double数值
   	 * @createTime 2014年6月25日 下午2:09:10
   	 * @author yangjianjun
   	 */
   	public static double getRoundDecimal(long divisor,long dividend,int k) {
		 BigDecimal dec1=new BigDecimal(divisor);
		 BigDecimal dec2=new BigDecimal(dividend);
		return  dec1.divide(dec2, k,BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
   	

   	/**
   	 * this method is 根据秒数求出时间
   	 * @param time
   	 * @return
   	 * @createTime 2014年6月25日 下午2:09:51
   	 * @author yangjianjun
   	 */
   	public static String paserTime(String time){  
        System.setProperty("user.timezone", "Asia/Shanghai");  
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
        TimeZone.setDefault(tz);  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(Long.parseLong(time) * 1000L));
//        System.out.print("日期为:" + times);
        return times;  
  }  

   	/**
   	 * this method is 根据时间求出秒数
   	 * @param date
   	 * @return
   	 * @createTime 2014年6月25日 下午2:10:08
   	 * @author yangjianjun
   	 */
   	public static long paserSecond(Date date){
   		long dates = date.getTime()/1000L;
   		return dates;
   	}
    /**
     * this method is  根据实际和天数计算出最新时间
     * @param dates	         当前时间
     * @param day      当前天数
     * @return		         最新时间
     * @createTime 2014年7月10日 上午11:11:51
     * @author yangjianjun
     */
    public static Date getBeforeAfterDate(Date dates, int day) {  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(dates);  
        int Year = cal.get(Calendar.YEAR);  
        int Month = cal.get(Calendar.MONTH);  
        int Day = cal.get(Calendar.DAY_OF_MONTH);  
        int NewDay = Day + day;  
        cal.set(Calendar.YEAR, Year);  
        cal.set(Calendar.MONTH, Month);  
        cal.set(Calendar.DAY_OF_MONTH, NewDay);  
        return new Date(cal.getTimeInMillis());  
    }  
    
    public static long  timeDistance(String countdownTime) throws Exception{
       long  nowSeconds=paserSecond(new Date());
       Date  countDownDate= stringToDate(countdownTime, 4);
       long  countDown=paserSecond(countDownDate);
       long result= countDown-nowSeconds;
      return result;
    }
}
