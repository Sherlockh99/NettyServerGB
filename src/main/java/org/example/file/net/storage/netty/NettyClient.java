package org.example.file.net.storage.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.example.file.net.storage.netty.dto.AuthRequest;

public class NettyClient {
    private static final int MB_20 = 20 * 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress("localhost", 45001)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new ObjectDecoder(MB_20, ClassResolvers.cacheDisabled(null)),
                                new ObjectEncoder(),
                                new ClientHandler()
                        );
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();
        Channel channel = channelFuture.channel();

        AuthRequest authRequest = new AuthRequest("log","pass");
        channel.writeAndFlush(authRequest);

        channelFuture.channel().closeFuture().sync();

    }
}
