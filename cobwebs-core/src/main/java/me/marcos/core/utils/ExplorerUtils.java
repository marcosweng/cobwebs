package me.marcos.core.utils;

import me.marcos.core.annotations.Alias;
import me.marcos.core.annotations.Version;
import me.marcos.core.explorer.ExplorerDefinition;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/12
 * Time:下午9:35
 * Description:
 */
public class ExplorerUtils {
    private static final Map<String, ExplorerDefinition> EXPLORER_DEFINITION_MAP = new HashMap<String, ExplorerDefinition>();


    public static List<ExplorerDefinition> extractExplorerDefinition(Class resourceClass) {
        //1. 保存class的配置
        List<ExplorerDefinition> explorerDefinitions = new ArrayList<ExplorerDefinition>();
        Method[] methods = resourceClass.getDeclaredMethods();

        return Stream.of(methods)
                .flatMap(method -> {
                    String preffixMetaId = parseExplorerAliasName(resourceClass) + "." + parseMethodAlias(method);
                    String[] versionValues = parseExplorerVersion(resourceClass, method);
                    return Stream.of(versionValues)
                            .map((versionValue) -> preffixMetaId + ":" + versionValue)
                            .map((metaId) ->
                                    ExplorerDefinition
                                            .builder()
                                            .metaId(metaId)
                                            .returnType(method.getReturnType())
                                            .build()
                            );
                })
                .collect(Collectors.toList());

    }


    private static String[] parseExplorerVersion(Class clazz, Method method) {
        Version version = (Version) clazz.getAnnotation(Version.class);
        String[] versionValues = {"1.0"};
        if (version != null) {
            versionValues = version.value();
        }

        Version methodVersion = method.getAnnotation(Version.class);
        if (methodVersion != null) {
            versionValues = methodVersion.value();
        }
        return versionValues;
    }

    private static String parseExplorerAliasName(Class clazz) {
        String serviceAliasName = clazz.getName();
        Alias alias = (Alias) clazz.getAnnotation(Alias.class);
        if (alias != null && StringUtils.isNoneBlank(alias.value())) {
            serviceAliasName = alias.value();
        }
        return serviceAliasName;
    }

    private static String parseMethodAlias(Method method) {
        String methodAliasName = method.getName();
        Alias methodAlias = method.getAnnotation(Alias.class);
        if (methodAlias != null && StringUtils.isNoneBlank(methodAlias.value())) {
            methodAliasName = methodAlias.value();
        }
        return methodAliasName;
    }

}
