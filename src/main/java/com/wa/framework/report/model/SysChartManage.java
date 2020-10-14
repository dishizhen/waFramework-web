package com.wa.framework.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 描述：图表管理
 * 创建人：guoyt
 * 创建时间：2016年8月2日下午1:41:39
 */
@Entity
@Table(name = "SYS_CHART_MANAGE")
public class SysChartManage implements java.io.Serializable  {

    private static final long serialVersionUID = -5444825400324764173L;

    public static final String PROP_CHART_ID = "sysChartId";

	private String id;
	private String processState;
	private String processInstanceId;
	private String requestId;
	private String insertUserId;
	private Date insertDateTime;
	private String updateUserId;
	private Date updateDateTime;
	private String sysChartId;
	private String chartTitle;
	private String chartSubTitle;
	private String sysChartType;//zhuzt:柱状图,tiaoxt:条形图,zhext:折线图,bingt:饼图
	private Date chartStartTime;
	private String isSupportChoose;//bukf:不开放，kaif:开放
	private String dataSourceSql;
	private String yAxisTitle;
	private String yAxisUnit;
	private String chartLegend;
	private String chartStatus;
	private String chartRemark;
	private String isDisplayValues;
	private String chartTimeType;
    private String isSupportExportChart;
    private String isSupportThreed;
    private String isAllowDecimals;
	private String sysChartTypeText;
	private String isSupportChooseText;
	private String chartStatusText;
	private String isDisplayValuesText;
	private String chartTimeTypeText;
    private String isSupportExportChartText;
    private String isSupportThreedText;
    private String isAllowDecimalsText;
    private String drillDownSourceSql;
	private String isSupportDrillDown;
	private String isSupportDrillDownText;
	
