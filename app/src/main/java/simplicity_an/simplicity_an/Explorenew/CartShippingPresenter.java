package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;

import java.util.List;

public interface CartShippingPresenter {
     List<ShippingModel> getAddress(Context context, String rtype, String userids);
    List<ShippingModel> Addressupdateornew(Context context, String rtype, String userids,String name,String phone,String email, String address,String location,String pincode,String landmark,String state,String shippingaddress_id);

    List<ShippingModel> getAddressdeleteandactive(Context context, String rtype, String userids,String shippingaddress_id);
}


