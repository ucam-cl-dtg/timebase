package uk.ac.cam.cl.dtg.android.time.rail;

public class TrainService {

	public String originName;
	public String originCRS;
	public String destName;
	public String destCRS;
	public String via;
	
	public String sta;
	public String eta;
	public	String std;
	public String etd;
	
	public String platform;
	public String TOC;
	public String serviceID;
	
	boolean isCircular = false;
	
	public boolean isCircular() {
		return isCircular;
	}
	public void setCircular(boolean isCircular) {
		this.isCircular = isCircular;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getOriginCRS() {
		return originCRS;
	}
	public void setOriginCRS(String originCRS) {
		this.originCRS = originCRS;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getDestCRS() {
		return destCRS;
	}
	public void setDestCRS(String destCRS) {
		this.destCRS = destCRS;
	}
	public String getSta() {
		return sta;
	}
	public void setSta(String sta) {
		this.sta = sta;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getTOC() {
		return TOC;
	}
	public void setTOC(String toc) {
		TOC = toc;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	
	public String getStd() {
		return std;
	}
	public void setStd(String std) {
		this.std = std;
	}
	public String getEtd() {
		return etd;
	}
	public void setEtd(String etd) {
		this.etd = etd;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String toString() {
		return "[" + sta + "] "+originName+"-"+destName + "("+eta+") Operated by "+TOC+" platform? "+platform;
	}

	
}
