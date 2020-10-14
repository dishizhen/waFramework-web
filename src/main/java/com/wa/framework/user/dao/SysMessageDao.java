package com.wa.framework.user.dao;

import java.util.List;
import java.util.Map;

import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysMessage;

/**
 * 描述：系统消息数据操作接口
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:53:28
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:53:28
 */
public interface SysMessageDao extends BaseEntityDao<SysMessage> {

	/**
	 * @Description: 根据任务接受者和实例id查找taskid
	 * @param: @param receiveId
	 * @param: @param instanceId
	 * @param: @return
	 * @return: String
	 * @throws
	 * @since JDK 1.6
	 */
	public String findTaskIdByInstanceId(String receiveId, String instanceId);
	
	/**
     * @Description: 根据用户id和实例id查找CopyProcessRecord
     * @param: @param userId
     * @param: @param instanceId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public List<Map<String, Object>> findCopyProcessRecord(String receiveId, String instanceId);
	
}
