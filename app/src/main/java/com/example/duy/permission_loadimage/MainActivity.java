package com.example.duy.permission_loadimage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.Manifest;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_CONTACT = 2;
    List<String> list;
    List<Data> data;
    RecyclerView recyclerView;
    private Adapter_contact contact;
    Adpter_image adpterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);


        list = new ArrayList<>();
        data = new ArrayList<>();
        adpterImage = new Adpter_image(list);
        contact = new Adapter_contact(data);
        findViewById(R.id.button_show_image).setOnClickListener(this);
        findViewById(R.id.button_show_contact).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show_image :
                setRequestlist();
                break;
            case R.id.button_show_contact :
                setRequestcontact();
                break;
            default: break;

            }
    }

    public List<String> getList() {
        //truy cập vào image trong media của điện thoại
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
      String projection[] = {MediaStore.Images.Media.DATA};
        // lấy toàn bộ hình ảnh prọection
      Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        List<String> path = new ArrayList<>();
        //duyệt từng dữ liệu có trong image có trong điện thoại
      /*  if (cursor != null && cursor.moveToFirst()) {
            do {
                path.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }   */
      //không cần duyệt qua bộ nhớ của điện thoiại.tải trực tiếp trên mạng sử dụng glide bên imga adpater
        path.add("http://anhnendep.net/wp-content/uploads/2016/02/vit-boi-roi-Psyduck.jpg");
        return path;
    }
    public void show_image(){
        list.clear();
        list.addAll(getList());
        adpterImage.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adpterImage);
    }

    public List<Data> getContact() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        List<Data> datas = new ArrayList<>();
        /// lấy tên và cột của số điện thoại
        String colName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String colNumber = ContactsContract.CommonDataKinds.Phone.NUMBER;

        String projection[] = {colName,colNumber};

        //LẤY TOÀN BỘ THÔNG TIN LƯU VÀO LIST
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        if (cursor !=null && cursor.moveToFirst()){
            do{
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                datas.add(new Data(name,phone));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    return datas;
    }
    public void showcontact(){
        data.clear();
        data.addAll(getContact());
        contact.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contact);
    }
    public void setRequestcontact(){

        int permissioncheck = ContextCompat.checkSelfPermission(this,"Manifest.permission"+".READ_");
        if(permissioncheck == PackageManager.PERMISSION_GRANTED)
        {
            showcontact();
        }
        else if(permissioncheck == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ket noi Contact Permission").setMessage("chung toi muon ket noi den du lieu").setPositiveButton(R.string.yes_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);

                    }
                }).setNegativeButton(R.string.no_option,null);
                builder.create().show();
            } else
                {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACT);
            }
        }
    }
    public void setRequestlist(){
        //yêu cầu truy cập vào bộ nhớ
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,"Manifest"+".permission.READ_EXTERNAL_STORAGE");
        //nếu có quyền thì thực  show_image
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                show_image();
            } else if(permissionCheck == PackageManager.PERMISSION_DENIED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Yeu cau perrmisson image").setMessage("can ket noi den thiet bi nho image").setPositiveButton(R.string.yes_option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_IMAGE);
                        }
                    }).setNegativeButton(R.string.no_option,null);
                    builder.create().show();
                }else   // nếu không được thì hiển thị thông báo
                    {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_IMAGE);

                }
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_IMAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    show_image();
                }
                else  {
                    Toast.makeText(this,R.string.denied,Toast.LENGTH_SHORT).show();
                }
                break;
            case  REQUEST_CONTACT :
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    showcontact();
                }
                else  {
                    Toast.makeText(this,R.string.denied,Toast.LENGTH_SHORT).show();
                }
                break;
            default: break;
        }

    }
}
