package mahyco.mipl.nxg.view.login;

import java.util.List;

public interface LoginAPIListener {
    public void onResult(String result);

    public void onListResponce(List result);
    public void onCountryListResponce(List result);
}
