/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_PriceList_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_PriceList_V extends org.compiere.model.PO implements I_M_PriceList_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 47105568L;

    /** Standard Constructor */
    public X_M_PriceList_V (Properties ctx, int M_PriceList_V_ID, String trxName)
    {
      super (ctx, M_PriceList_V_ID, trxName);
      /** if (M_PriceList_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_PriceList_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set attributes.
		@param attributes attributes	  */
	@Override
	public void setattributes (java.lang.String attributes)
	{
		set_ValueNoCheck (COLUMNNAME_attributes, attributes);
	}

	/** Get attributes.
		@return attributes	  */
	@Override
	public java.lang.String getattributes () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_attributes);
	}

	/** Set Saison Fix Preis.
		@param IsSeasonFixedPrice Saison Fix Preis	  */
	@Override
	public void setIsSeasonFixedPrice (boolean IsSeasonFixedPrice)
	{
		set_ValueNoCheck (COLUMNNAME_IsSeasonFixedPrice, Boolean.valueOf(IsSeasonFixedPrice));
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
		Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Bezeichnet eine einzelne Version der Preisliste
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
		Kategorie eines Produktes
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

	/** Set packingmaterialname.
		@param packingmaterialname packingmaterialname	  */
	@Override
	public void setpackingmaterialname (java.lang.String packingmaterialname)
	{
		set_ValueNoCheck (COLUMNNAME_packingmaterialname, packingmaterialname);
	}

	/** Get packingmaterialname.
		@return packingmaterialname	  */
	@Override
	public java.lang.String getpackingmaterialname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_packingmaterialname);
	}

	/** Set Standardpreis.
		@param PriceStd 
		Standardpreis
	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_ValueNoCheck (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standardpreis
	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Produktname.
		@param ProductName 
		Name des Produktes
	  */
	@Override
	public void setProductName (java.lang.String ProductName)
	{
		set_ValueNoCheck (COLUMNNAME_ProductName, ProductName);
	}

	/** Get Produktname.
		@return Name des Produktes
	  */
	@Override
	public java.lang.String getProductName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductName);
	}

	/** Set Produktschlüssel.
		@param ProductValue 
		Schlüssel des Produktes
	  */
	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_ValueNoCheck (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Produktschlüssel.
		@return Schlüssel des Produktes
	  */
	@Override
	public java.lang.String getProductValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (java.lang.String SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, SeqNo);
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public java.lang.String getSeqNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SeqNo);
	}
}