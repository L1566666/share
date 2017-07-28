package com.share.modules.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

import com.share.modules.entity.parentEntity.IdEntity;

@Entity
@Table(name = "share")
public class Share extends IdEntity implements Serializable{

	@Column(name = "creator_id")
	private Long creatorId;		//分享者id

	@Column(name = "email")
	private String email ;	//用户email

	//暂时不使用
//	@Column(name = "share_subject")
//	private String shareSubject;		//分享的主题

	@Column(name = "share_describe")
	private String shareDescribe;	//分享描述

	@Column(name = "share_url")
	private String shareUrl;	//分享链接

	@Column(name = "support_amount")
	private Long supportAmount;	//点赞总数

	@Column(name = "share_type")
	private Integer shareType;	//1代表随心分享,2代表软件,3代表视频,

	@Column(name = "step_amount")
	public Long stepAmount;	//踩的数量

	public Share(){

	}



	public Share(Long creatorId, String email, String shareDescribe, String shareUrl, Long supportAmount, Long stepAmount, Integer shareType) {
		this.creatorId = creatorId;
		this.email = email;
		this.shareDescribe = shareDescribe;
		this.shareUrl = shareUrl;
		this.supportAmount = supportAmount;
		this.stepAmount = stepAmount;
		this.shareType = shareType;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setShareDescribe(String shareDescribe) {
		this.shareDescribe = shareDescribe;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public void setSupportAmount(Long supportAmount) {
		this.supportAmount = supportAmount;
	}

	public void setStepAmount(Long stepAmount) {
		this.stepAmount = stepAmount;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public String getEmail() {
		return email;
	}

	public String getShareDescribe() {
		return shareDescribe;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public Long getSupportAmount() {
		return supportAmount;
	}

	public Long getStepAmount() {
		return stepAmount;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}



}
