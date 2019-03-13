/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_Cost
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Cost extends org.compiere.model.PO implements I_PP_Order_Cost, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 181610332L;

    /** Standard Constructor */
    public X_PP_Order_Cost (Properties ctx, int PP_Order_Cost_ID, String trxName)
    {
      super (ctx, PP_Order_Cost_ID, trxName);
      /** if (PP_Order_Cost_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setM_CostType_ID (0);
			setM_Product_ID (0);
			setPostCalculationAmt (BigDecimal.ZERO); // 0
			setPP_Order_Cost_ID (0);
			setPP_Order_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PP_Order_Cost (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
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

	/** Set Cost Distribution Percent.
		@param CostDistributionPercent Cost Distribution Percent	  */
	@Override
	public void setCostDistributionPercent (java.math.BigDecimal CostDistributionPercent)
	{
		set_Value (COLUMNNAME_CostDistributionPercent, CostDistributionPercent);
	}

	/** Get Cost Distribution Percent.
		@return Cost Distribution Percent	  */
	@Override
	public java.math.BigDecimal getCostDistributionPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostDistributionPercent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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
		set_ValueNoCheck (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
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
		set_ValueNoCheck (COLUMNNAME_CurrentCostPriceLL, CurrentCostPriceLL);
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
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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
			set_Value (COLUMNNAME_M_CostType_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
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

	/** Set Post Calculation Amount.
		@param PostCalculationAmt Post Calculation Amount	  */
	@Override
	public void setPostCalculationAmt (java.math.BigDecimal PostCalculationAmt)
	{
		set_Value (COLUMNNAME_PostCalculationAmt, PostCalculationAmt);
	}

	/** Get Post Calculation Amount.
		@return Post Calculation Amount	  */
	@Override
	public java.math.BigDecimal getPostCalculationAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PostCalculationAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Manufacturing Order Cost.
		@param PP_Order_Cost_ID Manufacturing Order Cost	  */
	@Override
	public void setPP_Order_Cost_ID (int PP_Order_Cost_ID)
	{
		if (PP_Order_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Cost_ID, Integer.valueOf(PP_Order_Cost_ID));
	}

	/** Get Manufacturing Order Cost.
		@return Manufacturing Order Cost	  */
	@Override
	public int getPP_Order_Cost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Cost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PP_Order_Cost_TrxType AD_Reference_ID=540941
	 * Reference name: PP_Order_Cost_TrxType
	 */
	public static final int PP_ORDER_COST_TRXTYPE_AD_Reference_ID=540941;
	/** MainProduct = MR */
	public static final String PP_ORDER_COST_TRXTYPE_MainProduct = "MR";
	/** MaterialIssue = MI */
	public static final String PP_ORDER_COST_TRXTYPE_MaterialIssue = "MI";
	/** ResourceUtilization = RU */
	public static final String PP_ORDER_COST_TRXTYPE_ResourceUtilization = "RU";
	/** ByProduct = BY */
	public static final String PP_ORDER_COST_TRXTYPE_ByProduct = "BY";
	/** CoProduct = CO */
	public static final String PP_ORDER_COST_TRXTYPE_CoProduct = "CO";
	/** Set Transaction Type.
		@param PP_Order_Cost_TrxType Transaction Type	  */
	@Override
	public void setPP_Order_Cost_TrxType (java.lang.String PP_Order_Cost_TrxType)
	{

		set_Value (COLUMNNAME_PP_Order_Cost_TrxType, PP_Order_Cost_TrxType);
	}

	/** Get Transaction Type.
		@return Transaction Type	  */
	@Override
	public java.lang.String getPP_Order_Cost_TrxType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PP_Order_Cost_TrxType);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}