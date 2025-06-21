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

/**
 * 留言 实体类
 * @author 
 * 
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_message")
@TableName("a_message")
@ApiModel(value = "留言")
public class Message extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言内容")
    private String content;

    @ApiModelProperty(value = "留言人")
    private String user;

    @ApiModelProperty(value = "留言时间")
    private String time;

    @ApiModelProperty(value = "回复内容")
    private String replyContent;

    @ApiModelProperty(value = "回复人")
    private String replyUser;

    @ApiModelProperty(value = "回复时间")
    private String replyTime;
}