package com.wa.framework.user.controller;

import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.MeetRoom;
import com.wa.framework.user.service.MeetRoomService;
import com.wa.framework.util.easyui.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：会议室controller
 * 创建人：Administrator
 * 创建时间：2020/10/10  17:20
 */
@Controller
@RequestMapping("/system/meetRoom")
public class MeetRoomController {
    @Autowired
    @Qualifier("meetRoomService")
    private MeetRoomService meetRoomService;


    @RequestMapping("/list")
    @MethodDescription(desc = "所有会议室")
    public void findAll(HttpServletRequest request, Writer writer)throws Exception{
        List<MeetRoom> roomList = meetRoomService.findAll();
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        for (MeetRoom meetRoom:roomList
             ) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id",meetRoom.getId());
            resultMap.put("text",meetRoom.getMeetRoom());
            resultList.add(resultMap);
        }
        String json = ResponseUtils.toJSONString(resultList);
        writer.write(json);
    }

}
