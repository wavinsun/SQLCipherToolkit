package cn.mutils.app.sqlcipher;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * Created by wenhua.ywh on 2017/8/10.
 */
public class SQLCipherUtil {

    private static boolean sHasLoadLibs = false;

    private static synchronized void ensureLoadLibs(Context context) {
        if (sHasLoadLibs) {
            return;
        }
        SQLiteDatabase.loadLibs(context);
        sHasLoadLibs = true;
    }

    public static void encrypt(Context context, String plainTextDBName,
                               String cipherTextDBName, String password) {
        encrypt(context, plainTextDBName, cipherTextDBName, password, true);
    }

    public static void encrypt(Context context, String plainTextDBName,
                               String cipherTextDBName, String password, boolean deletePlainTextDB) {
        File plainTextFile = context.getDatabasePath(plainTextDBName);
        if (!plainTextFile.exists()) {
            return;
        }
        boolean isSameDBName = plainTextDBName.equals(cipherTextDBName);
        File cipherTextFile = null;
        if (isSameDBName) {
            cipherTextFile = context.getDatabasePath(cipherTextDBName + ".tmp");
        } else {
            cipherTextFile = context.getDatabasePath(cipherTextDBName);
        }
        if (cipherTextFile.exists()) {
            cipherTextFile.delete();
        }
        ensureLoadLibs(context);
        SQLiteDatabase cipherTextDB = SQLiteDatabase.openOrCreateDatabase(
                cipherTextFile.getAbsolutePath(), password, null);
        cipherTextDB.close();
        SQLiteDatabase plainTextDB = SQLiteDatabase.openDatabase(plainTextFile.getAbsolutePath(),
                "", null, SQLiteDatabase.OPEN_READWRITE);
        plainTextDB.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                cipherTextFile.getAbsolutePath(), password));
        plainTextDB.rawExecSQL("SELECT sqlcipher_export('encrypted')");
        plainTextDB.rawExecSQL("DETACH DATABASE encrypted;");
        int version = plainTextDB.getVersion();
        plainTextDB.close();
        cipherTextDB = SQLiteDatabase.openDatabase(cipherTextFile.getAbsolutePath(),
                password, null, SQLiteDatabase.OPEN_READWRITE);
        cipherTextDB.setVersion(version);
        cipherTextDB.close();
        if (isSameDBName) {
            plainTextFile.delete();
            cipherTextFile.renameTo(plainTextFile);
            return;
        }
        if (deletePlainTextDB) {
            plainTextFile.delete();
        }
    }
}
