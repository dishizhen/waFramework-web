package com.wa.framework.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wa.framework.OrderProperty;
import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.QueryCondition;
import com.wa.framework.common.ComConstants;
import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.SysMenuDao;
import com.wa.framework.user.model.SysMenu;
import com.wa.framework.user.model.SysPrivilege;
import com.wa.framework.user.model.SysRole;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.user.model.base.BaseSysPrivilege;
import com.wa.framework.user.vo.SysAccessVO;
import com.wa.framework.user.vo.Tree;
import com.wa.framework.util.PropertyComparator;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 描述：权限管理service
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:20:11
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:20:11
 */
@Service("sysAccessService")
@ExpLog(type="权限管理")
public class SysAccessService extends BaseService {
    
    @Autowired
    @Qualifier("menuDao")
    private SysMenuDao sysMenuDao;

    /**
     * @Description: 菜单+权限树，返回json结构
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String getTreeJson() {
        List<SysMenu> sysMenus = baseDao.getAll(SysMenu.class);
        Collections.sort(sysMenus, new PropertyComparator(SysMenu.PROP_DISPLAY_ORDER));
        List<SysPrivilege> sysPrivileges = baseDao.getAll(SysPrivilege.class);
        Collections.sort(sysPrivileges, new PropertyComparator(SysPrivilege.PROP_PRIVILEGE_CODE));
        List<Tree> trees = new ArrayList<Tree>();
        Set<Tree> subtrees = createTree(sysMenus, sysPrivileges, ComConstants.ROOT_MENU_PARENTID);
        trees.add((Tree) subtrees.toArray()[0]);
        return JSON.toJSONString(trees);
    }

    /**
     * @Description: 构造菜单+权限树
     * @param: @param sysMenus
     * @param: @param sysPrivileges
     * @param: @param parentValue
     * @param: @return
     * @return: Set<Tree>
     * @throws
     * @since JDK 1.6
     */
    private Set<Tree> createTree(List<SysMenu> sysMenus,
            List<SysPrivilege> sysPrivileges, String parentValue) {
        Set<Tree> leafTrees = new HashSet<Tree>();
        for (SysMenu menu : sysMenus) {
            String parentId = menu.getParentId();
            if (parentId == parentValue || (parentId != null && parentId.equals(parentValue))) {
                String id = menu.getId();
                String name = menu.getMenuName();
                Tree node = new Tree(id, name);
                Set<Tree> subLeafs = createTree(sysMenus, sysPrivileges, id);
                if (subLeafs.size() > 0) {
                    node.setChildren(subLeafs);
                    if (!ComConstants.ROOT_MENU_PARENTID.equals(parentId)){
                        node.setState(ComConstants.TREE_CLOSED);
                    }
                } else {
                    Set<Tree> subtrees = new HashSet<Tree>();
                    for (SysPrivilege privilege : sysPrivileges) {
                        if (privilege.getSysMenu().getId().equals(menu.getId())) {
                            Tree subtree = new Tree(privilege.getId(), privilege.getPrivilegeName());
                            subtree.setAttributes("\"cid\":\"" + privilege.getId() + "\"");
                            subtrees.add(subtree);
                        }
                        node.setChildren(subtrees);
                    }
                    if (subtrees.size() > 0){
                        node.setState(ComConstants.TREE_CLOSED);
                    }
                    node.setAttributes("available");
                }
                leafTrees.add(node);
            }
        }
        return leafTrees;
    }

