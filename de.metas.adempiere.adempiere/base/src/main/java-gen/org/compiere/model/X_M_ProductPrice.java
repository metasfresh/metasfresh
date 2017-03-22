/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_ProductPrice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductPrice extends org.compiere.model.PO implements I_M_ProductPrice, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -463752021L;

    /** Standard Constructor */
    public X_M_ProductPrice (Properties ctx, int M_ProductPrice_ID, String trxName)
    {
      super (ctx, M_ProductPrice_ID, trxName);
      /** if (M_ProductPrice_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setC_UOM_ID (0);
			setIsAttributeDependant (false);
// N
			setIsDefault (false);
// N
			setIsSeasonFixedPrice (false);
// N
			setM_PriceList_Version_ID (0);
			setM_Product_ID (0);
			setM_ProductPrice_ID (0);
			setMatchSeqNo (0);
// @SQL=SELECT COALESCE(MAX(MatchSeqNo),0)+10 AS DefaultValue FROM M_ProductPrice WHERE M_PriceList_Version_ID=@M_PriceList_Version_ID@ AND M_Product_ID=@M_Product_ID@
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			setPriceStd (Env.ZERO);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_ProductPrice WHERE M_PriceList_Version_ID=@M_PriceList_Version_ID@
			setUseScalePrice (false);
// N
        } */
    }

    /** Load Constructor */
    public X_M_ProductPrice (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory);
	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Steuerkategorie
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Steuerkategorie
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set isAttributeDependant.
		@param IsAttributeDependant isAttributeDependant	  */
	@Override
	public void setIsAttributeDependant (boolean IsAttributeDependant)
	{
		set_Value (COLUMNNAME_IsAttributeDependant, Boolean.valueOf(IsAttributeDependant));
	}

	/** Get isAttributeDependant.
		@return isAttributeDependant	  */
	@Override
	public boolean isAttributeDependant () 
	{
		Object oo = get_Value(COLUMNNAME_IsAttributeDependant);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Saison Fix Preis.
		@param IsSeasonFixedPrice Saison Fix Preis	  */
	@Override
	public void setIsSeasonFixedPrice (boolean IsSeasonFixedPrice)
	{
		set_Value (COLUMNNAME_IsSeasonFixedPrice, Boolean.valueOf(IsSeasonFixedPrice));
	}

	/** Get Saison Fix Preis.
		@return Saison Fix Preis	  */
	@Override
	public boolean isSeasonFixedPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsSeasonFixedPrice);
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

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
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
	public org.compiere.model.I_M_DiscountSchemaLine getM_DiscountSchemaLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchemaLine_ID, org.compiere.model.I_M_DiscountSchemaLine.class);
	}

	@Override
	public void setM_DiscountSchemaLine(org.compiere.model.I_M_DiscountSchemaLine M_DiscountSchemaLine)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchemaLine_ID, org.compiere.model.I_M_DiscountSchemaLine.class, M_DiscountSchemaLine);
	}

	/** Set Discount Pricelist.
		@param M_DiscountSchemaLine_ID 
		Line of the pricelist trade discount schema
	  */
	@Override
	public void setM_DiscountSchemaLine_ID (int M_DiscountSchemaLine_ID)
	{
		if (M_DiscountSchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, Integer.valueOf(M_DiscountSchemaLine_ID));
	}

	/** Get Discount Pricelist.
		@return Line of the pricelist trade discount schema
	  */
	@Override
	public int getM_DiscountSchemaLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class);
	}

	@Override
	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class, M_PriceList_Version);
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_ID 
		Identifies a unique instance of a Price List
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Identifies a unique instance of a Price List
	  */
	@Override
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
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

	@Override
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice_Base() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_Base_ID, org.compiere.model.I_M_ProductPrice.class);
	}

	@Override
	public void setM_ProductPrice_Base(org.compiere.model.I_M_ProductPrice M_ProductPrice_Base)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_Base_ID, org.compiere.model.I_M_ProductPrice.class, M_ProductPrice_Base);
	}

	/** Set Base product price.
		@param M_ProductPrice_Base_ID Base product price	  */
	@Override
	public void setM_ProductPrice_Base_ID (int M_ProductPrice_Base_ID)
	{
		if (M_ProductPrice_Base_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Base_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Base_ID, Integer.valueOf(M_ProductPrice_Base_ID));
	}

	/** Get Base product price.
		@return Base product price	  */
	@Override
	public int getM_ProductPrice_Base_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_Base_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_ProductPrice_ID.
		@param M_ProductPrice_ID M_ProductPrice_ID	  */
	@Override
	public void setM_ProductPrice_ID (int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, Integer.valueOf(M_ProductPrice_ID));
	}

	/** Get M_ProductPrice_ID.
		@return M_ProductPrice_ID	  */
	@Override
	public int getM_ProductPrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Matching order.
		@param MatchSeqNo Matching order	  */
	@Override
	public void setMatchSeqNo (int MatchSeqNo)
	{
		set_Value (COLUMNNAME_MatchSeqNo, Integer.valueOf(MatchSeqNo));
	}

	/** Get Matching order.
		@return Matching order	  */
	@Override
	public int getMatchSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MatchSeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mindestpreis.
		@param PriceLimit 
		Lowest price for a product
	  */
	@Override
	public void setPriceLimit (java.math.BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Mindestpreis.
		@return Lowest price for a product
	  */
	@Override
	public java.math.BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Auszeichnungspreis.
		@param PriceList 
		Auszeichnungspreis
	  */
	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get Auszeichnungspreis.
		@return Auszeichnungspreis
	  */
	@Override
	public java.math.BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standardpreis.
		@param PriceStd 
		Standard Price
	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standard Price
	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Staffelpreis.
		@param UseScalePrice 
		Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	  */
	@Override
	public void setUseScalePrice (boolean UseScalePrice)
	{
		set_Value (COLUMNNAME_UseScalePrice, Boolean.valueOf(UseScalePrice));
	}

	/** Get Staffelpreis.
		@return Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	  */
	@Override
	public boolean isUseScalePrice () 
	{
		Object oo = get_Value(COLUMNNAME_UseScalePrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}