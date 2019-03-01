package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;

import java.util.List;

public interface PaymentPresenter {
    public List<PlaceorderModel> getMycartPayment(Context context,  final String rtype, final String userid);

}
