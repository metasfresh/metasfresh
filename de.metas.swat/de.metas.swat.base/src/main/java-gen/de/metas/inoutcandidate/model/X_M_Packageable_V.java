/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Packageable_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Packageable_V extends org.compiere.model.PO implements I_M_Packageable_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 420303175L;

    /** Standard Constructor */
    public X_M_Packageable_V (Properties ctx, int M_Packageable_V_ID, String trxName)
    {
      super (ctx, M_Packageable_V_ID, trxName);
      /** if (M_Packageable_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_Packageable_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Anschrift-Text abw..
		@param BPartnerAddress_Override Anschrift-Text abw.	  */
	@Override
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	/** Get Anschrift-Text abw..
		@return Anschrift-Text abw.	  */
	@Override
	public java.lang.String getBPartnerAddress_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress_Override);
	}

	/** Set BPartner location name.
		@param BPartnerLocationName BPartner location name	  */
	@Override
	public void setBPartnerLocationName (java.lang.String BPartnerLocationName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerLocationName, BPartnerLocationName);
	}

	/** Get BPartner location name.
		@return BPartner location name	  */
	@Override
	public java.lang.String getBPartnerLocationName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerLocationName);
	}

	/** Set BPartner name.
		@param BPartnerName BPartner name	  */
	@Override
	public void setBPartnerName (java.lang.String BPartnerName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerName, BPartnerName);
	}

	/** Get BPartner name.
		@return BPartner name	  */
	@Override
	public java.lang.String getBPartnerName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerName);
	}

	/** Set Geschäftspartner-Schlüssel.
		@param BPartnerValue 
		Key of the Business Partner
	  */
	@Override
	public void setBPartnerValue (java.lang.String BPartnerValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return Key of the Business Partner
	  */
	@Override
	public java.lang.String getBPartnerValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerValue);
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
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

	/** Set Auftragsdatum.
		@param DateOrdered 
		Datum des Auftrags
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Datum des Auftrags
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
	}

	/** 
	 * DocSubType AD_Reference_ID=148
	 * Reference name: C_DocType SubType
	 */
	public static final int DOCSUBTYPE_AD_Reference_ID=148;
	/** OnCreditOrder = WI */
	public static final String DOCSUBTYPE_OnCreditOrder = "WI";
	/** POSOrder = WR */
	public static final String DOCSUBTYPE_POSOrder = "WR";
	/** WarehouseOrder = WP */
	public static final String DOCSUBTYPE_WarehouseOrder = "WP";
	/** StandardOrder = SO */
	public static final String DOCSUBTYPE_StandardOrder = "SO";
	/** Proposal = ON */
	public static final String DOCSUBTYPE_Proposal = "ON";
	/** Quotation = OB */
	public static final String DOCSUBTYPE_Quotation = "OB";
	/** ReturnMaterial = RM */
	public static final String DOCSUBTYPE_ReturnMaterial = "RM";
	/** PrepayOrder = PR */
	public static final String DOCSUBTYPE_PrepayOrder = "PR";
	/** Auftrag (Vorkasse) zur Disposition = PM */
	public static final String DOCSUBTYPE_AuftragVorkasseZurDisposition = "PM";
	/** Provisionskorrektur = CC */
	public static final String DOCSUBTYPE_Provisionskorrektur = "CC";
	/** Provisionsberechnung = CA */
	public static final String DOCSUBTYPE_Provisionsberechnung = "CA";
	/** FlatFee = FF */
	public static final String DOCSUBTYPE_FlatFee = "FF";
	/** HoldingFee = HF */
	public static final String DOCSUBTYPE_HoldingFee = "HF";
	/** Subscription = SU */
	public static final String DOCSUBTYPE_Subscription = "SU";
	/** NB - Mengendifferenz = AQ */
	public static final String DOCSUBTYPE_NB_Mengendifferenz = "AQ";
	/** NB - Preisdifferenz = AP */
	public static final String DOCSUBTYPE_NB_Preisdifferenz = "AP";
	/** GS - Lieferdifferenz = CQ */
	public static final String DOCSUBTYPE_GS_Lieferdifferenz = "CQ";
	/** GS - Preisdifferenz = CR */
	public static final String DOCSUBTYPE_GS_Preisdifferenz = "CR";
	/** QualityInspection = QI */
	public static final String DOCSUBTYPE_QualityInspection = "QI";
	/** Leergutanlieferung = ER */
	public static final String DOCSUBTYPE_Leergutanlieferung = "ER";
	/** Produktanlieferung = MR */
	public static final String DOCSUBTYPE_Produktanlieferung = "MR";
	/** Produktauslieferung = MS */
	public static final String DOCSUBTYPE_Produktauslieferung = "MS";
	/** Leergutausgabe = ES */
	public static final String DOCSUBTYPE_Leergutausgabe = "ES";
	/** GS - Retoure = CS */
	public static final String DOCSUBTYPE_GS_Retoure = "CS";
	/** VendorInvoice = VI */
	public static final String DOCSUBTYPE_VendorInvoice = "VI";
	/** DownPayment = DP */
	public static final String DOCSUBTYPE_DownPayment = "DP";
	/** Saldokorektur = EC */
	public static final String DOCSUBTYPE_Saldokorektur = "EC";
	/** Internal Use Inventory = IUI */
	public static final String DOCSUBTYPE_InternalUseInventory = "IUI";
	/** Set Doc Sub Type.
		@param DocSubType 
		Document Sub Type
	  */
	@Override
	public void setDocSubType (java.lang.String DocSubType)
	{

		set_ValueNoCheck (COLUMNNAME_DocSubType, DocSubType);
	}

	/** Get Doc Sub Type.
		@return Document Sub Type
	  */
	@Override
	public java.lang.String getDocSubType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocSubType);
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	/** Set Frachtkostenberechnung.
		@param FreightCostRule 
		Methode zur Berechnung von Frachtkosten
	  */
	@Override
	public void setFreightCostRule (java.lang.String FreightCostRule)
	{

		set_ValueNoCheck (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	/** Get Frachtkostenberechnung.
		@return Methode zur Berechnung von Frachtkosten
	  */
	@Override
	public java.lang.String getFreightCostRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FreightCostRule);
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_ValueNoCheck (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	@Override
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
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

	/** Set Order Document No.
		@param OrderDocumentNo 
		Document Number of the Order
	  */
	@Override
	public void setOrderDocumentNo (java.lang.String OrderDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_OrderDocumentNo, OrderDocumentNo);
	}

	/** Get Order Document No.
		@return Document Number of the Order
	  */
	@Override
	public java.lang.String getOrderDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderDocumentNo);
	}

	/** Set Bereitstellungsdatum.
		@param PreparationDate Bereitstellungsdatum	  */
	@Override
	public void setPreparationDate (java.sql.Timestamp PreparationDate)
	{
		set_ValueNoCheck (COLUMNNAME_PreparationDate, PreparationDate);
	}

	/** Get Bereitstellungsdatum.
		@return Bereitstellungsdatum	  */
	@Override
	public java.sql.Timestamp getPreparationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PreparationDate);
	}

	/** 
	 * PriorityRule AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	/** Set Priorität.
		@param PriorityRule 
		Priority of a document
	  */
	@Override
	public void setPriorityRule (java.lang.String PriorityRule)
	{

		set_ValueNoCheck (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priorität.
		@return Priority of a document
	  */
	@Override
	public java.lang.String getPriorityRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule);
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

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Picked.
		@param QtyPicked Qty Picked	  */
	@Override
	public void setQtyPicked (java.math.BigDecimal QtyPicked)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPicked, QtyPicked);
	}

	/** Get Qty Picked.
		@return Qty Picked	  */
	@Override
	public java.math.BigDecimal getQtyPicked () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPicked);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty picked (planned).
		@param QtyPickedPlanned Qty picked (planned)	  */
	@Override
	public void setQtyPickedPlanned (java.math.BigDecimal QtyPickedPlanned)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedPlanned, QtyPickedPlanned);
	}

	/** Get Qty picked (planned).
		@return Qty picked (planned)	  */
	@Override
	public java.math.BigDecimal getQtyPickedPlanned () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPickedPlanned);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Ausliefermenge.
		@param QtyToDeliver Ausliefermenge	  */
	@Override
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	/** Get Ausliefermenge.
		@return Ausliefermenge	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Shipper name.
		@param ShipperName Shipper name	  */
	@Override
	public void setShipperName (java.lang.String ShipperName)
	{
		set_ValueNoCheck (COLUMNNAME_ShipperName, ShipperName);
	}

	/** Get Shipper name.
		@return Shipper name	  */
	@Override
	public java.lang.String getShipperName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipperName);
	}

	/** Set Lager.
		@param WarehouseName 
		Lagerbezeichnung
	  */
	@Override
	public void setWarehouseName (java.lang.String WarehouseName)
	{
		set_ValueNoCheck (COLUMNNAME_WarehouseName, WarehouseName);
	}

	/** Get Lager.
		@return Lagerbezeichnung
	  */
	@Override
	public java.lang.String getWarehouseName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseName);
	}
}