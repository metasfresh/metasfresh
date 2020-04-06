/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for GeocodingConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GeocodingConfig extends org.compiere.model.PO implements I_GeocodingConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1471304629L;

    /** Standard Constructor */
    public X_GeocodingConfig (Properties ctx, int GeocodingConfig_ID, String trxName)
    {
      super (ctx, GeocodingConfig_ID, trxName);
      /** if (GeocodingConfig_ID == 0)
        {
			setcacheCapacity (0); // 200
			setGeocodingConfig_ID (0);
			setosm_millisBetweenRequests (0); // 2000
        } */
    }

    /** Load Constructor */
    public X_GeocodingConfig (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Cache-Kapazität.
		@param cacheCapacity Cache-Kapazität	  */
	@Override
	public void setcacheCapacity (int cacheCapacity)
	{
		set_Value (COLUMNNAME_cacheCapacity, Integer.valueOf(cacheCapacity));
	}

	/** Get Cache-Kapazität.
		@return Cache-Kapazität	  */
	@Override
	public int getcacheCapacity () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cacheCapacity);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geocoding Konfiguration.
		@param GeocodingConfig_ID Geocoding Konfiguration	  */
	@Override
	public void setGeocodingConfig_ID (int GeocodingConfig_ID)
	{
		if (GeocodingConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GeocodingConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GeocodingConfig_ID, Integer.valueOf(GeocodingConfig_ID));
	}

	/** Get Geocoding Konfiguration.
		@return Geocoding Konfiguration	  */
	@Override
	public int getGeocodingConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GeocodingConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * GeocodingProvider AD_Reference_ID=541051
	 * Reference name: Geocoding Providers
	 */
	public static final int GEOCODINGPROVIDER_AD_Reference_ID=541051;
	/** GoogleMaps = GoogleMaps */
	public static final String GEOCODINGPROVIDER_GoogleMaps = "GoogleMaps";
	/** OpenStreetMaps = OpenStreetMaps */
	public static final String GEOCODINGPROVIDER_OpenStreetMaps = "OpenStreetMaps";
	/** Set Geocoding Dienst Provider.
		@param GeocodingProvider Geocoding Dienst Provider	  */
	@Override
	public void setGeocodingProvider (java.lang.String GeocodingProvider)
	{

		set_Value (COLUMNNAME_GeocodingProvider, GeocodingProvider);
	}

	/** Get Geocoding Dienst Provider.
		@return Geocoding Dienst Provider	  */
	@Override
	public java.lang.String getGeocodingProvider () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GeocodingProvider);
	}

	/** Set Google Maps - API-Schlüssel.
		@param gmaps_ApiKey Google Maps - API-Schlüssel	  */
	@Override
	public void setgmaps_ApiKey (java.lang.String gmaps_ApiKey)
	{
		set_Value (COLUMNNAME_gmaps_ApiKey, gmaps_ApiKey);
	}

	/** Get Google Maps - API-Schlüssel.
		@return Google Maps - API-Schlüssel	  */
	@Override
	public java.lang.String getgmaps_ApiKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_gmaps_ApiKey);
	}

	/** Set Open Street Maps - Basis-URL.
		@param osm_baseURL 
		The Base URL after which all parameters are added
	  */
	@Override
	public void setosm_baseURL (java.lang.String osm_baseURL)
	{
		set_Value (COLUMNNAME_osm_baseURL, osm_baseURL);
	}

	/** Get Open Street Maps - Basis-URL.
		@return The Base URL after which all parameters are added
	  */
	@Override
	public java.lang.String getosm_baseURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_osm_baseURL);
	}

	/** Set Open Street Maps - Millisekunden zwischen den Anfragen.
		@param osm_millisBetweenRequests 
		How many milliseconds to wait between 2 consecutive requests
	  */
	@Override
	public void setosm_millisBetweenRequests (int osm_millisBetweenRequests)
	{
		set_Value (COLUMNNAME_osm_millisBetweenRequests, Integer.valueOf(osm_millisBetweenRequests));
	}

	/** Get Open Street Maps - Millisekunden zwischen den Anfragen.
		@return How many milliseconds to wait between 2 consecutive requests
	  */
	@Override
	public int getosm_millisBetweenRequests () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_osm_millisBetweenRequests);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}