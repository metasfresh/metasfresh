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
	private static final long serialVersionUID = -1529474840L;

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
			setQty_Planner (BigDecimal.ZERO);
			setSeqNo (0);
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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
	 * MD_Candidate_SubType AD_Reference_ID=540709
	 * Reference name: MD_Candidate_SubType
	 */
	public static final int MD_CANDIDATE_SUBTYPE_AD_Reference_ID=540709;
	/** DISTRIBUTION = DISTRIBUTION */
	public static final String MD_CANDIDATE_SUBTYPE_DISTRIBUTION = "DISTRIBUTION";
	/** PRODUCTION = PRODUCTION */
	public static final String MD_CANDIDATE_SUBTYPE_PRODUCTION = "PRODUCTION";
	/** RECEIPT = RECEIPT */
	public static final String MD_CANDIDATE_SUBTYPE_RECEIPT = "RECEIPT";
	/** SHIPMENT = SHIPMENT */
	public static final String MD_CANDIDATE_SUBTYPE_SHIPMENT = "SHIPMENT";
	/** FORECAST = FORECAST */
	public static final String MD_CANDIDATE_SUBTYPE_FORECAST = "FORECAST";
	/** Set Untertyp.
		@param MD_Candidate_SubType Untertyp	  */
	@Override
	public void setMD_Candidate_SubType (java.lang.String MD_Candidate_SubType)
	{

		set_Value (COLUMNNAME_MD_Candidate_SubType, MD_Candidate_SubType);
	}

	/** Get Untertyp.
		@return Untertyp	  */
	@Override
	public java.lang.String getMD_Candidate_SubType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_SubType);
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

	/** Set Menge Abw..
		@param Qty_Override Menge Abw.	  */
	@Override
	public void setQty_Override (java.math.BigDecimal Qty_Override)
	{
		set_Value (COLUMNNAME_Qty_Override, Qty_Override);
	}

	/** Get Menge Abw..
		@return Menge Abw.	  */
	@Override
	public java.math.BigDecimal getQty_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Planmenge.
		@param Qty_Planner Planmenge	  */
	@Override
	public void setQty_Planner (java.math.BigDecimal Qty_Planner)
	{
		set_Value (COLUMNNAME_Qty_Planner, Qty_Planner);
	}

	/** Get Planmenge.
		@return Planmenge	  */
	@Override
	public java.math.BigDecimal getQty_Planner () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_Planner);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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