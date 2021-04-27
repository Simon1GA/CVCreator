package Models;

public class EduExpInfo {
    private String education;
    private String institute;
    private String subject;
    private String company;
    private String jobTitle;
    private String years;

    public EduExpInfo(){ }

    public EduExpInfo(String education, String institute, String subject, String company, String jobTitle, String years){
        this.education = education;
        this.institute = institute;
        this.subject = subject;
        this.company = company;
        this.jobTitle = jobTitle;
        this.years = years;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

}
