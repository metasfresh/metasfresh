// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for API_Response_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_API_Response_Audit extends org.compiere.model.PO implements I_API_Response_Audit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1611110786L;

    /** Standard Constructor */
    public X_API_Response_Audit (final Properties ctx, final int API_Response_Audit_ID, @Nullable final String trxName)
    {
      super (ctx, API_Response_Audit_ID, trxName);
    }

    /** Load Constructor */
    public X_API_Response_Audit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_API_Request_Audit getAPI_Request_Audit()
	{
		return get_ValueAsPO(COLUMNNAME_API_Request_Audit_ID, org.compiere.model.I_API_Request_Audit.class);
	}

	@Override
	public void setAPI_Request_Audit(final org.compiere.model.I_API_Request_Audit API_Request_Audit)
	{
		set_ValueFromPO(COLUMNNAME_API_Request_Audit_ID, org.compiere.model.I_API_Request_Audit.class, API_Request_Audit);
	}

	@Override
	public void setAPI_Request_Audit_ID (final int API_Request_Audit_ID)
	{
		if (API_Request_Audit_ID < 1) 
			set_Value (COLUMNNAME_API_Request_Audit_ID, null);
		else 
			set_Value (COLUMNNAME_API_Request_Audit_ID, API_Request_Audit_ID);
	}

	@Override
	public int getAPI_Request_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Request_Audit_ID);
	}

	@Override
	public void setAPI_Response_Audit_ID (final int API_Response_Audit_ID)
	{
		if (API_Response_Audit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_API_Response_Audit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_API_Response_Audit_ID, API_Response_Audit_ID);
	}

	@Override
	public int getAPI_Response_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Response_Audit_ID);
	}

	@Override
	public void setBody (final @Nullable java.lang.String Body)
	{
		set_Value (COLUMNNAME_Body, Body);
	}

	@Override
	public java.lang.String getBody() 
	{
		return get_ValueAsString(COLUMNNAME_Body);
	}

	@Override
	public void setHttpCode (final @Nullable java.lang.String HttpCode)
	{
		set_Value (COLUMNNAME_HttpCode, HttpCode);
	}

	@Override
	public java.lang.String getHttpCode() 
	{
		return get_ValueAsString(COLUMNNAME_HttpCode);
	}

	@Override
	public void setHttpHeaders (final @Nullable java.lang.String HttpHeaders)
	{
		set_Value (COLUMNNAME_HttpHeaders, HttpHeaders);
	}

	@Override
	public java.lang.String getHttpHeaders() 
	{
		return get_ValueAsString(COLUMNNAME_HttpHeaders);
	}

	@Override
	public void setTime (final @Nullable java.sql.Timestamp Time)
	{
		set_Value (COLUMNNAME_Time, Time);
	}

	@Override
	public java.sql.Timestamp getTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Time);
	}
}