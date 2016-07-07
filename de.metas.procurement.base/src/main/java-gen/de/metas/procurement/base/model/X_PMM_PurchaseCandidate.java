/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PMM_PurchaseCandidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_PurchaseCandidate extends org.compiere.model.PO implements I_PMM_PurchaseCandidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1305797989L;

    /** Standard Constructor */
    public X_PMM_PurchaseCandidate (Properties ctx, int PMM_PurchaseCandidate_ID, String trxName)
    {
      super (ctx, PMM_PurchaseCandidate_ID, trxName);
      /** if (PMM_PurchaseCandidate_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_UOM_ID (0);
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setM_HU_PI_Item_Product_ID (0);
			setM_PricingSystem_ID (0);
			setM_Product_ID (0);
			setPMM_PurchaseCandidate_ID (0);
			setPrice (Env.ZERO);
			setProcessed (false);
// N
			setQtyOrdered (Env.ZERO);
// 0
			setQtyOrdered_TU (Env.ZERO);
// 0
			setQtyPromised (Env.ZERO);
			setQtyPromised_TU (Env.ZERO);
// 0
			setQtyToOrder (Env.ZERO);
// 0
			setQtyToOrder_TU (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_PMM_PurchaseCandidate (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.flatrate.model.I_C_Flatrate_DataEntry.class);
	}

	@Override
	public void setC_Flatrate_DataEntry(de.metas.flatrate.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.flatrate.model.I_C_Flatrate_DataEntry.class, C_Flatrate_DataEntry);
	}

	/** Set Abrechnungssatz.
		@param C_Flatrate_DataEntry_ID Abrechnungssatz	  */
	@Override
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_DataEntry_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_DataEntry_ID, Integer.valueOf(C_Flatrate_DataEntry_ID));
	}

	/** Get Abrechnungssatz.
		@return Abrechnungssatz	  */
	@Override
	public int getC_Flatrate_DataEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_DataEntry_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_QtyReport_Event getLast_QtyReport_Event() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Last_QtyReport_Event_ID, de.metas.procurement.base.model.I_PMM_QtyReport_Event.class);
	}

	@Override
	public void setLast_QtyReport_Event(de.metas.procurement.base.model.I_PMM_QtyReport_Event Last_QtyReport_Event)
	{
		set_ValueFromPO(COLUMNNAME_Last_QtyReport_Event_ID, de.metas.procurement.base.model.I_PMM_QtyReport_Event.class, Last_QtyReport_Event);
	}

	/** Set Last PMM Qty Report Event.
		@param Last_QtyReport_Event_ID Last PMM Qty Report Event	  */
	@Override
	public void setLast_QtyReport_Event_ID (int Last_QtyReport_Event_ID)
	{
		if (Last_QtyReport_Event_ID < 1) 
			set_Value (COLUMNNAME_Last_QtyReport_Event_ID, null);
		else 
			set_Value (COLUMNNAME_Last_QtyReport_Event_ID, Integer.valueOf(Last_QtyReport_Event_ID));
	}

	/** Get Last PMM Qty Report Event.
		@return Last PMM Qty Report Event	  */
	@Override
	public int getLast_QtyReport_Event_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Last_QtyReport_Event_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
	}

	@Override
	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class, M_HU_PI_Item_Product);
	}

	/** Set Packvorschrift-Produkt Zuordnung.
		@param M_HU_PI_Item_Product_ID Packvorschrift-Produkt Zuordnung	  */
	@Override
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, Integer.valueOf(M_HU_PI_Item_Product_ID));
	}

	/** Get Packvorschrift-Produkt Zuordnung.
		@return Packvorschrift-Produkt Zuordnung	  */
	@Override
	public int getM_HU_PI_Item_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Item_Product_Override_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
	}

	@Override
	public void setM_HU_PI_Item_Product_Override(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Override)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Item_Product_Override_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class, M_HU_PI_Item_Product_Override);
	}

	/** Set Packvorschrift-Produkt Zuordnung abw..
		@param M_HU_PI_Item_Product_Override_ID Packvorschrift-Produkt Zuordnung abw.	  */
	@Override
	public void setM_HU_PI_Item_Product_Override_ID (int M_HU_PI_Item_Product_Override_ID)
	{
		if (M_HU_PI_Item_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, Integer.valueOf(M_HU_PI_Item_Product_Override_ID));
	}

	/** Get Packvorschrift-Produkt Zuordnung abw..
		@return Packvorschrift-Produkt Zuordnung abw.	  */
	@Override
	public int getM_HU_PI_Item_Product_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Bezeichnung der Preisliste
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
		@return Bezeichnung der Preisliste
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
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

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set V.
		@param PMM_ContractedPriceOrQty 
		Vertrag
	  */
	@Override
	public void setPMM_ContractedPriceOrQty (boolean PMM_ContractedPriceOrQty)
	{
		throw new IllegalArgumentException ("PMM_ContractedPriceOrQty is virtual column");	}

	/** Get V.
		@return Vertrag
	  */
	@Override
	public boolean isPMM_ContractedPriceOrQty () 
	{
		Object oo = get_Value(COLUMNNAME_PMM_ContractedPriceOrQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bestellkandidat.
		@param PMM_PurchaseCandidate_ID Bestellkandidat	  */
	@Override
	public void setPMM_PurchaseCandidate_ID (int PMM_PurchaseCandidate_ID)
	{
		if (PMM_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_ID, Integer.valueOf(PMM_PurchaseCandidate_ID));
	}

	/** Get Bestellkandidat.
		@return Bestellkandidat	  */
	@Override
	public int getPMM_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PMM_Trend_InTwoWeeks AD_Reference_ID=540648
	 * Reference name: PMM_Trend
	 */
	public static final int PMM_TREND_INTWOWEEKS_AD_Reference_ID=540648;
	/** Up = U */
	public static final String PMM_TREND_INTWOWEEKS_Up = "U";
	/** Down = D */
	public static final String PMM_TREND_INTWOWEEKS_Down = "D";
	/** Same = E */
	public static final String PMM_TREND_INTWOWEEKS_Same = "E";
	/** Zero = Z */
	public static final String PMM_TREND_INTWOWEEKS_Zero = "Z";
	/** Set Trend (in zwei Wochen).
		@param PMM_Trend_InTwoWeeks Trend (in zwei Wochen)	  */
	@Override
	public void setPMM_Trend_InTwoWeeks (java.lang.String PMM_Trend_InTwoWeeks)
	{

		throw new IllegalArgumentException ("PMM_Trend_InTwoWeeks is virtual column");	}

	/** Get Trend (in zwei Wochen).
		@return Trend (in zwei Wochen)	  */
	@Override
	public java.lang.String getPMM_Trend_InTwoWeeks () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PMM_Trend_InTwoWeeks);
	}

	/** Set Preis.
		@param Price 
		Preis
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Preis
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preis abweichend.
		@param Price_Override Preis abweichend	  */
	@Override
	public void setPrice_Override (java.math.BigDecimal Price_Override)
	{
		set_Value (COLUMNNAME_Price_Override, Price_Override);
	}

	/** Get Preis abweichend.
		@return Preis abweichend	  */
	@Override
	public java.math.BigDecimal getPrice_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price_Override);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		throw new IllegalArgumentException ("Processing is virtual column");	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Bestellte Menge (TU).
		@param QtyOrdered_TU 
		Bestellte Menge (TU)
	  */
	@Override
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU)
	{
		set_Value (COLUMNNAME_QtyOrdered_TU, QtyOrdered_TU);
	}

	/** Get Bestellte Menge (TU).
		@return Bestellte Menge (TU)
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge TU (next week).
		@param QtyOrdered_TU_NextWeek Bestellte Menge TU (next week)	  */
	@Override
	public void setQtyOrdered_TU_NextWeek (java.math.BigDecimal QtyOrdered_TU_NextWeek)
	{
		throw new IllegalArgumentException ("QtyOrdered_TU_NextWeek is virtual column");	}

	/** Get Bestellte Menge TU (next week).
		@return Bestellte Menge TU (next week)	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU_NextWeek () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU_NextWeek);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge TU (this week).
		@param QtyOrdered_TU_ThisWeek Bestellte Menge TU (this week)	  */
	@Override
	public void setQtyOrdered_TU_ThisWeek (java.math.BigDecimal QtyOrdered_TU_ThisWeek)
	{
		throw new IllegalArgumentException ("QtyOrdered_TU_ThisWeek is virtual column");	}

	/** Get Bestellte Menge TU (this week).
		@return Bestellte Menge TU (this week)	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU_ThisWeek () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU_ThisWeek);
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

	/** Set Zusagbar (TU).
		@param QtyPromised_TU Zusagbar (TU)	  */
	@Override
	public void setQtyPromised_TU (java.math.BigDecimal QtyPromised_TU)
	{
		set_Value (COLUMNNAME_QtyPromised_TU, QtyPromised_TU);
	}

	/** Get Zusagbar (TU).
		@return Zusagbar (TU)	  */
	@Override
	public java.math.BigDecimal getQtyPromised_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar TU (nächste Woche).
		@param QtyPromised_TU_NextWeek Zusagbar TU (nächste Woche)	  */
	@Override
	public void setQtyPromised_TU_NextWeek (java.math.BigDecimal QtyPromised_TU_NextWeek)
	{
		throw new IllegalArgumentException ("QtyPromised_TU_NextWeek is virtual column");	}

	/** Get Zusagbar TU (nächste Woche).
		@return Zusagbar TU (nächste Woche)	  */
	@Override
	public java.math.BigDecimal getQtyPromised_TU_NextWeek () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_TU_NextWeek);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar TU (diese Woche).
		@param QtyPromised_TU_ThisWeek Zusagbar TU (diese Woche)	  */
	@Override
	public void setQtyPromised_TU_ThisWeek (java.math.BigDecimal QtyPromised_TU_ThisWeek)
	{
		throw new IllegalArgumentException ("QtyPromised_TU_ThisWeek is virtual column");	}

	/** Get Zusagbar TU (diese Woche).
		@return Zusagbar TU (diese Woche)	  */
	@Override
	public java.math.BigDecimal getQtyPromised_TU_ThisWeek () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_TU_ThisWeek);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity to Order.
		@param QtyToOrder Quantity to Order	  */
	@Override
	public void setQtyToOrder (java.math.BigDecimal QtyToOrder)
	{
		set_Value (COLUMNNAME_QtyToOrder, QtyToOrder);
	}

	/** Get Quantity to Order.
		@return Quantity to Order	  */
	@Override
	public java.math.BigDecimal getQtyToOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToOrder);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity to Order (TU).
		@param QtyToOrder_TU Quantity to Order (TU)	  */
	@Override
	public void setQtyToOrder_TU (java.math.BigDecimal QtyToOrder_TU)
	{
		set_Value (COLUMNNAME_QtyToOrder_TU, QtyToOrder_TU);
	}

	/** Get Quantity to Order (TU).
		@return Quantity to Order (TU)	  */
	@Override
	public java.math.BigDecimal getQtyToOrder_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToOrder_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}