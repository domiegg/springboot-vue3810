package cn.zwz.pro.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * 成绩 实体类
 * @author
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_achievement")
@TableName("a_achievement")
@ApiModel(value = "成绩")
public class Achievement extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String curriculumId;

    @ApiModelProperty(value = "课程名称")
    private String curriculumName;

    @ApiModelProperty(value = "学生ID")
    private String userId;

    @ApiModelProperty(value = "学生姓名")
    private String userName;

    @ApiModelProperty(value = "成绩")
    private BigDecimal grade;

    @ApiModelProperty(value = "备注")
    private String reark;
}