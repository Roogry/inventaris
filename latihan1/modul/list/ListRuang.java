package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListRuang {

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_ruang")
	private String idRuang;

	@SerializedName("nama_ruang")
	private String namaRuang;

	@SerializedName("kode_ruang")
	private String kodeRuang;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setIdRuang(String idRuang){
		this.idRuang = idRuang;
	}

	public String getIdRuang(){
		return idRuang;
	}

	public void setNamaRuang(String namaRuang){
		this.namaRuang = namaRuang;
	}

	public String getNamaRuang(){
		return namaRuang;
	}

	public void setKodeRuang(String kodeRuang){
		this.kodeRuang = kodeRuang;
	}

	public String getKodeRuang(){
		return kodeRuang;
	}
}