package com.wa.framework.user.dao.impl;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.SimplePageable;
import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysMeetDao;
import com.wa.framework.user.model.SysMeet;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 描述：会议室dao实现类
 * 创建人：disz
 * 创建时间：
 */
@Repository("sysMeetDao")
public class SysMeetDaoImpl extends BaseEntityDaoImpl<SysMeet> implements SysMeetDao {

    /**
     * 根据会议室id，开始时间，结束时间查询，0代表没有，其他代表有
     * @param meetId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getCountByMeetId(String meetId, Date startTime, Date endTime) {
        Criteria criteria = this.getSession().createCriteria(SysMeet.class);
        criteria.add(Restrictions.eq("meetId",meetId));
        criteria.add(Restrictions.lt("startTime",startTime));
        criteria.add(Restrictions.gt("endTime",endTime));
        criteria.setProjection(Projections.rowCount());
        int totalRecords = ((Long) criteria.uniqueResult()).intValue();
        return totalRecords;
    }

    /**
     * 根据会议室和会议时间查询
     * @param page
     * @param meetRoom
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Pageable<SysMeet> findMeet(Page page, String meetRoom, Date startTime,Date endTime) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SysMeet.class);
        if (StringUtils.isNotBlank(meetRoom)){
            criteria.add(Restrictions.like("meetRoom","%"+meetRoom+"%"));
        }
        if (startTime!=null){
            criteria.add(Restrictions.ge("startTime",startTime));
        }
        if (endTime!=null){
            criteria.add(Restrictions.le("endTime",endTime));
        }
        Pageable<SysMeet> pageable = findByDetachedCriteriaWithPage(criteria, page);
        return pageable;
    }

    /**
     * 根据查询条件分页查询
     * @param criteria
     * @param page
     * @return
     */
    @Override
    public Pageable<SysMeet> getMeetList(DetachedCriteria criteria, Page page) {
        return findByDetachedCriteriaWithPage(criteria,page);
    }

    /**
     * 分页查询会议预定
     * @param page
     * @param meetRoom
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Pageable<SysMeet> findMeetWithRoom(Page page, String meetRoom, String startTime, String endTime,String createUser) {
        List lst=new ArrayList();
        Pageable<SysMeet> pageable=new SimplePageable<SysMeet>();

        DetachedCriteria criteria = DetachedCriteria.forClass(SysMeet.class);
        StringBuffer createSqlQuery=new  StringBuffer("from SysMeet where 1=1  ");
        StringBuffer countSqlQuery=new  StringBuffer("select count(*) from SysMeet where 1=1 ");
        Date beginTime=null;
        Date finishTime=null;



        if (StringUtils.isNotEmpty(meetRoom)){
            if(meetRoom.contains("%")||meetRoom.contains("_")){
                meetRoom=meetRoom.replace("%","\\%").replace("_", "\\_");
                lst.add("%"+ meetRoom+ "%");
                createSqlQuery.append(" and meetRoom like ?  escape '\\' ");
                countSqlQuery.append(" and meetRoom like ?  escape '\\' ");
            }else{
                lst.add("%"+ meetRoom+ "%");
                createSqlQuery.append(" and meetRoom like ? ");
                countSqlQuery.append(" and meetRoom like ? ");
            }
        }

        if (StringUtils.isNotEmpty(startTime)){
            beginTime=DateUtils.parseDate(startTime,DateUtils.YYYYMMDDHHMMSS_19);
            createSqlQuery.append(" and startTime > ? ");
            countSqlQuery.append(" and startTime > ?  ");
            lst.add(beginTime);
        }
        if (StringUtils.isNotEmpty(endTime)){
            finishTime=DateUtils.parseDate(endTime,DateUtils.YYYYMMDDHHMMSS_19);
            createSqlQuery.append(" and endTime < ? ");
            countSqlQuery.append(" and endTime < ?  ");
            lst.add(finishTime);
        }

        if (StringUtils.isNotEmpty(createUser)){
            lst.add(createUser);
            createSqlQuery.append(" and createUser is ? ");
            countSqlQuery.append(" and createUser is ? ");

        }

        pageable=findByHqlWithPage(createSqlQuery.toString(),countSqlQuery.toString(),page,lst);
        List<SysMeet> list = pageable.getList();
        for (SysMeet meet:list
             ) {
            if (new Date().compareTo(meet.getStartTime())<0){
                meet.setMeetState("未开始");
            }
            else if (new Date().compareTo(meet.getStartTime())>0&&new Date().compareTo(meet.getEndTime())<0){
                meet.setMeetState("进行中");
            }else {
                meet.setMeetState("过期");
            }
        }
        return pageable;
    }

    /**
     * 根据会议室查询预定会议室
     * @param meetRoom
     * @return
     */
    @Override
    public List<SysMeet> findByRoom(String meetRoom) {

        if (StringUtils.isNotEmpty(meetRoom)){
            List list = new ArrayList();
            StringBuffer createSqlQuery=new  StringBuffer("from SysMeet where 1=1  ");
            createSqlQuery.append(" and meetRoom is ?");
            list.add(meetRoom);
            List<SysMeet> byHql = findByHql(createSqlQuery.toString(), list);
            if (byHql!=null){
                return byHql;
            }else {
                return null;
            }
        }
        return null;
    }


}
