package com.project.nda.adapter;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ndact on 22/08/2016.
 */
public class GetDataFromAssetsAdapter {


    public  static final String DATABASE_NAME = "dbThuChi.sqlite" ;
    private static final String DB_PATH_SUFFIX = "/databases/";

    public void coppyDatabaseFromAssetsToSystem(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDatabaseFromAssets(context);

            } catch (Exception e) {
                Log.e("Loi", e.toString());
            }
        }
    }

    public void CopyDatabaseFromAssets(Context context) {
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePathSave(context); //Tra ve duong dan thu muc chua file
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }
            // Đọc file dung mamg byte dua vao OutPutStream
            OutputStream os = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            Log.e("Loi", e.toString());
        }

    }

    public String getDatabasePathSave(Context context) {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}
