package org.compiere.model;


/** Generated Interface for C_Order_CompensationGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order_CompensationGroup 
{

    /** TableName=C_Order_CompensationGroup */
    public static final String Table_Name = "C_Order_CompensationGroup";

    /** AD_Table_ID=540856 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_Client>(I_C_Order_CompensationGroup.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_Org>(I_C_Order_CompensationGroup.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CompensationGroup_Schema_ID();

	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	public void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

    /** Column definition for C_CompensationGroup_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, de.metas.order.model.I_C_CompensationGroup_Schema>(I_C_Order_CompensationGroup.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
    /** Column name C_CompensationGroup_Schema_ID */
    public static final String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Order Compensation Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID);

	/**
	 * Get Order Compensation Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_CompensationGroup_ID();

    /** Column definition for C_Order_CompensationGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object> COLUMN_C_Order_CompensationGroup_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object>(I_C_Order_CompensationGroup.class, "C_Order_CompensationGroup_ID", null);
    /** Column name C_Order_CompensationGroup_ID */
    public static final String COLUMNNAME_C_Order_CompensationGroup_ID = "C_Order_CompensationGroup_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_C_Order>(I_C_Order_CompensationGroup.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object>(I_C_Order_CompensationGroup.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_User>(I_C_Order_CompensationGroup.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object>(I_C_Order_CompensationGroup.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_M_Product_Category>(I_C_Order_CompensationGroup.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object>(I_C_Order_CompensationGroup.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, Object>(I_C_Order_CompensationGroup.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Order_CompensationGroup, org.compiere.model.I_AD_User>(I_C_Order_CompensationGroup.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
