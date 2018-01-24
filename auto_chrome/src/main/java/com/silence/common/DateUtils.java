package com.silence.common;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/***
 * 处理所有和日期相关的处理.
 * @author songy
 * @version $Revision: 7611 $
 */
public class DateUtils extends Object {
    /*** 系统总的失效日期. */
    public static final String DATE_FOREVER = "9999-12-31";
    /** 时间格式. */
    private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /** 到小时分钟的日期格式. */
    private static final String FORMAT_DATETIME_HM = "yyyy-MM-dd HH:mm";
    /** 全时间格式. */
    private static final String FORMAT_FULLTIME = "yyMMddHHmmssSSS";
    /** 日期格式. */
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    /** 日期格式. */
    private static final String FORMAT_YEARMONTH = "yyyy-MM";
    /** 纯时间格式. */
    private static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * compare two kinds String with format : 12:00 , 08:00; or 12:00:00,
     * 08:00:00.<br>
     * <br>
     * @param firstTime the first time string.
     * @param secondTime the second time string.
     * @return 0 -- same 1 -- first bigger than second -1 -- first smaller than
     *         second -2 -- invalid time format
     */
    public static int compareOnlyByTime(String firstTime, String secondTime) {
        try {
            String timeDelm = ":";

            // calculate the first time to integer
            int pos = firstTime.indexOf(timeDelm);
            int iFirst = Integer.parseInt(firstTime.substring(0, pos)) * 10000;
            firstTime = firstTime.substring(pos + 1);
            pos = firstTime.indexOf(timeDelm);

            if (pos > 0) {
                iFirst = iFirst + (Integer.parseInt(firstTime.substring(0, pos)) * 100)
                    + Integer.parseInt(firstTime.substring(pos + 1));
            } else {
                iFirst = iFirst + (Integer.parseInt(firstTime) * 100);
            }

            // calculate the second time string to integer
            pos = secondTime.indexOf(timeDelm);
            int iSecond = Integer.parseInt(secondTime.substring(0, pos)) * 10000;
            secondTime = secondTime.substring(pos + 1);
            pos = secondTime.indexOf(timeDelm);

            if (pos > 0) {
                iSecond = iSecond + (Integer.parseInt(secondTime.substring(0, pos)) * 100)
                    + Integer.parseInt(secondTime.substring(pos + 1));
            } else {
                iSecond = iSecond + (Integer.parseInt(secondTime) * 100);
            }

            // compare two
            if (iFirst == iSecond) {
                return 0;
            } else if (iFirst > iSecond) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -2;
        }
    }

    /**
     * 根据规定格式的字符串得到Calendar.<br>
     * <br>
     * @param dateString 日期串.
     * @return 对应Calendar
     */
    public static Calendar getCalendar(String dateString) {
        Calendar calendar = Calendar.getInstance();
        String[] items = dateString.split("-");
        calendar
            .set(Integer.parseInt(items[0]), Integer.parseInt(items[1]) - 1, Integer.parseInt(items[2]));
        return calendar;
    }

