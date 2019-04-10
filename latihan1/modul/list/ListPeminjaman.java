package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListPeminjaman {

	@SerializedName("id_peminjaman")
	private String idPeminjaman;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("tanggal_pinjam")
	private String tanggalPinjam;

	@SerializedName("tanggal_kembali")
	private String tanggalKembali;

	@SerializedName("id_peminjam")
	private String idPeminjam;

	@SerializedName("kode_peminjaman")
	private String kodePeminjaman;

	@SerializedName("nama_peminjam")
	private String namaPeminjam;

	@SerializedName("status_peminjaman")
	private String statusPeminjaman;

	public void setIdPeminjaman(String idPeminjaman){
		this.idPeminjaman = idPeminjaman;
	}

	public String getIdPeminjaman(){
		return idPeminjaman;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setTanggalPinjam(String tanggalPinjam){
		this.tanggalPinjam = tanggalPinjam;
	}

	public String getTanggalPinjam(){
		return tanggalPinjam;
	}

	public void setTanggalKembali(String tanggalKembali){
		this.tanggalKembali = tanggalKembali;
	}

	public String getTanggalKembali(){
		return tanggalKembali;
	}

	public void setIdPeminjam(String idPeminjam){
		this.idPeminjam = idPeminjam;
	}

	public String getIdPeminjam(){
		return idPeminjam;
	}

	public void setKodePeminjaman(String kodePeminjaman){
		this.kodePeminjaman = kodePeminjaman;
	}

	public String getKodePeminjaman(){
		return kodePeminjaman;
	}

	public void setNamaPeminjam(String namaPeminjam){
		this.namaPeminjam = namaPeminjam;
	}

	public String getNamaPeminjam(){
		return namaPeminjam;
	}

	public void setStatusPeminjaman(String statusPeminjaman){
		this.statusPeminjaman = statusPeminjaman;
	}

	public String getStatusPeminjaman(){
		return statusPeminjaman;
	}
}