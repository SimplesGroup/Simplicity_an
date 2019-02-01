package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;

import java.util.List;

public interface CartViewPresenter {
   public List<IndexProductModel> getMycart(Context context, final String lang, final String rtype, final String userid, final String cartid, final String qtyid, final String count);
}
