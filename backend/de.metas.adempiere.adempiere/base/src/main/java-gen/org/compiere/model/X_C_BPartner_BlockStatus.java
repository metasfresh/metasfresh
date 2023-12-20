// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_BlockStatus
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_BlockStatus extends org.compiere.model.PO implements I_C_BPartner_BlockStatus, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1926548310L;

    /** Standard Constructor */
    public X_C_BPartner_BlockStatus (final Properties ctx, final int C_BPartner_BlockStatus_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_BlockStatus_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_BlockStatus (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * BlockStatus AD_Reference_ID=541720
	 * Reference name: BlockStatus
	 */
	public static final int BLOCKSTATUS_AD_Reference_ID=541720;
	/** Blocked = B */
	public static final String BLOCKSTATUS_Blocked = "B";
	/** Unblocked = UB */
	public static final String BLOCKSTATUS_Unblocked = "UB";
	@Override
	public void setBlockStatus (final java.lang.String BlockStatus)
	{
		set_Value (COLUMNNAME_BlockStatus, BlockStatus);
	}

	@Override
	public java.lang.String getBlockStatus() 
	{
		return get_ValueAsString(COLUMNNAME_BlockStatus);
	}

	@Override
	public void setC_BPartner_BlockStatus_ID (final int C_BPartner_BlockStatus_ID)
	{
		if (C_BPartner_BlockStatus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_BlockStatus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_BlockStatus_ID, C_BPartner_BlockStatus_ID);
	}

	@Override
	public int getC_BPartner_BlockStatus_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_BlockStatus_ID);
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
	public void setIsCurrent (final boolean IsCurrent)
	{
		set_Value (COLUMNNAME_IsCurrent, IsCurrent);
	}

	@Override
	public boolean isCurrent() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCurrent);
	}

	@Override
	public void setReason (final @Nullable java.lang.String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	@Override
	public java.lang.String getReason() 
	{
		return get_ValueAsString(COLUMNNAME_Reason);
	}
}