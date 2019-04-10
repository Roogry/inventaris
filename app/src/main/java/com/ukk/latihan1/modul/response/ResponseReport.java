package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListReport;

public class ResponseReport{

	@SerializedName("listItem")
	private List<ListReport> listItem;

	public void setListItem(List<ListReport> listItem){
		this.listItem = listItem;
	}

	public List<ListReport> getListItem(){
		return listItem;
	}
}