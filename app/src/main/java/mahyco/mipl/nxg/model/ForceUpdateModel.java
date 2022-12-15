package mahyco.mipl.nxg.model;

public class ForceUpdateModel {

    String message;
    String AppVersion;
    boolean IsFeedbackStatus;
    boolean success;
    String Description;
    String DescriptionStatus;

    public ForceUpdateModel(String message, String appVersion, boolean isFeedbackStatus, boolean success, String description, String descriptionStatus) {
        this.message = message;
        AppVersion = appVersion;
        IsFeedbackStatus = isFeedbackStatus;
        this.success = success;
        Description = description;
        DescriptionStatus = descriptionStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public boolean isFeedbackStatus() {
        return IsFeedbackStatus;
    }

    public void setFeedbackStatus(boolean feedbackStatus) {
        IsFeedbackStatus = feedbackStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionStatus() {
        return DescriptionStatus;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        DescriptionStatus = descriptionStatus;
    }
}
