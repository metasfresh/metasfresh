package de.metas.document.archive.model;


/** Generated Interface for C_Doc_Outbound_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Doc_Outbound_Config 
{

    /** TableName=C_Doc_Outbound_Config */
    public static final String Table_Name = "C_Doc_Outbound_Config";

    /** AD_Table_ID=540434 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Client>(I_C_Doc_Outbound_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Org>(I_C_Doc_Outbound_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

    /** Column definition for AD_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_PrintFormat>(I_C_Doc_Outbound_Config.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_Table>(I_C_Doc_Outbound_Config.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set CC Pfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCCPath (java.lang.String CCPath);

	/**
	 * Get CC Pfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCCPath();

    /** Column definition for CCPath */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_CCPath = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "CCPath", null);
    /** Column name CCPath */
    public static final String COLUMNNAME_CCPath = "CCPath";

	/**
	 * Set Ausgehende Belege Konfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Doc_Outbound_Config_ID (int C_Doc_Outbound_Config_ID);

	/**
	 * Get Ausgehende Belege Konfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Doc_Outbound_Config_ID();

    /** Column definition for C_Doc_Outbound_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_C_Doc_Outbound_Config_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "C_Doc_Outbound_Config_ID", null);
    /** Column name C_Doc_Outbound_Config_ID */
    public static final String COLUMNNAME_C_Doc_Outbound_Config_ID = "C_Doc_Outbound_Config_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
