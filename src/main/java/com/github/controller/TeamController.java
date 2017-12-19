package com.github.controller;

import com.github.mapper.*;
import com.github.model.*;
import com.github.pagehelper.PageHelper;
import com.github.service.RoleService;
import com.github.util.Constants;
import com.github.util.CourseFidUtil;
import com.github.util.CurrentUser;
import com.github.util.DataTableJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *小组管理
 * @author wql
 * @create 2017-11-07 10:17
 **/

@Controller
@RequestMapping("team")
public class TeamController {

    @Resource
    private TeamMapper teamMapper;
    @Resource
    private TeamTypeMapper teamTypeMapper;
    @Resource
    private TeamRTypeMapper teamRTypeMapper;
    @Autowired
    private RoleService roleService;
    @Resource
    private RecommendedTeamMapper recommendedTeamMapper;


    @GetMapping("add-team-type")
    public String addSubjectType(Model model){
        return "page/team-type-add";
    }

    /**
     * 增加分类
     * @param loginUser
     * @param
     * @return
     */
    @PostMapping("addTeamType")
    public String addTeamType(@CurrentUser User loginUser, TeamType teamType){
//        teamType.setFid(loginUser.getFid());
        teamType.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        teamType.setSequnce((teamTypeMapper.selectMaxSeqByPid(teamType) + 1)+"");
        teamType.setStatus("1");
        teamType.setParentId(0);
        teamTypeMapper.insertSelective(teamType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 删除分类
     * @param typeId
     * @return
     */
    @RequestMapping(value = "{typeId}/delete", method = RequestMethod.GET)
    public String deleteTeamType(@PathVariable("typeId") Integer typeId){
        teamTypeMapper.deleteByPrimaryKey(typeId);
        teamRTypeMapper.deleteByTypeId(typeId);//删除分类的同时删除该类型和资源的关联关系
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    @RequestMapping("{typeId}/edit-team-type")
    public Object editTeamTYpe(@PathVariable("typeId") Integer id, Model model) {
        model.addAttribute("teamType", teamTypeMapper.selectByPrimaryKey(id));
        return "page/team-type-edit";
    }
    /**
     * 修改分类
     * @param teamType
     * @return
     */
    @PostMapping("update-team-type")
    public String updateTeamType(TeamType teamType){
        teamTypeMapper.updateByPrimaryKeySelective(teamType);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 根据类型父id获取子类型列表
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("{pid}/team-types-by-pid")
    public DataTableJson<TeamType> getSubjectTypesByPid(@PathVariable("pid") Integer pid,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize, TeamType teamType){

        teamType.setParentId(pid);
        teamType.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        return new DataTableJson<>(teamTypeMapper.getList(teamType));
    }

    @RequestMapping("child/list/{pid}")
    public String redirectToAddPage(@PathVariable("pid") Integer pid, Model model) {
        model.addAttribute("pid", pid);
        return "page/subject-type-child-list";
    }

    /**
     *
     * @param loginUser
     * @param pid
     * @return
     */
    @ResponseBody
    @GetMapping("{pid}/team-type-list-by-pid")
    public List<Map<String, Object>> getTeamListByPid(@CurrentUser User loginUser, @PathVariable("pid") Integer pid){
        TeamType team = new TeamType();
        team.setParentId(pid);
        team.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        List<Map<String, Object>> result = teamTypeMapper.getMap(team);
        for(Map<String, Object> map : result){
            team.setParentId((Integer) map.get("id"));
            map.put("children", teamTypeMapper.getMap(team));
        }
        return result;
    }

    /**
     * 小组列表
     * @param page
     * @param pageSize
     * @param loginUser
     * @param team
     * @param fid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("team-list")
    public DataTableJson<Team> teamList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                        @CurrentUser User loginUser, Team team,Integer fid, HttpServletRequest request){

        //如果是超级管理员，则将当前的fid放入Session,以便后面取出使用
        if(roleService.isSuperAdmin(loginUser)){
            if(fid != null) {
                request.getSession().setAttribute(Constants.TEAM_PAGE_FID, fid);
            }else{
                request.getSession().setAttribute(Constants.TEAM_PAGE_FID, null);
            }
        }

        PageHelper.startPage(page, pageSize);
        team.setFid(fid == null ? loginUser.getFid() : fid);
        return new DataTableJson<>(teamMapper.getList(team));
    }

    /**
     * 预览以后添加
     * @param loginUser
     * @param team
     * @return
     */
    @PostMapping("add-team")
    public String addTeam(@CurrentUser User loginUser, Team team){
        team.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        team.setStatus(1);
        teamMapper.insertSelective(team);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("{id}/delete-team")
    public String deleteTeam(@PathVariable("id") Integer id){
        teamMapper.deleteByPrimaryKey(id);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 编辑跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("{id}/edit-team")
    public Object edit(@PathVariable Integer id, Model model) {
        model.addAttribute("team", teamMapper.selectByPrimaryKey(id));
        return "page/team-edit";
    }

    /**
     * 更新小组
     * @param team
     * @return
     */
    @PostMapping("update-team")
    public String updateTeam(Team  team) {
        teamMapper.updateByPrimaryKeySelective(team);
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 添加之前预览看是否存在
     * @param teamId
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("preview")
    public String preview(String teamId, Model model) throws Exception {
        try {
//            String requestUrl = type == Constants.QIKAN_TYPE
//                    ? Constants.QIKAN_DETAIL_HREF.replace("{resId}", teamId)
//                    : Constants.GRT_COURSE_DETAIL_HREF.replace("{ids}", teamId);
//            JSONObject jsonObject = JSONObject.parseObject(new HttpRequest().doGet(requestUrl)).getJSONArray(type == Constants.QIKAN_TYPE ? "results" : "data").getJSONObject(0);
//            Subject subject = new Subject();
//            subject.setType(type);
//            subject.setDataId(dataId);
//            subject.setAuthor(type == Constants.QIKAN_TYPE ? jsonObject.getString("unit") : jsonObject.getString("teacherfactor"));
//            subject.setCover(type == Constants.QIKAN_TYPE ? jsonObject.getString("coverurl") : jsonObject.getString("imageurl"));
//            subject.setTitle(type == Constants.QIKAN_TYPE ? jsonObject.getString("magname") : jsonObject.getString("name"));
//            subject.setJourUrl(type == Constants.QIKAN_TYPE ? jsonObject.getString("mobileulr").replace("#INNER", "&from=space") : "");
//            subject.setHour(Constants.SUBJECT_DEFAULT_HOUR);
//            subject.setOnline(Constants.SUBJECT_ONLINE);
//            model.addAttribute("team", team);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page/team-preview";
    }







    /**
     * 类型资源关联
     * @param typeId 类型id
     * @param teamIds 例(1,2,3,4,5...)
     * @return
     */
    @ResponseBody
    @GetMapping("{typeId}/add-team-r-type")
    public DataTableJson addTeamRType(@PathVariable("typeId") Integer typeId, String teamIds){
        String[] ids = teamIds.split(",");
        TeamRType teamtRType = null;
        for(String id : ids){
            teamtRType = new TeamRType();
            teamtRType.setTypeId(typeId);
            teamtRType.setTeamId(Integer.parseInt(id));
            teamRTypeMapper.insertSelective(teamtRType);
        }
        DataTableJson d = new DataTableJson();
        d.setCode(0);
        return d;
    }

    /**
     * 删除资源类型关联
     * @param typeId
     * @param teamIds 例1,2,3,4,5...
     * @return
     */
    @GetMapping("{typeId}/delete-team-r-type")
    public String deleteSubjectRTypes(@PathVariable("typeId") Integer typeId, String teamIds){
        String[] ids = teamIds.split(",");
        TeamRType teamRType = null;
        for(String id : ids){
            teamRType = new TeamRType();
            teamRType.setTypeId(typeId);
            teamRType.setTeamId(Integer.parseInt(id));
            teamRTypeMapper.deleteByTypeIdAndTeamId(teamRType);
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 小组分类下关联的小组数据
     * @param page
     * @param pageSize
     * @param typeId
     * @param team
     * @return
     */
    @ResponseBody
    @RequestMapping("{typeId}/type-r-team-list")
    public DataTableJson<Team> selectTypeRSubject(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                              @PathVariable("typeId") Integer typeId, Team team){
        PageHelper.startPage(page, pageSize);
//        subject.setType(type);
        return new DataTableJson<>(teamMapper.selectTypeRTeam(typeId,team));
    }


    /**
     * 未关联的分类数据
     * @param page
     * @param pageSize
     * @param loginUser
     * @param typeId
     * @param team
     * @return
     */
    @ResponseBody
    @RequestMapping("{typeId}/type-not-r-team-list")
    public DataTableJson<Team> selectTypeNotRSubject(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize, @CurrentUser User loginUser,
                                                     @PathVariable("typeId") Integer typeId, Team team){

        team.setTeamType(typeId);
        team.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        return new DataTableJson<>(teamMapper.selectTypeNotRTeam(typeId, team));
    }


    /**
     * 获取首页推荐小组
     * @param page
     * @param pageSize
     * @param team
     * @return
     */
    @ResponseBody
    @RequestMapping("homepage-recommended")
    public DataTableJson<Team> homePageRecommended(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                      Team team){
        team.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        return new DataTableJson<>(teamMapper.homePageRecommended(team));
    }

    /**
     * 查询尚未推荐小组
     * @param page
     * @param pageSize
     * @param team
     * @return
     */
    @ResponseBody
    @RequestMapping("homepage-not-recommended")
    public DataTableJson<Team> homePageNotRecommended(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                         @RequestParam(name = "limit", required = false, defaultValue = "20") Integer pageSize,
                                                         Team team){
        team.setFid(CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID));
        PageHelper.startPage(page, pageSize);
        return new DataTableJson<>(teamMapper.homePageNotRecommended(team));
    }
    /**
     * 删除首页推荐图书\期刊
     * @param teamIds 例1,2,3,4,5...
     */
    @GetMapping("delete-homepage-rec")
    public String deleteByFidAndSubjectId( String teamIds){
       Integer fid = CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID);
        String[] ids = teamIds.split(",");
        RecommendedTeam rt = null;
        for(String id : ids){
            rt = new RecommendedTeam();
            rt.setFid(fid);
            rt.setTeamId(Integer.parseInt(id));
            recommendedTeamMapper.deleteByFidAndTeamId(rt);
        }
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/page/success.html";
    }

    /**
     * 添加推荐小组
     * @param teamIds 例(1,2,3,4,5...)
     * @return
     */
    @ResponseBody
    @GetMapping("add-homepage-rec")
    public DataTableJson addRecommededSubject(String teamIds){
        Integer fid =CourseFidUtil.getSubjectFid(Constants.TEAM_PAGE_FID);
        String[] ids = teamIds.split(",");
        RecommendedTeam rt = null;
        for(String id : ids){
            rt = new RecommendedTeam();
            rt.setFid(fid);
            rt.setTeamId(Integer.parseInt(id));
            recommendedTeamMapper.insertSelective(rt);
        }
        DataTableJson d = new DataTableJson();
        d.setCode(0);
        return d;
    }
}
