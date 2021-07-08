// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ProjectTask
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ProjectTask extends org.compiere.model.PO implements I_C_ProjectTask, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -495038692L;

    /** Standard Constructor */
    public X_C_ProjectTask (final Properties ctx, final int C_ProjectTask_ID, @Nullable final String trxName)
    {
      super (ctx, C_ProjectTask_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ProjectTask (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class);
	}

	@Override
	public void setC_ProjectPhase(final org.compiere.model.I_C_ProjectPhase C_ProjectPhase)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class, C_ProjectPhase);
	}

	@Override
	public void setC_ProjectPhase_ID (final int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, C_ProjectPhase_ID);
	}

	@Override
	public int getC_ProjectPhase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectPhase_ID);
	}

	@Override
	public void setC_ProjectTask_ID (final int C_ProjectTask_ID)
	{
		if (C_ProjectTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, C_ProjectTask_ID);
	}

	@Override
	public int getC_ProjectTask_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectTask_ID);
	}

	@Override
	public org.compiere.model.I_C_Task getC_Task()
	{
		return get_ValueAsPO(COLUMNNAME_C_Task_ID, org.compiere.model.I_C_Task.class);
	}

	@Override
	public void setC_Task(final org.compiere.model.I_C_Task C_Task)
	{
		set_ValueFromPO(COLUMNNAME_C_Task_ID, org.compiere.model.I_C_Task.class, C_Task);
	}

	@Override
	public void setC_Task_ID (final int C_Task_ID)
	{
		if (C_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Task_ID, C_Task_ID);
	}

	@Override
	public int getC_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Task_ID);
	}

	@Override
	public void setCommittedAmt (final BigDecimal CommittedAmt)
	{
		set_Value (COLUMNNAME_CommittedAmt, CommittedAmt);
	}

	@Override
	public BigDecimal getCommittedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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

	@Override
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_Value (COLUMNNAME_PlannedAmt, PlannedAmt);
	}

	@Override
	public BigDecimal getPlannedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ProjInvoiceRule AD_Reference_ID=383
	 * Reference name: C_Project InvoiceRule
	 */
	public static final int PROJINVOICERULE_AD_Reference_ID=383;
	/** None = - */
	public static final String PROJINVOICERULE_None = "-";
	/** Committed Amount = C */
	public static final String PROJINVOICERULE_CommittedAmount = "C";
	/** Time&Material max Comitted = c */
	public static final String PROJINVOICERULE_TimeMaterialMaxComitted = "c";
	/** Time&Material = T */
	public static final String PROJINVOICERULE_TimeMaterial = "T";
	/** Product  Quantity = P */
	public static final String PROJINVOICERULE_ProductQuantity = "P";
	@Override
	public void setProjInvoiceRule (final java.lang.String ProjInvoiceRule)
	{
		set_Value (COLUMNNAME_ProjInvoiceRule, ProjInvoiceRule);
	}

	@Override
	public java.lang.String getProjInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_ProjInvoiceRule);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}