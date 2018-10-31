package com.disruptor.netty.common;

import java.io.Serializable;

public class TranslatorData implements Serializable {

    private static final long serialVersionUID = 87635612899081881L;

    private String id;
    private String name;
    private String message;//传输消息题内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
