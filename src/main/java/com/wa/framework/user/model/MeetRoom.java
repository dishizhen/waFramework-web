package com.wa.framework.user.model;

/**
 * 描述：会议室
 * 创建人：disz
 * 创建时间：2020/10/1016:42
 */
public class MeetRoom {
    private static final long serialVersionUID=1L;

    private String id;
    private String meetRoom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetRoom() {
        return meetRoom;
    }

    public void setMeetRoom(String meetRoom) {
        this.meetRoom = meetRoom;
    }

    @Override
    public String toString() {
        return "MeetRoom{" +
                "id='" + id + '\'' +
                ", meetRoom='" + meetRoom + '\'' +
                '}';
    }
}
