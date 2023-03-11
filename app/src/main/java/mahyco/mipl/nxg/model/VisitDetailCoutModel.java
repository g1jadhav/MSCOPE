package mahyco.mipl.nxg.model;

public class VisitDetailCoutModel {
    public int getTempid() {
        return tempid;
    }

    public void setTempid(int tempid) {
        this.tempid = tempid;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getGrowerName() {
        return GrowerName;
    }

    public void setGrowerName(String growerName) {
        GrowerName = growerName;
    }

    public String getGrowerNationalId() {
        return GrowerNationalId;
    }

    public void setGrowerNationalId(String growerNationalId) {
        GrowerNationalId = growerNationalId;
    }

    public String getGrowerMobile() {
        return GrowerMobile;
    }

    public void setGrowerMobile(String growerMobile) {
        GrowerMobile = growerMobile;
    }

    public String getGrowerIssuedArea() {
        return GrowerIssuedArea;
    }

    public void setGrowerIssuedArea(String growerIssuedArea) {
        GrowerIssuedArea = growerIssuedArea;
    }

    public String getGrowerExistingArea() {
        return GrowerExistingArea;
    }

    public void setGrowerExistingArea(String growerExistingArea) {
        GrowerExistingArea = growerExistingArea;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitDate) {
        VisitDate = visitDate;
    }

    public String getVisitedBy() {
        return VisitedBy;
    }

    public void setVisitedBy(String visitedBy) {
        VisitedBy = visitedBy;
    }

    int tempid;

    public int getVisitID() {
        return VisitID;
    }

    public void setVisitID(int visitID) {
        VisitID = visitID;
    }

    int VisitID;

    public int getIsAreaLossStatus() {
        return isAreaLossStatus;
    }

    public void setIsAreaLossStatus(int isAreaLossStatus) {
        this.isAreaLossStatus = isAreaLossStatus;
    }

    int isAreaLossStatus=0;
    String UserId,GrowerName,GrowerNationalId,GrowerMobile,GrowerIssuedArea,GrowerExistingArea,VisitDate,VisitedBy;
}
