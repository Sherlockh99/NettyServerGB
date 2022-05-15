package org.example.file.net.storage.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.file.net.storage.netty.dto.BasicResponse;
import org.example.file.net.storage.netty.dto.GetFileListRequest;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicResponse response = (BasicResponse) msg;
        System.out.println(response.getResponse());
        String responseText = response.getResponse();
        if("file list....".equals(responseText)){
            ctx.close();
            return;
        }
        ctx.writeAndFlush(new GetFileListRequest());
    }

}
