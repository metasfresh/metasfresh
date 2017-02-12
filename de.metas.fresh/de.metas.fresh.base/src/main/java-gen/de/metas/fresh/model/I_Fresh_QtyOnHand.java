package de.metas.fresh.model;


/** Generated Interface for Fresh_QtyOnHand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fresh_QtyOnHand 
{

    /** TableName=Fresh_QtyOnHand */
    public static final String Table_Name = "Fresh_QtyOnHand";

    /** AD_Table_ID=540634 */
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_Client>(I_Fresh_QtyOnHand.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_Org>(I_Fresh_QtyOnHand.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_User>(I_Fresh_QtyOnHand.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Zählbestand Einkauf (fresh).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID);

	/**
	 * Get Zählbestand Einkauf (fresh).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFresh_QtyOnHand_ID();

    /** Column definition for Fresh_QtyOnHand_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_Fresh_QtyOnHand_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "Fresh_QtyOnHand_ID", null);
    /** Column name Fresh_QtyOnHand_ID */
    public static final String COLUMNNAME_Fresh_QtyOnHand_ID = "Fresh_QtyOnHand_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, Object>(I_Fresh_QtyOnHand.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand, org.compiere.model.I_AD_User>(I_Fresh_QtyOnHand.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
