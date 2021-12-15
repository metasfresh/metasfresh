// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_InvoiceLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InvoiceLine extends org.compiere.model.PO implements I_C_InvoiceLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 954930110L;

    /** Standard Constructor */
    public X_C_InvoiceLine (final Properties ctx, final int C_InvoiceLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_InvoiceLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InvoiceLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class);
	}

	@Override
	public void setA_Asset_Group(final org.compiere.model.I_A_Asset_Group A_Asset_Group)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class, A_Asset_Group);
	}

	@Override
	public void setA_Asset_Group_ID (final int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, A_Asset_Group_ID);
	}

	@Override
	public int getA_Asset_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_Group_ID);
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(final org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	@Override
	public void setA_Asset_ID (final int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, A_Asset_ID);
	}

	@Override
	public int getA_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_ID);
	}

	/** 
	 * A_CapvsExp AD_Reference_ID=53277
	 * Reference name: A_CapvsExp
	 */
	public static final int A_CAPVSEXP_AD_Reference_ID=53277;
	/** Capital = Cap */
	public static final String A_CAPVSEXP_Capital = "Cap";
	/** Expense = Exp */
	public static final String A_CAPVSEXP_Expense = "Exp";
	@Override
	public void setA_CapvsExp (final java.lang.String A_CapvsExp)
	{
		set_Value (COLUMNNAME_A_CapvsExp, A_CapvsExp);
	}

	@Override
	public java.lang.String getA_CapvsExp() 
	{
		return get_ValueAsString(COLUMNNAME_A_CapvsExp);
	}

	@Override
	public void setA_CreateAsset (final boolean A_CreateAsset)
	{
		set_Value (COLUMNNAME_A_CreateAsset, A_CreateAsset);
	}

	@Override
	public boolean isA_CreateAsset() 
	{
		return get_ValueAsBoolean(COLUMNNAME_A_CreateAsset);
	}

	@Override
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
	}

	@Override
	public void setA_Processed (final boolean A_Processed)
	{
		set_Value (COLUMNNAME_A_Processed, A_Processed);
	}

	@Override
	public boolean isA_Processed()
	{
		return get_ValueAsBoolean(COLUMNNAME_A_Processed);
	}

	@Override
	public void setBase_PricingSystem_ID (final int Base_PricingSystem_ID)
	{
		if (Base_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, Base_PricingSystem_ID);
	}

	@Override
	public int getBase_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Base_PricingSystem_ID);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(final org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID (final int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
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
	public org.compiere.model.I_C_ProjectTask getC_ProjectTask()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class);
	}

	@Override
	public void setC_ProjectTask(final org.compiere.model.I_C_ProjectTask C_ProjectTask)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class, C_ProjectTask);
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
	public void setC_Shipping_Location_ID (final int C_Shipping_Location_ID)
	{
		if (C_Shipping_Location_ID < 1)
			set_Value (COLUMNNAME_C_Shipping_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_Shipping_Location_ID, C_Shipping_Location_ID);
	}

	@Override
	public int getC_Shipping_Location_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Shipping_Location_ID);
	}

	@Override
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1)
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setExternalIds (final java.lang.String ExternalIds)
	{
		set_Value (COLUMNNAME_ExternalIds, ExternalIds);
	}

	@Override
	public java.lang.String getExternalIds() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalIds);
	}

	@Override
	public void setIsDescription (final boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, IsDescription);
	}

	@Override
	public boolean isDescription() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDescription);
	}

	@Override
	public void setIsOrderLineReadOnly (final boolean IsOrderLineReadOnly)
	{
		set_Value (COLUMNNAME_IsOrderLineReadOnly, IsOrderLineReadOnly);
	}

	@Override
	public boolean isOrderLineReadOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOrderLineReadOnly);
	}

	@Override
	public void setIsPrinted (final boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, IsPrinted);
	}

	@Override
	public boolean isPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrinted);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	/** 
	 * Line_CreditMemoReason AD_Reference_ID=540014
	 * Reference name: C_CreditMemo_Reason
	 */
	public static final int LINE_CREDITMEMOREASON_AD_Reference_ID=540014;
	/** Falschlieferung = CMF */
	public static final String LINE_CREDITMEMOREASON_Falschlieferung = "CMF";
	/** Doppellieferung = CMD */
	public static final String LINE_CREDITMEMOREASON_Doppellieferung = "CMD";
	@Override
	public void setLine_CreditMemoReason (final java.lang.String Line_CreditMemoReason)
	{
		set_Value (COLUMNNAME_Line_CreditMemoReason, Line_CreditMemoReason);
	}

	@Override
	public java.lang.String getLine_CreditMemoReason() 
	{
		return get_ValueAsString(COLUMNNAME_Line_CreditMemoReason);
	}

	@Override
	public void setLineNetAmt (final BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLineTotalAmt (final BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	@Override
	public BigDecimal getLineTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineTotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
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
	public org.compiere.model.I_M_RMALine getM_RMALine()
	{
		return get_ValueAsPO(COLUMNNAME_M_RMALine_ID, org.compiere.model.I_M_RMALine.class);
	}

	@Override
	public void setM_RMALine(final org.compiere.model.I_M_RMALine M_RMALine)
	{
		set_ValueFromPO(COLUMNNAME_M_RMALine_ID, org.compiere.model.I_M_RMALine.class, M_RMALine);
	}

	@Override
	public void setM_RMALine_ID (final int M_RMALine_ID)
	{
		if (M_RMALine_ID < 1) 
			set_Value (COLUMNNAME_M_RMALine_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMALine_ID, M_RMALine_ID);
	}

	@Override
	public int getM_RMALine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_RMALine_ID);
	}

	@Override
	public void setPriceActual (final BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceEntered (final BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	@Override
	public BigDecimal getPriceEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceLimit (final BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	@Override
	public BigDecimal getPriceLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList (final BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProductDescription (final java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyEntered (final BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRef_InvoiceLine_ID (final int Ref_InvoiceLine_ID)
	{
		if (Ref_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_Ref_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_InvoiceLine_ID, Ref_InvoiceLine_ID);
	}

	@Override
	public int getRef_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_InvoiceLine_ID);
	}

	@Override
	public void setRRAmt (final BigDecimal RRAmt)
	{
		set_Value (COLUMNNAME_RRAmt, RRAmt);
	}

	@Override
	public BigDecimal getRRAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RRAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRRStartDate (final java.sql.Timestamp RRStartDate)
	{
		set_Value (COLUMNNAME_RRStartDate, RRStartDate);
	}

	@Override
	public java.sql.Timestamp getRRStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_RRStartDate);
	}

	@Override
	public void setS_ResourceAssignment_ID (final int S_ResourceAssignment_ID)
	{
		if (S_ResourceAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, S_ResourceAssignment_ID);
	}

	@Override
	public int getS_ResourceAssignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceAssignment_ID);
	}

	@Override
	public void setTaxAmt (final BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(final org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(final org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}

	@Override
	public void setUserElementString1 (final java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}
}