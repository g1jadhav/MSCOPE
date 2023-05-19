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
import mahyco.mipl.nxg.model.FieldVisitModel;
import mahyco.mipl.nxg.model.FieldVisitModel_Server;
import mahyco.mipl.nxg.model.FirstVisitLocalModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.GrowerModel;
import mahyco.mipl.nxg.model.OldGrowerSeedDistributionModel;
import mahyco.mipl.nxg.model.ProductCodeModel;
import mahyco.mipl.nxg.model.ProductionClusterModel;
import mahyco.mipl.nxg.model.ReceiptModel;
import mahyco.mipl.nxg.model.SeasonModel;
import mahyco.mipl.nxg.model.SeedBatchNoModel;
import mahyco.mipl.nxg.model.SeedReceiptModel;
import mahyco.mipl.nxg.model.StoreAreaModel;
import mahyco.mipl.nxg.model.VillageModel;
import mahyco.mipl.nxg.model.VisitDetailCoutModel;


public class SqlightDatabase extends SQLiteOpenHelper {

    final static String DBName = "mipl.db";
    final static int version = 11;
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
                " MaleBatchNo text,\n" +
                " FemaleParentSeedBatchId INTEGER,\n" +
                " MaleParentSeedBatchId INTEGER,\n" +
                " ParentSeedReceiptId INTEGER,\n" +
                " ParentSeedReceiptType text,\n" +
                " ClusterId INTEGER,\n" +
                " GrowerId INTEGER,\n" +
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

        String tbl_firstVisit = "Create table IF NOT EXISTS tbl_firstVisit( \n" +
                "TempID Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "UserId INTEGER,\n" +
                "CountryId INTEGER,\n" +
                "CountryMasterId INTEGER,\n" +
                "MandatoryFieldVisitId INTEGER,\n" +
                "FieldVisitType  TEXT,\n" +
                "TotalSeedAreaLost  TEXT,\n" +
                "TaggedAreaInHA  TEXT,\n" +
                "ExistingAreaInHA  TEXT,\n" +
                "ReasonForTotalLossed  TEXT,\n" +
                "FemaleSowingDt  TEXT,\n" +
                "MaleSowingDt  TEXT,\n" +
                "IsolationM  TEXT,\n" +
                "IsolationMeter  TEXT,\n" +
                "CropStage  TEXT,\n" +
                "TotalNoOfFemaleLines  TEXT,\n" +
                "TotalNoOfMaleLines  TEXT,\n" +
                "FemaleSpacingRRinCM  TEXT,\n" +
                "FemaleSpacingPPinCM  TEXT,\n" +
                "MaleSpacingRRinCM  TEXT,\n" +
                "MaleSpacingPPinCM  TEXT,\n" +
                "PlantingRatioFemale  TEXT,\n" +
                "PlantingRatioMale  TEXT,\n" +
                "CropCategoryType  TEXT,\n" +
                "TotalFemalePlants  TEXT,\n" +
                "TotalMalePlants  TEXT,\n" +
                "YieldEstimateInKg  TEXT,\n" +
                "Observations  TEXT,\n" +
                "FieldVisitDt  TEXT,\n" +
                "Latitude  TEXT,\n" +
                "Longitude  TEXT,\n" +
                "CapturePhoto  TEXT,\n" +
                "CreatedBy  TEXT,\n" +
                "LocationData text,\n" +
                "LineData text,\n" +
                "AreaLossInHa text,\n" +
                "NoOfRoguedFemalePlants  text,\n" +
                "NoOfRoguedMalePlants  text,\n" +
                "SeedProductionMethod  text,\n" +
                "RoguingCompletedValidated  text,\n" +
                "SingleCobsPerPlant  text,\n" +
                "SingleCobsPerPlantInGm  text,\n" +
                "UnprocessedSeedReadyInKg  text,\n" +
                "PollinationStartDt  text,\n" +
                "PollinationEndDt text,\n" +
                "ExpectedDtOfHarvesting  text,\n" +
                "ExpectedDtOfDespatching  text,\n" +
                "MaleParentUprooted text,\n" +
                "fieldVisitRoguedPlantModels text,\n" +
                "fieldVisitFruitsCobModels Text,AreaLossStatus Text," +
                "AverageNoofExistingbolls Text ," +
                "DistanceFromField Text);";

        db.execSQL(tbl_firstVisit);

        String tbl_visit_master = "Create table IF NOT EXISTS tbl_visit_master(\n" +
                "TEMPID INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "FieldVisitId INTEGER,\n" +
                "UserId TEXT,\n" +
                "CountryId TEXT,\n" +
                "CountryMasterId TEXT,\n" +
                "MandatoryFieldVisitId Integer,\n" +
                "FieldVisitType TEXT,\n" +
                "TotalSeedAreaLost TEXT,\n" +
                "TaggedAreaInHA TEXT,\n" +
                "ExistingAreaInHA TEXT,\n" +
                "ReasonForTotalLossed TEXT,\n" +
                "FemaleSowingDt TEXT,\n" +
                "MaleSowingDt TEXT,\n" +
                "IsolationM TEXT,\n" +
                "IsolationMeter TEXT,\n" +
                "CropStage TEXT,\n" +
                "TotalNoOfFemaleLines TEXT,\n" +
                "TotalNoOfMaleLines TEXT,\n" +
                "FemaleSpacingRRinCM TEXT,\n" +
                "FemaleSpacingPPinCM TEXT,\n" +
                "MaleSpacingRRinCM TEXT,\n" +
                "MaleSpacingPPinCM TEXT,\n" +
                "PlantingRatioFemale TEXT,\n" +
                "PlantingRatioMale TEXT,\n" +
                "CropCategoryType TEXT,\n" +
                "TotalFemalePlants TEXT,\n" +
                "TotalMalePlants TEXT,\n" +
                "YieldEstimateInKg TEXT,\n" +
                "Observations TEXT,\n" +
                "FieldVisitDt TEXT,\n" +
                "Latitude TEXT,\n" +
                "Longitude TEXT,\n" +
                "CapturePhoto TEXT,\n" +
                "CreatedBy TEXT,\n" +
                "CreatedDt TEXT,AreaLossStatus Text)";

