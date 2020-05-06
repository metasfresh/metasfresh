package de.metas.inout.model;


/** Generated Interface for M_QualityNote
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_QualityNote 
{

    /** TableName=M_QualityNote */
    public static final String Table_Name = "M_QualityNote";

    /** AD_Table_ID=540794 */
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_Client>(I_M_QualityNote.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_Org>(I_M_QualityNote.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_User>(I_M_QualityNote.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Qualität-Notiz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_QualityNote_ID (int M_QualityNote_ID);

	/**
	 * Get Qualität-Notiz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_QualityNote_ID();

    /** Column definition for M_QualityNote_ID */
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_M_QualityNote_ID = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "M_QualityNote_ID", null);
    /** Column name M_QualityNote_ID */
    public static final String COLUMNNAME_M_QualityNote_ID = "M_QualityNote_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set PerformanceType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPerformanceType (java.lang.String PerformanceType);

	/**
	 * Get PerformanceType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPerformanceType();

    /** Column definition for PerformanceType */
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_PerformanceType = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "PerformanceType", null);
    /** Column name PerformanceType */
    public static final String COLUMNNAME_PerformanceType = "PerformanceType";

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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_QualityNote, org.compiere.model.I_AD_User>(I_M_QualityNote.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_QualityNote, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_QualityNote, Object>(I_M_QualityNote.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
