package org.compiere.model;


/** Generated Interface for M_DiscountSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_DiscountSchema 
{

    /** TableName=M_DiscountSchema */
    public static final String Table_Name = "M_DiscountSchema";

    /** AD_Table_ID=475 */
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Client>(I_M_DiscountSchema.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Org>(I_M_DiscountSchema.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User>(I_M_DiscountSchema.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Anwenden auf.
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCumulativeLevel (java.lang.String CumulativeLevel);

	/**
	 * Get Anwenden auf.
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCumulativeLevel();

    /** Column definition for CumulativeLevel */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_CumulativeLevel = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "CumulativeLevel", null);
    /** Column name CumulativeLevel */
    public static final String COLUMNNAME_CumulativeLevel = "CumulativeLevel";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt Art.
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDiscountType (java.lang.String DiscountType);

	/**
	 * Get Rabatt Art.
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDiscountType();

    /** Column definition for DiscountType */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_DiscountType = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "DiscountType", null);
    /** Column name DiscountType */
    public static final String COLUMNNAME_DiscountType = "DiscountType";

	/**
	 * Set Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFlatDiscount (java.math.BigDecimal FlatDiscount);

	/**
	 * Get Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFlatDiscount();

    /** Column definition for FlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_FlatDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "FlatDiscount", null);
    /** Column name FlatDiscount */
    public static final String COLUMNNAME_FlatDiscount = "FlatDiscount";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fester Rabatt.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/**
	 * Get Fester Rabatt.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBPartnerFlatDiscount();

    /** Column definition for IsBPartnerFlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsBPartnerFlatDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "IsBPartnerFlatDiscount", null);
    /** Column name IsBPartnerFlatDiscount */
    public static final String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/**
	 * Set Mengen Rabatt.
	 * Trade discount break level based on Quantity (not value)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQuantityBased (boolean IsQuantityBased);

	/**
	 * Get Mengen Rabatt.
	 * Trade discount break level based on Quantity (not value)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQuantityBased();

    /** Column definition for IsQuantityBased */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsQuantityBased = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "IsQuantityBased", null);
    /** Column name IsQuantityBased */
    public static final String COLUMNNAME_IsQuantityBased = "IsQuantityBased";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "M_DiscountSchema_ID", null);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScript (java.lang.String Script);

	/**
	 * Get Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getScript();

    /** Column definition for Script */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Script = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Script", null);
    /** Column name Script */
    public static final String COLUMNNAME_Script = "Script";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User>(I_M_DiscountSchema.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
