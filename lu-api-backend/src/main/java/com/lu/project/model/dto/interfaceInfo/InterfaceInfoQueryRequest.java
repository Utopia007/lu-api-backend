package com.lu.project.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lu.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/24 9:56
 * @description:
 */
@Data
//@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

}
