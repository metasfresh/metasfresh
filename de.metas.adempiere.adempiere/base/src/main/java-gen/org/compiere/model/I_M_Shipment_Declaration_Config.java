package org.compiere.model;


/** Generated Interface for M_Shipment_Declaration_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Shipment_Declaration_Config 
{

    /** TableName=M_Shipment_Declaration_Config */
    public static final String Table_Name = "M_Shipment_Declaration_Config";

    /** AD_Table_ID=541353 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_Client>(I_M_Shipment_Declaration_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_Org>(I_M_Shipment_Declaration_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Belegart korrektur.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_Correction_ID (int C_DocType_Correction_ID);

	/**
	 * Get Belegart korrektur.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_Correction_ID();

	public org.compiere.model.I_C_DocType getC_DocType_Correction();

	public void setC_DocType_Correction(org.compiere.model.I_C_DocType C_DocType_Correction);

    /** Column definition for C_DocType_Correction_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_C_DocType> COLUMN_C_DocType_Correction_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_C_DocType>(I_M_Shipment_Declaration_Config.class, "C_DocType_Correction_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_Correction_ID */
    public static final String COLUMNNAME_C_DocType_Correction_ID = "C_DocType_Correction_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_C_DocType>(I_M_Shipment_Declaration_Config.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_User>(I_M_Shipment_Declaration_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DocumentLinesNumber.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentLinesNumber (int DocumentLinesNumber);

	/**
	 * Get DocumentLinesNumber.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDocumentLinesNumber();

    /** Column definition for DocumentLinesNumber */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_DocumentLinesNumber = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "DocumentLinesNumber", null);
    /** Column name DocumentLinesNumber */
    public static final String COLUMNNAME_DocumentLinesNumber = "DocumentLinesNumber";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abgabemeldung Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipment_Declaration_Config_ID (int M_Shipment_Declaration_Config_ID);

	/**
	 * Get Abgabemeldung Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipment_Declaration_Config_ID();

    /** Column definition for M_Shipment_Declaration_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_M_Shipment_Declaration_Config_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "M_Shipment_Declaration_Config_ID", null);
    /** Column name M_Shipment_Declaration_Config_ID */
    public static final String COLUMNNAME_M_Shipment_Declaration_Config_ID = "M_Shipment_Declaration_Config_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, Object>(I_M_Shipment_Declaration_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Shipment_Declaration_Config, org.compiere.model.I_AD_User>(I_M_Shipment_Declaration_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