        db.execSQL(tbl_visit_master);

        String tbl_focusvillage_master = "Create Table if not Exists tbl_focusedvillage (\n" +
                "\n" +
                " TempId INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
                "\tVillageAllocationId INTEGER,\n" +
                "    LoginId INTEGER,\n" +
                "    CountryId INTEGER,\n" +
                "    CountryMasterId INTEGER,\n" +
                "    PLantingYear INTEGER,\n" +
                "    IsDelete TEXT,\n" +
                "    CreatedBy TEXT,\n" +
                "    CreatedDt TEXT,\n" +
                "    ModifiedBy TEXT,\n" +
                "    ModifiedDt TEXT,\n" +
                "    CountryName TEXT,\n" +
                "    CountryCode TEXT,\n" +
                "    Village TEXT,\n" +
                "    VillageCode TEXT,\n" +
                "    Section TEXT,\n" +
                "    SectionCode TEXT,\n" +
                "    EPA TEXT,\n" +
                "    EPACode TEXT,\n" +
                "    District TEXT,\n" +
                "    DistrictCode TEXT,\n" +
                "    ADDNAME TEXT,\n" +
                "    ADDCode TEXT,\n" +
                "    UserName TEXT,\n" +
                "    UserCode TEXT,\n" +
                "    NoofGrowers TEXT,\n" +
                "    AreaHAC TEXT\n" +
                ");";

        db.execSQL(tbl_focusvillage_master);

        String tbl_receiptmaster = "Create table tbl_seedreceipt (\n" +
                "     TEMPID INTEGER PRIMARY Key AUTOINCREMENT,\n" +
                "GrowerId  TEXT,\n" +
                "     GrowerName  TEXT,\n" +
                "     issued_seed_area  TEXT,\n" +
                "     production_code  TEXT,\n" +
                "     village  TEXT,\n" +
                "     existing_area  TEXT,\n" +
                "     area_loss  TEXT,\n" +
                "     reason_for_area_loss  TEXT,\n" +
                "     yeildinkg  TEXT,\n" +
                "     batchno  TEXT,\n" +
                "     noofbags  TEXT,\n" +
                "     weightinkg  TEXT,\n" +
                "     serviceprovider  TEXT,\n" +
                "     grower_mobile_no_edittext  TEXT,\n" +
                "     date_of_field_visit_textview  TEXT,\n" +
                "     staff_name_textview  TEXT,\n" +
                "     StaffID  TEXT\n" +
                "\t)";

        db.execSQL(tbl_receiptmaster);

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
        droptable(db, "tbl_seedreceipt");
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

    public boolean addFocusVillage(VillageModel f) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_focusedvillage" +
                    "(VillageAllocationId,LoginId,CountryId,CountryMasterId,PLantingYear,IsDelete,CreatedBy,CreatedDt,ModifiedBy,ModifiedDt,CountryName,CountryCode,Village,VillageCode,Section,SectionCode,EPA,EPACode,District,DistrictCode,ADDNAME,ADDCode,UserName,UserCode,NoofGrowers,AreaHAC) values" +
                    "('" + f.getVillageAllocationId() + "',\n" +
                    "'" + f.getLoginId() + "',\n" +
                    "'" + f.getCountryId() + "',\n" +
                    "'" + f.getCountryMasterId() + "',\n" +
                    "'" + f.getPLantingYear() + "',\n" +
                    "'" + f.isDelete() + "',\n" +
                    "'" + f.getCreatedBy() + "',\n" +
                    "'" + f.getCreatedDt() + "',\n" +
                    "'" + f.getModifiedBy() + "',\n" +
                    "'" + f.getModifiedDt() + "',\n" +
                    "'" + f.getCountryName() + "',\n" +
                    "'" + f.getCountryCode() + "',\n" +
                    "'" + f.getVillage() + "',\n" +
                    "'" + f.getVillageCode() + "',\n" +
                    "'" + f.getSection() + "',\n" +
                    "'" + f.getSectionCode() + "',\n" +
                    "'" + f.getEPA() + "'," +
                    "'" + f.getEPACode() + "',\n" +
                    "'" + f.getDistrict() + "',\n" +
                    "'" + f.getDistrictCode() + "',\n" +
                    "'" + f.getADD() + "',\n" +
                    "'" + f.getADDCode() + "',\n" +
                    "'" + f.getUserName() + "',\n" +
                    "'" + f.getUserCode() + "',\n" +
                    "'" + f.getNoofGrowers() + "',\n" +
                    "'" + f.getAreaHAC() + "')";
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

