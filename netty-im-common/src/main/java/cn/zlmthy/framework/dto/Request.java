package cn.zlmthy.framework.dto;

import lombok.Data;

@Data
public class Request {

    private long reqNo;

    private long timestamp;

    private Object data;
}
