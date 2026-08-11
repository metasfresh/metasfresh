// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for VATaxID_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_VATaxID_Config extends org.compiere.model.PO implements I_VATaxID_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -517157295L;

    /** Standard Constructor */
    public X_VATaxID_Config (final Properties ctx, final int VATaxID_Config_ID, @Nullable final String trxName)
    {
      super (ctx, VATaxID_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_VATaxID_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsFormatCheckEnabled (final boolean IsFormatCheckEnabled)
	{
		set_Value (COLUMNNAME_IsFormatCheckEnabled, IsFormatCheckEnabled);
	}

	@Override
	public boolean isFormatCheckEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFormatCheckEnabled);
	}

	@Override
	public void setIsVIESCheckEnabled (final boolean IsVIESCheckEnabled)
	{
		set_Value (COLUMNNAME_IsVIESCheckEnabled, IsVIESCheckEnabled);
	}

	@Override
	public boolean isVIESCheckEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVIESCheckEnabled);
	}

	/** 
	 * OnServiceUnavailable AD_Reference_ID=542125
	 * Reference name: USt-IdNr.-Prüfstatus
	 */
	public static final int ONSERVICEUNAVAILABLE_AD_Reference_ID=542125;
	/** NotChecked = NotChecked */
	public static final String ONSERVICEUNAVAILABLE_NotChecked = "NotChecked";
	/** RequestSent = RequestSent */
	public static final String ONSERVICEUNAVAILABLE_RequestSent = "RequestSent";
	/** Valid = Valid */
	public static final String ONSERVICEUNAVAILABLE_Valid = "Valid";
	/** Invalid = Invalid */
	public static final String ONSERVICEUNAVAILABLE_Invalid = "Invalid";
	/** NotSupported = NotSupported */
	public static final String ONSERVICEUNAVAILABLE_NotSupported = "NotSupported";
	/** ServiceUnavailable = ServiceUnavailable */
	public static final String ONSERVICEUNAVAILABLE_ServiceUnavailable = "ServiceUnavailable";
	@Override
	public void setOnServiceUnavailable (final java.lang.String OnServiceUnavailable)
	{
		set_Value (COLUMNNAME_OnServiceUnavailable, OnServiceUnavailable);
	}

	@Override
	public java.lang.String getOnServiceUnavailable() 
	{
		return get_ValueAsString(COLUMNNAME_OnServiceUnavailable);
	}

	@Override
	public void setRecheckAfterDays (final int RecheckAfterDays)
	{
		set_Value (COLUMNNAME_RecheckAfterDays, RecheckAfterDays);
	}

	@Override
	public int getRecheckAfterDays() 
	{
		return get_ValueAsInt(COLUMNNAME_RecheckAfterDays);
	}

	@Override
	public void setRequesterMemberStateCode (final @Nullable java.lang.String RequesterMemberStateCode)
	{
		set_Value (COLUMNNAME_RequesterMemberStateCode, RequesterMemberStateCode);
	}

	@Override
	public java.lang.String getRequesterMemberStateCode() 
	{
		return get_ValueAsString(COLUMNNAME_RequesterMemberStateCode);
	}

	@Override
	public void setRequesterNumber (final @Nullable java.lang.String RequesterNumber)
	{
		set_Value (COLUMNNAME_RequesterNumber, RequesterNumber);
	}

	@Override
	public java.lang.String getRequesterNumber() 
	{
		return get_ValueAsString(COLUMNNAME_RequesterNumber);
	}

	@Override
	public void setRestApiBaseURL (final @Nullable java.lang.String RestApiBaseURL)
	{
		set_Value (COLUMNNAME_RestApiBaseURL, RestApiBaseURL);
	}

	@Override
	public java.lang.String getRestApiBaseURL() 
	{
		return get_ValueAsString(COLUMNNAME_RestApiBaseURL);
	}

	@Override
	public void setVATaxID_Config_ID (final int VATaxID_Config_ID)
	{
		if (VATaxID_Config_ID < 1) 
			set_Value (COLUMNNAME_VATaxID_Config_ID, null);
		else 
			set_Value (COLUMNNAME_VATaxID_Config_ID, VATaxID_Config_ID);
	}

	@Override
	public int getVATaxID_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VATaxID_Config_ID);
	}
}