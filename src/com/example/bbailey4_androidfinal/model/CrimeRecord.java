package com.example.bbailey4_androidfinal.model;

public class CrimeRecord {

	// Fields
	private String caseNum;
	private String dateOf;
	private String block;
	private String iucr;
	private String primaryDesc;
	private String secondaryDesc;
	private String locationDesc;
	private String arrest;
	private String domestic;
	private String beat;
	private String ward;
	private String fbi_cd;
	private double latitude;
	private double longitude;
	
	
	// Constructors
	public CrimeRecord() {
		
	}

	public CrimeRecord(String caseNum, String dateOf, String block,
			String iucr, String primaryDesc, String secondaryDesc,
			String locationDesc, String arrest, String domestic, String beat,
			String ward, String fbi_cd, double latitude, double longitude) {
		this.caseNum = caseNum;
		this.dateOf = dateOf;
		this.block = block;
		this.iucr = iucr;
		this.primaryDesc = primaryDesc;
		this.secondaryDesc = secondaryDesc;
		this.locationDesc = locationDesc;
		this.arrest = arrest;
		this.domestic = domestic;
		this.beat = beat;
		this.ward = ward;
		this.fbi_cd = fbi_cd;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	// Getters and Setters
	/**
	 * @return the caseNum
	 */
	public String getCaseNum() {
		return caseNum;
	}

	/**
	 * @param caseNum the caseNum to set
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	/**
	 * @return the dateOf
	 */
	public String getDateOf() {
		return dateOf;
	}

	/**
	 * @param dateOf the dateOf to set
	 */
	public void setDateOf(String dateOf) {
		this.dateOf = dateOf;
	}

	/**
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * @return the iucr
	 */
	public String getIucr() {
		return iucr;
	}

	/**
	 * @param iucr the iucr to set
	 */
	public void setIucr(String iucr) {
		this.iucr = iucr;
	}

	/**
	 * @return the primaryDesc
	 */
	public String getPrimaryDesc() {
		return primaryDesc;
	}

	/**
	 * @param primaryDesc the primaryDesc to set
	 */
	public void setPrimaryDesc(String primaryDesc) {
		this.primaryDesc = primaryDesc;
	}

	/**
	 * @return the secondaryDesc
	 */
	public String getSecondaryDesc() {
		return secondaryDesc;
	}

	/**
	 * @param secondaryDesc the secondaryDesc to set
	 */
	public void setSecondaryDesc(String secondaryDesc) {
		this.secondaryDesc = secondaryDesc;
	}

	/**
	 * @return the locationDesc
	 */
	public String getLocationDesc() {
		return locationDesc;
	}

	/**
	 * @param locationDesc the locationDesc to set
	 */
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	/**
	 * @return the arrest
	 */
	public String getArrest() {
		return arrest;
	}

	/**
	 * @param arrest the arrest to set
	 */
	public void setArrest(String arrest) {
		this.arrest = arrest;
	}

	/**
	 * @return the domestic
	 */
	public String getDomestic() {
		return domestic;
	}

	/**
	 * @param domestic the domestic to set
	 */
	public void setDomestic(String domestic) {
		this.domestic = domestic;
	}

	/**
	 * @return the beat
	 */
	public String getBeat() {
		return beat;
	}

	/**
	 * @param beat the beat to set
	 */
	public void setBeat(String beat) {
		this.beat = beat;
	}

	/**
	 * @return the ward
	 */
	public String getWard() {
		return ward;
	}

	/**
	 * @param ward the ward to set
	 */
	public void setWard(String ward) {
		this.ward = ward;
	}

	/**
	 * @return the fbi_cd
	 */
	public String getFbi_cd() {
		return fbi_cd;
	}

	/**
	 * @param fbi_cd the fbi_cd to set
	 */
	public void setFbi_cd(String fbi_cd) {
		this.fbi_cd = fbi_cd;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param double latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @param String latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = Double.valueOf(latitude).doubleValue();
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param double longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * @param String longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = Double.valueOf(longitude).doubleValue();
	}

	
	// toString Method
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CrimeRecord [caseNum=" + caseNum + ", dateOf=" + dateOf
				+ ", block=" + block + ", iucr=" + iucr + ", primaryDesc="
				+ primaryDesc + ", secondaryDesc=" + secondaryDesc
				+ ", locationDesc=" + locationDesc + ", arrest=" + arrest
				+ ", domestic=" + domestic + ", beat=" + beat + ", ward="
				+ ward + ", fbi_cd=" + fbi_cd + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
	
} //end CrimeRecord
