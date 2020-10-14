package com.wa.framework.user.dao.impl;

import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.MeetRoomDao;
import com.wa.framework.user.model.MeetRoom;
import com.wa.framework.user.model.SysMeet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：会议室dao实现
 * 创建人：Administrator
 * 创建时间：2020/10/10  16:55
 */
@Repository("meetRoomDao")
public class MeetRoomDaoImpl extends BaseEntityDaoImpl<MeetRoom> implements MeetRoomDao {

    /**
     * 查询所有会议室
     * @return
     */
    @Override
    public List<MeetRoom> findAll() {
        String sql="select * from SYS_MEETROOM m where 1=1";
        List<MeetRoom> meetRooms = findBySql(sql, MeetRoom.class);
        return meetRooms;
    }

    /**
     * 根据id查询会议室
     * @param id
     * @return
     */
    @Override
    public MeetRoom findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MeetRoom.class);
        criteria.add(Restrictions.eq("id",id));
        List<MeetRoom> list = findByDetachedCriteria(criteria);
        return list.get(0);
    }
}
