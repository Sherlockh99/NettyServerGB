package org.example.file.net.storage.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
        * Создаем пулы потоков
        bossGroup   - поток, отвечающий за входящие соединения
        workerGroup - поток, отвечающий за конкректное соединение от клиента
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // (2) //создаем NettyServer

            /**
            * Настраиваем ServerBootstrap:
                Два тренда:
                    bossGroup - входящие соединения (создает SocketChannel)
                    workerGroup - конкретное соединение (работает с SocketChannel, для обработки данных)
                    .channel - с помощью чего мы хотим работать (в данном случае с NioServerSocketChannel)
             */
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * будет сеттать pipeline для каждого socketChannel
                             */

                            /**
                            * формируем трубопровод (массив), в котором формируем данные
                             */
                            socketChannel.pipeline()
                                    .addLast(
                                            new ByteToStringDecoder(),
                                            new ByteToStringEncoder(),
                                            new BasicHandler()
                            );
                        }
                    }); // (5) (6)
            /**
            * указываем, что наш NettyServer будет слушать порт 45001
            метод sync sync указывает на то, что мы хотим заблокироваться на этой строке
             */
            ChannelFuture channelFuture = serverBootstrap.bind(45001).sync(); //(7)
            /**
            * завершаем работу Netty
             */
            channelFuture.channel().closeFuture().sync();
        }finally {
            /**
            * закрываем пулы потоков
            */
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
