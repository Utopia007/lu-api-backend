package com.lu.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/27 16:46
 * @description: 接口调用请求
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

    private static final long serialVersionUID = 1L;
}
