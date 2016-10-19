package com.tywho.appdemo.framework.control;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-05 16:14
 */
public class PresenterProxy {
//    private static final PresenterProxy m_instance = new PresenterProxy();
//
//    public static PresenterProxy getInstance() {
//        return m_instance;
//    }


    public static <T> T getInstance(Class<T> theClass) {
        try {
            return theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T bind(Class cls, Object o) {
        Object ret = getInstance(cls);
        ((BasePresenter) ret).attachView(o);
        return (T) ret;
    }
}
