package cn.zwz.basics.parameter;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * 
 */
@ApiOperation(value = "验证码接口配置")
@Data
@Configuration
@ConfigurationProperties(prefix = "intercept")
public class CaptchaProperties {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需要图片验证码验证的接口")
    private List<String> verification = new ArrayList<>();

    @ApiModelProperty(value = "需要企微验证码验证的接口")
    private List<String> wechat = new ArrayList<>();
}
