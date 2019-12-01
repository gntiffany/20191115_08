package tw.org.iii.a20191115_08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView content;
    private File sdroot, approot;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 9487);
        } else {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9487) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                finish();
            }


        }
    }

    private void init() {
        content = findViewById(R.id.content);
        sp = getSharedPreferences("tiffany", MODE_PRIVATE);
        editor = sp.edit();

        sdroot = Environment.getExternalStorageDirectory();
        approot = new File(sdroot, "Android/data/" + getPackageName());
        if (!approot.exists()) {
            approot.mkdirs();
        }
    myDBHelper = new MyDBHelper(this,"mydb",null,1);
        db = myDBHelper.getReadableDatabase();

    }

    public void test1(View view) {
        editor.putString("username", "tiffany");
        editor.putBoolean("sound", false);
        editor.putInt("stage", 4);
        editor.commit();
        Toast.makeText(this, "save ok", Toast.LENGTH_SHORT).show();
    }

    public void test2(View view) {
        boolean isSound = sp.getBoolean("sound", true);
        String username = sp.getString("username", "nobody");
        int stage = sp.getInt("stage", 1);
    }

    public void test3(View view) {
        try {
            FileOutputStream fout = openFileOutput("tiffany.txt", MODE_APPEND);
            fout.write("Hello World\n".getBytes());

            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void test4(View view) {
        try (FileInputStream fin= openFileInput("tiffany.txt")) {
            StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024];
            int len;
            while ((len = fin.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            content.setText(sb.toString());
        } catch (Exception e) {
        }
    }

    public void test5(View view) {
        File file1 = new File(sdroot, "tiffany.ok");
        try {
            FileOutputStream fout=

        new FileOutputStream(file1);
        fout.write("Hello, Tiffany".getBytes());
        fout.flush();
        fout.close();
        Toast.makeText(this, "save ok1",
                Toast.LENGTH_SHORT).show();
    }catch(Exception e)

    {
        Log.v("tiffany", e.toString());
    }
}
    public void test6(View view){
    File file1 = new File(approot,"tiffany.ok");
    try {
        FileOutputStream fout =new FileOutputStream(file1);
        fout.write("Hello tiffany".getBytes());
        fout.flush();
        fout.close();
        Toast.makeText(this, "Save OK1",
                Toast.LENGTH_SHORT).show();
    }catch (Exception e){
        Log.v("tiffany", e.toString());
    }
    }


    public void test7(View view) {
        Cursor c = db.query("user",null,null,null,null,null,null);
        while (c.moveToNext()) {
            String id = c.getString(0);
            String username = c.getString(1);
            String tel = c.getString(2);
            String birthday = c.getString(3);
        }
        }

    public void test8(View view) {
        ContentValues values = new ContentValues();
        values.put("username","tiffany");
        values.put("tel","1234567");
        values.put("birthday","2000-01-02");
        db.insert("user",null,values);

        test7(null);
    }

    public void test9(View view) {
        db.delete("user","id = ? and username = ? ", new String[]{"3","tiffany"});
        test7(null);
    }


    public void test10(View view) {
        ContentValues values = new ContentValues();
        values.put("username","peter");
        values.put("tel","0912-123456");
        db.update("user",values, "id = ?", new String[]{"4"});

        test7(null);






    }
}