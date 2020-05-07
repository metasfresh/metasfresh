/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Cost
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Cost extends org.compiere.model.PO implements I_M_Cost, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -899018241L;

    /** Standard Constructor */
    public X_M_Cost (Properties ctx, int M_Cost_ID, String trxName)
    {
      super (ctx, M_Cost_ID, trxName);
      /** if (M_Cost_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setCurrentCostPrice (BigDecimal.ZERO);
			setCurrentCostPriceLL (BigDecimal.ZERO);
			setCurrentQty (BigDecimal.ZERO);
			setFutureCostPrice (BigDecimal.ZERO);
			setM_AttributeSetInstance_ID (0);
			setM_Cost_ID (0);
			setM_CostElement_ID (0);
			setM_CostType_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Cost (Properties ctx, ResultSet rs, String trxName)
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

		throw new IllegalArgumentException ("CostingMethod is virtual column");	}

	/** Get Kostenrechnungsmethode.
		@return Indicates how Costs will be calculated
	  */
	@Override
	public java.lang.String getCostingMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingMethod);
	}

	/** Set Betrag Kumuliert.
		@param CumulatedAmt 
		Betrag Kumuliert
	  */
	@Override
	public void setCumulatedAmt (java.math.BigDecimal CumulatedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedAmt, CumulatedAmt);
	}

	/** Get Betrag Kumuliert.
		@return Betrag Kumuliert
	  */
	@Override
	public java.math.BigDecimal getCumulatedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CumulatedAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge Kumuliert.
		@param CumulatedQty 
		Menge Kumuliert
	  */
	@Override
	public void setCumulatedQty (java.math.BigDecimal CumulatedQty)
	{
		set_ValueNoCheck (COLUMNNAME_CumulatedQty, CumulatedQty);
	}

	/** Get Menge Kumuliert.
		@return Menge Kumuliert
	  */
	@Override
	public java.math.BigDecimal getCumulatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CumulatedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Kostenpreis aktuell.
		@param CurrentCostPrice 
		Der gegenwärtig verwendete Kostenpreis
	  */
	@Override
	public void setCurrentCostPrice (java.math.BigDecimal CurrentCostPrice)
	{
		set_Value (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	/** Get Kostenpreis aktuell.
		@return Der gegenwärtig verwendete Kostenpreis
	  */
	@Override
	public java.math.BigDecimal getCurrentCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentCostPrice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Current Cost Price Lower Level.
		@param CurrentCostPriceLL 
		Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	  */
	@Override
	public void setCurrentCostPriceLL (java.math.BigDecimal CurrentCostPriceLL)
	{
		set_Value (COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
	}

	/** Get Current Cost Price Lower Level.
		@return Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	  */
	@Override
	public java.math.BigDecimal getCurrentCostPriceLL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentCostPriceLL);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge aktuell.
		@param CurrentQty 
		Menge aktuell
	  */
	@Override
	public void setCurrentQty (java.math.BigDecimal CurrentQty)
	{
		set_Value (COLUMNNAME_CurrentQty, CurrentQty);
	}

	/** Get Menge aktuell.
		@return Menge aktuell
	  */
	@Override
	public java.math.BigDecimal getCurrentQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Kostenpreis Zukünftig.
		@param FutureCostPrice Kostenpreis Zukünftig	  */
	@Override
	public void setFutureCostPrice (java.math.BigDecimal FutureCostPrice)
	{
		set_Value (COLUMNNAME_FutureCostPrice, FutureCostPrice);
	}

	/** Get Kostenpreis Zukünftig.
		@return Kostenpreis Zukünftig	  */
	@Override
	public java.math.BigDecimal getFutureCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FutureCostPrice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Future Cost Price Lower Level.
		@param FutureCostPriceLL Future Cost Price Lower Level	  */
	@Override
	public void setFutureCostPriceLL (java.math.BigDecimal FutureCostPriceLL)
	{
		set_Value (COLUMNNAME_FutureCostPriceLL, FutureCostPriceLL);
	}

	/** Get Future Cost Price Lower Level.
		@return Future Cost Price Lower Level	  */
	@Override
	public java.math.BigDecimal getFutureCostPriceLL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FutureCostPriceLL);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Kosten fixiert.
		@param IsCostFrozen Kosten fixiert	  */
	@Override
	public void setIsCostFrozen (boolean IsCostFrozen)
	{
		set_Value (COLUMNNAME_IsCostFrozen, Boolean.valueOf(IsCostFrozen));
	}

	/** Get Kosten fixiert.
		@return Kosten fixiert	  */
	@Override
	public boolean isCostFrozen () 
	{
		Object oo = get_Value(COLUMNNAME_IsCostFrozen);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Cost.
		@param M_Cost_ID Product Cost	  */
	@Override
	public void setM_Cost_ID (int M_Cost_ID)
	{
		if (M_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Cost_ID, Integer.valueOf(M_Cost_ID));
	}

	/** Get Product Cost.
		@return Product Cost	  */
	@Override
	public int getM_Cost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Cost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	/** Set Kostenart.
		@param M_CostElement_ID 
		Product Cost Element
	  */
	@Override
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
	}

	/** Get Kostenart.
		@return Product Cost Element
	  */
	@Override
	public int getM_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_CostType getM_CostType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class);
	}

	@Override
	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType)
	{
		set_ValueFromPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class, M_CostType);
	}

	/** Set Kostenkategorie.
		@param M_CostType_ID 
		Type of Cost (e.g. Current, Plan, Future)
	  */
	@Override
	public void setM_CostType_ID (int M_CostType_ID)
	{
		if (M_CostType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
	}

	/** Get Kostenkategorie.
		@return Type of Cost (e.g. Current, Plan, Future)
	  */
	@Override
	public int getM_CostType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Percent.
		@param Percent 
		Percentage
	  */
	@Override
	public void setPercent (int Percent)
	{
		set_Value (COLUMNNAME_Percent, Integer.valueOf(Percent));
	}

	/** Get Percent.
		@return Percentage
	  */
	@Override
	public int getPercent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Percent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		throw new IllegalArgumentException ("Processed is virtual column");	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}