package com.epy0n0ff.c88.task;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * WebP生成保存するタスク
 * Created by epy0n0ff on 2015/08/06.
 */
public class WebPAsyncTask extends AsyncTask<Void, Integer, String> {
  @Nullable private Callback mCallback;

  public void setCallback(@Nullable Callback callback) {
    mCallback = callback;
  }

  @Override protected void onPostExecute(@Nullable String s) {
    if (mCallback == null) {
      return;
    }

    mCallback.onReceivePath(s);
  }

  @Nullable @Override protected String doInBackground(Void... voids) {
    Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.RGB_565);
    bitmap.eraseColor(Color.GREEN);

    File bitmapDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File picture = new File(bitmapDir, "green.webp");
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(picture);
      bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fileOutputStream);
      bitmap.recycle();
    } catch (FileNotFoundException e) {
      return null;
    }
    return picture.getAbsolutePath();
  }

  public interface Callback {
    void onReceivePath(@Nullable String path);
  }
}
