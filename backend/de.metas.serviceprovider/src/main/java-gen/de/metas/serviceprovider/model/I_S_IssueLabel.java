package de.metas.serviceprovider.model;


/** Generated Interface for S_IssueLabel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_IssueLabel 
{

    /** TableName=S_IssueLabel */
    public static final String Table_Name = "S_IssueLabel";

    /** AD_Table_ID=541467 */
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
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_IssueLabel, Object>(I_S_IssueLabel.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_IssueLabel, Object>(I_S_IssueLabel.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Label.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLabel (java.lang.String Label);

	/**
	 * Get Label.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLabel();

    /** Column definition for Label */
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, Object> COLUMN_Label = new org.adempiere.model.ModelColumn<I_S_IssueLabel, Object>(I_S_IssueLabel.class, "Label", null);
    /** Column name Label */
    public static final String COLUMNNAME_Label = "Label";

	/**
	 * Set Issue.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_Issue_ID();

	public de.metas.serviceprovider.model.I_S_Issue getS_Issue();

	public void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue);

    /** Column definition for S_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Issue_ID = new org.adempiere.model.ModelColumn<I_S_IssueLabel, de.metas.serviceprovider.model.I_S_Issue>(I_S_IssueLabel.class, "S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
    /** Column name S_Issue_ID */
    public static final String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set Issue label ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_IssueLabel_ID (int S_IssueLabel_ID);

	/**
	 * Get Issue label ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_IssueLabel_ID();

    /** Column definition for S_IssueLabel_ID */
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, Object> COLUMN_S_IssueLabel_ID = new org.adempiere.model.ModelColumn<I_S_IssueLabel, Object>(I_S_IssueLabel.class, "S_IssueLabel_ID", null);
    /** Column name S_IssueLabel_ID */
    public static final String COLUMNNAME_S_IssueLabel_ID = "S_IssueLabel_ID";

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
    public static final org.adempiere.model.ModelColumn<I_S_IssueLabel, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_IssueLabel, Object>(I_S_IssueLabel.class, "Updated", null);
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
