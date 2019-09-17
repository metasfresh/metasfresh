package de.metas.invoicecandidate.model;


/** Generated Interface for M_ProductGroup_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ProductGroup_Product 
{

    /** TableName=M_ProductGroup_Product */
    public static final String Table_Name = "M_ProductGroup_Product";

    /** AD_Table_ID=540324 */
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_Client>(I_M_ProductGroup_Product.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_Org>(I_M_ProductGroup_Product.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object>(I_M_ProductGroup_Product.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_User>(I_M_ProductGroup_Product.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object>(I_M_ProductGroup_Product.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_M_Product_Category>(I_M_ProductGroup_Product.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID);

	/**
	 * Get Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductGroup_ID();

	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup();

	public void setM_ProductGroup(de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup);

    /** Column definition for M_ProductGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, de.metas.invoicecandidate.model.I_M_ProductGroup> COLUMN_M_ProductGroup_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, de.metas.invoicecandidate.model.I_M_ProductGroup>(I_M_ProductGroup_Product.class, "M_ProductGroup_ID", de.metas.invoicecandidate.model.I_M_ProductGroup.class);
    /** Column name M_ProductGroup_ID */
    public static final String COLUMNNAME_M_ProductGroup_ID = "M_ProductGroup_ID";

	/**
	 * Set Produktgruppe - Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductGroup_Product_ID (int M_ProductGroup_Product_ID);

	/**
	 * Get Produktgruppe - Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductGroup_Product_ID();

    /** Column definition for M_ProductGroup_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_M_ProductGroup_Product_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object>(I_M_ProductGroup_Product.class, "M_ProductGroup_Product_ID", null);
    /** Column name M_ProductGroup_Product_ID */
    public static final String COLUMNNAME_M_ProductGroup_Product_ID = "M_ProductGroup_Product_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_M_Product>(I_M_ProductGroup_Product.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, Object>(I_M_ProductGroup_Product.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ProductGroup_Product, org.compiere.model.I_AD_User>(I_M_ProductGroup_Product.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
