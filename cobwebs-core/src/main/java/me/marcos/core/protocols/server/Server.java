package me.marcos.core.protocols.server;

import me.marcos.core.message.Request;
import me.marcos.core.message.Response;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午9:49
 * Description:
 * 协议服务器：
 * 需要提供功能：
 *
 * 1. 开放资源访问端口
 * 2. 处理资源访问
 * 3. 提供接口文档描述说明
 */
public interface Server {

    void start();

    Response accept(Request request);
}
