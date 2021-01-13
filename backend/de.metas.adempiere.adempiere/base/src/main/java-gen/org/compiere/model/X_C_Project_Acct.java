// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Acct extends org.compiere.model.PO implements I_C_Project_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 289389398L;

    /** Standard Constructor */
    public X_C_Project_Acct (final Properties ctx, final int C_Project_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_Asset_A(final org.compiere.model.I_C_ValidCombination PJ_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_Asset_A);
	}

	@Override
	public void setPJ_Asset_Acct (final int PJ_Asset_Acct)
	{
		set_Value (COLUMNNAME_PJ_Asset_Acct, PJ_Asset_Acct);
	}

	@Override
	public int getPJ_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PJ_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_WIP_A()
	{
		return get_ValueAsPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_WIP_A(final org.compiere.model.I_C_ValidCombination PJ_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_WIP_A);
	}

	@Override
	public void setPJ_WIP_Acct (final int PJ_WIP_Acct)
	{
		set_Value (COLUMNNAME_PJ_WIP_Acct, PJ_WIP_Acct);
	}

	@Override
	public int getPJ_WIP_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PJ_WIP_Acct);
	}
}