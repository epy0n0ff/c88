package epy0n0ff.com.rx_okhttp_sample.net;

import android.support.annotation.NonNull;
import epy0n0ff.com.rx_okhttp_sample.mapper.ResponseJsonMapper;
import epy0n0ff.com.rx_okhttp_sample.net.dto.Get;
import epy0n0ff.com.rx_okhttp_sample.net.dto.Post;
import epy0n0ff.com.rx_okhttp_sample.net.feign.codec.ErrorDecoder;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://httpbin.orgのクライアントクラス
 * Created by epy0n0ff on 2015/08/06.
 */
public class Client {
  @NonNull private com.squareup.okhttp.OkHttpClient getDelegate() {
    com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
    okHttpClient.setConnectTimeout(25, TimeUnit.SECONDS);
    okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    return okHttpClient;
  }

  @NonNull private HttpService getService() {
    return Feign.builder()
        .decoder(new Decoder.Default())
        .encoder(new Encoder.Default()).errorDecoder(new ErrorDecoder())
        .client(new feign.okhttp.OkHttpClient(getDelegate()))
        .target(HttpService.class, "http://httpbin.org");
  }

  public Observable get(@NonNull String param) {
    return Observable.create(subscriber -> {
      ResponseJsonMapper mapper = new ResponseJsonMapper();
      subscriber.onNext(mapper.transformResponse(getService().get(param), Get.class));
      subscriber.onCompleted();
    }).compose(applySchedulers());
  }

  public Observable post(@NonNull String param) {
    return Observable.create(subscriber -> {
      ResponseJsonMapper mapper = new ResponseJsonMapper();
      subscriber.onNext(mapper.transformResponse(getService().post(param), Post.class));
      subscriber.onCompleted();
    }).compose(applySchedulers());
  }

  public Observable internalServerError() {
    return Observable.create(subscriber -> {
      ResponseJsonMapper mapper = new ResponseJsonMapper();
      subscriber.onNext(
          mapper.transformResponse(getService().getInternalServerError(), Post.class));
      subscriber.onCompleted();
    }).compose(applySchedulers());
  }

  <T> Observable.Transformer<T, T> applySchedulers() {
    return observable -> observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
