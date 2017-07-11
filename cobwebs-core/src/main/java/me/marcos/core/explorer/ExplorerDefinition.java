package me.marcos.core.explorer;

import lombok.*;
import me.marcos.core.serialize.Deserialier;
import me.marcos.core.serialize.Serializer;

import java.util.List;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午10:28
 * Description:
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExplorerDefinition {

    /**
     * 定义explorer的唯一值
     */
    private String metaId;

    /**
     * 定义explorer的返回类型
     */
    private Class returnType;


    /**
     * 定义explorer的访问参数类型
     */
    private List<Class> parameterTypes;

    /**
     * 序列化接口
     */
    private Serializer serializer;

    /**
     * 反序列化接口
     */
    private Deserialier deserialier;



    /**
     * 浏览对应的服务
     *
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> T explore(Object... parameters) {
        //构造基础输出
        //解析返回内容
        return null;
    }
}
