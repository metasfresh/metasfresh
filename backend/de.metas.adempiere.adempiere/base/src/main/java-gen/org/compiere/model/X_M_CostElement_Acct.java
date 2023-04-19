// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_CostElement_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostElement_Acct extends org.compiere.model.PO implements I_M_CostElement_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -117522961L;

    /** Standard Constructor */
    public X_M_CostElement_Acct (final Properties ctx, final int M_CostElement_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostElement_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostElement_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_CostElement_Acct_ID (final int M_CostElement_Acct_ID)
	{
		if (M_CostElement_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_Acct_ID, M_CostElement_Acct_ID);
	}

	@Override
	public int getM_CostElement_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_Acct_ID);
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(final org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostClearing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostClearing_A(final org.compiere.model.I_C_ValidCombination P_CostClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostClearing_A);
	}

	@Override
	public void setP_CostClearing_Acct (final int P_CostClearing_Acct)
	{
		set_Value (COLUMNNAME_P_CostClearing_Acct, P_CostClearing_Acct);
	}

	@Override
	public int getP_CostClearing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostClearing_Acct);
	}
}