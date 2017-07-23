package com.share.modules.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lin on 2017/7/20 15:31.
 */
@Entity
@Table(name = "support_or_step")
public class SupportOrStep {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;    //用户id

    @Column(name = "share_id")
    private Long shareId;   //分享帖子id

    @Column(name = "support_or_step")
    private Integer supportOrStep ;     //1代表赞,2代表踩

    @Column(name = "create_date")
    private Date createDate;	//创建时间

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public void setSupportOrStep(Integer supportOrStep) {
        this.supportOrStep = supportOrStep;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getShareId() {
        return shareId;
    }

    public Integer getSupportOrStep() {
        return supportOrStep;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
