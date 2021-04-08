package team.chenxin.utils;

import java.util.Date;

/**
 * @Description TODO 生成唯一的Long类型ID
 * @PACKAGE_NAME: team.chenxin.utils
 * @NAME: IDUtil
 * @USER: Chenxin
 * @DATE: 2021/4/7
 * @DAY_NAME_FULL: 星期三
 * @PROJECT_NAME: movie
 **/
public final class IDUtil {
    private static final Date date = new Date();
    //  private static StringBuilder buf = new StringBuilder();
    private static int seq = 0;
    private static final int ROTATION = 99999;

    public static synchronized long next(){
    if (seq > ROTATION) seq = 0;
     //    buf.delete(0, buf.length());
    date.setTime(System.currentTimeMillis());
    String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
    return Long.parseLong(str);
    }

}
