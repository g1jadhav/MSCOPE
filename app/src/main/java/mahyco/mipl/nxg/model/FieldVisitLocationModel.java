package mahyco.mipl.nxg.model;

public class FieldVisitLocationModel {


    int FieldNo;//": 1,

    public int getFieldNo() {
        return FieldNo;
    }

    public void setFieldNo(int fieldNo) {
        FieldNo = fieldNo;
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

    public Double getTaggedAreaInHA() {
        return TaggedAreaInHA;
    }

    public void setTaggedAreaInHA(Double taggedAreaInHA) {
        TaggedAreaInHA = taggedAreaInHA;
    }

    String Latitude;//": "19.886857",
            String Longitude;//": "75.3514908",
            Double TaggedAreaInHA;//": 0.1,

}
