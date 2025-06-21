package cn.zwz.pro.controller;

import cn.hutool.core.date.DateUtil;
import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.pro.entity.Message;
import cn.zwz.pro.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 *
 */
@Slf4j
@RestController
@Api(tags = "留言管理接口")
@RequestMapping("/zwz/message")
@Transactional
public class MessageController {

    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条留言")
    public Result<Message> get(@RequestParam String id){
        return new ResultUtil<Message>().setData(iMessageService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部留言个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iMessageService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部留言")
    public Result<List<Message>> getAll(){
        return new ResultUtil<List<Message>>().setData(iMessageService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询留言")
    public Result<IPage<Message>> getByPage(@ModelAttribute Message message ,@ModelAttribute PageVo page){
        QueryWrapper<Message> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(message.getContent())) {
            qw.like("content",message.getContent());
        }
        IPage<Message> data = iMessageService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Message>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改留言")
    public Result<Message> saveOrUpdate(Message message){
        if(iMessageService.saveOrUpdate(message)){
            return new ResultUtil<Message>().setData(message);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增留言")
    public Result<Message> insert(Message message){
        User currUser = securityUtil.getCurrUser();
        message.setUser(currUser.getNickname());
        message.setTime(DateUtil.now());
        message.setReplyContent("");
        message.setReplyUser("");
        message.setReplyTime("");
        iMessageService.saveOrUpdate(message);
        return new ResultUtil<Message>().setData(message);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑留言")
    public Result<Message> update(Message message){
        User currUser = securityUtil.getCurrUser();
        message.setReplyUser(currUser.getNickname());
        message.setReplyTime(DateUtil.now());
        iMessageService.saveOrUpdate(message);
        return new ResultUtil<Message>().setData(message);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除留言")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iMessageService.removeById(id);
        }
        return ResultUtil.success();
    }
}
