package com.github.model;

import java.io.Serializable;

public class FamousTeacherType implements Serializable {
    private Integer id;

    private String teacherTypeName;

    private String status;

    private Integer seqence;

    private Integer pid;

    private Integer fid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherTypeName() {
        return teacherTypeName;
    }

    public void setTeacherTypeName(String teacherTypeName) {
        this.teacherTypeName = teacherTypeName == null ? null : teacherTypeName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSeqence() {
        return seqence;
    }

    public void setSeqence(Integer seqence) {
        this.seqence = seqence;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
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
        FamousTeacherType other = (FamousTeacherType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTeacherTypeName() == null ? other.getTeacherTypeName() == null : this.getTeacherTypeName().equals(other.getTeacherTypeName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSeqence() == null ? other.getSeqence() == null : this.getSeqence().equals(other.getSeqence()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getFid() == null ? other.getFid() == null : this.getFid().equals(other.getFid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTeacherTypeName() == null) ? 0 : getTeacherTypeName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSeqence() == null) ? 0 : getSeqence().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getFid() == null) ? 0 : getFid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", teacherTypeName=").append(teacherTypeName);
        sb.append(", status=").append(status);
        sb.append(", seqence=").append(seqence);
        sb.append(", pid=").append(pid);
        sb.append(", fid=").append(fid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}