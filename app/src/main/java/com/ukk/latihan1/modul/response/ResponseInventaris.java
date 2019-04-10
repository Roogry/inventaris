package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListInventaris;

public class ResponseInventaris{

	@SerializedName("listItem")
	private List<ListInventaris> listItem;

	public void setListItem(List<ListInventaris> listItem){
		this.listItem = listItem;
	}

	public List<ListInventaris> getListItem(){
		return listItem;
	}
}