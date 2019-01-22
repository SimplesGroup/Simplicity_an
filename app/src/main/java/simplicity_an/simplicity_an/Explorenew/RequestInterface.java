package simplicity_an.simplicity_an.Explorenew;

import java.util.List;

public interface RequestInterface {
    void Send(List<IndexProductModel>listdata);
void searchdata(List<IndexProductModel>listsearch);
void RecyclerLayouts(String searchvalue);

interface CompanylistRequest{
    void SendComp(List<IndexProductModel>listdata);
    void searchdataComp(List<IndexProductModel>listsearch);
}
interface Productlist{
    void Sendproductlist(List<IndexProductModel>listdata);
    void searchproductlist(List<IndexProductModel>listsearch);
}

}
