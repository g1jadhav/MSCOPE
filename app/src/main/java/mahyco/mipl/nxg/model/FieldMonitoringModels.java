package mahyco.mipl.nxg.model;

import java.util.List;

public class FieldMonitoringModels {

    FieldVisitModel fieldVisitModel;
    List<FieldVisitLocationModel> fieldVisitLocationModels;/*": [
        {
        "FieldNo": 1,
        "Latitude": "19.886857",
        "Longitude": "75.3514908",
        "TaggedAreaInHA": 0.1,
        }
        ],*/

    public FieldVisitModel getFieldVisitModel() {
        return fieldVisitModel;
    }

    public void setFieldVisitModel(FieldVisitModel fieldVisitModel) {
        this.fieldVisitModel = fieldVisitModel;
    }

    public List<FieldVisitLocationModel> getFieldVisitLocationModels() {
        return fieldVisitLocationModels;
    }

    public void setFieldVisitLocationModels(List<FieldVisitLocationModel> fieldVisitLocationModels) {
        this.fieldVisitLocationModels = fieldVisitLocationModels;
    }

    public List<FieldPlantLaneModels> getFieldPlantLaneModels() {
        return fieldPlantLaneModels;
    }

    public void setFieldPlantLaneModels(List<FieldPlantLaneModels> fieldPlantLaneModels) {
        this.fieldPlantLaneModels = fieldPlantLaneModels;
    }

    List<FieldPlantLaneModels> fieldPlantLaneModels;/*": [

        {
        "PlantType": "Female",
        "LaneNo": 1,
        "NoOfPlants": 20,
        }
        ]*/

}
