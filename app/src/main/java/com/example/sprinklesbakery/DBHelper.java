package com.example.sprinklesbakery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bakery.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_ORDERS = "orders";

    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CUPCAKES = "cupcakes";


    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";

    public static final String COLUMN_CUSTOMER_EMAIL = "customer_email";

    public static final String COLUMN_CUSTOMER_PASSWORD = "customer_password";

    public static final String COLUMN_CUSTOMER_CONTACT = "customer_contact";
    public static final String COLUMN_CUSTOMER_ADDRESS = "customer_address";


    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_DATE = "order_date";


    public static final String COLUMN_ORDER_ITEM_ID = "order_item_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";


    public static final String COLUMN_CUPCAKE_ID = "cupcake_id";
    public static final String COLUMN_CUPCAKE_NAME = "cupcake_name";
    public static final String COLUMN_CUPCAKE_PRICE = "cupcake_price";
    public static final String COLUMN_CUPCAKE_DESCRIPTION = "cupcake_description";

    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_CATEGORY_NAME = "category_name";
    public static final String COLUMN_CATEGORY_DESCRIPTION = "category_description";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
            COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CUSTOMER_NAME + " TEXT, " +
            COLUMN_CUSTOMER_EMAIL + " TEXT, " +
            COLUMN_CUSTOMER_PASSWORD + " TEXT, " +
            COLUMN_CUSTOMER_CONTACT + " TEXT," +
            COLUMN_CUSTOMER_ADDRESS + " TEXT);";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ORDER_DATE + " TEXT, " +
            COLUMN_CUSTOMER_ID + " INTEGER," +
            "FOREIGN KEY(" + COLUMN_CUSTOMER_ID + ") REFERENCES " + TABLE_CUSTOMERS + "(" + COLUMN_CUSTOMER_ID + "));";


    private static final String CREATE_TABLE_ORDERS_ITEMS = "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
            COLUMN_ORDER_ITEM_ID + "INTEGER," +
            COLUMN_ORDER_ID + " INTEGER, " +
            COLUMN_CUPCAKE_ID + " INTEGER, " +
            COLUMN_QUANTITY + " INTEGER," +
            COLUMN_TOTAL_AMOUNT + " REAL," +
            "FOREIGN KEY(" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "), " +
            "FOREIGN KEY(" + COLUMN_CUPCAKE_ID + ") REFERENCES " + TABLE_CUPCAKES + "(" + COLUMN_CUPCAKE_ID + "));";


    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "(" +
            COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CATEGORY_NAME + " TEXT, " +
            COLUMN_CATEGORY_DESCRIPTION + " TEXT);";

    private static final String CREATE_TABLE_CUPCAKES = "CREATE TABLE " + TABLE_CUPCAKES + "(" +
            COLUMN_CUPCAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CUPCAKE_NAME + " TEXT, " +
            COLUMN_CUPCAKE_DESCRIPTION + " TEXT, " +
            COLUMN_CUPCAKE_PRICE + " REAL, " +
            COLUMN_CATEGORY_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "));";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_ORDERS_ITEMS);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_CUPCAKES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String TEMP_TABLE_NAME = "temp_" + TABLE_ORDER_ITEMS;
        String CREATE_TEMP_TABLE = "CREATE TABLE " + TEMP_TABLE_NAME + " (" +
                COLUMN_ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ORDER_ID + " INTEGER, " +
                COLUMN_CUPCAKE_ID + " INTEGER, " +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_TOTAL_AMOUNT + " REAL, " +
                "FOREIGN KEY(" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_CUPCAKE_ID + ") REFERENCES " + TABLE_CUPCAKES + "(" + COLUMN_CUPCAKE_ID + "));";
        db.execSQL(CREATE_TEMP_TABLE);

        String DROP_OLD_TABLE = "DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS;
        db.execSQL(DROP_OLD_TABLE);

        String RENAME_TEMP_TABLE = "ALTER TABLE " + TEMP_TABLE_NAME + " RENAME TO " + TABLE_ORDER_ITEMS;
        db.execSQL(RENAME_TEMP_TABLE);


    }

    @SuppressLint("Range")
    public boolean login(String Email, String Password, String[] customerName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_CUSTOMER_NAME +
                    " FROM " + TABLE_CUSTOMERS +
                    " WHERE " + COLUMN_CUSTOMER_EMAIL + "=? AND " + COLUMN_CUSTOMER_PASSWORD + "=?";
            cursor = db.rawQuery(query, new String[]{Email, Password});

            if (cursor != null && cursor.moveToFirst()) {
                if (customerName != null && customerName.length > 0) {
                    customerName[0] = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME));
                }
                return true;
            }
        } finally{
                if (cursor != null) {
                    cursor.close();
                }
                db.close();
            }

            return false;


    }


    // 1. Reading categories from the database using cursor and setting them from cursor to variables in modal class via modal class objects
    // 2.Reading categories from the database and storing them in an array list containing objects of modal class (modalCategories)
    public ArrayList<modalCategories> readCategories(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCategories = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);

        ArrayList<modalCategories> categoryArrayList
                = new ArrayList<>();

        if (cursorCategories.moveToFirst()) {
            do {

                modalCategories modalCat=new modalCategories();

                modalCat.setCategoryId(cursorCategories.getInt(0));
                modalCat.setCategoryName(cursorCategories.getString(1));
                modalCat.setCategoryDesc(cursorCategories.getString(2));


                categoryArrayList.add(modalCat);


            } while (cursorCategories.moveToNext());

        }
        cursorCategories.close();
        return categoryArrayList;
    }


    @SuppressLint("Range")
    public int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CATEGORY_ID};
        String selection = COLUMN_CATEGORY_NAME + " = ?";
        String[] selectionArgs = {categoryName};

        Cursor cursor = db.query(TABLE_CATEGORY, columns, selection, selectionArgs, null, null, null);
        int categoryId = -1;

        if (cursor != null && cursor.moveToFirst()) {
            categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
            cursor.close();
        }

        db.close();

        return categoryId;
    }

     @SuppressLint("Range")
     public int getCupcakeIdByName(String cupcakeName){
         SQLiteDatabase db = this.getReadableDatabase();
         String[] columns = {COLUMN_CUPCAKE_ID};
         String selection = COLUMN_CUPCAKE_NAME + " = ?";
         String[] selectionArgs = {cupcakeName};

         Cursor cursor = db.query(TABLE_CUPCAKES, columns, selection, selectionArgs, null, null, null);
         int cupcakeId = -1;

         if (cursor != null && cursor.moveToFirst()) {
             cupcakeId = cursor.getInt(cursor.getColumnIndex(COLUMN_CUPCAKE_ID));
             cursor.close();
         }

         db.close();

         return cupcakeId;
     }

    @SuppressLint("Range")
    public int getCustomerIdByName(String customerName){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CUSTOMER_ID};
        String selection = COLUMN_CUSTOMER_NAME + " = ?";
        String[] selectionArgs = {customerName};

        Cursor cursor = db.query(TABLE_CUSTOMERS, columns, selection, selectionArgs, null, null, null);
        int customerId = -1;

        if (cursor != null && cursor.moveToFirst()) {
            customerId = cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_ID));
            cursor.close();
        }

        db.close();

        return customerId;
    }





    public ArrayList<modalCupcakes> readCupcakes(int categoryId){
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_CUPCAKES + " WHERE " + COLUMN_CATEGORY_ID + " = " + categoryId;
        Cursor cursorCupcakes = db.rawQuery(query, null);


        ArrayList<modalCupcakes> cupcakeArrayList = new ArrayList<>();

        if (cursorCupcakes.moveToFirst()) {
            do {
                modalCupcakes modalCup=new modalCupcakes();

                modalCup.setCupcakeId(cursorCupcakes.getInt(0));
                modalCup.setCupcakeName(cursorCupcakes.getString(1));
                modalCup.setCupcakeDescription(cursorCupcakes.getString(2));
                modalCup.setCupcakePrice(cursorCupcakes.getFloat(3));

                cupcakeArrayList.add(modalCup);
            } while (cursorCupcakes.moveToNext());
        }

        cursorCupcakes.close();
        return cupcakeArrayList;
    }

   /* public ArrayList<modalCupcakes> readCupcakes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCupcakes = db.rawQuery("SELECT * FROM " + TABLE_CUPCAKES, null);

        ArrayList<modalCupcakes> cupcakeArrayList
                = new ArrayList<>();

        if (cursorCupcakes.moveToFirst()) {
            do {

                modalCupcakes modalCup=new modalCupcakes();

                modalCup.setCupcakeId(cursorCupcakes.getInt(0));
                modalCup.setCupcakeName(cursorCupcakes.getString(1));
                modalCup.setCupcakeDescription(cursorCupcakes.getString(2));


                cupcakeArrayList.add(modalCup);


            } while (cursorCupcakes.moveToNext());

        }
        cursorCupcakes.close();
        return cupcakeArrayList;
    }*/

    /*public ArrayList<modalOrder> readOrders(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorOrders = db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);

        ArrayList<modalCategories> categoryArrayList
                = new ArrayList<>();

        if (cursorCategories.moveToFirst()) {
            do {

                modalCategories modalCat=new modalCategories();

                modalCat.setCategoryId(cursorCategories.getInt(0));
                modalCat.setCategoryName(cursorCategories.getString(1));
                modalCat.setCategoryDesc(cursorCategories.getString(2));


                categoryArrayList.add(modalCat);


            } while (cursorCategories.moveToNext());

        }
        cursorCategories.close();
        return categoryArrayList;
    }*/

}
