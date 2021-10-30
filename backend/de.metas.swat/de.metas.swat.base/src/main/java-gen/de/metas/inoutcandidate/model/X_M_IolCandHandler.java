// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_IolCandHandler
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_IolCandHandler extends org.compiere.model.PO implements I_M_IolCandHandler, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1140899552L;

    /** Standard Constructor */
    public X_M_IolCandHandler (final Properties ctx, final int M_IolCandHandler_ID, @Nullable final String trxName)
    {
      super (ctx, M_IolCandHandler_ID, trxName);
    }

    /** Load Constructor */
    public X_M_IolCandHandler (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setClassname (final java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return get_ValueAsString(COLUMNNAME_Classname);
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
	public void setTableName (final java.lang.String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	@Override
	public java.lang.String getTableName() 
	{
		return get_ValueAsString(COLUMNNAME_TableName);
	}
}