// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Category_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Category_Acct extends org.compiere.model.PO implements I_M_Product_Category_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1146295676L;

    /** Standard Constructor */
    public X_M_Product_Category_Acct (final Properties ctx, final int M_Product_Category_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Category_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Category_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * CostingLevel AD_Reference_ID=355
	 * Reference name: C_AcctSchema CostingLevel
	 */
	public static final int COSTINGLEVEL_AD_Reference_ID=355;
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** BatchLot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	@Override
	public void setCostingLevel (final @Nullable java.lang.String CostingLevel)
	{
		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	@Override
	public java.lang.String getCostingLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CostingLevel);
	}

	/** 
	 * CostingMethod AD_Reference_ID=122
	 * Reference name: C_AcctSchema Costing Method
	 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** StandardCosting = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** AveragePO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** LastPOPrice = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** AverageInvoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** LastInvoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** UserDefined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	/** MovingAverageInvoice = M */
	public static final String COSTINGMETHOD_MovingAverageInvoice = "M";
	@Override
	public void setCostingMethod (final @Nullable java.lang.String CostingMethod)
	{
		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	@Override
	public java.lang.String getCostingMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostingMethod);
	}

	@Override
	public void setM_Product_Category_Acct_ID (final int M_Product_Category_Acct_ID)
	{
		if (M_Product_Category_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_Acct_ID, M_Product_Category_Acct_ID);
	}

	@Override
	public int getM_Product_Category_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_Acct_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Asset_A(final org.compiere.model.I_C_ValidCombination P_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, P_Asset_A);
	}

	@Override
	public void setP_Asset_Acct (final int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, P_Asset_Acct);
	}

	@Override
	public int getP_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Burden_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Burden_A(final org.compiere.model.I_C_ValidCombination P_Burden_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class, P_Burden_A);
	}

	@Override
	public void setP_Burden_Acct (final int P_Burden_Acct)
	{
		set_Value (COLUMNNAME_P_Burden_Acct, P_Burden_Acct);
	}

	@Override
	public int getP_Burden_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Burden_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_COGS_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_COGS_A(final org.compiere.model.I_C_ValidCombination P_COGS_A)
	{
		set_ValueFromPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class, P_COGS_A);
	}

	@Override
	public void setP_COGS_Acct (final int P_COGS_Acct)
	{
		set_Value (COLUMNNAME_P_COGS_Acct, P_COGS_Acct);
	}

	@Override
	public int getP_COGS_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_COGS_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostAdjustment_A(final org.compiere.model.I_C_ValidCombination P_CostAdjustment_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostAdjustment_A);
	}

	@Override
	public void setP_CostAdjustment_Acct (final int P_CostAdjustment_Acct)
	{
		set_Value (COLUMNNAME_P_CostAdjustment_Acct, P_CostAdjustment_Acct);
	}

	@Override
	public int getP_CostAdjustment_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostAdjustment_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostOfProduction_A(final org.compiere.model.I_C_ValidCombination P_CostOfProduction_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostOfProduction_A);
	}

	@Override
	public void setP_CostOfProduction_Acct (final int P_CostOfProduction_Acct)
	{
		set_Value (COLUMNNAME_P_CostOfProduction_Acct, P_CostOfProduction_Acct);
	}

	@Override
	public int getP_CostOfProduction_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostOfProduction_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Expense_A(final org.compiere.model.I_C_ValidCombination P_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, P_Expense_A);
	}

	@Override
	public void setP_Expense_Acct (final int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, P_Expense_Acct);
	}

	@Override
	public int getP_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_ExternallyOwnedStock_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_ExternallyOwnedStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_ExternallyOwnedStock_A(final org.compiere.model.I_C_ValidCombination P_ExternallyOwnedStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_ExternallyOwnedStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_ExternallyOwnedStock_A);
	}

	@Override
	public void setP_ExternallyOwnedStock_Acct (final int P_ExternallyOwnedStock_Acct)
	{
		set_Value (COLUMNNAME_P_ExternallyOwnedStock_Acct, P_ExternallyOwnedStock_Acct);
	}

	@Override
	public int getP_ExternallyOwnedStock_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_ExternallyOwnedStock_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_FloorStock_A(final org.compiere.model.I_C_ValidCombination P_FloorStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_FloorStock_A);
	}

	@Override
	public void setP_FloorStock_Acct (final int P_FloorStock_Acct)
	{
		set_Value (COLUMNNAME_P_FloorStock_Acct, P_FloorStock_Acct);
	}

	@Override
	public int getP_FloorStock_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_FloorStock_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InventoryClearing_A(final org.compiere.model.I_C_ValidCombination P_InventoryClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_InventoryClearing_A);
	}

	@Override
	public void setP_InventoryClearing_Acct (final int P_InventoryClearing_Acct)
	{
		set_Value (COLUMNNAME_P_InventoryClearing_Acct, P_InventoryClearing_Acct);
	}

	@Override
	public int getP_InventoryClearing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_InventoryClearing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InvoicePriceVariance_A(final org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_InvoicePriceVariance_A);
	}

	@Override
	public void setP_InvoicePriceVariance_Acct (final int P_InvoicePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_InvoicePriceVariance_Acct, P_InvoicePriceVariance_Acct);
	}

	@Override
	public int getP_InvoicePriceVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_InvoicePriceVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Labor_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Labor_A(final org.compiere.model.I_C_ValidCombination P_Labor_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class, P_Labor_A);
	}

	@Override
	public void setP_Labor_Acct (final int P_Labor_Acct)
	{
		set_Value (COLUMNNAME_P_Labor_Acct, P_Labor_Acct);
	}

	@Override
	public int getP_Labor_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Labor_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MethodChangeVariance_A(final org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MethodChangeVariance_A);
	}

	@Override
	public void setP_MethodChangeVariance_Acct (final int P_MethodChangeVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MethodChangeVariance_Acct, P_MethodChangeVariance_Acct);
	}

	@Override
	public int getP_MethodChangeVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_MethodChangeVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MixVariance_A(final org.compiere.model.I_C_ValidCombination P_MixVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MixVariance_A);
	}

	@Override
	public void setP_MixVariance_Acct (final int P_MixVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MixVariance_Acct, P_MixVariance_Acct);
	}

	@Override
	public int getP_MixVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_MixVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_OutsideProcessing_A(final org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class, P_OutsideProcessing_A);
	}

	@Override
	public void setP_OutsideProcessing_Acct (final int P_OutsideProcessing_Acct)
	{
		set_Value (COLUMNNAME_P_OutsideProcessing_Acct, P_OutsideProcessing_Acct);
	}

	@Override
	public int getP_OutsideProcessing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_OutsideProcessing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Overhead_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Overhead_A(final org.compiere.model.I_C_ValidCombination P_Overhead_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class, P_Overhead_A);
	}

	@Override
	public void setP_Overhead_Acct (final int P_Overhead_Acct)
	{
		set_Value (COLUMNNAME_P_Overhead_Acct, P_Overhead_Acct);
	}

	@Override
	public int getP_Overhead_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Overhead_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_PurchasePriceVariance_A(final org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_PurchasePriceVariance_A);
	}

	@Override
	public void setP_PurchasePriceVariance_Acct (final int P_PurchasePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_PurchasePriceVariance_Acct, P_PurchasePriceVariance_Acct);
	}

	@Override
	public int getP_PurchasePriceVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_PurchasePriceVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_RateVariance_A(final org.compiere.model.I_C_ValidCombination P_RateVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_RateVariance_A);
	}

	@Override
	public void setP_RateVariance_Acct (final int P_RateVariance_Acct)
	{
		set_Value (COLUMNNAME_P_RateVariance_Acct, P_RateVariance_Acct);
	}

	@Override
	public int getP_RateVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_RateVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Revenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Revenue_A(final org.compiere.model.I_C_ValidCombination P_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, P_Revenue_A);
	}

	@Override
	public void setP_Revenue_Acct (final int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, P_Revenue_Acct);
	}

	@Override
	public int getP_Revenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Revenue_Acct);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Scrap_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Scrap_A(final org.compiere.model.I_C_ValidCombination P_Scrap_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class, P_Scrap_A);
	}

	@Override
	public void setP_Scrap_Acct (final int P_Scrap_Acct)
	{
		set_Value (COLUMNNAME_P_Scrap_Acct, P_Scrap_Acct);
	}

	@Override
	public int getP_Scrap_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Scrap_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountGrant_A(final org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountGrant_A);
	}

	@Override
	public void setP_TradeDiscountGrant_Acct (final int P_TradeDiscountGrant_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountGrant_Acct, P_TradeDiscountGrant_Acct);
	}

	@Override
	public int getP_TradeDiscountGrant_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_TradeDiscountGrant_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountRec_A(final org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountRec_A);
	}

	@Override
	public void setP_TradeDiscountRec_Acct (final int P_TradeDiscountRec_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountRec_Acct, P_TradeDiscountRec_Acct);
	}

	@Override
	public int getP_TradeDiscountRec_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_TradeDiscountRec_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_UsageVariance_A(final org.compiere.model.I_C_ValidCombination P_UsageVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_UsageVariance_A);
	}

	@Override
	public void setP_UsageVariance_Acct (final int P_UsageVariance_Acct)
	{
		set_Value (COLUMNNAME_P_UsageVariance_Acct, P_UsageVariance_Acct);
	}

	@Override
	public int getP_UsageVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_UsageVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_WIP_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_WIP_A(final org.compiere.model.I_C_ValidCombination P_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, P_WIP_A);
	}

	@Override
	public void setP_WIP_Acct (final int P_WIP_Acct)
	{
		set_Value (COLUMNNAME_P_WIP_Acct, P_WIP_Acct);
	}

	@Override
	public int getP_WIP_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_WIP_Acct);
	}
}