    /**
     * @Description: 增加权限
     * @param: @param menuId
     * @param: @param privilegeCodeArr
     * @param: @param privilegeNameArr
     * @param: @param desArr
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String addAccess(String menuId, String[] privilegeCodeArr,
            String[] privilegeNameArr, String[] desArr) {
        
        //用户输入相同的编码或者名称
        Set<String> codeset = new HashSet<String>();
        Set<String> nameset = new HashSet<String>();
        for(String str : privilegeCodeArr){
            codeset.add(str);
        }
        if(codeset.size() != privilegeCodeArr.length){
            return ResponseUtils.buildResultJson(false, "增加功能权限编码重复!");
        }
        
        for(String str : privilegeNameArr){
            nameset.add(str);
        }
        if(nameset.size() != privilegeNameArr.length){
            return ResponseUtils.buildResultJson(false, "增加功能权限名称重复!");
        }
        
        for(int i = 0 ; i < privilegeCodeArr.length; i++){
            SysPrivilege existPriv = baseDao.unique(SysPrivilege.class,
                            QueryCondition.eq(SysPrivilege.PROP_PRIVILEGE_CODE, privilegeCodeArr[i]));
            SysPrivilege existObj = baseDao.unique(SysPrivilege.class, 
                            QueryCondition.eq(SysPrivilege.PROP_PRIVILEGE_NAME, privilegeNameArr[i]));
            if (existObj != null) {
                return ResponseUtils.buildResultJson(false, "增加功能权限名称重复!");
            }
                    
            if (existPriv != null) {
                return ResponseUtils.buildResultJson(false, "增加功能权限编码重复!");
            }
        }
        
        SysMenu sysMenu = baseDao.get(SysMenu.class, menuId);
        for(int i = 0 ; i < privilegeCodeArr.length; i++){
            SysPrivilege sysPrivilege = new SysPrivilege();
            sysPrivilege.setPrivilegeCode(privilegeCodeArr[i]);
            sysPrivilege.setPrivilegeName(privilegeNameArr[i]);
            sysPrivilege.setDescription(desArr[i]);
            sysPrivilege.setSysMenu(sysMenu);
            baseDao.save(sysPrivilege);
        }
        return ResponseUtils.buildResultJson(true, "增加功能权限成功!");  
    }

    /**
     * @Description: 修改权限
     * @param: @param privilegeId
     * @param: @param privilegeCode
     * @param: @param privilegeName
     * @param: @param privilegeOldName
     * @param: @param editMenuId
     * @param: @param description
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String editAccess(String privilegeId, String privilegeCode,
            String privilegeName, String privilegeOldName, String editMenuId,
            String description) {
        SysPrivilege existPriv = baseDao.get(SysPrivilege.class, privilegeId);
        if (existPriv != null) {
            SysPrivilege existObj = baseDao.unique(SysPrivilege.class, 
                            QueryCondition.eq(SysPrivilege.PROP_PRIVILEGE_NAME, privilegeName));
            if (existObj != null && !privilegeName.equals(privilegeOldName)) {
                    return ResponseUtils.buildResultJson(false, "修改功能权限名称重复!");
            }
            if (privilegeCode != null
                    && !privilegeCode.equals(existPriv.getPrivilegeCode())) {
                SysPrivilege existEditPriv = baseDao.unique(SysPrivilege.class,
                        QueryCondition.eq(BaseSysPrivilege.PROP_PRIVILEGE_CODE, privilegeCode));
                if (existEditPriv != null) {
                    return ResponseUtils.buildResultJson(false, "修改功能权限编码重复!");
                }
            }
            existPriv.setPrivilegeCode(privilegeCode);
            existPriv.setPrivilegeName(privilegeName);
            existPriv.setDescription(description);
            
            SysMenu sysMenu = baseDao.get(SysMenu.class, editMenuId);
            existPriv.setSysMenu(sysMenu);
            baseDao.update(existPriv);
            
            return ResponseUtils.buildResultJson(true, "修改功能权限成功!");
        }
        return ResponseUtils.buildResultJson(false, "修改功能权限失败!");
    }

    /**
     * @Description: 删除权限
     * @param: @param id
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String deleteAccess(String id) {
        if (!StringUtils.isBlank(id)) {
            SysPrivilege sysPriv = baseDao.get(SysPrivilege.class, id);
            if(sysPriv!=null){
                baseDao.delete(sysPriv);
            }else{
                   return ResponseUtils.buildResultJson(false, "删除功能权限失败,无功能权限!");
            }
            return ResponseUtils.buildResultJson(true, "删除功能权限成功!");
        } else {
            return ResponseUtils.buildResultJson(false, "删除功能权限失败!");
        }
    }
    
    /**
     * @Description: 查询功能权限
     * @param: @param page
     * @param: @param parentId
     * @param: @return
     * @return: Pageable
     * @throws
     * @since JDK 1.6
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Pageable findMenusByParentIdPage(Page page, String parentId) {
        Pageable pageable=baseDao.findWithPage(SysPrivilege.class, page, OrderProperty.asc(SysPrivilege.PROP_PRIVILEGE_CODE));
        List<SysPrivilege> privList = new ArrayList<SysPrivilege>();
        if (StringUtils.isBlank(parentId)||parentId.equals("ROOT")) {
            privList = pageable.getList();
        } else {
            SysPrivilege sysp=baseDao.get(SysPrivilege.class, parentId);
            if(sysp!=null){
                privList.add(sysp);
            }else{
                privList=getSysPrivilegeListByMenuId(parentId);
            }
        }
        
        if(null!=parentId&&!parentId.equals("ROOT")){
            List<SysAccessVO> voList = getViewList(privList, page);
            pageable.getList().clear();
            pageable.addData(voList);
            pageable.setTotalRecords(privList.size());
            int totalPages = (int) Math.ceil(privList.size() / (double) page.getPageSize());
            pageable.setTotalPages(totalPages);
            return pageable;
        }
        return pageable;
    }
    
    /**
     * @Description: 封装返回的权限列表
     * @param: @param privList
     * @param: @param page
     * @param: @return
     * @return: List<SysAccessVO>
     * @throws
     * @since JDK 1.6
     */
    private List<SysAccessVO> getViewList(List<SysPrivilege> privList, Page page) {
        List<SysAccessVO> voList = new ArrayList<SysAccessVO>();
        int max = (page.getCurrentPage()) * (page.getPageSize());
        int beginIndex = (page.getCurrentPage() - 1) * page.getPageSize();
        int endIndex = (max > privList.size()) ? privList.size() : max;
        for (int i = beginIndex; i < endIndex; i++) {
            SysAccessVO accessVo = new SysAccessVO();
            accessVo.setDescription(privList.get(i).getDescription());
            accessVo.setPrivilegeId(privList.get(i).getId());
            accessVo.setPrivilegeCode(privList.get(i).getPrivilegeCode());
            accessVo.setPrivilegeName(privList.get(i).getPrivilegeName());
            accessVo.setMenuName(privList.get(i).getSysMenu().getMenuName());
            accessVo.setMenuId(privList.get(i).getSysMenu().getId());
            voList.add(accessVo);
        }
        return voList;
    }
    
