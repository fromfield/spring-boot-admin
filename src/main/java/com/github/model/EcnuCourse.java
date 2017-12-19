package com.github.model;

import java.io.Serializable;
import java.util.Date;

public class EcnuCourse implements Serializable {
    private Integer id;

    private Integer courseId;

    private Integer uid;

    private String uname;

    private Integer fid;

    private String fname;

    private String name;

    private String cover;

    private Integer hour;

    private Integer enroll;

    private Integer featureTemplateId;

    private Float average;

    private Date checkTime;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer online;

    private Integer typeId;

    private Integer teacherHour;

    private Integer teacherHourFlag;

    private String description;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    public Integer getFeatureTemplateId() {
        return featureTemplateId;
    }

    public void setFeatureTemplateId(Integer featureTemplateId) {
        this.featureTemplateId = featureTemplateId;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTeacherHour() {
        return teacherHour;
    }

    public void setTeacherHour(Integer teacherHour) {
        this.teacherHour = teacherHour;
    }

    public Integer getTeacherHourFlag() {
        return teacherHourFlag;
    }

    public void setTeacherHourFlag(Integer teacherHourFlag) {
        this.teacherHourFlag = teacherHourFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EcnuCourse other = (EcnuCourse) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getFid() == null ? other.getFid() == null : this.getFid().equals(other.getFid()))
            && (this.getFname() == null ? other.getFname() == null : this.getFname().equals(other.getFname()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
            && (this.getHour() == null ? other.getHour() == null : this.getHour().equals(other.getHour()))
            && (this.getEnroll() == null ? other.getEnroll() == null : this.getEnroll().equals(other.getEnroll()))
            && (this.getFeatureTemplateId() == null ? other.getFeatureTemplateId() == null : this.getFeatureTemplateId().equals(other.getFeatureTemplateId()))
            && (this.getAverage() == null ? other.getAverage() == null : this.getAverage().equals(other.getAverage()))
            && (this.getCheckTime() == null ? other.getCheckTime() == null : this.getCheckTime().equals(other.getCheckTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOnline() == null ? other.getOnline() == null : this.getOnline().equals(other.getOnline()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getTeacherHour() == null ? other.getTeacherHour() == null : this.getTeacherHour().equals(other.getTeacherHour()))
            && (this.getTeacherHourFlag() == null ? other.getTeacherHourFlag() == null : this.getTeacherHourFlag().equals(other.getTeacherHourFlag()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getFid() == null) ? 0 : getFid().hashCode());
        result = prime * result + ((getFname() == null) ? 0 : getFname().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getHour() == null) ? 0 : getHour().hashCode());
        result = prime * result + ((getEnroll() == null) ? 0 : getEnroll().hashCode());
        result = prime * result + ((getFeatureTemplateId() == null) ? 0 : getFeatureTemplateId().hashCode());
        result = prime * result + ((getAverage() == null) ? 0 : getAverage().hashCode());
        result = prime * result + ((getCheckTime() == null) ? 0 : getCheckTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOnline() == null) ? 0 : getOnline().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getTeacherHour() == null) ? 0 : getTeacherHour().hashCode());
        result = prime * result + ((getTeacherHourFlag() == null) ? 0 : getTeacherHourFlag().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", uid=").append(uid);
        sb.append(", uname=").append(uname);
        sb.append(", fid=").append(fid);
        sb.append(", fname=").append(fname);
        sb.append(", name=").append(name);
        sb.append(", cover=").append(cover);
        sb.append(", hour=").append(hour);
        sb.append(", enroll=").append(enroll);
        sb.append(", featureTemplateId=").append(featureTemplateId);
        sb.append(", average=").append(average);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", online=").append(online);
        sb.append(", typeId=").append(typeId);
        sb.append(", teacherHour=").append(teacherHour);
        sb.append(", teacherHourFlag=").append(teacherHourFlag);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}