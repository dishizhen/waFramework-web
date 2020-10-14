package com.wa.framework.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysMessageDao;
import com.wa.framework.user.model.SysMessage;

/**
 * 描述：系统消息数据操作接口实现类
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:53:28
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:53:28
 */
@Repository("sysMessageDao")
public class SysMessageDaoImpl extends BaseEntityDaoImpl<SysMessage> implements SysMessageDao {

    /**
     * @Description: 根据任务接受者和实例id查找taskid
     * @param: @param receiveId
     * @param: @param instanceId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    @Override
    public String findTaskIdByInstanceId(String receiveId, String instanceId) {
        String sql = " select to_char(ID_) from act_ru_task WHERE SUSPENSION_STATE_<> 2 AND PROC_INST_ID_ ='"+instanceId+"' AND ASSIGNEE_ ='"+receiveId+"' ";
        List<Map<String, Object>> obj = findBySql(sql);
        if(obj != null && obj.size() >0) {
            return obj.get(0).get("TO_CHAR(ID_)").toString();
        } else {
            return "";
        }
    }
    
    /**
     * @Description: 根据用户id和实例id查找CopyProcessRecord
     * @param: @param userId
     * @param: @param instanceId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    @Override
    public List<Map<String, Object>> findCopyProcessRecord(String userId, String instanceId) {
        String sql = " select * from copy_process_record WHERE PROCESS_INSTANCE_ID ='"+instanceId+"' AND COPY_TO ='"+userId+"' ";
        List<Map<String, Object>> obj = findBySql(sql);
        return obj;
    }

}
