/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Cockpit
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Cockpit extends org.compiere.model.PO implements I_MD_Cockpit, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 236129388L;

    /** Standard Constructor */
    public X_MD_Cockpit (Properties ctx, int MD_Cockpit_ID, String trxName)
    {
      super (ctx, MD_Cockpit_ID, trxName);
      /** if (MD_Cockpit_ID == 0)
        {
			setDateGeneral (new Timestamp( System.currentTimeMillis() ));
			setM_Product_ID (0);
			setMD_Cockpit_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Cockpit (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AttributesKey (technical).
		@param AttributesKey AttributesKey (technical)	  */
	@Override
	public void setAttributesKey (java.lang.String AttributesKey)
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	/** Get AttributesKey (technical).
		@return AttributesKey (technical)	  */
	@Override
	public java.lang.String getAttributesKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributesKey);
	}

	/** Set Datum.
		@param DateGeneral Datum	  */
	@Override
	public void setDateGeneral (java.sql.Timestamp DateGeneral)
	{
		set_Value (COLUMNNAME_DateGeneral, DateGeneral);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getDateGeneral () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateGeneral);
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

	/** Set Materialcockpit.
		@param MD_Cockpit_ID Materialcockpit	  */
	@Override
	public void setMD_Cockpit_ID (int MD_Cockpit_ID)
	{
		if (MD_Cockpit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, Integer.valueOf(MD_Cockpit_ID));
	}

	/** Get Materialcockpit.
		@return Materialcockpit	  */
	@Override
	public int getMD_Cockpit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Cockpit_ID);
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
		set_Value (COLUMNNAME_PMM_QtyPromised_OnDate, PMM_QtyPromised_OnDate);
	}

	/** Get Zusage Lieferant.
		@return Vom Lieferanten per Webapplikation zugesagte Menge
	  */
	@Override
	public java.math.BigDecimal getPMM_QtyPromised_OnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PMM_QtyPromised_OnDate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	/** Set Produktionsstätte.
		@param PP_Plant_ID Produktionsstätte	  */
	@Override
	public void setPP_Plant_ID (int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, Integer.valueOf(PP_Plant_ID));
	}

	/** Get Produktionsstätte.
		@return Produktionsstätte	  */
	@Override
	public int getPP_Plant_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Plant_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Produktschlüssel.
		@param ProductValue 
		Schlüssel des Produktes
	  */
	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Produktschlüssel.
		@return Schlüssel des Produktes
	  */
	@Override
	public java.lang.String getProductValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** Set Zusagbare Menge.
		@param QtyAvailableToPromise Zusagbare Menge	  */
	@Override
	public void setQtyAvailableToPromise (java.math.BigDecimal QtyAvailableToPromise)
	{
		set_Value (COLUMNNAME_QtyAvailableToPromise, QtyAvailableToPromise);
	}

	/** Get Zusagbare Menge.
		@return Zusagbare Menge	  */
	@Override
	public java.math.BigDecimal getQtyAvailableToPromise () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailableToPromise);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Materialentnahme.
		@param QtyMaterialentnahme Materialentnahme	  */
	@Override
	public void setQtyMaterialentnahme (java.math.BigDecimal QtyMaterialentnahme)
	{
		set_Value (COLUMNNAME_QtyMaterialentnahme, QtyMaterialentnahme);
	}

	/** Get Materialentnahme.
		@return Materialentnahme	  */
	@Override
	public java.math.BigDecimal getQtyMaterialentnahme () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMaterialentnahme);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zählmenge.
		@param QtyOnHandCount 
		Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	  */
	@Override
	public void setQtyOnHandCount (java.math.BigDecimal QtyOnHandCount)
	{
		set_Value (COLUMNNAME_QtyOnHandCount, QtyOnHandCount);
	}

	/** Get Zählmenge.
		@return Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	  */
	@Override
	public java.math.BigDecimal getQtyOnHandCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHandCount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Schätzbestand.
		@param QtyOnHandEstimate 
		Kombination aus der Zählmenge des jeweiligen Tages mit aktuellen Warenein- und Ausgängen sowie Materialentnahmen
	  */
	@Override
	public void setQtyOnHandEstimate (java.math.BigDecimal QtyOnHandEstimate)
	{
		set_Value (COLUMNNAME_QtyOnHandEstimate, QtyOnHandEstimate);
	}

	/** Get Schätzbestand.
		@return Kombination aus der Zählmenge des jeweiligen Tages mit aktuellen Warenein- und Ausgängen sowie Materialentnahmen
	  */
	@Override
	public java.math.BigDecimal getQtyOnHandEstimate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHandEstimate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge für Produktion.
		@param QtyRequiredForProduction Menge für Produktion	  */
	@Override
	public void setQtyRequiredForProduction (java.math.BigDecimal QtyRequiredForProduction)
	{
		set_Value (COLUMNNAME_QtyRequiredForProduction, QtyRequiredForProduction);
	}

	/** Get Menge für Produktion.
		@return Menge für Produktion	  */
	@Override
	public java.math.BigDecimal getQtyRequiredForProduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiredForProduction);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellt.
		@param QtyReserved_Purchase Bestellt	  */
	@Override
	public void setQtyReserved_Purchase (java.math.BigDecimal QtyReserved_Purchase)
	{
		set_Value (COLUMNNAME_QtyReserved_Purchase, QtyReserved_Purchase);
	}

	/** Get Bestellt.
		@return Bestellt	  */
	@Override
	public java.math.BigDecimal getQtyReserved_Purchase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved_Purchase);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Beauftragt.
		@param QtyReserved_Sale Beauftragt	  */
	@Override
	public void setQtyReserved_Sale (java.math.BigDecimal QtyReserved_Sale)
	{
		set_Value (COLUMNNAME_QtyReserved_Sale, QtyReserved_Sale);
	}

	/** Get Beauftragt.
		@return Beauftragt	  */
	@Override
	public java.math.BigDecimal getQtyReserved_Sale () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved_Sale);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestandsänderung.
		@param QtyStockChange Bestandsänderung	  */
	@Override
	public void setQtyStockChange (java.math.BigDecimal QtyStockChange)
	{
		set_Value (COLUMNNAME_QtyStockChange, QtyStockChange);
	}

	/** Get Bestandsänderung.
		@return Bestandsänderung	  */
	@Override
	public java.math.BigDecimal getQtyStockChange () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyStockChange);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}