/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_IolCandHandler_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_IolCandHandler_Log extends org.compiere.model.PO implements I_M_IolCandHandler_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1003008725L;

    /** Standard Constructor */
    public X_M_IolCandHandler_Log (Properties ctx, int M_IolCandHandler_Log_ID, String trxName)
    {
      super (ctx, M_IolCandHandler_Log_ID, trxName);
      /** if (M_IolCandHandler_Log_ID == 0)
        {
			setM_IolCandHandler_ID (0);
			setM_IolCandHandler_Log_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_IolCandHandler_Log (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	/** Set M_IolCandHandler.
		@param M_IolCandHandler_ID M_IolCandHandler	  */
	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	/** Get M_IolCandHandler.
		@return M_IolCandHandler	  */
	@Override
	public int getM_IolCandHandler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_IolCandHandler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_IolCandHandler_Log.
		@param M_IolCandHandler_Log_ID M_IolCandHandler_Log	  */
	@Override
	public void setM_IolCandHandler_Log_ID (int M_IolCandHandler_Log_ID)
	{
		if (M_IolCandHandler_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_Log_ID, Integer.valueOf(M_IolCandHandler_Log_ID));
	}

	/** Get M_IolCandHandler_Log.
		@return M_IolCandHandler_Log	  */
	@Override
	public int getM_IolCandHandler_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_IolCandHandler_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}