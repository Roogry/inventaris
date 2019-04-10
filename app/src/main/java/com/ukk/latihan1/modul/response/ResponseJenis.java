package com.ukk.latihan1.modul.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ukk.latihan1.modul.list.ListJenis;

public class ResponseJenis{

	@SerializedName("listItem")
	private List<ListJenis> listJenis;

	public void setListItem(List<ListJenis> listItem){
		this.listJenis = listJenis;
	}

	public List<ListJenis> getListItem(){
		return listJenis;
	}
}