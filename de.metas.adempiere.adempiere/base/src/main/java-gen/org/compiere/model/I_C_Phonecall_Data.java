package org.compiere.model;


/** Generated Interface for C_Phonecall_Data
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Phonecall_Data 
{

    /** TableName=C_Phonecall_Data */
    public static final String Table_Name = "C_Phonecall_Data";

    /** AD_Table_ID=541176 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_Client>(I_C_Phonecall_Data.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_Org>(I_C_Phonecall_Data.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User>(I_C_Phonecall_Data.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_BPartner>(I_C_Phonecall_Data.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_BPartner_Location>(I_C_Phonecall_Data.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anruf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Data_ID (int C_Phonecall_Data_ID);

	/**
	 * Get Anruf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Data_ID();

    /** Column definition for C_Phonecall_Data_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_C_Phonecall_Data_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "C_Phonecall_Data_ID", null);
    /** Column name C_Phonecall_Data_ID */
    public static final String COLUMNNAME_C_Phonecall_Data_ID = "C_Phonecall_Data_ID";

	/**
	 * Set Anruflistenversion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID);

	/**
	 * Get Anruflistenversion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_ID();

	public org.compiere.model.I_C_Phonecall_Schema_Version getC_Phonecall_Schema_Version();

	public void setC_Phonecall_Schema_Version(org.compiere.model.I_C_Phonecall_Schema_Version C_Phonecall_Schema_Version);

    /** Column definition for C_Phonecall_Schema_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_Phonecall_Schema_Version> COLUMN_C_Phonecall_Schema_Version_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_C_Phonecall_Schema_Version>(I_C_Phonecall_Data.class, "C_Phonecall_Schema_Version_ID", org.compiere.model.I_C_Phonecall_Schema_Version.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User>(I_C_Phonecall_Data.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManualPhonecall (boolean IsManualPhonecall);

	/**
	 * Get Manuell.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManualPhonecall();

    /** Column definition for IsManualPhonecall */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_IsManualPhonecall = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "IsManualPhonecall", null);
    /** Column name IsManualPhonecall */
    public static final String COLUMNNAME_IsManualPhonecall = "IsManualPhonecall";

	/**
	 * Set Anrufdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhonecallDateTime (java.sql.Timestamp PhonecallDateTime);

	/**
	 * Get Anrufdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallDateTime();

    /** Column definition for PhonecallDateTime */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_PhonecallDateTime = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "PhonecallDateTime", null);
    /** Column name PhonecallDateTime */
    public static final String COLUMNNAME_PhonecallDateTime = "PhonecallDateTime";

	/**
	 * Set Anrufzeit (inkl. Puffer).
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhonecallDateTimeMax (java.sql.Timestamp PhonecallDateTimeMax);

	/**
	 * Get Anrufzeit (inkl. Puffer).
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallDateTimeMax();

    /** Column definition for PhonecallDateTimeMax */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_PhonecallDateTimeMax = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "PhonecallDateTimeMax", null);
    /** Column name PhonecallDateTimeMax */
    public static final String COLUMNNAME_PhonecallDateTimeMax = "PhonecallDateTimeMax";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, Object>(I_C_Phonecall_Data.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Phonecall_Data, org.compiere.model.I_AD_User>(I_C_Phonecall_Data.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