    public boolean addFirstVisit1(FieldVisitModel visitModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_firstVisit" +
                    "(UserId, CountryId, CountryMasterId, MandatoryFieldVisitId, FieldVisitType, TotalSeedAreaLost, TaggedAreaInHA, ExistingAreaInHA, ReasonForTotalLossed, FemaleSowingDt, MaleSowingDt, IsolationM, IsolationMeter, CropStage, TotalNoOfFemaleLines, TotalNoOfMaleLines, FemaleSpacingRRinCM, FemaleSpacingPPinCM, MaleSpacingRRinCM, MaleSpacingPPinCM, PlantingRatioFemale, PlantingRatioMale, CropCategoryType, TotalFemalePlants, TotalMalePlants, YieldEstimateInKg, Observations, FieldVisitDt, Latitude, Longitude, CapturePhoto, CreatedBy, LocationData, LineData,AreaLossInHa,NoOfRoguedFemalePlants,NoOfRoguedMalePlants,SeedProductionMethod,RoguingCompletedValidated,SingleCobsPerPlant,SingleCobsPerPlantInGm,UnprocessedSeedReadyInKg,PollinationStartDt,PollinationEndDt,ExpectedDtOfHarvesting,ExpectedDtOfDespatching,MaleParentUprooted,fieldVisitRoguedPlantModels,fieldVisitFruitsCobModels,AreaLossStatus,AverageNoofExistingbolls,DistanceFromField) values" +
                    "('" + visitModel.getUserId() + "','" + visitModel.getCountryId() + "','" + visitModel.getCountryMasterId() + "','" + visitModel.getMandatoryFieldVisitId() + "','" + visitModel.getFieldVisitType() + "','" + visitModel.getTotalSeedAreaLost() + "','" + visitModel.getTaggedAreaInHA() + "','" + visitModel.getExistingAreaInHA() + "','" + visitModel.getReasonForTotalLossed() + "','" + visitModel.getFemaleSowingDt() + "','" + visitModel.getMaleSowingDt() + "','" + visitModel.getIsolationM() + "','" + visitModel.getIsolationMeter() + "','" + visitModel.getCropStage() + "','" + visitModel.getTotalNoOfFemaleLines() + "','" + visitModel.getTotalNoOfMaleLines() + "','" + visitModel.getFemaleSpacingRRinCM() + "','" + visitModel.getFemaleSpacingPPinCM() + "','" + visitModel.getMaleSpacingRRinCM() + "','" + visitModel.getMaleSpacingPPinCM() + "','" + visitModel.getPlantingRatioFemale() + "','" + visitModel.getPlantingRatioMale() + "','" + visitModel.getCropCategoryType() + "','" + visitModel.getTotalFemalePlants() + "','" + visitModel.getTotalMalePlants() + "','" + visitModel.getYieldEstimateInKg() + "','" + visitModel.getObservations() + "','" + visitModel.getFieldVisitDt() + "','" + visitModel.getLatitude() + "','" + visitModel.getLongitude() + "','" + visitModel.getCapturePhoto() + "','" + visitModel.getCreatedBy() + "','" + visitModel.getLocationData() + "','" + visitModel.getLineData() + "','" + visitModel.getAreaLossInHa() + "','" + visitModel.getNoOfRoguedFemalePlants() + "','" + visitModel.getNoOfRoguedMalePlants() + "','" + visitModel.getSeedProductionMethod() + "','" + visitModel.getRoguingCompletedValidated() + "','" + visitModel.getSingleCobsPerPlant() + "','" + visitModel.getSingleCobsPerPlantInGm() + "','" + visitModel.getUnprocessedSeedReadyInKg() + "','" + visitModel.getPollinationStartDt() + "','" + visitModel.getPollinationEndDt() + "','" + visitModel.getExpectedDtOfHarvesting() + "','" + visitModel.getExpectedDtOfDespatching() + "','" + visitModel.getMaleParentUprooted() + "','" + visitModel.getFieldVisitRoguedPlantModels() + "','" + visitModel.getFieldVisitFruitsCobModelsText() + "','" + visitModel.getAreaLossStatus() + "','" + visitModel.getAverageNoofExistingbolls() + "','" + visitModel.getDistanceFromField() + "')";
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

    public boolean addFirstVisit(FirstVisitLocalModel firstVisitLocalModel) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_firstVisit" +
                    "(" +
                    "UserId," +
                    "PhotoPath," +
                    "Data" +
                    ") values" +
                    "(" + firstVisitLocalModel.getUserid() + "," +
                    "'" + firstVisitLocalModel.getPath() + "'," +
                    "'" + firstVisitLocalModel.getData() + "')";
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

    public String getCount() {
        SQLiteDatabase myDb = null;
        try {
            int cnt1 = 0, cnt2 = 0, cnt3 = 0, cnt4 = 0, cnt0 = 0;
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_firstVisit";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FieldVisitModel> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    int id = cursorCourses.getInt(4);
                    switch (id) {
                        case 1:
                            cnt1++;
                            break;
                        case 2:
                            cnt2++;
                            break;
                        case 3:
                            cnt3++;
                            break;
                        case 4:
                            cnt4++;
                            break;
                        case 0:
                            cnt0++;
                            break;
                    }
                } while (cursorCourses.moveToNext());
            }
            return cnt1 + "~" + cnt2 + "~" + cnt3 + "~" + cnt4 + "~" + cnt0;
        } catch (Exception e) {
            return "0~0~0~0~0";
        } finally {
            myDb.close();
        }
    }

    public int getCount(int visitID, int userid) {
        SQLiteDatabase myDb = null;
        try {
            int cnt = 0;
            myDb = this.getReadableDatabase();
            String q = "SELECT  count(*)as cnt FROM tbl_firstVisit where MandatoryFieldVisitId=" + visitID + " and UserId=" + userid;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cnt = cursorCourses.getInt(0);
            }
            return cnt;
        } catch (Exception e) {
            return 0;
        } finally {
            myDb.close();
        }
    }

    public int getCountServer(int visitID, int userid) {
        SQLiteDatabase myDb = null;
        try {
            int cnt = 0;
            myDb = this.getReadableDatabase();
            String q = "SELECT  count(*)as cnt FROM tbl_visit_master where MandatoryFieldVisitId=" + visitID + " and UserId=" + userid;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cnt = cursorCourses.getInt(0);
            }
            return cnt;
        } catch (Exception e) {
            return 0;
        } finally {
            myDb.close();
        }
    }

    public VisitDetailCoutModel getCountServerObject(int userid) {
        SQLiteDatabase myDb = null;
        VisitDetailCoutModel visitDetailCoutModel = new VisitDetailCoutModel();
        try {
            int cnt = 0;
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_visit_master where UserId=" + userid + " and MandatoryFieldVisitId>0 order by FieldVisitId  desc  LIMIT 1";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cnt = cursorCourses.getInt(0);

                visitDetailCoutModel.setTempid(cursorCourses.getInt(0));
                visitDetailCoutModel.setUserId(cursorCourses.getString(2));
                visitDetailCoutModel.setGrowerName(cursorCourses.getString(0));
                visitDetailCoutModel.setGrowerNationalId(cursorCourses.getString(0));
                visitDetailCoutModel.setGrowerMobile(cursorCourses.getString(35)); // Storing Observations Status
                visitDetailCoutModel.setGrowerIssuedArea(cursorCourses.getString(0));
                visitDetailCoutModel.setGrowerExistingArea(cursorCourses.getString(9));
                visitDetailCoutModel.setVisitDate(cursorCourses.getString(34));
                visitDetailCoutModel.setVisitedBy(cursorCourses.getString(33));
                visitDetailCoutModel.setVisitID(cursorCourses.getInt(5));
                visitDetailCoutModel.setIsAreaLossStatus(0);
            }
            return visitDetailCoutModel;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<FieldVisitModel> getAllFirstFieldVisit1() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_firstVisit";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FieldVisitModel> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    FieldVisitModel f = new FieldVisitModel();
                    f.setUserId(cursorCourses.getInt(1));
                    f.setCountryId(cursorCourses.getInt(2));
                    f.setCountryMasterId(cursorCourses.getInt(3));
                    f.setMandatoryFieldVisitId(cursorCourses.getInt(4));
                    f.setFieldVisitType(cursorCourses.getString(5));
                    f.setTotalSeedAreaLost(cursorCourses.getDouble(6));
                    f.setTaggedAreaInHA(cursorCourses.getDouble(7));
                    f.setExistingAreaInHA(cursorCourses.getDouble(8));
                    f.setReasonForTotalLossed(cursorCourses.getString(9));
                    f.setFemaleSowingDt(cursorCourses.getString(10));
                    f.setMaleSowingDt(cursorCourses.getString(11));
                    f.setIsolationM(cursorCourses.getString(12));
                    f.setIsolationMeter(cursorCourses.getInt(13));
                    f.setCropStage(cursorCourses.getString(14));
                    f.setTotalNoOfFemaleLines(cursorCourses.getInt(15));
                    f.setTotalNoOfMaleLines(cursorCourses.getInt(16));
                    f.setFemaleSpacingRRinCM(cursorCourses.getInt(17));
                    f.setFemaleSpacingPPinCM(cursorCourses.getInt(18));
                    f.setMaleSpacingRRinCM(cursorCourses.getInt(19));
                    f.setMaleSpacingPPinCM(cursorCourses.getInt(20));
                    f.setPlantingRatioFemale(cursorCourses.getInt(21));
                    f.setPlantingRatioMale(cursorCourses.getInt(22));
                    f.setCropCategoryType(cursorCourses.getString(23));
                    f.setTotalFemalePlants(cursorCourses.getInt(24));
                    f.setTotalMalePlants(cursorCourses.getInt(25));
                    f.setYieldEstimateInKg(cursorCourses.getInt(26));
                    f.setObservations(cursorCourses.getString(27));
                    f.setFieldVisitDt(cursorCourses.getString(28));
                    f.setLatitude(cursorCourses.getString(29));
                    f.setLongitude(cursorCourses.getString(30));
                    f.setCapturePhoto(cursorCourses.getString(31));
                    f.setCreatedBy(cursorCourses.getString(32));
                    f.setLocationData(cursorCourses.getString(33));
                    f.setLineData(cursorCourses.getString(34));
                    f.setAreaLossInHa(cursorCourses.getString(35));
                    f.setNoOfRoguedFemalePlants(cursorCourses.getString(36));
                    f.setNoOfRoguedMalePlants(cursorCourses.getString(37));
                    f.setSeedProductionMethod(cursorCourses.getString(38));
                    f.setRoguingCompletedValidated(cursorCourses.getString(39));
                    f.setSingleCobsPerPlant(cursorCourses.getString(40));
                    f.setSingleCobsPerPlantInGm(cursorCourses.getString(41));
                    f.setUnprocessedSeedReadyInKg(cursorCourses.getString(42));
                    f.setPollinationStartDt(cursorCourses.getString(43));
                    f.setPollinationEndDt(cursorCourses.getString(44));
                    f.setExpectedDtOfHarvesting(cursorCourses.getString(45));
                    f.setExpectedDtOfDespatching(cursorCourses.getString(46));
                    f.setMaleParentUprooted(cursorCourses.getString(47));
                    f.setFieldVisitRoguedPlantModels(cursorCourses.getString(48));
                    f.setFieldVisitFruitsCobModelsText(cursorCourses.getString(49));
                    f.setAreaLossStatus(cursorCourses.getString(50));
                    f.setAverageNoofExistingbolls(cursorCourses.getString(51));
                    f.setDistanceFromField(cursorCourses.getString(52));

                    fieldLocationArrayList.add(f);
                } while (cursorCourses.moveToNext());
            }
            return fieldLocationArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<ReceiptModel> getAllSeedReceipt() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_seedreceipt";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<ReceiptModel> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    ReceiptModel f = new ReceiptModel();
                    f.setTempId(cursorCourses.getInt(0));
                    f.setGrowerId(cursorCourses.getString(1));
                    f.setGrowerName(cursorCourses.getString(2));
                    f.setIssued_seed_area(cursorCourses.getString(3));
                    f.setProduction_code(cursorCourses.getString(4));
                    f.setVillage(cursorCourses.getString(5));
                    f.setExisting_area(cursorCourses.getString(6));
                    f.setArea_loss(cursorCourses.getString(7));
                    f.setReason_for_area_loss(cursorCourses.getString(8));
                    f.setYeildinkg(cursorCourses.getString(9));
                    f.setBatchno(cursorCourses.getString(10));
                    f.setNoofbags(cursorCourses.getString(11));
                    f.setWeightinkg(cursorCourses.getString(12));
                    f.setServiceprovider(cursorCourses.getString(13));
                    f.setGrower_mobile_no_edittext(cursorCourses.getString(14));
                    f.setDate_of_field_visit_textview(cursorCourses.getString(15));
                    f.setStaff_name_textview(cursorCourses.getString(16));
                    f.setStaffID(cursorCourses.getString(17));



                    fieldLocationArrayList.add(f);
                } while (cursorCourses.moveToNext());
            }
            return fieldLocationArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<String> getAllDistinctProductionCode() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "select distinct ProductionCode from tbl_allseeddistributionmaster";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<String> lst_productioncode = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    lst_productioncode.add(cursorCourses.getString(0));
                } while (cursorCourses.moveToNext());
            }
            return lst_productioncode;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<VillageModel> getAllFocusVillage() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_focusedvillage";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<VillageModel> focuseVillageList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    VillageModel f = new VillageModel();
                    f.setVillageAllocationId(cursorCourses.getInt(1));
                    f.setLoginId(cursorCourses.getInt(2));
                    f.setCountryId(cursorCourses.getInt(3));
                    f.setCountryMasterId(cursorCourses.getInt(4));
                    f.setPLantingYear(cursorCourses.getInt(5));
                    //f.isDelete=(false);
                    f.setCreatedBy(cursorCourses.getString(7));
                    f.setCreatedDt(cursorCourses.getString(8));
                    f.setModifiedBy(cursorCourses.getString(9));
                    f.setModifiedDt(cursorCourses.getString(10));
                    f.setCountryName(cursorCourses.getString(11));
                    f.setCountryCode(cursorCourses.getString(12));
                    f.setVillage(cursorCourses.getString(13));
                    f.setVillageCode(cursorCourses.getString(14));
                    f.setSection(cursorCourses.getString(15));
                    f.setSectionCode(cursorCourses.getString(16));
                    f.setEPA(cursorCourses.getString(17));
                    f.setEPACode(cursorCourses.getString(18));
                    f.setDistrict(cursorCourses.getString(19));
                    f.setDistrictCode(cursorCourses.getString(20));
                    f.setADD(cursorCourses.getString(21));
                    f.setADDCode(cursorCourses.getString(22));
                    f.setUserName(cursorCourses.getString(23));
                    f.setUserCode(cursorCourses.getString(24));
                    f.setNoofGrowers(cursorCourses.getInt(25));
                    f.setAreaHAC(cursorCourses.getInt(26));

                    focuseVillageList.add(f);
                } while (cursorCourses.moveToNext());
            }
            return focuseVillageList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<FirstVisitLocalModel> getAllFirstFieldVisit() {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_firstVisit";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FirstVisitLocalModel> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    FirstVisitLocalModel f = new FirstVisitLocalModel();
                    f.setUserid(cursorCourses.getInt(1));
                    f.setPath(cursorCourses.getString(2));
                    f.setData(cursorCourses.getString(3));

                    fieldLocationArrayList.add(f);
                } while (cursorCourses.moveToNext());
            }
            return fieldLocationArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public boolean updateFieldMaster(int fieldid, String total) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update tbl_field_master set TotalArea='" + total + "' where FieldId=" + fieldid;
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
                    FieldMaster fieldLocation = new FieldMaster();
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
            String q;
            if (fieldId == -1)
                q = "SELECT  * FROM tbl_field_location";
            else
                q = "SELECT  * FROM tbl_field_location where FieldId=" + fieldId;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<FieldLocation> fieldLocationArrayList = new ArrayList<>();
            if (cursorCourses.moveToFirst()) {
                do {
                    FieldLocation fieldLocation = new FieldLocation();
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
            String q = "";
            if (userType == 1)
                q = "Delete from tbl_registrationmaster where UserType='Grower'";
            else
                q = "Delete from tbl_registrationmaster where UserType='Organizer'";
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
                    String b = isFirstFieldVisitDone(cursorCourses.getInt(2));
                    String ss = "";
                    if (!b.equals("0")) {
                        ss = b + " Visit.";
                    }
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
                            cursorCourses.getString(25) + "(" + ss + ")",
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

    public ArrayList<GetAllSeedDistributionModel> getAllSeedDistributionListNo(String value, String pcode) {
        SQLiteDatabase myDb = null;
        try {
            Log.i("Query", value + "-->" + pcode);
            myDb = this.getReadableDatabase();
            String q = "select * from tbl_allseeddistributionmaster";
            if (value.trim().equals("")) {
                q = "select * from tbl_allseeddistributionmaster where (select count(*) from tbl_focusedvillage where CountryMasterId=(select countryMasterid from tbl_growermaster where UserId=GrowerId))>0";
            } else {
                q = "select * from tbl_allseeddistributionmaster where (select count(*) from tbl_focusedvillage where CountryMasterId=(select countryMasterid from tbl_growermaster where UserId=GrowerId))>0 and upper((select LandMark from tbl_growermaster where UserId=GrowerId limit 1)) like '" + value.toLowerCase() + ",%'";
            }
            if (pcode.trim().equals("")) {

            } else {
                q += " and ProductionCode='" + pcode + "'";
               /* if(value.trim().equals(""))
                    q+= " and ProductionCode='"+pcode+"'";
                else
                    q+= " and ProductionCode='"+pcode+"'";*/
            }
            Log.i("Query", "-->" + q);
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<GetAllSeedDistributionModel> courseModalArrayList = new ArrayList<>();
            int srno = 1;
            if (cursorCourses.moveToFirst()) {
                do {
                    String b = isFirstFieldVisitDone(cursorCourses.getInt(2));
                    String b1 = isFirstFieldVisitDoneLocal(cursorCourses.getInt(2));
                    String ss = "";
                    if (!b.equals("0")) {
                        ss = getSuperscript(b) + " Visit.";
                    }
                    if (!b1.equals("0")) {
                        ss = getSuperscript(b1) + " Visit.";
                    }
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
                            srno + "-" + cursorCourses.getInt(2) + " " + cursorCourses.getString(25) + "(" + ss + ")",
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
                    srno++;
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    public ArrayList<GetAllSeedDistributionModel> getAllSeedDistributionListForReceipt(String value, String pcode) {
        SQLiteDatabase myDb = null;
        try {
            Log.i("Query Receipt", value + "-->" + pcode);
            myDb = this.getReadableDatabase();
            String q = "select * from tbl_allseeddistributionmaster a where (select count(*) from tbl_visit_master where MandatoryFieldVisitId = 3 and AreaLossStatus= 'No' and UserId=a.GrowerId)>0";
            if (value.trim().equals("")) {
                q = "select * from tbl_allseeddistributionmaster a where (select count(*) from tbl_visit_master where MandatoryFieldVisitId = 3 and AreaLossStatus= 'No' and UserId=a.GrowerId)>0 and (select count(*) from tbl_focusedvillage where CountryMasterId=(select countryMasterid from tbl_growermaster where UserId=GrowerId))>0";
            } else {
                q = "select * from tbl_allseeddistributionmaster a where (select count(*) from tbl_visit_master where MandatoryFieldVisitId = 3 and AreaLossStatus= 'No' and UserId=a.GrowerId)>0  and (select count(*) from tbl_focusedvillage where CountryMasterId=(select countryMasterid from tbl_growermaster where UserId=GrowerId))>0 and upper((select LandMark from tbl_growermaster where UserId=GrowerId limit 1)) like '" + value.toLowerCase() + ",%'";
            }
            if (pcode.trim().equals("")) {

            } else {
                q += " and ProductionCode='" + pcode + "'";
               /* if(value.trim().equals(""))
                    q+= " and ProductionCode='"+pcode+"'";
                else
                    q+= " and ProductionCode='"+pcode+"'";*/
            }

            Log.i("Query", "-->" + q);
            Cursor cursorCourses = myDb.rawQuery(q, null);
            ArrayList<GetAllSeedDistributionModel> courseModalArrayList = new ArrayList<>();
            int srno = 1;
            if (cursorCourses.moveToFirst()) {
                do {
                    String b = isFirstFieldVisitDone(cursorCourses.getInt(2));
                    String b1 = isFirstFieldVisitDoneLocal(cursorCourses.getInt(2));
                    String ss = "";
                    if (!b.equals("0")) {
                        ss = getSuperscript(b) + " Visit.";
                    }
                    if (!b1.equals("0")) {
                        ss = getSuperscript(b1) + " Visit.";
                    }
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
                            srno + "-" + cursorCourses.getInt(2) + " " + cursorCourses.getString(25) + "(" + ss + ")",
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
                    srno++;
                } while (cursorCourses.moveToNext());
            }
            return courseModalArrayList;
        } catch (Exception e) {
            return null;
        } finally {
            myDb.close();
        }
    }

    String getSuperscript(String a) {
        int k = 0;
        String str = "";
        try {
            k = Integer.parseInt(a.trim());
        } catch (NumberFormatException e) {

        }
        switch (k) {
            case 1:
                str = "1st";
                break;

            case 2:
                str = "2nd";
                break;

            case 3:
                str = "3rd";
                break;

            case 4:
                str = "4th";
                break;

        }
        return str;
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

    public String getGetGrowerCountryMasterId(String uniqueid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "select CountryMasterId,LandMark from tbl_growermaster where UniqueCode='" + uniqueid + "'";
            Cursor cursorCourses = myDb.rawQuery(q, null);

            if (cursorCourses.moveToFirst()) {
                Log.i("QQ", q + "" + cursorCourses.getString(0));
                cursorCourses.getString(0);
                return cursorCourses.getString(0) + "~" + cursorCourses.getString(1);
            } else {
                return " ~ ";

            }

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

    public boolean addVisitMaster(FieldVisitModel_Server f) {
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_visit_master" +
                    "(FieldVisitId," +
                    "UserId," +
                    "CountryId," +
                    "CountryMasterId," +
                    "MandatoryFieldVisitId," +
                    "FieldVisitType," +
                    "TotalSeedAreaLost," +
                    "TaggedAreaInHA," +
                    "ExistingAreaInHA," +
                    "ReasonForTotalLossed," +
                    "FemaleSowingDt," +
                    "MaleSowingDt," +
                    "IsolationM," +
                    "IsolationMeter," +
                    "CropStage," +
                    "TotalNoOfFemaleLines," +
                    "TotalNoOfMaleLines," +
                    "FemaleSpacingRRinCM," +
                    "FemaleSpacingPPinCM," +
                    "MaleSpacingRRinCM," +
                    "MaleSpacingPPinCM," +
                    "PlantingRatioFemale," +
                    "PlantingRatioMale," +
                    "CropCategoryType," +
                    "TotalFemalePlants," +
                    "TotalMalePlants," +
                    "YieldEstimateInKg," +
                    "Observations," +
                    "FieldVisitDt," +
                    "Latitude," +
                    "Longitude," +
                    "CapturePhoto," +
                    "CreatedBy," +
                    "CreatedDt,AreaLossStatus) values" +
                    "('" + f.getFieldVisitId() + "','" + f.getUserId() + "','" + f.getCountryId() + "','" + f.getCountryMasterId() + "','" + f.getMandatoryFieldVisitId() + "','" + f.getFieldVisitType() + "','" + f.getTotalSeedAreaLost() + "','" + f.getTaggedAreaInHA() + "','" + f.getExistingAreaInHA() + "','" + f.getReasonForTotalLossed() + "','" + f.getFemaleSowingDt() + "','" + f.getMaleSowingDt() + "','" + f.getIsolationM() + "','" + f.getIsolationMeter() + "','" + f.getCropStage() + "','" + f.getTotalNoOfFemaleLines() + "','" + f.getTotalNoOfMaleLines() + "','" + f.getFemaleSpacingRRinCM() + "','" + f.getFemaleSpacingPPinCM() + "','" + f.getMaleSpacingRRinCM() + "','" + f.getMaleSpacingPPinCM() + "','" + f.getPlantingRatioFemale() + "','" + f.getPlantingRatioMale() + "','" + f.getCropCategoryType() + "','" + f.getTotalFemalePlants() + "','" + f.getTotalMalePlants() + "','" + f.getYieldEstimateInKg() + "','" + f.getObservations() + "','" + f.getFieldVisitDt() + "','" + f.getLatitude() + "','" + f.getLongitude() + "','" + f.getCapturePhoto() + "','" + f.getCreatedBy() + "','" + f.getCreatedDt() + "','" + f.getAreaLossStatus() + "')";
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

    public String isFirstFieldVisitDone(int userid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  MandatoryFieldVisitId FROM tbl_visit_master where UserId=" + userid + " order by MandatoryFieldVisitId desc";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cursorCourses.getString(0);
                return cursorCourses.getString(0);
            }
            return "0";
        } catch (Exception e) {
            return "0";
        } finally {
            myDb.close();
        }
    }

    public boolean isMandetoryVisitDone(int userid, int visitid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  MandatoryFieldVisitId FROM tbl_firstVisit where UserId=" + userid + " and MandatoryFieldVisitId=" + visitid + " order by MandatoryFieldVisitId desc";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cursorCourses.getString(0);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            myDb.close();
        }
    }

    public String isFirstFieldVisitDoneLocal(int userid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "SELECT  MandatoryFieldVisitId FROM tbl_firstVisit where UserId=" + userid + " order by MandatoryFieldVisitId desc";
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                return cursorCourses.getString(0);
            }
            return "0";
        } catch (Exception e) {
            return "0";
        } finally {
            myDb.close();
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

    public FieldVisitModel_Server getVisitDetailsById(int userid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "Select TEMPID,FieldVisitId," +
                    "UserId," +
                    "CountryId," +
                    "CountryMasterId," +
                    "MandatoryFieldVisitId," +
                    "FieldVisitType," +
                    "TotalSeedAreaLost," +
                    "TaggedAreaInHA," +
                    "ExistingAreaInHA," +
                    "ReasonForTotalLossed," +
                    "FemaleSowingDt," +
                    "MaleSowingDt," +
                    "IsolationM," +
                    "IsolationMeter," +
                    "CropStage," +
                    "TotalNoOfFemaleLines," +
                    "TotalNoOfMaleLines," +
                    "FemaleSpacingRRinCM," +
                    "FemaleSpacingPPinCM," +
                    "MaleSpacingRRinCM," +
                    "MaleSpacingPPinCM," +
                    "PlantingRatioFemale," +
                    "PlantingRatioMale," +
                    "CropCategoryType," +
                    "TotalFemalePlants," +
                    "TotalMalePlants," +
                    "YieldEstimateInKg," +
                    "Observations," +
                    "FieldVisitDt," +
                    "Latitude," +
                    "Longitude," +
                    "CapturePhoto," +
                    "CreatedBy," +  //UserId="+userid+" and MandatoryFieldVisitId>0 order by FieldVisitId
                    "CreatedDt from tbl_visit_master where UserId=" + userid + "  and MandatoryFieldVisitId>0 order by FieldVisitId desc";
            Log.i("query", q);
            Cursor cursorCourses = myDb.rawQuery(q, null);
            FieldVisitModel_Server m = new FieldVisitModel_Server();
            if (cursorCourses.moveToFirst()) {
                m.setFieldVisitId(cursorCourses.getInt(1));
                m.setUserId(cursorCourses.getInt(2));
                m.setCountryId(cursorCourses.getInt(3));
                m.setCountryMasterId(cursorCourses.getInt(4));
                m.setMandatoryFieldVisitId(cursorCourses.getInt(5));
                m.setFieldVisitType(cursorCourses.getString(6));
                m.setTotalSeedAreaLost(cursorCourses.getDouble(7));
                m.setTaggedAreaInHA(cursorCourses.getDouble(8));
                m.setExistingAreaInHA(Double.parseDouble(cursorCourses.getString(9).trim()));
                m.setReasonForTotalLossed(cursorCourses.getString(10));
                m.setFemaleSowingDt(cursorCourses.getString(11));
                m.setMaleSowingDt(cursorCourses.getString(12));
                m.setIsolationM(cursorCourses.getString(13));
                m.setIsolationMeter(cursorCourses.getInt(14));
                m.setCropStage(cursorCourses.getString(15));
                m.setTotalNoOfFemaleLines(cursorCourses.getInt(16));
                m.setTotalNoOfMaleLines(cursorCourses.getInt(17));
                m.setFemaleSpacingRRinCM(cursorCourses.getInt(18));
                m.setFemaleSpacingPPinCM(cursorCourses.getInt(19));
                m.setMaleSpacingRRinCM(cursorCourses.getInt(20));
                m.setMaleSpacingPPinCM(cursorCourses.getInt(21));
                m.setPlantingRatioFemale(cursorCourses.getInt(22));
                m.setPlantingRatioMale(cursorCourses.getInt(23));
                m.setCropCategoryType(cursorCourses.getString(24));
                m.setTotalFemalePlants(cursorCourses.getInt(25));
                m.setTotalMalePlants(cursorCourses.getInt(26));
                m.setYieldEstimateInKg(cursorCourses.getInt(27));
                m.setObservations(cursorCourses.getString(28));
                m.setFieldVisitDt(cursorCourses.getString(29));
                m.setLatitude(cursorCourses.getString(30));
                m.setLongitude(cursorCourses.getString(31));
                m.setCapturePhoto(cursorCourses.getString(32));
                m.setCreatedBy(cursorCourses.getString(33));
                m.setCreatedDt(cursorCourses.getString(34));
            }
            return m;
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
                    "'" + categoryModel.getAddr() + "," + categoryModel.getLandMark() + "'," +
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

    public boolean updateAreaData(String plantingYear, String productionCode,
                                  String parentSeedReceiptType, int productionClusterId,
                                  float area) {
        /*Log.e("temporary","updateAreaData plantingYear "+plantingYear
                +" productionCode "+ productionCode+ " parentSeedReceiptType "+ parentSeedReceiptType
                +" productionClusterId "+ productionClusterId);*/
        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update tbl_storestributiondata set SeedProductionArea='" + area + "' WHERE PlantingYear='" + plantingYear +
                    "' AND ProductionCode='" + productionCode + "' AND ProductionClusterId='" + productionClusterId +
                    "' AND ParentSeedReceiptType='" + parentSeedReceiptType + "'";
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
                                             int clusterId*/) {
        SQLiteDatabase myDb = null;
        float sum = 0;
        try {
            myDb = this.getReadableDatabase();
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_storestributiondata WHERE PlantingYear='" + plantingYear
                    + "' AND ProductionCode='" + productionCode + "' AND FemaleBatchNo='" + femaleBatchNo + "' AND FemaleParentSeedBatchId='" + femaleBatchId + "'";
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
            , String productionCode, String maleBatchNo, int maleBatchId/* , int receiptId, String receiptType,
                                           int clusterId*/) {
        SQLiteDatabase myDb = null;
        float sum = 0;
        try {
            myDb = this.getReadableDatabase();
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_storestributiondata WHERE PlantingYear='" + plantingYear
                    + "' AND ProductionCode='" + productionCode + "' AND MaleBatchNo='" + maleBatchNo + "' AND MaleParentSeedBatchId='" + maleBatchId + "'";
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
            String q1 = "SELECT SUM(SeedProductionArea) FROM tbl_allseeddistributionmaster WHERE PlantingYear='" + plantingYear
                    + "' AND ProductionCode='" + productionCode + "' AND ProductionClusterId='"
                    + productionClusterId + "' AND ParentSeedReceiptType='" + parentSeedReceiptType + "'";
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
            String q = "select count(*) from tbl_storestributiondata WHERE PlantingYear='" + plantingYear
                    + "' AND ProductionCode='" + productionCode + "' AND ProductionClusterId='" + productionClusterId
                    + "' AND ParentSeedReceiptType='" + parentSeedReceiptType + "'";
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

    public boolean addReceiptDetails(ReceiptModel receiptModel) {


        SQLiteDatabase mydb = null;
        try {
            String str = "insert into tbl_seedreceipt( GrowerId,\n" +
                    " GrowerName,\n" +
                    " issued_seed_area,\n" +
                    " production_code,\n" +
                    " village,\n" +
                    " existing_area,\n" +
                    " area_loss,\n" +
                    " reason_for_area_loss,\n" +
                    " yeildinkg,\n" +
                    " batchno,\n" +
                    " noofbags,\n" +
                    " weightinkg,\n" +
                    " serviceprovider,\n" +
                    " grower_mobile_no_edittext,\n" +
                    " date_of_field_visit_textview,\n" +
                    " staff_name_textview,\n" +
                    " StaffID) Values " +
                    "(" +
                    "'" + receiptModel.getGrowerId() + "'," +
                    "'" + receiptModel.getGrowerName() + "'," +
                    "'" + receiptModel.getIssued_seed_area() + "'," +
                    "'" + receiptModel.getProduction_code() + "'," +
                    "'" + receiptModel.getVillage() + "'," +
                    "'" + receiptModel.getExisting_area() + "'," +
                    "'" + receiptModel.getArea_loss() + "'," +
                    "'" + receiptModel.getReason_for_area_loss() + "'," +
                    "'" + receiptModel.getYeildinkg() + "'," +
                    "'" + receiptModel.getBatchno() + "'," +
                    "'" + receiptModel.getNoofbags() + "'," +
                    "'" + receiptModel.getWeightinkg() + "'," +
                    "'" + receiptModel.getServiceprovider() + "'," +
                    "'" + receiptModel.getGrower_mobile_no_edittext() + "'," +
                    "'" + receiptModel.getDate_of_field_visit_textview() + "'," +
                    "'" + receiptModel.getStaff_name_textview() + "'," +
                    "'" + receiptModel.getStaffID() + "')";
            mydb = this.getReadableDatabase();
            String q = str;
            Log.e("temporary", "updateAreaData Query is -------> " + q);
            mydb.execSQL(q);
            return true;
        } catch (Exception e) {
            Log.e("temporary", "updateAreaData Error is  Added Order Details : " + e.getMessage());
            return false;
        } finally {
            mydb.close();
            //  return false;

        }


    }

    public int isLocalReceiptDone(int userid) {
        SQLiteDatabase myDb = null;
        try {
            myDb = this.getReadableDatabase();
            String q = "select count(*)as cnt from tbl_seedreceipt where GrowerId=" + userid;
            Cursor cursorCourses = myDb.rawQuery(q, null);
            if (cursorCourses.moveToFirst()) {
                cursorCourses.getInt(0);
                return cursorCourses.getInt(0);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        } finally {
            myDb.close();
        }
    }
}
