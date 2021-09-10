// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for API_Request_Audit_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_API_Request_Audit_Log extends org.compiere.model.PO implements I_API_Request_Audit_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 419451654L;

    /** Standard Constructor */
    public X_API_Request_Audit_Log (final Properties ctx, final int API_Request_Audit_Log_ID, @Nullable final String trxName)
    {
      super (ctx, API_Request_Audit_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_API_Request_Audit_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
	public void setAPI_Request_Audit_Log_ID (final int API_Request_Audit_Log_ID)
	{
		if (API_Request_Audit_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_API_Request_Audit_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_API_Request_Audit_Log_ID, API_Request_Audit_Log_ID);
	}

	@Override
	public int getAPI_Request_Audit_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Request_Audit_Log_ID);
	}

	@Override
	public void setLogmessage (final @Nullable java.lang.String Logmessage)
	{
		set_Value (COLUMNNAME_Logmessage, Logmessage);
	}

	@Override
	public java.lang.String getLogmessage() 
	{
		return get_ValueAsString(COLUMNNAME_Logmessage);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setTrxName (final @Nullable java.lang.String TrxName)
	{
		set_Value (COLUMNNAME_TrxName, TrxName);
	}

	@Override
	public java.lang.String getTrxName() 
	{
		return get_ValueAsString(COLUMNNAME_TrxName);
	}

	/** 
	 * Type AD_Reference_ID=541329
	 * Reference name: TypeList
	 */
	public static final int TYPE_AD_Reference_ID=541329;
	/** Created = Created */
	public static final String TYPE_Created = "Created";
	/** Updated = Updated */
	public static final String TYPE_Updated = "Updated";
	/** Deleted = Deleted */
	public static final String TYPE_Deleted = "Deleted";
	@Override
	public void setType (final @Nullable java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}