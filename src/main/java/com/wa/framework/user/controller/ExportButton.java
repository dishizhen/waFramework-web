package com.wa.framework.user.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 导出按钮
 * @author Administrator
 *
 */
public class ExportButton extends TagSupport {

    private static final long serialVersionUID = 6277937657178232464L;

    private static final Log logger = LogFactory.getLog(ExportButton.class);

    private String id;
    private String exportUrl;
    private String gridId;
    private String exportType;
    private String getAllUrl;
    @Override
    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();
        Configuration config = new Configuration();
        try {
            config.setDirectoryForTemplateLoading(new File(ExportButton.class.getResource("/templates").toURI()));
            config.setObjectWrapper(new DefaultObjectWrapper());
            Template template = config.getTemplate("exportGrid.ftl", "UTF-8");
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("id", id);
            root.put("gridId", gridId);
            root.put("exportType", exportType);
            root.put("exportUrl", exportUrl);
            root.put("getAllUrl", getAllUrl);
            template.process(root, writer);
            writer.flush();
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (TemplateException e) {
            logger.error(e.getMessage(), e);
        }
        return (SKIP_BODY);
    }

    @Override
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public String getGridId() {
        return gridId;
    }

    public String getGetAllUrl() {
        return getAllUrl;
    }

    public void setGetAllUrl(String getAllUrl) {
        this.getAllUrl = getAllUrl;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

}
