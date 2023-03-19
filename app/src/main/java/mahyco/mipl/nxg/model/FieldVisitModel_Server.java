package mahyco.mipl.nxg.model;

public class FieldVisitModel_Server {
    int FieldVisitId;// 1,
    int UserId;// 1,
    int CountryId;// 1,
    int CountryMasterId;// 90,
    int MandatoryFieldVisitId;// 1,
    String FieldVisitType;// "Mandatory Field Visit",
    double  TotalSeedAreaLost;// 0.02,
    double TaggedAreaInHA;// 0.1,
    double ExistingAreaInHA;// 0.1,
    String ReasonForTotalLossed;// "Reason For Total Lossed",
    String FemaleSowingDt;// "2023-01-15T00:00:00",
    String MaleSowingDt;// "2023-01-15T00:00:00",
    String IsolationM;// "Yes",
    int IsolationMeter;// 2,
    String CropStage;// "For Field Crop",
    int TotalNoOfFemaleLines;// 10,
    int TotalNoOfMaleLines;// 10,
    int FemaleSpacingRRinCM;// 2,
    int FemaleSpacingPPinCM;// 3,
    int MaleSpacingRRinCM;// 2,
    int MaleSpacingPPinCM;// 3,
    int PlantingRatioFemale;// 5,
    int PlantingRatioMale;// 4,
    String CropCategoryType;// "For Field Crop",

    public String getAreaLossStatus() {
        return AreaLossStatus;
    }

    public void setAreaLossStatus(String areaLossStatus) {
        AreaLossStatus = areaLossStatus;
    }

    String AreaLossStatus;

    public int getFieldVisitId() {
        return FieldVisitId;
    }

