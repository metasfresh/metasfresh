/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for RV_Fresh_Pricelist
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Fresh_Pricelist extends org.compiere.model.PO implements I_RV_Fresh_Pricelist, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -842664207L;

    /** Standard Constructor */
    public X_RV_Fresh_Pricelist (Properties ctx, int RV_Fresh_Pricelist_ID, String trxName)
    {
      super (ctx, RV_Fresh_Pricelist_ID, trxName);
      /** if (RV_Fresh_Pricelist_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_RV_Fresh_Pricelist (Properties ctx, ResultSet rs, String trxName)
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
		set_Value (COLUMNNAME_attributes, attributes);
	}

	/** Get attributes.
		@return attributes	  */
	@Override
	public java.lang.String getattributes () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_attributes);
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set CU:TU Zuordnung.
		@param ItemProductName CU:TU Zuordnung	  */
	@Override
	public void setItemProductName (java.lang.String ItemProductName)
	{
		set_Value (COLUMNNAME_ItemProductName, ItemProductName);
	}

	/** Get CU:TU Zuordnung.
		@return CU:TU Zuordnung	  */
	@Override
	public java.lang.String getItemProductName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ItemProductName);
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

	/** Set packingmaterialname.
		@param packingmaterialname packingmaterialname	  */
	@Override
	public void setpackingmaterialname (java.lang.String packingmaterialname)
	{
		set_Value (COLUMNNAME_packingmaterialname, packingmaterialname);
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
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	/** Get Produktname.
		@return Name des Produktes
	  */
	@Override
	public java.lang.String getProductName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductName);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}