package com.yahoo.hackday.yummier.models;

import java.io.Serializable;

/**
 * Bean that represents the set of search filters
 * the user can use when performing a search
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class SearchFilters implements Serializable {

	private static final long serialVersionUID = -1418877620593254904L;
	
	private String imageSize = null;
	private String colorFilter = null;
	private String imageType = null;
	private String siteFilter = null;
	
	/** 
	 * Default constructor
	 */
	public SearchFilters() { }

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getSiteFilter() {
		return siteFilter;
	}

	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	
	@Override
	public String toString() {
		return "SearchFilters: \n" +
				" - ImageSize=[" + this.imageSize + "]\n" 
				+ " - ColorFilter=[" + this.colorFilter + "]\n" 
				+ " - ImageType=[" + this.imageType + "]\n" 
				+ " - SiteFilter=[" + this.siteFilter + "]";				
	}
	
}
