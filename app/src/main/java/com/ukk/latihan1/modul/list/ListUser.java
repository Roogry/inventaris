package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListUser {

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_level")
	private String idLevel;

	@SerializedName("id")
	private String id;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("username")
	private String username;

	@SerializedName("status_peminjam")
	private String statusPeminjam;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdLevel(String idLevel){
		this.idLevel = idLevel;
	}

	public String getIdLevel(){
		return idLevel;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatusPeminjam(String statusPeminjam){
		this.statusPeminjam = statusPeminjam;
	}

	public String getStatusPeminjam(){
		return statusPeminjam;
	}
}