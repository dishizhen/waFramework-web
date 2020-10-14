package com.wa.framework.report.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.QueryCondition;
import com.wa.framework.controller.BaseController;
import com.wa.framework.report.model.SysChartManage;
import com.wa.framework.report.service.ChartService;
import com.wa.framework.report.service.ReportStatisticService;
import com.wa.framework.service.BaseService;
import com.wa.framework.util.easyui.ResponseUtils;


/**
 * 描述：图表控制类
 * 创建人：chenhq
 * 创建时间：2016年8月2日下午3:28:05
 * 修改人：chenhq
 * 修改时间：2016年8月2日下午3:28:05
 */
@Controller("/chart")
@RequestMapping(value = "/chart")
public class ChartStatisticController extends BaseController<Object, Object> {

    public static final String CHART_TYPE_BINGTU = "pie";
    
    public static final String IS_SUPPORT_YES = "yes";
    
    @Autowired
    private BaseService baseService;
    
    @Autowired
    private ChartService chartService;
    
    @Autowired
    private ReportStatisticService reportStatisticService;
    
    /**
     * @Description: 跳转图表模板页面
     * @param: @param request
     * @param: @param reportName
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/chartDetail")
    public ModelAndView chartDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String chartId = request.getParameter("reportId");
        response.setContentType("text/html; charset=utf-8");
        ModelAndView view = new ModelAndView();

        SysChartManage chartManage = baseService.unique(SysChartManage.class,
                        QueryCondition.eq(SysChartManage.PROP_CHART_ID, chartId));
        if (chartManage != null){
            if(CHART_TYPE_BINGTU.equals(chartManage.getSysChartType())){
                view.setViewName("/report/pie_detail");
            }else{
                view.setViewName("/report/histogram_detail");
            }
            
            Map<String, String[]> parameterMap = request.getParameterMap();
            String whereValue = reportStatisticService.getWhereValue(parameterMap,chartManage.getChartTimeType());
            
            if (StringUtils.isNotEmpty(chartManage.getChartLegend())) {
                String[] chartLegends = chartManage.getChartLegend().split(";");
                view.addObject("chartLegends",  ResponseUtils.toJSONString(chartLegends));
            }
            
            Map<String, Object> categoryMap = chartService.getChartCategory(chartManage.getDataSourceSql(), whereValue);
            Map<String, Object> dataMap = chartService.getChartDatas(chartManage.getDataSourceSql(), whereValue);
            if (chartManage.getIsSupportDrillDown() != null && chartManage.getIsSupportDrillDown().equals(IS_SUPPORT_YES)) {
                List<Map<String, Object>> listCategory = chartService.getListCategory(chartManage.getDrillDownSourceSql(), categoryMap, whereValue);
                List<Map<String, Object>> listData = chartService.getListData(chartManage.getDrillDownSourceSql(), categoryMap, whereValue);
                view.addObject("listCategory", ResponseUtils.toJSONString(listCategory));
                view.addObject("listData", ResponseUtils.toJSONString(listData));
            }
            view.addObject("category", ResponseUtils.toJSONString(categoryMap));
            view.addObject("dataMap", ResponseUtils.toJSONString(dataMap));
        }
     
        view.addObject("chartManage", chartManage);
        view.addObject("chartId",chartId);
        return view;
    }

}
