// Generated Model - DO NOT CHANGE
package org.adempiere.process.rpl.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EXP_ReplicationTrx
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EXP_ReplicationTrx extends org.compiere.model.PO implements I_EXP_ReplicationTrx, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1145021183L;

    /** Standard Constructor */
    public X_EXP_ReplicationTrx (final Properties ctx, final int EXP_ReplicationTrx_ID, @Nullable final String trxName)
    {
      super (ctx, EXP_ReplicationTrx_ID, trxName);
    }

    /** Load Constructor */
    public X_EXP_ReplicationTrx (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setErrorMsg (final @Nullable java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setEXP_ReplicationTrx_ID (final int EXP_ReplicationTrx_ID)
	{
		if (EXP_ReplicationTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrx_ID, EXP_ReplicationTrx_ID);
	}

	@Override
	public int getEXP_ReplicationTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EXP_ReplicationTrx_ID);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setIsReplicationTrxFinished (final boolean IsReplicationTrxFinished)
	{
		set_Value (COLUMNNAME_IsReplicationTrxFinished, IsReplicationTrxFinished);
	}

	@Override
	public boolean isReplicationTrxFinished() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReplicationTrxFinished);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}
}