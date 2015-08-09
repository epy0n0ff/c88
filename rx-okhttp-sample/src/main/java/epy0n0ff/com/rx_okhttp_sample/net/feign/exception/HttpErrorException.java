package epy0n0ff.com.rx_okhttp_sample.net.feign.exception;

/**
 * 200台ステータス以外の時に投げる例外
 * Created by epy0n0ff on 2015/08/07.
 */
public class HttpErrorException extends RuntimeException {
  private final int mStatusCode;

  public HttpErrorException(int statusCode) {
    mStatusCode = statusCode;
  }

  public int getStatusCode() {
    return mStatusCode;
  }
}
