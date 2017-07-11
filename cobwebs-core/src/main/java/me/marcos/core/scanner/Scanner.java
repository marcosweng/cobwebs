package me.marcos.core.scanner;

import java.util.Collection;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午10:15
 * Description:
 * 用于定义扫描器
 *  ：如针对资源的扫描，资源探针的扫描
 */
public interface Scanner<T> {
    Collection<T> scan();
}
