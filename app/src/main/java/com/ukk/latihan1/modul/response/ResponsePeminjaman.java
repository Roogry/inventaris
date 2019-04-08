package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListPeminjaman;

public class ResponsePeminjaman{

	@SerializedName("listItem")
	private List<ListPeminjaman> listItem;

	public void setListItem(List<ListPeminjaman> listItem){
		this.listItem = listItem;
	}

	public List<ListPeminjaman> getListItem(){
		return listItem;
	}
}