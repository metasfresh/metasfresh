/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PMM_QtyReport_Event
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_QtyReport_Event extends org.compiere.model.PO implements I_PMM_QtyReport_Event, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1984611341L;

    /** Standard Constructor */
    public X_PMM_QtyReport_Event (Properties ctx, int PMM_QtyReport_Event_ID, String trxName)
    {
      super (ctx, PMM_QtyReport_Event_ID, trxName);
      /** if (PMM_QtyReport_Event_ID == 0)
        {
			setIsError (false);
// N
			setIsPlanning (false);
// N
			setM_AttributeSetInstance_ID (0);
// 0
			setPMM_QtyReport_Event_ID (0);
			setProcessed (false);
// N
			setQtyPromised_Old (Env.ZERO);
// 0
			setQtyPromised_TU (Env.ZERO);
// 0
			setQtyPromised_TU_Old (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_PMM_QtyReport_Event (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
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

	/** Set ContractLine UUID.
		@param ContractLine_UUID ContractLine UUID	  */
	@Override
	public void setContractLine_UUID (java.lang.String ContractLine_UUID)
	{
		set_Value (COLUMNNAME_ContractLine_UUID, ContractLine_UUID);
	}

	/** Get ContractLine UUID.
		@return ContractLine UUID	  */
	@Override
	public java.lang.String getContractLine_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContractLine_UUID);
	}

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_ValueNoCheck (COLUMNNAME_DatePromised, DatePromised);
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

	/** Set Event UUID.
		@param Event_UUID Event UUID	  */
	@Override
	public void setEvent_UUID (java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	/** Get Event UUID.
		@return Event UUID	  */
	@Override
	public java.lang.String getEvent_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Event_UUID);
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Anbauplanung.
		@param IsPlanning Anbauplanung	  */
	@Override
	public void setIsPlanning (boolean IsPlanning)
	{
		set_Value (COLUMNNAME_IsPlanning, Boolean.valueOf(IsPlanning));
	}

	/** Get Anbauplanung.
		@return Anbauplanung	  */
	@Override
	public boolean isPlanning () 
	{
		Object oo = get_Value(COLUMNNAME_IsPlanning);
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
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
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

	/** Set Partner UUID.
		@param Partner_UUID Partner UUID	  */
	@Override
	public void setPartner_UUID (java.lang.String Partner_UUID)
	{
		set_Value (COLUMNNAME_Partner_UUID, Partner_UUID);
	}

	/** Get Partner UUID.
		@return Partner UUID	  */
	@Override
	public java.lang.String getPartner_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Partner_UUID);
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_Product getPMM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class);
	}

	@Override
	public void setPMM_Product(de.metas.procurement.base.model.I_PMM_Product PMM_Product)
	{
		set_ValueFromPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class, PMM_Product);
	}

	/** Set Lieferprodukt.
		@param PMM_Product_ID Lieferprodukt	  */
	@Override
	public void setPMM_Product_ID (int PMM_Product_ID)
	{
		if (PMM_Product_ID < 1) 
			set_Value (COLUMNNAME_PMM_Product_ID, null);
		else 
			set_Value (COLUMNNAME_PMM_Product_ID, Integer.valueOf(PMM_Product_ID));
	}

	/** Get Lieferprodukt.
		@return Lieferprodukt	  */
	@Override
	public int getPMM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_PurchaseCandidate getPMM_PurchaseCandidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PMM_PurchaseCandidate_ID, de.metas.procurement.base.model.I_PMM_PurchaseCandidate.class);
	}

	@Override
	public void setPMM_PurchaseCandidate(de.metas.procurement.base.model.I_PMM_PurchaseCandidate PMM_PurchaseCandidate)
	{
		set_ValueFromPO(COLUMNNAME_PMM_PurchaseCandidate_ID, de.metas.procurement.base.model.I_PMM_PurchaseCandidate.class, PMM_PurchaseCandidate);
	}

	/** Set Bestellkandidat.
		@param PMM_PurchaseCandidate_ID Bestellkandidat	  */
	@Override
	public void setPMM_PurchaseCandidate_ID (int PMM_PurchaseCandidate_ID)
	{
		if (PMM_PurchaseCandidate_ID < 1) 
			set_Value (COLUMNNAME_PMM_PurchaseCandidate_ID, null);
		else 
			set_Value (COLUMNNAME_PMM_PurchaseCandidate_ID, Integer.valueOf(PMM_PurchaseCandidate_ID));
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

	/** Set Lieferplanungsdatensatz.
		@param PMM_QtyReport_Event_ID Lieferplanungsdatensatz	  */
	@Override
	public void setPMM_QtyReport_Event_ID (int PMM_QtyReport_Event_ID)
	{
		if (PMM_QtyReport_Event_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_QtyReport_Event_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_QtyReport_Event_ID, Integer.valueOf(PMM_QtyReport_Event_ID));
	}

	/** Get Lieferplanungsdatensatz.
		@return Lieferplanungsdatensatz	  */
	@Override
	public int getPMM_QtyReport_Event_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_QtyReport_Event_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Preis.
		@param Price 
		Preis
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_ValueNoCheck (COLUMNNAME_Price, Price);
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

	/** Set Produkt UUID.
		@param Product_UUID Produkt UUID	  */
	@Override
	public void setProduct_UUID (java.lang.String Product_UUID)
	{
		set_Value (COLUMNNAME_Product_UUID, Product_UUID);
	}

	/** Get Produkt UUID.
		@return Produkt UUID	  */
	@Override
	public java.lang.String getProduct_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Product_UUID);
	}

	/** Set Zusagbar.
		@param QtyPromised Zusagbar	  */
	@Override
	public void setQtyPromised (java.math.BigDecimal QtyPromised)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPromised, QtyPromised);
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

	/** Set Old Zusagbar.
		@param QtyPromised_Old Old Zusagbar	  */
	@Override
	public void setQtyPromised_Old (java.math.BigDecimal QtyPromised_Old)
	{
		set_Value (COLUMNNAME_QtyPromised_Old, QtyPromised_Old);
	}

	/** Get Old Zusagbar.
		@return Old Zusagbar	  */
	@Override
	public java.math.BigDecimal getQtyPromised_Old () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_Old);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar (TU).
		@param QtyPromised_TU Zusagbar (TU)	  */
	@Override
	public void setQtyPromised_TU (java.math.BigDecimal QtyPromised_TU)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPromised_TU, QtyPromised_TU);
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

	/** Set Old Zusagbar (TU).
		@param QtyPromised_TU_Old Old Zusagbar (TU)	  */
	@Override
	public void setQtyPromised_TU_Old (java.math.BigDecimal QtyPromised_TU_Old)
	{
		set_Value (COLUMNNAME_QtyPromised_TU_Old, QtyPromised_TU_Old);
	}

	/** Get Old Zusagbar (TU).
		@return Old Zusagbar (TU)	  */
	@Override
	public java.math.BigDecimal getQtyPromised_TU_Old () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_TU_Old);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}