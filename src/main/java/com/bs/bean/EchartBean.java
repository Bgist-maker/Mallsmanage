package com.bs.bean;

import java.util.List;

public class EchartBean {
	private List<String> xAxis;
	private List<String> series;
	public List<String> getxAxis() {
		return xAxis;
	}
	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
}
