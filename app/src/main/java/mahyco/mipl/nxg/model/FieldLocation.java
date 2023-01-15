package mahyco.mipl.nxg.model;

public class FieldLocation {
    int FieldId;

    public int getFieldId() {
        return FieldId;
    }

    public void setFieldId(int fieldId) {
        FieldId = fieldId;
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

    String Latitude,Longitude;
}
