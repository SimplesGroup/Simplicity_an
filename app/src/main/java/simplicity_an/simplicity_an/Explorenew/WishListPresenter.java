package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;

import java.util.List;

public interface WishListPresenter {
    public List<IndexProductModel> MyWishList(Context context,String rtype, String lang, String userid, String wishlistid);
    public List<IndexProductModel>MyWishremove(Context context,String rtype,  String userids, String wishlistids);
}
