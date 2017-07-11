package me.marcos.cobwebs.protocol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import me.marcos.core.message.Request;
import me.marcos.core.message.Response;
import me.marcos.core.utils.URL;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午9:55
 * Description:
 */
@Slf4j
public class NettyConnectionManager {

    private Map<String, NettyClientHandler> connectedHandlers = new HashMap<String, NettyClientHandler>();
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup(150);


    public NettyClientHandler connect(String address) {

        if (!isConnect(address)) {
            synchronized (connectedHandlers) {
                if (!isConnect(address)) {
                    URL url = URL.of(address);
                    connectNode(url);
                }
            }
        }
        return connectedHandlers.get(address);
    }

    private void connectNode(URL url) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline cp = ch.pipeline();
                        cp.addLast(new NettyEncoder(Request.class));
                        cp.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024 * 1024, 0, 4, 0, 0));
                        cp.addLast(new NettyDecoder(Response.class));
                        cp.addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect();
        try {
            channelFuture.sync();
        } catch (Exception e) {
            //ignore
        }
        if (channelFuture.isSuccess()) {
            log.debug("连接到地址：{}", url);
            NettyClientHandler handler = channelFuture.channel().pipeline().get(NettyClientHandler.class);
            connectedHandlers.put(url.toString(), handler);
        } else {
            throw new RuntimeException(MessageFormat.format("无法连接到地址：{}", url));
        }

    }

    public boolean isConnect(String url) {
        NettyClientHandler nettyClientHandler = connectedHandlers.get(url);
        return nettyClientHandler != null && nettyClientHandler.isClose();
    }

}
