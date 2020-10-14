package com.wa.framework.report.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.QueryCondition;
import com.wa.framework.common.ComConstants;
import com.wa.framework.common.PropertyConfigurer;
import com.wa.framework.controller.BaseController;
import com.wa.framework.report.model.ReportLink;
import com.wa.framework.report.model.ReportTemplateFile;
import com.wa.framework.report.model.SysChartManage;
import com.wa.framework.report.model.SysReportManage;
import com.wa.framework.report.service.ExportReportService;
import com.wa.framework.report.service.ReportStatisticService;
import com.wa.framework.report.util.ConnectionProvider;
import com.wa.framework.report.util.ReportDateUtil;
import com.wa.framework.report.vo.DataInfoVo;
import com.wa.framework.service.BaseService;
import com.wa.framework.util.DateUtils;

/**
 * 描述：报表控制类
 * 创建人：guoyt
 * 创建时间：2016年7月22日上午10:32:17
 * 修改人：guoyt
 * 修改时间：2016年7月22日上午10:32:17
 */
@SuppressWarnings("deprecation")
@Controller("/report")
@RequestMapping(value = "/report")
public class ReportStatisticController extends BaseController<Object, Object> {

    private static final Log logger = LogFactory.getLog(ReportStatisticController.class);

    @Autowired
    private ReportStatisticService reportStatisticService;
    
    @Autowired
    private BaseService baseService;
    
    @Autowired
    private ExportReportService exportReportService;
    
    private static final String NEXT_PAGE = "1";//上一页
    private static final String LAST_PAGE = "2";//下一页

