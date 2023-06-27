// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Country
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Country extends org.compiere.model.PO implements I_C_Country, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1162965645L;

    /** Standard Constructor */
    public X_C_Country (final Properties ctx, final int C_Country_ID, @Nullable final String trxName)
    {
      super (ctx, C_Country_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Country (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountTypeLength (final @Nullable java.lang.String AccountTypeLength)
	{
		set_Value (COLUMNNAME_AccountTypeLength, AccountTypeLength);
	}

	@Override
	public java.lang.String getAccountTypeLength() 
	{
		return get_ValueAsString(COLUMNNAME_AccountTypeLength);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setAllowCitiesOutOfList (final boolean AllowCitiesOutOfList)
	{
		set_Value (COLUMNNAME_AllowCitiesOutOfList, AllowCitiesOutOfList);
	}

	@Override
	public boolean isAllowCitiesOutOfList() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowCitiesOutOfList);
	}

	@Override
	public void setCaptureSequence (final @Nullable java.lang.String CaptureSequence)
	{
		set_Value (COLUMNNAME_CaptureSequence, CaptureSequence);
	}

	@Override
	public java.lang.String getCaptureSequence() 
	{
		return get_ValueAsString(COLUMNNAME_CaptureSequence);
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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCountryCode (final java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
		return get_ValueAsString(COLUMNNAME_CountryCode);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDisplaySequence (final java.lang.String DisplaySequence)
	{
		set_Value (COLUMNNAME_DisplaySequence, DisplaySequence);
	}

	@Override
	public java.lang.String getDisplaySequence() 
	{
		return get_ValueAsString(COLUMNNAME_DisplaySequence);
	}

	@Override
	public void setDisplaySequenceLocal (final @Nullable java.lang.String DisplaySequenceLocal)
	{
		set_Value (COLUMNNAME_DisplaySequenceLocal, DisplaySequenceLocal);
	}

	@Override
	public java.lang.String getDisplaySequenceLocal() 
	{
		return get_ValueAsString(COLUMNNAME_DisplaySequenceLocal);
	}

	@Override
	public void setExpressionBankAccountNo (final @Nullable java.lang.String ExpressionBankAccountNo)
	{
		set_Value (COLUMNNAME_ExpressionBankAccountNo, ExpressionBankAccountNo);
	}

	@Override
	public java.lang.String getExpressionBankAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_ExpressionBankAccountNo);
	}

	@Override
	public void setExpressionBankRoutingNo (final @Nullable java.lang.String ExpressionBankRoutingNo)
	{
		set_Value (COLUMNNAME_ExpressionBankRoutingNo, ExpressionBankRoutingNo);
	}

	@Override
	public java.lang.String getExpressionBankRoutingNo() 
	{
		return get_ValueAsString(COLUMNNAME_ExpressionBankRoutingNo);
	}

	@Override
	public void setExpressionPhone (final @Nullable java.lang.String ExpressionPhone)
	{
		set_Value (COLUMNNAME_ExpressionPhone, ExpressionPhone);
	}

	@Override
	public java.lang.String getExpressionPhone() 
	{
		return get_ValueAsString(COLUMNNAME_ExpressionPhone);
	}

	@Override
	public void setExpressionPostal (final @Nullable java.lang.String ExpressionPostal)
	{
		set_Value (COLUMNNAME_ExpressionPostal, ExpressionPostal);
	}

	@Override
	public java.lang.String getExpressionPostal() 
	{
		return get_ValueAsString(COLUMNNAME_ExpressionPostal);
	}

	@Override
	public void setExpressionPostal_Add (final @Nullable java.lang.String ExpressionPostal_Add)
	{
		set_Value (COLUMNNAME_ExpressionPostal_Add, ExpressionPostal_Add);
	}

	@Override
	public java.lang.String getExpressionPostal_Add() 
	{
		return get_ValueAsString(COLUMNNAME_ExpressionPostal_Add);
	}

	@Override
	public void setHasPostal_Add (final boolean HasPostal_Add)
	{
		set_Value (COLUMNNAME_HasPostal_Add, HasPostal_Add);
	}

	@Override
	public boolean isHasPostal_Add() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasPostal_Add);
	}

	@Override
	public void setHasRegion (final boolean HasRegion)
	{
		set_Value (COLUMNNAME_HasRegion, HasRegion);
	}

	@Override
	public boolean isHasRegion() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasRegion);
	}

	@Override
	public void setIsAddressLinesLocalReverse (final boolean IsAddressLinesLocalReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesLocalReverse, IsAddressLinesLocalReverse);
	}

	@Override
	public boolean isAddressLinesLocalReverse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAddressLinesLocalReverse);
	}

	@Override
	public void setIsAddressLinesReverse (final boolean IsAddressLinesReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesReverse, IsAddressLinesReverse);
	}

	@Override
	public boolean isAddressLinesReverse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAddressLinesReverse);
	}

	@Override
	public void setIsEnforceCorrectionInvoice (final boolean IsEnforceCorrectionInvoice)
	{
		set_Value (COLUMNNAME_IsEnforceCorrectionInvoice, IsEnforceCorrectionInvoice);
	}

	@Override
	public boolean isEnforceCorrectionInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEnforceCorrectionInvoice);
	}

	@Override
	public void setMediaSize (final @Nullable java.lang.String MediaSize)
	{
		set_Value (COLUMNNAME_MediaSize, MediaSize);
	}

	@Override
	public java.lang.String getMediaSize() 
	{
		return get_ValueAsString(COLUMNNAME_MediaSize);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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
}