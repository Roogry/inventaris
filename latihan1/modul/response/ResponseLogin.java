package com.ukk.latihan1.modul.response;

import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListUser;

public class ResponseLogin{

	@SerializedName("listItem")
	private ListUser listItem;

	public void setListItem(ListUser listItem){
		this.listItem = listItem;
	}

	public ListUser getListItem(){
		return listItem;
	}
}