package com.wa.framework.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wa.framework.common.ComConstants;
import com.wa.framework.common.comet.Message;
import com.wa.framework.common.comet.MessageManager;
import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.SysMessageDao;

/**
 * 描述：系统消息service类
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:54:18
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:54:18
 */
@Service
@ExpLog(type="系统消息管理")
public class MessageService extends BaseService {
    
    @Resource
    SysMessageDao sysMessageDao;

    /**
     * @Description: 根据任务接受者和实例id查找taskid
     * @param: @param receiveId
     * @param: @param instanceId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
	public String findTaskIdByInstanceId(String receiveId, String instanceId) {
        return sysMessageDao.findTaskIdByInstanceId(receiveId, instanceId);
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
    public List<Map<String, Object>> findCopyProcessRecord(String userId, String instanceId) {
        return sysMessageDao.findCopyProcessRecord(userId, instanceId);
    }
	
    /**
     * @Description: comet推送已读消息记录
     * @param: @param receiverList
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public void sendDelegePopMessage(List<String> receiverList, int readNum) {
        if (receiverList != null && receiverList.size() > 0){
            Message message = new Message();
            message.setMessage("消息已读通知");
            message.setType(ComConstants.MESSAGE_TYPE_SYS);
            Map<String, Object> hmap = new HashMap<String, Object>();
            hmap.put("countType", ComConstants.SYS_MESSAGE_DEL);
            hmap.put("readNum", readNum);

            message.setParam(hmap);
            MessageManager.messageSender.send(message, receiverList);
        }
    }
}
