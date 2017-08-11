package cn.mutils.app.sqlcipher.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import cn.mutils.app.sqlcipher.SQLCipherUtil;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cn.mutils.app.sqlcipher.app", appContext.getPackageName());
    }

    @Test
    public void readPlainTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "plain.db");
        Database db = helper.getWritableDb();
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        Query<User> query = dao.queryBuilder().build();
        List<User> users = query.list();
        long end = System.currentTimeMillis();
        Log.d("readPlainTest", (end - start) + "ms");
    }

    @Test
    public void writePlainTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "plain.db");
        Database db = helper.getWritableDb();
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        for (int i = 0; i < 500; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            u.setName("name" + i);
            dao.insert(u);
        }
        long end = System.currentTimeMillis();
        Log.d("writePlainTest", (end - start) + "ms");
    }

    @Test
    public void deleteAllPlainTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "plain.db");
        Database db = helper.getWritableDb();
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        dao.deleteAll();
        long end = System.currentTimeMillis();
        Log.d("deleteAllPlainTest", (end - start) + "ms");
    }

    @Test
    public void readCipherTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cipher.db");
        Database db = helper.getEncryptedWritableDb("password");
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        Query<User> query = dao.queryBuilder().build();
        List<User> users = query.list();
        long end = System.currentTimeMillis();
        Log.d("readCipherTest", (end - start) + "ms");
    }

    @Test
    public void writeCipherTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cipher.db");
        Database db = helper.getEncryptedWritableDb("password");
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        for (int i = 0; i < 500; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            u.setName("name" + i);
            dao.insert(u);
        }
        long end = System.currentTimeMillis();
        Log.d("writeCipherTest", (end - start) + "ms");
    }

    @Test
    public void deleteAllCipherTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cipher.db");
        Database db = helper.getEncryptedWritableDb("password");
        DaoSession session = new DaoMaster(db).newSession();
        UserDao dao = session.getUserDao();
        dao.deleteAll();
        long end = System.currentTimeMillis();
        Log.d("deleteAllCipherTest", (end - start) + "ms");
    }

    @Test
    public void updatePlainToCipherTest() {
        long start = System.currentTimeMillis();
        Context context = InstrumentationRegistry.getTargetContext();
        SQLCipherUtil.encrypt(context, "plain.db", "cipher.db", "password", false);
        long end = System.currentTimeMillis();
        Log.d("updatePlainToCipherTest", (end - start) + "ms");
    }
}
