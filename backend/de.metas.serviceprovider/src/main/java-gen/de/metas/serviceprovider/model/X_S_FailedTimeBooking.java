// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_FailedTimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_FailedTimeBooking extends org.compiere.model.PO implements I_S_FailedTimeBooking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1027400139L;

    /** Standard Constructor */
    public X_S_FailedTimeBooking (final Properties ctx, final int S_FailedTimeBooking_ID, @Nullable final String trxName)
    {
      super (ctx, S_FailedTimeBooking_ID, trxName);
    }

    /** Load Constructor */
    public X_S_FailedTimeBooking (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
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
	/** ALBERTA = ALBERTA */
	public static final String EXTERNALSYSTEM_ALBERTA = "ALBERTA";
	/** Shopware6 = Shopware6 */
	public static final String EXTERNALSYSTEM_Shopware6 = "Shopware6";
	/** Other = Other */
	public static final String EXTERNALSYSTEM_Other = "Other";
	@Override
	public void setExternalSystem (final @Nullable java.lang.String ExternalSystem)
	{
		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	@Override
	public java.lang.String getExternalSystem() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystem);
	}

	@Override
	public void setImportErrorMsg (final @Nullable java.lang.String ImportErrorMsg)
	{
		set_Value (COLUMNNAME_ImportErrorMsg, ImportErrorMsg);
	}

	@Override
	public java.lang.String getImportErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ImportErrorMsg);
	}

	@Override
	public void setJSONValue (final @Nullable java.lang.String JSONValue)
	{
		set_Value (COLUMNNAME_JSONValue, JSONValue);
	}

	@Override
	public java.lang.String getJSONValue() 
	{
		return get_ValueAsString(COLUMNNAME_JSONValue);
	}

	@Override
	public void setS_FailedTimeBooking_ID (final int S_FailedTimeBooking_ID)
	{
		if (S_FailedTimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, S_FailedTimeBooking_ID);
	}

	@Override
	public int getS_FailedTimeBooking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_FailedTimeBooking_ID);
	}
}