package com.wa.framework.user.controller;

import com.wa.framework.Pageable;
import com.wa.framework.common.DTBean.DTRequestParamsBean;
import com.wa.framework.common.MailUtils;
import com.wa.framework.common.PageUtils;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysMeet;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.user.service.MeetRoomService;
import com.wa.framework.user.service.SysMeetService;
import com.wa.framework.user.service.UserService;
import com.wa.framework.util.DateUtils;
import com.wa.framework.util.easyui.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：会议室预定管理
 * 创建人：disz
 * 创建时间：2020/10/8 11:57
 */
@Controller("/system/meeting")
@RequestMapping("/system/meet")
public class MeetController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("sysMeetService")
    private SysMeetService sysMeetService;

    @RequestMapping("/meet")
    @MethodDescription(desc = "查询会议室预定")
    @RequiresPermissions("system.user.meeting.query")
    public ModelAndView meeting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<SysUser> users = userService.findUserOrderByName();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("sys/user/meet_list");
        return modelAndView;
    }

    @RequestMapping("/list")
    @MethodDescription(desc = "查询会议室预定")
    @RequiresPermissions("system.user.meeting.query")
    @ResponseBody
    public String meetingList(HttpServletRequest request) throws Exception {
        DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
        DetachedCriteria criteria = DetachedCriteria.forClass(SysMeet.class);
        String meetRoom = request.getParameter("meetRoom");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String createUser = request.getParameter("createUser");
        Pageable<SysMeet> pageable = sysMeetService.findMeetWithRoom(dtParams.getPage(), meetRoom, startTime, endTime, createUser);
        return PageUtils.buildDTData(pageable, request);
    }

    /**
     * @param request
     * @param response
     * @param writer
     * @param sysMeet
     * @throws Exception
     */
    @RequestMapping("/add")
    @MethodDescription(desc = "添加会议预定")
    @RequiresPermissions("system.user.meeting.add")
    public void meetAdd(HttpServletRequest request,
                        HttpServletResponse response,
                        Writer writer,
                        SysMeet sysMeet) throws Exception {

        System.out.println(sysMeet);
        String visitorStr = "";
        String[] split = StringUtils.split(sysMeet.getMeetVisitor(), ",");
        for (String userId : split
        ) {
            String uid = StringUtils.trim(userId);
            Map<String, Object> userById = userService.getUserById(uid);
            if (userById.get("USERNAME") != null) {
                visitorStr += userById.get("USERNAME").toString() + " ";
            }
        }
        sysMeet.setMeetVisitor(visitorStr);
        Map<String, String> jsonMap = new HashMap<String, String>();
        if (sysMeet.getStartTime().compareTo(sysMeet.getEndTime()) > 0) {
            jsonMap.put("result", "3");//3:会议结束时间不能小于等于会议开始时间
        } else {
            int i = sysMeetService.addMeet(sysMeet);
            if (i == 2) {
                jsonMap.put("result", "2");//2:该会议室在该时间段内已被预定，请重新预定
            }
            if (i == 0) {
                jsonMap.put("result", "0");//0:添加成功
                if (sysMeet.getEmail().equals("1")) {
                    String[] nameStrs = StringUtils.split(sysMeet.getMeetVisitor(), " ");
                    for (String nameStr : nameStrs
                    ) {
                        String name = StringUtils.trim(nameStr);
                        SysUser userByName = userService.findUserByName(name);
                        if (userByName != null) {
                            SysUser createUser = userService.findUserByName(sysMeet.getCreateUser());
                            String text = "";
                            String startTime = DateUtils.format(sysMeet.getStartTime(), DateUtils.YYYYMMDDHHMMSS_19);
                            String endTime = DateUtils.format(sysMeet.getEndTime(), DateUtils.YYYYMMDDHHMMSS_19);
                            text += "会议时间 ：" + startTime + " 到 " + endTime + "  " + "会议地点 ：" + sysMeet.getMeetRoom() + "  " + "会议主持人 ：" + sysMeet.getCreateUser() + "  " + "与会人 ：" + sysMeet.getMeetVisitor();
                            new MailUtils("smtp.citgc.com", createUser.getEmail(), "1995DszBis", userByName.getEmail(),
                                    sysMeet.getMeetTheme(), text, sysMeet.getCreateUser(), "", "").send();
                        }

                    }
                }

            }
        }
        String json = ResponseUtils.toJSONString(jsonMap);
        writer.write(json);

    }

    @RequestMapping("/delete")
    @MethodDescription(desc = "删除会议")
    @RequiresPermissions("system.user.meeting.delete")
    public void deleteMeet(String meetId, Writer writer) throws Exception {
        HashMap<String, Object> mapJson = new HashMap<String, Object>();
        int num = sysMeetService.deleteMeet(meetId);
        mapJson.put("result", num);
        String json = ResponseUtils.toJSONString(mapJson);
        writer.write(json);
    }

}
