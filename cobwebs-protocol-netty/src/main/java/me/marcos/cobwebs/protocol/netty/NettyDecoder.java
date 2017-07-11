package me.marcos.cobwebs.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午10:12
 * Description:
 * 提供请求响应内容的序列化能力
 */
public class NettyDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;


    public NettyDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
