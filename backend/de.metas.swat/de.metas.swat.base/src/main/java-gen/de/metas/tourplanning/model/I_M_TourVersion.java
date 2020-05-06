package de.metas.tourplanning.model;


/** Generated Interface for M_TourVersion
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_TourVersion 
{

    /** TableName=M_TourVersion */
    public static final String Table_Name = "M_TourVersion";

    /** AD_Table_ID=540295 */
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_Client>(I_M_TourVersion.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_Org>(I_M_TourVersion.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_User>(I_M_TourVersion.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Intervall (in Monaten).
	 * Alle X Monate
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEveryMonth (int EveryMonth);

	/**
	 * Get Intervall (in Monaten).
	 * Alle X Monate
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEveryMonth();

    /** Column definition for EveryMonth */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_EveryMonth = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "EveryMonth", null);
    /** Column name EveryMonth */
    public static final String COLUMNNAME_EveryMonth = "EveryMonth";

	/**
	 * Set Intervall (in Wochen).
	 * Alle X Wochen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEveryWeek (int EveryWeek);

	/**
	 * Get Intervall (in Wochen).
	 * Alle X Wochen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEveryWeek();

    /** Column definition for EveryWeek */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_EveryWeek = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "EveryWeek", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Liefertage verwerfen.
	 * Liefertage, die nicht auf einen Werktag fallen, werden verworfen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCancelDeliveryDay (boolean IsCancelDeliveryDay);

	/**
	 * Get Liefertage verwerfen.
	 * Liefertage, die nicht auf einen Werktag fallen, werden verworfen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCancelDeliveryDay();

    /** Column definition for IsCancelDeliveryDay */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_IsCancelDeliveryDay = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "IsCancelDeliveryDay", null);
    /** Column name IsCancelDeliveryDay */
    public static final String COLUMNNAME_IsCancelDeliveryDay = "IsCancelDeliveryDay";

	/**
	 * Set Monatlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMonthly (boolean IsMonthly);

	/**
	 * Get Monatlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMonthly();

    /** Column definition for IsMonthly */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_IsMonthly = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "IsMonthly", null);
    /** Column name IsMonthly */
    public static final String COLUMNNAME_IsMonthly = "IsMonthly";

	/**
	 * Set Liefertage verschieben.
	 * Liefertage, Die nicht auf einen Werktag fallen, werden auf den nächsten Werktag verschoben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMoveDeliveryDay (boolean IsMoveDeliveryDay);

	/**
	 * Get Liefertage verschieben.
	 * Liefertage, Die nicht auf einen Werktag fallen, werden auf den nächsten Werktag verschoben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMoveDeliveryDay();

    /** Column definition for IsMoveDeliveryDay */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_IsMoveDeliveryDay = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "IsMoveDeliveryDay", null);
    /** Column name IsMoveDeliveryDay */
    public static final String COLUMNNAME_IsMoveDeliveryDay = "IsMoveDeliveryDay";

	/**
	 * Set Wöchentlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsWeekly (boolean IsWeekly);

	/**
	 * Get Wöchentlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isWeekly();

    /** Column definition for IsWeekly */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_IsWeekly = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "IsWeekly", null);
    /** Column name IsWeekly */
    public static final String COLUMNNAME_IsWeekly = "IsWeekly";

	/**
	 * Set Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_ID();

	public de.metas.tourplanning.model.I_M_Tour getM_Tour();

	public void setM_Tour(de.metas.tourplanning.model.I_M_Tour M_Tour);

    /** Column definition for M_Tour_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, de.metas.tourplanning.model.I_M_Tour> COLUMN_M_Tour_ID = new org.adempiere.model.ModelColumn<I_M_TourVersion, de.metas.tourplanning.model.I_M_Tour>(I_M_TourVersion.class, "M_Tour_ID", de.metas.tourplanning.model.I_M_Tour.class);
    /** Column name M_Tour_ID */
    public static final String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Tour Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_TourVersion_ID (int M_TourVersion_ID);

	/**
	 * Get Tour Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_TourVersion_ID();

    /** Column definition for M_TourVersion_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_M_TourVersion_ID = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "M_TourVersion_ID", null);
    /** Column name M_TourVersion_ID */
    public static final String COLUMNNAME_M_TourVersion_ID = "M_TourVersion_ID";

	/**
	 * Set Tag des Monats.
	 * Tag des Monats 1 bis 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMonthDay (int MonthDay);

	/**
	 * Get Tag des Monats.
	 * Tag des Monats 1 bis 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMonthDay();

    /** Column definition for MonthDay */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_MonthDay = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "MonthDay", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnFriday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnFriday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnMonday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnMonday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnSaturday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnSaturday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnSunday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnSunday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnThursday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnThursday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnTuesday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnTuesday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_OnWednesday = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "OnWednesday", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_1 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_1", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_2 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_2", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_3 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_3", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_4 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_4", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_5 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_5", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_6 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_6", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_PreparationTime_7 = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "PreparationTime_7", null);
    /** Column name PreparationTime_7 */
    public static final String COLUMNNAME_PreparationTime_7 = "PreparationTime_7";

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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_TourVersion, org.compiere.model.I_AD_User>(I_M_TourVersion.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersion, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_TourVersion, Object>(I_M_TourVersion.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
