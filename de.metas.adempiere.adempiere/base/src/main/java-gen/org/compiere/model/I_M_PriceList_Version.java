package org.compiere.model;


/** Generated Interface for M_PriceList_Version
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_PriceList_Version 
{

    /** TableName=M_PriceList_Version */
    public static final String Table_Name = "M_PriceList_Version";

    /** AD_Table_ID=295 */
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_Client>(I_M_PriceList_Version.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_Org>(I_M_PriceList_Version.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_User>(I_M_PriceList_Version.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Rabatt-Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt-Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_DiscountSchema>(I_M_PriceList_Version.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList();

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_PriceList>(I_M_PriceList_Version.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Basis-Preislistenversion.
	 * Basis für Preiskalkulationen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Pricelist_Version_Base_ID (int M_Pricelist_Version_Base_ID);

	/**
	 * Get Basis-Preislistenversion.
	 * Basis für Preiskalkulationen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Pricelist_Version_Base_ID();

	public org.compiere.model.I_M_PriceList_Version getM_Pricelist_Version_Base();

	public void setM_Pricelist_Version_Base(org.compiere.model.I_M_PriceList_Version M_Pricelist_Version_Base);

    /** Column definition for M_Pricelist_Version_Base_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_PriceList_Version> COLUMN_M_Pricelist_Version_Base_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_PriceList_Version>(I_M_PriceList_Version.class, "M_Pricelist_Version_Base_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name M_Pricelist_Version_Base_ID */
    public static final String COLUMNNAME_M_Pricelist_Version_Base_ID = "M_Pricelist_Version_Base_ID";

	/**
	 * Set Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_Version_ID();

    /** Column definition for M_PriceList_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_M_PriceList_Version_ID = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "M_PriceList_Version_ID", null);
    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcCreate (java.lang.String ProcCreate);

	/**
	 * Get Erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProcCreate();

    /** Column definition for ProcCreate */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_ProcCreate = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "ProcCreate", null);
    /** Column name ProcCreate */
    public static final String COLUMNNAME_ProcCreate = "ProcCreate";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, org.compiere.model.I_AD_User>(I_M_PriceList_Version.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_PriceList_Version, Object>(I_M_PriceList_Version.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
