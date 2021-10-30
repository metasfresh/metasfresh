// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_IolCandHandler_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_IolCandHandler_Log extends org.compiere.model.PO implements I_M_IolCandHandler_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -41886622L;

    /** Standard Constructor */
    public X_M_IolCandHandler_Log (final Properties ctx, final int M_IolCandHandler_Log_ID, @Nullable final String trxName)
    {
      super (ctx, M_IolCandHandler_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_M_IolCandHandler_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(final de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	@Override
	public void setM_IolCandHandler_ID (final int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, M_IolCandHandler_ID);
	}

	@Override
	public int getM_IolCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_ID);
	}

	@Override
	public void setM_IolCandHandler_Log_ID (final int M_IolCandHandler_Log_ID)
	{
		if (M_IolCandHandler_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, M_IolCandHandler_Log_ID);
	}

	@Override
	public int getM_IolCandHandler_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_Log_ID);
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
	public void setStatus (final @Nullable java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}