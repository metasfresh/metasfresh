// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ProjectPhase
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ProjectPhase extends org.compiere.model.PO implements I_C_ProjectPhase, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 260271158L;

    /** Standard Constructor */
    public X_C_ProjectPhase (final Properties ctx, final int C_ProjectPhase_ID, @Nullable final String trxName)
    {
      super (ctx, C_ProjectPhase_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ProjectPhase (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_Phase getC_Phase()
	{
		return get_ValueAsPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class);
	}

	@Override
	public void setC_Phase(final org.compiere.model.I_C_Phase C_Phase)
	{
		set_ValueFromPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class, C_Phase);
	}

	@Override
	public void setC_Phase_ID (final int C_Phase_ID)
	{
		if (C_Phase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, C_Phase_ID);
	}

	@Override
	public int getC_Phase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Phase_ID);
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
	public void setEndDate (final @Nullable java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	@Override
	public java.sql.Timestamp getEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EndDate);
	}

	@Override
	public void setGenerateOrder (final @Nullable java.lang.String GenerateOrder)
	{
		set_Value (COLUMNNAME_GenerateOrder, GenerateOrder);
	}

	@Override
	public java.lang.String getGenerateOrder() 
	{
		return get_ValueAsString(COLUMNNAME_GenerateOrder);
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
	public void setIsCommitCeiling (final boolean IsCommitCeiling)
	{
		set_Value (COLUMNNAME_IsCommitCeiling, IsCommitCeiling);
	}

	@Override
	public boolean isCommitCeiling() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommitCeiling);
	}

	@Override
	public void setIsComplete (final boolean IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, IsComplete);
	}

	@Override
	public boolean isComplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsComplete);
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

	@Override
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
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

	@Override
	public void setStartDate (final @Nullable java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	@Override
	public java.sql.Timestamp getStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StartDate);
	}
}