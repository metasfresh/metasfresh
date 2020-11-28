package org.compiere.model;


/** Generated Interface for M_CostQueue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_CostQueue 
{

    /** TableName=M_CostQueue */
    public static final String Table_Name = "M_CostQueue";

    /** AD_Table_ID=817 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_Client>(I_M_CostQueue.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_Org>(I_M_CostQueue.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_C_AcctSchema>(I_M_CostQueue.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_User>(I_M_CostQueue.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Kostenpreis aktuell.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrentCostPrice (java.math.BigDecimal CurrentCostPrice);

	/**
	 * Get Kostenpreis aktuell.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrentCostPrice();

    /** Column definition for CurrentCostPrice */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_CurrentCostPrice = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "CurrentCostPrice", null);
    /** Column name CurrentCostPrice */
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/**
	 * Set Menge aktuell.
	 * Menge aktuell
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrentQty (java.math.BigDecimal CurrentQty);

	/**
	 * Get Menge aktuell.
	 * Menge aktuell
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrentQty();

    /** Column definition for CurrentQty */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_CurrentQty = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "CurrentQty", null);
    /** Column name CurrentQty */
    public static final String COLUMNNAME_CurrentQty = "CurrentQty";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_AttributeSetInstance>(I_M_CostQueue.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostElement_ID();

	public org.compiere.model.I_M_CostElement getM_CostElement();

	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement);

    /** Column definition for M_CostElement_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_CostElement>(I_M_CostQueue.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
    /** Column name M_CostElement_ID */
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Kosten-Reihe.
	 * FiFo/LiFo Cost Queue
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostQueue_ID (int M_CostQueue_ID);

	/**
	 * Get Kosten-Reihe.
	 * FiFo/LiFo Cost Queue
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostQueue_ID();

    /** Column definition for M_CostQueue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_M_CostQueue_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "M_CostQueue_ID", null);
    /** Column name M_CostQueue_ID */
    public static final String COLUMNNAME_M_CostQueue_ID = "M_CostQueue_ID";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostType_ID();

	public org.compiere.model.I_M_CostType getM_CostType();

	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

    /** Column definition for M_CostType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_CostType>(I_M_CostQueue.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
    /** Column name M_CostType_ID */
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_M_Product>(I_M_CostQueue.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_CostQueue, Object>(I_M_CostQueue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_CostQueue, org.compiere.model.I_AD_User>(I_M_CostQueue.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
