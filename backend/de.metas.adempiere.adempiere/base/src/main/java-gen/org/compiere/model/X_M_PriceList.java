/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PriceList
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_PriceList extends org.compiere.model.PO implements I_M_PriceList, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1775524734L;

    /** Standard Constructor */
    public X_M_PriceList (Properties ctx, int M_PriceList_ID, String trxName)
    {
      super (ctx, M_PriceList_ID, trxName);
      /** if (M_PriceList_ID == 0)
        {
			setC_Currency_ID (0);
			setEnforcePriceLimit (false);
			setIsDefault (false);
			setIsRoundNetAmountToCurrencyPrecision (false); // N
			setIsSOPriceList (false);
			setIsTaxIncluded (false);
			setM_PriceList_ID (0);
			setM_PricingSystem_ID (0);
			setName (null);
			setPricePrecision (0); // 2
        } */
    }

    /** Load Constructor */
    public X_M_PriceList (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Basis Preisliste.
		@param BasePriceList_ID 
		Pricelist to be used, if product not found on this pricelist
	  */
	@Override
	public void setBasePriceList_ID (int BasePriceList_ID)
	{
		if (BasePriceList_ID < 1) 
			set_Value (COLUMNNAME_BasePriceList_ID, null);
		else 
			set_Value (COLUMNNAME_BasePriceList_ID, Integer.valueOf(BasePriceList_ID));
	}

	/** Get Basis Preisliste.
		@return Pricelist to be used, if product not found on this pricelist
	  */
	@Override
	public int getBasePriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BasePriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
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
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Standard Steuerkategorie.
		@param Default_TaxCategory_ID Standard Steuerkategorie	  */
	@Override
	public void setDefault_TaxCategory_ID (int Default_TaxCategory_ID)
	{
		if (Default_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_Default_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_Default_TaxCategory_ID, Integer.valueOf(Default_TaxCategory_ID));
	}

	/** Get Standard Steuerkategorie.
		@return Standard Steuerkategorie	  */
	@Override
	public int getDefault_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Default_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Preislimit erzwingen.
		@param EnforcePriceLimit 
		Do not allow prices below the limit price
	  */
	@Override
	public void setEnforcePriceLimit (boolean EnforcePriceLimit)
	{
		set_Value (COLUMNNAME_EnforcePriceLimit, Boolean.valueOf(EnforcePriceLimit));
	}

	/** Get Preislimit erzwingen.
		@return Do not allow prices below the limit price
	  */
	@Override
	public boolean isEnforcePriceLimit () 
	{
		Object oo = get_Value(COLUMNNAME_EnforcePriceLimit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isPresentForProduct.
		@param IsPresentForProduct isPresentForProduct	  */
	@Override
	public void setIsPresentForProduct (boolean IsPresentForProduct)
	{
		set_Value (COLUMNNAME_IsPresentForProduct, Boolean.valueOf(IsPresentForProduct));
	}

	/** Get isPresentForProduct.
		@return isPresentForProduct	  */
	@Override
	public boolean isPresentForProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsPresentForProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Netto-Beträge auf Währungspräzision runden.
		@param IsRoundNetAmountToCurrencyPrecision Netto-Beträge auf Währungspräzision runden	  */
	@Override
	public void setIsRoundNetAmountToCurrencyPrecision (boolean IsRoundNetAmountToCurrencyPrecision)
	{
		set_Value (COLUMNNAME_IsRoundNetAmountToCurrencyPrecision, Boolean.valueOf(IsRoundNetAmountToCurrencyPrecision));
	}

	/** Get Netto-Beträge auf Währungspräzision runden.
		@return Netto-Beträge auf Währungspräzision runden	  */
	@Override
	public boolean isRoundNetAmountToCurrencyPrecision () 
	{
		Object oo = get_Value(COLUMNNAME_IsRoundNetAmountToCurrencyPrecision);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufspreisliste.
		@param IsSOPriceList 
		This is a Sales Price List
	  */
	@Override
	public void setIsSOPriceList (boolean IsSOPriceList)
	{
		set_Value (COLUMNNAME_IsSOPriceList, Boolean.valueOf(IsSOPriceList));
	}

	/** Get Verkaufspreisliste.
		@return This is a Sales Price List
	  */
	@Override
	public boolean isSOPriceList () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOPriceList);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preis inklusive Steuern.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	@Override
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Preis inklusive Steuern.
		@return Tax is included in the price 
	  */
	@Override
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Preis Präzision.
		@param PricePrecision 
		Precision (number of decimals) for the Price
	  */
	@Override
	public void setPricePrecision (int PricePrecision)
	{
		set_Value (COLUMNNAME_PricePrecision, Integer.valueOf(PricePrecision));
	}

	/** Get Preis Präzision.
		@return Precision (number of decimals) for the Price
	  */
	@Override
	public int getPricePrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PricePrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}