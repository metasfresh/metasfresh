// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InvoiceLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InvoiceLine extends org.compiere.model.PO implements I_C_InvoiceLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 146765435L;

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
	public void setA_CapvsExp (final @Nullable java.lang.String A_CapvsExp)
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
	public void setC_BPartner2_ID (final int C_BPartner2_ID)
	{
		if (C_BPartner2_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner2_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner2_ID, C_BPartner2_ID);
	}

	@Override
	public int getC_BPartner2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner2_ID);
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
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Harvesting_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Harvesting_Calendar(final org.compiere.model.I_C_Calendar C_Harvesting_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Harvesting_Calendar);
	}

	@Override
	public void setC_Harvesting_Calendar_ID (final int C_Harvesting_Calendar_ID)
	{
		if (C_Harvesting_Calendar_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Harvesting_Calendar_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Harvesting_Calendar_ID, C_Harvesting_Calendar_ID);
	}

	@Override
	public int getC_Harvesting_Calendar_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Harvesting_Calendar_ID);
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
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
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(final org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (final int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
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
	public void setC_UOM_BPartner_ID (final int C_UOM_BPartner_ID)
	{
		if (C_UOM_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, C_UOM_BPartner_ID);
	}

	@Override
	public int getC_UOM_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_BPartner_ID);
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
	public void setC_VAT_Code_ID (final int C_VAT_Code_ID)
	{
		if (C_VAT_Code_ID < 1) 
			set_Value (COLUMNNAME_C_VAT_Code_ID, null);
		else 
			set_Value (COLUMNNAME_C_VAT_Code_ID, C_VAT_Code_ID);
	}

	@Override
	public int getC_VAT_Code_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_VAT_Code_ID);
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
	public void setDiscount (final @Nullable BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setExternalIds (final @Nullable java.lang.String ExternalIds)
	{
		set_Value (COLUMNNAME_ExternalIds, ExternalIds);
	}

	@Override
	public java.lang.String getExternalIds() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalIds);
	}

	@Override
	public org.compiere.model.I_C_Year getHarvesting_Year()
	{
		return get_ValueAsPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setHarvesting_Year(final org.compiere.model.I_C_Year Harvesting_Year)
	{
		set_ValueFromPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class, Harvesting_Year);
	}

	@Override
	public void setHarvesting_Year_ID (final int Harvesting_Year_ID)
	{
		if (Harvesting_Year_ID < 1)
			set_ValueNoCheck (COLUMNNAME_Harvesting_Year_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Harvesting_Year_ID, Harvesting_Year_ID);
	}

	@Override
	public int getHarvesting_Year_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Harvesting_Year_ID);
	}

	public void setIsDescription (final boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, IsDescription);
	}

	/**
	 * InvoicingGroup AD_Reference_ID=541742
	 * Reference name: InvoicingGroup
	 */
	public static final int INVOICINGGROUP_AD_Reference_ID=541742;
	/** Service = Service */
	public static final String INVOICINGGROUP_Service = "Service";
	/** Costs = Costs */
	public static final String INVOICINGGROUP_Costs = "Costs";
	@Override
	public void setInvoicingGroup (final @Nullable java.lang.String InvoicingGroup)
	{
		set_Value (COLUMNNAME_InvoicingGroup, InvoicingGroup);
	}

	@Override
	public java.lang.String getInvoicingGroup()
	{
		return get_ValueAsString(COLUMNNAME_InvoicingGroup);
	}

	@Override
	public boolean isDescription()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDescription);
	}

	@Override
	public void setIsFreightCostLine (final boolean IsFreightCostLine)
	{
		throw new IllegalArgumentException ("IsFreightCostLine is virtual column");	}

	@Override
	public boolean isFreightCostLine()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFreightCostLine);
	}

	@Override
	public void setIsManualPrice (final boolean IsManualPrice)
	{
		set_Value (COLUMNNAME_IsManualPrice, IsManualPrice);
	}

	@Override
	public boolean isManualPrice()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualPrice);
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
	public void setIsPackagingMaterial (final boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, IsPackagingMaterial);
	}

	@Override
	public boolean isPackagingMaterial()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPackagingMaterial);
	}

	@Override
	public void setIsPriceReadOnly (final boolean IsPriceReadOnly)
	{
		set_Value (COLUMNNAME_IsPriceReadOnly, IsPriceReadOnly);
	}

	@Override
	public boolean isPriceReadOnly()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPriceReadOnly);
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
	public void setIsQtyReadOnly (final boolean IsQtyReadOnly)
	{
		set_Value (COLUMNNAME_IsQtyReadOnly, IsQtyReadOnly);
	}

	@Override
	public boolean isQtyReadOnly()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQtyReadOnly);
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
	public void setLine_CreditMemoReason (final @Nullable java.lang.String Line_CreditMemoReason)
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
	public void setLineTotalAmt (final @Nullable BigDecimal LineTotalAmt)
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
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1)
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		throw new IllegalArgumentException ("M_InOut_ID is virtual column");	}

	@Override
	public int getM_InOut_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
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
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
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
	public void setPrice_UOM_ID (final int Price_UOM_ID)
	{
		if (Price_UOM_ID < 1)
			set_ValueNoCheck (COLUMNNAME_Price_UOM_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Price_UOM_ID, Price_UOM_ID);
	}

	@Override
	public int getPrice_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Price_UOM_ID);
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
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName()
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
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
	public void setQtyEnteredInBPartnerUOM (final @Nullable BigDecimal QtyEnteredInBPartnerUOM)
	{
		set_Value (COLUMNNAME_QtyEnteredInBPartnerUOM, QtyEnteredInBPartnerUOM);
	}

	@Override
	public BigDecimal getQtyEnteredInBPartnerUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredInBPartnerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEnteredTU (final @Nullable BigDecimal QtyEnteredTU)
	{
		set_Value (COLUMNNAME_QtyEnteredTU, QtyEnteredTU);
	}

	@Override
	public BigDecimal getQtyEnteredTU()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredTU);
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
	public void setQtyInvoicedInPriceUOM (final @Nullable BigDecimal QtyInvoicedInPriceUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInPriceUOM, QtyInvoicedInPriceUOM);
	}

	@Override
	public BigDecimal getQtyInvoicedInPriceUOM()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInPriceUOM);
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
	public void setRRAmt (final @Nullable BigDecimal RRAmt)
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
	public void setRRStartDate (final @Nullable java.sql.Timestamp RRStartDate)
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
	public void setTaxAmt (final @Nullable BigDecimal TaxAmt)
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
	public void setTaxAmtInfo (final @Nullable BigDecimal TaxAmtInfo)
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	@Override
	public BigDecimal getTaxAmtInfo()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setUserElementNumber1 (final @Nullable BigDecimal UserElementNumber1)
	{
		set_Value (COLUMNNAME_UserElementNumber1, UserElementNumber1);
	}

	@Override
	public BigDecimal getUserElementNumber1()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber1);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementNumber2 (final @Nullable BigDecimal UserElementNumber2)
	{
		set_Value (COLUMNNAME_UserElementNumber2, UserElementNumber2);
	}

	@Override
	public BigDecimal getUserElementNumber2()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber2);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementDate1 (final @Nullable java.sql.Timestamp UserElementDate1)
	{
		set_Value (COLUMNNAME_UserElementDate1, UserElementDate1);
	}

	@Override
	public java.sql.Timestamp getUserElementDate1()
	{
		return get_ValueAsTimestamp(COLUMNNAME_UserElementDate1);
	}

	@Override
	public void setUserElementDate2 (final @Nullable java.sql.Timestamp UserElementDate2)
	{
		set_Value (COLUMNNAME_UserElementDate2, UserElementDate2);
	}

	@Override
	public java.sql.Timestamp getUserElementDate2()
	{
		return get_ValueAsTimestamp(COLUMNNAME_UserElementDate2);
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}
}