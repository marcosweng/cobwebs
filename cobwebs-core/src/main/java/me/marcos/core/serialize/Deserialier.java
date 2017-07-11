package me.marcos.core.serialize;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午11:10
 * Description:
 * 反序列化处理接口
 */
public interface Deserialier {
    <T> T deserialize(byte[] data,Class<T> type);
}
