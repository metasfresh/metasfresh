package org.compiere.model;


/** Generated Interface for C_BPartner_Stats
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Stats 
{

    /** TableName=C_BPartner_Stats */
    public static final String Table_Name = "C_BPartner_Stats";

    /** AD_Table_ID=540763 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktueller Gesamtertrag.
	 * Aktueller Gesamtertrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualLifeTimeValue (java.math.BigDecimal ActualLifeTimeValue);

	/**
	 * Get Aktueller Gesamtertrag.
	 * Aktueller Gesamtertrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualLifeTimeValue();

    /** Column definition for ActualLifeTimeValue */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_ActualLifeTimeValue = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "ActualLifeTimeValue", null);
    /** Column name ActualLifeTimeValue */
    public static final String COLUMNNAME_ActualLifeTimeValue = "ActualLifeTimeValue";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_Client>(I_C_BPartner_Stats.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_Org>(I_C_BPartner_Stats.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_C_BPartner>(I_C_BPartner_Stats.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_BPartner_Stats.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Stats_ID (int C_BPartner_Stats_ID);

	/**
	 * Get C_BPartner_Stats.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Stats_ID();

    /** Column definition for C_BPartner_Stats_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_C_BPartner_Stats_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "C_BPartner_Stats_ID", null);
    /** Column name C_BPartner_Stats_ID */
    public static final String COLUMNNAME_C_BPartner_Stats_ID = "C_BPartner_Stats_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_User>(I_C_BPartner_Stats.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditLimitIndicator (java.lang.String CreditLimitIndicator);

	/**
	 * Get Credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditLimitIndicator();

    /** Column definition for CreditLimitIndicator */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_CreditLimitIndicator = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "CreditLimitIndicator", null);
    /** Column name CreditLimitIndicator */
    public static final String COLUMNNAME_CreditLimitIndicator = "CreditLimitIndicator";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Offene Posten.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOpenItems (java.math.BigDecimal OpenItems);

	/**
	 * Get Offene Posten.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOpenItems();

    /** Column definition for OpenItems */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_OpenItems = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "OpenItems", null);
    /** Column name OpenItems */
    public static final String COLUMNNAME_OpenItems = "OpenItems";

	/**
	 * Set Kredit gewährt.
	 * Gegenwärtiger Aussenstand
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSO_CreditUsed (java.math.BigDecimal SO_CreditUsed);

	/**
	 * Get Kredit gewährt.
	 * Gegenwärtiger Aussenstand
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getSO_CreditUsed();

    /** Column definition for SO_CreditUsed */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_SO_CreditUsed = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "SO_CreditUsed", null);
    /** Column name SO_CreditUsed */
    public static final String COLUMNNAME_SO_CreditUsed = "SO_CreditUsed";

	/**
	 * Set Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSOCreditStatus (java.lang.String SOCreditStatus);

	/**
	 * Get Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSOCreditStatus();

    /** Column definition for SOCreditStatus */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_SOCreditStatus = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "SOCreditStatus", null);
    /** Column name SOCreditStatus */
    public static final String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, Object>(I_C_BPartner_Stats.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_AD_User>(I_C_BPartner_Stats.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
