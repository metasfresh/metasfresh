/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_IolCandHandler
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_IolCandHandler extends org.compiere.model.PO implements I_M_IolCandHandler, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -368330067L;

    /** Standard Constructor */
    public X_M_IolCandHandler (Properties ctx, int M_IolCandHandler_ID, String trxName)
    {
      super (ctx, M_IolCandHandler_ID, trxName);
    }

    /** Load Constructor */
    public X_M_IolCandHandler (Properties ctx, ResultSet rs, String trxName)
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
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
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
	public void setTableName (java.lang.String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	@Override
	public java.lang.String getTableName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TableName);
	}
}