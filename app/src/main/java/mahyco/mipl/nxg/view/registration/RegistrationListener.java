package mahyco.mipl.nxg.view.registration;

import java.util.List;

import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.UserTypeModel;

public interface RegistrationListener {
    public void onResult(String result);

    void onListResponce(List<CategoryModel> result);
    void onCountryListResponce(List<CategoryChildModel> result);
    void onUserTypeListResponce(List<UserTypeModel> result);
}
