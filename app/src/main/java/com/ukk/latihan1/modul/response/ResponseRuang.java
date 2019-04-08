package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListRuang;

public class ResponseRuang{

	@SerializedName("listItem")
	private List<ListRuang> listItem;

	public void setListItem(List<ListRuang> listItem){
		this.listItem = listItem;
	}

	public List<ListRuang> getListItem(){
		return listItem;
	}
}