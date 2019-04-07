/**
 * 
 */
package com.incedo.commandVOs;

/**
 * @author deb
 *
 */
public class ExperimentVariantVo {
	private String variantToken;
	private String bucket;
	private int variantId;
	private int expId;
	private String exptName;
	private String channelName;
	private String layerName;
	
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * @return the layerName
	 */
	public String getLayerName() {
		return layerName;
	}
	/**
	 * @param layerName the layerName to set
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	/**
	 * @return the exptName
	 */
	public String getExptName() {
		return exptName;
	}
	/**
	 * @param exptName the exptName to set
	 */
	public void setExptName(String exptName) {
		this.exptName = exptName;
	}
	/**
	 * @return the variantToken
	 */
	public String getVariantToken() {
		return variantToken;
	}
	/**
	 * @param variantToken the variantToken to set
	 */
	public void setVariantToken(String variantToken) {
		this.variantToken = variantToken;
	}
	/**
	 * @return the bucket
	 */
	public String getBucket() {
		return bucket;
	}
	/**
	 * @param bucket the bucket to set
	 */
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	/**
	 * @return the variantId
	 */
	public int getVariantId() {
		return variantId;
	}
	/**
	 * @param variantId the variantId to set
	 */
	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}
	/**
	 * @return the expId
	 */
	public int getExpId() {
		return expId;
	}
	/**
	 * @param expId the expId to set
	 */
	public void setExpId(int expId) {
		this.expId = expId;
	}
	
}
