package org.example.file.net.storage.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.file.net.storage.netty.dto.AuthRequest;
import org.example.file.net.storage.netty.dto.BasicRequest;
import org.example.file.net.storage.netty.dto.BasicResponse;
import org.example.file.net.storage.netty.dto.GetFileListRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BasicHandler extends ChannelInboundHandlerAdapter {

    /**
    ChannelInboundHandlerAdapter - дефолтные реализации Netty
    Здесь же мы реализовываем (переопределяем) только то, что нам надо
     */

    private static final Map<Class<? extends BasicRequest>, Consumer<ChannelHandlerContext>> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(AuthRequest.class, channelHandlerContext -> {
            channelHandlerContext.writeAndFlush(new BasicResponse("login ok"));
        });
        REQUEST_HANDLERS.put(GetFileListRequest.class, channelHandlerContext -> {
            channelHandlerContext.writeAndFlush(new BasicResponse("file list...."));
        });
    }

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

        BasicRequest request = (BasicRequest) msg;
        System.out.println(request.getType());

        /* !!! ПЕРЕДЕЛАТЬ НА ЭТОТ МЕТОД
        //Consumer<ChannelHandlerContext> channelHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelHandlerContextConsumer.accept(ctx);
         */
        if(request instanceof AuthRequest){
            BasicResponse loginOkResponse = new BasicResponse("login ok");
            ctx.writeAndFlush(loginOkResponse);
        }else if(request instanceof GetFileListRequest){
            BasicResponse basicResponse = new BasicResponse("file list....");
            ctx.writeAndFlush(basicResponse);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * обрабатывает ошибки
         */
        cause.printStackTrace();
    }
}
