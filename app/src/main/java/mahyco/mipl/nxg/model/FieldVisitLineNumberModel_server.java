package mahyco.mipl.nxg.model;

public class FieldVisitLineNumberModel_server {
    int FieldPlantLaneId;// 1,
    int FieldVisitId;// 1,

    public int getFieldPlantLaneId() {
        return FieldPlantLaneId;
    }

    public void setFieldPlantLaneId(int fieldPlantLaneId) {
        FieldPlantLaneId = fieldPlantLaneId;
    }

    public int getFieldVisitId() {
        return FieldVisitId;
    }

    public void setFieldVisitId(int fieldVisitId) {
        FieldVisitId = fieldVisitId;
    }

    public String getPlantType() {
        return PlantType;
    }

    public void setPlantType(String plantType) {
        PlantType = plantType;
    }

    public int getLaneNo() {
        return LaneNo;
    }

    public void setLaneNo(int laneNo) {
        LaneNo = laneNo;
    }

    public int getNoOfPlants() {
        return NoOfPlants;
    }

    public void setNoOfPlants(int noOfPlants) {
        NoOfPlants = noOfPlants;
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

    String PlantType;// "Female",
    int LaneNo;// 1,
    int NoOfPlants;// 20,
    String CreatedBy;// "55000066",
    String CreatedDt;// "2023-01-15T11:28:33.217"
}
