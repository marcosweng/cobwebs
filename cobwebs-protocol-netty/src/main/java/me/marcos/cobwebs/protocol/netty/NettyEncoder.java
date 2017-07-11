package me.marcos.cobwebs.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午10:10
 * Description:
 * 提供对目标类的序列化能力
 */
@Slf4j
public class NettyEncoder extends MessageToByteEncoder {
    private Class<?> encodeClass;

    public NettyEncoder(Class<?> encodeClass) {
        this.encodeClass = encodeClass;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

    }
}
