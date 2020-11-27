package com.example.sy2;



import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.sy2.R.drawable.fab_bg_mini;


public class Add extends AppCompatActivity implements OnClickListener{
    String Title,Content,simpleDate,photo;
    Button ButtonAddCancel,ButtonAddSave;
    EditText EditTextAddTitle,EditTextAddContent,EditTextAddAuthor;
    String Author;

    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    Button deletePicture;


    public static String imagePath =null;//定义一个全局变量，把图片路径变为string保存到数据库中
    public static final int CHOOSE_PHOTO = 2;

    public void qu(Bitmap bitmap){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
//        EditTextContent = (EditText)findViewById(R.id.EditTextEditContent);
//        EditTextTitle = (EditText)findViewById(R.id.EditTextEditTitle) ;
//        ButtonCancel = (Button)findViewById(R.id.ButtonCancel);
//        ButtonSave = (Button)findViewById(R.id.ButtonSave);
//        ButtonDelete = (Button)findViewById(R.id.ButtonDelete);
//        EditTextEditAuthor = findViewById(R.id.EditTextEditAuthor);
//
//        showImage = findViewById(R.id.showImage);
//
//
//        ButtonCancel.setOnClickListener(this);
//        ButtonSave.setOnClickListener(this);
//        ButtonDelete.setOnClickListener(this);


            @Override
            public void onClick(View view) {
                picture.setImageAlpha(0);
            }
        });

        ButtonAddCancel = (Button)findViewById(R.id.ButtonAddCancel);
        ButtonAddSave = (Button)findViewById(R.id.ButtonAddSave);
        EditTextAddContent = findViewById(R.id.EditTextAddContent);
        EditTextAddTitle = findViewById(R.id.EditTextAddTitle);
        EditTextAddAuthor = findViewById(R.id.EditTextAddAuthor);

        ButtonAddCancel.setOnClickListener(this);
        ButtonAddSave.setOnClickListener(this);

    @Override
    public void onRequestPermissionsResult(int requstCode,String[] permissions,int[] grantResults) {
        switch (requstCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    public void onClick(View v){
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this,"Note.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.ButtonAddSave:
                Date date = new Date();
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //配置时间格式
                simpleDate = simpleDateFormat.format(date);
                ContentValues values = new ContentValues();
                Title = String.valueOf(EditTextAddTitle.getText());         //获取需要储存的值
                Content = String.valueOf(EditTextAddContent.getText());

                if(Title.length()==0){               //标题为空给出提示
                    Toast.makeText(this, "请输入一个标题", Toast.LENGTH_LONG).show();
                }else {
                    values.put("title", Title);
                    values.put("content", Content);
                    values.put("date", simpleDate);
                    if(path != null){
                    values.put("picture", path);}
                    //把图片存到数据库中
                    db.insert("Note", null, values);               //将值传入数据库中
                    Add.this.setResult(RESULT_OK, getIntent());
                    Add.this.finish();
        }


                Author = String.valueOf(EditTextAddAuthor.getText());
                if (Author.length() != 0){
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("author",Author);      //使用sharedperferences设置默认作者
                    editor.apply();
                }
                break;

            case R.id.ButtonAddCancel:
                Add.this.setResult(RESULT_OK,getIntent());
                Add.this.finish();

                break;
        }


    }

}