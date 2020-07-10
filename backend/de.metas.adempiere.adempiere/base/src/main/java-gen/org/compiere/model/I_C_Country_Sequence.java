package org.compiere.model;


/** Generated Interface for C_Country_Sequence
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Country_Sequence 
{

    /** TableName=C_Country_Sequence */
    public static final String Table_Name = "C_Country_Sequence";

    /** AD_Table_ID=540805 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_Client>(I_C_Country_Sequence.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_Org>(I_C_Country_Sequence.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_C_Country>(I_C_Country_Sequence.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Country Sequence.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_Sequence_ID (int C_Country_Sequence_ID);

	/**
	 * Get Country Sequence.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_Sequence_ID();

    /** Column definition for C_Country_Sequence_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_C_Country_Sequence> COLUMN_C_Country_Sequence_ID = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_C_Country_Sequence>(I_C_Country_Sequence.class, "C_Country_Sequence_ID", org.compiere.model.I_C_Country_Sequence.class);
    /** Column name C_Country_Sequence_ID */
    public static final String COLUMNNAME_C_Country_Sequence_ID = "C_Country_Sequence_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_User>(I_C_Country_Sequence.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Adress-Druckformat.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplaySequence (java.lang.String DisplaySequence);

	/**
	 * Get Adress-Druckformat.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplaySequence();

    /** Column definition for DisplaySequence */
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_DisplaySequence = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "DisplaySequence", null);
    /** Column name DisplaySequence */
    public static final String COLUMNNAME_DisplaySequence = "DisplaySequence";

	/**
	 * Set Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplaySequenceLocal (java.lang.String DisplaySequenceLocal);

	/**
	 * Get Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplaySequenceLocal();

    /** Column definition for DisplaySequenceLocal */
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_DisplaySequenceLocal = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "DisplaySequenceLocal", null);
    /** Column name DisplaySequenceLocal */
    public static final String COLUMNNAME_DisplaySequenceLocal = "DisplaySequenceLocal";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, Object>(I_C_Country_Sequence.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Country_Sequence, org.compiere.model.I_AD_User>(I_C_Country_Sequence.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
