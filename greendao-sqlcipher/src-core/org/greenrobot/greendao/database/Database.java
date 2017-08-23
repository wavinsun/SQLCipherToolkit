/*
 * Copyright (C) 2011-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.greenrobot.greendao.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import java.util.Locale;

/**
 * Database abstraction used internally by greenDAO.
 */
public interface Database {
    Cursor rawQuery(String sql, String[] selectionArgs);

    void execSQL(String sql) throws SQLException;

    void beginTransaction();

    void endTransaction();

    boolean inTransaction();

    void setTransactionSuccessful();

    void execSQL(String sql, Object[] bindArgs) throws SQLException;

    DatabaseStatement compileStatement(String sql);

    boolean isDbLockedByCurrentThread();

    void close();

    Object getRawDatabase();

    int update(String table, ContentValues values, String whereClause, String[] whereArgs);

    int updateWithOnConflict(String table, ContentValues values,
                             String whereClause, String[] whereArgs, int conflictAlgorithm);

    boolean isReadOnly();

    void yieldIfContendedSafely();

    boolean yieldIfContendedSafely(long sleepAfterYieldDelay);

    long getMaximumSize();

    long setMaximumSize(long numBytes);

    long getPageSize();

    void setPageSize(long numBytes);

    Cursor query(boolean distinct, String table, String[] columns,
                 String selection, String[] selectionArgs, String groupBy,
                 String having, String orderBy, String limit);

    Cursor query(String table, String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy);

    long insert(String table, String nullColumnHack, ContentValues values);

    long insertOrThrow(String table, String nullColumnHack, ContentValues values);

    long replace(String table, String nullColumnHack, ContentValues initialValues);

    long replaceOrThrow(String table, String nullColumnHack,
                        ContentValues initialValues) throws SQLException;

    long insertWithOnConflict(String table, String nullColumnHack,
                              ContentValues initialValues, int conflictAlgorithm);

    int delete(String table, String whereClause, String[] whereArgs);

    boolean isOpen();

    boolean needUpgrade(int newVersion);

    String getPath();

    void setLocale(Locale locale);

    void setMaxSqlCacheSize(int cacheSize);

}
