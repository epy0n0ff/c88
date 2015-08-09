package epy0n0ff.com.rx_okhttp_sample.net.feign.codec;

import epy0n0ff.com.rx_okhttp_sample.net.feign.exception.HttpErrorException;
import feign.Response;

/**
 * ErrorDecoder
 * Created by epy0n0ff on 2015/08/07.
 */
public class ErrorDecoder implements feign.codec.ErrorDecoder {
  @Override public Exception decode(String methodKey, Response response) {
    return new HttpErrorException(response.status());
  }
}
