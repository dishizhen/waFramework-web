package com.wa.framework.report.service;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wa.framework.QueryCondition;
import com.wa.framework.log.ExpLog;
import com.wa.framework.report.model.ReportTemplateFile;
import com.wa.framework.report.util.ReportDateUtil;
import com.wa.framework.service.BaseService;
import com.wa.framework.util.easyui.ResponseUtils;
 
/**
 * 描述：报表查询条件
 * 创建人：caohy
 * 创建时间：2016年7月25日上午11:42:48
 */
@Service("reportStaticService")
@ExpLog(type="报表管理")
public class ReportStatisticService extends BaseService{
	
	
	/**
	 * @Description: 保存自定义报表查询条件
	 * @param: @param temFile
	 * @param: @return
	 * @return: String
	 * @throws
	 * @since JDK 1.7
	 */
	public String temSave(ReportTemplateFile temFile) {
		if (temFile.getSysReportManage() != null) {

			ReportTemplateFile existTemFile = baseDao.unique(
					ReportTemplateFile.class, QueryCondition.eq(
							ReportTemplateFile.PROP_REPORT_ID,
							temFile.getSysReportManage()));
			if (existTemFile != null) {
				existTemFile.setTemplateInfo(temFile.getTemplateInfo());
				update(existTemFile);
			} else {
			    temFile.setSysReportManage(temFile.getSysReportManage());
			    add(temFile);
			}
		} else if (temFile.getSysChartManage() != null) {

            ReportTemplateFile existTemFile = baseDao.unique(
                    ReportTemplateFile.class, QueryCondition.eq(
                            ReportTemplateFile.PROP_CHART_ID,
                            temFile.getSysChartManage()));
            if (existTemFile != null) {
                existTemFile.setTemplateInfo(temFile.getTemplateInfo());
                update(existTemFile);
            } else {
                temFile.setSysChartManage(temFile.getSysChartManage());
                add(temFile);
            }
        }
		
		return ResponseUtils.buildResultJson(true, "保存成功!");
	}
	
	/**
     * @Description: 拼接报表自定义查询条件
     * @param: @param parameterMap
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.7
     */
    public String getWhereValue(Map<String, String[]> parameterMap, String reportType) {
        Set<String> keys = parameterMap.keySet();
        String cons = " 1=1 ";
        for (String key : keys) {
            if(!"pageNum".equals(key)){
            if ((!"pageType".equals(key)&&!"reportId".equals(key) && parameterMap.get(key) != null && 
                            parameterMap.get(key).toString() != "")) {
                boolean flag = true;
                // 时间类型处理
                if (key.indexOf("reportTime") != -1) {// 开始时间
                    if (ReportDateUtil.HALF_YEAR_REPORT.equals(reportType)
                            || ReportDateUtil.QUARTER_REPORT.equals(reportType)) {// 半年报、季报
                        String[] tempendtime = parameterMap.get(key);
                        if (tempendtime != null && tempendtime.length > 0
                                && !StringUtils.isEmpty(tempendtime[0])) {
                            String[] times = tempendtime[0].split(";");
                            cons += " and to_char(" + key + ",'yyyy-mm-dd')"+ " >= '" + times[0] + "'";
                            cons += " and to_char(" + key + ",'yyyy-mm-dd')"+ " <= '" + times[1] + "'";
                        }
                    } else if (ReportDateUtil.YEAR_REPORT.equals(reportType)) {// 年报
                        String[] tempstarttime = parameterMap.get(key);
                        if (tempstarttime != null && tempstarttime.length > 0
                                && !StringUtils.isEmpty(tempstarttime[0])) {
                            cons += " and to_char(" + key + ",'yyyy')" + " = '"+ tempstarttime[0] + "'";
                        }
                    } else if (ReportDateUtil.MONTH_REPORT.equals(reportType)) {
                        String[] temptime = parameterMap.get(key);
                        if (temptime != null && temptime.length > 0) {
                            if (!StringUtils.isEmpty(temptime[0])) {
                                cons += " and to_char(" + key+ ",'yyyy-mm-dd hh24:mi:ss')" + " >= '"+ temptime[0] + "'";
                            }
                            if (temptime.length >= 2
                                    && !StringUtils.isEmpty(temptime[1])) {
                                cons += " and to_char(" + key+ ",'yyyy-mm-dd hh24:mi:ss')" + " <= '"+ temptime[1] + "'";
                            }
                        }
                        flag = false;
                    }
                }else{
                    if (key.indexOf("reportDate") != -1) {// 开始日期
                        String[] tempDate = parameterMap.get(key);
                        if (tempDate != null && tempDate.length > 0) {
                            if (!StringUtils.isEmpty(tempDate[0])) {
                                cons += " and to_char(" + key+ ",'yyyy-mm-dd')" + " >= '"+ tempDate[0] + "'";
                            }
                            if (tempDate.length >= 2
                                    && !StringUtils.isEmpty(tempDate[1])) {
                                cons += " and to_char(" + key+ ",'yyyy-mm-dd')" + " <= '"+ tempDate[1] + "'";
                            }
                        }
                        flag = false;
                    }
                    if (flag) {// 一般查询条件
                        String[] tempValue = parameterMap.get(key);
                        if (tempValue != null && tempValue.length > 0
                                && !StringUtils.isEmpty(tempValue[0])) {
                            if(tempValue[0].contains("%") || tempValue[0].contains("_")){
                               tempValue[0] = tempValue[0].replace("%","\\%").replace("_", "\\_");
                               cons += " and " + key + " like '%" + tempValue[0]+ "%' escape '\\'";
                            }else{
                               cons += " and " + key + " like '%" + tempValue[0]+ "%'";
                            }
                        }
                    }
                }
            }
          }
        }
        return cons;
        
    }

}
