// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ProjectIssueMA
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ProjectIssueMA extends org.compiere.model.PO implements I_C_ProjectIssueMA, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1518652296L;

    /** Standard Constructor */
    public X_C_ProjectIssueMA (final Properties ctx, final int C_ProjectIssueMA_ID, @Nullable final String trxName)
    {
      super (ctx, C_ProjectIssueMA_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ProjectIssueMA (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ProjectIssue getC_ProjectIssue()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class);
	}

	@Override
	public void setC_ProjectIssue(final org.compiere.model.I_C_ProjectIssue C_ProjectIssue)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectIssue_ID, org.compiere.model.I_C_ProjectIssue.class, C_ProjectIssue);
	}

	@Override
	public void setC_ProjectIssue_ID (final int C_ProjectIssue_ID)
	{
		if (C_ProjectIssue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssue_ID, C_ProjectIssue_ID);
	}

	@Override
	public int getC_ProjectIssue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectIssue_ID);
	}

	@Override
	public void setC_ProjectIssueMA_ID (final int C_ProjectIssueMA_ID)
	{
		if (C_ProjectIssueMA_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssueMA_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectIssueMA_ID, C_ProjectIssueMA_ID);
	}

	@Override
	public int getC_ProjectIssueMA_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectIssueMA_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setMovementQty (final BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	@Override
	public BigDecimal getMovementQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MovementQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}