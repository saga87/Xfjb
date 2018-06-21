package com.fxn.xfjb.model;

import com.fxn.xfjb.App;
import com.fxn.xfjb.entity.ContextText;
import com.fxn.xfjb.entity.Fankui;
import com.fxn.xfjb.entity.JbList;
import com.fxn.xfjb.entity.PhotoId;
import com.fxn.xfjb.entity.WtflList;
import com.fxn.xfjb.unit.ICallBack;
import com.fxn.xfjb.unit.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fxn on 2017/9/2.
 */

public class InformModel {
    private static final String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private static final String BOUNDARY = "FlPm4LpSXsE" ; //UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
    /**
     *  举报查询
     */
    public void appcxnr(String reportRand,final ICallBack callBack) {
        //创建一个Request
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/appcxnr?reportRand="+reportRand)
                .get()
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    ContextText user = gson.fromJson(htmlStr, ContextText.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *  举报级别
     */
    public void jblist(final ICallBack callBack) {
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/jblist")
                .post(body)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
                    ArrayList<JsonObject> jsonObjects = new Gson().fromJson(htmlStr, type);
                    ArrayList<JbList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, JbList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *  举报问题分类
     */
    public void wtfllist(final ICallBack callBack) {
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/wtfllist")
                .post(body)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
                    ArrayList<JsonObject> jsonObjects = new Gson().fromJson(htmlStr, type);
                    ArrayList<WtflList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, WtflList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *  举报问题细节分类
     */
    public void wtflxjlist(String id,final ICallBack callBack) {
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("id",id)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/wtflxjlist")
                .post(body)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
                    ArrayList<JsonObject> jsonObjects = new Gson().fromJson(htmlStr, type);
                    ArrayList<WtflList> arrayList = new ArrayList<>();
                    for (JsonObject jsonObject : jsonObjects){
                        arrayList.add(new Gson().fromJson(jsonObject, WtflList.class));
                    }
                    callBack.succeed(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *  举报内容
     */
    public void appadd(String userName,String userIdCard,String userContact,String reportName,
                       String reportDept,String reportLevelId,String title,String content,
                       String reportQuestionId1, String reportQuestionId,String fileIds,final ICallBack callBack) {
        //创建一个Request
        FormBody body = new FormBody.Builder()
                .add("userName", userName)
                .add("userIdCard", userIdCard)
                .add("userContact", userContact)
                .add("reportName", reportName)
                .add("reportDept", reportDept)
                .add("reportLevelId", reportLevelId)
                .add("title", title)
                .add("content", content)
                .add("reportQuestionId1", reportQuestionId1)
                .add("reportQuestionId", reportQuestionId)
                .add("fileIds", fileIds)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/appadd")
                .post(body)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr =  response.body().string();
                    Gson gson = new Gson();
                    Fankui user = gson.fromJson(htmlStr, Fankui.class);
                    callBack.succeed(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    /**
     *  上传图片
     */
    public void addFeedback(String mImgUrls, final ICallBack callBack) {
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File f = new File(mImgUrls);
        builder.addFormDataPart("photo", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(UrlUtil.url+"Home/Wx/appuploadPic")
                .header("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)
                .post(requestBody)
                .build();
        //new call
        Call call = App.getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e.getMessage().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String htmlStr = response.body().string();
                    Gson gson = new Gson();
                    PhotoId user = gson.fromJson(htmlStr, PhotoId.class);
                    callBack.succeed(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
