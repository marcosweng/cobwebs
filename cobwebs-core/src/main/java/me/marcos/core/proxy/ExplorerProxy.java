package me.marcos.core.proxy;

import me.marcos.core.explorer.ExplorerDefinition;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/12
 * Time:下午9:54
 * Description:
 */
public interface ExplorerProxy {
    Object createProxy(Class explorerClass);
}
