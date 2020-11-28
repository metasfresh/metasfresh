package org.compiere.model;


/** Generated Interface for I_BPartner_GlobalID
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_BPartner_GlobalID 
{

    /** TableName=I_BPartner_GlobalID */
    public static final String Table_Name = "I_BPartner_GlobalID";

    /** AD_Table_ID=541180 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_Client>(I_I_BPartner_GlobalID.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_Org>(I_I_BPartner_GlobalID.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_C_BPartner>(I_I_BPartner_GlobalID.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_User>(I_I_BPartner_GlobalID.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGlobalId (java.lang.String GlobalId);

	/**
	 * Get Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGlobalId();

    /** Column definition for GlobalId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_GlobalId = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "GlobalId", null);
    /** Column name GlobalId */
    public static final String COLUMNNAME_GlobalId = "GlobalId";

	/**
	 * Set Import BPartnerr Global ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_BPartner_GlobalID_ID (int I_BPartner_GlobalID_ID);

	/**
	 * Get Import BPartnerr Global ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_BPartner_GlobalID_ID();

    /** Column definition for I_BPartner_GlobalID_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_I_BPartner_GlobalID_ID = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "I_BPartner_GlobalID_ID", null);
    /** Column name I_BPartner_GlobalID_ID */
    public static final String COLUMNNAME_I_BPartner_GlobalID_ID = "I_BPartner_GlobalID_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, org.compiere.model.I_AD_User>(I_I_BPartner_GlobalID.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL3.
	 * Vollständige Web-Addresse, z.B. https://metasfresh.com/
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL3 (java.lang.String URL3);

	/**
	 * Get URL3.
	 * Vollständige Web-Addresse, z.B. https://metasfresh.com/
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL3();

    /** Column definition for URL3 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object> COLUMN_URL3 = new org.adempiere.model.ModelColumn<I_I_BPartner_GlobalID, Object>(I_I_BPartner_GlobalID.class, "URL3", null);
    /** Column name URL3 */
    public static final String COLUMNNAME_URL3 = "URL3";
}
