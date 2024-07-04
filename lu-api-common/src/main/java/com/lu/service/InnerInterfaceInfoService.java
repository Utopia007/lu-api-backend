package com.lu.service;

import com.lu.model.entity.InterfaceInfo;

/**
 * @Author: 鹿又笑
 * @Create: 2024/7/3 16:31
 * @description:
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询接口是否存在
     *
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

}
