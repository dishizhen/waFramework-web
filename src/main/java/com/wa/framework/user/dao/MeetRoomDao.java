package com.wa.framework.user.dao;

import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.MeetRoom;

import java.util.List;

/**
 * 描述：会议室dao
 * 创建人：disz
 * 创建时间：2020/10/10 16:52
 */
public interface MeetRoomDao extends BaseEntityDao<MeetRoom> {

    List<MeetRoom> findAll();

    MeetRoom findById(String id);
}
