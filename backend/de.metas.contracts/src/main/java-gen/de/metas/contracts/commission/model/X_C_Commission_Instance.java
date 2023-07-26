// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Instance
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Commission_Instance extends org.compiere.model.PO implements I_C_Commission_Instance, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1559329349L;

    /** Standard Constructor */
    public X_C_Commission_Instance (final Properties ctx, final int C_Commission_Instance_ID, @Nullable final String trxName)
    {
      super (ctx, C_Commission_Instance_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Commission_Instance (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
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
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setCommissionDate (final java.sql.Timestamp CommissionDate)
	{
		set_Value (COLUMNNAME_CommissionDate, CommissionDate);
	}

	@Override
	public java.sql.Timestamp getCommissionDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CommissionDate);
	}

	/** 
	 * CommissionTrigger_Type AD_Reference_ID=541115
	 * Reference name: CommissionTrigger_Type
	 */
	public static final int COMMISSIONTRIGGER_TYPE_AD_Reference_ID=541115;
	/** InvoiceCandidate = InvoiceCandidate */
	public static final String COMMISSIONTRIGGER_TYPE_InvoiceCandidate = "InvoiceCandidate";
	/** CustomerInvoice = CustomerInvoice */
	public static final String COMMISSIONTRIGGER_TYPE_CustomerInvoice = "CustomerInvoice";
	/** CustomerCreditmemo = CustomerCreditmemo */
	public static final String COMMISSIONTRIGGER_TYPE_CustomerCreditmemo = "CustomerCreditmemo";
	/** Mediated order = MediatedOrder */
	public static final String COMMISSIONTRIGGER_TYPE_MediatedOrder = "MediatedOrder";
	@Override
	public void setCommissionTrigger_Type (final String CommissionTrigger_Type)
	{
		set_Value (COLUMNNAME_CommissionTrigger_Type, CommissionTrigger_Type);
	}

	@Override
	public String getCommissionTrigger_Type()
	{
		return get_ValueAsString(COLUMNNAME_CommissionTrigger_Type);
	}

	@Override
	public void setM_Product_Order_ID (final int M_Product_Order_ID)
	{
		if (M_Product_Order_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Order_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Order_ID, M_Product_Order_ID);
	}

	@Override
	public int getM_Product_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Order_ID);
	}

	@Override
	public void setMostRecentTriggerTimestamp (final java.sql.Timestamp MostRecentTriggerTimestamp)
	{
		set_Value (COLUMNNAME_MostRecentTriggerTimestamp, MostRecentTriggerTimestamp);
	}

	@Override
	public java.sql.Timestamp getMostRecentTriggerTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MostRecentTriggerTimestamp);
	}

	@Override
	public void setPointsBase_Forecasted (final BigDecimal PointsBase_Forecasted)
	{
		set_Value (COLUMNNAME_PointsBase_Forecasted, PointsBase_Forecasted);
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
		set_Value (COLUMNNAME_PointsBase_Invoiceable, PointsBase_Invoiceable);
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
		set_Value (COLUMNNAME_PointsBase_Invoiced, PointsBase_Invoiced);
	}

	@Override
	public BigDecimal getPointsBase_Invoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsBase_Invoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPOReference (final @Nullable String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public String getPOReference()
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}
}