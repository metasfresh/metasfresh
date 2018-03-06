package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_FaultInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_FaultInfo 
{

    /** TableName=MSV3_FaultInfo */
    public static final String Table_Name = "MSV3_FaultInfo";

    /** AD_Table_ID=540915 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_Client>(I_MSV3_FaultInfo.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_Org>(I_MSV3_FaultInfo.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_User>(I_MSV3_FaultInfo.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set EndanwenderFehlertext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_EndanwenderFehlertext (java.lang.String MSV3_EndanwenderFehlertext);

	/**
	 * Get EndanwenderFehlertext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_EndanwenderFehlertext();

    /** Column definition for MSV3_EndanwenderFehlertext */
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_MSV3_EndanwenderFehlertext = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "MSV3_EndanwenderFehlertext", null);
    /** Column name MSV3_EndanwenderFehlertext */
    public static final String COLUMNNAME_MSV3_EndanwenderFehlertext = "MSV3_EndanwenderFehlertext";

	/**
	 * Set ErrorCode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_ErrorCode (java.lang.String MSV3_ErrorCode);

	/**
	 * Get ErrorCode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_ErrorCode();

    /** Column definition for MSV3_ErrorCode */
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_MSV3_ErrorCode = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "MSV3_ErrorCode", null);
    /** Column name MSV3_ErrorCode */
    public static final String COLUMNNAME_MSV3_ErrorCode = "MSV3_ErrorCode";

	/**
	 * Set MSV3_FaultInfo.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_FaultInfo_ID (int MSV3_FaultInfo_ID);

	/**
	 * Get MSV3_FaultInfo.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_FaultInfo_ID();

    /** Column definition for MSV3_FaultInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_MSV3_FaultInfo_ID = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "MSV3_FaultInfo_ID", null);
    /** Column name MSV3_FaultInfo_ID */
    public static final String COLUMNNAME_MSV3_FaultInfo_ID = "MSV3_FaultInfo_ID";

	/**
	 * Set FaultInfoType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_FaultInfoType (java.lang.String MSV3_FaultInfoType);

	/**
	 * Get FaultInfoType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_FaultInfoType();

    /** Column definition for MSV3_FaultInfoType */
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_MSV3_FaultInfoType = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "MSV3_FaultInfoType", null);
    /** Column name MSV3_FaultInfoType */
    public static final String COLUMNNAME_MSV3_FaultInfoType = "MSV3_FaultInfoType";

	/**
	 * Set TechnischerFehlertext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_TechnischerFehlertext (java.lang.String MSV3_TechnischerFehlertext);

	/**
	 * Get TechnischerFehlertext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_TechnischerFehlertext();

    /** Column definition for MSV3_TechnischerFehlertext */
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_MSV3_TechnischerFehlertext = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "MSV3_TechnischerFehlertext", null);
    /** Column name MSV3_TechnischerFehlertext */
    public static final String COLUMNNAME_MSV3_TechnischerFehlertext = "MSV3_TechnischerFehlertext";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, Object>(I_MSV3_FaultInfo.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_FaultInfo, org.compiere.model.I_AD_User>(I_MSV3_FaultInfo.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
