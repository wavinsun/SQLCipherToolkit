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
import net.sqlcipher.database.SQLiteDatabase;

import java.util.Locale;

public class EncryptedDatabase implements Database {
    private final SQLiteDatabase delegate;

    public EncryptedDatabase(SQLiteDatabase delegate) {
        this.delegate = delegate;
    }

    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return delegate.rawQuery(sql, selectionArgs);
    }

    @Override
    public void execSQL(String sql) throws SQLException {
        delegate.execSQL(sql);
    }

    @Override
    public void beginTransaction() {
        delegate.beginTransaction();
    }

    @Override
    public void endTransaction() {
        delegate.endTransaction();
    }

    @Override
    public boolean inTransaction() {
        return delegate.inTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        delegate.setTransactionSuccessful();
    }

    @Override
    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        delegate.execSQL(sql, bindArgs);
    }

    @Override
    public DatabaseStatement compileStatement(String sql) {
        return new EncryptedDatabaseStatement(delegate.compileStatement(sql));
    }

    @Override
    public boolean isDbLockedByCurrentThread() {
        return delegate.isDbLockedByCurrentThread();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public Object getRawDatabase() {
        return delegate;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return delegate;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return delegate.update(table, values, whereClause, whereArgs);
    }

    public int updateWithOnConflict(String table, ContentValues values,
                                    String whereClause, String[] whereArgs, int conflictAlgorithm) {
        return delegate.updateWithOnConflict(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    public boolean isReadOnly() {
        return delegate.isReadOnly();
    }

    public void yieldIfContendedSafely() {
        delegate.yieldIfContendedSafely();
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return delegate.yieldIfContendedSafely(sleepAfterYieldDelay);
    }

    public long getMaximumSize() {
        return delegate.getMaximumSize();
    }

    public long setMaximumSize(long numBytes) {
        return delegate.setMaximumSize(numBytes);
    }

    public long getPageSize() {
        return delegate.getPageSize();
    }

    public void setPageSize(long numBytes) {
        delegate.setPageSize(numBytes);
    }

    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        return delegate.query(distinct, table, columns,
                selection, selectionArgs, groupBy,
                having, orderBy, limit);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return delegate.query(table, columns, selection,
                selectionArgs, groupBy, having,
                orderBy);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        return delegate.insert(table, nullColumnHack, values);
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) {
        return delegate.insertOrThrow(table, nullColumnHack, values);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        return delegate.replace(table, nullColumnHack, initialValues);
    }

    public long replaceOrThrow(String table, String nullColumnHack,
                               ContentValues initialValues) throws SQLException {
        return delegate.replaceOrThrow(table, nullColumnHack,
                initialValues);
    }

    public long insertWithOnConflict(String table, String nullColumnHack,
                                     ContentValues initialValues, int conflictAlgorithm) {
        return delegate.insertWithOnConflict(table, nullColumnHack,
                initialValues, conflictAlgorithm);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        return delegate.delete(table, whereClause, whereArgs);
    }

    public boolean isOpen() {
        return delegate.isOpen();
    }

    public boolean needUpgrade(int newVersion) {
        return delegate.needUpgrade(newVersion);
    }

    public String getPath() {
        return delegate.getPath();
    }

    public void setLocale(Locale locale) {
        delegate.setLocale(locale);
    }

    public void setMaxSqlCacheSize(int cacheSize) {
        delegate.setMaxSqlCacheSize(cacheSize);
    }
}
