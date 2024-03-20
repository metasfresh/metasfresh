// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Doc_Approval_Strategy
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Approval_Strategy extends org.compiere.model.PO implements I_C_Doc_Approval_Strategy, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1301811140L;

    /** Standard Constructor */
    public X_C_Doc_Approval_Strategy (final Properties ctx, final int C_Doc_Approval_Strategy_ID, @Nullable final String trxName)
    {
      super (ctx, C_Doc_Approval_Strategy_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Doc_Approval_Strategy (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Doc_Approval_Strategy_ID (final int C_Doc_Approval_Strategy_ID)
	{
		if (C_Doc_Approval_Strategy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_ID, C_Doc_Approval_Strategy_ID);
	}

	@Override
	public int getC_Doc_Approval_Strategy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Approval_Strategy_ID);
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
}