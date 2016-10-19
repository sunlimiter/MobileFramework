/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.tywho.appdemo.flux.event;

/**
 * http://www.tywho.com
 * 自定义event，必须继承BaseEvent
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public class OtherEvent extends BaseEvent {

    public OtherEvent() {
    }

    public OtherEvent(String type) {
        super(type);
    }
}
