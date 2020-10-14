package com.wa.framework.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * 描述：报表管理
 * 创建人：caohy
 * 创建时间：2016年7月25日下午3:26:54
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_REPORT_MANAGE")
public class SysReportManage implements java.io.Serializable  {

	public static final String PROP_REPORT_ID = "sysReportId";

	private String id;
	private String processState;
	private String processInstanceId;
	private String requestId;
	private String insertUserId;
	private Date insertDateTime;
	private String updateUserId;
	private Date updateDateTime;
	private String sysReportId;
	private String sysReportName;
	private String sysReportType;//bannb:半年报,nianb:年报,jib:季报,yueb:月报,jishib:即时报
	private Date reportStartTime;
	private String isSupportChooseTime;//bukf:不开放，kaif:开放
	private String optionTemplateFile;
	private String primaryReportFile;
	private String subReportFile;
	private String remark;
	private String sysReportTypeText;
	private String isSupportChooseTimeText;
	private String reportStatus;
	private String reportStatusText;
	
	
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

	@Column(name = "sys_report_id")
	public String getSysReportId() {
		return sysReportId;
	}

	public void setSysReportId(String sysReportId) {
		this.sysReportId = sysReportId;
	}

	@Column(name = "sys_report_name")
	public String getSysReportName() {
		return sysReportName;
	}

	public void setSysReportName(String sysReportName) {
		this.sysReportName = sysReportName;
	}

	@Column(name = "sys_report_type")
	public String getSysReportType() {
		return sysReportType;
	}

	public void setSysReportType(String sysReportType) {
		this.sysReportType = sysReportType;
	}

	@Column(name = "report_start_time")
	public Date getReportStartTime() {
		return reportStartTime;
	}

	public void setReportStartTime(Date reportStartTime) {
		this.reportStartTime = reportStartTime;
	}

	@Column(name = "is_support_choose_time")
	public String getIsSupportChooseTime() {
		return isSupportChooseTime;
	}

	public void setIsSupportChooseTime(String isSupportChooseTime) {
		this.isSupportChooseTime = isSupportChooseTime;
	}

	@Column(name = "option_template_file")
	public String getOptionTemplateFile() {
		return optionTemplateFile;
	}

	public void setOptionTemplateFile(String optionTemplateFile) {
		this.optionTemplateFile = optionTemplateFile;
	}

	@Column(name = "primary_report_file")
	public String getPrimaryReportFile() {
		return primaryReportFile;
	}

	public void setPrimaryReportFile(String primaryReportFile) {
		this.primaryReportFile = primaryReportFile;
	}

	@Column(name = "sub_report_file")
	public String getSubReportFile() {
		return subReportFile;
	}

	public void setSubReportFile(String subReportFile) {
		this.subReportFile = subReportFile;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sys_report_type_text")
	public String getSysReportTypeText() {
		return sysReportTypeText;
	}

	public void setSysReportTypeText(String sysReportTypeText) {
		this.sysReportTypeText = sysReportTypeText;
	}

	@Column(name = "is_support_choose_time_text")
	public String getIsSupportChooseTimeText() {
		return isSupportChooseTimeText;
	}

	public void setIsSupportChooseTimeText(String isSupportChooseTimeText) {
		this.isSupportChooseTimeText = isSupportChooseTimeText;
	}

	@Column(name = "report_status")
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	@Column(name = "report_status_text")
	public String getReportStatusText() {
		return reportStatusText;
	}

	public void setReportStatusText(String reportStatusText) {
		this.reportStatusText = reportStatusText;
	}
	
	@OneToOne(mappedBy = "sysReportManage")
	public ReportTemplateFile getReportTemplateFile() {
		return reportTemplateFile;
	}
	public void setReportTemplateFile(ReportTemplateFile reportTemplateFile) {
		this.reportTemplateFile = reportTemplateFile;
	}
	
	
}
