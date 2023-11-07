package com.bs.domain;

public class Message {
    private Integer id;

    private Integer userid;

    private String message;

    private Integer objecttype;

    private Integer buyid;

    private Integer sellerid;

    private Integer isdeal;
    
    private User user;
    
    private Buyer buyer;
    
    private Seller seller;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getObjecttype() {
        return objecttype;
    }

    public void setObjecttype(Integer objecttype) {
        this.objecttype = objecttype;
    }

    public Integer getBuyid() {
        return buyid;
    }

    public void setBuyid(Integer buyid) {
        this.buyid = buyid;
    }

    public Integer getSellerid() {
        return sellerid;
    }

    public void setSellerid(Integer sellerid) {
        this.sellerid = sellerid;
    }

    public Integer getIsdeal() {
        return isdeal;
    }

    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}