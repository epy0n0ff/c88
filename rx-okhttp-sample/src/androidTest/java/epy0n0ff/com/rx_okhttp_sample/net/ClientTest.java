package epy0n0ff.com.rx_okhttp_sample.net;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import epy0n0ff.com.rx_okhttp_sample.net.dto.Get;
import epy0n0ff.com.rx_okhttp_sample.net.dto.Post;
import epy0n0ff.com.rx_okhttp_sample.net.feign.exception.HttpErrorException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.Observer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link Client}のテストクラス
 * Created by epy0n0ff on 2015/08/06.
 */
@RunWith(AndroidJUnit4.class) public class ClientTest {
  public static final int TIMEOUT_SEC = 10;

  @Test public void get() throws Exception {
    String param = "hoge";

    Client client = new Client();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    client.get(param).subscribe(new ObserverCountDownLatch<Get>(countDownLatch) {
      @Override public void onNext(Get o) {
        assertThat(o.args.param1, is(param));

        assertThat(o.headers, is(notNullValue()));
        assertThat(o.headers.accept, is(notNullValue()));
        assertThat(o.headers.acceptEncoding, is(notNullValue()));
        assertThat(o.headers.host, is(notNullValue()));
        assertThat(o.headers.userAgent, is(notNullValue()));

        assertThat(o.origin, is(notNullValue()));
        assertThat(o.url, is(notNullValue()));
      }
    });
    assertThat("check timeout.", true, is(countDownLatch.await(TIMEOUT_SEC, TimeUnit.SECONDS)));
  }

  @Test public void post() throws InterruptedException {
    String param = "hoge";

    Client client = new Client();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    client.post(param).subscribe(new ObserverCountDownLatch<Post>(countDownLatch) {
      @Override public void onNext(Post o) {
        assertThat(o.args, is(notNullValue()));
        assertThat(o.data, is(notNullValue()));
        assertThat(o.files, is(notNullValue()));

        assertThat(o.form, is(notNullValue()));
        assertThat(o.form.param1, is(param));

        assertThat(o.headers, is(notNullValue()));
        assertThat(o.headers.accept, is(notNullValue()));
        assertThat(o.headers.acceptEncoding, is(notNullValue()));
        assertThat(o.headers.contentLength, is(notNullValue()));
        assertThat(o.headers.contentType, is(notNullValue()));
        assertThat(o.headers.host, is(notNullValue()));
        assertThat(o.headers.userAgent, is(notNullValue()));

        assertThat(o.json, is(nullValue()));
        assertThat(o.origin, is(notNullValue()));
        assertThat(o.url, is(notNullValue()));
      }
    });

    assertThat("check timeout.", true, is(countDownLatch.await(TIMEOUT_SEC, TimeUnit.SECONDS)));
  }

  @Test public void internalServerError() throws InterruptedException {
    Client client = new Client();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    client.internalServerError().subscribe(new ObserverCountDownLatch<Post>(countDownLatch) {
      @Override public void onError(Throwable e) {
        super.onError(e);
        assertThat(e instanceof HttpErrorException, is(true));
        assertThat(((HttpErrorException) e).getStatusCode(), is(500));
        countDownLatch.countDown();
      }

      @Override public void onNext(Post o) {
      }
    });

    assertThat("check timeout.", true, is(countDownLatch.await(TIMEOUT_SEC, TimeUnit.SECONDS)));
  }

  public abstract class ObserverCountDownLatch<T> implements Observer<T> {
    private final CountDownLatch mCountDownLatch;

    public ObserverCountDownLatch(CountDownLatch countDownLatch) {
      mCountDownLatch = countDownLatch;
    }

    @Override public void onCompleted() {
      mCountDownLatch.countDown();
    }

    @Override public void onError(Throwable e) {
      Log.e("error", "onError", e);
    }

    @Override public abstract void onNext(T o);
  }
}