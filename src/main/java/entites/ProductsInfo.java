package entites;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Products_Info")
@DynamicUpdate
public class ProductsInfo implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="tag")
    private String tagId;

    @Column(name="productId")
    private long productId;

    @Column(name="productName")
    private String productName;

    @Column(name="productImage")
    private String productImage;

    @Column(name="productURL")
    private String productURL;

    @Column(name="createdDate")
    private String createdDate;

    @Column(name="ranks_2018")
    private String ranks_2018;

    @Column(name="ranks_2019")
    private String ranks_2019;

    @Column(name="ranks_2020")
    private String ranks_2020;

    @Column(name="ranks_2021")
    private String ranks_2021;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRanks_2018() {
        return ranks_2018;
    }

    public void setRanks_2018(String ranks_2018) {
        this.ranks_2018 = ranks_2018;
    }

    public String getRanks_2019() {
        return ranks_2019;
    }

    public void setRanks_2019(String ranks_2019) {
        this.ranks_2019 = ranks_2019;
    }

    public String getRanks_2020() {
        return ranks_2020;
    }

    public void setRanks_2020(String ranks_2020) {
        this.ranks_2020 = ranks_2020;
    }

    public String getRanks_2021() {
        return ranks_2021;
    }

    public void setRanks_2021(String ranks_2021) {
        this.ranks_2021 = ranks_2021;
    }

    @Override
    public String toString() {
        return "ProductsInfo{" +
                "id=" + id +
                ", tagId='" + tagId + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productURL='" + productURL + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", ranks_2018='" + ranks_2018 + '\'' +
                ", ranks_2019='" + ranks_2019 + '\'' +
                ", ranks_2020='" + ranks_2020 + '\'' +
                ", ranks_2021='" + ranks_2021 + '\'' +
                '}';
    }
}