    /**
     * @Description: 跳转报表模板页面
     * @param: @param request
     * @param: @param reportName
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/pagedetail/{reportId}")
    public ModelAndView pagedetail(HttpServletRequest request, @PathVariable String reportId,
                    HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        ReportTemplateFile temFile = null;
        
        // 图表和报表控制都在这里
        SysReportManage manage = baseService.unique(SysReportManage.class, QueryCondition.eq(
        		SysReportManage.PROP_REPORT_ID, reportId));
        
        String times = "";
        String reportType = "";
        Date reportStartTime = null;
       
        if (manage != null) {
            temFile = baseService.unique(ReportTemplateFile.class,
                            QueryCondition.eq(ReportTemplateFile.PROP_REPORT_ID, manage));
            times = ReportDateUtil.getTimeByReportType(manage.getSysReportType());
            reportType = manage.getSysReportType();
            reportStartTime = manage.getReportStartTime();
            view.addObject("manage", manage);
        } else {
            SysChartManage chartmanage = baseService.unique(SysChartManage.class,
                            QueryCondition.eq(SysChartManage.PROP_CHART_ID, reportId));
            if (chartmanage != null) {
                temFile = baseService.unique(ReportTemplateFile.class,
                                QueryCondition.eq(ReportTemplateFile.PROP_CHART_ID, chartmanage));
                times = ReportDateUtil.getTimeByReportType(chartmanage.getChartTimeType());
                reportType = chartmanage.getChartTimeType();
                reportStartTime = chartmanage.getChartStartTime();
                view.addObject("chartManage", chartmanage); 
            }
        }
        
        if (temFile == null || StringUtils.isEmpty(temFile.getTemplateInfo())){
            view.addObject("isHasTemFile", false);
        } else if (StringUtils.isNotEmpty(temFile.getTemplateInfo()) && StringUtils.isEmpty(temFile.getTemplateInfo().trim())){
            view.addObject("isHasTemFile", false);
        } else {
            view.addObject("isHasTemFile", true);
        }
        
        view.addObject("timesId", times);
		view.addObject("temFile", temFile);
		view.addObject("reportId", reportId);
		
		List<DataInfoVo> beginlist = new ArrayList<DataInfoVo>();
		List<DataInfoVo> endlist = new ArrayList<DataInfoVo>();
		
		if(reportStartTime != null && StringUtils.isNotEmpty(reportType)){
		    if(ReportDateUtil.HALF_YEAR_REPORT.equals(reportType)){
	            beginlist = ReportDateUtil.getHalfListByDate(reportStartTime);
	        }else if(ReportDateUtil.QUARTER_REPORT.equals(reportType)){
	            beginlist = ReportDateUtil.getQuarterListByDate(reportStartTime);
	        }else if(ReportDateUtil.YEAR_REPORT.equals(reportType)){
	            beginlist = ReportDateUtil.getYearListByYear(reportStartTime);
	        }else if(ReportDateUtil.MONTH_REPORT.equals(reportType)){
	            beginlist = ReportDateUtil.getMonthStartListByDate(reportStartTime);
	            endlist = ReportDateUtil.getEndStartListByDate(reportStartTime);
	        }
		}
		
		view.addObject("dataInfoVoBegin", beginlist);
		view.addObject("dataInfoVoEnd", endlist);
		request.getSession().setAttribute("pageIndex", 0);
        view.setViewName("/report/report_template_list");
        return view;
    }
    
    /**
     * @Description: 跳转报表图表组合模板页面
     * @param: @param request
     * @param: @param reportName
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/pagedetail/{reportId}/{chartId}")
    public ModelAndView pagedetail(HttpServletRequest request, @PathVariable String reportId,
                    @PathVariable String chartId, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        
        // 图表和报表控制都在这里
        SysReportManage manage = baseService.unique(SysReportManage.class, QueryCondition.eq(
                SysReportManage.PROP_REPORT_ID, reportId));
        
        SysChartManage chartmanage = baseService.unique(SysChartManage.class,
                        QueryCondition.eq(SysChartManage.PROP_CHART_ID, chartId));
        
        List<ReportLink> reportLinks = baseService.find(ReportLink.class,
                        QueryCondition.eq(ReportLink.PROP_REPORT_ID, reportId));
        if (reportLinks != null && reportLinks.size() > 0){
            ReportLink reportLink = reportLinks.get(0);
            view.addObject("reportLink", reportLink);
        } else {
            ReportLink reportLink = new ReportLink();
            reportLink.setSysChartId(chartId);
            reportLink.setSysReportId(reportId);
            reportLink.setLocationType(ReportLink.LOCATION_TOP);
            view.addObject("reportLink", reportLink);
        }
        
        view.addObject("reportId", reportId);
        view.addObject("chartId", chartId);
        view.addObject("manage", manage);
        view.addObject("chartmanage", chartmanage);
        
        ReportTemplateFile temFile = null;
        String times = "";
        String reportType = "";
        Date reportStartTime = null;
       
        if (manage != null) {
            temFile = baseService.unique(ReportTemplateFile.class,
                            QueryCondition.eq(ReportTemplateFile.PROP_REPORT_ID, manage));
            times = ReportDateUtil.getTimeByReportType(manage.getSysReportType());
            reportType = manage.getSysReportType();
            reportStartTime = manage.getReportStartTime();
        }
        
        if (temFile == null || StringUtils.isEmpty(temFile.getTemplateInfo())){
            view.addObject("isHasTemFile", false);
        } else if (StringUtils.isNotEmpty(temFile.getTemplateInfo()) && StringUtils.isEmpty(temFile.getTemplateInfo().trim())){
            view.addObject("isHasTemFile", false);
        } else {
            view.addObject("isHasTemFile", true);
        }
        
        view.addObject("temFile", temFile);
        view.addObject("timesId", times);
        
        List<DataInfoVo> beginlist = new ArrayList<DataInfoVo>();
        List<DataInfoVo> endlist = new ArrayList<DataInfoVo>();
        
        if(reportStartTime != null && StringUtils.isNotEmpty(reportType)){
            if(ReportDateUtil.HALF_YEAR_REPORT.equals(reportType)){
                beginlist = ReportDateUtil.getHalfListByDate(reportStartTime);
            }else if(ReportDateUtil.QUARTER_REPORT.equals(reportType)){
                beginlist = ReportDateUtil.getQuarterListByDate(reportStartTime);
            }else if(ReportDateUtil.YEAR_REPORT.equals(reportType)){
                beginlist = ReportDateUtil.getYearListByYear(reportStartTime);
            }else if(ReportDateUtil.MONTH_REPORT.equals(reportType)){
                beginlist = ReportDateUtil.getMonthStartListByDate(reportStartTime);
                endlist = ReportDateUtil.getEndStartListByDate(reportStartTime);
            }
        }
        
        view.addObject("dataInfoVoBegin", beginlist);
        view.addObject("dataInfoVoEnd", endlist);
        request.getSession().setAttribute("pageIndex", 0);
        view.setViewName("/report/report_chart_list");
        return view;
    }
    
    /**
     * @Description: 跳转报表详细页面
     * @param: @param request
     * @param: @param reportName
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/reportDetail")
    public ModelAndView reportDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String reportId = request.getParameter("reportId");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter(); 
        Integer reportSize = 0;
        Connection connection = null;
        try {
            connection = ConnectionProvider.getConnection();

            String basePath =  PropertyConfigurer.getValue(ComConstants.FILE_PATH);
            String reportFileName =  basePath + "/reports/" + reportId + ".jasper";
            
            Map<String, Object> parameters = new HashMap<String, Object>();

            Map<String, String[]> parameterMap = request.getParameterMap();
            SysReportManage manage = baseService.unique(
                            SysReportManage.class,
                            QueryCondition.eq(SysReportManage.PROP_REPORT_ID, reportId));
            String whereValue = reportStatisticService.getWhereValue(parameterMap,manage.getSysReportType());
            parameters.put("whereValue", whereValue);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, connection);

            JRHtmlExporter exporter = new JRHtmlExporter();

            Map imageMaps = new HashMap();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imageMaps);

            String imgServDirUrl = "/images/";
            File imgRealDir = new File(basePath + imgServDirUrl);
            if (!imgRealDir.exists()) {
                imgRealDir.mkdirs();
            }
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imgServDirUrl);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR, imgRealDir);
            exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);

            List<JRPrintPage> sizeList = jasperPrint.getPages();
            reportSize = sizeList.size();
            request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            request.getSession().setAttribute("IMAGES_MAP", imageMaps);
            request.getSession().setAttribute("repoprt.size", reportSize);
            request.getSession().setAttribute("exportPrintObject", exporter);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            ConnectionProvider.closeConnection(connection);
        }

        ModelAndView view = new ModelAndView();
        String pageType = request.getParameter("pageType");
        int pageIndex = Integer.parseInt(request.getSession().getAttribute("pageIndex").toString());
        String pageNum = request.getParameter("pageNum");
        if(pageNum != null && !"".equals(pageNum)){
            pageIndex = Integer.valueOf(pageNum) - 1;
            if(pageIndex > (reportSize - 1)){
                pageIndex = reportSize - 1;
            }
            if(pageIndex < 0){
                pageIndex = 0;
            }
            request.getSession().setAttribute("pageIndex",pageIndex);
        }else{
            if(NEXT_PAGE.equals(pageType)){
            	if(pageIndex < (reportSize-1)){
            		pageIndex += 1;
            		request.getSession().setAttribute("pageIndex",pageIndex);
            	}else{
            		request.getSession().setAttribute("pageIndex",pageIndex);
            	}
            }else if(LAST_PAGE.equals(pageType)){
                if(pageIndex > 0){
                    pageIndex = pageIndex - 1;
                }
                request.getSession().setAttribute("pageIndex",pageIndex);
            }else{
            	request.getSession().setAttribute("pageIndex",0);
            }
        }
        
        view.addObject("reportId",reportId);
        view.addObject("pageIndex",pageIndex);
        view.addObject("reportSize",reportSize);
        view.setViewName("/report/report_detail");
        return view;
    }
    
    /**
     * @Description:打印预览
     * @param: @param request
     * @param: @param response
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/printPreview")
    public ModelAndView printPreview(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        Date date = new Date();
        mv.addObject("timer", String.valueOf(date.getTime()));
        mv.setViewName("/report/swf");
        return mv;
    }
    
    /**
     * @Description: 导出PDF
     * @param: @param request
     * @param: @param response
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(HttpServletRequest request, HttpServletResponse response) {
        try {
            String reportId = request.getParameter("reportId");
            String defaultFilename = getDefaultFilename(reportId);
            exportReportService.service(defaultFilename, request, response);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServletException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @Description: 导出EXCEL
     * @param: @param request
     * @param: @param response
     * @param: @param names
     * @param: @throws JRException
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws JRException {
        try {
            String reportId = request.getParameter("reportId");
            String defaultFilename = getDefaultFilename(reportId);
            exportReportService.exportExcel(defaultFilename, request, response, reportId);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServletException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @Description: 获取导出文件名称
     * @param: @param reportId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    private String getDefaultFilename(String reportId) {
        SysReportManage manage = baseService.unique(SysReportManage.class, QueryCondition.eq(
                        SysReportManage.PROP_REPORT_ID, reportId));
        String defaultFilename = null;
        if (manage != null){
            defaultFilename = manage.getSysReportName() + DateUtils.format(new Date(), "yyyyMMdd");
        } else {
            defaultFilename = DateUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        }
        return defaultFilename;
    }
    
    /**
     * @Description: 跳转自定义查询条件编辑页面
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/openEditOption")
    public ModelAndView openEditOption(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        String businessKey = request.getParameter("businessKey");
        ReportTemplateFile temFile = null;
        SysReportManage manage = baseService.findById(SysReportManage.class,businessKey);
        if(manage != null){
        	 temFile = baseService.unique(
        			ReportTemplateFile.class, QueryCondition.eq(
        					ReportTemplateFile.PROP_REPORT_ID, manage));
        	 view.addObject("isReport", true);
        } else {
            SysChartManage chartManage = baseService.findById(SysChartManage.class,businessKey);
            if(chartManage != null){
                temFile = baseService.unique(
                                ReportTemplateFile.class, QueryCondition.eq(
                                        ReportTemplateFile.PROP_CHART_ID, chartManage));
                view.addObject("isChart", true);
            }
        }
        view.addObject("temFile",temFile);
        view.addObject("businessKey",businessKey);
        view.setViewName("/report/report_edit_option");
        return view;
    }

    /**
     * @Description: 跳转到运行预览页面
     * @param: @param request
     * @param: @param reportName
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.7
     */
	@RequestMapping("/toEmpty")
	public ModelAndView toEmpty(HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {
		ModelAndView view = new ModelAndView();
		view.setViewName("/report/empty");
		return view;
	}
	
	/**
	 * @Description: 保存报表自定义查询条件
	 * @param: @param temFile
	 * @param: @param request
	 * @param: @param writer
	 * @param: @throws Exception
	 * @return: void
	 * @throws
	 * @since JDK 1.7
	 */
	@RequestMapping(value ="/temSave", method = RequestMethod.POST)
	public void temSave(ReportTemplateFile temFile, HttpServletRequest request,
			Writer writer) throws Exception {
		String json = "";
       try{
    	   json = reportStatisticService.temSave(temFile);
    	   
       }catch(Exception e){
    	   logger.error(e.getMessage(), e);
       }
		writer.write(json);
	}
}
