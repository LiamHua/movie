package team.chenxin.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * @Description TODO 时间工具
 * @PACKAGE_NAME: team.chenxin.utils
 * @NAME: TimeUtil
 * @USER: Chenxin
 * @DATE: 2021/4/6
 * @DAY_NAME_FULL: 星期二
 * @PROJECT_NAME: movie
 **/
public final class TimeUtil {
    public static String getTimeNow()
    {
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        return date.format(newFormatter);
    }
}
