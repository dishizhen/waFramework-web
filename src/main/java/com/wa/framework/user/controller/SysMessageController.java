package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.OrderProperty;
import com.wa.framework.Pageable;
import com.wa.framework.QueryCondition;
import com.wa.framework.QueryConditions;
import com.wa.framework.common.ComConstants;
import com.wa.framework.common.CommonUtil;
import com.wa.framework.common.PropertyConfigurer;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysMessage;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.user.service.MessageService;
import com.wa.framework.util.easyui.RequestUtils;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 描述：系统消息控制类
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:46:30
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:46:30
 */
@SuppressWarnings("rawtypes")
@Controller("/system/message")
@RequestMapping("/system/message")
public class SysMessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/message")
    @MethodDescription(desc="系统消息管理")
    public ModelAndView user(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/message/message_list");
        return view;
    }

    /**
     * @param request
     * @param response
     * @param writer
     * @throws Exception
     */
    @RequestMapping("/list")
    @MethodDescription(desc="系统消息管理")
    public void userList(HttpServletRequest request, HttpServletResponse response, Writer writer,@RequestParam(value="state",defaultValue="") String state) throws Exception {
        QueryConditions queryConditions = new QueryConditions();
        String userId = CommonUtil.getCurrentUserId();
        queryConditions.add(QueryCondition.eq("receiverId", userId));
        if(!StringUtils.isEmpty(state)) {
            queryConditions.add(QueryCondition.eq("state", Boolean.valueOf(state)));
        }
        Pageable<SysMessage> page = baseService.findWithPage(SysMessage.class, RequestUtils.buildPage(request), OrderProperty.desc("sendDate"),queryConditions);
        List<SysMessage> list = page.getList();
        for(SysMessage sm : list) {
            SysUser sender = baseService.findById(SysUser.class, sm.getSenderId());
            sm.setSendName(!StringUtils.isEmpty(sender.getRealName()) ? sender.getRealName() : sender.getUsername());
            String taskid = messageService.findTaskIdByInstanceId(sm.getReceiverId(), sm.getInstanceId());
            if(!StringUtils.isEmpty(taskid)) {
                sm.setTaskId(taskid);
            }
            
            List<Map<String, Object>> recordList = messageService.findCopyProcessRecord(userId, sm.getInstanceId());
            if(recordList.size() > 0){
                sm.setRecordId(sm.getInstanceId());
            }
        }
        String json = ResponseUtils.buildJson(page);
        writer.write(json);
    }

    /**
     * @Description: 返回系统消息详细信息
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/showDetail")
    @MethodDescription(desc="系统消息管理")
    public void showDetail(HttpServletRequest request, Writer writer) throws Exception {
        String id = request.getParameter("id");
        SysMessage sm = baseService.findById(SysMessage.class, id);
        boolean oldState = sm.getState();
        if (!oldState) {
            // 更新未读消息为已读状态
            sm.setState(true);
            baseService.update(sm);
        }

        // 推送系统消息数量减少通知
        if (ComConstants.SUPPORT_MESSAGE_YES.equals(PropertyConfigurer.getValue(ComConstants.IS_SUPPORT_MESSAGE))) {
            if (!oldState) {
                List<String> receiverList = new ArrayList<String>();
                String userId = CommonUtil.getCurrentUserId();
                receiverList.add(userId);
                messageService.sendDelegePopMessage(receiverList, 1);
            }
        }
        if ("system".equals(sm.getSenderId())) {
            sm.setSendName("管理员");
        } else {
            SysUser sender = baseService.findById(SysUser.class, sm.getSenderId());
            sm.setSendName(!StringUtils.isEmpty(sender.getRealName()) ? sender.getRealName() : sender.getUsername());
        }
        writer.write(ResponseUtils.toJSONString(sm));
    }
    
    /**
     * @Description: 设置消息已读
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    @ResponseBody
    @RequestMapping(value="/read",method=RequestMethod.POST)
    @MethodDescription(desc="系统消息管理")
    public boolean read(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        read(id);
        
        // 推送系统消息数量减少通知
        if (ComConstants.SUPPORT_MESSAGE_YES.equals(PropertyConfigurer.getValue(ComConstants.IS_SUPPORT_MESSAGE))){
            List<String> receiverList = new ArrayList<String>();
            String userId = CommonUtil.getCurrentUserId();
            
            receiverList.add(userId);
            messageService.sendDelegePopMessage(receiverList, 1);
        }
        
        return true;
    }
    
    /**
     * @Description: 设置全部消息为已读
     * @param: @param ids
     * @param: @return
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    @ResponseBody
    @RequestMapping(value="/readAll",method=RequestMethod.POST)
    @MethodDescription(desc="系统消息管理")
    public boolean readAll(HttpServletRequest request, HttpServletResponse response, String ids){
        if (StringUtils.isEmpty(ids)){
            return true;
        }
    	String[] mIds = ids.split(",");
    	for(String id:mIds){
    		read(id);
    	}
    	
    	// 推送系统消息数量减少通知
    	if (ComConstants.SUPPORT_MESSAGE_YES.equals(PropertyConfigurer.getValue(ComConstants.IS_SUPPORT_MESSAGE))){
    	    List<String> receiverList = new ArrayList<String>();
            String userId = CommonUtil.getCurrentUserId();
            receiverList.add(userId);
            messageService.sendDelegePopMessage(receiverList, mIds.length);
    	}
    	return true;
    	
    }
    
    /**
     * @Description: 删除系统消息
     * @param: @param ids
     * @param: @return
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    @ResponseBody
    @RequestMapping(value="/deleteMessage",method=RequestMethod.POST)
    @MethodDescription(desc="系统消息管理")
    public boolean delete(String ids){
    	String[] mIds = ids.split(",");
    	baseService.deleteByIds(SysMessage.class, mIds);
    	return true;
    }
    
    /**
     * @Description: 更新消息为已读
     * @param: @param id
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private void read(String id){
    	SysMessage sm = baseService.findById(SysMessage.class, id);
        sm.setState(true);
        baseService.update(sm);
    }
    
    /**
     * <描述>: 获取登录用户未读的系统消息列表
     * @author 作者：lijj
     * @version 创建时间：2016年7月28日上午9:38:37
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping("/unread")
    @MethodDescription(desc="系统消息管理")
    public void getUnreadMsgList(HttpServletRequest request, Writer writer) throws Exception {
        QueryConditions queryConditions = new QueryConditions();
        String userId = CommonUtil.getCurrentUserId();
        queryConditions.add(QueryCondition.eq("receiverId", userId));// 消息接收用户id
        queryConditions.add(QueryCondition.eq("state", false));// 未读
        List<SysMessage> unreadList = baseService.find(SysMessage.class, queryConditions);
        String json = ResponseUtils.toJSONString(unreadList);
        writer.write(json);
    }
    
}
