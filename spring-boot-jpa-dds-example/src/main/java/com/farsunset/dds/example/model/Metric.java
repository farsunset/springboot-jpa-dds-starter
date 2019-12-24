
package com.farsunset.dds.example.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Metric.TABLE_NAME)
public class Metric implements Serializable {

    private transient static final long serialVersionUID = 4733464888738356502L;

    transient static final String TABLE_NAME = "t_monitor_metric";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 6)
    private Integer id;

    @Column(name = "command", length = 1024)
    private String command;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "author", length = 32)
    private String author;

    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
