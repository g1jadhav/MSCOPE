package mahyco.mipl.nxg.util;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.FieldClassification;
import android.util.Log;
//import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.CropModel;
import mahyco.mipl.nxg.model.CropTypeModel;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.FieldLocation;
import mahyco.mipl.nxg.model.FieldMaster;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.GrowerModel;
import mahyco.mipl.nxg.model.OldGrowerSeedDistributionModel;
import mahyco.mipl.nxg.model.ProductCodeModel;
import mahyco.mipl.nxg.model.ProductionClusterModel;
import mahyco.mipl.nxg.model.SeasonModel;
import mahyco.mipl.nxg.model.SeedBatchNoModel;
import mahyco.mipl.nxg.model.SeedReceiptModel;
import mahyco.mipl.nxg.model.StoreAreaModel;


public class SqlightDatabase extends SQLiteOpenHelper {

    final static String DBName = "mipl";
    final static int version =11;
    long count = 0;
    final String tbl_categorymaster = "tbl_categorymaster";
    final String tbl_locationmaster = "tbl_locationmaster";

    final String tbl_parentSeedDistribution = "tbl_parentseeddistribution";

    public SqlightDatabase(Context context) {
        super(context, DBName, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        String createCategoryMaster = "Create table IF NOT EXISTS tbl_categorymaster(\n" +
                "    TempId  INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    CategoryId integer,\n" +
                "    CountryName text,\n" +
                "    Position integer,\n" +
                "    CategoryName text,\n" +
                "    DisplayTitle text,\n" +
                "    IsDelete text,\n" +
                "    CreatedBy text,\n" +
                "    CreatedDt text,\n" +
                "    ModifiedBy text,\n" +
                "    ModifiedDt text\n" +
                ")";
        db.execSQL(createCategoryMaster);

        String createlocationmaster = " Create table IF NOT EXISTS tbl_locationmaster(\n" +
                "    TempId  INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    CountryMasterId INTEGER,\n" +
                "    CategoryId INTEGER,\n" +
                "    ParentId INTEGER,\n" +
                "    KeyValue Text,\n" +
                "    KeyCode text,\n" +
                "    IsDelete text,\n" +
                "    CreatedBy text,\n" +
                "    CreatedDt text,\n" +
                "    ModifiedBy text,\n" +
                "    ModifiedDt text,\n" +
                "    CountryName text,\n" +
                "    CategoryName text,\n" +
                "    DisplayTitle text\n" +
                ")";

        db.execSQL(createlocationmaster);

        String createseasonmaster = "Create table IF NOT EXISTS tbl_seasonmaster(" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "SeasonId INTEGER,\n" +
                "    CountryId INTEGER,\n" +
                "    Season TEXT,\n" +
                "    IsDelete TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt  TEXT,\n" +
                "    ModifiedBy  TEXT,\n" +
                "    ModifiedDt  TEXT,\n" +
                "    CountryName  TEXT)";

        db.execSQL(createseasonmaster);

        String creategrowermaster = " Create table IF NOT EXISTS tbl_growermaster(\n" +
                "    TempId  INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    UserId INTEGER,\n" +
                "    LoginId INTEGER,\n" +
                "    CountryId INTEGER,\n" +
                "    CountryMasterId INTEGER,\n" +
                "    UniqueId text,\n" +
                "    UserType text,\n" +
                "    LandMark Text,\n" +
                "    FullName text,\n" +
                "    DOB text,\n" +
                "    Gender text,\n" +
                "    MobileNo text,\n" +
                "    UniqueCode text,\n" +
                "    RegDt text,\n" +
                "    CreatedBy text,\n" +
                "    CreatedDt text,\n" +
                "    ModifiedBy text,\n" +
                "    ModifiedDt text,\n" +
                "    CountryName text,\n" +
                "    CategoryName text,\n" +
                "    KeyValue text,\n" +
                "    KeyCode text,\n" +
                "    UserName text,\n" +
                "    UserCode text\n" +
                ")";

        db.execSQL(creategrowermaster);
       /*Added by Jeevan 28-11-2022 IF NOT EXISTS previous its only Create table*/
        String createRegistration = " Create table IF NOT EXISTS tbl_registrationmaster(\n" +
                "    TempId  INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    CountryId INTEGER,\n" +
                "    CountryMasterId INTEGER,\n" +
                "    FarmerPhoto Text,\n" +
                "    Landmark text,\n" +
                "    GrowerName text,\n" +
                "    Gender text,\n" +
                "    DateOfBirth text,\n" +
                "    MobileNo text,\n" +
                "    UniqueCode text,\n" +
                "    DateOfRegistration text,\n" +
                "    StaffNameId text,\n" +
                "    FrontPhoto text,\n" +
                "    IsSync INTEGER,\n" +
                "    CreatedBy text,\n" +
                "    UserType text,\n" +
                "    BackPhoto text,\n" +
                "    LoginId INTEGER,\n" +
                "    UniqueId text,\n" +
                "    GrowerImageUpload INTEGER,\n" +
                "    FrontImageUpload INTEGER,\n" +
                "    BackImageUpload INTEGER,\n" +
                "    Addr TEXT\n" +
                ")";

        db.execSQL(createRegistration);

        /*Added by Jeevan 28-11-2022 IF NOT EXISTS previous its only Create table*/
        String parentSeedDistribution = " Create table IF NOT EXISTS " + tbl_parentSeedDistribution + "(\n" +
                "    TempId  INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    CountryId INTEGER,\n" +
                "    PlantingYear text,\n" +
                "    SeasonId INTEGER,\n" +
                "    CropId INTEGER,\n" +
                "    ProductionClusterId INTEGER,\n" +
                "    OrganizerId INTEGER,\n" +
                "    GrowerId INTEGER,\n" +
                "    ParentSeedReceiptId INTEGER,\n" +
                "    FemaleParentSeedBatchId INTEGER,\n" +
                "    MaleParentSeedBatchId INTEGER,\n" +
                "    IssueDt text,\n" +
                "    SeedParentArea REAL,\n" +
                "    CreatedBy text\n" +
                ")";

        db.execSQL(parentSeedDistribution);

        String createcropnmaster = "Create table IF NOT EXISTS tbl_cropmaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    CropId INTEGER,\n" +
                "    CropCode TEXT,\n" +
                "    CropName TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt  TEXT,\n" +
                "    ModifiedBy  TEXT,\n" +
                "    ModifiedDt  TEXT)";

        db.execSQL(createcropnmaster);

        String createclustermaster = "Create table IF NOT EXISTS tbl_clustermaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    ProductionClusterId INTEGER,\n" +
                "    CountryId INTEGER,\n" +
                "    ProductionClusterCode TEXT,\n" +
                "    ProductionCluster TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt  TEXT,\n" +
                "    ModifiedBy  TEXT,\n" +
                "    ModifiedDt  TEXT,\n" +
                "    CountryName  TEXT)";

        db.execSQL(createclustermaster);

        String createproductcodemaster = "Create table  IF NOT EXISTS tbl_productcodemaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    ProductId INTEGER,\n" +
                "    CropId INTEGER,\n" +
                "    ProductType TEXT,\n" +
                "    ProductCode TEXT,\n" +
                "    ProductName TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt  TEXT,\n" +
                "    ModifiedBy  TEXT,\n" +
                "    ModifiedDt  TEXT,\n" +
                "    CropCode  TEXT,\n" +
                "    CropName  TEXT)";

        db.execSQL(createproductcodemaster);

        String createseedreceiptmaster = "Create table IF NOT EXISTS tbl_seedreciptmaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "  ParentSeedReceiptId INTEGER,\n" +
                "  SeedProductionTargetId INTEGER,\n" +
                "  ProductionCode text,\n" +
                "  ParentSeedReceiptType text,\n" +
                "  CountryId INTEGER,\n" +
                "  ProductionClusterId INTEGER,\n" +
                "  UserId INTEGER,\n" +
                "  PlantingYear text,\n" +
                "  SeasonId INTEGER,\n" +
                "  CropTypeId INTEGER,\n" +
                "  CropId INTEGER,\n" +
                "  ProductId INTEGER,\n" +
                "  PlannedArea REAL,\n" +
                "  PlannedProcessedQty REAL,\n" +
                "  PlannedUnprocessedQty REAL,\n" +
                "  NoofFemalePkts REAL,\n" +
                "  NoofMalePkts REAL,\n" +
                "  FemaleSeedRate REAL,\n" +
                "  FemaleSeedPacking REAL,\n" +
                "  MaleSeedRate REAL,\n" +
                "  MaleSeedPacking REAL,\n" +
                "  FemaleParentSeedsArea REAL,\n" +
                "  MaleParentSeedArea REAL,\n" +
                "  TotalFemaleParentSeeds REAL,\n" +
                "  TotalMaleParentSeeds REAL,\n" +
                "  STONo_DeliveryChallanNo text,\n" +
                "  ParentSeedReceiptDt text,\n" +
                "  CreatedBy text,\n" +
                "  CreatedDt text,\n" +
                "  ModifiedBy text,\n" +
                "  ModifiedDt text,\n" +
                "  CountryName text,\n" +
                "  Season text,\n" +
                "  CropCode text,\n" +
                "  CropName text,\n" +
                "  CropType text,\n" +
                "  ProductionCluster text,\n" +
                "  OrganizerName text,\n" +
                "  ProductName text,\n" +
                "    FullName  TEXT)";

        db.execSQL(createseedreceiptmaster);

        String createseedbatchnomaster = "Create table IF NOT EXISTS tbl_seedbatchnomaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    ParentSeedBatchId INTEGER,\n" +
                "    ParentSeedReceiptId INTEGER,\n" +
                "    CountryId INTEGER,\n" +
                "    ParentType TEXT,\n" +
                "    BatchNo TEXT,\n" +
                "    NoOfPackets REAL,\n" +
                "    QTYInKG REAL,\n" +
                "    SeedArea REAL,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt TEXT,\n" +
                "    ModifiedBy TEXT,\n" +
                "    ModifiedDt TEXT,\n" +
                "    ProductionCode TEXT,\n" +
                "    ParentSeedReceiptType TEXT)";

        db.execSQL(createseedbatchnomaster);

        String createcroptypemaster = "Create table IF NOT EXISTS tbl_croptypemaster(\n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    CropTypeId INTEGER,\n" +
                "    CropType TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt  TEXT,\n" +
                "    ModifiedBy  TEXT,\n" +
                "    ModifiedDt  TEXT)";

        db.execSQL(createcroptypemaster);

        String createallseeddistributionmaster = "Create table IF NOT EXISTS tbl_allseeddistributionmaster(\n" +
                " TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "    ParentSeedDistributionId INTEGER,\n" +
                " GrowerId INTEGER,\n" +
                " CountryId INTEGER,\n" +
                " PlantingYear text,\n" +
                " SeasonId INTEGER,\n" +
                " CropId INTEGER,\n" +
                " OrganizerId INTEGER,\n" +
                " ParentSeedReceiptId INTEGER,\n" +
                " SeedProductionArea REAL,\n" +
                " ProductionClusterId INTEGER,\n" +
                " FemaleParentSeedBatchId INTEGER,\n" +
                " MaleParentSeedBatchId INTEGER,\n" +
                " IssueDt text,\n" +
                " CreatedBy text,\n" +
                " CreatedDt text,\n" +
                " ModifiedBy text,\n" +
                " ModifiedDt text,\n" +
                " CountryName text,\n" +
                " CountryCode text,\n" +
                " Season text,\n" +
                " CropCode text,\n" +
                " CropName text,\n" +
                " Grower text,\n" +
                " GrowerUniqueId text,\n" +
                " GrowerFullName text,\n" +
                " GrowerMobileNo text,\n" +
                " GrowerUniqueCode text,\n" +
                " Organizer text,\n" +
                " OrganizerUniqueId text,\n" +
                " OrganizerFullName text,\n" +
                " OrganizerMobileNo text,\n" +
                " OrganizerUniqueCode text,\n" +
                " ProductionCode text,\n" +
                " ParentSeedReceiptType text,\n" +
                " ParentSeedReceiptDt text,\n" +
                " FemaleParentType text,\n" +
                " FemaleBatchNo text,\n" +
                " FemaleNoOfPackets text,\n" +
                " FemaleQTYInKG text,\n" +
                " FemaleSeedArea text,\n" +
                " MaleParentType text,\n" +
                " MaleBatchNo text,\n" +
                " MaleoOfPackets text,\n" +
                " MaleQTYInKG text,\n" +
                " MaleSeedArea text)";

        db.execSQL(createallseeddistributionmaster);

        /*Added by Jeevan 28-11-2022 IF NOT EXISTS previous its only Create table*/
        /*Added by Jeevan 9-12-2022 added GrowerId INTEGER*/
        String storeAreaDistributionData = "Create table IF NOT EXISTS tbl_storestributiondata(\n" +
                " TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                " ProductionCode text,\n" +
                " PlantingYear text,\n" +
                " FemaleBatchNo text,\n" +
                " MaleBatchNo text,\n"  +
                " FemaleParentSeedBatchId INTEGER,\n"  +
                " MaleParentSeedBatchId INTEGER,\n"  +
                " ParentSeedReceiptId INTEGER,\n"  +
                " ParentSeedReceiptType text,\n"  +
                " ClusterId INTEGER,\n"  +
                " GrowerId INTEGER,\n"  +
                " SeedProductionArea REAL)";

        db.execSQL(storeAreaDistributionData);

        String tbl_field_master = "Create table IF NOT EXISTS tbl_field_master(\n" +
                " TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                " FieldId int,\n" +
                " TotalArea text)";

        db.execSQL(tbl_field_master);

        String tbl_field_location = "Create table IF NOT EXISTS tbl_field_location(\n" +
                " TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                " FieldId int,\n" +
                " Latitude text,\n" +
                " Longitude text)";

        db.execSQL(tbl_field_location);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        droptable(db, "tbl_locationmaster");
        droptable(db, "tbl_categorymaster");
        droptable(db, "tbl_seasonmaster");
        droptable(db, "tbl_growermaster");
        /*Commented by Jeevan 28-11-2022*/
       // droptable(db, "tbl_registrationmaster");
       // droptable(db, tbl_parentSeedDistribution);
        /*Commented by Jeevan ended here 28-11-2022*/
        droptable(db, "tbl_cropmaster");
        droptable(db, "tbl_clustermaster");
        droptable(db, "tbl_productcodemaster");
        droptable(db, "tbl_seedreciptmaster");
        droptable(db, "tbl_seedbatchnomaster");
        droptable(db, "tbl_croptypemaster");
        droptable(db, "tbl_allseeddistributionmaster");
        /*Commented by Jeevan 28-11-2022*/
        /*droptable(db, "tbl_storestributiondata");*/
        /*Commented by Jeevan 28-11-2022*/
        onCreate(db);
    }

    private void droptable(SQLiteDatabase db, String s) {
        try {

            String tbl_order_terms = "drop table " + s;
            db.execSQL(tbl_order_terms);
           // Log.i("Table Drop :", s);

        } catch (Exception e) {
           // Log.i("Error is :", e.getMessage());
        }
    }

    public void trucateTable(String s) {
        try {
            SQLiteDatabase db = null;
            db = this.getReadableDatabase();
            String trucateStr = "delete from " + s;
            db.execSQL(trucateStr);
          //  Log.i("Table Trucated :", s);

        } catch (Exception e) {
           // Log.i("Error is :", e.getMessage());
        }
    }

    public boolean addCategory(CategoryModel categoryModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_categorymaster" +
                    "(" +
                    "" +
                    "CategoryId," +
                    "CountryName," +
                    "Position," +
                    "CategoryName," +
                    "DisplayTitle," +
                    "IsDelete," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt" +
                    ") values" +
                    "('" + categoryModel.getCategoryId() + "'," +
                    "'" + categoryModel.getCountryName() + "'," +
                    "'" + categoryModel.getPosition() + "'," +
                    "'" + categoryModel.getCategoryName() + "'," +
                    "'" + categoryModel.getDisplayTitle() + "'," +
                    "'" + categoryModel.isDelete() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getCreatedDt() + "'," +
                    "'" + categoryModel.getModifiedBy() + "'," +
                    "'" + categoryModel.getModifiedDt() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean addLocation(CategoryChildModel categoryModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_locationmaster" +
                    "(" +
                    "" +
                    "CountryMasterId," +
                    "CategoryId," +
                    "ParentId," +
                    "KeyValue," +
                    "KeyCode," +
                    "IsDelete," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName," +
                    "CategoryName," +
                    "DisplayTitle" +
                    ") values" +
                    "('" + categoryModel.getCountryMasterId() + "'," +
                    "'" + categoryModel.getCategoryId() + "'," +
                    "'" + categoryModel.getParentId() + "'," +
                    "'" + categoryModel.getKeyValue() + "'," +
                    "'" + categoryModel.getKeyCode() + "'," +
                    "'" + categoryModel.isDelete() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getCreatedDt() + "'," +
                    "'" + categoryModel.getModifiedBy() + "'," +
                    "'" + categoryModel.getModifiedDt() + "'," +
                    "'" + categoryModel.getCountryName() + "'," +
                    "'" + categoryModel.getCategoryName() + "'," +
                    "'" + categoryModel.getDisplayTitle() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean addFieldLocation(FieldLocation fieldLocation) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_field_location" +
                    "(" +
                    "FieldId," +
                    "Latitude," +
                    "Longitude" +
                    ") values" +
                    "(" + fieldLocation.getFieldId() + "," +
                    "'" + fieldLocation.getLatitude() + "'," +
                    "'" + fieldLocation.getLongitude() + "')";
            // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
            // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean updateFieldMaster(int fieldid,String total) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update tbl_field_master set TotalArea='"+total+"' where FieldId="+fieldid;
            // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
            // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean addFieldMaster(FieldMaster fieldMaster) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_field_master" +
                    "(" +
                    "FieldId," +
                    "TotalArea" +
                    ") values" +
                    "(" + fieldMaster.getFieldId() + "," +
                    "'" + fieldMaster.getTotalArea() + "')";
            // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
            // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public ArrayList<FieldMaster> getAllFieldMaster() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_field_master";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FieldMaster> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    FieldMaster fieldLocation=new FieldMaster();
                    fieldLocation.setFieldId(cursorCourses.getInt(1));
                    fieldLocation.setTotalArea(cursorCourses.getString(2));

                    fieldLocationArrayList.add(fieldLocation);
                } while (cursorCourses.moveToNext());
            }
            return fieldLocationArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }


