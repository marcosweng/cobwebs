package me.marcos.core.provider;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午11:16
 * Description:
 * 资源中心
 */
@Slf4j
public class ResourceCenters {

    private static final Map<String, ResourceContext> RESOURCE_CONTEXT_MAP = new HashMap<String, ResourceContext>();

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 通过metaId,获取资源上下文
     *
     * @param metaId
     * @return
     */
    public static ResourceContext explore(String metaId) {
        try {
            lock.readLock().lock();
            if (RESOURCE_CONTEXT_MAP.containsKey(metaId)) {
                return RESOURCE_CONTEXT_MAP.get(metaId);
            }
            return ResourceContext.UNKNOWN_RESOURCE;
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * 存储资源上下文
     *
     * @param resourceContext
     */
    public void store(ResourceContext resourceContext) {
        try {
            lock.writeLock().lock();
            RESOURCE_CONTEXT_MAP.put(resourceContext.getResourceDefinition().getMetaId(), resourceContext);
        } finally {
            lock.writeLock().unlock();
        }
    }


}
