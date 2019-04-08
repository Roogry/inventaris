package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListPeminjam {

	@SerializedName("password")
	private String password;

	@SerializedName("nama_peminjam")
	private String namaPeminjam;

	@SerializedName("id_peminjam")
	private String idPeminjam;

	@SerializedName("username")
	private String username;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("status_peminjam")
	private String statusPeminjam;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNamaPeminjam(String namaPeminjam){
		this.namaPeminjam = namaPeminjam;
	}

	public String getNamaPeminjam(){
		return namaPeminjam;
	}

	public void setIdPeminjam(String idPeminjam){
		this.idPeminjam = idPeminjam;
	}

	public String getIdPeminjam(){
		return idPeminjam;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setStatusPeminjam(String statusPeminjam){
		this.statusPeminjam = statusPeminjam;
	}

	public String getStatusPeminjam(){
		return statusPeminjam;
	}
}