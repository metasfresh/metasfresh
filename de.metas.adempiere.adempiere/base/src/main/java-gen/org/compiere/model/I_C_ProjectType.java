package org.compiere.model;


/** Generated Interface for C_ProjectType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_ProjectType 
{

    /** TableName=C_ProjectType */
    public static final String Table_Name = "C_ProjectType";

    /** AD_Table_ID=575 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Client>(I_C_ProjectType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Org>(I_C_ProjectType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Projekt-Nummernfolge.
	 * Nummernfolge für Projekt-Suchschlüssel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Sequence_ProjectValue_ID (int AD_Sequence_ProjectValue_ID);

	/**
	 * Get Projekt-Nummernfolge.
	 * Nummernfolge für Projekt-Suchschlüssel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Sequence_ProjectValue_ID();

	public org.compiere.model.I_AD_Sequence getAD_Sequence_ProjectValue();

	public void setAD_Sequence_ProjectValue(org.compiere.model.I_AD_Sequence AD_Sequence_ProjectValue);

    /** Column definition for AD_Sequence_ProjectValue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ProjectValue_ID = new org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Sequence>(I_C_ProjectType.class, "AD_Sequence_ProjectValue_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name AD_Sequence_ProjectValue_ID */
    public static final String COLUMNNAME_AD_Sequence_ProjectValue_ID = "AD_Sequence_ProjectValue_ID";

	/**
	 * Set Projektart.
	 * Type of the project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectType_ID (int C_ProjectType_ID);

	/**
	 * Get Projektart.
	 * Type of the project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectType_ID();

    /** Column definition for C_ProjectType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_C_ProjectType_ID = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "C_ProjectType_ID", null);
    /** Column name C_ProjectType_ID */
    public static final String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_User>(I_C_ProjectType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProjectCategory (java.lang.String ProjectCategory);

	/**
	 * Get Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProjectCategory();

    /** Column definition for ProjectCategory */
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_ProjectCategory = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "ProjectCategory", null);
    /** Column name ProjectCategory */
    public static final String COLUMNNAME_ProjectCategory = "ProjectCategory";

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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_ProjectType, Object>(I_C_ProjectType.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_User>(I_C_ProjectType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
