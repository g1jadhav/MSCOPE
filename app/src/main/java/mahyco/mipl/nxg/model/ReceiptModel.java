package mahyco.mipl.nxg.model;

public class ReceiptModel {

    public int getTempId() {
        return TempId;
    }

    public void setTempId(int tempId) {
        TempId = tempId;
    }

    int TempId;
    public String getGrowerId() {
        return GrowerId;
    }

    public void setGrowerId(String growerId) {
        GrowerId = growerId;
    }

    public String getGrowerName() {
        return GrowerName;
    }

    public void setGrowerName(String growerName) {
        GrowerName = growerName;
    }

    public String getIssued_seed_area() {
        return issued_seed_area;
    }

    public void setIssued_seed_area(String issued_seed_area) {
        this.issued_seed_area = issued_seed_area;
    }

    public String getProduction_code() {
        return production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getExisting_area() {
        return existing_area;
    }

    public void setExisting_area(String existing_area) {
        this.existing_area = existing_area;
    }

    public String getArea_loss() {
        return area_loss;
    }

    public void setArea_loss(String area_loss) {
        this.area_loss = area_loss;
    }

    public String getReason_for_area_loss() {
        return reason_for_area_loss;
    }

    public void setReason_for_area_loss(String reason_for_area_loss) {
        this.reason_for_area_loss = reason_for_area_loss;
    }

    public String getYeildinkg() {
        return yeildinkg;
    }

    public void setYeildinkg(String yeildinkg) {
        this.yeildinkg = yeildinkg;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getNoofbags() {
        return noofbags;
    }

    public void setNoofbags(String noofbags) {
        this.noofbags = noofbags;
    }

    public String getWeightinkg() {
        return weightinkg;
    }

    public void setWeightinkg(String weightinkg) {
        this.weightinkg = weightinkg;
    }

    public String getServiceprovider() {
        return serviceprovider;
    }

    public void setServiceprovider(String serviceprovider) {
        this.serviceprovider = serviceprovider;
    }

    public String getGrower_mobile_no_edittext() {
        return grower_mobile_no_edittext;
    }

    public void setGrower_mobile_no_edittext(String grower_mobile_no_edittext) {
        this.grower_mobile_no_edittext = grower_mobile_no_edittext;
    }

    public String getDate_of_field_visit_textview() {
        return date_of_field_visit_textview;
    }

    public void setDate_of_field_visit_textview(String date_of_field_visit_textview) {
        this.date_of_field_visit_textview = date_of_field_visit_textview;
    }

    public String getStaff_name_textview() {
        return staff_name_textview;
    }

    public void setStaff_name_textview(String staff_name_textview) {
        this.staff_name_textview = staff_name_textview;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }
    String GrowerId;
    String GrowerName;
    String issued_seed_area;
    String production_code;
    String village;
    String existing_area;
    String area_loss;
    String reason_for_area_loss;
    String yeildinkg;
    String batchno;
    String noofbags;
    String weightinkg;
    String serviceprovider;
    String grower_mobile_no_edittext;
    String date_of_field_visit_textview;
    String staff_name_textview;
    String StaffID;

    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(String countryID) {
        CountryID = countryID;
    }

    String CountryID;
}
