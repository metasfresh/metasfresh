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
	private static final long serialVersionUID = -1483333538L;

    /** Standard Constructor */
    public X_M_DiscountSchemaBreak (Properties ctx, int M_DiscountSchemaBreak_ID, String trxName)
    {
      super (ctx, M_DiscountSchemaBreak_ID, trxName);
      /** if (M_DiscountSchemaBreak_ID == 0)
        {
			setBreakDiscount (BigDecimal.ZERO);
			setBreakValue (BigDecimal.ZERO);
			setIsBPartnerFlatDiscount (false); // N
			setIsValid (true); // Y
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

	/** Set Preissystem.
		@param Base_PricingSystem_ID Preissystem	  */
	@Override
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID)
	{
		if (Base_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, Integer.valueOf(Base_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Preissystem	  */
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

	/** Set Mengenstufe.
		@param BreakValue 
		Mindestmenge ab der die Kondition gilt
	  */
	@Override
	public void setBreakValue (java.math.BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	/** Get Mengenstufe.
		@return Mindestmenge ab der die Kondition gilt
	  */
	@Override
	public java.math.BigDecimal getBreakValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Gültig.
		@param IsValid 
		Element ist gültig
	  */
	@Override
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Gültig.
		@return Element ist gültig
	  */
	@Override
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
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

	/** Set Hersteller.
		@param Manufacturer_ID 
		Hersteller des Produktes
	  */
	@Override
	public void setManufacturer_ID (int Manufacturer_ID)
	{
		if (Manufacturer_ID < 1) 
			set_Value (COLUMNNAME_Manufacturer_ID, null);
		else 
			set_Value (COLUMNNAME_Manufacturer_ID, Integer.valueOf(Manufacturer_ID));
	}

	/** Get Hersteller.
		@return Hersteller des Produktes
	  */
	@Override
	public int getManufacturer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Manufacturer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Not valid reason.
		@param NotValidReason Not valid reason	  */
	@Override
	public void setNotValidReason (java.lang.String NotValidReason)
	{
		set_Value (COLUMNNAME_NotValidReason, NotValidReason);
	}

	/** Get Not valid reason.
		@return Not valid reason	  */
	@Override
	public java.lang.String getNotValidReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NotValidReason);
	}

	/** Set Skonto %.
		@param PaymentDiscount Skonto %	  */
	@Override
	public void setPaymentDiscount (java.math.BigDecimal PaymentDiscount)
	{
		set_Value (COLUMNNAME_PaymentDiscount, PaymentDiscount);
	}

	/** Get Skonto %.
		@return Skonto %	  */
	@Override
	public java.math.BigDecimal getPaymentDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaymentDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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
	/** Set Preisgrundlage.
		@param PriceBase Preisgrundlage	  */
	@Override
	public void setPriceBase (java.lang.String PriceBase)
	{

		set_Value (COLUMNNAME_PriceBase, PriceBase);
	}

	/** Get Preisgrundlage.
		@return Preisgrundlage	  */
	@Override
	public java.lang.String getPriceBase () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriceBase);
	}

	/** Set Festpreis.
		@param PriceStdFixed 
		Festpreis, ohne ggf. zusätzliche Rabatte
	  */
	@Override
	public void setPriceStdFixed (java.math.BigDecimal PriceStdFixed)
	{
		set_Value (COLUMNNAME_PriceStdFixed, PriceStdFixed);
	}

	/** Get Festpreis.
		@return Festpreis, ohne ggf. zusätzliche Rabatte
	  */
	@Override
	public java.math.BigDecimal getPriceStdFixed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStdFixed);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preisaufschlag.
		@param PricingSystemSurchargeAmt 
		Aufschlag auf den Preis, der aus dem Preissystem resultieren würde
	  */
	@Override
	public void setPricingSystemSurchargeAmt (java.math.BigDecimal PricingSystemSurchargeAmt)
	{
		set_Value (COLUMNNAME_PricingSystemSurchargeAmt, PricingSystemSurchargeAmt);
	}

	/** Get Preisaufschlag.
		@return Aufschlag auf den Preis, der aus dem Preissystem resultieren würde
	  */
	@Override
	public java.math.BigDecimal getPricingSystemSurchargeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricingSystemSurchargeAmt);
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
}