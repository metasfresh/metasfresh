// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for VATaxID_CheckLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_VATaxID_CheckLog extends org.compiere.model.PO implements I_VATaxID_CheckLog, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1435818294L;

    /** Standard Constructor */
    public X_VATaxID_CheckLog (final Properties ctx, final int VATaxID_CheckLog_ID, @Nullable final String trxName)
    {
      super (ctx, VATaxID_CheckLog_ID, trxName);
    }

    /** Load Constructor */
    public X_VATaxID_CheckLog (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public void setAD_Session_ID (final int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, AD_Session_ID);
	}

	@Override
	public int getAD_Session_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setRawResponse (final @Nullable java.lang.String RawResponse)
	{
		set_ValueNoCheck (COLUMNNAME_RawResponse, RawResponse);
	}

	@Override
	public java.lang.String getRawResponse() 
	{
		return get_ValueAsString(COLUMNNAME_RawResponse);
	}

	@Override
	public void setRequestDate (final java.sql.Timestamp RequestDate)
	{
		set_ValueNoCheck (COLUMNNAME_RequestDate, RequestDate);
	}

	@Override
	public java.sql.Timestamp getRequestDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_RequestDate);
	}

	@Override
	public void setRequestIdentifier (final @Nullable java.lang.String RequestIdentifier)
	{
		set_ValueNoCheck (COLUMNNAME_RequestIdentifier, RequestIdentifier);
	}

	@Override
	public java.lang.String getRequestIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_RequestIdentifier);
	}

	@Override
	public void setResponseDate (final @Nullable java.sql.Timestamp ResponseDate)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseDate, ResponseDate);
	}

	@Override
	public java.sql.Timestamp getResponseDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ResponseDate);
	}

	@Override
	public void setReturnedAddress (final @Nullable java.lang.String ReturnedAddress)
	{
		set_ValueNoCheck (COLUMNNAME_ReturnedAddress, ReturnedAddress);
	}

	@Override
	public java.lang.String getReturnedAddress() 
	{
		return get_ValueAsString(COLUMNNAME_ReturnedAddress);
	}

	@Override
	public void setReturnedName (final @Nullable java.lang.String ReturnedName)
	{
		set_ValueNoCheck (COLUMNNAME_ReturnedName, ReturnedName);
	}

	@Override
	public java.lang.String getReturnedName() 
	{
		return get_ValueAsString(COLUMNNAME_ReturnedName);
	}

	@Override
	public void setTraderAddressMatch (final @Nullable java.lang.String TraderAddressMatch)
	{
		set_ValueNoCheck (COLUMNNAME_TraderAddressMatch, TraderAddressMatch);
	}

	@Override
	public java.lang.String getTraderAddressMatch() 
	{
		return get_ValueAsString(COLUMNNAME_TraderAddressMatch);
	}

	@Override
	public void setTraderNameMatch (final @Nullable java.lang.String TraderNameMatch)
	{
		set_ValueNoCheck (COLUMNNAME_TraderNameMatch, TraderNameMatch);
	}

	@Override
	public java.lang.String getTraderNameMatch() 
	{
		return get_ValueAsString(COLUMNNAME_TraderNameMatch);
	}

	@Override
	public void setVATaxID (final java.lang.String VATaxID)
	{
		set_ValueNoCheck (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return get_ValueAsString(COLUMNNAME_VATaxID);
	}

	@Override
	public void setVATaxID_CheckLog_ID (final int VATaxID_CheckLog_ID)
	{
		if (VATaxID_CheckLog_ID < 1) 
			set_Value (COLUMNNAME_VATaxID_CheckLog_ID, null);
		else 
			set_Value (COLUMNNAME_VATaxID_CheckLog_ID, VATaxID_CheckLog_ID);
	}

	@Override
	public int getVATaxID_CheckLog_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VATaxID_CheckLog_ID);
	}

	/** 
	 * VATaxIDStatus AD_Reference_ID=542125
	 * Reference name: USt-IdNr.-Prüfstatus
	 */
	public static final int VATAXIDSTATUS_AD_Reference_ID=542125;
	/** NotChecked = NotChecked */
	public static final String VATAXIDSTATUS_NotChecked = "NotChecked";
	/** RequestSent = RequestSent */
	public static final String VATAXIDSTATUS_RequestSent = "RequestSent";
	/** Valid = Valid */
	public static final String VATAXIDSTATUS_Valid = "Valid";
	/** Invalid = Invalid */
	public static final String VATAXIDSTATUS_Invalid = "Invalid";
	/** NotSupported = NotSupported */
	public static final String VATAXIDSTATUS_NotSupported = "NotSupported";
	/** ServiceUnavailable = ServiceUnavailable */
	public static final String VATAXIDSTATUS_ServiceUnavailable = "ServiceUnavailable";
	@Override
	public void setVATaxIDStatus (final java.lang.String VATaxIDStatus)
	{
		set_ValueNoCheck (COLUMNNAME_VATaxIDStatus, VATaxIDStatus);
	}

	@Override
	public java.lang.String getVATaxIDStatus() 
	{
		return get_ValueAsString(COLUMNNAME_VATaxIDStatus);
	}
}