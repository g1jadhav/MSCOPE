package mahyco.mipl.nxg;

import java.util.List;

import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.ForceUpdateModel;
import retrofit2.Response;

public interface MainActivityListListener {
    public void onResult(String result);

    public void onListResponce(List<CategoryModel> result);

    /*Added by jeevan 28-11-2022*/
    public void onAppUpdateResponse(ForceUpdateModel forceUpdateModel);
    /*Added by jeevan ended here 28-11-2022*/
}
