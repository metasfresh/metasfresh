/** Generated Model - DO NOT CHANGE */
package de.metas.pricing.attributebased;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ProductPrice_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductPrice_Attribute extends org.compiere.model.PO implements I_M_ProductPrice_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1464992206L;

    /** Standard Constructor */
    public X_M_ProductPrice_Attribute (Properties ctx, int M_ProductPrice_Attribute_ID, String trxName)
    {
      super (ctx, M_ProductPrice_Attribute_ID, trxName);
      /** if (M_ProductPrice_Attribute_ID == 0)
        {
			setIsDefault (false); // N
			setM_ProductPrice_Attribute_ID (0);
			setM_ProductPrice_ID (0);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_ProductPrice_Attribute WHERE M_ProductPrice_ID=@M_ProductPrice_ID@
        } */
    }

    /** Load Constructor */
    public X_M_ProductPrice_Attribute (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Attribute_Line_Included_Tab.
		@param Attribute_Line_Included_Tab Attribute_Line_Included_Tab	  */
	@Override
	public void setAttribute_Line_Included_Tab (java.lang.String Attribute_Line_Included_Tab)
	{
		set_Value (COLUMNNAME_Attribute_Line_Included_Tab, Attribute_Line_Included_Tab);
	}

	/** Get Attribute_Line_Included_Tab.
		@return Attribute_Line_Included_Tab	  */
	@Override
	public java.lang.String getAttribute_Line_Included_Tab () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Attribute_Line_Included_Tab);
	}

	/** Set Merkmale.
		@param AttributeValues Merkmale	  */
	@Override
	public void setAttributeValues (java.lang.String AttributeValues)
	{
		throw new IllegalArgumentException ("AttributeValues is virtual column");	}

	/** Get Merkmale.
		@return Merkmale	  */
	@Override
	public java.lang.String getAttributeValues () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValues);
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

	/** Set Unbestimmte Kapazität.
		@param IsInfiniteCapacity Unbestimmte Kapazität	  */
	@Override
	public void setIsInfiniteCapacity (boolean IsInfiniteCapacity)
	{
		throw new IllegalArgumentException ("IsInfiniteCapacity is virtual column");	}

	/** Get Unbestimmte Kapazität.
		@return Unbestimmte Kapazität	  */
	@Override
	public boolean isInfiniteCapacity () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfiniteCapacity);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Attribute price.
		@param M_ProductPrice_Attribute_ID Attribute price	  */
	@Override
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID)
	{
		if (M_ProductPrice_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, Integer.valueOf(M_ProductPrice_Attribute_ID));
	}

	/** Get Attribute price.
		@return Attribute price	  */
	@Override
	public int getM_ProductPrice_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class);
	}

	@Override
	public void setM_ProductPrice(org.compiere.model.I_M_ProductPrice M_ProductPrice)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class, M_ProductPrice);
	}

	/** Set Produkt-Preis.
		@param M_ProductPrice_ID Produkt-Preis	  */
	@Override
	public void setM_ProductPrice_ID (int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, Integer.valueOf(M_ProductPrice_ID));
	}

	/** Get Produkt-Preis.
		@return Produkt-Preis	  */
	@Override
	public int getM_ProductPrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mindestpreis.
		@param PriceLimit 
		Unterster Preis für Kostendeckung
	  */
	@Override
	public void setPriceLimit (java.math.BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Mindestpreis.
		@return Unterster Preis für Kostendeckung
	  */
	@Override
	public java.math.BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Standardpreis.
		@param PriceStd Standardpreis	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standardpreis	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
		if (bd == null)
			 return BigDecimal.ZERO;
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
}