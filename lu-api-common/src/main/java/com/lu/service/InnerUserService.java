package com.lu.service;

import com.lu.model.entity.User;

/**
 * @Author: 鹿又笑
 * @Create: 2024/7/3 16:32
 * @description:
 */
public interface InnerUserService{

    /**
     * 数据库中查询是否已经分配给用户密钥（ak）
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
