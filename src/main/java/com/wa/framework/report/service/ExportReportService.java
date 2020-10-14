package com.wa.framework.report.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wa.framework.common.ComConstants;
import com.wa.framework.common.PropertyConfigurer;
import com.wa.framework.log.ExpLog;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

/**
 * 描述：报表导出service
 * 创建人：guoyt
 * 创建时间：2016年7月22日上午10:31:59
 * 修改人：guoyt
 * 修改时间：2016年7月22日上午10:31:59
 */
@SuppressWarnings("deprecation")
@ExpLog(type="报表导出")
public class ExportReportService extends BaseHttpServlet {

    private static final long serialVersionUID = 8199781393773908551L;

    private static final Log logger = LogFactory.getLog(ExportReportService.class);

    /**
     * @Description: 导出PDF
     * @param: @param request
     * @param: @param response
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public void service(String defaultFilename, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<JasperPrint> jasperPrintList = BaseHttpServlet.getJasperPrintList(request);

        OutputStream outputStream = null;
        if (jasperPrintList == null) {
            logger.error("ExportReportService : service(), JasperPrint文件不存在.");
            throw new ServletException("JasperPrint文件不存在.");
        }

        try {
            JasperPrint jasperPrint = jasperPrintList.get(0);
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            
            String defaultname = defaultFilename + ".pdf";
            String fileName = new String(defaultname.getBytes("gbk"), "utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (JRException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
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
    @SuppressWarnings("resource")
    public static void exportExcel(String defaultFilename, HttpServletRequest request, HttpServletResponse response,
                    String reportId) throws IOException, JRException, ServletException {
       
        String basePath =  PropertyConfigurer.getValue(ComConstants.FILE_PATH);
        String filenurl =  basePath + "/reports/" + reportId + ".jasper";
        File file = new File(filenurl);
        InputStream is = null;
        is = new FileInputStream(file);
        List<JasperPrint> jasperPrintList = BaseHttpServlet.getJasperPrintList(request);

        if (jasperPrintList == null) {
            logger.error("ExportReportService : exportExcel(), JasperPrint文件不存在.");
            throw new ServletException("JasperPrint文件不存在.");
        }

        JasperPrint jasperPrint = jasperPrintList.get(0);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
        prepareReport(jasperReport, "excel");
        
        //设置头信息
        response.setContentType("application/vnd.ms-excel");
        String defaultname = defaultFilename + ".xls";
        String fileName = new String(defaultname.getBytes("gbk"), "utf-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.exportReport();
            outputStream.flush();
        } catch (JRException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        
    }

    /**
     * @Description: 如果导出的是excel，则需要去掉周围的margin
     * @param: @param jasperReport
     * @param: @param type
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private static void prepareReport(JasperReport jasperReport, String type) {
        if ("excel".equals(type)){
            try {
                Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("topMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("bottomMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                Field pageHeight = JRBaseReport.class.getDeclaredField("pageHeight");
                pageHeight.setAccessible(true);
                pageHeight.setInt(jasperReport, 2147483647);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
