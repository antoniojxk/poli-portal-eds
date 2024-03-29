package co.com.estacionsannicolas.beans;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = MarketingCampaignBean.class)
public class MarketingCampaignBean extends BaseBean {

    private String name;

    private Date startDate;

    private Date endDate;

    @JsonIgnore
    private List<AwardBean> awards;

    @JsonIgnore
    private List<AwardPointBean> awardPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<AwardBean> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardBean> awards) {
        this.awards = awards;
    }

    public List<AwardPointBean> getAwardPoints() {
        return awardPoints;
    }

    public void setAwardPoints(List<AwardPointBean> awardPoints) {
        this.awardPoints = awardPoints;
    }

}
