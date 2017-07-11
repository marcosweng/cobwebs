package me.marcos.cobwebs.protocol.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.marcos.core.message.Request;
import me.marcos.core.message.Response;
import me.marcos.core.protocols.server.Server;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午9:54
 * Description:
 * Netty服务器，提供服务访问能力
 */
@Slf4j
public class NettyServer extends SimpleChannelInboundHandler<Request> implements Server {
    private static final ServerBootstrap BOOTSTRAP = new ServerBootstrap();
    @Setter
    private int port;

    @Setter
    private int maxThreadSize = 250;
    @Setter
    private int corePoolSize = 100;
    @Setter
    private long keepAliveTime = 120;

    private ThreadPoolExecutor threadPoolExecutor;

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(150);
        try {
            BOOTSTRAP.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {

                            channel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024 * 1024, 0, 4, 0, 0))
                                    .addLast(new NettyDecoder(Request.class))
                                    .addLast(new NettyEncoder(Response.class))
                                    .addLast(NettyServer.this);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = BOOTSTRAP.bind("0.0.0.0", port).sync();
            log.info("启动netty节点端口 {}", port);
            future.channel().closeFuture().sync();
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxThreadSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        } catch (Exception e) {
            throw new RuntimeException("无法启动netty服务器", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public Response accept(Request request) {
        //进行request处理
        return null;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        threadPoolExecutor.submit(() -> ctx.writeAndFlush(accept(msg)));
    }
}
