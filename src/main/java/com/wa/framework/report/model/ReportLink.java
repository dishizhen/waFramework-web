package com.wa.framework.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：报表图表位置关系表
 * 创建人：guoyt
 * 创建时间：2016年8月2日下午1:41:39
 */
@Entity
@Table(name = "REPORTLINK")
public class ReportLink implements java.io.Serializable  {

    private static final long serialVersionUID = -5444825400324764173L;

    public static final String PROP_REPORT_ID = "sysReportId";
    public static final String PROP_CHART_ID = "sysChartId";
    
    public static final String LOCATION_RIGHT = "right";
    public static final String LOCATION_LEFT = "left";
    public static final String LOCATION_TOP = "top";
    public static final String LOCATION_BOTTOM = "bottom";

	private String id;
	private String sysReportId;
	private String sysChartId;
	private String locationType;  //right,left,top,bottom
	
	@Id
	@Column(name = "id", unique = false, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "sys_report_id")
    public String getSysReportId() {
        return sysReportId;
    }
    public void setSysReportId(String sysReportId) {
        this.sysReportId = sysReportId;
    }
    
    @Column(name = "sys_chart_id")
    public String getSysChartId() {
        return sysChartId;
    }
    public void setSysChartId(String sysChartId) {
        this.sysChartId = sysChartId;
    }
    
    @Column(name = "location_type")
    public String getLocationType() {
        return locationType;
    }
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
	
}
