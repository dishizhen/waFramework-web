package com.wa.framework.report.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wa.framework.report.vo.DataInfoVo;
import com.wa.framework.util.DateUtils;

public class ReportDateUtil {
    private static final String YEAR = "年";
    private static final String MONTH = "月";
    private static final String START_SUFFIX = " 00:00:00";
    private static final String END_SUFFIX = " 23:59:59";
    
    public static final String HALF_YEAR_REPORT = "bannb";//半年报
    public static final String QUARTER_REPORT = "jib";//季报
    public static final String YEAR_REPORT = "nianb";//年报
    public static final String MONTH_REPORT = "yueb";//月报

    /**
     * @Description: 获取年报时间列表
     * @param: @param beginYearDate
     * @param: @return
     * @return: List<DataInfoVo>
     * @throws
     * @since JDK 1.6
     */
    public static List<DataInfoVo> getYearListByYear(Date beginYearDate) {
        List<DataInfoVo> yearList = new ArrayList<DataInfoVo>();

        // 获取开始时间的年份
        DataInfoVo vo = new DataInfoVo();
        int beginYear = getYear(beginYearDate);
        vo.setId(String.valueOf(beginYear));
        vo.setValue(spliceYear(beginYear));
        yearList.add(vo);

        int currentYear = getCurrentYear();

        int i = 0;
        while (true) {
            i++;
            Date addYearDate = DateUtils.addYears(beginYearDate, i);
            int addYear = getYear(addYearDate);
            if (addYear > currentYear) {
                break;
            }

            DataInfoVo voNew = new DataInfoVo();
            voNew.setId(String.valueOf(addYear));
            voNew.setValue(spliceYear(addYear));
            yearList.add(voNew);
        }

        return yearList;
    }

    /**
     * @Description: 获取月报开始时间列表
     * @param: @param beginYearDate
     * @param: @return
     * @return: List<DataInfoVo>
     * @throws
     * @since JDK 1.6
     */
    public static List<DataInfoVo> getMonthStartListByDate(Date beginYearDate) {
        List<DataInfoVo> monthList = new ArrayList<DataInfoVo>();

        DataInfoVo vo = new DataInfoVo();
        Date startDate = getStartDayOfMonth(beginYearDate);
        vo.setId(DateUtils.format(startDate, DateUtils.YYYYMMDD_10) + START_SUFFIX);
        int beginYear = getYear(beginYearDate);
        int beginMonth = getMonth(beginYearDate);
        vo.setValue(spliceYearAndMonth(beginYear, beginMonth));
        monthList.add(vo);

        Date currentDate = new Date();
        int i = 0;
        while (true) {
            i++;
            Date addMonthDate = DateUtils.addMonths(startDate, i);
            if (DateUtils.compareYearToDay(addMonthDate, currentDate) > 0) {
                break;
            }

            DataInfoVo voNew = new DataInfoVo();
            voNew.setId(DateUtils.format(addMonthDate) + START_SUFFIX);
            int addYear = getYear(addMonthDate);
            int aMonth = getMonth(addMonthDate);
            voNew.setValue(spliceYearAndMonth(addYear, aMonth));
            monthList.add(voNew);
        }

        return monthList;
    }
    
    /**
     * @Description: 获取月报结束时间列表
     * @param: @param beginYearDate
     * @param: @return
     * @return: List<DataInfoVo>
     * @throws
     * @since JDK 1.6
     */
    public static List<DataInfoVo> getEndStartListByDate(Date beginYearDate) {
        List<DataInfoVo> monthList = new ArrayList<DataInfoVo>();

        DataInfoVo vo = new DataInfoVo();
        Date endDate = getEndDayOfMonth(beginYearDate);
        vo.setId(DateUtils.format(endDate, DateUtils.YYYYMMDD_10) + END_SUFFIX);
        int beginYear = getYear(beginYearDate);
        int beginMonth = getMonth(beginYearDate);
        vo.setValue(spliceYearAndMonth(beginYear, beginMonth));
        monthList.add(vo);

        Date currentDate = new Date();
        int i = 0;
        while (true) {
            i++;
            Date addMonthDate = DateUtils.addMonths(endDate, i);
            if (DateUtils.compareYearToDay(addMonthDate, currentDate) > 0) {
                DataInfoVo voNew = new DataInfoVo();
                voNew.setId(DateUtils.format(getEndDayOfMonth(addMonthDate)) + END_SUFFIX);
                int addYear = getYear(addMonthDate);
                int aMonth = getMonth(addMonthDate);
                voNew.setValue(spliceYearAndMonth(addYear, aMonth));
                monthList.add(voNew);
                break;
            }

            DataInfoVo voNew = new DataInfoVo();
            voNew.setId(DateUtils.format(getEndDayOfMonth(addMonthDate)) + END_SUFFIX);
            int addYear = getYear(addMonthDate);
            int aMonth = getMonth(addMonthDate);
            voNew.setValue(spliceYearAndMonth(addYear, aMonth));
            monthList.add(voNew);
        }

        return monthList;
    }
    
