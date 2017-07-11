package me.marcos.cobwebs.protocol.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import me.marcos.core.message.Response;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午9:57
 * Description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {

    @Getter
    private volatile Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {

    }

    public boolean isClose(){
        return this.channel!=null && !this.channel.isOpen();
    }


}
