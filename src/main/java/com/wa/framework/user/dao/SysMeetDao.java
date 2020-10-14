package com.wa.framework.user.dao;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysMeet;
import org.hibernate.criterion.DetachedCriteria;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * 描述：会议室dao
 * 创建人：disz
 * 创建时间：
 */
public interface SysMeetDao extends BaseEntityDao<SysMeet> {

    /**
     * 根据会议室id，开始时间，结束时间查询，0代表没有，其他代表有
     * @param meetId
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer getCountByMeetId(String meetId,Date startTime,Date endTime);

    /**
     * 根据会议室和会议时间查询
     * @param page
     * @param meetRoom
     * @param startTime
     * @param endTime
     * @return
     */
    Pageable<SysMeet> findMeet(Page page, String meetRoom, Date startTime,Date endTime);

    /**
     * 根据查询条件分页查询
     * @param criteria
     * @param page
     * @return
     */
    Pageable<SysMeet> getMeetList(DetachedCriteria criteria,Page page);


    Pageable<SysMeet> findMeetWithRoom(Page page,String meetRoom,String startTime,String endTime,String createUser );


    List<SysMeet> findByRoom(String meetRoom);


}
