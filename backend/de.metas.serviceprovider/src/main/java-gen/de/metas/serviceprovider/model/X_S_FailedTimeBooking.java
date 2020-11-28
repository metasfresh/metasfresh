/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_FailedTimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_FailedTimeBooking extends org.compiere.model.PO implements I_S_FailedTimeBooking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 519124833L;

    /** Standard Constructor */
    public X_S_FailedTimeBooking (Properties ctx, int S_FailedTimeBooking_ID, String trxName)
    {
      super (ctx, S_FailedTimeBooking_ID, trxName);
    }

    /** Load Constructor */
    public X_S_FailedTimeBooking (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Everhour = Everhour */
	public static final String EXTERNALSYSTEM_Everhour = "Everhour";
	@Override
	public void setExternalSystem (java.lang.String ExternalSystem)
	{

		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	@Override
	public java.lang.String getExternalSystem() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalSystem);
	}

	@Override
	public void setImportErrorMsg (java.lang.String ImportErrorMsg)
	{
		set_Value (COLUMNNAME_ImportErrorMsg, ImportErrorMsg);
	}

	@Override
	public java.lang.String getImportErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportErrorMsg);
	}

	@Override
	public void setJSONValue (java.lang.String JSONValue)
	{
		set_Value (COLUMNNAME_JSONValue, JSONValue);
	}

	@Override
	public java.lang.String getJSONValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JSONValue);
	}

	@Override
	public void setS_FailedTimeBooking_ID (int S_FailedTimeBooking_ID)
	{
		if (S_FailedTimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, Integer.valueOf(S_FailedTimeBooking_ID));
	}

	@Override
	public int getS_FailedTimeBooking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_FailedTimeBooking_ID);
	}
}