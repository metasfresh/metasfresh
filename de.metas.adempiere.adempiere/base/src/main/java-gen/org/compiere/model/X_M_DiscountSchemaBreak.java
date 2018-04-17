/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_DiscountSchemaBreak
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DiscountSchemaBreak extends org.compiere.model.PO implements I_M_DiscountSchemaBreak, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2123339649L;

    /** Standard Constructor */
    public X_M_DiscountSchemaBreak (Properties ctx, int M_DiscountSchemaBreak_ID, String trxName)
    {
      super (ctx, M_DiscountSchemaBreak_ID, trxName);
      /** if (M_DiscountSchemaBreak_ID == 0)
        {
			setBreakDiscount (BigDecimal.ZERO);
			setBreakValue (BigDecimal.ZERO);
			setIsBPartnerFlatDiscount (false); // N
			setIsPriceOverride (false); // N
			setM_DiscountSchema_ID (0);
			setM_DiscountSchemaBreak_ID (0);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_DiscountSchemaBreak WHERE M_DiscountSchema_ID=@M_DiscountSchema_ID@
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchemaBreak (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_PricingSystem getBase_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Base_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_Base_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, Base_PricingSystem);
	}

	/** Set Base_PricingSystem_ID.
		@param Base_PricingSystem_ID Base_PricingSystem_ID	  */
	@Override
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID)
	{
		if (Base_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, Integer.valueOf(Base_PricingSystem_ID));
	}

	/** Get Base_PricingSystem_ID.
		@return Base_PricingSystem_ID	  */
	@Override
	public int getBase_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Base_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Break Discount %.
		@param BreakDiscount 
		Trade Discount in Percent for the break level
	  */
	@Override
	public void setBreakDiscount (java.math.BigDecimal BreakDiscount)
	{
		set_Value (COLUMNNAME_BreakDiscount, BreakDiscount);
	}

	/** Get Break Discount %.
		@return Trade Discount in Percent for the break level
	  */
	@Override
	public java.math.BigDecimal getBreakDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Break Value.
		@param BreakValue 
		Low Value of trade discount break level
	  */
	@Override
	public void setBreakValue (java.math.BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	/** Get Break Value.
		@return Low Value of trade discount break level
	  */
	@Override
	public java.math.BigDecimal getBreakValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fester Rabatt.
		@param IsBPartnerFlatDiscount 
		Use flat discount defined on Business Partner Level
	  */
	@Override
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount)
	{
		set_Value (COLUMNNAME_IsBPartnerFlatDiscount, Boolean.valueOf(IsBPartnerFlatDiscount));
	}

	/** Get Fester Rabatt.
		@return Use flat discount defined on Business Partner Level
	  */
	@Override
	public boolean isBPartnerFlatDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerFlatDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPriceOverride.
		@param IsPriceOverride IsPriceOverride	  */
	@Override
	public void setIsPriceOverride (boolean IsPriceOverride)
	{
		set_Value (COLUMNNAME_IsPriceOverride, Boolean.valueOf(IsPriceOverride));
	}

	/** Get IsPriceOverride.
		@return IsPriceOverride	  */
	@Override
	public boolean isPriceOverride () 
	{
		Object oo = get_Value(COLUMNNAME_IsPriceOverride);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeValue getM_AttributeValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class);
	}

	@Override
	public void setM_AttributeValue(org.compiere.model.I_M_AttributeValue M_AttributeValue)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class, M_AttributeValue);
	}

	/** Set Merkmals-Wert.
		@param M_AttributeValue_ID 
		Product Attribute Value
	  */
	@Override
	public void setM_AttributeValue_ID (int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeValue_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
	}

	/** Get Merkmals-Wert.
		@return Product Attribute Value
	  */
	@Override
	public int getM_AttributeValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	/** Set Rabatt Schema.
		@param M_DiscountSchema_ID 
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt Schema.
		@return Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	@Override
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	@Override
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
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

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** 
	 * PriceBase AD_Reference_ID=540847
	 * Reference name: M_DiscountSchemaBreak_PriceBase
	 */
	public static final int PRICEBASE_AD_Reference_ID=540847;
	/** Pricing System = P */
	public static final String PRICEBASE_PricingSystem = "P";
	/** Fixed = F */
	public static final String PRICEBASE_Fixed = "F";
	/** Set PriceBase.
		@param PriceBase PriceBase	  */
	@Override
	public void setPriceBase (java.lang.String PriceBase)
	{

		set_Value (COLUMNNAME_PriceBase, PriceBase);
	}

	/** Get PriceBase.
		@return PriceBase	  */
	@Override
	public java.lang.String getPriceBase () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriceBase);
	}

	/** Set Standardpreis.
		@param PriceStd 
		Standardpreis
	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standardpreis
	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QualityIssuePercentage.
		@param QualityIssuePercentage QualityIssuePercentage	  */
	@Override
	public void setQualityIssuePercentage (java.math.BigDecimal QualityIssuePercentage)
	{
		set_Value (COLUMNNAME_QualityIssuePercentage, QualityIssuePercentage);
	}

	/** Get QualityIssuePercentage.
		@return QualityIssuePercentage	  */
	@Override
	public java.math.BigDecimal getQualityIssuePercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityIssuePercentage);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aufschlag auf Standardpreis.
		@param Std_AddAmt 
		Amount added to a price as a surcharge
	  */
	@Override
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt)
	{
		set_Value (COLUMNNAME_Std_AddAmt, Std_AddAmt);
	}

	/** Get Aufschlag auf Standardpreis.
		@return Amount added to a price as a surcharge
	  */
	@Override
	public java.math.BigDecimal getStd_AddAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_AddAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}