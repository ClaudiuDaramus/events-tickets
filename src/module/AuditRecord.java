package module;

import java.util.Date;

public class AuditRecord {
    private Long id;
    private User user;
    private String tableName;
    private String threadName;
    private Date auditDate;

    public AuditRecord(Long id, User user, String tableName, String threadName, Date auditDate) {
        this.id = id;
        this.user = user;
        this.tableName = tableName;
        this.threadName = threadName;
        this.auditDate = auditDate;
    }

    public AuditRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public String toString() {
        return "AuditRecord{" +
                "id=" + id +
                ", user=" + user +
                ", tableName='" + tableName + '\'' +
                ", threadName='" + threadName + '\'' +
                ", auditDate='" + auditDate + '\'' +
                '}';
    }
}
