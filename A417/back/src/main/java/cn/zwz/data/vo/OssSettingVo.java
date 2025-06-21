package cn.zwz.data.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 *
 */
@Api(tags = "文件存储配置VO类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssSettingVo {

    @ApiModelProperty(value = "访问")
    private String fileView;

    @ApiModelProperty(value = "http")
    private String fileHttp;

    @ApiModelProperty(value = "路径")
    private String filePath;
}
