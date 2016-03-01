package com.gs.pp.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class FileBean implements Serializable {

    private MultipartFile file;
    private int fileType;//1 文件 2 图片   3 static 静态资源文件
    private String fileName;//带后缀的文件名

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}