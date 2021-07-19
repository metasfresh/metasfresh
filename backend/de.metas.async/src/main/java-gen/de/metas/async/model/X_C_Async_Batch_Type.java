// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Async_Batch_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Async_Batch_Type extends org.compiere.model.PO implements I_C_Async_Batch_Type, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 851172719L;

    /** Standard Constructor */
    public X_C_Async_Batch_Type (final Properties ctx, final int C_Async_Batch_Type_ID, @Nullable final String trxName)
    {
      super (ctx, C_Async_Batch_Type_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Async_Batch_Type (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_BoilerPlate_ID (final int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, AD_BoilerPlate_ID);
	}

	@Override
	public int getAD_BoilerPlate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BoilerPlate_ID);
	}

	@Override
	public void setC_Async_Batch_Type_ID (final int C_Async_Batch_Type_ID)
	{
		if (C_Async_Batch_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Type_ID, C_Async_Batch_Type_ID);
	}

	@Override
	public int getC_Async_Batch_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_Type_ID);
	}

	@Override
	public void setInternalName (final String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public String getInternalName()
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setKeepAliveTimeHours (final @Nullable String KeepAliveTimeHours)
	{
		set_Value (COLUMNNAME_KeepAliveTimeHours, KeepAliveTimeHours);
	}

	@Override
	public String getKeepAliveTimeHours()
	{
		return get_ValueAsString(COLUMNNAME_KeepAliveTimeHours);
	}

	/** 
	 * NotificationType AD_Reference_ID=540643
	 * Reference name: _NotificationType
	 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=540643;
	/** Async Batch Processed = ABP */
	public static final String NOTIFICATIONTYPE_AsyncBatchProcessed = "ABP";
	/** Workpackage Processed = WPP */
	public static final String NOTIFICATIONTYPE_WorkpackageProcessed = "WPP";
	@Override
	public void setNotificationType (final @Nullable String NotificationType)
	{
		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	@Override
	public String getNotificationType()
	{
		return get_ValueAsString(COLUMNNAME_NotificationType);
	}

	@Override
	public void setSkipTimeoutMillis (final int SkipTimeoutMillis)
	{
		set_Value (COLUMNNAME_SkipTimeoutMillis, SkipTimeoutMillis);
	}

	@Override
	public int getSkipTimeoutMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_SkipTimeoutMillis);
	}
}