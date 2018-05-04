package de.metas.shipper.gateway.derkurier.model;


/** Generated Interface for DerKurier_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DerKurier_Shipper_Config 
{

    /** TableName=DerKurier_Shipper_Config */
    public static final String Table_Name = "DerKurier_Shipper_Config";

    /** AD_Table_ID=540965 */
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Client>(I_DerKurier_Shipper_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Mail Box.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_MailBox_ID (int AD_MailBox_ID);

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_MailBox_ID();

    /** Column definition for AD_MailBox_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_AD_MailBox_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "AD_MailBox_ID", null);
    /** Column name AD_MailBox_ID */
    public static final String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Org>(I_DerKurier_Shipper_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Reihenfolge.
	 * Nummernfolgen für Belege
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Reihenfolge.
	 * Nummernfolgen für Belege
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Sequence_ID();

	public org.compiere.model.I_AD_Sequence getAD_Sequence();

	public void setAD_Sequence(org.compiere.model.I_AD_Sequence AD_Sequence);

    /** Column definition for AD_Sequence_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_Sequence>(I_DerKurier_Shipper_Config.class, "AD_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name AD_Sequence_ID */
    public static final String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Set REST API Server BaseURL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAPIServerBaseURL (java.lang.String APIServerBaseURL);

	/**
	 * Get REST API Server BaseURL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAPIServerBaseURL();

    /** Column definition for APIServerBaseURL */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_APIServerBaseURL = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "APIServerBaseURL", null);
    /** Column name APIServerBaseURL */
    public static final String COLUMNNAME_APIServerBaseURL = "APIServerBaseURL";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_User>(I_DerKurier_Shipper_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DerKurier Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDerKurier_Shipper_Config_ID (int DerKurier_Shipper_Config_ID);

	/**
	 * Get DerKurier Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDerKurier_Shipper_Config_ID();

    /** Column definition for DerKurier_Shipper_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_DerKurier_Shipper_Config_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "DerKurier_Shipper_Config_ID", null);
    /** Column name DerKurier_Shipper_Config_ID */
    public static final String COLUMNNAME_DerKurier_Shipper_Config_ID = "DerKurier_Shipper_Config_ID";

	/**
	 * Set Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_CustomerNumber (java.lang.String DK_CustomerNumber);

	/**
	 * Get Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_CustomerNumber();

    /** Column definition for DK_CustomerNumber */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_DK_CustomerNumber = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "DK_CustomerNumber", null);
    /** Column name DK_CustomerNumber */
    public static final String COLUMNNAME_DK_CustomerNumber = "DK_CustomerNumber";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_M_Shipper>(I_DerKurier_Shipper_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, Object>(I_DerKurier_Shipper_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_Shipper_Config, org.compiere.model.I_AD_User>(I_DerKurier_Shipper_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
