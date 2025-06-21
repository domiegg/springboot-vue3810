package cn.zwz.pro.serviceimpl;

import cn.zwz.pro.mapper.AchievementMapper;
import cn.zwz.pro.entity.Achievement;
import cn.zwz.pro.service.IAchievementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 成绩 服务层接口实现
 * @author
 *
 */
@Slf4j
@Service
@Transactional
public class IAchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements IAchievementService {

    @Autowired
    private AchievementMapper achievementMapper;
}