    /**
     * @Description: 获取季度时间列表
     * @param: @param beginYearDate
     * @param: @return
     * @return: List<DataInfoVo>
     * @throws
     * @since JDK 1.6
     */
    public static List<DataInfoVo> getQuarterListByDate(Date beginYearDate) {
        List<DataInfoVo> quarterList = new ArrayList<DataInfoVo>();

        DataInfoVo vo = new DataInfoVo();
        String startDateStr = getFirstDayofThisSeasonTime(beginYearDate);
        String endDateStr = getEndDayofThisSeasonTime(beginYearDate);
        vo.setId(startDateStr+";"+endDateStr);
        int beginYear = getYear(beginYearDate);
        vo.setValue(spliceYear(beginYear) + getQuarterStr(getMonth(beginYearDate)));
        quarterList.add(vo);

        Date currentDate = new Date();
        Date currentDateQuarter = DateUtils.parseDate(getFirstDayofThisSeasonTime(currentDate), DateUtils.YYYYMMDD_10);;
        Date startDate = DateUtils.parseDate(startDateStr, DateUtils.YYYYMMDD_10);
        int i = 0;
        while (true) {
            i = i + 3;
            Date addMonthDate = DateUtils.addMonths(startDate, i);
            if (DateUtils.compareYearToDay(addMonthDate, currentDateQuarter) > 0) {
                break;
            }

            DataInfoVo voNew = new DataInfoVo();
            String addStartDate = getFirstDayofThisSeasonTime(addMonthDate);
            String addEndDateStr = getEndDayofThisSeasonTime(addMonthDate);
            voNew.setId(addStartDate+";"+addEndDateStr);
            int addYear = getYear(addMonthDate);
            voNew.setValue(spliceYear(addYear) + getQuarterStr(getMonth(addMonthDate)));
            quarterList.add(voNew);
        }

        return quarterList;
    }
    
    /**
     * @Description: 获取半年度时间列表
     * @param: @param beginYearDate
     * @param: @return
     * @return: List<DataInfoVo>
     * @throws
     * @since JDK 1.6
     */
    public static List<DataInfoVo> getHalfListByDate(Date beginYearDate) {
        List<DataInfoVo> quarterList = new ArrayList<DataInfoVo>();

        DataInfoVo vo = new DataInfoVo();
        String startDateStr = getFirstDayofThisHalfTime(beginYearDate);
        String endDateStr = getEndDayofThisHalfTime(beginYearDate);
        vo.setId(startDateStr+";"+endDateStr);
        int beginYear = getYear(beginYearDate);
        vo.setValue(spliceYear(beginYear) + getHalfStr(getMonth(beginYearDate)));
        quarterList.add(vo);

        Date currentDate = new Date();
        Date currentDateQuarter = DateUtils.parseDate(getFirstDayofThisHalfTime(currentDate), DateUtils.YYYYMMDD_10);;
        Date startDate = DateUtils.parseDate(startDateStr, DateUtils.YYYYMMDD_10);
        int i = 0;
        while (true) {
            i = i + 6;
            Date addMonthDate = DateUtils.addMonths(startDate, i);
            if (DateUtils.compareYearToDay(addMonthDate, currentDateQuarter) > 0) {
                break;
            }

            DataInfoVo voNew = new DataInfoVo();
            String addStartDate = getFirstDayofThisHalfTime(addMonthDate);
            String addEndDateStr = getEndDayofThisHalfTime(addMonthDate);
            voNew.setId(addStartDate+";"+addEndDateStr);
            int addYear = getYear(addMonthDate);
            voNew.setValue(spliceYear(addYear) + getHalfStr(getMonth(addMonthDate)));
            quarterList.add(voNew);
        }

        return quarterList;
    }
    
    /**
     * @Description: 根据报表类型获取报表初始时的开始时间和结束时间
     * @param: @param reportType
     * @param: @return
     * @return: String[]
     * @throws
     * @since JDK 1.6
     */
    public static String getTimeByReportType(String reportType){
        String retStr = "";
        
        Date currentDate = new Date();
        int beginYear = getYear(currentDate);
        if(ReportDateUtil.HALF_YEAR_REPORT.equals(reportType)){
            retStr = getFirstDayofThisHalfTime(currentDate) + ";" + getEndDayofThisHalfTime(currentDate);
        }else if(ReportDateUtil.QUARTER_REPORT.equals(reportType)){
            retStr = getFirstDayofThisSeasonTime(currentDate) + ";" + getEndDayofThisSeasonTime(currentDate);
        }else if(ReportDateUtil.YEAR_REPORT.equals(reportType)){
            retStr = String.valueOf(beginYear);
        }else if(ReportDateUtil.MONTH_REPORT.equals(reportType)){
            Date beginDate = getStartDayOfMonth(currentDate);
            Date endDate = getEndDayOfMonth(currentDate);
            retStr = DateUtils.format(beginDate, DateUtils.YYYYMMDDHHMMSS_19) + ";" + DateUtils.format(endDate, DateUtils.YYYYMMDDHHMMSS_19);
        }
        return retStr;
    }
    
