package org.compiere.model;


/** Generated Interface for C_PriceLimit_Restriction
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PriceLimit_Restriction 
{

    /** TableName=C_PriceLimit_Restriction */
    public static final String Table_Name = "C_PriceLimit_Restriction";

    /** AD_Table_ID=540962 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_Client>(I_C_PriceLimit_Restriction.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_Org>(I_C_PriceLimit_Restriction.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getBase_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getBase_PricingSystem();

	public void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem);

    /** Column definition for Base_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_M_PricingSystem> COLUMN_Base_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_M_PricingSystem>(I_C_PriceLimit_Restriction.class, "Base_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name Base_PricingSystem_ID */
    public static final String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

	/**
	 * Set Price Limit Restriction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PriceLimit_Restriction_ID (int C_PriceLimit_Restriction_ID);

	/**
	 * Get Price Limit Restriction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PriceLimit_Restriction_ID();

    /** Column definition for C_PriceLimit_Restriction_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_C_PriceLimit_Restriction_ID = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "C_PriceLimit_Restriction_ID", null);
    /** Column name C_PriceLimit_Restriction_ID */
    public static final String COLUMNNAME_C_PriceLimit_Restriction_ID = "C_PriceLimit_Restriction_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_User>(I_C_PriceLimit_Restriction.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt);

	/**
	 * Get Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_AddAmt();

    /** Column definition for Std_AddAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_Std_AddAmt = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "Std_AddAmt", null);
    /** Column name Std_AddAmt */
    public static final String COLUMNNAME_Std_AddAmt = "Std_AddAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, Object>(I_C_PriceLimit_Restriction.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_PriceLimit_Restriction, org.compiere.model.I_AD_User>(I_C_PriceLimit_Restriction.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
