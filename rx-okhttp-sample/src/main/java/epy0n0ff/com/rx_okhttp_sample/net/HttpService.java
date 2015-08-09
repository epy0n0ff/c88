package epy0n0ff.com.rx_okhttp_sample.net;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * http://httpbin.orgのサービスクラス
 * Created by epy0n0ff on 2015/08/06.
 */
public interface HttpService {
  @RequestLine("GET /get?param1={param1}")
  String get(@Param("param1") String param1);

  @Headers("Content-Type: application/x-www-form-urlencoded")
  @Body("param1={param1}")
  @RequestLine("POST /post")
  String post(@Param("param1") String param1);

  @RequestLine("GET /status/500") String getInternalServerError();
}
