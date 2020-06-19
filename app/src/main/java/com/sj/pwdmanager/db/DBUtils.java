package com.sj.pwdmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sj.pwdmanager.AppConstant;
import com.sj.pwdmanager.utils.AESUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJ on 2019/1/22.
 * 使用时需要在Application中初始化
 * 查询操作时 需要及时关闭游标与数据库
 */
public class DBUtils {
    private static DBHelper dbHelper;

    public static void init(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
    }

    private static SQLiteDatabase getReadableDatabase() {
        if (dbHelper != null) {
            return dbHelper.getReadableDatabase();
        } else {
            throw new SQLException("DBUtils未初始化！");
        }
    }

    /**
     * 执行增删改SQL语句
     *
     * @param sql
     */
    public static void execSQL(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }

    /**
     * 执行增删改SQL语句
     *
     * @param sql      带占位符的语句
     * @param bindArgs 实际value
     */
    public static void execSQL(String sql, Object[] bindArgs) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(sql, bindArgs);
        db.close();
    }

    /**
     * @param table          表名
     * @param nullColumnHack 指定为null的列
     * @param values         添加的值集合
     */
    public static long insert(String table, String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = getReadableDatabase();
        long result = db.insert(table, nullColumnHack, values);
        db.close();
        return result;
    }

    /**
     * 删除
     *
     * @param table       表名
     * @param whereClause 删除条件
     * @param whereArgs   条件对应值
     */
    public static void delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(table, whereClause, whereArgs);
        db.close();
    }

    /**
     * 修改
     *
     * @param table       表名
     * @param values      需要修改的值
     * @param whereClause 条件
     * @param whereArgs   条件对应值
     */
    public static void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getReadableDatabase();
        db.update(table, values, whereClause, whereArgs);
        db.close();
    }

    /**
     * @param table         String：表名
     * @param columns       String[]:要查询的列名
     * @param selection     String：查询条件
     * @param selectionArgs String[]：查询条件的参数
     * @param groupBy       String:对查询的结果进行分组
     * @param having        String：对分组的结果进行限制
     * @param orderBy       String：对查询的结果进行排序
     * @return 返回游标，使用后需要及时关闭游标
     */
    public static Cursor query(String table, String[] columns, String selection,
                               String[] selectionArgs, String groupBy, String having,
                               String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    /**
     * @param sql           带占位符的查询语句
     * @param selectionArgs 查询条件实际值
     * @return 返回游标，使用后需要及时关闭游标
     */
    public static Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, selectionArgs);
    }

    public static void colseDB() {
        dbHelper.close();
    }

    public static List<String> getDataByType(int dataType) {
        ArrayList<String> list = new ArrayList<>();
        String columns = "account";
        switch (dataType) {
            case AppConstant.TYPE_ACCOUNT:
                columns = "account";
                break;
            case AppConstant.TYPE_BELONG:
                columns = "belong";
                break;
            case AppConstant.TYPE_TYPE:
                columns = "type";
                break;
            case AppConstant.TYPE_ALL:
                return getDataByTable(DBTables.TABLE_ACCOUNT);
        }
        Cursor cursor = getReadableDatabase().query(DBTables.TABLE_ACCOUNT, new String[]{columns}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(columns)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public static List<String> getDataByTable(String tableName) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            StringBuilder builder;
            do {
                builder = new StringBuilder();
                builder.append("类别：").append(cursor.getString(cursor.getColumnIndex("type"))).append("-");
                builder.append("归属：").append(cursor.getString(cursor.getColumnIndex("belong"))).append("\n");
                builder.append("账号：").append(cursor.getString(cursor.getColumnIndex("account"))).append("\n");
                builder.append("密码：");
                 String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                try {
                    builder.append(AESUtils.decrypt(pwd));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                builder.append("\n").append("密文：").append(pwd).append("\n");
                list.add(builder.toString());
                builder = null;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public static List<String> getTypeDetailData(String text) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(DBTables.TABLE_ACCOUNT, new String[]{"type"}, "type = ?", new String[]{text}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("type")));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
