package org.example.file.net.storage.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToStringDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        while (byteBuf.isReadable()){
            stringBuffer.append((char) byteBuf.readByte());
        }
        list.add(stringBuffer.toString());
    }
}
