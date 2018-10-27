package org.compiere.model;


/** Generated Interface for AD_Sequence
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Sequence 
{

    /** TableName=AD_Sequence */
    public static final String Table_Name = "AD_Sequence";

    /** AD_Table_ID=115 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_Client>(I_AD_Sequence.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_Org>(I_AD_Sequence.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Reihenfolge.
	 * Document Sequence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Reihenfolge.
	 * Document Sequence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Sequence_ID();

    /** Column definition for AD_Sequence_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_AD_Sequence_ID = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "AD_Sequence_ID", null);
    /** Column name AD_Sequence_ID */
    public static final String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_User>(I_AD_Sequence.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktuell nächster Wert.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrentNext (int CurrentNext);

	/**
	 * Get Aktuell nächster Wert.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCurrentNext();

    /** Column definition for CurrentNext */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_CurrentNext = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "CurrentNext", null);
    /** Column name CurrentNext */
    public static final String COLUMNNAME_CurrentNext = "CurrentNext";

	/**
	 * Set Current Next (System).
	 * Next sequence for system use
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrentNextSys (int CurrentNextSys);

	/**
	 * Get Current Next (System).
	 * Next sequence for system use
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCurrentNextSys();

    /** Column definition for CurrentNextSys */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_CurrentNextSys = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "CurrentNextSys", null);
    /** Column name CurrentNextSys */
    public static final String COLUMNNAME_CurrentNextSys = "CurrentNextSys";

	/**
	 * Set Abw. Sequenznummer-Implementierung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomSequenceNoProvider_JavaClass_ID (int CustomSequenceNoProvider_JavaClass_ID);

	/**
	 * Get Abw. Sequenznummer-Implementierung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCustomSequenceNoProvider_JavaClass_ID();

    /** Column definition for CustomSequenceNoProvider_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_CustomSequenceNoProvider_JavaClass_ID = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "CustomSequenceNoProvider_JavaClass_ID", null);
    /** Column name CustomSequenceNoProvider_JavaClass_ID */
    public static final String COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID = "CustomSequenceNoProvider_JavaClass_ID";

	/**
	 * Set Date Column.
	 * Fully qualified date column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateColumn (java.lang.String DateColumn);

	/**
	 * Get Date Column.
	 * Fully qualified date column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDateColumn();

    /** Column definition for DateColumn */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_DateColumn = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "DateColumn", null);
    /** Column name DateColumn */
    public static final String COLUMNNAME_DateColumn = "DateColumn";

	/**
	 * Set Decimal Pattern.
	 * Java Decimal Pattern
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDecimalPattern (java.lang.String DecimalPattern);

	/**
	 * Get Decimal Pattern.
	 * Java Decimal Pattern
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDecimalPattern();

    /** Column definition for DecimalPattern */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_DecimalPattern = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "DecimalPattern", null);
    /** Column name DecimalPattern */
    public static final String COLUMNNAME_DecimalPattern = "DecimalPattern";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Increment.
	 * The number to increment the last document number by
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIncrementNo (int IncrementNo);

	/**
	 * Get Increment.
	 * The number to increment the last document number by
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getIncrementNo();

    /** Column definition for IncrementNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_IncrementNo = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "IncrementNo", null);
    /** Column name IncrementNo */
    public static final String COLUMNNAME_IncrementNo = "IncrementNo";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Activate Audit.
	 * Activate Audit Trail of what numbers are generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAudited (boolean IsAudited);

	/**
	 * Get Activate Audit.
	 * Activate Audit Trail of what numbers are generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAudited();

    /** Column definition for IsAudited */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_IsAudited = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "IsAudited", null);
    /** Column name IsAudited */
    public static final String COLUMNNAME_IsAudited = "IsAudited";

	/**
	 * Set Automatische Nummerierung.
	 * Automatically assign the next number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoSequence (boolean IsAutoSequence);

	/**
	 * Get Automatische Nummerierung.
	 * Automatically assign the next number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoSequence();

    /** Column definition for IsAutoSequence */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_IsAutoSequence = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "IsAutoSequence", null);
    /** Column name IsAutoSequence */
    public static final String COLUMNNAME_IsAutoSequence = "IsAutoSequence";

	/**
	 * Set Used for Record ID.
	 * The document number  will be used as the record key
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsTableID (boolean IsTableID);

	/**
	 * Get Used for Record ID.
	 * The document number  will be used as the record key
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isTableID();

    /** Column definition for IsTableID */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_IsTableID = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "IsTableID", null);
    /** Column name IsTableID */
    public static final String COLUMNNAME_IsTableID = "IsTableID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Prefix.
	 * Prefix before the sequence number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrefix (java.lang.String Prefix);

	/**
	 * Get Prefix.
	 * Prefix before the sequence number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrefix();

    /** Column definition for Prefix */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Prefix = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Prefix", null);
    /** Column name Prefix */
    public static final String COLUMNNAME_Prefix = "Prefix";

	/**
	 * Set Nummernfolge jedes Jahr neu beginnen.
	 * Restart the sequence with Start on every 1/1
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartNewYear (boolean StartNewYear);

	/**
	 * Get Nummernfolge jedes Jahr neu beginnen.
	 * Restart the sequence with Start on every 1/1
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isStartNewYear();

    /** Column definition for StartNewYear */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_StartNewYear = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "StartNewYear", null);
    /** Column name StartNewYear */
    public static final String COLUMNNAME_StartNewYear = "StartNewYear";

	/**
	 * Set Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStartNo (int StartNo);

	/**
	 * Get Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getStartNo();

    /** Column definition for StartNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_StartNo = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "StartNo", null);
    /** Column name StartNo */
    public static final String COLUMNNAME_StartNo = "StartNo";

	/**
	 * Set Suffix.
	 * Suffix after the number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSuffix (java.lang.String Suffix);

	/**
	 * Get Suffix.
	 * Suffix after the number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSuffix();

    /** Column definition for Suffix */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Suffix = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Suffix", null);
    /** Column name Suffix */
    public static final String COLUMNNAME_Suffix = "Suffix";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Sequence, org.compiere.model.I_AD_User>(I_AD_Sequence.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVFormat (java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVFormat();

    /** Column definition for VFormat */
    public static final org.adempiere.model.ModelColumn<I_AD_Sequence, Object> COLUMN_VFormat = new org.adempiere.model.ModelColumn<I_AD_Sequence, Object>(I_AD_Sequence.class, "VFormat", null);
    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";
}
