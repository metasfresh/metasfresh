/** Generated Model - DO NOT CHANGE */
package de.metas.dao.selection.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for T_Query_Selection_ToDelete
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_T_Query_Selection_ToDelete extends org.compiere.model.PO implements I_T_Query_Selection_ToDelete, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -270638300L;

    /** Standard Constructor */
    public X_T_Query_Selection_ToDelete (Properties ctx, int T_Query_Selection_ToDelete_ID, String trxName)
    {
      super (ctx, T_Query_Selection_ToDelete_ID, trxName);
      /** if (T_Query_Selection_ToDelete_ID == 0)
        {
			setUUID (null);
        } */
    }

    /** Load Constructor */
    public X_T_Query_Selection_ToDelete (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Executor_UUID.
		@param Executor_UUID Executor_UUID	  */
	@Override
	public void setExecutor_UUID (java.lang.String Executor_UUID)
	{
		set_Value (COLUMNNAME_Executor_UUID, Executor_UUID);
	}

	/** Get Executor_UUID.
		@return Executor_UUID	  */
	@Override
	public java.lang.String getExecutor_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Executor_UUID);
	}

	/** Set UUID.
		@param UUID UUID	  */
	@Override
	public void setUUID (java.lang.String UUID)
	{
		set_ValueNoCheck (COLUMNNAME_UUID, UUID);
	}

	/** Get UUID.
		@return UUID	  */
	@Override
	public java.lang.String getUUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UUID);
	}
}