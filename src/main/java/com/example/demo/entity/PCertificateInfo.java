package com.example.demo.entity;

import java.util.Date;

public class PCertificateInfo {
    private Integer id;

    private String certificateScanningFile;

    private String otherScanningFile;

    private Integer isDelete;

    private Date createDate;

    private String creator;

    private Date updateDate;

    private String regenerator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificateScanningFile() {
        return certificateScanningFile;
    }

    public void setCertificateScanningFile(String certificateScanningFile) {
        this.certificateScanningFile = certificateScanningFile == null ? null : certificateScanningFile.trim();
    }

    public String getOtherScanningFile() {
        return otherScanningFile;
    }

    public void setOtherScanningFile(String otherScanningFile) {
        this.otherScanningFile = otherScanningFile == null ? null : otherScanningFile.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRegenerator() {
        return regenerator;
    }

    public void setRegenerator(String regenerator) {
        this.regenerator = regenerator == null ? null : regenerator.trim();
    }
}