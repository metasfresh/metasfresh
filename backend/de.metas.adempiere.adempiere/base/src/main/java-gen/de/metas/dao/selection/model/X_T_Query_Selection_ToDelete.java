/** Generated Model - DO NOT CHANGE */
package de.metas.dao.selection.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for T_Query_Selection_ToDelete
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_T_Query_Selection_ToDelete extends org.compiere.model.PO implements I_T_Query_Selection_ToDelete, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1105719445L;

    /** Standard Constructor */
    public X_T_Query_Selection_ToDelete (Properties ctx, int T_Query_Selection_ToDelete_ID, String trxName)
    {
      super (ctx, T_Query_Selection_ToDelete_ID, trxName);
    }

    /** Load Constructor */
    public X_T_Query_Selection_ToDelete (Properties ctx, ResultSet rs, String trxName)
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
	public void setExecutor_UUID (java.lang.String Executor_UUID)
	{
		set_Value (COLUMNNAME_Executor_UUID, Executor_UUID);
	}

	@Override
	public java.lang.String getExecutor_UUID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Executor_UUID);
	}

	@Override
	public void setT_Query_Selection_ToDelete_ID (int T_Query_Selection_ToDelete_ID)
	{
		if (T_Query_Selection_ToDelete_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_Query_Selection_ToDelete_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_Query_Selection_ToDelete_ID, Integer.valueOf(T_Query_Selection_ToDelete_ID));
	}

	@Override
	public int getT_Query_Selection_ToDelete_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Query_Selection_ToDelete_ID);
	}

	@Override
	public void setUUID (java.lang.String UUID)
	{
		set_ValueNoCheck (COLUMNNAME_UUID, UUID);
	}

	@Override
	public java.lang.String getUUID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UUID);
	}
}