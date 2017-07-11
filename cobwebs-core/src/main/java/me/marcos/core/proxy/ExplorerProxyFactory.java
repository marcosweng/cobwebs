package me.marcos.core.proxy;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/12
 * Time:下午10:03
 * Description:
 */
public class ExplorerProxyFactory {
    private static final Map<Class,Object> PROXYS = new HashMap<>();

    public static final synchronized void registedProxy(Class<?> clazz,Object object){
        PROXYS.put(clazz,object);
    }


    @Setter
    private Class proxyClass;


    public Object getObject() {
        return PROXYS.get(proxyClass);
    }
}
