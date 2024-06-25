package com.lu.project.service;

import com.lu.project.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.lu.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lu.project.model.entity.Post;

/**
* @author 鹿又笑
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-06-24 09:18:15
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}
