package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysMeet;

import java.util.Date;

public class SysMeet extends BaseSysMeet {
    private static final long serialVersionUID = 1L;


    public SysMeet() {

    }

    /**
     * Constructor for primary key
     * @param meetId
     */
    public SysMeet(String meetId) {
        super(meetId);
    }

    /**
     * Constructor for required fields
     * @param meetId
     * @param createUser
     * @param meetRoom
     * @param meetHost
     * @param meetTheme
     * @param startTime
     * @param endTime
     * @param email
     */
    public SysMeet(String meetId,
                   String createUser,
                   String meetRoom,
                   String meetHost,
                   String meetTheme,
                   Date startTime,
                   Date endTime,
                   String email) {
        super(
                meetId,
                createUser,
                meetRoom,
                meetHost,
                meetTheme,
                startTime,
                endTime,
                email);
    }


}