	private ReportTemplateFile reportTemplateFile;
	@Id
	@Column(name = "id", unique = false, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "process_state")
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	
	@Column(name = "process_instance_id")
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Column(name = "requestid")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "insert_userid")
	public String getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}

	@Column(name = "insert_datetime")
	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	@Column(name = "update_userid")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "update_datetime")
	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Column(name = "sys_chart_id")
	public String getSysChartId() {
        return sysChartId;
    }
    public void setSysChartId(String sysChartId) {
        this.sysChartId = sysChartId;
    }
    
    @Column(name = "chart_title")
    public String getChartTitle() {
        return chartTitle;
    }
    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }
    
    @Column(name = "chart_sub_title")
    public String getChartSubTitle() {
        return chartSubTitle;
    }
    public void setChartSubTitle(String chartSubTitle) {
        this.chartSubTitle = chartSubTitle;
    }
    
    @Column(name = "sys_chart_type")
    public String getSysChartType() {
        return sysChartType;
    }
    public void setSysChartType(String sysChartType) {
        this.sysChartType = sysChartType;
    }
    
    @Column(name = "chart_start_time")
    public Date getChartStartTime() {
        return chartStartTime;
    }
    public void setChartStartTime(Date chartStartTime) {
        this.chartStartTime = chartStartTime;
    }
    
    @Column(name = "is_support_choose")
    public String getIsSupportChoose() {
        return isSupportChoose;
    }
    public void setIsSupportChoose(String isSupportChoose) {
        this.isSupportChoose = isSupportChoose;
    }
    
    @Column(name = "data_source_sql")
    public String getDataSourceSql() {
        return dataSourceSql;
    }
    public void setDataSourceSql(String dataSourceSql) {
        this.dataSourceSql = dataSourceSql;
    }
    
    @Column(name = "y_axis_title")
    public String getyAxisTitle() {
        return yAxisTitle;
    }
    public void setyAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }
    
    @Column(name = "y_axis_unit")
    public String getyAxisUnit() {
        return yAxisUnit;
    }
    public void setyAxisUnit(String yAxisUnit) {
        this.yAxisUnit = yAxisUnit;
    }
    
    @Column(name = "chart_legend")
    public String getChartLegend() {
        return chartLegend;
    }
    public void setChartLegend(String chartLegend) {
        this.chartLegend = chartLegend;
    }
    
    @Column(name = "chart_status")
    public String getChartStatus() {
        return chartStatus;
    }
    public void setChartStatus(String chartStatus) {
        this.chartStatus = chartStatus;
    }
    
    @Column(name = "chart_remark")
    public String getChartRemark() {
        return chartRemark;
    }
    public void setChartRemark(String chartRemark) {
        this.chartRemark = chartRemark;
    }
    
    @Column(name = "is_display_values")
    public String getIsDisplayValues() {
        return isDisplayValues;
    }
    public void setIsDisplayValues(String isDisplayValues) {
        this.isDisplayValues = isDisplayValues;
    }
    
    @Column(name = "sys_chart_type_text")
    public String getSysChartTypeText() {
        return sysChartTypeText;
    }
    public void setSysChartTypeText(String sysChartTypeText) {
        this.sysChartTypeText = sysChartTypeText;
    }
    
    @Column(name = "is_support_choose_text")
    public String getIsSupportChooseText() {
        return isSupportChooseText;
    }
    public void setIsSupportChooseText(String isSupportChooseText) {
        this.isSupportChooseText = isSupportChooseText;
    }
    
    @Column(name = "chart_status_text")
    public String getChartStatusText() {
        return chartStatusText;
    }
    public void setChartStatusText(String chartStatusText) {
        this.chartStatusText = chartStatusText;
    }
    
    @Column(name = "is_display_values_text")
    public String getIsDisplayValuesText() {
        return isDisplayValuesText;
    }
    public void setIsDisplayValuesText(String isDisplayValuesText) {
        this.isDisplayValuesText = isDisplayValuesText;
    }
    
    @Column(name = "chart_time_type")
	public String getChartTimeType() {
        return chartTimeType;
    }
    public void setChartTimeType(String chartTimeType) {
        this.chartTimeType = chartTimeType;
    }
    
    @Column(name = "is_support_export_chart")
    public String getIsSupportExportChart() {
        return isSupportExportChart;
    }
    public void setIsSupportExportChart(String isSupportExportChart) {
        this.isSupportExportChart = isSupportExportChart;
    }
    
    @Column(name = "chart_time_type_text")
    public String getChartTimeTypeText() {
        return chartTimeTypeText;
    }
    public void setChartTimeTypeText(String chartTimeTypeText) {
        this.chartTimeTypeText = chartTimeTypeText;
    }
    
    @Column(name = "is_support_export_chart_text")
    public String getIsSupportExportChartText() {
        return isSupportExportChartText;
    }
    public void setIsSupportExportChartText(String isSupportExportChartText) {
        this.isSupportExportChartText = isSupportExportChartText;
    }
    
    @Column(name = "is_support_threed")
    public String getIsSupportThreed() {
        return isSupportThreed;
    }
    public void setIsSupportThreed(String isSupportThreed) {
        this.isSupportThreed = isSupportThreed;
    }
    
    @Column(name = "is_support_threed_text")
    public String getIsSupportThreedText() {
        return isSupportThreedText;
    }
    public void setIsSupportThreedText(String isSupportThreedText) {
        this.isSupportThreedText = isSupportThreedText;
    }
    
    @Column(name = "is_allow_decimals")
    public String getIsAllowDecimals() {
        return isAllowDecimals;
    }
    public void setIsAllowDecimals(String isAllowDecimals) {
        this.isAllowDecimals = isAllowDecimals;
    }
    
    @Column(name = "is_allow_decimals_text")
    public String getIsAllowDecimalsText() {
        return isAllowDecimalsText;
    }
    public void setIsAllowDecimalsText(String isAllowDecimalsText) {
        this.isAllowDecimalsText = isAllowDecimalsText;
    }
    
    @OneToOne(mappedBy = "sysChartManage")
	public ReportTemplateFile getReportTemplateFile() {
		return reportTemplateFile;
	}
	public void setReportTemplateFile(ReportTemplateFile reportTemplateFile) {
		this.reportTemplateFile = reportTemplateFile;
	}
	
	@Column(name = "DRILLDATA_SOURCE_SQL")
    public String getDrillDownSourceSql() {
        return drillDownSourceSql;
    }
    public void setDrillDownSourceSql(String drillDownSourceSql) {
        this.drillDownSourceSql = drillDownSourceSql;
    }
    
    @Column(name = "is_support_drilldown")
    public String getIsSupportDrillDown() {
        return isSupportDrillDown;
    }
    public void setIsSupportDrillDown(String isSupportDrillDown) {
        this.isSupportDrillDown = isSupportDrillDown;
    }
    
    @Column(name = "is_support_drilldown_text")
    public String getIsSupportDrillDownText() {
        return isSupportDrillDownText;
    }
    public void setIsSupportDrillDownText(String isSupportDrillDownText) {
        this.isSupportDrillDownText = isSupportDrillDownText;
    }
	
	
}
