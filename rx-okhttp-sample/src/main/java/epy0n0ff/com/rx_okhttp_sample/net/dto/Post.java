package epy0n0ff.com.rx_okhttp_sample.net.dto;

import com.google.gson.annotations.SerializedName;

/**
 * http://httpbin.org/postのレスポンスDto
 * Created by epy0n0ff on 2015/08/07.
 */
public class Post {
  public Args args;
  public String data;
  public Files files;
  public Form form;
  public Headers headers;
  public Object json;
  public String origin;
  public String url;

  public static class Args {
  }

  public static class Files {
  }

  public static class Form {
    public String param1;
  }

  public static class Headers {
    @SerializedName("Accept") public String accept;
    @SerializedName("Accept-Encoding") public String acceptEncoding;
    @SerializedName("Content-Length") public String contentLength;
    @SerializedName("Content-Type") public String contentType;
    @SerializedName("Host") public String host;
    @SerializedName("User-Agent") public String userAgent;
  }
}
