package org.compiere.model;


/** Generated Interface for C_Phonecall_Schema_Version
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Phonecall_Schema_Version 
{

    /** TableName=C_Phonecall_Schema_Version */
    public static final String Table_Name = "C_Phonecall_Schema_Version";

    /** AD_Table_ID=541175 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_Client>(I_C_Phonecall_Schema_Version.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_Org>(I_C_Phonecall_Schema_Version.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Anrufliste.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_ID (int C_Phonecall_Schema_ID);

	/**
	 * Get Anrufliste.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_ID();

	public org.compiere.model.I_C_Phonecall_Schema getC_Phonecall_Schema();

	public void setC_Phonecall_Schema(org.compiere.model.I_C_Phonecall_Schema C_Phonecall_Schema);

    /** Column definition for C_Phonecall_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_C_Phonecall_Schema> COLUMN_C_Phonecall_Schema_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_C_Phonecall_Schema>(I_C_Phonecall_Schema_Version.class, "C_Phonecall_Schema_ID", org.compiere.model.I_C_Phonecall_Schema.class);
    /** Column name C_Phonecall_Schema_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_ID = "C_Phonecall_Schema_ID";

	/**
	 * Set Anruflistenversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID);

	/**
	 * Get Anruflistenversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_ID();

    /** Column definition for C_Phonecall_Schema_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_C_Phonecall_Schema_Version_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "C_Phonecall_Schema_Version_ID", null);
    /** Column name C_Phonecall_Schema_Version_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_Version_ID = "C_Phonecall_Schema_Version_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_User>(I_C_Phonecall_Schema_Version.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Every Month.
	 * Every x Months
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEveryMonth (int EveryMonth);

	/**
	 * Get Every Month.
	 * Every x Months
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEveryMonth();

    /** Column definition for EveryMonth */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_EveryMonth = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "EveryMonth", null);
    /** Column name EveryMonth */
    public static final String COLUMNNAME_EveryMonth = "EveryMonth";

	/**
	 * Set Every Week.
	 * Every x weeks
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEveryWeek (int EveryWeek);

	/**
	 * Get Every Week.
	 * Every x weeks
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEveryWeek();

    /** Column definition for EveryWeek */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_EveryWeek = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "EveryWeek", null);
    /** Column name EveryWeek */
    public static final String COLUMNNAME_EveryWeek = "EveryWeek";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsCancelPhonecallDay.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCancelPhonecallDay (boolean IsCancelPhonecallDay);

	/**
	 * Get IsCancelPhonecallDay.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCancelPhonecallDay();

    /** Column definition for IsCancelPhonecallDay */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_IsCancelPhonecallDay = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "IsCancelPhonecallDay", null);
    /** Column name IsCancelPhonecallDay */
    public static final String COLUMNNAME_IsCancelPhonecallDay = "IsCancelPhonecallDay";

	/**
	 * Set Monthly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMonthly (boolean IsMonthly);

	/**
	 * Get Monthly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMonthly();

    /** Column definition for IsMonthly */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_IsMonthly = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "IsMonthly", null);
    /** Column name IsMonthly */
    public static final String COLUMNNAME_IsMonthly = "IsMonthly";

	/**
	 * Set IsMovePhonecallDay.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMovePhonecallDay (boolean IsMovePhonecallDay);

	/**
	 * Get IsMovePhonecallDay.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMovePhonecallDay();

    /** Column definition for IsMovePhonecallDay */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_IsMovePhonecallDay = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "IsMovePhonecallDay", null);
    /** Column name IsMovePhonecallDay */
    public static final String COLUMNNAME_IsMovePhonecallDay = "IsMovePhonecallDay";

	/**
	 * Set Weekly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWeekly (boolean IsWeekly);

	/**
	 * Get Weekly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWeekly();

    /** Column definition for IsWeekly */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_IsWeekly = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "IsWeekly", null);
    /** Column name IsWeekly */
    public static final String COLUMNNAME_IsWeekly = "IsWeekly";

	/**
	 * Set Day of the Month.
	 * Day of the month 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMonthDay (int MonthDay);

	/**
	 * Get Day of the Month.
	 * Day of the month 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMonthDay();

    /** Column definition for MonthDay */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_MonthDay = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "MonthDay", null);
    /** Column name MonthDay */
    public static final String COLUMNNAME_MonthDay = "MonthDay";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Freitag.
	 * Freitags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnFriday (boolean OnFriday);

	/**
	 * Get Freitag.
	 * Freitags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnFriday();

    /** Column definition for OnFriday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnFriday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnFriday", null);
    /** Column name OnFriday */
    public static final String COLUMNNAME_OnFriday = "OnFriday";

	/**
	 * Set Montag.
	 * Montags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnMonday (boolean OnMonday);

	/**
	 * Get Montag.
	 * Montags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnMonday();

    /** Column definition for OnMonday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnMonday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnMonday", null);
    /** Column name OnMonday */
    public static final String COLUMNNAME_OnMonday = "OnMonday";

	/**
	 * Set Samstag.
	 * Samstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnSaturday (boolean OnSaturday);

	/**
	 * Get Samstag.
	 * Samstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnSaturday();

    /** Column definition for OnSaturday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnSaturday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnSaturday", null);
    /** Column name OnSaturday */
    public static final String COLUMNNAME_OnSaturday = "OnSaturday";

	/**
	 * Set Sonntag.
	 * Sonntags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnSunday (boolean OnSunday);

	/**
	 * Get Sonntag.
	 * Sonntags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnSunday();

    /** Column definition for OnSunday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnSunday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnSunday", null);
    /** Column name OnSunday */
    public static final String COLUMNNAME_OnSunday = "OnSunday";

	/**
	 * Set Donnerstag.
	 * Donnerstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnThursday (boolean OnThursday);

	/**
	 * Get Donnerstag.
	 * Donnerstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnThursday();

    /** Column definition for OnThursday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnThursday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnThursday", null);
    /** Column name OnThursday */
    public static final String COLUMNNAME_OnThursday = "OnThursday";

	/**
	 * Set Dienstag.
	 * Dienstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnTuesday (boolean OnTuesday);

	/**
	 * Get Dienstag.
	 * Dienstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnTuesday();

    /** Column definition for OnTuesday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnTuesday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnTuesday", null);
    /** Column name OnTuesday */
    public static final String COLUMNNAME_OnTuesday = "OnTuesday";

	/**
	 * Set Mittwoch.
	 * Mittwochs verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOnWednesday (boolean OnWednesday);

	/**
	 * Get Mittwoch.
	 * Mittwochs verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOnWednesday();

    /** Column definition for OnWednesday */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_OnWednesday = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "OnWednesday", null);
    /** Column name OnWednesday */
    public static final String COLUMNNAME_OnWednesday = "OnWednesday";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, org.compiere.model.I_AD_User>(I_C_Phonecall_Schema_Version.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version, Object>(I_C_Phonecall_Schema_Version.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
