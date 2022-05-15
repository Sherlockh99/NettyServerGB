package org.example.file.net.storage.netty.dto;

public class GetFileListRequest implements BasicRequest{

    @Override
    public String getType() {
        return "getFileList";
    }
}
