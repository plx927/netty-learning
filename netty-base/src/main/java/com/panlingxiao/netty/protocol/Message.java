package com.panlingxiao.netty.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Setter
@Getter
public class Message {

    private Header header;

    private String body;

    public Message(Header header, String body) {
        this.header = header;
        this.body = body;
    }
}
