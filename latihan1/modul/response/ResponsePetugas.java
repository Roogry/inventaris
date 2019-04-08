package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListPetugas;

public class ResponsePetugas{

	@SerializedName("listItem")
	private List<ListPetugas> listItem;

	public void setListItem(List<ListPetugas> listItem){
		this.listItem = listItem;
	}

	public List<ListPetugas> getListItem(){
		return listItem;
	}
}