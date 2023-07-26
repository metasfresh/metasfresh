// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Commission_Overview_V
 *  @author metasfresh (generated) 
 */
public class X_C_Commission_Overview_V extends org.compiere.model.PO implements I_C_Commission_Overview_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -151577667L;

    /** Standard Constructor */
    public X_C_Commission_Overview_V (final Properties ctx, final int C_Commission_Overview_V_ID, @Nullable final String trxName)
    {
      super (ctx, C_Commission_Overview_V_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Commission_Overview_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setC_BPartner_SalesRep_ID (final int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, C_BPartner_SalesRep_ID);
	}

	@Override
	public int getC_BPartner_SalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_SalesRep_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Instance getC_Commission_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class);
	}

	@Override
	public void setC_Commission_Instance(final de.metas.contracts.commission.model.I_C_Commission_Instance C_Commission_Instance)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class, C_Commission_Instance);
	}

	@Override
	public void setC_Commission_Instance_ID (final int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, C_Commission_Instance_ID);
	}

	@Override
	public int getC_Commission_Instance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Instance_ID);
	}

	@Override
	public void setC_Commission_Overview_V_ID (final int C_Commission_Overview_V_ID)
	{
		if (C_Commission_Overview_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Overview_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Overview_V_ID, C_Commission_Overview_V_ID);
	}

	@Override
	public int getC_Commission_Overview_V_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Overview_V_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_CommissionSettingsLine getC_CommissionSettingsLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CommissionSettingsLine_ID, de.metas.contracts.commission.model.I_C_CommissionSettingsLine.class);
	}

	@Override
	public void setC_CommissionSettingsLine(final de.metas.contracts.commission.model.I_C_CommissionSettingsLine C_CommissionSettingsLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CommissionSettingsLine_ID, de.metas.contracts.commission.model.I_C_CommissionSettingsLine.class, C_CommissionSettingsLine);
	}

	@Override
	public void setC_CommissionSettingsLine_ID (final int C_CommissionSettingsLine_ID)
	{
		if (C_CommissionSettingsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, C_CommissionSettingsLine_ID);
	}

	@Override
	public int getC_CommissionSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CommissionSettingsLine_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class);
	}

	@Override
	public void setC_Commission_Share(final de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class, C_Commission_Share);
	}

	@Override
	public void setC_Commission_Share_ID (final int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, C_Commission_Share_ID);
	}

	@Override
	public int getC_Commission_Share_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Share_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Commission_ID (final int C_Invoice_Candidate_Commission_ID)
	{
		if (C_Invoice_Candidate_Commission_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Commission_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Commission_ID, C_Invoice_Candidate_Commission_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Commission_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Commission_ID);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice_Commission()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Commission_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice_Commission(final org.compiere.model.I_C_Invoice C_Invoice_Commission)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Commission_ID, org.compiere.model.I_C_Invoice.class, C_Invoice_Commission);
	}

	@Override
	public void setC_Invoice_Commission_ID (final int C_Invoice_Commission_ID)
	{
		if (C_Invoice_Commission_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Commission_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Commission_ID, C_Invoice_Commission_ID);
	}

	@Override
	public int getC_Invoice_Commission_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Commission_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine_Commission()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_Commission_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine_Commission(final org.compiere.model.I_C_InvoiceLine C_InvoiceLine_Commission)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_Commission_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine_Commission);
	}

	@Override
	public void setC_InvoiceLine_Commission_ID (final int C_InvoiceLine_Commission_ID)
	{
		if (C_InvoiceLine_Commission_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_Commission_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_Commission_ID, C_InvoiceLine_Commission_ID);
	}

	@Override
	public int getC_InvoiceLine_Commission_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_Commission_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(final org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	@Override
	public void setC_InvoiceLine_ID (final int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);
	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
	}

	@Override
	public void setCommissionDate (final java.sql.Timestamp CommissionDate)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionDate, CommissionDate);
	}

	@Override
	public java.sql.Timestamp getCommissionDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CommissionDate);
	}

	@Override
	public void setCommissionTrigger_Type (final java.lang.String CommissionTrigger_Type)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionTrigger_Type, CommissionTrigger_Type);
	}

	@Override
	public java.lang.String getCommissionTrigger_Type() 
	{
		return get_ValueAsString(COLUMNNAME_CommissionTrigger_Type);
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
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		set_ValueNoCheck (COLUMNNAME_IsSimulation, IsSimulation);
	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setLevelHierarchy (final int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, LevelHierarchy);
	}

	@Override
	public int getLevelHierarchy() 
	{
		return get_ValueAsInt(COLUMNNAME_LevelHierarchy);
	}

	@Override
	public void setMostRecentTriggerTimestamp (final java.sql.Timestamp MostRecentTriggerTimestamp)
	{
		set_ValueNoCheck (COLUMNNAME_MostRecentTriggerTimestamp, MostRecentTriggerTimestamp);
	}

	@Override
	public java.sql.Timestamp getMostRecentTriggerTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MostRecentTriggerTimestamp);
	}

	@Override
	public void setM_Product_Order_ID (final int M_Product_Order_ID)
	{
		if (M_Product_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Order_ID, M_Product_Order_ID);
	}

	@Override
	public int getM_Product_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Order_ID);
	}

	@Override
	public void setPercentOfBasePoints (final BigDecimal PercentOfBasePoints)
	{
		set_ValueNoCheck (COLUMNNAME_PercentOfBasePoints, PercentOfBasePoints);
	}

	@Override
	public BigDecimal getPercentOfBasePoints() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentOfBasePoints);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsBase_Forecasted (final BigDecimal PointsBase_Forecasted)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Forecasted, PointsBase_Forecasted);
	}

	@Override
	public BigDecimal getPointsBase_Forecasted() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsBase_Forecasted);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsBase_Invoiceable (final BigDecimal PointsBase_Invoiceable)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Invoiceable, PointsBase_Invoiceable);
	}

	@Override
	public BigDecimal getPointsBase_Invoiceable() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsBase_Invoiceable);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsBase_Invoiced (final BigDecimal PointsBase_Invoiced)
	{
		set_ValueNoCheck (COLUMNNAME_PointsBase_Invoiced, PointsBase_Invoiced);
	}

	@Override
	public BigDecimal getPointsBase_Invoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsBase_Invoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_Settled (final BigDecimal PointsSum_Settled)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_Settled, PointsSum_Settled);
	}

	@Override
	public BigDecimal getPointsSum_Settled() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_Settled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_ToSettle (final BigDecimal PointsSum_ToSettle)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_ToSettle, PointsSum_ToSettle);
	}

	@Override
	public BigDecimal getPointsSum_ToSettle() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_ToSettle);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPOReference (final java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setQtyEntered (final BigDecimal QtyEntered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}