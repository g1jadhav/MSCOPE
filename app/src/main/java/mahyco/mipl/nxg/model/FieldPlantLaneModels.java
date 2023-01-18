package mahyco.mipl.nxg.model;

public class FieldPlantLaneModels {
    String PlantType;//": "Female",
            int LaneNo;//": 1,

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

    int NoOfPlants;//": 20,
}
