package me.marcos.core.selectors;

import me.marcos.core.provider.ResourceProvider;

import java.util.Collection;
import java.util.List;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午10:29
 * Description:
 * 资源提供选择器，用于从可用提供方处选出需要进行访问的资源提供者
 * 一般可用于：
 *
 * 1. 负载均衡策略上的扩展
 * 2. 服务资源上的广播
 */
public interface Selector {

    Collection<ResourceProvider> select(List<ResourceProvider> availableProviders);
}
