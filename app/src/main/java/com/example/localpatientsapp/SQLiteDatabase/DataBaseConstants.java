package com.example.localpatientsapp.SQLiteDatabase;

/**
 * Class Name        :  <b>DataBaseConstants.java<b/>
 * Purpose           :  DataBaseConstants is class related of constants.
 * Developed By      :  <b>@ketan waghmare</b>
 * Created Date      :  <b>29-08-2020</b>
 */

/**
 * created by ketan 29-9-2020
 */
public class DataBaseConstants {

    /**
     * datebase name and database version
     */
    public static final String DATABASE_NAME = "demo_database";
    public static final int DATABASE_VERSION = 1;

    /**
     * table name constants
     */
    public static class TableNames {
        public static final String TBL_PATIENTS = "patients";
    }

    /**
     * constants for table patients
     */
    public static class Constants_TBL_PATIENTS {
        public static final String ID = "id";
        public static final String GENDER = "gender";
        public static final String STATUS = "status";
        public static final String LAST_NAME = "last_name";
        public static final String MOBILE_NUMBER = "mobile_number";
        public static final String IS_DELETED = "is_deleted";
        public static final String FIRST_NAME = "first_name";
        public static final String BLOOD_GROUP = "blood_group";
        public static final String CREATE_DATE = "create_date";
        public static final String DATE_OF_BIRTH = "date_of_birth";
    }
}