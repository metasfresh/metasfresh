package de.metas.invoicecandidate.model;


/** Generated Interface for M_ProductGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ProductGroup 
{

    /** TableName=M_ProductGroup */
    public static final String Table_Name = "M_ProductGroup";

    /** AD_Table_ID=540323 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_Client>(I_M_ProductGroup.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_Org>(I_M_ProductGroup.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ProductGroup, Object>(I_M_ProductGroup.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_User>(I_M_ProductGroup.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ProductGroup, Object>(I_M_ProductGroup.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID);

	/**
	 * Get Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductGroup_ID();

    /** Column definition for M_ProductGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, Object> COLUMN_M_ProductGroup_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup, Object>(I_M_ProductGroup.class, "M_ProductGroup_ID", null);
    /** Column name M_ProductGroup_ID */
    public static final String COLUMNNAME_M_ProductGroup_ID = "M_ProductGroup_ID";

	/**
	 * Set Stellvertreter-Produkt.
	 * Produkt, dass bei einer Aggregation unterschiedlicher Produkte als Stellvertreter der aggregierten Produkte ausgewiesen werden kann
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Proxy_ID (int M_Product_Proxy_ID);

	/**
	 * Get Stellvertreter-Produkt.
	 * Produkt, dass bei einer Aggregation unterschiedlicher Produkte als Stellvertreter der aggregierten Produkte ausgewiesen werden kann
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Proxy_ID();

    /** Column definition for M_Product_Proxy_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_M_Product> COLUMN_M_Product_Proxy_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_M_Product>(I_M_ProductGroup.class, "M_Product_Proxy_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Proxy_ID */
    public static final String COLUMNNAME_M_Product_Proxy_ID = "M_Product_Proxy_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_ProductGroup, Object>(I_M_ProductGroup.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ProductGroup, Object>(I_M_ProductGroup.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ProductGroup, org.compiere.model.I_AD_User>(I_M_ProductGroup.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
