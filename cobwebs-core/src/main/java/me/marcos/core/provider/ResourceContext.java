package me.marcos.core.provider;

import lombok.Data;
import me.marcos.core.resource.ResourceDefinition;

import java.util.List;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午11:18
 * Description:
 *
 * 资源上下文
 */
@Data
public class ResourceContext {

    public static ResourceContext UNKNOWN_RESOURCE = new ResourceContext();

    private ResourceDefinition resourceDefinition;

    /**
     * 资源提供方，一般情况下有多个
     */
    private List<ResourceProvider> providers;


    /**
     * 可用资源提供方，某些情况下，资源提供方因为限制访问被禁用，但连接需要保持，某些服务不可访问，
     * 这时需冗余一份提供方数据出来，存储到availableProviders中
     */
    private List<ResourceProvider> availableProviders;
}
