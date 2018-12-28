package simplicity_an.simplicity_an.Explorenew;

public class IndexProductModel {
    private String id;
    private String title;
    private String image;
    private String main_category_id,category_id,category_title,category_description;

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
