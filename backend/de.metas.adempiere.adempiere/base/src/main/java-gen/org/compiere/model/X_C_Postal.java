// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Postal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Postal extends org.compiere.model.PO implements I_C_Postal, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 985183472L;

    /** Standard Constructor */
    public X_C_Postal (final Properties ctx, final int C_Postal_ID, @Nullable final String trxName)
    {
      super (ctx, C_Postal_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Postal (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Org_InCharge_ID (final int AD_Org_InCharge_ID)
	{
		if (AD_Org_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_InCharge_ID, AD_Org_InCharge_ID);
	}

	@Override
	public int getAD_Org_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_InCharge_ID);
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
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, C_City_ID);
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
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
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
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_Region_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public void setDistrict (final @Nullable java.lang.String District)
	{
		set_Value (COLUMNNAME_District, District);
	}

	@Override
	public java.lang.String getDistrict() 
	{
		return get_ValueAsString(COLUMNNAME_District);
	}

	@Override
	public void setIsManual (final boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, IsManual);
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsValidDPD (final boolean IsValidDPD)
	{
		set_Value (COLUMNNAME_IsValidDPD, IsValidDPD);
	}

	@Override
	public boolean isValidDPD() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValidDPD);
	}

	@Override
	public void setNonStdAddress (final boolean NonStdAddress)
	{
		set_Value (COLUMNNAME_NonStdAddress, NonStdAddress);
	}

	@Override
	public boolean isNonStdAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_NonStdAddress);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	@Override
	public void setPostal_Add (final @Nullable java.lang.String Postal_Add)
	{
		set_Value (COLUMNNAME_Postal_Add, Postal_Add);
	}

	@Override
	public java.lang.String getPostal_Add() 
	{
		return get_ValueAsString(COLUMNNAME_Postal_Add);
	}

	@Override
	public void setRegionName (final @Nullable java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	@Override
	public java.lang.String getRegionName() 
	{
		return get_ValueAsString(COLUMNNAME_RegionName);
	}

	@Override
	public void setTownship (final @Nullable java.lang.String Township)
	{
		set_Value (COLUMNNAME_Township, Township);
	}

	@Override
	public java.lang.String getTownship() 
	{
		return get_ValueAsString(COLUMNNAME_Township);
	}
}