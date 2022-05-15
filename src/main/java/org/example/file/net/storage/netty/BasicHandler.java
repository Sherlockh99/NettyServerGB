package org.example.file.net.storage.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BasicHandler extends ChannelInboundHandlerAdapter {

    /**
    ChannelInboundHandlerAdapter - дефолтные реализации Netty
    Здесь же мы реализовываем (переопределяем) только то, что нам надо
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * срабатывает при подключении клиента
         */
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * обрабатывает входные данные
         */
        ByteBuf buffer = (ByteBuf) msg;
        StringBuffer stringBuffer = new StringBuffer();
        while (buffer.isReadable()){
            stringBuffer.append((char) buffer.readByte());
        }
        System.out.println(stringBuffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * обрабатывает ошибки
         */
        cause.printStackTrace();
    }
}