    public ArrayList<FieldLocation> getAllFieldDetails(int fieldId) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_field_location where FieldId="+fieldId;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FieldLocation> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    FieldLocation fieldLocation=new FieldLocation();
                    fieldLocation.setFieldId(cursorCourses.getInt(1));
                    fieldLocation.setLatitude(cursorCourses.getString(2));
                    fieldLocation.setLongitude(cursorCourses.getString(3));
                    fieldLocationArrayList.add(fieldLocation);
                } while (cursorCourses.moveToNext());
            }
            return fieldLocationArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addRegistration(GrowerModel growerModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_registrationmaster" +
                    "(" +
                    "" +
                    "CountryId," +
                    "CountryMasterId," +
                    "FarmerPhoto," +
                    "Landmark," +
                    "Addr," +
                    "GrowerName," +
                    "Gender," +
                    "DateOfBirth," +
                    "MobileNo," +
                    "UniqueCode," +
                    "DateOfRegistration," +
                    "StaffNameId," +
                    "FrontPhoto," +
                    "IsSync," +
                    "CreatedBy," +
                    "UserType," +
                    "BackPhoto," +
                    "LoginId," +
                    "UniqueId," +
                    "GrowerImageUpload," +
                    "FrontImageUpload," +
                    "BackImageUpload" +
                    ") values" +
                    "('" + growerModel.getCountryId() + "'," +
                    "'" + growerModel.getCountryMasterId() + "'," +
                    "'" + growerModel.getUploadPhoto() + "'," +
                    "'" + growerModel.getLandMark() + "'," +
                    "'" + growerModel.getAddr() + "'," +
                    "'" + growerModel.getFullName() + "'," +
                    "'" + growerModel.getGender() + "'," +
                    "'" + growerModel.getDOB() + "'," +
                    "'" + growerModel.getMobileNo() + "'," +
                    "'" + growerModel.getUniqueCode() + "'," +
                    "'" + growerModel.getRegDt() + "'," +
                    "'" + growerModel.getStaffNameAndId() + "'," +
                    "'" + growerModel.getIdProofFrontCopy() + "'," +
                    "'" + growerModel.getIsSync() + "'," +
                    "'" + growerModel.getCreatedBy() + "'," +
                    "'" + growerModel.getUserType() + "'," +
                    "'" + growerModel.getIdProofBackCopy() + "'," +
                    "'" + growerModel.getLoginId() + "'," +
                    "'" + growerModel.getUniqueId() + "'," +
                    "'" + growerModel.getGrowerImageUpload() + "'," +
                    "'" + growerModel.getFrontImageUpload() + "'," +
                    "'" + growerModel.getBackImageUpload() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean parentSeedDistribution(OldGrowerSeedDistributionModel seedDistributionModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into " + tbl_parentSeedDistribution +
                    "(" +
                    "" +
                    "CountryId," +
                    "PlantingYear," +
                    "SeasonId," +
                    "CropId," +
                    "ProductionClusterId," +
                    "OrganizerId," +
                    "GrowerId," +
                    "ParentSeedReceiptId," +
                    "FemaleParentSeedBatchId," +
                    "MaleParentSeedBatchId," +
                    "IssueDt," +
                    "SeedParentArea," +
                    "CreatedBy" +
                    ") values" +
                    "('" + seedDistributionModel.getCountryId() + "'," +
                    "'" + seedDistributionModel.getPlantingYear() + "'," +
                    "'" + seedDistributionModel.getSeasonId() + "'," +
                    "'" + seedDistributionModel.getCropId() + "'," +
                    "'" + seedDistributionModel.getProductionClusterId() + "'," +
                    "'" + seedDistributionModel.getOrganizerId() + "'," +
                    "'" + seedDistributionModel.getGrowerId() + "'," +
                    "'" + seedDistributionModel.getParentSeedReceiptId() + "'," +
                    "'" + seedDistributionModel.getFemaleParentSeedBatchId() + "'," +
                    "'" + seedDistributionModel.getMaleParentSeedBatchId() + "'," +
                    "'" + seedDistributionModel.getIssueDt() + "'," +
                    "'" + seedDistributionModel.getSeedProductionArea() + "'," +
                    "'" + seedDistributionModel.getCreatedBy() + "')";
           // Log.e("temporary", "Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary", "Error is Product Added " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public ArrayList<CategoryModel> getAllCategories() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_categorymaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<CategoryModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new CategoryModel(cursorCourses.getInt(1),
                            cursorCourses.getString(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<GrowerModel> getAllRegistration() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT * FROM tbl_registrationmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<GrowerModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new GrowerModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10),
                            cursorCourses.getString(11),
                            cursorCourses.getString(12),
                            cursorCourses.getInt(13),
                            cursorCourses.getString(14),
                            cursorCourses.getString(15),
                            cursorCourses.getString(16),
                            cursorCourses.getInt(17),
                            cursorCourses.getString(18),
                            cursorCourses.getInt(0),
                            cursorCourses.getInt(19),
                            cursorCourses.getInt(20),
                            cursorCourses.getInt(21),
                            cursorCourses.getString(22)
                    ));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<OldGrowerSeedDistributionModel> getParentSeedDistributionList() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT * FROM " + tbl_parentSeedDistribution;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<OldGrowerSeedDistributionModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new OldGrowerSeedDistributionModel(
                            cursorCourses.getInt(1),
                            cursorCourses.getString(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getInt(4),
                            cursorCourses.getInt(5),
                            cursorCourses.getInt(6),
                            cursorCourses.getInt(7),
                            cursorCourses.getInt(8),
                            cursorCourses.getInt(9),
                            cursorCourses.getInt(10),
                            cursorCourses.getString(11),
                            cursorCourses.getFloat(12),
                            cursorCourses.getString(13)
                    ));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean updateRegistrationStatus(String uniqueCode, int status) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            // String q = "update  tbl_registrationmaster set IsSync=" + status + " where MobileNo=" + mobileNumber;
            String q = "update  tbl_registrationmaster set IsSync='" + status + "' where UniqueCode='" + uniqueCode + "'";
          //  Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is  Added ", "Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean deleteSeedDistribution(/*int tempId*/) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "DELETE from tbl_parentSeedDistribution"/* where TempId='" + tempId + "'"*/;
           // Log.e("temporary", " deleted Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.e("temporary", " deleted Error is Clear List " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean deleteRegistration(String uniqueCode) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "DELETE from tbl_registrationmaster where UniqueCode='" + uniqueCode + "'";
           // Log.e("temporary", " deleted Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary", " deleted Error is Clear List " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }
    public boolean updateAllUserType(int userType) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q="";
            if(userType==1)
             q = "Delete from tbl_registrationmaster where UserType='Grower'";
            else
              q=  "Delete from tbl_registrationmaster where UserType='Organizer'";
             Log.e("temporary", " deleted Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
            // Log.e("temporary", " deleted Error is Clear List " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean updateRegistrationImagePath(String uniqueCode, String imageType, String path, int isInsertedValue) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q;
            if (imageType.equalsIgnoreCase("growerPhoto")) {
                q = "update  tbl_registrationmaster set FarmerPhoto='" + path + "',  GrowerImageUpload='" + isInsertedValue + "'where UniqueCode='" + uniqueCode + "'";
            } else if (imageType.equalsIgnoreCase("docBackPhoto")) {
                q = "update  tbl_registrationmaster set BackPhoto='" + path + "',  BackImageUpload='" + isInsertedValue + "'where UniqueCode='" + uniqueCode + "'";
            } else {
                q = "update  tbl_registrationmaster set FrontPhoto='" + path + "',  FrontImageUpload='" + isInsertedValue + "'where UniqueCode='" + uniqueCode + "'";
            }
           // Log.e("temporary", "updateRegistrationImagePath Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary", "updateRegistrationImagePath Error is Added Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<CategoryChildModel> getLocationCategories(int countryMasterId) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_locationmaster where ParentId=" + countryMasterId;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<CategoryChildModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new CategoryChildModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10),
                            cursorCourses.getString(11),
                            cursorCourses.getString(12),
                            cursorCourses.getString(13)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addSeason(SeasonModel seasonModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_seasonmaster" +
                    "(" +
                    "" +
                    "SeasonId," +
                    "CountryId," +
                    "Season," +
                    "IsDelete," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName" +

                    ") values" +
                    "('" + seasonModel.getSeasonId() + "'," +
                    "'" + seasonModel.getCountryId() + "'," +
                    "'" + seasonModel.getSeason() + "'," +
                    "'" + seasonModel.isDelete() + "'," +
                    "'" + seasonModel.getCreatedBy() + "'," +
                    "'" + seasonModel.getCreatedDt() + "'," +
                    "'" + seasonModel.getModifiedBy() + "'," +
                    "'" + seasonModel.getModifiedDt() + "'," +
                    "'" + seasonModel.getCountryName() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<SeasonModel> getSeasonMaster() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_seasonmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<SeasonModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new SeasonModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addCrop(CropModel cropModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_cropmaster" +
                    "(" +
                    "" +
                    "CropId," +
                    "CropCode," +
                    "CropName," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt" +

                    ") values" +
                    "('" + cropModel.getCropId() + "'," +
                    "'" + cropModel.getCropCode() + "'," +
                    "'" + cropModel.getCropName() + "'," +
                    "'" + cropModel.getCreatedBy() + "'," +
                    "'" + cropModel.getCreatedDt() + "'," +
                    "'" + cropModel.getModifiedBy() + "'," +
                    "'" + cropModel.getModifiedDt() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<CropModel> getCropMaster() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_cropmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<CropModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new CropModel(cursorCourses.getInt(1),
                            cursorCourses.getString(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addProdCluster(ProductionClusterModel cropModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_clustermaster" +
                    "(" +
                    "" +
                    "ProductionClusterId," +
                    "CountryId," +
                    "ProductionClusterCode," +
                    "ProductionCluster," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName" +

                    ") values" +
                    "('" + cropModel.getProductionClusterId() + "'," +
                    "'" + cropModel.getCountryId() + "'," +
                    "'" + cropModel.getProductionClusterCode() + "'," +
                    "'" + cropModel.getProductionCluster() + "'," +
                    "'" + cropModel.getCreatedBy() + "'," +
                    "'" + cropModel.getCreatedDt() + "'," +
                    "'" + cropModel.getModifiedBy() + "'," +
                    "'" + cropModel.getModifiedDt() + "'," +
                    "'" + cropModel.getCountryName() + "')";
          //  Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean addSeedReceipt(SeedReceiptModel cropModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_seedreciptmaster" +
                    "(" +
                    "" +
                    "ParentSeedReceiptId," +
                    "SeedProductionTargetId," +
                    "ProductionCode," +
                    "ParentSeedReceiptType," +
                    "CountryId," +
                    "ProductionClusterId," +
                    "UserId," +
                    "PlantingYear," +
                    "SeasonId," +
                    "CropTypeId," +
                    "CropId," +
                    "ProductId," +
                    "PlannedArea," +
                    "PlannedProcessedQty," +
                    "PlannedUnprocessedQty," +
                    "NoofFemalePkts," +
                    "NoofMalePkts," +
                    "FemaleSeedRate," +
                    "FemaleSeedPacking," +
                    "MaleSeedRate," +
                    "MaleSeedPacking," +
                    "FemaleParentSeedsArea," +
                    "MaleParentSeedArea," +
                    "TotalFemaleParentSeeds," +
                    "TotalMaleParentSeeds," +
                    "STONo_DeliveryChallanNo," +
                    "ParentSeedReceiptDt," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName," +
                    "Season," +
                    "CropCode," +
                    "CropName," +
                    "CropType," +
                    "ProductionCluster," +
                    "OrganizerName," +
                    "ProductName," +
                    "FullName" +

                    ") values" +
                    "('" + cropModel.getParentSeedReceiptId() + "'," +
                    "'" + cropModel.getSeedProductionTargetId() + "'," +
                    "'" + cropModel.getProductionCode() + "'," +
                    "'" + cropModel.getParentSeedReceiptType() + "'," +
                    "'" + cropModel.getCountryId() + "'," +
                    "'" + cropModel.getProductionClusterId() + "'," +
                    "'" + cropModel.getUserId() + "'," +
                    "'" + cropModel.getPlantingYear() + "'," +
                    "'" + cropModel.getSeasonId() + "'," +
                    "'" + cropModel.getCropTypeId() + "'," +
                    "'" + cropModel.getCropId() + "'," +
                    "'" + cropModel.getProductId() + "'," +
                    "'" + cropModel.getPlannedArea() + "'," +
                    "'" + cropModel.getPlannedProcessedQty() + "'," +
                    "'" + cropModel.getPlannedUnprocessedQty() + "'," +
                    "'" + cropModel.getNoofFemalePkts() + "'," +
                    "'" + cropModel.getNoofMalePkts() + "'," +
                    "'" + cropModel.getFemaleSeedRate() + "'," +
                    "'" + cropModel.getFemaleSeedPacking() + "'," +
                    "'" + cropModel.getMaleSeedRate() + "'," +
                    "'" + cropModel.getMaleSeedPacking() + "'," +
                    "'" + cropModel.getFemaleParentSeedsArea() + "'," +
                    "'" + cropModel.getMaleParentSeedArea() + "'," +
                    "'" + cropModel.getTotalFemaleParentSeeds() + "'," +
                    "'" + cropModel.getTotalMaleParentSeeds() + "'," +
                    "'" + cropModel.getSTONo_DeliveryChallanNo() + "'," +
                    "'" + cropModel.getParentSeedReceiptDt() + "'," +
                    "'" + cropModel.getCreatedBy() + "'," +
                    "'" + cropModel.getCreatedDt() + "'," +
                    "'" + cropModel.getModifiedBy() + "'," +
                    "'" + cropModel.getModifiedDt() + "'," +
                    "'" + cropModel.getCountryName() + "'," +
                    "'" + cropModel.getSeason() + "'," +
                    "'" + cropModel.getCropCode() + "'," +
                    "'" + cropModel.getCropName() + "'," +
                    "'" + cropModel.getCropType() + "'," +
                    "'" + cropModel.getProductionCluster() + "'," +
                    "'" + cropModel.getOrganizerName() + "'," +
                    "'" + cropModel.getProductName() + "'," +
                    "'" + cropModel.getFullName() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<SeedReceiptModel> getSeedReceiptMaster(/*String cropCode*/) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
//            String q = "SELECT  * FROM tbl_seedreciptmaster where CropCode=" + cropCode;
            String q = "SELECT  * FROM tbl_seedreciptmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<SeedReceiptModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new SeedReceiptModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getInt(5),
                            cursorCourses.getInt(6),
                            cursorCourses.getInt(7),
                            cursorCourses.getString(8),
                            cursorCourses.getInt(9),
                            cursorCourses.getInt(10),
                            cursorCourses.getInt(11),
                            cursorCourses.getInt(12),
                            cursorCourses.getFloat(13),
                            cursorCourses.getFloat(14),
                            cursorCourses.getFloat(15),
                            cursorCourses.getFloat(16),
                            cursorCourses.getFloat(17),
                            cursorCourses.getFloat(18),
                            cursorCourses.getFloat(19),
                            cursorCourses.getFloat(20),
                            cursorCourses.getFloat(21),
                            cursorCourses.getFloat(22),
                            cursorCourses.getFloat(23),
                            cursorCourses.getFloat(24),
                            cursorCourses.getFloat(25),
                            cursorCourses.getString(26),
                            cursorCourses.getString(27),
                            cursorCourses.getString(28),
                            cursorCourses.getString(29),
                            cursorCourses.getString(30),
                            cursorCourses.getString(31),
                            cursorCourses.getString(32),
                            cursorCourses.getString(33),
                            cursorCourses.getString(34),
                            cursorCourses.getString(35),
                            cursorCourses.getString(36),
                            cursorCourses.getString(37),
                            cursorCourses.getString(38),
                            cursorCourses.getString(39),
                            cursorCourses.getString(40)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean updateSeedParentArea(int parentReceiptId, float femaleParentSeedsArea,
                                        float maleParentSeedsArea) {
//        Log.e("temporary"," parentReceiptId " + parentReceiptId
//        +" femaleParentSeedsArea " + femaleParentSeedsArea+" maleParentSeedsArea " +maleParentSeedsArea);
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update  tbl_seedreciptmaster set FemaleParentSeedsArea='" + femaleParentSeedsArea + "',  MaleParentSeedArea='" + maleParentSeedsArea + "'where ParentSeedReceiptId='" + parentReceiptId + "'";
          //  Log.e("temporary","Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary","Error is  Added Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean addAllSeedDistributionList(GetAllSeedDistributionModel categoryModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_allseeddistributionmaster" +
                    "(" +
                    "" +
                    "ParentSeedDistributionId," +
                    "GrowerId," +
                    "CountryId," +
                    "PlantingYear," +
                    "SeasonId," +
                    "CropId," +
                    "OrganizerId," +
                    "ParentSeedReceiptId," +
                    "SeedProductionArea," +
                    "ProductionClusterId," +
                    "FemaleParentSeedBatchId," +
                    "MaleParentSeedBatchId," +
                    "IssueDt," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName," +
                    "CountryCode," +
                    "Season ," +
                    "CropCode ," +
                    "CropName," +
                    "Grower," +
                    "GrowerUniqueId," +
                    "GrowerFullName," +
                    "GrowerMobileNo," +
                    "GrowerUniqueCode," +
                    "Organizer," +
                    "OrganizerUniqueId," +
                    "OrganizerFullName," +
                    "OrganizerMobileNo," +
                    "OrganizerUniqueCode," +
                    "ProductionCode," +
                    "ParentSeedReceiptType," +
                    "ParentSeedReceiptDt," +
                    "FemaleParentType," +
                    "FemaleBatchNo," +
                    "FemaleNoOfPackets," +
                    "FemaleQTYInKG," +
                    "FemaleSeedArea," +
                    "MaleParentType," +
                    "MaleBatchNo," +
                    "MaleoOfPackets," +
                    "MaleQTYInKG," +
                    "MaleSeedArea" +
                    ") values" +
                    "('" + categoryModel.getParentSeedDistributionId() + "'," +
                    "'" + categoryModel.getGrowerId() + "'," +
                    "'" + categoryModel.getCountryId() + "'," +
                    "'" + categoryModel.getPlantingYear() + "'," +
                    "'" + categoryModel.getSeasonId() + "'," +
                    "'" + categoryModel.getCropId() + "'," +
                    "'" + categoryModel.getOrganizerId() + "'," +
                    "'" + categoryModel.getParentSeedReceiptId() + "'," +
                    "'" + categoryModel.getSeedProductionArea() + "'," +
                    "'" + categoryModel.getProductionClusterId() + "'," +
                    "'" + categoryModel.getFemaleParentSeedBatchId() + "'," +
                    "'" + categoryModel.getMaleParentSeedBatchId() + "'," +
                    "'" + categoryModel.getIssueDt() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getCreatedDt() + "'," +
                    "'" + categoryModel.getModifiedBy() + "'," +
                    "'" + categoryModel.getModifiedDt() + "'," +
                    "'" + categoryModel.getCountryName() + "'," +
                    "'" + categoryModel.getCountryCode() + "'," +
                    "'" + categoryModel.getSeason() + "'," +
                    "'" + categoryModel.getCropCode() + "'," +
                    "'" + categoryModel.getCropName() + "'," +
                    "'" + categoryModel.getGrower() + "'," +
                    "'" + categoryModel.getGrowerUniqueId() + "'," +
                    "'" + categoryModel.getGrowerFullName() + "'," +
                    "'" + categoryModel.getGrowerMobileNo() + "'," +
                    "'" + categoryModel.getGrowerUniqueCode() + "'," +
                    "'" + categoryModel.getOrganizer() + "'," +
                    "'" + categoryModel.getOrganizerUniqueId() + "'," +
                    "'" + categoryModel.getOrganizerFullName() + "'," +
                    "'" + categoryModel.getOrganizerMobileNo() + "'," +
                    "'" + categoryModel.getOrganizerUniqueCode() + "'," +
                    "'" + categoryModel.getProductionCode() + "'," +
                    "'" + categoryModel.getParentSeedReceiptType() + "'," +
                    "'" + categoryModel.getParentSeedReceiptDt() + "'," +
                    "'" + categoryModel.getFemaleParentType() + "'," +
                    "'" + categoryModel.getFemaleBatchNo() + "'," +
                    "'" + categoryModel.getFemaleNoOfPackets() + "'," +
                    "'" + categoryModel.getFemaleQTYInKG() + "'," +
                    "'" + categoryModel.getFemaleSeedArea() + "'," +
                    "'" + categoryModel.getMaleParentType() + "'," +
                    "'" + categoryModel.getMaleBatchNo() + "'," +
                    "'" + categoryModel.getMaleoOfPackets() + "'," +
                    "'" + categoryModel.getMaleQTYInKG() + "'," +
                    "'" + categoryModel.getMaleSeedArea() + "')";
          //  Log.e("temporary"," seed distribution Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.e("temporary","Error is Product Added " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<GetAllSeedDistributionModel> getAllSeedDistributionListNo() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_allseeddistributionmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<GetAllSeedDistributionModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new GetAllSeedDistributionModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getString(4),
                            cursorCourses.getInt(5),
                            cursorCourses.getInt(6),
                            cursorCourses.getInt(7),
                            cursorCourses.getInt(8),
                            cursorCourses.getFloat(9),
                            cursorCourses.getInt(10),
                            cursorCourses.getInt(11),
                            cursorCourses.getInt(12),
                            cursorCourses.getString(13),
                            cursorCourses.getString(14),
                            cursorCourses.getString(15),
                            cursorCourses.getString(16),
                            cursorCourses.getString(17),
                            cursorCourses.getString(18),
                            cursorCourses.getString(19),
                            cursorCourses.getString(20),
                            cursorCourses.getString(21),
                            cursorCourses.getString(22),
                            cursorCourses.getString(23),
                            cursorCourses.getString(24),
                            cursorCourses.getString(25),
                            cursorCourses.getString(26),
                            cursorCourses.getString(27),
                            cursorCourses.getString(28),
                            cursorCourses.getString(29),
                            cursorCourses.getString(30),
                            cursorCourses.getString(31),
                            cursorCourses.getString(32),
                            cursorCourses.getString(33),
                            cursorCourses.getString(34),
                            cursorCourses.getString(35),
                            cursorCourses.getString(36),
                            cursorCourses.getString(37),
                            cursorCourses.getString(38),
                            cursorCourses.getString(39),
                            cursorCourses.getString(40),
                            cursorCourses.getString(41),
                            cursorCourses.getString(42),
                            cursorCourses.getString(43),
                            cursorCourses.getString(44),
                            cursorCourses.getString(45)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addSeedBatchNo(SeedBatchNoModel categoryModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_seedbatchnomaster" +
                    "(" +
                    "" +
                    "ParentSeedBatchId," +
                    "ParentSeedReceiptId," +
                    "CountryId," +
                    "ParentType," +
                    "BatchNo," +
                    "NoOfPackets," +
                    "QTYInKG," +
                    "SeedArea," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "ProductionCode," +
                    "ParentSeedReceiptType" +
                    ") values" +
                    "('" + categoryModel.getParentSeedBatchId() + "'," +
                    "'" + categoryModel.getParentSeedReceiptId() + "'," +
                    "'" + categoryModel.getCountryId() + "'," +
                    "'" + categoryModel.getParentType() + "'," +
                    "'" + categoryModel.getBatchNo() + "'," +
                    "'" + categoryModel.getNoOfPackets() + "'," +
                    "'" + categoryModel.getQTYInKG() + "'," +
                    "'" + categoryModel.getSeedArea() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getModifiedBy() + "'," +
                    "'" + categoryModel.getModifiedDt() + "'," +
                    "'" + categoryModel.getProductionCode() + "'," +
                    "'" + categoryModel.getParentSeedReceiptType() + "')";
          //  Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean addCropType(CropTypeModel cropModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_croptypemaster" +
                    "(" +
                    "" +
                    "CropTypeId," +
                    "CropType," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt" +

                    ") values" +
                    "('" + cropModel.getCropTypeId() + "'," +
                    "'" + cropModel.getCropType() + "'," +
                    "'" + cropModel.getCreatedBy() + "'," +
                    "'" + cropModel.getCreatedDt() + "'," +
                    "'" + cropModel.getModifiedBy() + "'," +
                    "'" + cropModel.getModifiedDt() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<CropTypeModel> getCropType() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_croptypemaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<CropTypeModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new CropTypeModel(cursorCourses.getInt(1),
                            cursorCourses.getString(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<SeedBatchNoModel> getSeedBatchNo(String productionCode) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_seedbatchnomaster  where ProductionCode='" + productionCode + "'";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<SeedBatchNoModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new SeedBatchNoModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getFloat(6),
                            cursorCourses.getFloat(7),
                            cursorCourses.getFloat(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10),
                            cursorCourses.getString(11),
                            cursorCourses.getString(12),
                            cursorCourses.getString(13),
                            cursorCourses.getString(14)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean addProdCode(ProductCodeModel cropModel) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_productcodemaster" +
                    "(" +
                    "" +
                    "ProductId," +
                    "CropId," +
                    "ProductType," +
                    "ProductCode," +
                    "ProductName," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CropCode," +
                    "CropName" +

                    ") values" +
                    "('" + cropModel.getProductId() + "'," +
                    "'" + cropModel.getCropId() + "'," +
                    "'" + cropModel.getProductType() + "'," +
                    "'" + cropModel.getProductCode() + "'," +
                    "'" + cropModel.getProductName() + "'," +
                    "'" + cropModel.getCreatedBy() + "'," +
                    "'" + cropModel.getCreatedDt() + "'," +
                    "'" + cropModel.getModifiedBy() + "'," +
                    "'" + cropModel.getModifiedDt() + "'," +
                    "'" + cropModel.getCropCode() + "'," +
                    "'" + cropModel.getCropName() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<ProductCodeModel> getProdCodeMaster(String cropCode) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_productcodemaster  where CropCode=" + cropCode;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<ProductCodeModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new ProductCodeModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10),
                            cursorCourses.getString(11)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<ProductionClusterModel> getProdClusterMaster() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_clustermaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<ProductionClusterModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new ProductionClusterModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getString(3),
                            cursorCourses.getString(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public int checkUniqueCodeInGrowerMaster(String uniqueCode) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String countQuery = "SELECT COUNT (*) FROM tbl_growermaster WHERE UniqueCode='" + uniqueCode + "'";
           // Log.e("temporary", "checkUniqueCodeInGrowerMaster Query is -------> " + countQuery);
            Cursor cursor = mydb.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        } catch (Exception e) {
          //  Log.e("temporary", "checkUniqueCodeInGrowerMaster Error  : " + e.getMessage());
            return -1;
        } finally {
            mydb.close();
        }
    }

    public boolean addGrower(DownloadGrowerModel categoryModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_growermaster" +
                    "(" +
                    "" +
                    "UserId," +
                    "LoginId," +
                    "CountryId," +
                    "CountryMasterId," +
                    "UniqueId," +
                    "UserType," +
                    "LandMark," +
                    "FullName," +
                    "DOB," +
                    "Gender," +
                    "MobileNo," +
                    "UniqueCode," +
                    "RegDt," +
                    "CreatedBy," +
                    "CreatedDt," +
                    "ModifiedBy," +
                    "ModifiedDt," +
                    "CountryName," +
                    "CategoryName," +
                    "KeyValue," +
                    "KeyCode," +
                    "UserName," +
                    "UserCode" +
                    ") values" +
                    "('" + categoryModel.getUserId() + "'," +
                    "'" + categoryModel.getLoginId() + "'," +
                    "'" + categoryModel.getCountryId() + "'," +
                    "'" + categoryModel.getCountryMasterId() + "'," +
                    "'" + categoryModel.getUniqueId() + "'," +
                    "'" + categoryModel.getUserType() + "'," +
                    "'" + categoryModel.getAddr()+","+categoryModel.getLandMark() + "'," +
                    "'" + categoryModel.getFullName() + "'," +
                    "'" + categoryModel.getDOB() + "'," +
                    "'" + categoryModel.getGender() + "'," +
                    "'" + categoryModel.getMobileNo() + "'," +
                    "'" + categoryModel.getUniqueCode() + "'," +
                    "'" + categoryModel.getRegDt() + "'," +
                    "'" + categoryModel.getCreatedBy() + "'," +
                    "'" + categoryModel.getCreatedDt() + "'," +
                    "'" + categoryModel.getModifiedBy() + "'," +
                    "'" + categoryModel.getModifiedDt() + "'," +
                    "'" + categoryModel.getCountryName() + "'," +
                    "'" + categoryModel.getCategoryName() + "'," +
                    "'" + categoryModel.getKeyValue() + "'," +
                    "'" + categoryModel.getKeyCode() + "'," +
                    "'" + categoryModel.getUserName() + "'," +
                    "'" + categoryModel.getUserCode() + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public ArrayList<DownloadGrowerModel> getDownloadedGrowerMaster() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            /*Commented by Jeevan 09-12-2022*/
           /* String q = "SELECT  * FROM tbl_growermaster";*/
            /*Commented by Jeevan 09-12-2022 ended here*/

            /*Added by Jeevan 09-12-2022*/
            String q = "SELECT  *, IFNULL ((SELECT SUM(SeedProductionArea) from tbl_storestributiondata where GrowerId = UserId), 0) as area from tbl_growermaster";
            /*Added by Jeevan 09-12-2022 ended here*/
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<DownloadGrowerModel> courseModalArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    courseModalArrayList.add(new DownloadGrowerModel(cursorCourses.getInt(1),
                            cursorCourses.getInt(2),
                            cursorCourses.getInt(3),
                            cursorCourses.getInt(4),
                            cursorCourses.getString(5),
                            cursorCourses.getString(6),
                            cursorCourses.getString(7),
                            cursorCourses.getString(8),
                            cursorCourses.getString(9),
                            cursorCourses.getString(10),
                            cursorCourses.getString(11),
                            cursorCourses.getString(12),
                            cursorCourses.getString(13),
                            cursorCourses.getString(14),
                            cursorCourses.getString(15),
                            cursorCourses.getString(16),
                            cursorCourses.getString(17),
                            cursorCourses.getString(18),
                            cursorCourses.getString(19),
                            cursorCourses.getString(20),
                            cursorCourses.getString(21),
                            cursorCourses.getString(22),
                            cursorCourses.getString(23),
                            cursorCourses.getString(24)));
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }


    public boolean addOrderLocal(String id, String details, int status, String cname) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_order_local(Details,Status,customername,createddate) values" +
                    "('" + details + "'," + status + ",'" + cname + "',datetime('now'))";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.i("Error is  Added ", "Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean updateLocalOrerStatus(String id, int status) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update  tbl_order_local set Status=" + status + " where id=" + id;
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is  Added ", "Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean clearProductList() {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "delete from order_details";
            //String q = "delete from tbl_customersatyam";

          //  Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Clear List", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean clearProductList(int id) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "delete from order_details where productid=" + id + "";
            //String q = "delete from tbl_customersatyam";

           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Clear List", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean clearTermsList() {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "delete from tbl_order_terms";

           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Clear List", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean clearTermsList(int id) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "delete from tbl_order_terms where ParticularId='" + id + "'";

           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.i("Error is Clear List", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public Vector getProductDetailsById(String id) {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_cstatus where id=" + id;

            Cursor c = mydb.rawQuery(q, null);
            v = new Vector();
            if (c.moveToNext()) {
                //v[i]=new Vector();

                v.addElement(c.getInt(0));
                v.addElement(c.getString(1));
                v.addElement(c.getString(2));
                v.addElement(c.getString(3));
                v.addElement(c.getString(4));
                v.addElement(c.getString(5));
                v.addElement(c.getString(6));


                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public Vector[] getAllProducts() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v[];
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM order_details";

            Cursor c = mydb.rawQuery(q, null);
            v = new Vector[c.getCount()];
            while (c.moveToNext()) {
                v[i] = new Vector();

                v[i].addElement(c.getInt(0)); //id
                v[i].addElement(c.getString(1));//productid
                v[i].addElement(c.getInt(2));
                v[i].addElement(c.getString(3));
                v[i].addElement(c.getString(4));
                v[i].addElement(c.getString(5));
                v[i].addElement(c.getString(6));
                v[i].addElement(c.getString(7));
                v[i].addElement(c.getString(8));
                v[i].addElement(c.getString(9));

                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public boolean addTerms(int termId, int srNo, int orderId, int particularId, String condition, boolean isRemoved, String name) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_order_terms(TermId,SrNo,OrderId,ParticularId,Condition,IsRemoved,name) values" +
                    "(" + termId + "," + srNo + "," + orderId + "," + particularId + ",'" + condition + "','" + isRemoved + "','" + name + "')";
           // Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.i("Error is Product Added ", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }


    }

    public Vector[] getAllTerms() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v[];
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_order_terms";

            Cursor c = mydb.rawQuery(q, null);
            v = new Vector[c.getCount()];
            while (c.moveToNext()) {
                v[i] = new Vector();

                v[i].addElement(c.getInt(0)); //id
                v[i].addElement(c.getInt(1));//productid
                v[i].addElement(c.getInt(2));
                v[i].addElement(c.getInt(3));
                v[i].addElement(c.getString(4));
                v[i].addElement(c.getString(5));
                v[i].addElement(c.getString(6));

                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public Vector[] getAllTermsForAdd() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v[];
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_order_terms";

            Cursor c = mydb.rawQuery(q, null);
            v = new Vector[c.getCount()];
            while (c.moveToNext()) {
                v[i] = new Vector();

                v[i].addElement(c.getInt(0)); //id
                v[i].addElement(c.getInt(1));//productid
                v[i].addElement(c.getInt(2));
                v[i].addElement(c.getInt(3));
                v[i].addElement(c.getString(4));
                v[i].addElement(c.getString(5));


                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public Vector[] getAllOfflineOrders() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v[];
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_order_local where status=0";

            Cursor c = mydb.rawQuery(q, null);
            v = new Vector[c.getCount()];
            while (c.moveToNext()) {
                v[i] = new Vector();

                v[i].addElement(c.getInt(0)); //id
                v[i].addElement(c.getString(1));//productid
                v[i].addElement(c.getInt(2));
                v[i].addElement(c.getString(3));
                v[i].addElement(c.getString(4));
                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


    public Vector getAllOfflineOrdersById(int id) {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        ;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_order_local where id=" + id + " and status=0";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {


                v.addElement(c.getInt(0)); //id
                v.addElement(c.getString(1).replace("\\\"", ""));//productid
                v.addElement(c.getInt(2));
                v.addElement(c.getString(3));
                v.addElement(c.getString(4));
                i++;
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public int getMaxOfflineOrderID() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        ;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  ifnull(max(id),0)+1 FROM tbl_order_local";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                i = c.getInt(0); //id
            }

            return i;
        } catch (Exception e) {

            return 0;
        } finally {
            mydb.close();
        }
    }

    public boolean isGrowerRegister(String uniqueID) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
//            String q = "select count(*)as cnt from tbl_registrationmaster where UniqueCode="+uniqueID;
            String q = "SELECT  * FROM tbl_registrationmaster WHERE UniqueCode='" + uniqueID + "'";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            // Log.e("temporary"," isGrowerRegister move to first "+ cursorCourses.moveToFirst() +" uniqueID "+uniqueID);
            if (cursorCourses.moveToFirst()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            myDb.close();
        }
    }

    public boolean isGrowerDownloaded(String uniqueID) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            //String q = "select * from tbl_growermaster where UniqueId="+uniqueID;
            String q = "SELECT  * FROM tbl_growermaster WHERE UniqueCode='" + uniqueID + "'";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            //   Log.e("temporary","isGrowerDownloaded move to first "+ cursorCourses.moveToFirst() +" uniqueID "+uniqueID);
            if (cursorCourses.moveToFirst()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            myDb.close();
        }
    }

    public boolean isSeedDistributionRegister(int userId, String plantingYear) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
//            String q = "select count(*)as cnt from tbl_parentSeedDistribution where GrowerId="+userId;
            String q = "SELECT  * FROM tbl_parentSeedDistribution WHERE GrowerId='" + userId + "' AND PlantingYear='" + plantingYear + "'";
            Cursor cursorCourses = myDb.rawQuery(q, null);
           // Log.e("temporary", "isSeedDistributionRegister move to first " + cursorCourses.moveToFirst() + " userId " + userId);
            if (cursorCourses.moveToFirst()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            myDb.close();
        }
    }

    public boolean isSeedDistributionListDownloaded(int userId, String plantingYear) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            // String q = "select * from tbl_allseeddistributionmaster where GrowerId="+userId;
            String q = "SELECT  * FROM tbl_allseeddistributionmaster WHERE GrowerId='" + userId + "' AND PlantingYear='" + plantingYear + "'";

            Cursor cursorCourses = myDb.rawQuery(q, null);
           // Log.e("temporary", "isSeedDistributionListDownloaded move to first " + cursorCourses.moveToFirst() + " userId " + userId);
            if (cursorCourses.moveToFirst()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            myDb.close();
        }
    }

    /*Added by Jeevan 9-12-2022 added GrowerId INTEGER
    * and   "'" + storeAreaModel.getGrowerId() + "'," +*/
    public boolean addAreaData(StoreAreaModel storeAreaModel) {
       SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_storestributiondata" +
                    "(" +
                    "" +
                    "plantingYear," +
                    "productionCode," +
                    "FemaleBatchNo," +
                    "MaleBatchNo," +
                    "FemaleParentSeedBatchId," +
                    "MaleParentSeedBatchId," +
                    "ParentSeedReceiptId," +
                    "ParentSeedReceiptType," +
                    "ClusterId," +
                    "GrowerId," +
                    "SeedProductionArea" +
                    ") values" +
                    "('" + storeAreaModel.getPlantingYear() + "'," +
                    "'" + storeAreaModel.getProductionCode() + "'," +
                    "'" + storeAreaModel.getFemaleBatchNo() + "'," +
                    "'" + storeAreaModel.getMaleBatchNo() + "'," +
                    "'" + storeAreaModel.getFemaleParentSeedBatchId() + "'," +
                    "'" + storeAreaModel.getMaleParentSeedBatchId() + "'," +
                    "'" + storeAreaModel.getParentSeedReceiptId() + "'," +
                    "'" + storeAreaModel.getParentSeedReceiptType() + "'," +
                    "'" + storeAreaModel.getClusterId() + "'," +
                    "'" + storeAreaModel.getGrowerId() + "'," +
                    "'" + storeAreaModel.getSeedProductionArea() + "')";
           // Log.e("temporary", "Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
          //  Log.e("temporary","Error is Product Added " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public boolean updateAreaData(String plantingYear , String productionCode,
                               String parentSeedReceiptType, int productionClusterId ,
                               float area) {
        /*Log.e("temporary","updateAreaData plantingYear "+plantingYear
                +" productionCode "+ productionCode+ " parentSeedReceiptType "+ parentSeedReceiptType
                +" productionClusterId "+ productionClusterId);*/
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update tbl_storestributiondata set SeedProductionArea='" + area + "' WHERE PlantingYear='"+plantingYear+
                    "' AND ProductionCode='"+ productionCode+"' AND ProductionClusterId='"+ productionClusterId+
                    "' AND ParentSeedReceiptType='"+parentSeedReceiptType+"'";
           // Log.e("temporary","updateAreaData Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary","updateAreaData Error is  Added Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public Float getStoreFemaleBatchAreaArea(String plantingYear
            , String productionCode, String femaleBatchNo, int femaleBatchId/*, int receiptId, String receiptType,
                                             int clusterId*/){
        SQLiteDatabase myDb = null;
        float sum = 0;
        try {
            myDb = this.getReadableDatabase();
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_storestributiondata WHERE PlantingYear='"+plantingYear
                    +"' AND ProductionCode='"+ productionCode+"' AND FemaleBatchNo='"+ femaleBatchNo+"' AND FemaleParentSeedBatchId='"+ femaleBatchId+"'";
            Cursor cursorCourses = myDb.rawQuery(q1, null);
           // Log.e("temporary", "getStoreArea move to first " + q1);
            if (cursorCourses.moveToFirst()) {
                sum = cursorCourses.getFloat(0);
              //  Log.e("temporary", "getStoreArea sum " + sum);
            }
            return sum;
        } catch (Exception e) {
          //  Log.e("temporary", " Exception e " + e.getCause()+" " + e.getLocalizedMessage());
            return sum;
        } finally {
            myDb.close();
        }
    }

    public Float getStoreMaleBatchAreaArea(String plantingYear
            , String productionCode, String maleBatchNo , int maleBatchId/* , int receiptId, String receiptType,
                                           int clusterId*/){
        SQLiteDatabase myDb = null;
        float sum = 0;
        try {
            myDb = this.getReadableDatabase();
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_storestributiondata WHERE PlantingYear='"+plantingYear
                    +"' AND ProductionCode='"+ productionCode+"' AND MaleBatchNo='"+ maleBatchNo+"' AND MaleParentSeedBatchId='"+ maleBatchId+"'";
            Cursor cursorCourses = myDb.rawQuery(q1, null);
           // Log.e("temporary", "getStoreArea move to first " + q1);
            if (cursorCourses.moveToFirst()) {
                sum = cursorCourses.getFloat(0);
               // Log.e("temporary", "getStoreArea sum " + sum);
            }
            return sum;
        } catch (Exception e) {
           // Log.e("temporary", " Exception e " + e.getCause()+" " + e.getLocalizedMessage());
            return sum;
        } finally {
            myDb.close();
        }
    }

    public boolean deleteAreaData() {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "DELETE from tbl_storestributiondata";
          //  Log.e("temporary", " deleted Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
           // Log.e("temporary", " deleted Error is Clear List " + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }
    }

    public Float totalOfSeedDistribution(String plantingYear
            , String productionCode, String parentSeedReceiptType, int productionClusterId) {
        SQLiteDatabase myDb = null;
        float sum = 0;
        try {
            myDb = this.getReadableDatabase();
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_allseeddistributionmaster WHERE PlantingYear='"+plantingYear
                    +"' AND ProductionCode='"+ productionCode+"' AND ProductionClusterId='"
                    + productionClusterId+"' AND ParentSeedReceiptType='"+parentSeedReceiptType+"'";
            Cursor cursorCourses = myDb.rawQuery(q1, null);
           // Log.e("temporary", "totalOfSeedDistribution move to first " + q1);
            if (cursorCourses.moveToFirst()) {
                sum = cursorCourses.getFloat(0);
               // Log.e("temporary", "sum " + sum);
            }
            return sum;
        } catch (Exception e) {
           // Log.e("temporary", " Exception e " + sum);
            return sum;
        } finally {
            myDb.close();
        }
    }

    public Integer gteStoredDataCount(String plantingYear
            , String productionCode, String parentSeedReceiptType, int productionClusterId) {
        SQLiteDatabase myDb = null;
        int temp = 0;
        try {
            myDb = this.getReadableDatabase();
            String q = "select count(*) from tbl_storestributiondata WHERE PlantingYear='"+plantingYear
                    +"' AND ProductionCode='"+ productionCode+"' AND ProductionClusterId='"+ productionClusterId
                    +"' AND ParentSeedReceiptType='"+parentSeedReceiptType+"'";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            // Log.e("temporary"," gteStoredDataCount move to first "+ cursorCourses.moveToFirst());
            if (cursorCourses.moveToFirst()) {
                 temp = cursorCourses.getInt(0);
               // Log.e("temporary", "temp " + temp);
            }
            return temp;
        } catch (Exception e) {
            return 0;
        } finally {
            myDb.close();
        }
    }
}
