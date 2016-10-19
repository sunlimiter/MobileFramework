package com.tywho.appdemo.framework.net;

import com.tywho.appdemo.api.Api;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.tywho.com
 * http工厂类
 * @author：sunlimiter
 * @create：2016-05-04 10:19
 */
public class HttpFactory {
    public static Api provideUserService() {
        return provideService(Api.class);
    }

    private static Map<Class, Object> m_service = new HashMap();

    public static <T> T provideService(Class cls) {
        Object serv = m_service.get(cls);
        if (serv == null) {
            synchronized (cls) {
                serv = m_service.get(cls);
                if (serv == null) {
                    serv = HttpControl.getInstance().createService(cls);
                    m_service.put(cls, serv);
                }
            }
        }
        return (T) serv;
    }
}
