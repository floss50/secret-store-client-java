package com.oceanprotocol.secretstore.models;

public class HttpResponse {

    private int statusCode;
    private String body;
    private String charset;
    private long contentLenght;

    public HttpResponse(int statusCode, String body, String charset, long contentLenght) {
        this.statusCode = statusCode;
        this.body = body;
        this.charset = charset;
        this.contentLenght = contentLenght;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public long getContentLenght() {
        return contentLenght;
    }

    public void setContentLenght(long contentLenght) {
        this.contentLenght = contentLenght;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", charset='" + charset + '\'' +
                ", contentLenght=" + contentLenght +
                '}';
    }
}
