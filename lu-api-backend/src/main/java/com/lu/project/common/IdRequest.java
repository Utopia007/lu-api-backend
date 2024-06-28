package com.lu.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/27 9:53
 * @description:
 */
@Data
public class IdRequest implements Serializable {

    private Long id;

    private static final long serialVersionUID = 1L;

}
