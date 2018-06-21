package com.fxn.xfjb.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fxn.xfjb.R;
import com.fxn.xfjb.camera.PhotoUtils;
import com.fxn.xfjb.camera.ToastUtils;
import com.fxn.xfjb.entity.Fankui;
import com.fxn.xfjb.entity.JbList;
import com.fxn.xfjb.entity.PhotoId;
import com.fxn.xfjb.entity.WtflList;
import com.fxn.xfjb.model.InformModel;
import com.fxn.xfjb.unit.ICallBack;
import com.fxn.xfjb.view.CameraBottomMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportProblemActivity extends AppCompatActivity {

    @Bind(R.id.iv_problem_back)
    ImageView ivJbcxBack;
    @Bind(R.id.et_problem_sfsmjb_name)
    EditText etProblemSfsmjbName;
    @Bind(R.id.et_problem_sfsmjb_sfz)
    EditText etProblemSfsmjbSfz;
    @Bind(R.id.et_problem_sfsmjb_phone)
    EditText etProblemSfsmjbPhone;
    @Bind(R.id.ll_problem_sfsmjb)
    LinearLayout llProblemSfsmjb;
    @Bind(R.id.et_problem_name)
    EditText etProblemName;
    @Bind(R.id.et_problem_unit)
    EditText etProblemUnit;
    @Bind(R.id.et_problem_jbbt)
    EditText etProblemJbbt;
    @Bind(R.id.et_problem_jbwt)
    EditText etProblemJbwt;
    @Bind(R.id.btn_problem_tj)
    Button btnProblemTj;
    @Bind(R.id.sp_jb)
    Spinner spJb;
    @Bind(R.id.sp_wtlb)
    Spinner spWtlb;
    @Bind(R.id.sp_wtxj)
    Spinner spWtxj;
    @Bind(R.id.iv_image_1)
    ImageView iv1;
    @Bind(R.id.delete1)
    ImageView delete1;
    @Bind(R.id.iv_image_2)
    ImageView iv2;
    @Bind(R.id.delete2)
    ImageView delete2;
    @Bind(R.id.iv_image_3)
    ImageView iv3;
    @Bind(R.id.delete3)
    ImageView delete3;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;

    private CameraBottomMenu menuWindow;
    private List<Bitmap> images = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private Bitmap img;
    private InformModel model;
    private PhotoId id;
    private Fankui fk;
    private String JbId;
    private String dtnum;
    private String xjnum;
    private List<JbList> jbList= new ArrayList<>();
    private List<WtflList> wtflList= new ArrayList<>();
    private List<WtflList> wtflList1= new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<String> names1 = new ArrayList<>();
    List<String> names2 = new ArrayList<>();
    private String typeId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                judge();
                urls.add(id.getId());
            }
            if (msg.what == 2) {
                if (fk.isCode()) {
                    Intent intent = new Intent(ReportProblemActivity.this, SuccessActivity.class);
                    intent.putExtra("yzm", fk.getYzm());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ReportProblemActivity.this, "提交失败，请从新操作", Toast.LENGTH_SHORT).show();
                }
            }
            if (msg.what == 3) {
                names.add("举报问题级别(选填)");
                for (int i =0;i<jbList.size();i++) {
                    String typename = jbList.get(i).getTypename();
                    names.add(typename);
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(ReportProblemActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spJb.setAdapter(arr_adapter1);
                spJb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for(int i=0;i<jbList.size();i++){
                            if(jbList.get(i).getTypename().equals(spJb.getSelectedItem().toString())){
                                JbId = jbList.get(i).getId();
                            }
                        }
                        if(JbId==null){
                            JbId = "";
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            }
            if (msg.what == 4 ) {
                names1.add("举报问题类型(选填)");
                for (int i =0;i<wtflList.size();i++) {
                    String typename = wtflList.get(i).getTypename();
                    names1.add(typename);
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(ReportProblemActivity.this, android.R.layout.simple_spinner_dropdown_item, names1);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spWtlb.setAdapter(arr_adapter1);
                spWtlb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for(int i=0;i<wtflList.size();i++){
                            if(wtflList.get(i).getTypename().equals(spWtlb.getSelectedItem().toString())){
                                typeId = wtflList.get(i).getNum();
                                dtnum = wtflList.get(i).getNum();
                            }
                        }
                        if(typeId==null){
                            typeId = "";
                            dtnum = "";
                            Message message = new Message();
                            message.what = 5;
                            handler.sendMessage(message);
                        }
                        model.wtflxjlist(typeId, new ICallBack() {
                            @Override
                            public void succeed(Object object) {
                                wtflList1 = (List<WtflList>) object;
                                Message message = new Message();
                                message.what = 5;
                                handler.sendMessage(message);
                            }
                            public void error(Object object) {}
                        });
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            }
            if (msg.what == 5 ) {
                names2.clear();
                names2.add("举报问题细节(选填)");
                for (int i =0;i<wtflList1.size();i++) {
                    String typename = wtflList1.get(i).getTypename();
                    names2.add(typename);
                }
                ArrayAdapter<String> arr_adapter1 = new ArrayAdapter<String>(ReportProblemActivity.this, android.R.layout.simple_spinner_dropdown_item, names2);
                //设置样式
                arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spWtxj.setAdapter(arr_adapter1);
                spWtxj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for(int i=0;i<wtflList1.size();i++){
                            if(wtflList1.get(i).getTypename().equals(spWtxj.getSelectedItem().toString())){
                                xjnum = wtflList1.get(i).getId();
                            }
                        }
                        if(xjnum==null){
                            xjnum = "";
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        ButterKnife.bind(this);
        setModel();
        setListener();

        iv2.setClickable(false);
        iv3.setClickable(false);

        delete1.setVisibility(View.GONE);
        delete2.setVisibility(View.GONE);
        delete3.setVisibility(View.GONE);
    }

    private void setModel() {
        model = new InformModel();
        model.jblist(new ICallBack() {
            @Override
            public void succeed(Object object) {
                jbList = (List<JbList>) object;
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
            public void error(Object object) {}
        });
        model.wtfllist(new ICallBack() {
            @Override
            public void succeed(Object object) {
                wtflList = (List<WtflList>) object;
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
            public void error(Object object) {}
        });
    }

    private void setListener() {
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 0) {
                    menuWindow = new CameraBottomMenu(ReportProblemActivity.this, clickListener);
                    menuWindow.show();
                    iv2.setClickable(true);
                    hintKbTwo();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportProblemActivity.this);
                    builder.setTitle("删除");
                    builder.setMessage("是否删除该图片");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove(0);
                        }
                    });
                    builder.show();
                }
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 1) {
                    menuWindow = new CameraBottomMenu(ReportProblemActivity.this, clickListener);
                    menuWindow.show();
                    iv3.setClickable(true);
                    hintKbTwo();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportProblemActivity.this);
                    builder.setTitle("删除");
                    builder.setMessage("是否删除该图片");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove(1);
                        }
                    });
                    builder.show();
                }
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() == 2) {
                    menuWindow = new CameraBottomMenu(ReportProblemActivity.this, clickListener);
                    menuWindow.show();
                    hintKbTwo();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportProblemActivity.this);
                    builder.setTitle("删除");
                    builder.setMessage("是否删除该图片");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove(2);
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @OnClick({R.id.iv_problem_back, R.id.btn_problem_tj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_problem_back:
                finish();
                break;
            case R.id.btn_problem_tj:
                String jbname = etProblemSfsmjbName.getText().toString();
                String jbsfz = etProblemSfsmjbSfz.getText().toString();
                String jbphone = etProblemSfsmjbPhone.getText().toString();
                String name = etProblemName.getText().toString();
                String unit = etProblemUnit.getText().toString();
                String jbbt = etProblemJbbt.getText().toString();
                String jbnr = etProblemJbwt.getText().toString();
                String str = urls.toString();
                if (jbname.equals("") || jbsfz.equals("") || jbphone.equals("") ||name.equals("") || unit.equals("") || jbbt.equals("") || jbnr.equals("")) {
                    Toast.makeText(ReportProblemActivity.this, "请补全数据，部分数据不能为空。", Toast.LENGTH_SHORT).show();
                    return;
                }
                str = str.replace(" ", "");
                final String s = str.substring(1, str.length() - 1);
                model.appadd(jbname, jbsfz, jbphone, name, unit, JbId, jbbt, jbnr, dtnum, xjnum, s, new ICallBack() {
                    @Override
                    public void succeed(Object object) {
                        fk = (Fankui) object;
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void error(Object object) {
                    }
                });
                break;
        }
    }

    /**
     * 此方法只是关闭软键盘
     */
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) ReportProblemActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && ReportProblemActivity.this.getCurrentFocus() != null) {
            if (ReportProblemActivity.this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(ReportProblemActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static int main() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            return r.nextInt();
        }
        return 0;
    }

    /**
     * 删除图片
     */
    private void remove(int i) {
        switch (i) {
            case 0:
                try {
                    images.remove(0);
                    urls.remove(0);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    images.remove(1);
                    urls.remove(1);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    images.remove(2);
                    urls.remove(2);
                    judge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 刷新图片
     */
    private void judge() {
        iv1.setImageDrawable(null);
        iv2.setImageDrawable(null);
        iv3.setImageDrawable(null);
        delete1.setVisibility(View.GONE);
        delete2.setVisibility(View.GONE);
        delete3.setVisibility(View.GONE);
        if (images.size() == 0) {
            iv1.setImageResource(R.drawable.iamge);
        }
        if (images.size() == 1) {
            iv1.setImageBitmap(images.get(0));
            delete1.setVisibility(View.VISIBLE);
            iv2.setImageResource(R.drawable.iamge);
        }
        if (images.size() == 2) {
            iv1.setImageBitmap(images.get(0));
            iv2.setImageBitmap(images.get(1));
            delete1.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            iv3.setImageResource(R.drawable.iamge);
        }
        if (images.size() == 3) {
            iv1.setImageBitmap(images.get(0));
            iv2.setImageBitmap(images.get(1));
            iv3.setImageBitmap(images.get(2));
            delete1.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            delete3.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    autoObtainCameraPermission();
                    break;
                case R.id.btn2:
                    autoObtainStoragePermission();
                    break;
            }
        }
    };

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(ReportProblemActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ReportProblemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ReportProblemActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(ReportProblemActivity.this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(ReportProblemActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(ReportProblemActivity.this, "com.zz.fileprovider.xfjb", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(ReportProblemActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(ReportProblemActivity.this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(ReportProblemActivity.this, "com.zz.fileprovider.xfjb", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(ReportProblemActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(ReportProblemActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(ReportProblemActivity.this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(ReportProblemActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(ReportProblemActivity.this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo" + main() + ".jpg");
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(ReportProblemActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo" + main() + ".jpg");
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(ReportProblemActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(ReportProblemActivity.this, "com.zz.fileprovider.xfjb", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(ReportProblemActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);

                    } else {
                        ToastUtils.showShort(ReportProblemActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    String u = fileCropUri.toString();

                    img = PhotoUtils.getBitmapFromUri(cropImageUri, ReportProblemActivity.this);
                    if (images.size() < 4) {
                        images.add(img);
                    }
                    model.addFeedback(u, new ICallBack() {
                        @Override
                        public void succeed(Object object) {
                            id = (PhotoId) object;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                        public void error(Object object) {}
                    });
                    break;
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(ReportProblemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReportProblemActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(ReportProblemActivity.this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
