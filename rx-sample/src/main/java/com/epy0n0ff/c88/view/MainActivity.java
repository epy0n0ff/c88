package com.epy0n0ff.c88.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.epy0n0ff.c88.R;
import com.epy0n0ff.c88.task.WebPAsyncTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements WebPAsyncTask.Callback {
  private static final String TAG = MainActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
  }

  @OnClick(R.id.async_task_button) void onClickAsynckTaskButton() {
    WebPAsyncTask asyncTask = new WebPAsyncTask();
    asyncTask.setCallback(this);
    asyncTask.execute();
  }

  @OnClick(R.id.rxandroid_button) void onClickRxAndroidButton() {
    rxAndroidAndRetrolambda();
  }

  private void rxAndroidAndRetrolambda() {
    Observable.create(subscriber -> {
      Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.RGB_565);
      bitmap.eraseColor(Color.GREEN);

      File bitmapDir =
          Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
      File picture = new File(bitmapDir, "green.webp");
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(picture);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fileOutputStream);
        bitmap.recycle();
      } catch (FileNotFoundException e) {
        subscriber.onError(e);
      }

      subscriber.onNext(picture.getAbsolutePath());
      subscriber.onCompleted();
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(next -> showToast((String) next), this::showError);
  }

  private void onlyRxAndroid() {
    Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.RGB_565);
        bitmap.eraseColor(Color.GREEN);

        File bitmapDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File picture = new File(bitmapDir, "green.webp");
        try {
          FileOutputStream fileOutputStream = new FileOutputStream(picture);
          bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fileOutputStream);
          bitmap.recycle();
        } catch (FileNotFoundException e) {
          subscriber.onError(e);
        }

        subscriber.onNext(picture.getAbsolutePath());
        subscriber.onCompleted();
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
          @Override public void onCompleted() {
            // no-op
          }

          @Override public void onError(Throwable e) {
            showError(e);
          }

          @Override public void onNext(String s) {
            showToast(s);
          }
        });
  }

  private void showToast(@Nullable String path) {
    Toast.makeText(this, path, Toast.LENGTH_LONG).show();
  }

  private void showError(Throwable e) {
    Log.e(TAG, "showError", e);
  }

  @Override public void onReceivePath(@Nullable String path) {
    showToast(path);
  }
}
