package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate 
{

    /** TableName=MD_Candidate */
    public static final String Table_Name = "MD_Candidate";

    /** AD_Table_ID=540808 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_Client>(I_MD_Candidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_Org>(I_MD_Candidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_BPartner>(I_MD_Candidate.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Order>(I_MD_Candidate.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
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
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_User>(I_MD_Candidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
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
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
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
	 * Set Geschäftsvorfall.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_BusinessCase (java.lang.String MD_Candidate_BusinessCase);

	/**
	 * Get Geschäftsvorfall.
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
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Product>(I_MD_Candidate.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
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
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Warehouse>(I_MD_Candidate.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
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
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQty_Display (java.math.BigDecimal Qty_Display);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getQty_Display();

    /** Column definition for Qty_Display */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_Display = new org.adempiere.model.ModelColumn<I_MD_Candidate, Object>(I_MD_Candidate.class, "Qty_Display", null);
    /** Column name Qty_Display */
    public static final String COLUMNNAME_Qty_Display = "Qty_Display";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
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
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
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
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate, org.compiere.model.I_AD_User>(I_MD_Candidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
