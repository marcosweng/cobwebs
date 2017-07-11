package me.marcos.core.serialize;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午11:10
 * Description:
 * 序列化接口
 */
public interface Serializer {
    byte[] serialize(Object object);
}
