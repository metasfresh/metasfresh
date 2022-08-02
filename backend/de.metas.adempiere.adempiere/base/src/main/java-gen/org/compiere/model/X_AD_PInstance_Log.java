// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PInstance_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_PInstance_Log extends org.compiere.model.PO implements I_AD_PInstance_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1144192003L;

    /** Standard Constructor */
    public X_AD_PInstance_Log (final Properties ctx, final int AD_PInstance_Log_ID, @Nullable final String trxName)
    {
      super (ctx, AD_PInstance_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PInstance_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
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
	public void setAD_PInstance_Log_ID (final int AD_PInstance_Log_ID)
	{
		if (AD_PInstance_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_Log_ID, AD_PInstance_Log_ID);
	}

	@Override
	public int getAD_PInstance_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_Log_ID);
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
	public void setLog_ID (final int Log_ID)
	{
		if (Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Log_ID, Log_ID);
	}

	@Override
	public int getLog_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Log_ID);
	}

	@Override
	public void setP_Date (final @Nullable java.sql.Timestamp P_Date)
	{
		set_ValueNoCheck (COLUMNNAME_P_Date, P_Date);
	}

	@Override
	public java.sql.Timestamp getP_Date() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_P_Date);
	}

	@Override
	public void setP_Msg (final @Nullable java.lang.String P_Msg)
	{
		set_ValueNoCheck (COLUMNNAME_P_Msg, P_Msg);
	}

	@Override
	public java.lang.String getP_Msg() 
	{
		return get_ValueAsString(COLUMNNAME_P_Msg);
	}

	@Override
	public void setP_Number (final @Nullable BigDecimal P_Number)
	{
		set_ValueNoCheck (COLUMNNAME_P_Number, P_Number);
	}

	@Override
	public BigDecimal getP_Number() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_P_Number);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setWarnings (final @Nullable java.lang.String Warnings)
	{
		set_Value (COLUMNNAME_Warnings, Warnings);
	}

	@Override
	public java.lang.String getWarnings()
	{
		return get_ValueAsString(COLUMNNAME_Warnings);
	}
}