package com.lu.project.model.enums;

import javax.xml.soap.Text;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/27 10:20
 * @description:
 */
public enum InterfaceInfoStatusEnum {

    OFFLINE("下线", 0),
    ONLINE("上线", 1);

    private final String text;
    private final int value;

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
