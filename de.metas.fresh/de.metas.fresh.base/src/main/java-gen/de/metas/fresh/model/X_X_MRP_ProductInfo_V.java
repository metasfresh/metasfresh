/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for X_MRP_ProductInfo_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_X_MRP_ProductInfo_V extends org.compiere.model.PO implements I_X_MRP_ProductInfo_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 592773067L;

    /** Standard Constructor */
    public X_X_MRP_ProductInfo_V (Properties ctx, int X_MRP_ProductInfo_V_ID, String trxName)
    {
      super (ctx, X_MRP_ProductInfo_V_ID, trxName);
      /** if (X_MRP_ProductInfo_V_ID == 0)
        {
			setFresh_QtyMRP (Env.ZERO);
// 0
			setFresh_QtyOnHand_OnDate (Env.ZERO);
// 0
			setFresh_QtyOnHand_OnDate_Ind9 (Env.ZERO);
// 0
			setFresh_QtyOnHand_OnDate_Stö2 (Env.ZERO);
// 0
			setFresh_QtyPromised (Env.ZERO);
// 0
			setFresh_QtyPromised_OnDate (Env.ZERO);
// 0
			setQtyAvailable (Env.ZERO);
// 0
			setQtyMaterialentnahme (Env.ZERO);
// 0
			setQtyOnHand (Env.ZERO);
// 0
			setQtyOrdered (Env.ZERO);
// 0
			setQtyOrdered_OnDate (Env.ZERO);
// 0
			setQtyPromised (Env.ZERO);
// 0
			setQtyReserved (Env.ZERO);
// 0
			setQtyReserved_OnDate (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_X_MRP_ProductInfo_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Datum.
		@param DateGeneral Datum	  */
	@Override
	public void setDateGeneral (java.sql.Timestamp DateGeneral)
	{
		set_ValueNoCheck (COLUMNNAME_DateGeneral, DateGeneral);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getDateGeneral () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateGeneral);
	}

	/** Set MRP Menge.
		@param Fresh_QtyMRP MRP Menge	  */
	@Override
	public void setFresh_QtyMRP (java.math.BigDecimal Fresh_QtyMRP)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyMRP, Fresh_QtyMRP);
	}

	/** Get MRP Menge.
		@return MRP Menge	  */
	@Override
	public java.math.BigDecimal getFresh_QtyMRP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyMRP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zählbestand.
		@param Fresh_QtyOnHand_OnDate Zählbestand	  */
	@Override
	public void setFresh_QtyOnHand_OnDate (java.math.BigDecimal Fresh_QtyOnHand_OnDate)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_OnDate, Fresh_QtyOnHand_OnDate);
	}

	/** Get Zählbestand.
		@return Zählbestand	  */
	@Override
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyOnHand_OnDate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ind9.
		@param Fresh_QtyOnHand_OnDate_Ind9 Ind9	  */
	@Override
	public void setFresh_QtyOnHand_OnDate_Ind9 (java.math.BigDecimal Fresh_QtyOnHand_OnDate_Ind9)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_OnDate_Ind9, Fresh_QtyOnHand_OnDate_Ind9);
	}

	/** Get Ind9.
		@return Ind9	  */
	@Override
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate_Ind9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyOnHand_OnDate_Ind9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Stö2.
		@param Fresh_QtyOnHand_OnDate_Stö2 Stö2	  */
	@Override
	public void setFresh_QtyOnHand_OnDate_Stö2 (java.math.BigDecimal Fresh_QtyOnHand_OnDate_Stö2)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_OnDate_Stö2, Fresh_QtyOnHand_OnDate_Stö2);
	}

	/** Get Stö2.
		@return Stö2	  */
	@Override
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate_Stö2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyOnHand_OnDate_Stö2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar Zählbestand.
		@param Fresh_QtyPromised Zusagbar Zählbestand	  */
	@Override
	public void setFresh_QtyPromised (java.math.BigDecimal Fresh_QtyPromised)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyPromised, Fresh_QtyPromised);
	}

	/** Get Zusagbar Zählbestand.
		@return Zusagbar Zählbestand	  */
	@Override
	public java.math.BigDecimal getFresh_QtyPromised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyPromised);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar Tag.
		@param Fresh_QtyPromised_OnDate Zusagbar Tag	  */
	@Override
	public void setFresh_QtyPromised_OnDate (java.math.BigDecimal Fresh_QtyPromised_OnDate)
	{
		set_ValueNoCheck (COLUMNNAME_Fresh_QtyPromised_OnDate, Fresh_QtyPromised_OnDate);
	}

	/** Get Zusagbar Tag.
		@return Zusagbar Tag	  */
	@Override
	public java.math.BigDecimal getFresh_QtyPromised_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fresh_QtyPromised_OnDate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Eingekauft.
		@param IsPurchased 
		Die Organisation kauft dieses Produkt ein
	  */
	@Override
	public void setIsPurchased (boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, Boolean.valueOf(IsPurchased));
	}

	/** Get Eingekauft.
		@return Die Organisation kauft dieses Produkt ein
	  */
	@Override
	public boolean isPurchased () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkauft.
		@param IsSold 
		Die Organisation verkauft dieses Produkt
	  */
	@Override
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Verkauft.
		@return Die Organisation verkauft dieses Produkt
	  */
	@Override
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
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

	/** Set Zusage Lieferant.
		@param PMM_QtyPromised_OnDate 
		Vom Lieferanten per Webapplikation zugesagte Menge
	  */
	@Override
	public void setPMM_QtyPromised_OnDate (java.math.BigDecimal PMM_QtyPromised_OnDate)
	{
		set_ValueNoCheck (COLUMNNAME_PMM_QtyPromised_OnDate, PMM_QtyPromised_OnDate);
	}

	/** Get Zusage Lieferant.
		@return Vom Lieferanten per Webapplikation zugesagte Menge
	  */
	@Override
	public java.math.BigDecimal getPMM_QtyPromised_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PMM_QtyPromised_OnDate);
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

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	@Override
	public void setQtyAvailable (java.math.BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	@Override
	public java.math.BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Materialentnahme.
		@param QtyMaterialentnahme Materialentnahme	  */
	@Override
	public void setQtyMaterialentnahme (java.math.BigDecimal QtyMaterialentnahme)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMaterialentnahme, QtyMaterialentnahme);
	}

	/** Get Materialentnahme.
		@return Materialentnahme	  */
	@Override
	public java.math.BigDecimal getQtyMaterialentnahme () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMaterialentnahme);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestand.
		@param QtyOnHand 
		Bestand
	  */
	@Override
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get Bestand.
		@return Bestand
	  */
	@Override
	public java.math.BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Bestellte Menge
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge Tag.
		@param QtyOrdered_OnDate Bestellte Menge Tag	  */
	@Override
	public void setQtyOrdered_OnDate (java.math.BigDecimal QtyOrdered_OnDate)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered_OnDate, QtyOrdered_OnDate);
	}

	/** Get Bestellte Menge Tag.
		@return Bestellte Menge Tag	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_OnDate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar.
		@param QtyPromised Zusagbar	  */
	@Override
	public void setQtyPromised (java.math.BigDecimal QtyPromised)
	{
		set_Value (COLUMNNAME_QtyPromised, QtyPromised);
	}

	/** Get Zusagbar.
		@return Zusagbar	  */
	@Override
	public java.math.BigDecimal getQtyPromised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reservierte Menge.
		@param QtyReserved 
		Reservierte Menge
	  */
	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reservierte Menge.
		@return Reservierte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reservierte Menge Tag.
		@param QtyReserved_OnDate Reservierte Menge Tag	  */
	@Override
	public void setQtyReserved_OnDate (java.math.BigDecimal QtyReserved_OnDate)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved_OnDate, QtyReserved_OnDate);
	}

	/** Get Reservierte Menge Tag.
		@return Reservierte Menge Tag	  */
	@Override
	public java.math.BigDecimal getQtyReserved_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved_OnDate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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