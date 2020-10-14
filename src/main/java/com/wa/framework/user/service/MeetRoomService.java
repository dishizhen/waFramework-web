package com.wa.framework.user.service;

import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.MeetRoomDao;
import com.wa.framework.user.model.MeetRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：会议室service
 * 创建人：disz
 * 创建时间：2020/10/10  17:13
 */
@Service("meetRoomService")
@ExpLog(type="会议室")
public class MeetRoomService extends BaseService {
    @Autowired
    @Qualifier("meetRoomDao")
    private MeetRoomDao meetRoomDao;

    /**
     * 查询所有会议室
     * @return
     */
    public List<MeetRoom> findAll(){
        return meetRoomDao.findAll();
    }

    /**
     * 通过id查会议室
     * @param id
     * @return
     */
    public MeetRoom findById(String id){
        return meetRoomDao.findById(id);
    }

}