    private static String getQuarterStr(int month){
        String quarter = null;
        if(month == 1 || month ==2 || month ==3){
            quarter = "第一季度";
        }
        else if(month == 4 || month ==5 || month ==6){
            quarter = "第二季度";
        }
        else if(month == 7 || month ==8 || month ==9){
            quarter = "第三季度";
        }
        else if(month == 10 || month ==11 || month ==12){
            quarter = "第四季度";
        }
        
        return quarter;
    }
    
    private static String getHalfStr(int month){
        String half = null;
        if (month >= 1 && month <= 6) {
            half = "上半年";
        }
        if (month >= 7 && month <= 12) {
            half = "下半年";
        }
        
        return half;
    }

    /**
     * @Description: 根据日期获取年份
     * @param: @param beginYear
     * @param: @return
     * @return: int
     * @throws
     * @since JDK 1.6
     */
    private static int getYear(Date beginYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginYear);
        int year = cal.get(Calendar.YEAR);

        return year;
    }

    private static int getMonth(Date beginYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginYear);
        int month = cal.get(Calendar.MONTH) + 1;

        return month;
    }

    private static String spliceYear(int year) {
        String returnStr = year + YEAR;
        return returnStr;
    }

    private static String spliceYearAndMonth(int year, int month) {
        String returnStr = year + YEAR + month + MONTH;
        return returnStr;
    }

    /**
     * @Description: 获取当前年份
     * @param: @return
     * @return: int
     * @throws
     * @since JDK 1.6
     */
    private static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
    
    public static int getCurrentMonth () {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH);
    }

    private static Date getStartDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getStartOfDay(cal.getTime());
    }

    private static Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return getDate(year, month, day);
    }

    private static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);

        return cal.getTime();
    }
    
    private static Date getEndDayOfMonth (Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.set(Calendar.DAY_OF_MONTH, cal
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return getEndOfDay(cal.getTime());
    }

    private static Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

        return cal.getTime();
    }
    
    // 获取本季度第一天
    public static String getFirstDayofThisSeasonTime(Date date) {
        int month = getMonth(date);
        
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];

        int beginYear = getYear(date);

        int start_days = 1;
        Date beginQuarterDate = getDate(beginYear, start_month, start_days);
        String seasonDate = DateUtils.format(beginQuarterDate, DateUtils.YYYYMMDD_10);
        return seasonDate;
    }
    
    public static String getEndDayofThisSeasonTime(Date date) {
        int month = getMonth(date);
        
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int end_month = array[season - 1][2];

        int beginYear = getYear(date);

        int end_days = getLastDayOfMonth(beginYear, end_month);
        Date beginQuarterDate = getDate(beginYear, end_month, end_days);
        String seasonDate = DateUtils.format(beginQuarterDate, DateUtils.YYYYMMDD_10);
        
        return seasonDate;

    }

    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    public static String getFirstDayofThisHalfTime(Date date) {
        int month = getMonth(date);
        
        int start_month = 1;
        if (month >= 7 && month <= 12) {
            start_month = 7;
        }

        int beginYear = getYear(date);

        int start_days = 1;
        Date beginHalfDate = getDate(beginYear, start_month, start_days);
        String seasonDate = DateUtils.format(beginHalfDate, DateUtils.YYYYMMDD_10);
        return seasonDate;
    }
    
    public static String getEndDayofThisHalfTime(Date date) {
        int month = getMonth(date);
        
        int end_month = 6;
        int end_days = 30;
        if (month >= 7 && month <= 12) {
            end_month = 12;
            end_days = 31;
        }

        int beginYear = getYear(date);

        Date beginQuarterDate = getDate(beginYear, end_month, end_days);
        String seasonDate = DateUtils.format(beginQuarterDate, DateUtils.YYYYMMDD_10);
        
        return seasonDate;

    }

    public static void main(String[] args) {
//        String date = "2013-06-07";
//        Date a = DateUtils.parseDate(date, DateUtils.YYYYMMDD_10);
        String type = HALF_YEAR_REPORT;
        String b = getTimeByReportType(type);

        System.out.println(b);
    }
}
