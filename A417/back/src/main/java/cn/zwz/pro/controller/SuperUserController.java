package cn.zwz.pro.controller;

import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import cn.zwz.pro.entity.Achievement;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "用户接口")
@RequestMapping("/zwz/superUser")
@Transactional
public class SuperUserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部学生")
    public Result<List<User>> getUserList(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("type",0);
        return new ResultUtil<List<User>>().setData(iUserService.list(qw));
    }
}
