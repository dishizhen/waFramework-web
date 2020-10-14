package com.wa.framework.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：报表自定义查询条件
 * 创建人：caohy
 * 创建时间：2016年7月25日上午11:08:11
 */
@Entity
@Table(name = "REPORT_TEMPLATE_FILE")
public class ReportTemplateFile implements java.io.Serializable {
	private static final long serialVersionUID = 5454155125314435342L;
	
	public static final String PROP_REPORT_ID = "sysReportManage";
	public static final String PROP_CHART_ID = "sysChartManage";
    private String id;
    private SysReportManage sysReportManage;
    private SysChartManage sysChartManage;
    private String templateInfo;
    
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name = "sys_report_id")
	public SysReportManage getSysReportManage() {
		return sysReportManage;
	}
	public void setSysReportManage(SysReportManage sysReportManage) {
		this.sysReportManage = sysReportManage;
	}
	
	@OneToOne
    @JoinColumn(name = "sys_chart_id")
	public SysChartManage getSysChartManage() {
        return sysChartManage;
    }
    public void setSysChartManage(SysChartManage sysChartManage) {
        this.sysChartManage = sysChartManage;
    }
    
    @Column(name = "TEMPLATEINFO",  length = 65535)
	public String getTemplateInfo() {
		return templateInfo;
	}
	public void setTemplateInfo(String templateInfo) {
		this.templateInfo = templateInfo;
	}
	
}
