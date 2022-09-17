package com.example.colearn.my;


import static com.example.colearn.MainActivity.baseUrl;
import static com.kongzue.dialogx.interfaces.BaseDialog.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.colearn.CoLearnRequestInterface;
import com.example.colearn.R;
import com.example.colearn.pojo.User;
import com.example.colearn.databinding.ActivityPersonalInformationBinding;
import com.example.colearn.utils.GlideEngine;
import com.example.colearn.utils.OkHttpUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.utils.MediaUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalInformation extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "PersonalInformation";

    private String[] genderList = {"男", "女"};
    private int constellationSelectOption = 0;
    private ActivityPersonalInformationBinding binding;
    private String imageUrl;

    //权限请求
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public static void changeIngo(String changeResult, String path) {
        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpUtil.getOkHttpClient())
                //设置网络请求BaseUrl地址
                .baseUrl(baseUrl)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建网络请求接口对象实例
        CoLearnRequestInterface request = retrofit.create(CoLearnRequestInterface.class);
        //对发送请求进行封装
        Call<ResponseBody> call = request.changeInfo(changeResult, path);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //步骤8：请求处理,输出结果
                ResponseBody body = response.body();
                if (body == null) return;
                try {
                    Log.d(TAG, "返回的数据：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.code() == 200){
                    User.getUser().setGender(changeResult);
                }else {
                    
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "post回调失败：" + throwable.getMessage() + "," + throwable.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInformationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarDarkFont(true, 0f)
                .statusBarColor(R.color.white)
                .init();
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
        if (User.getUser() != null){
            User user = User.getUser();
            binding.nickname.setText(user.getNickname());
            binding.gender.setText(user.getGender());
            binding.id.setText(user.getId());
        }
        binding.changeAvatar.setOnClickListener(this::onClick);
        binding.changeGender.setOnClickListener(this::onClick);
        binding.changeNickname.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_avatar:
                PictureSelector.create(this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine()) // 这里就是设置图片加载引擎
                        .setMaxSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.change_nickname:
                Intent intent = new Intent();
                intent.setClass(PersonalInformation.this, ChangeNickname.class);
                startActivity(intent);
                break;
            case R.id.change_gender:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                        binding.gender.setText(genderList[options1]);
                        constellationSelectOption = options1;
                        changeIngo(genderList[options1], "gender");
                        return false;
                    }
                })
                        .setTitleText("")
                        .setSelectOptions(constellationSelectOption)
                        .build();
                pvOptions.setPicker(genderList);
                pvOptions.show();
                break;
        }
    }

    /**
     * 图片选择器回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST || requestCode == PictureConfig.REQUEST_CAMERA) {
                ArrayList<LocalMedia> result = PictureSelector.obtainSelectorList(data);
                analyticalSelectResults(result);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.i("TAG", "onActivityResult PictureSelector Cancel");
        }
    }

    /**
     * 处理选择结果
     *
     * @param result
     */
    private void analyticalSelectResults(ArrayList<LocalMedia> result) {
        for (LocalMedia media : result) {
            if (media.getWidth() == 0 || media.getHeight() == 0) {
                if (PictureMimeType.isHasImage(media.getMimeType())) {
                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(this, media.getPath());
                    media.setWidth(imageExtraInfo.getWidth());
                    media.setHeight(imageExtraInfo.getHeight());
                } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                    MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(this, media.getPath());
                    media.setWidth(videoExtraInfo.getWidth());
                    media.setHeight(videoExtraInfo.getHeight());
                }
            }
//            Log.i(TAG, "文件名: " + media.getFileName());
//            Log.i(TAG, "是否压缩:" + media.isCompressed());
//            Log.i(TAG, "压缩:" + media.getCompressPath());
//            Log.i(TAG, "初始路径:" + media.getPath());
//            Log.i(TAG, "绝对路径:" + media.getRealPath());
//            Log.i(TAG, "是否裁剪:" + media.isCut());
//            Log.i(TAG, "裁剪:" + media.getCutPath());
//            Log.i(TAG, "是否开启原图:" + media.isOriginal());
//            Log.i(TAG, "原图路径:" + media.getOriginalPath());
//            Log.i(TAG, "沙盒路径:" + media.getSandboxPath());
//            Log.i(TAG, "水印路径:" + media.getWatermarkPath());
//            Log.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
//            Log.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
//            Log.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
//            Log.i(TAG, "文件大小: " + media.getSize());
            imageUrl = media.getRealPath();
            //mImageList.add(); // 接收已选图片地址，用于接口上传图片
            Glide.with(this).load(imageUrl).apply(requestOptions).into(binding.avatar);

        }
    }
}