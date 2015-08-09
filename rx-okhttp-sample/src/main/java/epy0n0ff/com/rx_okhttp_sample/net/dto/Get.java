package epy0n0ff.com.rx_okhttp_sample.net.dto;

import com.google.gson.annotations.SerializedName;

/**
 * http://httpbin.org/getのレスポンスDto
 * Created by epy0n0ff on 2015/08/06.
 */
public class Get {
  public Args args;
  public Headers headers;
  public String origin;
  public String url;

  public static class Args {
    public String param1;
  }

  public static class Headers {
    @SerializedName("Accept") public String accept;
    @SerializedName("Accept-Encoding") public String acceptEncoding;
    @SerializedName("Host") public String host;
    @SerializedName("User-Agent") public String userAgent;
  }
}
