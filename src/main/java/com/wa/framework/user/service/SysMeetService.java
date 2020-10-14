package com.wa.framework.user.service;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.SysMeetDao;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.SysMeet;
import com.wa.framework.user.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：会议室预定管理service
 * 创建人：disz
 * 创建时间：2020/10/810:17
 */
@Service("sysMeetService")
@ExpLog(type = "预定会议室管理")
public class SysMeetService extends BaseService {
    @Autowired
    @Qualifier("sysMeetDao")
    private SysMeetDao sysMeetDao;

    /**
     * 添加会议室预定
     * @param sysMeet
     * @return 0=success, 1=fail 2="该会议室在该时间段内已被预定，请重新预定" ,3="会议结束时间不能小于等于会议开始时间"
     */
    public int addMeet(SysMeet sysMeet){
        List<SysMeet> sysMeets = findByMeetRoom(sysMeet.getMeetRoom());
        if (sysMeets==null){
           sysMeetDao.save(sysMeet);
           return 0;
        }else {
            boolean status=true;
            for (SysMeet meet:sysMeets
                 ) {
                if (sysMeet.getStartTime().compareTo(meet.getEndTime())>0){

                }else if (sysMeet.getEndTime().compareTo(meet.getStartTime())<0){

                }else {
                   status=false;
                }
            }
            if (status==true){
                sysMeetDao.save(sysMeet);
                return 0;
            }else {
                return 2;
            }
        }

    }

    /**
     * 按meetRoom查询会议预定
     * @param meetRoom
     * @return
     */
    public List<SysMeet> findByMeetRoom(String meetRoom){
        return sysMeetDao.findByRoom(meetRoom);
    }





    /**
     * 修改会议室预定
     * @param sysMeet
     */
    public void editMeet(SysMeet sysMeet){
        SysMeet meet = sysMeetDao.get(sysMeet.getMeetId());
       if (StringUtils.isNotEmpty(sysMeet.getMeetRoom())){
           meet.setMeetRoom(sysMeet.getMeetRoom());
       }
       if (StringUtils.isNotEmpty(sysMeet.getCreateUser())){
           meet.setCreateUser(sysMeet.getCreateUser());
       }
       if (StringUtils.isNotEmpty(sysMeet.getMeetTheme())){
           meet.setMeetTheme(sysMeet.getMeetTheme());
       }
       if (StringUtils.isNotEmpty(sysMeet.getMeetHost())){
           meet.setMeetHost(sysMeet.getMeetHost());
       }
       if (sysMeet.getStartTime()!=null){
           meet.setStartTime(sysMeet.getStartTime());
       }
       if (sysMeet.getEndTime()!=null){
           meet.setEndTime(sysMeet.getEndTime());
       }
       sysMeetDao.update(meet);
    }

    /**
     * 删除会议室预定
     * @param meetId
     * @return 1：删除成功  2：该会议正在进行中，不能删除 3删除失败，会议不存在
     */
    public int deleteMeet(String meetId){
        if (StringUtils.isNotEmpty(meetId)){
            SysMeet sysMeet = sysMeetDao.get(meetId);
            if (sysMeet!=null){
                if (new Date().compareTo(sysMeet.getStartTime())>0&&new Date().compareTo(sysMeet.getEndTime())<0){
                    return 2;
                }else {
                    sysMeetDao.delete(sysMeet);
                    return 1;
                }
            }else {
                return 3;
            }
        }
       return 3;
    }

    /**
     * 查询会议预定
     * @param page
     * @param meetRoom
     * @param startTime
     * @param endTime
     * @return
     */
    public Pageable<SysMeet> findMeet(Page page, String meetRoom, Date startTime,Date endTime){
       return sysMeetDao.findMeet(page, meetRoom, startTime,endTime);

    }


    /**
     * 根据查询条件分页查询
     * @param criteria
     * @param page
     * @return
     */
    public Pageable<SysMeet> getMeetList(DetachedCriteria criteria,Page page){
        return sysMeetDao.getMeetList(criteria,page);
    }

    /**
     *预定会议查询
     * @param page
     * @param meetRoom
     * @param startTime
     * @param endTime
     * @return
     */
    public Pageable<SysMeet> findMeetWithRoom(Page page, String meetRoom, String startTime, String endTime,String createUser){
        Pageable<SysMeet> list = sysMeetDao.findMeetWithRoom(page, meetRoom, startTime, endTime,createUser);
        return list;
    }


}
