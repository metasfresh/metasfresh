package org.compiere.model;


/** Generated Interface for C_Phonecall_Schema_Version_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Phonecall_Schema_Version_Line 
{

    /** TableName=C_Phonecall_Schema_Version_Line */
    public static final String Table_Name = "C_Phonecall_Schema_Version_Line";

    /** AD_Table_ID=541178 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Contact_ID (int C_BP_Contact_ID);

	/**
	 * Get Kontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Contact_ID();

    /** Column name C_BP_Contact_ID */
    public static final String COLUMNNAME_C_BP_Contact_ID = "C_BP_Contact_ID";

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anruf Planung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_ID (int C_Phonecall_Schema_ID);

	/**
	 * Get Anruf Planung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_ID();

	public org.compiere.model.I_C_Phonecall_Schema getC_Phonecall_Schema();

	public void setC_Phonecall_Schema(org.compiere.model.I_C_Phonecall_Schema C_Phonecall_Schema);

    /** Column definition for C_Phonecall_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, org.compiere.model.I_C_Phonecall_Schema> COLUMN_C_Phonecall_Schema_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, org.compiere.model.I_C_Phonecall_Schema>(I_C_Phonecall_Schema_Version_Line.class, "C_Phonecall_Schema_ID", org.compiere.model.I_C_Phonecall_Schema.class);
    /** Column name C_Phonecall_Schema_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_ID = "C_Phonecall_Schema_ID";

	/**
	 * Set Anruf Planung Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID);

	/**
	 * Get Anruf Planung Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_ID();

	public org.compiere.model.I_C_Phonecall_Schema_Version getC_Phonecall_Schema_Version();

	public void setC_Phonecall_Schema_Version(org.compiere.model.I_C_Phonecall_Schema_Version C_Phonecall_Schema_Version);

    /** Column definition for C_Phonecall_Schema_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, org.compiere.model.I_C_Phonecall_Schema_Version> COLUMN_C_Phonecall_Schema_Version_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, org.compiere.model.I_C_Phonecall_Schema_Version>(I_C_Phonecall_Schema_Version_Line.class, "C_Phonecall_Schema_Version_ID", org.compiere.model.I_C_Phonecall_Schema_Version.class);
    /** Column name C_Phonecall_Schema_Version_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_Version_ID = "C_Phonecall_Schema_Version_ID";

	/**
	 * Set Anruf Planung Version Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Phonecall_Schema_Version_Line_ID (int C_Phonecall_Schema_Version_Line_ID);

	/**
	 * Get Anruf Planung Version Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Phonecall_Schema_Version_Line_ID();

    /** Column definition for C_Phonecall_Schema_Version_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_C_Phonecall_Schema_Version_Line_ID = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "C_Phonecall_Schema_Version_Line_ID", null);
    /** Column name C_Phonecall_Schema_Version_Line_ID */
    public static final String COLUMNNAME_C_Phonecall_Schema_Version_Line_ID = "C_Phonecall_Schema_Version_Line_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Erreichbar bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPhonecallTimeMax (java.sql.Timestamp PhonecallTimeMax);

	/**
	 * Get Erreichbar bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallTimeMax();

    /** Column definition for PhonecallTimeMax */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_PhonecallTimeMax = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "PhonecallTimeMax", null);
    /** Column name PhonecallTimeMax */
    public static final String COLUMNNAME_PhonecallTimeMax = "PhonecallTimeMax";

	/**
	 * Set Erreichbar von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPhonecallTimeMin (java.sql.Timestamp PhonecallTimeMin);

	/**
	 * Get Erreichbar von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPhonecallTimeMin();

    /** Column definition for PhonecallTimeMin */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_PhonecallTimeMin = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "PhonecallTimeMin", null);
    /** Column name PhonecallTimeMin */
    public static final String COLUMNNAME_PhonecallTimeMin = "PhonecallTimeMin";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Phonecall_Schema_Version_Line, Object>(I_C_Phonecall_Schema_Version_Line.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
