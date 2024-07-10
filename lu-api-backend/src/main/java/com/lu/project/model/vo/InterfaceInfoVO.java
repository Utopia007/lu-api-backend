package com.lu.project.model.vo;

import com.lu.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口信息封装视图
 *
 * @Author: 鹿又笑
 * @Create: 2024/7/5 10:45
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
// 这里就继承InterfaceInfo，再补充一个调用次数的字段
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
