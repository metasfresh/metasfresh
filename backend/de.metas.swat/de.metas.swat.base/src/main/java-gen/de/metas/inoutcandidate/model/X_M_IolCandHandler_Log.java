/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_IolCandHandler_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_IolCandHandler_Log extends org.compiere.model.PO implements I_M_IolCandHandler_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1064408496L;

    /** Standard Constructor */
    public X_M_IolCandHandler_Log (Properties ctx, int M_IolCandHandler_Log_ID, String trxName)
    {
      super (ctx, M_IolCandHandler_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_M_IolCandHandler_Log (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	@Override
	public int getM_IolCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_ID);
	}

	@Override
	public void setM_IolCandHandler_Log_ID (int M_IolCandHandler_Log_ID)
	{
		if (M_IolCandHandler_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, Integer.valueOf(M_IolCandHandler_Log_ID));
	}

	@Override
	public int getM_IolCandHandler_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_Log_ID);
	}

	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}