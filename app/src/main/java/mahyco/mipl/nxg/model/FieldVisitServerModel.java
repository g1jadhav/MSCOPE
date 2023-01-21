package mahyco.mipl.nxg.model;

import java.util.List;

public class FieldVisitServerModel {
    public List<FieldVisitModel_Server> getFieldVisitModel() {
        return fieldVisitModel;
    }

    public void setFieldVisitModel(List<FieldVisitModel_Server> fieldVisitModel) {
        this.fieldVisitModel = fieldVisitModel;
    }

    public List<FieldVisitLocationModel_server> getFieldVisitLocationModels() {
        return fieldVisitLocationModels;
    }

    public void setFieldVisitLocationModels(List<FieldVisitLocationModel_server> fieldVisitLocationModels) {
        this.fieldVisitLocationModels = fieldVisitLocationModels;
    }

    public List<FieldVisitLineNumberModel_server> getFieldPlantLaneModels() {
        return fieldPlantLaneModels;
    }

    public void setFieldPlantLaneModels(List<FieldVisitLineNumberModel_server> fieldPlantLaneModels) {
        this.fieldPlantLaneModels = fieldPlantLaneModels;
    }

    List<FieldVisitModel_Server> fieldVisitModel;
    List<FieldVisitLocationModel_server> fieldVisitLocationModels;
    List<FieldVisitLineNumberModel_server> fieldPlantLaneModels;
}
