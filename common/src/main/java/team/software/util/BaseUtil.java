package team.software.util;

import org.springframework.util.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author huao
 * @ClassName BaseUtil.java
 * @Description TODO
 * @createTime 2021年03月16日 10:23:00
 */
public class BaseUtil {
    private static final Pattern isNumericPattern = Pattern.compile("[0-9]*");

    /**
     * 判断字符串是否为整数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if (!BaseUtil.isEmpty(str)){
            return isNumericPattern.matcher(str).matches();
        }
        return false;
    }

    /**
     * 得到今日日期
     * @return
     */
    public static String getToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 得到此刻时间
     * @return
     */
    public static String getNowTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 判断参数是否为空
     * @param object
     * @return
     */
    public static Boolean isEmpty(Object object){
        // return null == object || "".equals(object) || "null".equals(object);
        return null == object || "".equals(object);
    }

    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static Boolean isEmptyList(List<?> list){
        return null == list || 0 == list.size();
    }

    /**
     * 判断多个参数是否为空
     * @param object
     * @return
     */
    public static Boolean isEmpty(Object... object){
        Object[] objects = object;
        ArrayList<Object> objectArrayList = new ArrayList<>(Arrays.asList(objects));
        for (Object obj : objectArrayList){
            if (isEmpty(obj)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断邮件格式是否正确
     * @param email
     * @return
     */
    public static Boolean isEmail(String email){
        if (isEmpty(email)){
            return false;
        }
        String REGEX = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
        return Pattern.compile(REGEX).matcher(email).matches();
    }

    /**
     * 根据文件名称获取文件后缀
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 验证时间格式  例 2019-10-09
     * @param date
     * @return
     */
    public static Boolean verifyDate(String date){
        if (isEmpty(date)){
            return false;
        }
        String REGEX = "^\\d{4}-\\d{2}-\\d{2}";
        return Pattern.compile(REGEX).matcher(date).matches();
    }

    /**
     * 日期比较,判断 date1 的日期是否在 date2 的后面
     * 例：date1-->2020-12-16 date2-->2020-12-17 返回false
     * 例：date1-->2020-12-16 date2-->2020-12-16 返回false
     * 例：date1-->2020-12-17 date2-->2020-12-16 返回true
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean dateCompare(String date1,String date2){
        // 日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date1).after(dateFormat.parse(date2))?true:false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 一个指定日期加上指定天数得到新日期
     * @param nowTime
     * @param day
     * @return
     */
    public static String addDate(String nowTime,long day){
        // 日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 指定日期
        Date date = null;
        try {
            date = dateFormat.parse(nowTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 得到指定日期的毫秒数
        long time = date.getTime();
        // 要加上的天数转换成毫秒数
        day = day * 24 * 60 * 60 * 1000;
        // 相加得到新的毫秒数
        time += day;
        // 将毫秒数转换成日期并转换成String格式
        return dateFormat.format(new Date(time));
    }

    /**
     * 计算两个日期的日期差
     * 例：date1-->2020-12-16 date2-->2020-12-187 返回 2
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(String date1,String date2){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try{
            calendar.setTime(dateFormat.parse(date1));
            time1 = calendar.getTimeInMillis();
            calendar.setTime(dateFormat.parse(date2));
            time2 = calendar.getTimeInMillis();
        }catch(Exception e){
            e.printStackTrace();
        }
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 日期转换成String
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        if (null == date){
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return simpleDateFormat.format(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 格式化String为Date
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr){
        if (isEmpty(dateStr)){
            return null;
        }
        try{
            String formatStr = null;
            if (dateStr.indexOf(":") > 0){
                formatStr = "yyyy-MM-dd HH:mm:ss";
            }else {
                formatStr = "yyyy-MM-dd";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr,Locale.UK);
            return simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 将密码进行md5加密
     * @param password
     * @return
     */
    public static String md5Password(String password){
        if (isEmpty(password)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
