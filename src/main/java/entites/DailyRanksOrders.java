package entites;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Daily_Ranks_Orders")
@DynamicUpdate
public class DailyRanksOrders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pageNumber")
    private int pageNumber;

    @Column(name = "dates")
    private String dates;

    @Column(name = "tag")
    private String tagId;

    @Column(name = "rank_order")
    private String rankOrder;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String date) {
        this.dates = date;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankOrder() {
        return rankOrder;
    }

    public void setRankOrder(String rankOrder) {
        this.rankOrder = rankOrder;
    }

    @Override
    public String toString() {
        return "DailyRanksOrders{" +
                "id=" + id +
                ", pageNumber=" + pageNumber +
                ", dates='" + dates + '\'' +
                ", tagId='" + tagId + '\'' +
                ", rankOrder='" + rankOrder + '\'' +
                '}';
    }
}