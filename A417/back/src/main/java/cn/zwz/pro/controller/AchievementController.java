package cn.zwz.pro.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.data.vo.AntvVo;
import cn.zwz.pro.entity.Achievement;
import cn.zwz.pro.entity.Curriculum;
import cn.zwz.pro.service.IAchievementService;
import cn.zwz.pro.service.ICurriculumService;
import cn.zwz.test.entity.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author
 *
 */
@Slf4j
@RestController
@Api(tags = "成绩管理接口")
@RequestMapping("/zwz/achievement")
@Transactional
public class AchievementController {

    @Autowired
    private IAchievementService iAchievementService;

    @Autowired
    private ICurriculumService iCurriculumService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条成绩")
    public Result<Achievement> get(@RequestParam String id){
        return new ResultUtil<Achievement>().setData(iAchievementService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部成绩个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iAchievementService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部成绩")
    public Result<List<Achievement>> getAll(){
        return new ResultUtil<List<Achievement>>().setData(iAchievementService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询成绩")
    public Result<IPage<Achievement>> getByPage(@ModelAttribute Achievement achievement ,@ModelAttribute PageVo page){
        QueryWrapper<Achievement> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND (role_id = '1536606659751841799' OR role_id = '1774358297214849024')");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("user_id",currUser.getId());
        }
        if(!ZwzNullUtils.isNull(achievement.getCurriculumName())) {
            qw.like("curriculum_name",achievement.getCurriculumName());
        }
        if(!ZwzNullUtils.isNull(achievement.getUserName())) {
            qw.like("user_name",achievement.getUserName());
        }
        IPage<Achievement> data = iAchievementService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Achievement>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改成绩")
    public Result<Achievement> saveOrUpdate(Achievement achievement){
        if(iAchievementService.saveOrUpdate(achievement)){
            return new ResultUtil<Achievement>().setData(achievement);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增成绩")
    public Result<Achievement> insert(Achievement achievement){
        Curriculum c = iCurriculumService.getById(achievement.getCurriculumId());
        if(c == null) {
            return ResultUtil.error("课程不存在");
        }
        achievement.setCurriculumName(c.getTitle());
        User u = iUserService.getById(achievement.getUserId());
        if(u == null) {
            return ResultUtil.error("学生不存在");
        }
        achievement.setUserName(u.getNickname());
        iAchievementService.saveOrUpdate(achievement);
        return new ResultUtil<Achievement>().setData(achievement);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑成绩")
    public Result<Achievement> update(Achievement achievement){
        Curriculum c = iCurriculumService.getById(achievement.getCurriculumId());
        if(c == null) {
            return ResultUtil.error("课程不存在");
        }
        achievement.setCurriculumName(c.getTitle());
        User u = iUserService.getById(achievement.getUserId());
        if(u == null) {
            return ResultUtil.error("学生不存在");
        }
        achievement.setUserName(u.getNickname());
        iAchievementService.saveOrUpdate(achievement);
        return new ResultUtil<Achievement>().setData(achievement);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除成绩")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iAchievementService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getAntvVoList", method = RequestMethod.GET)
    @ApiOperation(value = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoList(){
        List<AntvVo> ansList = new ArrayList<>();
        List<Achievement> teacherList = iAchievementService.list();
        for (Achievement o : teacherList) {
            boolean flag = false;
            for (AntvVo vo : ansList) {
                if(Objects.equals(vo.getTitle(),o.getCurriculumName())) {
                    flag = true;
                    vo.setValue(vo.getValue().add(o.getGrade()));
                    vo.setTimes(vo.getTimes() + 1);
                    break;
                }
            }
            if(!flag) {
                AntvVo vo = new AntvVo();
                vo.setTitle(o.getCurriculumName());
                vo.setType("课程平均分");
                vo.setValue(o.getGrade());
                vo.setTimes(1);
                ansList.add(vo);
            }
        }
        for (AntvVo v : ansList) {
            v.setValue(v.getValue().divide(BigDecimal.valueOf(v.getTimes()),2, RoundingMode.HALF_UP));
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }

    @RequestMapping(value = "/getAntvVoList2", method = RequestMethod.GET)
    @ApiOperation(value = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoList2(){
        List<AntvVo> ansList = new ArrayList<>();
        QueryWrapper<Achievement> qw = new QueryWrapper<>();
        qw.orderByDesc("grade");
        List<Achievement> teacherList = iAchievementService.list(qw);
        for (Achievement achievement : teacherList) {
            AntvVo vo = new AntvVo();
            vo.setTitle(achievement.getUserName() + " - " + achievement.getCurriculumName());
            vo.setType("成绩");
            vo.setValue(achievement.getGrade());
            ansList.add(vo);
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }
}
