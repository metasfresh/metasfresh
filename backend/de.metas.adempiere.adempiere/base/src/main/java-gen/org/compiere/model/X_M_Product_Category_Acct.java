/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Category_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Category_Acct extends org.compiere.model.PO implements I_M_Product_Category_Acct, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1585176680L;

    /** Standard Constructor */
    public X_M_Product_Category_Acct (Properties ctx, int M_Product_Category_Acct_ID, String trxName)
    {
      super (ctx, M_Product_Category_Acct_ID, trxName);
      /** if (M_Product_Category_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setM_Product_Category_Acct_ID (0);
			setM_Product_Category_ID (0);
			setP_Asset_Acct (0);
			setP_Burden_Acct (0);
			setP_COGS_Acct (0);
			setP_CostAdjustment_Acct (0);
			setP_CostOfProduction_Acct (0);
			setP_Expense_Acct (0);
			setP_FloorStock_Acct (0);
			setP_InventoryClearing_Acct (0);
			setP_InvoicePriceVariance_Acct (0);
			setP_Labor_Acct (0);
			setP_MethodChangeVariance_Acct (0);
			setP_MixVariance_Acct (0);
			setP_OutsideProcessing_Acct (0);
			setP_Overhead_Acct (0);
			setP_PurchasePriceVariance_Acct (0);
			setP_RateVariance_Acct (0);
			setP_Revenue_Acct (0);
			setP_Scrap_Acct (0);
			setP_TradeDiscountGrant_Acct (0);
			setP_TradeDiscountRec_Acct (0);
			setP_UsageVariance_Acct (0);
			setP_WIP_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Category_Acct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Kostenrechnungsstufe.
		@param CostingLevel 
		The lowest level to accumulate Costing Information
	  */
	@Override
	public void setCostingLevel (java.lang.String CostingLevel)
	{

		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	/** Get Kostenrechnungsstufe.
		@return The lowest level to accumulate Costing Information
	  */
	@Override
	public java.lang.String getCostingLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingLevel);
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
	/** Set Kostenrechnungsmethode.
		@param CostingMethod 
		Indicates how Costs will be calculated
	  */
	@Override
	public void setCostingMethod (java.lang.String CostingMethod)
	{

		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	/** Get Kostenrechnungsmethode.
		@return Indicates how Costs will be calculated
	  */
	@Override
	public java.lang.String getCostingMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingMethod);
	}

	/** Set Product Category Acct.
		@param M_Product_Category_Acct_ID Product Category Acct	  */
	@Override
	public void setM_Product_Category_Acct_ID (int M_Product_Category_Acct_ID)
	{
		if (M_Product_Category_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_Acct_ID, Integer.valueOf(M_Product_Category_Acct_ID));
	}

	/** Get Product Category Acct.
		@return Product Category Acct	  */
	@Override
	public int getM_Product_Category_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Category of a Product
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Asset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, P_Asset_A);
	}

	/** Set Warenbestand.
		@param P_Asset_Acct 
		Konto für Warenbestand
	  */
	@Override
	public void setP_Asset_Acct (int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, Integer.valueOf(P_Asset_Acct));
	}

	/** Get Warenbestand.
		@return Konto für Warenbestand
	  */
	@Override
	public int getP_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Burden_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class, P_Burden_A);
	}

	/** Set Burden.
		@param P_Burden_Acct 
		The Burden account is the account used Manufacturing Order
	  */
	@Override
	public void setP_Burden_Acct (int P_Burden_Acct)
	{
		set_Value (COLUMNNAME_P_Burden_Acct, Integer.valueOf(P_Burden_Acct));
	}

	/** Get Burden.
		@return The Burden account is the account used Manufacturing Order
	  */
	@Override
	public int getP_Burden_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Burden_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_COGS_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A)
	{
		set_ValueFromPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class, P_COGS_A);
	}

	/** Set Produkt Vertriebsausgaben.
		@param P_COGS_Acct 
		Konto für Produkt Vertriebsausgaben
	  */
	@Override
	public void setP_COGS_Acct (int P_COGS_Acct)
	{
		set_Value (COLUMNNAME_P_COGS_Acct, Integer.valueOf(P_COGS_Acct));
	}

	/** Get Produkt Vertriebsausgaben.
		@return Konto für Produkt Vertriebsausgaben
	  */
	@Override
	public int getP_COGS_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_COGS_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostAdjustment_A);
	}

	/** Set Bezugsnebenkosten.
		@param P_CostAdjustment_Acct 
		Konto für Bezugsnebenkosten
	  */
	@Override
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct)
	{
		set_Value (COLUMNNAME_P_CostAdjustment_Acct, Integer.valueOf(P_CostAdjustment_Acct));
	}

	/** Get Bezugsnebenkosten.
		@return Konto für Bezugsnebenkosten
	  */
	@Override
	public int getP_CostAdjustment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostAdjustment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostOfProduction_A);
	}

	/** Set Cost Of Production.
		@param P_CostOfProduction_Acct 
		The Cost Of Production account is the account used Manufacturing Order
	  */
	@Override
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct)
	{
		set_Value (COLUMNNAME_P_CostOfProduction_Acct, Integer.valueOf(P_CostOfProduction_Acct));
	}

	/** Get Cost Of Production.
		@return The Cost Of Production account is the account used Manufacturing Order
	  */
	@Override
	public int getP_CostOfProduction_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostOfProduction_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, P_Expense_A);
	}

	/** Set Produkt Aufwand.
		@param P_Expense_Acct 
		Konto für Produkt Aufwand
	  */
	@Override
	public void setP_Expense_Acct (int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, Integer.valueOf(P_Expense_Acct));
	}

	/** Get Produkt Aufwand.
		@return Konto für Produkt Aufwand
	  */
	@Override
	public int getP_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_FloorStock_A);
	}

	/** Set Floor Stock.
		@param P_FloorStock_Acct 
		The Floor Stock account is the account used Manufacturing Order
	  */
	@Override
	public void setP_FloorStock_Acct (int P_FloorStock_Acct)
	{
		set_Value (COLUMNNAME_P_FloorStock_Acct, Integer.valueOf(P_FloorStock_Acct));
	}

	/** Get Floor Stock.
		@return The Floor Stock account is the account used Manufacturing Order
	  */
	@Override
	public int getP_FloorStock_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_FloorStock_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_InventoryClearing_A);
	}

	/** Set Inventory Clearing.
		@param P_InventoryClearing_Acct 
		Product Inventory Clearing Account
	  */
	@Override
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct)
	{
		set_Value (COLUMNNAME_P_InventoryClearing_Acct, Integer.valueOf(P_InventoryClearing_Acct));
	}

	/** Get Inventory Clearing.
		@return Product Inventory Clearing Account
	  */
	@Override
	public int getP_InventoryClearing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InventoryClearing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_InvoicePriceVariance_A);
	}

	/** Set Preisdifferenz Einkauf Rechnung.
		@param P_InvoicePriceVariance_Acct 
		Konto für Preisdifferenz Einkauf Rechnung
	  */
	@Override
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_InvoicePriceVariance_Acct, Integer.valueOf(P_InvoicePriceVariance_Acct));
	}

	/** Get Preisdifferenz Einkauf Rechnung.
		@return Konto für Preisdifferenz Einkauf Rechnung
	  */
	@Override
	public int getP_InvoicePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InvoicePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Labor_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class, P_Labor_A);
	}

	/** Set Labor.
		@param P_Labor_Acct 
		The Labor account is the account used Manufacturing Order
	  */
	@Override
	public void setP_Labor_Acct (int P_Labor_Acct)
	{
		set_Value (COLUMNNAME_P_Labor_Acct, Integer.valueOf(P_Labor_Acct));
	}

	/** Get Labor.
		@return The Labor account is the account used Manufacturing Order
	  */
	@Override
	public int getP_Labor_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Labor_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MethodChangeVariance_A);
	}

	/** Set Method Change Variance.
		@param P_MethodChangeVariance_Acct 
		The Method Change Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MethodChangeVariance_Acct, Integer.valueOf(P_MethodChangeVariance_Acct));
	}

	/** Get Method Change Variance.
		@return The Method Change Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_MethodChangeVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MethodChangeVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MixVariance_A);
	}

	/** Set Mix Variance.
		@param P_MixVariance_Acct 
		The Mix Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_MixVariance_Acct (int P_MixVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MixVariance_Acct, Integer.valueOf(P_MixVariance_Acct));
	}

	/** Get Mix Variance.
		@return The Mix Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_MixVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MixVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class, P_OutsideProcessing_A);
	}

	/** Set Outside Processing.
		@param P_OutsideProcessing_Acct 
		The Outside Processing Account is the account used in Manufacturing Order
	  */
	@Override
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct)
	{
		set_Value (COLUMNNAME_P_OutsideProcessing_Acct, Integer.valueOf(P_OutsideProcessing_Acct));
	}

	/** Get Outside Processing.
		@return The Outside Processing Account is the account used in Manufacturing Order
	  */
	@Override
	public int getP_OutsideProcessing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_OutsideProcessing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Overhead_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class, P_Overhead_A);
	}

	/** Set Overhead.
		@param P_Overhead_Acct 
		The Overhead account is the account used  in Manufacturing Order 
	  */
	@Override
	public void setP_Overhead_Acct (int P_Overhead_Acct)
	{
		set_Value (COLUMNNAME_P_Overhead_Acct, Integer.valueOf(P_Overhead_Acct));
	}

	/** Get Overhead.
		@return The Overhead account is the account used  in Manufacturing Order 
	  */
	@Override
	public int getP_Overhead_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Overhead_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_PurchasePriceVariance_A);
	}

	/** Set Preisdifferenz Bestellung.
		@param P_PurchasePriceVariance_Acct 
		Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	  */
	@Override
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_PurchasePriceVariance_Acct, Integer.valueOf(P_PurchasePriceVariance_Acct));
	}

	/** Get Preisdifferenz Bestellung.
		@return Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	  */
	@Override
	public int getP_PurchasePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_PurchasePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_RateVariance_A);
	}

	/** Set Rate Variance.
		@param P_RateVariance_Acct 
		The Rate Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_RateVariance_Acct (int P_RateVariance_Acct)
	{
		set_Value (COLUMNNAME_P_RateVariance_Acct, Integer.valueOf(P_RateVariance_Acct));
	}

	/** Get Rate Variance.
		@return The Rate Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_RateVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_RateVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Revenue_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, P_Revenue_A);
	}

	/** Set Produkt Ertrag.
		@param P_Revenue_Acct 
		Konto für Produkt Ertrag
	  */
	@Override
	public void setP_Revenue_Acct (int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
	}

	/** Get Produkt Ertrag.
		@return Konto für Produkt Ertrag
	  */
	@Override
	public int getP_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Scrap_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class, P_Scrap_A);
	}

	/** Set Scrap.
		@param P_Scrap_Acct 
		The Scrap account is the account used  in Manufacturing Order 
	  */
	@Override
	public void setP_Scrap_Acct (int P_Scrap_Acct)
	{
		set_Value (COLUMNNAME_P_Scrap_Acct, Integer.valueOf(P_Scrap_Acct));
	}

	/** Get Scrap.
		@return The Scrap account is the account used  in Manufacturing Order 
	  */
	@Override
	public int getP_Scrap_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Scrap_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountGrant_A);
	}

	/** Set Gewährte Rabatte.
		@param P_TradeDiscountGrant_Acct 
		Konto für gewährte Rabatte
	  */
	@Override
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountGrant_Acct, Integer.valueOf(P_TradeDiscountGrant_Acct));
	}

	/** Get Gewährte Rabatte.
		@return Konto für gewährte Rabatte
	  */
	@Override
	public int getP_TradeDiscountGrant_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountGrant_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountRec_A);
	}

	/** Set Erhaltene Rabatte.
		@param P_TradeDiscountRec_Acct 
		Konto für erhaltene Rabatte
	  */
	@Override
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountRec_Acct, Integer.valueOf(P_TradeDiscountRec_Acct));
	}

	/** Get Erhaltene Rabatte.
		@return Konto für erhaltene Rabatte
	  */
	@Override
	public int getP_TradeDiscountRec_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountRec_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_UsageVariance_A);
	}

	/** Set Usage Variance.
		@param P_UsageVariance_Acct 
		The Usage Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct)
	{
		set_Value (COLUMNNAME_P_UsageVariance_Acct, Integer.valueOf(P_UsageVariance_Acct));
	}

	/** Get Usage Variance.
		@return The Usage Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_UsageVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_UsageVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_WIP_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, P_WIP_A);
	}

	/** Set Unfertige Leistungen.
		@param P_WIP_Acct 
		Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	  */
	@Override
	public void setP_WIP_Acct (int P_WIP_Acct)
	{
		set_Value (COLUMNNAME_P_WIP_Acct, Integer.valueOf(P_WIP_Acct));
	}

	/** Get Unfertige Leistungen.
		@return Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	  */
	@Override
	public int getP_WIP_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_WIP_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}