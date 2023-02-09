// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Location
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Location extends org.compiere.model.PO implements I_C_Location, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 710682099L;

    /** Standard Constructor */
    public X_C_Location (final Properties ctx, final int C_Location_ID, @Nullable final String trxName)
    {
      super (ctx, C_Location_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Location (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAddress1 (final @Nullable java.lang.String Address1)
	{
		set_ValueNoCheck (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1() 
	{
		return get_ValueAsString(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (final @Nullable java.lang.String Address2)
	{
		set_ValueNoCheck (COLUMNNAME_Address2, Address2);
	}

	@Override
	public java.lang.String getAddress2() 
	{
		return get_ValueAsString(COLUMNNAME_Address2);
	}

	@Override
	public void setAddress3 (final @Nullable java.lang.String Address3)
	{
		set_ValueNoCheck (COLUMNNAME_Address3, Address3);
	}

	@Override
	public java.lang.String getAddress3() 
	{
		return get_ValueAsString(COLUMNNAME_Address3);
	}

	@Override
	public void setAddress4 (final @Nullable java.lang.String Address4)
	{
		set_ValueNoCheck (COLUMNNAME_Address4, Address4);
	}

	@Override
	public java.lang.String getAddress4() 
	{
		return get_ValueAsString(COLUMNNAME_Address4);
	}

	@Override
	public void setCareOf (final @Nullable java.lang.String CareOf)
	{
		set_ValueNoCheck (COLUMNNAME_CareOf, CareOf);
	}

	@Override
	public java.lang.String getCareOf() 
	{
		return get_ValueAsString(COLUMNNAME_CareOf);
	}

	@Override
	public org.compiere.model.I_C_City getC_City()
	{
		return get_ValueAsPO(COLUMNNAME_C_City_ID, org.compiere.model.I_C_City.class);
	}

	@Override
	public void setC_City(final org.compiere.model.I_C_City C_City)
	{
		set_ValueFromPO(COLUMNNAME_C_City_ID, org.compiere.model.I_C_City.class, C_City);
	}

	@Override
	public void setC_City_ID (final int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_City_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_City_ID, C_City_ID);
	}

	@Override
	public int getC_City_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_City_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_ValueNoCheck (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Postal getC_Postal()
	{
		return get_ValueAsPO(COLUMNNAME_C_Postal_ID, org.compiere.model.I_C_Postal.class);
	}

	@Override
	public void setC_Postal(final org.compiere.model.I_C_Postal C_Postal)
	{
		set_ValueFromPO(COLUMNNAME_C_Postal_ID, org.compiere.model.I_C_Postal.class, C_Postal);
	}

	@Override
	public void setC_Postal_ID (final int C_Postal_ID)
	{
		if (C_Postal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Postal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Postal_ID, C_Postal_ID);
	}

	@Override
	public int getC_Postal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Postal_ID);
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region()
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	@Override
	public void setC_Region_ID (final int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_Region_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setDHL_PostId (final @Nullable java.lang.String DHL_PostId)
	{
		set_Value (COLUMNNAME_DHL_PostId, DHL_PostId);
	}

	@Override
	public java.lang.String getDHL_PostId() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_PostId);
	}

	@Override
	public void setGeocoding_Issue_ID (final int Geocoding_Issue_ID)
	{
		if (Geocoding_Issue_ID < 1) 
			set_Value (COLUMNNAME_Geocoding_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_Geocoding_Issue_ID, Geocoding_Issue_ID);
	}

	@Override
	public int getGeocoding_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Geocoding_Issue_ID);
	}

	/** 
	 * GeocodingStatus AD_Reference_ID=540990
	 * Reference name: GeocodingStatus
	 */
	public static final int GEOCODINGSTATUS_AD_Reference_ID=540990;
	/** NotChecked = N */
	public static final String GEOCODINGSTATUS_NotChecked = "N";
	/** Resolved = R */
	public static final String GEOCODINGSTATUS_Resolved = "R";
	/** NotResolved = X */
	public static final String GEOCODINGSTATUS_NotResolved = "X";
	/** Error = E */
	public static final String GEOCODINGSTATUS_Error = "E";
	@Override
	public void setGeocodingStatus (final java.lang.String GeocodingStatus)
	{
		set_Value (COLUMNNAME_GeocodingStatus, GeocodingStatus);
	}

	@Override
	public java.lang.String getGeocodingStatus() 
	{
		return get_ValueAsString(COLUMNNAME_GeocodingStatus);
	}

	@Override
	public void setHouseNumber (final @Nullable java.lang.String HouseNumber)
	{
		set_Value (COLUMNNAME_HouseNumber, HouseNumber);
	}

	@Override
	public java.lang.String getHouseNumber() 
	{
		return get_ValueAsString(COLUMNNAME_HouseNumber);
	}

	@Override
	public void setIsPOBoxNum (final boolean IsPOBoxNum)
	{
		set_Value (COLUMNNAME_IsPOBoxNum, IsPOBoxNum);
	}

	@Override
	public boolean isPOBoxNum() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPOBoxNum);
	}

	@Override
	public void setIsPostalValidated (final boolean IsPostalValidated)
	{
		set_ValueNoCheck (COLUMNNAME_IsPostalValidated, IsPostalValidated);
	}

	@Override
	public boolean isPostalValidated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPostalValidated);
	}

	@Override
	public void setLatitude (final @Nullable BigDecimal Latitude)
	{
		set_Value (COLUMNNAME_Latitude, Latitude);
	}

	@Override
	public BigDecimal getLatitude() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Latitude);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLongitude (final @Nullable BigDecimal Longitude)
	{
		set_Value (COLUMNNAME_Longitude, Longitude);
	}

	@Override
	public BigDecimal getLongitude() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Longitude);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPOBox (final @Nullable java.lang.String POBox)
	{
		set_ValueNoCheck (COLUMNNAME_POBox, POBox);
	}

	@Override
	public java.lang.String getPOBox() 
	{
		return get_ValueAsString(COLUMNNAME_POBox);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
	{
		set_ValueNoCheck (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	@Override
	public void setPostal_Add (final @Nullable java.lang.String Postal_Add)
	{
		set_ValueNoCheck (COLUMNNAME_Postal_Add, Postal_Add);
	}

	@Override
	public java.lang.String getPostal_Add() 
	{
		return get_ValueAsString(COLUMNNAME_Postal_Add);
	}

	@Override
	public void setRegionName (final @Nullable java.lang.String RegionName)
	{
		set_ValueNoCheck (COLUMNNAME_RegionName, RegionName);
	}

	@Override
	public java.lang.String getRegionName() 
	{
		return get_ValueAsString(COLUMNNAME_RegionName);
	}

	@Override
	public void setStreet (final @Nullable java.lang.String Street)
	{
		set_Value (COLUMNNAME_Street, Street);
	}

	@Override
	public java.lang.String getStreet() 
	{
		return get_ValueAsString(COLUMNNAME_Street);
	}
}