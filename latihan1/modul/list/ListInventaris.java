package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListInventaris {

	@SerializedName("kode_inventaris")
	private String kodeInventaris;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_inventaris")
	private String idInventaris;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kondisi")
	private String kondisi;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("id_jenis")
	private String idJenis;

	@SerializedName("id_ruang")
	private String idRuang;

	@SerializedName("tanggal_register")
	private String tanggalRegister;

	@SerializedName("id_petugas")
	private String idPetugas;

	public void setKodeInventaris(String kodeInventaris){
		this.kodeInventaris = kodeInventaris;
	}

	public String getKodeInventaris(){
		return kodeInventaris;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setIdInventaris(String idInventaris){
		this.idInventaris = idInventaris;
	}

	public String getIdInventaris(){
		return idInventaris;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKondisi(String kondisi){
		this.kondisi = kondisi;
	}

	public String getKondisi(){
		return kondisi;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setIdJenis(String idJenis){
		this.idJenis = idJenis;
	}

	public String getIdJenis(){
		return idJenis;
	}

	public void setIdRuang(String idRuang){
		this.idRuang = idRuang;
	}

	public String getIdRuang(){
		return idRuang;
	}

	public void setTanggalRegister(String tanggalRegister){
		this.tanggalRegister = tanggalRegister;
	}

	public String getTanggalRegister(){
		return tanggalRegister;
	}

	public void setIdPetugas(String idPetugas){
		this.idPetugas = idPetugas;
	}

	public String getIdPetugas(){
		return idPetugas;
	}
}