    /**
     * 得到与当前日期相差指定天数的日期字符串.<br>
     * <br>
     * @param days 前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     * @param dateString 指定的日期.
     * @param days 前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int days) {
        Calendar calendar = getCalendar(dateString);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     * @param dateString 指定的日期.
     * @param period 前后的天数，正值为后， 负值为前.
     * @param periodType 周期类别 可以是天、月、年.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int period, int periodType) {
        Calendar calendar = getCalendar(dateString);

        switch (periodType) {
            case 1: // 天
                calendar.add(Calendar.DATE, period);
                break;
            case 2: // 月
                calendar.add(Calendar.MONTH, period);
                break;
            case 3: // 年
                calendar.add(Calendar.MONTH, period * 12);
                break;
            default:
        }
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 某日期（带时间）加上几天得到另外一个日期(带时间).<br>
     * <br>
     * @param datetime 需要调整的日期(带时间).
     * @param days 调整天数.
     * @return 调整后的日期(带时间)
     */
    public static String getCertainDatetime(String datetime, int days) {
        Date curDate = getDateFromString(datetime, FORMAT_DATETIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     * @param dif 前后的月数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     * @param dif 前后的月数，正值为后， 负值为前.
     * @param format 格式
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), format);
    }

    /**
     * 得到当前日期的中文日期字符串.<br>
     * <br>
     * @return 中文日期字符串
     */
    public static String getChineseDate() {
        return getChineseDate(getDate());
    }
    
    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     * @param date 日期值.
     * @return 中文日期字符串
     */
    public static String getChineseDate(String date) {
        if (date.length() < Integer.valueOf("10")) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日
            String y1 = year.substring(0, 1); // 年 字符1
            String y2 = year.substring(1, 2); // 年 字符1
            String y3 = year.substring(2, 3); // 年 字符3
            String y4 = year.substring(3, 4); // 年 字符4
            String m2 = month.substring(1, 2); // 月 字符2
            String d1 = day.substring(0, 1); // 日 1
            String d2 = day.substring(1, 2); // 日 2
            String cy1 = getChineseNum(y1);
            String cy2 = getChineseNum(y2);
            String cy3 = getChineseNum(y3);
            String cy4 = getChineseNum(y4);
            String cm2 = getChineseNum(m2);
            String cd1 = getChineseNum(d1);
            String cd2 = getChineseNum(d2);
            String cYear = cy1 + cy2 + cy3 + cy4 + "年";
            String cMonth = "月";

            if (Integer.parseInt(month) > 9) {
                cMonth = "十" + cm2 + cMonth;
            } else {
                cMonth = cm2 + cMonth;
            }

            String cDay = "日";

            if (Integer.parseInt(day) > 9) {
                cDay = cd1 + "十" + cd2 + cDay;
            } else {
                cDay = cd2 + cDay;
            }

            String chineseday = cYear + cMonth + cDay;
            return chineseday;
        }
    }

    /**
     * 得到当前日期的星期数 : 例如 '星期一', '星期二'等.<br>
     * <br>
     * @return 当前日期的星期数
     */
    public static String getChineseDayOfWeek() {
        return getChineseDayOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期数.<br>
     * <br>
     * @param strDate 指定日期字符串.
     * @return 指定日期的星期数
     */
    public static String getChineseDayOfWeek(String strDate) {
        Calendar calendar = getCalendar(strDate);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String strWeek = "";

        switch (week) {
            case Calendar.SUNDAY:
                strWeek = "星期日";
                break;
            case Calendar.MONDAY:
                strWeek = "星期一";
                break;
            case Calendar.TUESDAY:
                strWeek = "星期二";
                break;
            case Calendar.WEDNESDAY:
                strWeek = "星期三";
                break;
            case Calendar.THURSDAY:
                strWeek = "星期四";
                break;
            case Calendar.FRIDAY:
                strWeek = "星期五";
                break;
            case Calendar.SATURDAY:
                strWeek = "星期六";
                break;
            default:
                strWeek = "星期一";
                break;
        }

        return strWeek;
    }

    /**
     * 根据数字得到中文数字.<br>
     * <br>
     * @param number 数字.
     * @return 中文数字
     */
    public static String getChineseNum(String number) {
        String chinese = "";
        int x = Integer.parseInt(number);

        switch (x) {
            case 0:
                chinese = "零";
                break;
            case 1:
                chinese = "一";
                break;
            case 2:
                chinese = "二";
                break;
            case 3:
                chinese = "三";
                break;
            case 4:
                chinese = "四";
                break;
            case 5:
                chinese = "五";
                break;
            case 6:
                chinese = "六";
                break;
            case 7:
                chinese = "七";
                break;
            case 8:
                chinese = "八";
                break;
            case 9:
                chinese = "九";
                break;
            default:
        }
        return chinese;
    }

    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     * @param date 给定日期.
     * @return 2005年09月23日格式的日期
     */
    public static String getChineseTwoDate(String date) {
        if (date.length() < 10) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日

            return year + "年" + month + "月" + day + "日";
        }
    }

    /**
     * 自定义当前时间格式.<br>
     * <br>
     * @param customFormat 自定义格式
     * @return 日期时间字符串
     */
    public static String getCustomDateTime(String customFormat) {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), customFormat);
    }

    /**
     * 得到当前的日期字符串.<br>
     * <br>
     * @return 日期字符串
     */
    public static String getDate() {
        return getDate(Calendar.getInstance());
    }

    /**
     * 得到指定日期的字符串.<br>
     * <br>
     * @param calendar 指定的日期.
     * @return 日期字符串
     */
    public static String getDate(Calendar calendar) {
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 某日期加上几天得到另外一个日期.<br>
     * <br>
     * @param addNum 要增加的天数.
     * @param getDate 某日期.
     * @return 与某日期相差addNum天的日期
     */
    public static String getDateAdded(int addNum, String getDate) {
        return getCertainDate(getDate, addNum);
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @return 日期
     */
    public static Date getDateFromString(String s) {
        return getDateFromString(s, FORMAT_DATE);
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @param format 字符串格式.
     * @return 日期
     */
    public static Date getDateFromString(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到当前的日期时间字符串,到小时分钟.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDateTimeHm() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME_HM);
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetimeW3C() {
        return getDate() + "T" + getTime();
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getDatetimeZd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到当前的日期.<br>
     * <br>
     * @return 当前日期
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取相差时间，精确到分钟.<br>
     * <br>
     * @param beginTime 开始时间.
     * @param endTime 结束时间.
     * @return 相差时间
     */
    public static String getDiffTime(String beginTime, String endTime) {
        try {
            if (endTime == null || endTime.length() == 0) {
                endTime = getDatetime();
            }
            Date eTime = getDateFromString(endTime, FORMAT_DATETIME);
            Date bTime = getDateFromString(beginTime, FORMAT_DATETIME);
            long time = eTime.getTime() - bTime.getTime();
            StringBuffer sb = new StringBuffer();
            int day = (int) Math.floor(time / (24 * 3600000));
            if (day > 0) {
                sb.append(day).append("天");
            }
            time = time % (24 * 3600000);
            int hour = Cast.to(Math.floor(time / 3600000), 0);
            if (hour > 0) {
                sb.append(hour).append("小时");
            }
            time = time % 3600000;
            int minute = Cast.to(Math.ceil(time / 60000), 0);
            if (minute > 0) {
                sb.append(minute).append("分钟");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到本周星期一的日期.<br>
     * <br>
     * @return 日期字符串
     */
    public static String getFirstDateOfWeek() {
        return getFirstDateOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期一的日期.<br>
     * <br>
     * @param dateString 日期字符串.
     * @return 本周星期一的日期
     */
    public static String getFirstDateOfWeek(String dateString) {
        Calendar calendar = getCalendar(dateString);
        int iCount;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            iCount = -6;
        } else {
            iCount = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK);
        }

        return getCertainDate(dateString, iCount);
    }

    /**
     * 得到当前的全时间字符串，包含毫秒.<br>
     * <br>
     * @return 日期时间字符串
     */
    public static String getFulltime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_FULLTIME);
    }

    /**
     * 得到当前的月份.<br>
     * <br>
     * @return 当前月份
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得两日期间的月份差数.
     * @param startDate 起始日期.
     * @param endDate 结束日期.
     * @return 月份差数
     */
    public static int getMonthDiff(String startDate, String endDate) {
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");
        int startYear = Integer.parseInt(startArray[0]);
        int startMonth = Integer.parseInt(startArray[1]);
        int endYear = Integer.parseInt(endArray[0]);
        int endMonth = Integer.parseInt(endArray[1]);
        return Math.abs((endYear - startYear) * 12 + endMonth - startMonth);
    }

    /**
     * 得到当前日期的所在月的第一天的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return String 返回的日期
     */
    public static String getMonthFirstDay(String date) {
        Calendar cal = getCalendar(date);
        String month = Cast.to(cal.get(Calendar.MONTH) + 1, "1");
        String year = Cast.to(cal.get(Calendar.YEAR), "");
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        return year + "-" + month + "-01";
    }

    /**
     * 得到当前日期的所在月的最后一天的日期.
     * @param date 当前日期.
     * @return 返回的日期
     */
    public static String getMonthLastDay(String date) {
        Calendar cal = getCalendar(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int nextMonth = month + 1;
        int nextYear = year;
        if (nextMonth == 13) {
            nextMonth = 1;
            nextYear = nextYear + 1;
        }
        String nextMonthFirstDay = nextYear + "-" + nextMonth + "-01";
        return getCertainDate(nextMonthFirstDay, -1);
    }

    /**
     * 将日期格式化为指定的字符串.<br>
     * <br>
     * @param d 日期.
     * @param format 输出字符串格式.
     * @return 日期字符串
     */
    public static String getStringFromDate(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     * 得到当前的纯时间字符串.<br>
     * <br>
     * @return 时间字符串
     */
    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_TIME);
    }

    /**
     * 如果当前日期是周六或者周日，则返回下周一的日期.<br>
     * <br>
     * @param date 当前日期.
     * @return 下周一日期
     */
    public static String getWorkDate(final String date) {
        Date curDate = getDateFromString(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        if (week == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 2);
        } else if (week == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return getDate(calendar);
    }

    /**
     * 得到当前的年份.<br>
     * <br>
     * @return 当前年份
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到当前的年月日期字符串.<br>
     * <br>
     * @return 年月日期字符串
     */
    public static String getYearMonth() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_YEARMONTH);
    }

    /**
     * 当前日期与参数传递的日期的相差天数.<br>
     * <br>
     * @param dateinfo 指定的日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String dateinfo) {
        return selectDateDiff(dateinfo, getDate());
    }

    /**
     * 当得到两个日期相差天数.<br>
     * <br>
     * @param first 第一个日期.
     * @param second 第二个日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String first, String second) {
        int dif = 0;
        try {
            Date fDate = getDateFromString(first, FORMAT_DATE);
            Date sDate = getDateFromString(second, FORMAT_DATE);
            dif = (int) ((fDate.getTime() - sDate.getTime()) / 86400000);
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

    public static double getDiffHoure(String beginTime, String endTime) {
        double dif = 0.00;
        try {
            Date eDatetime = DateUtils.getDateFromString(endTime,
                "yyyy-MM-dd HH:mm:ss");
            Date bDatetime = DateUtils.getDateFromString(beginTime,
                "yyyy-MM-dd HH:mm:ss");
            double ed = eDatetime.getTime();
            double sd = bDatetime.getTime();
            BigDecimal et = new BigDecimal(ed / 1000 / 3600 - sd / 1000 / 3600);
            dif = et.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

}
