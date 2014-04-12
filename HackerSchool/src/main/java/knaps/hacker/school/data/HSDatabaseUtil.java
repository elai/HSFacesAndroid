package knaps.hacker.school.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

import knaps.hacker.school.models.Batch;
import knaps.hacker.school.models.Student;
import knaps.hacker.school.utils.StringUtil;

/**
 * Created by lisaneigut on 14 Sep 2013.
 */
public class HSDatabaseUtil {

    /**
     * Must be run on background thread
     */
    protected static void writeBatchesToDatabase(HSDatabaseHelper dbHelper, final List<Batch> batches) {
        long startTime = System.currentTimeMillis();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        final SQLiteStatement stmt = db.compileStatement(HSData.Batch.SQL_UPSERT_ALL);
        final SimpleDateFormat formatter = StringUtil.getSimpleDateFormatter();

        for (Batch batch : batches) {
            bindBatch(stmt, batch, formatter, startTime);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        Log.d("DB _ timing",
                "Total time for writing to db: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void writeBatchToDatabase(final HSDatabaseHelper dbHelper, final Batch batch) {
        long startTime = System.currentTimeMillis();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        final SQLiteStatement stmt = db.compileStatement(HSData.Batch.SQL_UPSERT_ALL);
        final SimpleDateFormat formatter = StringUtil.getSimpleDateFormatter();

        bindBatch(stmt, batch, formatter, startTime);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    private static void bindBatch(SQLiteStatement stmt, final Batch batch, SimpleDateFormat formatter, final long startTime) {
        stmt.bindLong(1, batch.id);
        stmt.bindString(2, batch.name);
        stmt.bindString(3, formatter.format(batch.startDate));
        stmt.bindString(4, formatter.format(batch.endDate));
        stmt.bindLong(5, startTime);
        stmt.execute();
        stmt.clearBindings();

    }

    protected static void writeStudentsToDatabase(final HSDatabaseHelper dbHelper, final List<Student> students) {
        long startTime = System.currentTimeMillis();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        final SQLiteStatement stmt = db.compileStatement(HSData.HSer.SQL_UPSERT_ALL);

        for (Student student : students) {
            bindStudent(stmt, student, startTime);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.d("DB _ timing",
                "Total time for writing to db: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    protected static void writeStudentToDatabase(final HSDatabaseHelper dbHelper, final Student student) {
        long startTime = System.currentTimeMillis();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        final SQLiteStatement stmt = db.compileStatement(HSData.HSer.SQL_UPSERT_ALL);

        bindStudent(stmt, student, startTime);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.d("DB _ timing",
                "Total time for writing to db: " + (System.currentTimeMillis() - startTime) + "ms");

    }

    private static void bindStudent(final SQLiteStatement stmt, final Student student, final long startTime) {
        stmt.bindLong(1, student.id);
        stmt.bindString(2, student.firstName);
        stmt.bindString(3, student.lastName);
        stmt.bindString(4, student.image);
        stmt.bindString(5, student.mJob);
        stmt.bindString(6, student.mJobUrl);
        stmt.bindString(7, student.mSkills);
        stmt.bindString(8, student.email);
        stmt.bindString(9, student.github);
        stmt.bindString(10, student.twitter);
        stmt.bindLong(11, student.batch.id);
        stmt.bindLong(12, startTime);
        stmt.execute();
        stmt.clearBindings();
    }

    protected static long[] getBatchIds(HSDatabaseHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(HSData.Batch.SQL_SINGLE_LINE_RESULT, null);

        String string = "";
        if (c.moveToFirst()) {
            string = c.getString(1);
        }

        String[] idStrings = string.split(":");
        long[] ids = new long[idStrings.length];
        for (int i = 0; i < idStrings.length; i++) {
            try {
                ids[i] = Long.parseLong(idStrings[i].trim());
            }
            catch (NumberFormatException e) {
                // problem parsing that string
            }
        }

        return ids;
    }

}