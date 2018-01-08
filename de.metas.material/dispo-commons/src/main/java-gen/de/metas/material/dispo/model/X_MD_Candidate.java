/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate extends org.compiere.model.PO implements I_MD_Candidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1226392063L;

    /** Standard Constructor */
    public X_MD_Candidate (Properties ctx, int MD_Candidate_ID, String trxName)
    {
      super (ctx, MD_Candidate_ID, trxName);
      /** if (MD_Candidate_ID == 0)
        {
			setDateProjected (new Timestamp( System.currentTimeMillis() ));
			setMD_Candidate_ID (0);
			setMD_Candidate_Type (null);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setQty (BigDecimal.ZERO);
			setSeqNo (0);
			setStorageAttributesKey (null); // -1002
        } */
    }

    /** Load Constructor */
    public X_MD_Candidate (Properties ctx, ResultSet rs, String trxName)
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
		throw new IllegalArgumentException ("C_Order_ID is virtual column");	}

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

	/** Set Plandatum.
		@param DateProjected Plandatum	  */
	@Override
	public void setDateProjected (java.sql.Timestamp DateProjected)
	{
		set_Value (COLUMNNAME_DateProjected, DateProjected);
	}

	/** Get Plandatum.
		@return Plandatum	  */
	@Override
	public java.sql.Timestamp getDateProjected () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateProjected);
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

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MD_Candidate_BusinessCase AD_Reference_ID=540709
	 * Reference name: MD_Candidate_BusinessCase
	 */
	public static final int MD_CANDIDATE_BUSINESSCASE_AD_Reference_ID=540709;
	/** DISTRIBUTION = DISTRIBUTION */
	public static final String MD_CANDIDATE_BUSINESSCASE_DISTRIBUTION = "DISTRIBUTION";
	/** PRODUCTION = PRODUCTION */
	public static final String MD_CANDIDATE_BUSINESSCASE_PRODUCTION = "PRODUCTION";
	/** RECEIPT = RECEIPT */
	public static final String MD_CANDIDATE_BUSINESSCASE_RECEIPT = "RECEIPT";
	/** SHIPMENT = SHIPMENT */
	public static final String MD_CANDIDATE_BUSINESSCASE_SHIPMENT = "SHIPMENT";
	/** FORECAST = FORECAST */
	public static final String MD_CANDIDATE_BUSINESSCASE_FORECAST = "FORECAST";
	/** Set Geschäftsvorfall.
		@param MD_Candidate_BusinessCase Geschäftsvorfall	  */
	@Override
	public void setMD_Candidate_BusinessCase (java.lang.String MD_Candidate_BusinessCase)
	{

		set_Value (COLUMNNAME_MD_Candidate_BusinessCase, MD_Candidate_BusinessCase);
	}

	/** Get Geschäftsvorfall.
		@return Geschäftsvorfall	  */
	@Override
	public java.lang.String getMD_Candidate_BusinessCase () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_BusinessCase);
	}

	/** Set Gruppen-ID.
		@param MD_Candidate_GroupId Gruppen-ID	  */
	@Override
	public void setMD_Candidate_GroupId (int MD_Candidate_GroupId)
	{
		set_Value (COLUMNNAME_MD_Candidate_GroupId, Integer.valueOf(MD_Candidate_GroupId));
	}

	/** Get Gruppen-ID.
		@return Gruppen-ID	  */
	@Override
	public int getMD_Candidate_GroupId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_GroupId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dispositionskandidat.
		@param MD_Candidate_ID Dispositionskandidat	  */
	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	/** Get Dispositionskandidat.
		@return Dispositionskandidat	  */
	@Override
	public int getMD_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate_Parent() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_Parent_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate_Parent(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate_Parent)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_Parent_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate_Parent);
	}

	/** Set Elterndatensatz.
		@param MD_Candidate_Parent_ID Elterndatensatz	  */
	@Override
	public void setMD_Candidate_Parent_ID (int MD_Candidate_Parent_ID)
	{
		if (MD_Candidate_Parent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, Integer.valueOf(MD_Candidate_Parent_ID));
	}

	/** Get Elterndatensatz.
		@return Elterndatensatz	  */
	@Override
	public int getMD_Candidate_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MD_Candidate_Status AD_Reference_ID=540715
	 * Reference name: MD_Candidate_Status
	 */
	public static final int MD_CANDIDATE_STATUS_AD_Reference_ID=540715;
	/** doc_created = doc_created */
	public static final String MD_CANDIDATE_STATUS_Doc_created = "doc_created";
	/** doc_planned = doc_planned */
	public static final String MD_CANDIDATE_STATUS_Doc_planned = "doc_planned";
	/** doc_completed = doc_completed */
	public static final String MD_CANDIDATE_STATUS_Doc_completed = "doc_completed";
	/** doc_closed = doc_closed */
	public static final String MD_CANDIDATE_STATUS_Doc_closed = "doc_closed";
	/** Set Status.
		@param MD_Candidate_Status Status	  */
	@Override
	public void setMD_Candidate_Status (java.lang.String MD_Candidate_Status)
	{

		set_Value (COLUMNNAME_MD_Candidate_Status, MD_Candidate_Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getMD_Candidate_Status () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_Status);
	}

	/** 
	 * MD_Candidate_Type AD_Reference_ID=540707
	 * Reference name: MD_Candidate_Type
	 */
	public static final int MD_CANDIDATE_TYPE_AD_Reference_ID=540707;
	/** STOCK = STOCK */
	public static final String MD_CANDIDATE_TYPE_STOCK = "STOCK";
	/** DEMAND = DEMAND */
	public static final String MD_CANDIDATE_TYPE_DEMAND = "DEMAND";
	/** SUPPLY = SUPPLY */
	public static final String MD_CANDIDATE_TYPE_SUPPLY = "SUPPLY";
	/** STOCK_UP = STOCK_UP */
	public static final String MD_CANDIDATE_TYPE_STOCK_UP = "STOCK_UP";
	/** UNRELATED_INCREASE = UNRELATED_INCREASE */
	public static final String MD_CANDIDATE_TYPE_UNRELATED_INCREASE = "UNRELATED_INCREASE";
	/** UNRELATED_DECREASE = UNRELATED_DECREASE */
	public static final String MD_CANDIDATE_TYPE_UNRELATED_DECREASE = "UNRELATED_DECREASE";
	/** Set Typ.
		@param MD_Candidate_Type Typ	  */
	@Override
	public void setMD_Candidate_Type (java.lang.String MD_Candidate_Type)
	{

		set_Value (COLUMNNAME_MD_Candidate_Type, MD_Candidate_Type);
	}

	/** Get Typ.
		@return Typ	  */
	@Override
	public java.lang.String getMD_Candidate_Type () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_Type);
	}

	@Override
	public org.compiere.model.I_M_Forecast getM_Forecast() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class);
	}

	@Override
	public void setM_Forecast(org.compiere.model.I_M_Forecast M_Forecast)
	{
		set_ValueFromPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class, M_Forecast);
	}

	/** Set Prognose.
		@param M_Forecast_ID 
		Vorhersagen zu Material-/Produkt-/Artikelentwicklung
	  */
	@Override
	public void setM_Forecast_ID (int M_Forecast_ID)
	{
		throw new IllegalArgumentException ("M_Forecast_ID is virtual column");	}

	/** Get Prognose.
		@return Vorhersagen zu Material-/Produkt-/Artikelentwicklung
	  */
	@Override
	public int getM_Forecast_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Forecast_ID);
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

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		throw new IllegalArgumentException ("M_ShipmentSchedule_ID is virtual column");	}

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

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
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

	/** Set StorageAttributesKey (technical).
		@param StorageAttributesKey StorageAttributesKey (technical)	  */
	@Override
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	/** Get StorageAttributesKey (technical).
		@return StorageAttributesKey (technical)	  */
	@Override
	public java.lang.String getStorageAttributesKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageAttributesKey);
	}
}