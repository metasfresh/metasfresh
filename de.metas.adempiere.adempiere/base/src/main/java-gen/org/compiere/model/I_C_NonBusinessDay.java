package org.compiere.model;


/** Generated Interface for C_NonBusinessDay
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_NonBusinessDay 
{

    /** TableName=C_NonBusinessDay */
    public static final String Table_Name = "C_NonBusinessDay";

    /** AD_Table_ID=163 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_Client>(I_C_NonBusinessDay.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_Org>(I_C_NonBusinessDay.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_C_Calendar>(I_C_NonBusinessDay.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Geschäftsfreier Tag.
	 * Day on which business is not transacted
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_NonBusinessDay_ID (int C_NonBusinessDay_ID);

	/**
	 * Get Geschäftsfreier Tag.
	 * Day on which business is not transacted
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_NonBusinessDay_ID();

    /** Column definition for C_NonBusinessDay_ID */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_C_NonBusinessDay_ID = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "C_NonBusinessDay_ID", null);
    /** Column name C_NonBusinessDay_ID */
    public static final String COLUMNNAME_C_NonBusinessDay_ID = "C_NonBusinessDay_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_User>(I_C_NonBusinessDay.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum.
	 * Date when business is not conducted
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDate1 (java.sql.Timestamp Date1);

	/**
	 * Get Datum.
	 * Date when business is not conducted
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDate1();

    /** Column definition for Date1 */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_Date1 = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "Date1", null);
    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/**
	 * Set Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEndDate (java.sql.Timestamp EndDate);

	/**
	 * Get Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEndDate();

    /** Column definition for EndDate */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_EndDate = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "EndDate", null);
    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFrequency (java.lang.String Frequency);

	/**
	 * Get Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrequency();

    /** Column definition for Frequency */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_Frequency = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "Frequency", null);
    /** Column name Frequency */
    public static final String COLUMNNAME_Frequency = "Frequency";

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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Repeat.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRepeat (boolean IsRepeat);

	/**
	 * Get Repeat.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRepeat();

    /** Column definition for IsRepeat */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_IsRepeat = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "IsRepeat", null);
    /** Column name IsRepeat */
    public static final String COLUMNNAME_IsRepeat = "IsRepeat";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, Object>(I_C_NonBusinessDay.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_NonBusinessDay, org.compiere.model.I_AD_User>(I_C_NonBusinessDay.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
