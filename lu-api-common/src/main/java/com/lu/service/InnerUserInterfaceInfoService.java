package com.lu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lu.model.entity.UserInterfaceInfo;

/**
 * @Author: 鹿又笑
 * @Create: 2024/7/3 16:27
 * @description:
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

}
