package epy0n0ff.com.rx_okhttp_sample.mapper;

import android.support.annotation.NonNull;
import com.google.gson.Gson;

/**
 * レスポンスのJsonMapperクラス
 * Created by epy0n0ff on 2015/08/06.
 */
public class ResponseJsonMapper {
  private final Gson mGson;

  public ResponseJsonMapper() {
    mGson = new Gson();
  }

  public <T> T transformResponse(@NonNull String response, Class<T> classz) {
    return mGson.fromJson(response, classz);
  }
}
