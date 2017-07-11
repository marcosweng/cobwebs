package me.marcos.core.message;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/12
 * Time:下午9:16
 * Description:
 */
@Data
public class Request {

    //调用链相关
    private String rootId;
    private String prevId;
    private String requestId;

    //服务标识
    private String metaId;

    //请求参数
    private Object[] parameters;

    //设置调用链的额外属性
    private Map<String,String> properties = new HashMap<String, String>();

}
