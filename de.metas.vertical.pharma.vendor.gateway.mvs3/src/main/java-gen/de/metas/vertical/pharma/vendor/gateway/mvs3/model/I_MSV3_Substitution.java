package de.metas.vertical.pharma.vendor.gateway.mvs3.model;


/** Generated Interface for MSV3_Substitution
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_Substitution 
{

    /** TableName=MSV3_Substitution */
    public static final String Table_Name = "MSV3_Substitution";

    /** AD_Table_ID=540908 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_Client>(I_MSV3_Substitution.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_Org>(I_MSV3_Substitution.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_User>(I_MSV3_Substitution.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Grund (java.lang.String MSV3_Grund);

	/**
	 * Get Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Grund();

    /** Column definition for MSV3_Grund */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_MSV3_Grund = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "MSV3_Grund", null);
    /** Column name MSV3_Grund */
    public static final String COLUMNNAME_MSV3_Grund = "MSV3_Grund";

	/**
	 * Set MSV3_LieferPzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_LieferPzn (java.lang.String MSV3_LieferPzn);

	/**
	 * Get MSV3_LieferPzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_LieferPzn();

    /** Column definition for MSV3_LieferPzn */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_MSV3_LieferPzn = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "MSV3_LieferPzn", null);
    /** Column name MSV3_LieferPzn */
    public static final String COLUMNNAME_MSV3_LieferPzn = "MSV3_LieferPzn";

	/**
	 * Set Substitution.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Substitution_ID (int MSV3_Substitution_ID);

	/**
	 * Get Substitution.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Substitution_ID();

    /** Column definition for MSV3_Substitution_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_MSV3_Substitution_ID = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "MSV3_Substitution_ID", null);
    /** Column name MSV3_Substitution_ID */
    public static final String COLUMNNAME_MSV3_Substitution_ID = "MSV3_Substitution_ID";

	/**
	 * Set Substitutionsgrund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Substitutionsgrund (java.lang.String MSV3_Substitutionsgrund);

	/**
	 * Get Substitutionsgrund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Substitutionsgrund();

    /** Column definition for MSV3_Substitutionsgrund */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_MSV3_Substitutionsgrund = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "MSV3_Substitutionsgrund", null);
    /** Column name MSV3_Substitutionsgrund */
    public static final String COLUMNNAME_MSV3_Substitutionsgrund = "MSV3_Substitutionsgrund";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, Object>(I_MSV3_Substitution.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Substitution, org.compiere.model.I_AD_User>(I_MSV3_Substitution.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
