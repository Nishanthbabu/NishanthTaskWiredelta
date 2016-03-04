package wiredelta.com.gson;

import java.util.Date;

/**
 * Created by Nishanth on 3/5/2016.
 */
public class CompanyModel {
    private String companyID = null;
    private String companyName = null;
    private String companyOwner =null;
    private Date companyStartDate = null;
    private String companyDescription = null;
    private String companyDepartments = null;


    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public Date getCompanyStartDate() {
        return companyStartDate;
    }

    public void setCompanyStartDate(Date companyStartDate) {
        this.companyStartDate = companyStartDate;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyDepartments() {
        return companyDepartments;
    }

    public void setCompanyDepartments(String companyDepartments) {
        this.companyDepartments = companyDepartments;
    }
}
