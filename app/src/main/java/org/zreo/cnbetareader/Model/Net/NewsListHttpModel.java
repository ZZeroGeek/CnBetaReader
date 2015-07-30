package org.zreo.cnbetareader.Model.Net;

import com.google.gson.reflect.TypeToken;

import org.zreo.cnbetareader.Entitys.ResponseEntity;

/**
 * Created by zqh on 2015/7/30  11:01.
 * Email:zqhkey@163.com
 * 对各种状况进行处理，返回数据到界面
 */
public abstract class NewsListHttpModel<T> extends GsonHttpModel<ResponseEntity<T>> {

    public NewsListHttpModel(TypeToken<ResponseEntity<T>> typeToken) {
        super(typeToken);
    }


    @Override
    protected void onSuccess(int sattusCode, ResponseEntity<T> model) {
        if (model.getState().equals("success")) {
            T result = model.getResult();
            onSuccess(result);

        } else {
            onError(sattusCode, null, new Exception(model.getState()));
        }
    }

    @Override
    protected void onError(int sttusCode, String responseString, Throwable throwable) {
        onError();
    }

    @Override
    protected void onFailure(int sttusCode, String responseString, Throwable throwable) {
        onFailure();
    }

    protected abstract void onSuccess(T result);

    protected abstract void onError();

    protected abstract void onFailure();
}