    /**
     * @Description: 根据菜单id查询对对应的功能权限列表
     * @param: @param menuId
     * @param: @return
     * @return: List<SysPrivilege>
     * @throws
     * @since JDK 1.6
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<SysPrivilege> getSysPrivilegeListByMenuId(String menuId) {
        List<SysPrivilege> returnList = new ArrayList();
        Set<SysPrivilege> privilegeSet = new HashSet<SysPrivilege>();
        List<SysMenu> menuList = sysMenuDao.findChildsByMenuid(menuId);
        if (menuList.isEmpty()){
            SysMenu sysMenu = baseDao.get(SysMenu.class, menuId);
            List<SysPrivilege> privilegeList = baseDao.find(SysPrivilege.class,
                            QueryCondition.eq(SysPrivilege.PROP_SYS_MENU, sysMenu));
            privilegeSet.addAll(privilegeList);
        } else {
            for (SysMenu sysMenu : menuList) {
                List<SysPrivilege> privilegeList = baseDao.find(SysPrivilege.class,
                                QueryCondition.eq(SysPrivilege.PROP_SYS_MENU, sysMenu));
                privilegeSet.addAll(privilegeList);
            }
        }

        returnList.addAll(privilegeSet);
        return returnList;
    }
    
    /**
     * @Description: 新增角色时获取的权限树
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String getPrivilegeJson() {
        List<SysMenu> sysMenus = baseDao.getAll(SysMenu.class);
        Collections.sort(sysMenus, new PropertyComparator(SysMenu.PROP_DISPLAY_ORDER));
        List<SysPrivilege> sysPrivileges = baseDao.getAll(SysPrivilege.class);
        Collections.sort(sysPrivileges, new PropertyComparator(SysPrivilege.PROP_PRIVILEGE_CODE));
        List<Tree> trees = new ArrayList<Tree>();
        Set<Tree> subtrees = createTree(sysMenus, sysPrivileges, "0");
        trees.add((Tree) subtrees.toArray()[0]);
        return JSON.toJSONString(trees);
    }
    
    /**
     * @Description: 用户授权时获取的权限树，已授权的需要打勾
     * @param: @param userId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String getUserPrivilegeJson(String userId) {
        List<SysMenu> sysMenus = baseDao.getAll(SysMenu.class);
        Collections.sort(sysMenus, new PropertyComparator(SysMenu.PROP_DISPLAY_ORDER));
        List<SysPrivilege> sysPrivileges = baseDao.getAll(SysPrivilege.class);
        Collections.sort(sysPrivileges, new PropertyComparator(SysPrivilege.PROP_PRIVILEGE_CODE));
        List<Tree> trees = new ArrayList<Tree>();
        if (!StringUtils.isBlank(userId)) {
            List<SysPrivilege> privileges = getUserPrivilege(userId);
            Set<Tree> subtrees = editTree(sysMenus, sysPrivileges, "0", privileges);
            trees.add((Tree) subtrees.toArray()[0]);
        }
        return JSON.toJSONString(trees);
    }

    /**
     * @Description: 角色授权时获取的权限树，已授权的需要打勾
     * @param: @param roleId
     * @param: @return
     * @return: String
     * @throws
     * @since JDK 1.6
     */
    public String getRolePrivilegeJson(String roleId) {
        List<SysMenu> sysMenus = baseDao.getAll(SysMenu.class);
        Collections.sort(sysMenus, new PropertyComparator(SysMenu.PROP_DISPLAY_ORDER));
        List<SysPrivilege> sysPrivileges = baseDao.getAll(SysPrivilege.class);
        Collections.sort(sysPrivileges, new PropertyComparator(SysPrivilege.PROP_PRIVILEGE_CODE));
        List<Tree> trees = new ArrayList<Tree>();
        if (!StringUtils.isBlank(roleId)) {
            SysRole role = baseDao.get(SysRole.class, roleId);
            List<SysPrivilege> privileges = new LinkedList<SysPrivilege>(role.getSysPrivileges());
            Set<Tree> subtrees = editTree(sysMenus, sysPrivileges, "0", privileges);
            trees.add((Tree) subtrees.toArray()[0]);
        }
        return JSON.toJSONString(trees);
    }
    
