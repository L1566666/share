package com.share.modules.entity;

import com.share.modules.entity.parentEntity.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lin on 2017/7/8 19:29.
 */
@Entity
@Table(name = "token")
public class Token{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;    //tokenId

    @Column(name = "user_id")
    private Long userId;    //userId

    @Column(name = "ip")
    private String ip;  //客户端电脑ip

    @Column(name = "mac_address")
    private String macAddress;  //客户端电脑网卡mac地址

    @Column(name = "token")
    private String token;   //根据登录时间戳+用户id+用户名 ,md5加密后的字符串

    @Column(name = "create_date")
    private Date createDate;	//创建时间

    @Column(name = "update_date")
    private Date updateDate;	//修改时间

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {

        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
