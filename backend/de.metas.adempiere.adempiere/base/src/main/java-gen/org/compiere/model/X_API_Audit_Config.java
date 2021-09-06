// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for API_Audit_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_API_Audit_Config extends org.compiere.model.PO implements I_API_Audit_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1831783931L;

    /** Standard Constructor */
    public X_API_Audit_Config (final Properties ctx, final int API_Audit_Config_ID, @Nullable final String trxName)
    {
      super (ctx, API_Audit_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_API_Audit_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_UserGroup getAD_UserGroup_InCharge()
	{
		return get_ValueAsPO(COLUMNNAME_AD_UserGroup_InCharge_ID, org.compiere.model.I_AD_UserGroup.class);
	}

	@Override
	public void setAD_UserGroup_InCharge(final org.compiere.model.I_AD_UserGroup AD_UserGroup_InCharge)
	{
		set_ValueFromPO(COLUMNNAME_AD_UserGroup_InCharge_ID, org.compiere.model.I_AD_UserGroup.class, AD_UserGroup_InCharge);
	}

	@Override
	public void setAD_UserGroup_InCharge_ID (final int AD_UserGroup_InCharge_ID)
	{
		if (AD_UserGroup_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_UserGroup_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserGroup_InCharge_ID, AD_UserGroup_InCharge_ID);
	}

	@Override
	public int getAD_UserGroup_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_UserGroup_InCharge_ID);
	}

	@Override
	public void setAPI_Audit_Config_ID (final int API_Audit_Config_ID)
	{
		if (API_Audit_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_API_Audit_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_API_Audit_Config_ID, API_Audit_Config_ID);
	}

	@Override
	public int getAPI_Audit_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Audit_Config_ID);
	}

	@Override
	public void setIsInvokerWaitsForResult (final boolean IsInvokerWaitsForResult)
	{
		set_Value (COLUMNNAME_IsInvokerWaitsForResult, IsInvokerWaitsForResult);
	}

	@Override
	public boolean isInvokerWaitsForResult() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvokerWaitsForResult);
	}

	@Override
	public void setKeepRequestBodyDays (final int KeepRequestBodyDays)
	{
		set_Value (COLUMNNAME_KeepRequestBodyDays, KeepRequestBodyDays);
	}

	@Override
	public int getKeepRequestBodyDays() 
	{
		return get_ValueAsInt(COLUMNNAME_KeepRequestBodyDays);
	}

	@Override
	public void setKeepRequestDays (final int KeepRequestDays)
	{
		set_Value (COLUMNNAME_KeepRequestDays, KeepRequestDays);
	}

	@Override
	public int getKeepRequestDays() 
	{
		return get_ValueAsInt(COLUMNNAME_KeepRequestDays);
	}

	@Override
	public void setKeepResponseBodyDays (final int KeepResponseBodyDays)
	{
		set_Value (COLUMNNAME_KeepResponseBodyDays, KeepResponseBodyDays);
	}

	@Override
	public int getKeepResponseBodyDays() 
	{
		return get_ValueAsInt(COLUMNNAME_KeepResponseBodyDays);
	}

	@Override
	public void setKeepResponseDays (final int KeepResponseDays)
	{
		set_Value (COLUMNNAME_KeepResponseDays, KeepResponseDays);
	}

	@Override
	public int getKeepResponseDays() 
	{
		return get_ValueAsInt(COLUMNNAME_KeepResponseDays);
	}

	/** 
	 * Method AD_Reference_ID=541306
	 * Reference name: Http_Method
	 */
	public static final int METHOD_AD_Reference_ID=541306;
	/** GET = GET */
	public static final String METHOD_GET = "GET";
	/** POST = POST */
	public static final String METHOD_POST = "POST";
	/** PUT = PUT */
	public static final String METHOD_PUT = "PUT";
	/** DELETE = DELETE */
	public static final String METHOD_DELETE = "DELETE";
	@Override
	public void setMethod (final @Nullable java.lang.String Method)
	{
		set_Value (COLUMNNAME_Method, Method);
	}

	@Override
	public java.lang.String getMethod() 
	{
		return get_ValueAsString(COLUMNNAME_Method);
	}

	/** 
	 * NotifyUserInCharge AD_Reference_ID=541315
	 * Reference name: NotifyItems
	 */
	public static final int NOTIFYUSERINCHARGE_AD_Reference_ID=541315;
	/** Niemals = NEVER  */
	public static final String NOTIFYUSERINCHARGE_Niemals = "NEVER ";
	/** Aufrufen mit Fehler = ONLY_ON_ERROR  */
	public static final String NOTIFYUSERINCHARGE_AufrufenMitFehler = "ONLY_ON_ERROR ";
	/** Allen Aufrufen = ALWAYS  */
	public static final String NOTIFYUSERINCHARGE_AllenAufrufen = "ALWAYS ";
	@Override
	public void setNotifyUserInCharge (final @Nullable java.lang.String NotifyUserInCharge)
	{
		set_Value (COLUMNNAME_NotifyUserInCharge, NotifyUserInCharge);
	}

	@Override
	public java.lang.String getNotifyUserInCharge() 
	{
		return get_ValueAsString(COLUMNNAME_NotifyUserInCharge);
	}

	@Override
	public void setPathPrefix (final @Nullable java.lang.String PathPrefix)
	{
		set_Value (COLUMNNAME_PathPrefix, PathPrefix);
	}

	@Override
	public java.lang.String getPathPrefix() 
	{
		return get_ValueAsString(COLUMNNAME_PathPrefix);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}