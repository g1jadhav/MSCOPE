package mahyco.mipl.nxg.model;

public class SeedRegistraionLocalModel {

    int tempid;// INTEGER PRIMARY KEY AUTOINCREMENT,

    public int getTempid() {
        return tempid;
    }

    public void setTempid(int tempid) {
        this.tempid = tempid;
    }

    public String getGrowerid() {
        return growerid;
    }

    public void setGrowerid(String growerid) {
        this.growerid = growerid;
    }

    public String getGrowername() {
        return growername;
    }

    public void setGrowername(String growername) {
        this.growername = growername;
    }

    public String getNationalis() {
        return nationalis;
    }

    public void setNationalis(String nationalis) {
        this.nationalis = nationalis;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlantingyear() {
        return plantingyear;
    }

    public void setPlantingyear(String plantingyear) {
        this.plantingyear = plantingyear;
    }

    public int getSeasonid() {
        return seasonid;
    }

    public void setSeasonid(int seasonid) {
        this.seasonid = seasonid;
    }

    public String getSeasonnmae() {
        return seasonnmae;
    }

    public void setSeasonnmae(String seasonnmae) {
        this.seasonnmae = seasonnmae;
    }

    public int getCropid() {
        return cropid;
    }

    public void setCropid(int cropid) {
        this.cropid = cropid;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public int getClusterid() {
        return clusterid;
    }

    public void setClusterid(int clusterid) {
        this.clusterid = clusterid;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getSeedarea() {
        return seedarea;
    }

    public void setSeedarea(String seedarea) {
        this.seedarea = seedarea;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getCreatedbyid() {
        return createdbyid;
    }

    public void setCreatedbyid(String createdbyid) {
        this.createdbyid = createdbyid;
    }

    public String getCreatedbyname() {
        return createdbyname;
    }

    public void setCreatedbyname(String createdbyname) {
        this.createdbyname = createdbyname;
    }

    String growerid;// INTEGER UNIQUE,
    String growername;// text,
    String nationalis;// text,
    String address;// text,
    String plantingyear;// text,
    int seasonid;// INTEGER,
    String seasonnmae;// text,
    int cropid;// INTEGER,
    String cropname;// text,
    int clusterid;// INTEGER,
    String clustername;// text,
    String seedarea;// text,
    String mobile;// text,
    String issuedate;// text,
    String createdbyid;// text,
    String createdbyname;// TEXT
}
