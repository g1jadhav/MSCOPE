package mahyco.mipl.nxg.util;

public class Constants {
    //Live Server
  //  public static final String BASE_URL = "http://10.80.50.26/MIPL/api/";
  //public static final String BASE_URL = "https://mscope.mahyco.com/api/";
  // Test Server
  public static final String BASE_URL = "http://10.80.50.153/MIPL/api/";

    public static final String CREATEUSER ="staffUser/create" ;
    public static final String GETCATEGORY = "countryCategory/getall";
    public static final String GETCATEGORY_BY_PARENT = "countryMaster/getall";
  //  public static final String SUBMIT_GROWER = "users/create";
    public static final String SUBMIT_GROWER = "users/newCreate";
    public static final String SUBMIT_FISRTVISIT = "fieldMonitoringVisit/create";
    public static final String GETALL_FISRTVISIT = "fieldMonitoringVisit/getall";
    public static final String GETALL_VILLAGEVISIT = "parentSeedDistributionVillages/getall";
    public static final String CHANAGE_PASSWORD ="staffUser/changePassword" ;
  public static final String SUBMIT_SEEDRECEIPT = "seedReceipt/create";
  public static final String SUBMIT_PRODUCTIONREGISTRATION = "seedProductionRegistration/create";
  //  public static final String GETALLSEEDRECEIPTDATA = "seedReceipt/getall";
    public static final String GETALLSEEDRECEIPTDATA = "seedReceipt/getDetailReport";
    public static final String GETALLSEEDREGISTRATIONDATA = "seedProductionRegistration/getall";
    static final String CHECK_LOGIN="customToken/customToken";
    public static final String GET_SEASON = "season/getall";
    public static final String GET_CROP = "crop/getall";
    public static final String GET_GROWER = "users/getall";
    public static final String GET_LOCATION = "countryMaster/getall";
    public static final String GET_USER_TYPE = "role/getall";
    public static final String PRODUCTION_CLUSTER = "productionCluster/getall";
    public static final String PRODUCTION_CODE = "product/getall";
    public static final String SEED_RECEIPT_BATCH = "parentSeedReceipt/getall";
    public static final String MALE_FEMALE_BATCH = "parentSeedBatch/getall";
    public static final String CREATE_DISTRIBUTION = "parentSeedDistribution/create";
    public static final String CROP_TYPE = "cropType/getall";
    public static final String PARENT_SEED_DISTRIBUTION_GET_ALL = "parentSeedDistribution/getall";

}
