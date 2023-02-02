package mahyco.mipl.nxg.model;

import java.util.List;

public class FieldVisitModel {
    int UserId;//": 1,
    int CountryId;//": 1,
    int CountryMasterId;//": 90,
    int MandatoryFieldVisitId;//": 1,
    String FieldVisitType;//": "Mandatory Field Visit",
    double TotalSeedAreaLost;//": 0.02,
    double TaggedAreaInHA;//": 0.1,
    double ExistingAreaInHA;//": 0.1,
    String ReasonForTotalLossed;//": "Reason For Total Lossed",
    String FemaleSowingDt;//": "2023-01-15T05:35:13.528Z",
    String MaleSowingDt;//": "2023-01-15T05:35:13.528Z",
    String IsolationM;//": "Yes",
    int IsolationMeter;//": 2,
    String CropStage;//": "For Field Crop",
    int TotalNoOfFemaleLines;//": 10,
    int TotalNoOfMaleLines;//": 10,
    int FemaleSpacingRRinCM;//": 2,
    int FemaleSpacingPPinCM;//": 3,
    int MaleSpacingRRinCM;//": 2,
    int MaleSpacingPPinCM;//": 3,
    int PlantingRatioFemale;//": 5,
    int PlantingRatioMale;//": 4,
    String CropCategoryType;//": "For Field Crop",
    int TotalFemalePlants;//": 20,
    int TotalMalePlants;//": 20,
    int YieldEstimateInKg;//": 50,
    String Observations;//": "Observations Here",
    String FieldVisitDt;//": "2023-01-15T05:35:13.529Z",
    String Latitude;//": "19.886857",
    String Longitude;//": "75.3514908",
    String CapturePhoto;//": "",
    String CreatedBy;//": "55000066"
    String LocationData;
    String LineData;
    String AreaLossInHa;
    String NoOfRoguedFemalePlants;

    public String getAreaLossInHa() {
        return AreaLossInHa;
    }

    public void setAreaLossInHa(String areaLossInHa) {
        AreaLossInHa = areaLossInHa;
    }

    public String getNoOfRoguedFemalePlants() {
        return NoOfRoguedFemalePlants;
    }

    public void setNoOfRoguedFemalePlants(String noOfRoguedFemalePlants) {
        NoOfRoguedFemalePlants = noOfRoguedFemalePlants;
    }

    public String getNoOfRoguedMalePlants() {
        return NoOfRoguedMalePlants;
    }

    public void setNoOfRoguedMalePlants(String noOfRoguedMalePlants) {
        NoOfRoguedMalePlants = noOfRoguedMalePlants;
    }

    public String getSeedProductionMethod() {
        return SeedProductionMethod;
    }

    public void setSeedProductionMethod(String seedProductionMethod) {
        SeedProductionMethod = seedProductionMethod;
    }

    public String getRoguingCompletedValidated() {
        return RoguingCompletedValidated;
    }

    public void setRoguingCompletedValidated(String roguingCompletedValidated) {
        RoguingCompletedValidated = roguingCompletedValidated;
    }

    public String getSingleCobsPerPlant() {
        return SingleCobsPerPlant;
    }

    public void setSingleCobsPerPlant(String singleCobsPerPlant) {
        SingleCobsPerPlant = singleCobsPerPlant;
    }

    public String getSingleCobsPerPlantInGm() {
        return SingleCobsPerPlantInGm;
    }

    public void setSingleCobsPerPlantInGm(String singleCobsPerPlantInGm) {
        SingleCobsPerPlantInGm = singleCobsPerPlantInGm;
    }

    public String getUnprocessedSeedReadyInKg() {
        return UnprocessedSeedReadyInKg;
    }

    public void setUnprocessedSeedReadyInKg(String unprocessedSeedReadyInKg) {
        UnprocessedSeedReadyInKg = unprocessedSeedReadyInKg;
    }

    public String getPollinationStartDt() {
        return PollinationStartDt;
    }

    public void setPollinationStartDt(String pollinationStartDt) {
        PollinationStartDt = pollinationStartDt;
    }

    public String getPollinationEndDt() {
        return PollinationEndDt;
    }

    public void setPollinationEndDt(String pollinationEndDt) {
        PollinationEndDt = pollinationEndDt;
    }

    public String getExpectedDtOfHarvesting() {
        return ExpectedDtOfHarvesting;
    }

    public void setExpectedDtOfHarvesting(String expectedDtOfHarvesting) {
        ExpectedDtOfHarvesting = expectedDtOfHarvesting;
    }

    public String getExpectedDtOfDespatching() {
        return ExpectedDtOfDespatching;
    }

    public void setExpectedDtOfDespatching(String expectedDtOfDespatching) {
        ExpectedDtOfDespatching = expectedDtOfDespatching;
    }

    public String getMaleParentUprooted() {
        return MaleParentUprooted;
    }

    public void setMaleParentUprooted(String maleParentUprooted) {
        MaleParentUprooted = maleParentUprooted;
    }

    public String getFieldVisitRoguedPlantModels() {
        return fieldVisitRoguedPlantModels;
    }

    public void setFieldVisitRoguedPlantModels(String fieldVisitRoguedPlantModels) {
        this.fieldVisitRoguedPlantModels = fieldVisitRoguedPlantModels;
    }

    public String getFieldVisitFruitsCobModelsText() {
        return fieldVisitFruitsCobModelsText;
    }

    public void setFieldVisitFruitsCobModelsText(String fieldVisitFruitsCobModelsText) {
        this.fieldVisitFruitsCobModelsText = fieldVisitFruitsCobModelsText;
    }

    String NoOfRoguedMalePlants;
    String SeedProductionMethod;
    String RoguingCompletedValidated;
    String SingleCobsPerPlant;
    String SingleCobsPerPlantInGm;
    String UnprocessedSeedReadyInKg;
    String PollinationStartDt;
    String PollinationEndDt;
    String ExpectedDtOfHarvesting;
    String ExpectedDtOfDespatching;
    String MaleParentUprooted;
    String fieldVisitRoguedPlantModels;
    String fieldVisitFruitsCobModelsText;



    public String getLocationData() {
        return LocationData;
    }

    public void setLocationData(String locationData) {
        LocationData = locationData;
    }

    public String getLineData() {
        return LineData;
    }

    public void setLineData(String lineData) {
        LineData = lineData;
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


}
