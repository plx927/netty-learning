package com.panlingxiao.netty.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Setter
@Getter
public class Header {

    private int magicNum = 0xABCDEF12;

    /**
     * Zero is request,1 is response
     */
    private int type = 0;

    private int requestId;



}
