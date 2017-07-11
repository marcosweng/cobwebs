package me.marcos.core.message;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/12
 * Time:下午9:16
 * Description:
 */
public class Response {

    //调用链相关
    private String rootId;
    private String prevId;
    private String requestId;

    //服务标识
    private String metaId;

    //结果对象
    private Object result;
}
