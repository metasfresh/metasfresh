package org.compiere.model;


/** Generated Interface for AD_User_SaveCustomInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_User_SaveCustomInfo 
{

    /** TableName=AD_User_SaveCustomInfo */
    public static final String Table_Name = "AD_User_SaveCustomInfo";

    /** AD_Table_ID=540280 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_Client>(I_AD_User_SaveCustomInfo.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_Org>(I_AD_User_SaveCustomInfo.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set User Save Custom Info.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_SaveCustomInfo_ID (int AD_User_SaveCustomInfo_ID);

	/**
	 * Get User Save Custom Info.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_SaveCustomInfo_ID();

    /** Column definition for AD_User_SaveCustomInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User_SaveCustomInfo> COLUMN_AD_User_SaveCustomInfo_ID = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User_SaveCustomInfo>(I_AD_User_SaveCustomInfo.class, "AD_User_SaveCustomInfo_ID", org.compiere.model.I_AD_User_SaveCustomInfo.class);
    /** Column name AD_User_SaveCustomInfo_ID */
    public static final String COLUMNNAME_AD_User_SaveCustomInfo_ID = "AD_User_SaveCustomInfo_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_C_Country>(I_AD_User_SaveCustomInfo.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCaptureSequence (java.lang.String CaptureSequence);

	/**
	 * Get Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCaptureSequence();

    /** Column definition for CaptureSequence */
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object> COLUMN_CaptureSequence = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object>(I_AD_User_SaveCustomInfo.class, "CaptureSequence", null);
    /** Column name CaptureSequence */
    public static final String COLUMNNAME_CaptureSequence = "CaptureSequence";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object>(I_AD_User_SaveCustomInfo.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User>(I_AD_User_SaveCustomInfo.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object>(I_AD_User_SaveCustomInfo.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, Object>(I_AD_User_SaveCustomInfo.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_User_SaveCustomInfo, org.compiere.model.I_AD_User>(I_AD_User_SaveCustomInfo.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
