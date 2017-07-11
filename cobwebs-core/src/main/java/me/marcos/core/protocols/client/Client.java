package me.marcos.core.protocols.client;

import me.marcos.core.message.Request;
import me.marcos.core.message.Response;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午9:49
* Description:
 * 协议客户端：
 * 微服务支持的协议客户端要与服务提供方对应
 */
public interface Client {
    Response explore(Request request);
}
