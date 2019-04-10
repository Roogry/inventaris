package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListPetugas {

	@SerializedName("password")
	private String password;

	@SerializedName("nama_petugas")
	private String namaPetugas;

	@SerializedName("id_petugas")
	private String idPetugas;

	@SerializedName("id_level")
	private String idLevel;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNamaPetugas(String namaPetugas){
		this.namaPetugas = namaPetugas;
	}

	public String getNamaPetugas(){
		return namaPetugas;
	}

	public void setIdPetugas(String idPetugas){
		this.idPetugas = idPetugas;
	}

	public String getIdPetugas(){
		return idPetugas;
	}

	public void setIdLevel(String idLevel){
		this.idLevel = idLevel;
	}

	public String getIdLevel(){
		return idLevel;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}