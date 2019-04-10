package com.ukk.latihan1.modul.list;

import com.google.gson.annotations.SerializedName;

public class ListReport {

	@SerializedName("masih_terpinjam")
	private String masihTerpinjam;

	@SerializedName("sudah_dikembalikan")
	private String sudahDikembalikan;

	@SerializedName("inventaris")
	private String inventaris;

	@SerializedName("total_terpinjam")
	private String totalTerpinjam;

	@SerializedName("petugas")
	private String petugas;

	public void setMasihTerpinjam(String masihTerpinjam){
		this.masihTerpinjam = masihTerpinjam;
	}

	public String getMasihTerpinjam(){
		return masihTerpinjam;
	}

	public void setSudahDikembalikan(String sudahDikembalikan){
		this.sudahDikembalikan = sudahDikembalikan;
	}

	public String getSudahDikembalikan(){
		return sudahDikembalikan;
	}

	public void setInventaris(String inventaris){
		this.inventaris = inventaris;
	}

	public String getInventaris(){
		return inventaris;
	}

	public void setTotalTerpinjam(String totalTerpinjam){
		this.totalTerpinjam = totalTerpinjam;
	}

	public String getTotalTerpinjam(){
		return totalTerpinjam;
	}

	public void setPetugas(String petugas){
		this.petugas = petugas;
	}

	public String getPetugas(){
		return petugas;
	}
}