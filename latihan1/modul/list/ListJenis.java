package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListJenis {

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_jenis")
	private String idJenis;

	@SerializedName("kode_jenis")
	private String kodeJenis;

	@SerializedName("nama_jenis")
	private String namaJenis;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setIdJenis(String idJenis){
		this.idJenis = idJenis;
	}

	public String getIdJenis(){
		return idJenis;
	}

	public void setKodeJenis(String kodeJenis){
		this.kodeJenis = kodeJenis;
	}

	public String getKodeJenis(){
		return kodeJenis;
	}

	public void setNamaJenis(String namaJenis){
		this.namaJenis = namaJenis;
	}

	public String getNamaJenis(){
		return namaJenis;
	}
}