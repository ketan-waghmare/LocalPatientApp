package com.example.localpatientsapp.SQLiteDatabase;

/**
 * created by ketan 29-9-2020
 */
public class SQLiteQueries {

    /**
     * create patient table query
     */
    public static final String QUERY_TBl_PATIENTS = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_PATIENTS + "("
            + DataBaseConstants.Constants_TBL_PATIENTS.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.GENDER + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.CREATE_DATE + " VARCHAR" + ");";

}