    public void setFieldVisitId(int fieldVisitId) {
        FieldVisitId = fieldVisitId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public int getCountryMasterId() {
        return CountryMasterId;
    }

    public void setCountryMasterId(int countryMasterId) {
        CountryMasterId = countryMasterId;
    }

    public int getMandatoryFieldVisitId() {
        return MandatoryFieldVisitId;
    }

    public void setMandatoryFieldVisitId(int mandatoryFieldVisitId) {
        MandatoryFieldVisitId = mandatoryFieldVisitId;
    }

    public String getFieldVisitType() {
        return FieldVisitType;
    }

    public void setFieldVisitType(String fieldVisitType) {
        FieldVisitType = fieldVisitType;
    }

    public double getTotalSeedAreaLost() {
        return TotalSeedAreaLost;
    }

    public void setTotalSeedAreaLost(double totalSeedAreaLost) {
        TotalSeedAreaLost = totalSeedAreaLost;
    }

    public double getTaggedAreaInHA() {
        return TaggedAreaInHA;
    }

    public void setTaggedAreaInHA(double taggedAreaInHA) {
        TaggedAreaInHA = taggedAreaInHA;
    }

    public double getExistingAreaInHA() {
        return ExistingAreaInHA;
    }

    public void setExistingAreaInHA(double existingAreaInHA) {
        ExistingAreaInHA = existingAreaInHA;
    }

    public String getReasonForTotalLossed() {
        return ReasonForTotalLossed;
    }

    public void setReasonForTotalLossed(String reasonForTotalLossed) {
        ReasonForTotalLossed = reasonForTotalLossed;
    }

    public String getFemaleSowingDt() {
        return FemaleSowingDt;
    }

    public void setFemaleSowingDt(String femaleSowingDt) {
        FemaleSowingDt = femaleSowingDt;
    }

    public String getMaleSowingDt() {
        return MaleSowingDt;
    }

    public void setMaleSowingDt(String maleSowingDt) {
        MaleSowingDt = maleSowingDt;
    }

    public String getIsolationM() {
        return IsolationM;
    }

    public void setIsolationM(String isolationM) {
        IsolationM = isolationM;
    }

    public int getIsolationMeter() {
        return IsolationMeter;
    }

    public void setIsolationMeter(int isolationMeter) {
        IsolationMeter = isolationMeter;
    }

    public String getCropStage() {
        return CropStage;
    }

    public void setCropStage(String cropStage) {
        CropStage = cropStage;
    }

    public int getTotalNoOfFemaleLines() {
        return TotalNoOfFemaleLines;
    }

    public void setTotalNoOfFemaleLines(int totalNoOfFemaleLines) {
        TotalNoOfFemaleLines = totalNoOfFemaleLines;
    }

    public int getTotalNoOfMaleLines() {
        return TotalNoOfMaleLines;
    }

    public void setTotalNoOfMaleLines(int totalNoOfMaleLines) {
        TotalNoOfMaleLines = totalNoOfMaleLines;
    }

    public int getFemaleSpacingRRinCM() {
        return FemaleSpacingRRinCM;
    }

    public void setFemaleSpacingRRinCM(int femaleSpacingRRinCM) {
        FemaleSpacingRRinCM = femaleSpacingRRinCM;
    }

    public int getFemaleSpacingPPinCM() {
        return FemaleSpacingPPinCM;
    }

    public void setFemaleSpacingPPinCM(int femaleSpacingPPinCM) {
        FemaleSpacingPPinCM = femaleSpacingPPinCM;
    }

    public int getMaleSpacingRRinCM() {
        return MaleSpacingRRinCM;
    }

    public void setMaleSpacingRRinCM(int maleSpacingRRinCM) {
        MaleSpacingRRinCM = maleSpacingRRinCM;
    }

    public int getMaleSpacingPPinCM() {
        return MaleSpacingPPinCM;
    }

    public void setMaleSpacingPPinCM(int maleSpacingPPinCM) {
        MaleSpacingPPinCM = maleSpacingPPinCM;
    }

    public int getPlantingRatioFemale() {
        return PlantingRatioFemale;
    }

    public void setPlantingRatioFemale(int plantingRatioFemale) {
        PlantingRatioFemale = plantingRatioFemale;
    }

    public int getPlantingRatioMale() {
        return PlantingRatioMale;
    }

    public void setPlantingRatioMale(int plantingRatioMale) {
        PlantingRatioMale = plantingRatioMale;
    }

    public String getCropCategoryType() {
        return CropCategoryType;
    }

    public void setCropCategoryType(String cropCategoryType) {
        CropCategoryType = cropCategoryType;
    }

    public int getTotalFemalePlants() {
        return TotalFemalePlants;
    }

    public void setTotalFemalePlants(int totalFemalePlants) {
        TotalFemalePlants = totalFemalePlants;
    }

    public int getTotalMalePlants() {
        return TotalMalePlants;
    }

    public void setTotalMalePlants(int totalMalePlants) {
        TotalMalePlants = totalMalePlants;
    }

    public int getYieldEstimateInKg() {
        return YieldEstimateInKg;
    }

    public void setYieldEstimateInKg(int yieldEstimateInKg) {
        YieldEstimateInKg = yieldEstimateInKg;
    }

    public String getObservations() {
        return Observations;
    }

    public void setObservations(String observations) {
        Observations = observations;
    }

    public String getFieldVisitDt() {
        return FieldVisitDt;
    }

    public void setFieldVisitDt(String fieldVisitDt) {
        FieldVisitDt = fieldVisitDt;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCapturePhoto() {
        return CapturePhoto;
    }

    public void setCapturePhoto(String capturePhoto) {
        CapturePhoto = capturePhoto;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDt() {
        return CreatedDt;
    }

    public void setCreatedDt(String createdDt) {
        CreatedDt = createdDt;
    }

    int TotalFemalePlants;// 20,
    int TotalMalePlants;// 20,
    int YieldEstimateInKg;// 50,
    String Observations;// "Observations Here",
    String FieldVisitDt;// "2023-01-15T05:35:13.53",
    String Latitude;// "19.886857",
    String Longitude;// "75.3514908",
    String CapturePhoto;// "Field-Visit-Img-U1-V1-20230115112859249.jpg",
    String CreatedBy;// "55000066",
    String CreatedDt;// "2023-01-15T11:28:16.4"
}
