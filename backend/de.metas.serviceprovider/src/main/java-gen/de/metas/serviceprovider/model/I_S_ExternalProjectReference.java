package de.metas.serviceprovider.model;


/** Generated Interface for S_ExternalProjectReference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_ExternalProjectReference 
{

    /** TableName=S_ExternalProjectReference */
    public static final String Table_Name = "S_ExternalProjectReference";

    /** AD_Table_ID=541466 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "Created", null);
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
	 * Set External project owner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalProjectOwner (java.lang.String ExternalProjectOwner);

	/**
	 * Get External project owner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalProjectOwner();

    /** Column definition for ExternalProjectOwner */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalProjectOwner = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "ExternalProjectOwner", null);
    /** Column name ExternalProjectOwner */
    public static final String COLUMNNAME_ExternalProjectOwner = "ExternalProjectOwner";

	/**
	 * Set External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalReference (java.lang.String ExternalReference);

	/**
	 * Get External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalReference();

    /** Column definition for ExternalReference */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalReference = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "ExternalReference", null);
    /** Column name ExternalReference */
    public static final String COLUMNNAME_ExternalReference = "ExternalReference";

	/**
	 * Set ExternalReferenceURL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalReferenceURL (java.lang.String ExternalReferenceURL);

	/**
	 * Get ExternalReferenceURL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalReferenceURL();

    /** Column definition for ExternalReferenceURL */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalReferenceURL = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "ExternalReferenceURL", null);
    /** Column name ExternalReferenceURL */
    public static final String COLUMNNAME_ExternalReferenceURL = "ExternalReferenceURL";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExternalSystem (java.lang.String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalSystem();

    /** Column definition for ExternalSystem */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalSystem = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "ExternalSystem", null);
    /** Column name ExternalSystem */
    public static final String COLUMNNAME_ExternalSystem = "ExternalSystem";

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Project type.
	 * Specifies the type of a project. ( e.g. Budget, Development )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProjectType (java.lang.String ProjectType);

	/**
	 * Get Project type.
	 * Specifies the type of a project. ( e.g. Budget, Development )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProjectType();

    /** Column definition for ProjectType */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ProjectType = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "ProjectType", null);
    /** Column name ProjectType */
    public static final String COLUMNNAME_ProjectType = "ProjectType";

	/**
	 * Set External project reference ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID);

	/**
	 * Get External project reference ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_ExternalProjectReference_ID();

    /** Column definition for S_ExternalProjectReference_ID */
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_S_ExternalProjectReference_ID = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "S_ExternalProjectReference_ID", null);
    /** Column name S_ExternalProjectReference_ID */
    public static final String COLUMNNAME_S_ExternalProjectReference_ID = "S_ExternalProjectReference_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_ExternalProjectReference, Object>(I_S_ExternalProjectReference.class, "Updated", null);
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
