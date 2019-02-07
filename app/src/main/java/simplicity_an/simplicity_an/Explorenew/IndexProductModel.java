package simplicity_an.simplicity_an.Explorenew;

import java.util.List;

public class IndexProductModel {
    private String id;
    private String title;
    private String image;
    private String main_category_id,category_id,category_title,category_description;
private String company_id,sub_category_id,low_category_id,product_id,product_title,product_description,company_title,description;
private String measurement,quantity,price,stock,offer_type_text,offer_type;
private String sub_category_title,low_category_title;
private String wastage,netweight,net_price,material_price;
private String stonename,stonecut,stonecolor,stoneclarity,stoneweight,stoneprice;
    private int quantity_id,offer_price;
    private int visit_list,cart_list;
private String count,cart_id,qty_id;
    private  String item_arrayname;

    private int mycarttotalitem,mycart_netprice;
    private List<IndexProductModel>pricelist;
    private List<IndexProductModel>stonelist;

    public int getMycarttotalitem() {
        return mycarttotalitem;
    }

    public void setMycarttotalitem(int mycarttotalitem) {
        this.mycarttotalitem = mycarttotalitem;
    }

    public int getMycart_netprice() {
        return mycart_netprice;
    }

    public void setMycart_netprice(int mycart_netprice) {
        this.mycart_netprice = mycart_netprice;
    }

    public List<IndexProductModel> getStonelist() {
        return stonelist;
    }

    public void setStonelist(List<IndexProductModel> stonelist) {
        this.stonelist = stonelist;
    }

    public String getQty_id() {
        return qty_id;
    }

    public void setQty_id(String qty_id) {
        this.qty_id = qty_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStonename() {
        return stonename;
    }

    public void setStonename(String stonename) {
        this.stonename = stonename;
    }

    public String getStonecut() {
        return stonecut;
    }

    public void setStonecut(String stonecut) {
        this.stonecut = stonecut;
    }

    public String getStonecolor() {
        return stonecolor;
    }

    public void setStonecolor(String stonecolor) {
        this.stonecolor = stonecolor;
    }

    public String getStoneclarity() {
        return stoneclarity;
    }

    public void setStoneclarity(String stoneclarity) {
        this.stoneclarity = stoneclarity;
    }

    public String getStoneweight() {
        return stoneweight;
    }

    public void setStoneweight(String stoneweight) {
        this.stoneweight = stoneweight;
    }

    public String getStoneprice() {
        return stoneprice;
    }

    public void setStoneprice(String stoneprice) {
        this.stoneprice = stoneprice;
    }



    public void setMaterial_price(String material_price) {
        this.material_price = material_price;
    }

    public void setNet_price(String net_price) {
        this.net_price = net_price;
    }

    public void setNetweight(String netweight) {
        this.netweight = netweight;
    }

    public void setWastage(String wastage) {
        this.wastage = wastage;
    }

    public String getMaterial_price() {
        return material_price;
    }

    public String getNet_price() {
        return net_price;
    }

    public String getNetweight() {
        return netweight;
    }

    public String getWastage() {
        return wastage;
    }

    public String getLow_category_title() {
        return low_category_title;
    }

    public String getSub_category_title() {
        return sub_category_title;
    }

    public void setLow_category_title(String low_category_title) {
        this.low_category_title = low_category_title;
    }

    public void setSub_category_title(String sub_category_title) {
        this.sub_category_title = sub_category_title;
    }

    public String getItem_arrayname() {
        return item_arrayname;
    }

    public void setItem_arrayname(String item_arrayname) {
        this.item_arrayname = item_arrayname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany_title() {
        return company_title;
    }

    public void setCompany_title(String company_title) {
        this.company_title = company_title;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getQuantity_id() {
        return quantity_id;
    }

    public void setQuantity_id(int quantity_id) {
        this.quantity_id = quantity_id;
    }

    public int getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(int offer_price) {
        this.offer_price = offer_price;
    }

    public void setCart_list(int cart_list) {
        this.cart_list = cart_list;
    }

    public int getCart_list() {
        return cart_list;
    }

    public int getVisit_list() {
        return visit_list;
    }

    public void setVisit_list(int visit_list) {
        this.visit_list = visit_list;
    }

    public List<IndexProductModel> getPricelist() {
        return pricelist;
    }

    public void setPricelist(List<IndexProductModel> pricelist) {
        this.pricelist = pricelist;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getLow_category_id() {
        return low_category_id;
    }

    public void setLow_category_id(String low_category_id) {
        this.low_category_id = low_category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOffer_type_text() {
        return offer_type_text;
    }

    public void setOffer_type_text(String offer_type_text) {
        this.offer_type_text = offer_type_text;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    public String getCategory_description() {
        return category_description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public String getMain_category_id() {
        return main_category_id;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public void setMain_category_id(String main_category_id) {
        this.main_category_id = main_category_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
