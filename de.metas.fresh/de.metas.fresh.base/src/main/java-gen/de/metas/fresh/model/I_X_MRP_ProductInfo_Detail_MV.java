package de.metas.fresh.model;


/** Generated Interface for X_MRP_ProductInfo_Detail_MV
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_X_MRP_ProductInfo_Detail_MV 
{

    /** TableName=X_MRP_ProductInfo_Detail_MV */
    public static final String Table_Name = "X_MRP_ProductInfo_Detail_MV";

    /** AD_Table_ID=540787 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_Client>(I_X_MRP_ProductInfo_Detail_MV.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_Org>(I_X_MRP_ProductInfo_Detail_MV.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setASIKey (java.lang.String ASIKey);

	/**
	 * Get ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getASIKey();

    /** Column definition for ASIKey */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_ASIKey = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "ASIKey", null);
    /** Column name ASIKey */
    public static final String COLUMNNAME_ASIKey = "ASIKey";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_User>(I_X_MRP_ProductInfo_Detail_MV.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.sql.Timestamp DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsFallBack.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFallBack (boolean IsFallBack);

	/**
	 * Get IsFallBack.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFallBack();

    /** Column definition for IsFallBack */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_IsFallBack = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "IsFallBack", null);
    /** Column name IsFallBack */
    public static final String COLUMNNAME_IsFallBack = "IsFallBack";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_M_AttributeSetInstance>(I_X_MRP_ProductInfo_Detail_MV.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_M_Product>(I_X_MRP_ProductInfo_Detail_MV.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand);

	/**
	 * Get Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHand();

    /** Column definition for QtyOnHand */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_QtyOnHand = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "QtyOnHand", null);
    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_AD_User>(I_X_MRP_ProductInfo_Detail_MV.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set X_MRP_ProductInfo_Detail_MV.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setX_MRP_ProductInfo_Detail_MV_ID (int X_MRP_ProductInfo_Detail_MV_ID);

	/**
	 * Get X_MRP_ProductInfo_Detail_MV.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getX_MRP_ProductInfo_Detail_MV_ID();

    /** Column definition for X_MRP_ProductInfo_Detail_MV_ID */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object> COLUMN_X_MRP_ProductInfo_Detail_MV_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_Detail_MV, Object>(I_X_MRP_ProductInfo_Detail_MV.class, "X_MRP_ProductInfo_Detail_MV_ID", null);
    /** Column name X_MRP_ProductInfo_Detail_MV_ID */
    public static final String COLUMNNAME_X_MRP_ProductInfo_Detail_MV_ID = "X_MRP_ProductInfo_Detail_MV_ID";
}
