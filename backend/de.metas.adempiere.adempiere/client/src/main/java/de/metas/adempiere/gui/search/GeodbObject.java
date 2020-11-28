package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * @author teo.sarca@gmail.com
 */
public class GeodbObject
{
	final private int geodb_loc_id;
	final private String city;
	private String city_7bitlc;
	final private String zip;
	final private double lon;
	final private double lat;
	private int C_Country_ID = -1;
	private String countryName = null;
	//
	private String stringRepresentation = null;
	
	public GeodbObject(int geodb_loc_id, String city, String zip, double lon, double lat)
	{
		super();
		this.geodb_loc_id = geodb_loc_id;
		this.city = city;
		this.zip = zip;
		this.lon = lon;
		this.lat = lat;
	}

	public int getGeodb_loc_id()
	{
		return geodb_loc_id;
	}

	public String getCity()
	{
		return city;
	}

	public String getZip()
	{
		return zip;
	}
	
	public double getLon()
	{
		return lon;
	}

	public double getLat()
	{
		return lat;
	}

	public int getC_Country_ID() {
		return C_Country_ID;
	}

	public void setC_Country_ID(int cCountryID) {
		C_Country_ID = cCountryID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCity_7bitlc() {
		return city_7bitlc;
	}

	public void setCity_7bitlc(String city_7bitlc) {
		this.city_7bitlc = city_7bitlc;
	}
	
	public String getStringRepresentation() {
		return stringRepresentation;
	}

	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (! (obj instanceof GeodbObject))
			return false;
		if (this == obj)
			return true;
		//
		GeodbObject go = (GeodbObject)obj;
		return this.geodb_loc_id == go.geodb_loc_id
		&& this.zip.equals(go.zip)
		;
	}

	@Override
	public String toString()
	{
		String str = getStringRepresentation();
		if (str != null)
			return str;
		return city+", "+zip+" - "+countryName;
	}
}
