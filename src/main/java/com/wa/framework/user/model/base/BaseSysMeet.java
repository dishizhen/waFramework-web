package com.wa.framework.user.model.base;

import javax.persistence.Transient;
import java.util.Date;

public abstract class BaseSysMeet {

    private static final long serialVersionUID=-3290511862168291156L;

    public static String REF="SysMeetDao";
    public static String PROP_MEET_ID="meetId";
    public static String PROP_CREATE_USER="createUser";
    public static String PROP_MEET_ROOM="meetRoom";
    public static String PROP_MEET_HOST="meetHost";
    public static String PROP_MEET_THEME="meetTheme";
    public static String PROP_START_TIME="startTime";
    public static String PROP_END_TIME="endTime";
    public static String PROP_EMAIL="email";

    //constructors
    public BaseSysMeet() {
        initialize();
    }

    /**
     * Consructor for primary key
     * @param meetId
     */
    public BaseSysMeet(String meetId) {
        this.meetId = meetId;
        initialize();
    }

    public BaseSysMeet(
            String meetId,
            String createUser,
            String meetRoom,
            String meetHost,
            String meetTheme,
            Date startTime,
            Date endTime,
            String email) {
        this.meetId = meetId;
        this.createUser = createUser;
        this.meetRoom = meetRoom;
        this.meetHost = meetHost;
        this.meetTheme = meetTheme;
        this.startTime = startTime;
        this.endTime = endTime;
        this.email = email;
        initialize();
    }

    protected void initialize(){}

    private int hashCode=Integer.MIN_VALUE;

    //primary key
    private java.lang.String meetId;

    //fields
    private java.lang.String createUser;
    private java.lang.String meetRoom;
    private java.lang.String meetHost;
    private java.lang.String meetTheme;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private java.lang.String email;

    private String meetVisitor;


    @Transient
    private String meetState;



    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
        this.hashCode=Integer.MIN_VALUE;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getMeetRoom() {
        return meetRoom;
    }

    public void setMeetRoom(String meetRoom) {
        this.meetRoom = meetRoom;
    }

    public String getMeetHost() {
        return meetHost;
    }

    public void setMeetHost(String meetHost) {
        this.meetHost = meetHost;
    }

    public String getMeetTheme() {
        return meetTheme;
    }

    public void setMeetTheme(String meetTheme) {
        this.meetTheme = meetTheme;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof com.wa.framework.user.model.SysMeet)) return false;
        else {
            com.wa.framework.user.model.SysMeet sysMeet = (com.wa.framework.user.model.SysMeet) obj;
            if (null == this.getMeetId() || null == sysMeet.getMeetId()) return false;
            else return (this.getMeetId().equals(sysMeet.getMeetId()));
        }
    }
    public int hashCode(){
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getMeetId()) return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getMeetId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }



    public String getMeetVisitor() {
        return meetVisitor;
    }

    public void setMeetVisitor(String meetVisitor) {
        this.meetVisitor = meetVisitor;
    }

    public String getMeetState() {
        return meetState;
    }

    public void setMeetState(String meetState) {
        this.meetState = meetState;
    }

    @Override
    public String toString() {
        return "BaseSysMeet{" +
                "hashCode=" + hashCode +
                ", meetId='" + meetId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", meetRoom='" + meetRoom + '\'' +
                ", meetHost='" + meetHost + '\'' +
                ", meetTheme='" + meetTheme + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", email='" + email + '\'' +
                ", meetVisitor='" + meetVisitor + '\'' +
                ", meetState='" + meetState + '\'' +
                '}';
    }
}
