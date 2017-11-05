public class Record {
    private String cmteId;
    private String zipCode;
    private String transDate;
    private Integer transAmt;

    public Record() {
    }

    public Record(String cmteId, Integer transAmt) {
        this.cmteId = cmteId;
        this.transAmt = transAmt;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = (zipCode.length() > 4) ? zipCode : " ";
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = (transDate.length() == 8) ? transDate : " ";
    }

    public String getCmteId() {
        return cmteId;
    }

    public void setCmteId(String cmteId) {
        this.cmteId = cmteId;
    }

    public Integer getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Integer transAmt) {
        this.transAmt = transAmt;
    }
}