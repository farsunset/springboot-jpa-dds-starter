
package com.farsunset.dds.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Rule.TABLE_NAME)
public class Rule implements Serializable {

    private transient static final long serialVersionUID = 4733464888738356502L;

    transient static final String TABLE_NAME = "t_monitor_rule";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 6)
    private Integer id;

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "action", length = 1)
    private String action;

    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