    /**
     * @Description: 根据用户id获取权限列表
     * @param: @param userId
     * @param: @return
     * @return: List<SysPrivilege>
     * @throws
     * @since JDK 1.6
     */
    private List<SysPrivilege> getUserPrivilege(String userId) {
        SysUser user = baseDao.get(SysUser.class, userId);
        List<SysPrivilege> privileges = new LinkedList<SysPrivilege>(user.getSysPrivileges());
        return privileges;
    }
    
    /**
     * @Description: 构造菜单+权限树，已授权的需要打勾
     * @param: @param sysMenus
     * @param: @param sysPrivileges
     * @param: @param parentValue
     * @param: @param privileges
     * @param: @return
     * @return: Set<Tree>
     * @throws
     * @since JDK 1.6
     */
    private Set<Tree> editTree(List<SysMenu> sysMenus, List<SysPrivilege> sysPrivileges, String parentValue,
                    List<SysPrivilege> privileges) {
        Set<Tree> leafTrees = new HashSet<Tree>();
        for (SysMenu menu : sysMenus) {
            String parentId = menu.getParentId();
            if (parentId == parentValue || (parentId != null && parentId.equals(parentValue))) {
                String id = menu.getId();
                String name = menu.getMenuName();
                Tree node = new Tree(id, name);
                Set<Tree> subLeafs = editTree(sysMenus, sysPrivileges, id, privileges);
                if (subLeafs.size() > 0) {
                    node.setChildren(subLeafs);
                    if (!ComConstants.ROOT_MENU_PARENTID.equals(parentId)) {
                        node.setState(ComConstants.TREE_CLOSED);
                    }
                } else {
                    Set<Tree> subtrees = new HashSet<Tree>();
                    for (SysPrivilege privilege : sysPrivileges) {
                        if (privilege.getSysMenu().getId().equals(menu.getId())) {
                            Tree subtree = new Tree(privilege.getId(), privilege.getPrivilegeName());
                            subtree.setAttributes("\"cid\":\"" + privilege.getId() + "\"");
                            if (privileges.contains(privilege)) {
                                subtree.setChecked("true");
                            }
                            subtrees.add(subtree);
                        }
                        node.setChildren(subtrees);
                        node.setState(ComConstants.TREE_CLOSED);
                    }
                }
                leafTrees.add(node);
            }
        }
        return leafTrees;
    }

}
