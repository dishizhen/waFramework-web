package com.wa.framework.user.model;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 描述：系统消息实体类
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:50:40
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:50:40
 */
@Entity
@Table(name = "SYS_MESSAGE")
public class SysMessage implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String content;
    private Date sendDate;
    private String senderId;
    private String receiverId;
    private Boolean state;
    private String sendName;
    private String taskId;
    private String instanceId;
    
    private String recordId; //阅知记录

    public SysMessage() {
    }

    public SysMessage(String id) {
        this.id = id;
    }


    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID", unique = true, nullable = false, length = 50)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TITLE", length = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "CONTENT", length = 500)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SEND_DATE", length = 10)
    public Date getSendDate() {
        return this.sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Column(name = "SENDER_ID", length = 50)
    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Column(name = "RECEIVER_ID", length = 50)
    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name = "STATE", length = 10)
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Transient
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Column(name = "INSTANCE_ID", length = 50)
    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    
    @Transient
    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
    
    @Transient
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
