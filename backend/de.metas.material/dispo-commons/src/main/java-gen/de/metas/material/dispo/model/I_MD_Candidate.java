package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate 
{

    /** TableName=MD_Candidate */
    public static final String Table_Name = "MD_Candidate";

    /** AD_Table_ID=540808 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Customer_ID();

    /** Column name C_BPartner_Customer_ID */
    public static final String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderSO_ID();

	public org.compiere.model.I_C_Order getC_OrderSO();

	public void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO);

    /** Column definition for C_OrderSO_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Order>(I_MD_Candidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderSO_ID */
    public static final String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Plandatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateProjected (java.sql.Timestamp DateProjected);

	/**
	 * Get Plandatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateProjected();

    /** Column definition for DateProjected */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_DateProjected = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "DateProjected", null);
    /** Column name DateProjected */
    public static final String COLUMNNAME_DateProjected = "DateProjected";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ATP reserved for customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReservedForCustomer (boolean IsReservedForCustomer);

	/**
	 * Get ATP reserved for customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReservedForCustomer();

    /** Column definition for IsReservedForCustomer */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_IsReservedForCustomer = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "IsReservedForCustomer", null);
    /** Column name IsReservedForCustomer */
    public static final String COLUMNNAME_IsReservedForCustomer = "IsReservedForCustomer";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_AttributeSetInstance>(I_MD_Candidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Business case.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_BusinessCase (java.lang.String MD_Candidate_BusinessCase);

	/**
	 * Get Business case.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMD_Candidate_BusinessCase();

    /** Column definition for MD_Candidate_BusinessCase */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_BusinessCase = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "MD_Candidate_BusinessCase", null);
    /** Column name MD_Candidate_BusinessCase */
    public static final String COLUMNNAME_MD_Candidate_BusinessCase = "MD_Candidate_BusinessCase";

	/**
	 * Set Gruppen-ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_GroupId (int MD_Candidate_GroupId);

	/**
	 * Get Gruppen-ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_GroupId();

    /** Column definition for MD_Candidate_GroupId */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_GroupId = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "MD_Candidate_GroupId", null);
    /** Column name MD_Candidate_GroupId */
    public static final String COLUMNNAME_MD_Candidate_GroupId = "MD_Candidate_GroupId";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_ID();

    /** Column definition for MD_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "MD_Candidate_ID", null);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Elterndatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Parent_ID (int MD_Candidate_Parent_ID);

	/**
	 * Get Elterndatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Parent_ID();

	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate_Parent();

	public void setMD_Candidate_Parent(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate_Parent);

    /** Column definition for MD_Candidate_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_Parent_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate.class, "MD_Candidate_Parent_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_Parent_ID */
    public static final String COLUMNNAME_MD_Candidate_Parent_ID = "MD_Candidate_Parent_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Status (java.lang.String MD_Candidate_Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMD_Candidate_Status();

    /** Column definition for MD_Candidate_Status */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_Status = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "MD_Candidate_Status", null);
    /** Column name MD_Candidate_Status */
    public static final String COLUMNNAME_MD_Candidate_Status = "MD_Candidate_Status";

	/**
	 * Set Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Type (java.lang.String MD_Candidate_Type);

	/**
	 * Get Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMD_Candidate_Type();

    /** Column definition for MD_Candidate_Type */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_Type = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "MD_Candidate_Type", null);
    /** Column name MD_Candidate_Type */
    public static final String COLUMNNAME_MD_Candidate_Type = "MD_Candidate_Type";

	/**
	 * Set Prognose.
	 * Vorhersagen zu Material-/Produkt-/Artikelentwicklung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Forecast_ID (int M_Forecast_ID);

	/**
	 * Get Prognose.
	 * Vorhersagen zu Material-/Produkt-/Artikelentwicklung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Forecast_ID();

	public org.compiere.model.I_M_Forecast getM_Forecast();

	public void setM_Forecast(org.compiere.model.I_M_Forecast M_Forecast);

    /** Column definition for M_Forecast_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Forecast> COLUMN_M_Forecast_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Forecast>(I_MD_Candidate.class, "M_Forecast_ID", org.compiere.model.I_M_Forecast.class);
    /** Column name M_Forecast_ID */
    public static final String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQty_AvailableToPromise (java.math.BigDecimal Qty_AvailableToPromise);

	/**
	 * Get Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getQty_AvailableToPromise();

    /** Column definition for Qty_AvailableToPromise */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_AvailableToPromise = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Qty_AvailableToPromise", null);
    /** Column name Qty_AvailableToPromise */
    public static final String COLUMNNAME_Qty_AvailableToPromise = "Qty_AvailableToPromise";

	/**
	 * Set Fulfilled quantity.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyFulfilled (java.math.BigDecimal QtyFulfilled);

	/**
	 * Get Fulfilled quantity.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyFulfilled();

    /** Column definition for QtyFulfilled */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_QtyFulfilled = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "QtyFulfilled", null);
    /** Column name QtyFulfilled */
    public static final String COLUMNNAME_QtyFulfilled = "QtyFulfilled";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQty_Planned_Display (java.math.BigDecimal Qty_Planned_Display);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getQty_Planned_Display();

    /** Column definition for Qty_Planned_Display */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_Planned_Display = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Qty_Planned_Display", null);
    /** Column name Qty_Planned_Display */
    public static final String COLUMNNAME_Qty_Planned_Display = "Qty_Planned_Display";

	/**
	 * Set Höchstmenge.
	 * Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplenish_MaxQty (java.math.BigDecimal Replenish_MaxQty);

	/**
	 * Get Höchstmenge.
	 * Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getReplenish_MaxQty();

    /** Column definition for Replenish_MaxQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Replenish_MaxQty = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Replenish_MaxQty", null);
    /** Column name Replenish_MaxQty */
    public static final String COLUMNNAME_Replenish_MaxQty = "Replenish_MaxQty";

	/**
	 * Set Minimal ATP.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplenish_MinQty (java.math.BigDecimal Replenish_MinQty);

	/**
	 * Get Minimal ATP.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getReplenish_MinQty();

    /** Column definition for Replenish_MinQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Replenish_MinQty = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Replenish_MinQty", null);
    /** Column name Replenish_MinQty */
    public static final String COLUMNNAME_Replenish_MinQty = "Replenish_MinQty";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStorageAttributesKey();

    /** Column definition for StorageAttributesKey */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_StorageAttributesKey = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "StorageAttributesKey", null);
    /** Column name StorageAttributesKey */
    public static final String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
