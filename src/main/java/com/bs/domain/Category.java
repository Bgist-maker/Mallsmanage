package com.bs.domain;

public class Category {
    private Integer id;

    private String name;

    private Integer maxamount;

    private Integer minamount;
    
    private Integer sellMaxamount;
    
    private Integer sellMinamount;

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(Integer maxamount) {
        this.maxamount = maxamount;
    }

    public Integer getMinamount() {
        return minamount;
    }

    public void setMinamount(Integer minamount) {
        this.minamount = minamount;
    }

	public Integer getSellMaxamount() {
		return sellMaxamount;
	}

	public void setSellMaxamount(Integer sellMaxamount) {
		this.sellMaxamount = sellMaxamount;
	}

	public Integer getSellMinamount() {
		return sellMinamount;
	}

	public void setSellMinamount(Integer sellMinamount) {
		this.sellMinamount = sellMinamount;
	}
}