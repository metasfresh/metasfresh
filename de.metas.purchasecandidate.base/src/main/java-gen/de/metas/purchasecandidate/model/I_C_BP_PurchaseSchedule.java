package de.metas.purchasecandidate.model;


/** Generated Interface for C_BP_PurchaseSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BP_PurchaseSchedule 
{

    /** TableName=C_BP_PurchaseSchedule */
    public static final String Table_Name = "C_BP_PurchaseSchedule";

    /** AD_Table_ID=540975 */
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_Client>(I_C_BP_PurchaseSchedule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_Org>(I_C_BP_PurchaseSchedule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_C_BPartner>(I_C_BP_PurchaseSchedule.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set BPartner's purchase schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_PurchaseSchedule_ID (int C_BP_PurchaseSchedule_ID);

	/**
	 * Get BPartner's purchase schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_PurchaseSchedule_ID();

    /** Column definition for C_BP_PurchaseSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_C_BP_PurchaseSchedule_ID = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "C_BP_PurchaseSchedule_ID", null);
    /** Column name C_BP_PurchaseSchedule_ID */
    public static final String COLUMNNAME_C_BP_PurchaseSchedule_ID = "C_BP_PurchaseSchedule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_User>(I_C_BP_PurchaseSchedule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFrequency (int Frequency);

	/**
	 * Get Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFrequency();

    /** Column definition for Frequency */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Frequency = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "Frequency", null);
    /** Column name Frequency */
    public static final String COLUMNNAME_Frequency = "Frequency";

	/**
	 * Set Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrequencyType();

    /** Column definition for FrequencyType */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_FrequencyType = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "FrequencyType", null);
    /** Column name FrequencyType */
    public static final String COLUMNNAME_FrequencyType = "FrequencyType";

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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_MonthDay = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "MonthDay", null);
    /** Column name MonthDay */
    public static final String COLUMNNAME_MonthDay = "MonthDay";

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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnFriday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnFriday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnMonday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnMonday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnSaturday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnSaturday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnSunday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnSunday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnThursday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnThursday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnTuesday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnTuesday", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnWednesday = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "OnWednesday", null);
    /** Column name OnWednesday */
    public static final String COLUMNNAME_OnWednesday = "OnWednesday";

	/**
	 * Set Bereitstellungszeit Mo.
	 * Preparation time for monday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_1 (java.sql.Timestamp PreparationTime_1);

	/**
	 * Get Bereitstellungszeit Mo.
	 * Preparation time for monday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_1();

    /** Column definition for PreparationTime_1 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_1 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_1", null);
    /** Column name PreparationTime_1 */
    public static final String COLUMNNAME_PreparationTime_1 = "PreparationTime_1";

	/**
	 * Set Bereitstellungszeit Di.
	 * Preparation time for tuesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_2 (java.sql.Timestamp PreparationTime_2);

	/**
	 * Get Bereitstellungszeit Di.
	 * Preparation time for tuesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_2();

    /** Column definition for PreparationTime_2 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_2 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_2", null);
    /** Column name PreparationTime_2 */
    public static final String COLUMNNAME_PreparationTime_2 = "PreparationTime_2";

	/**
	 * Set Bereitstellungszeit Mi.
	 * Preparation time for wednesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_3 (java.sql.Timestamp PreparationTime_3);

	/**
	 * Get Bereitstellungszeit Mi.
	 * Preparation time for wednesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_3();

    /** Column definition for PreparationTime_3 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_3 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_3", null);
    /** Column name PreparationTime_3 */
    public static final String COLUMNNAME_PreparationTime_3 = "PreparationTime_3";

	/**
	 * Set Bereitstellungszeit Do.
	 * Preparation time for thursday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_4 (java.sql.Timestamp PreparationTime_4);

	/**
	 * Get Bereitstellungszeit Do.
	 * Preparation time for thursday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_4();

    /** Column definition for PreparationTime_4 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_4 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_4", null);
    /** Column name PreparationTime_4 */
    public static final String COLUMNNAME_PreparationTime_4 = "PreparationTime_4";

	/**
	 * Set Bereitstellungszeit Fr.
	 * Preparation time for Friday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_5 (java.sql.Timestamp PreparationTime_5);

	/**
	 * Get Bereitstellungszeit Fr.
	 * Preparation time for Friday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_5();

    /** Column definition for PreparationTime_5 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_5 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_5", null);
    /** Column name PreparationTime_5 */
    public static final String COLUMNNAME_PreparationTime_5 = "PreparationTime_5";

	/**
	 * Set Bereitstellungszeit Sa.
	 * Preparation time for Saturday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_6 (java.sql.Timestamp PreparationTime_6);

	/**
	 * Get Bereitstellungszeit Sa.
	 * Preparation time for Saturday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_6();

    /** Column definition for PreparationTime_6 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_6 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_6", null);
    /** Column name PreparationTime_6 */
    public static final String COLUMNNAME_PreparationTime_6 = "PreparationTime_6";

	/**
	 * Set Bereitstellungszeit So.
	 * Preparation time for Sunday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationTime_7 (java.sql.Timestamp PreparationTime_7);

	/**
	 * Get Bereitstellungszeit So.
	 * Preparation time for Sunday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationTime_7();

    /** Column definition for PreparationTime_7 */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_7 = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "PreparationTime_7", null);
    /** Column name PreparationTime_7 */
    public static final String COLUMNNAME_PreparationTime_7 = "PreparationTime_7";

	/**
	 * Set Wiedervorlage (min).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReminderTimeInMin (int ReminderTimeInMin);

	/**
	 * Get Wiedervorlage (min).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getReminderTimeInMin();

    /** Column definition for ReminderTimeInMin */
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_ReminderTimeInMin = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "ReminderTimeInMin", null);
    /** Column name ReminderTimeInMin */
    public static final String COLUMNNAME_ReminderTimeInMin = "ReminderTimeInMin";

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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_AD_User>(I_C_BP_PurchaseSchedule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_BP_PurchaseSchedule, Object>(I_C_BP_PurchaseSchedule.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
