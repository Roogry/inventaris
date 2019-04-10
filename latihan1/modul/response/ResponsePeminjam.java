package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListPeminjam;

public class ResponsePeminjam {

	@SerializedName("listItem")
	private List<ListPeminjam> listItem;

	public void setListItem(List<ListPeminjam> listItem){
		this.listItem = listItem;
	}

	public List<ListPeminjam> getListItem(){
		return listItem;
	}
}