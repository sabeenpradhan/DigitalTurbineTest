package sabeen.digitalturbinetest.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model For Ad
 * Extends RealmObject for using Realm Database
 * Annotated @root for using xml parser of retrofit
 * Created by sabeen on 8/12/16.
 */

@Root(name = "ad",strict = false)
public class Ad extends RealmObject{

    @Element(name = "productThumbnail")
    private String productThumbnail;
    @Element(name = "productName")
    private String productName;
    @Element(name = "productDescription")
    private String productDescription;
    @Element(name = "categoryName")
    private String categoryName;
    @PrimaryKey
    @Element(name = "appId")
    private String appId;
    @Element(name = "rating")
    private String rating;
//    @Element(name = "minOSVersion")
//    private String minOSVersion;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

//    public String getMinOSVersion() {
//        return minOSVersion;
//    }
//
//    public void setMinOSVersion(String minOSVersion) {
//        this.minOSVersion = minOSVersion;
//    }
}
