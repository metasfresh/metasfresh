package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Productdata_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Productdata_Result 
{

    /** TableName=M_Securpharm_Productdata_Result */
    public static final String Table_Name = "M_Securpharm_Productdata_Result";

    /** AD_Table_ID=541349 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktiv Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActiveStatus (java.lang.String ActiveStatus);

	/**
	 * Get Aktiv Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getActiveStatus();

    /** Column definition for ActiveStatus */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ActiveStatus = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ActiveStatus", null);
    /** Column name ActiveStatus */
    public static final String COLUMNNAME_ActiveStatus = "ActiveStatus";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Client>(I_M_Securpharm_Productdata_Result.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Org>(I_M_Securpharm_Productdata_Result.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Productdata_Result.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Decommissioned Server Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDecommissionedServerTransactionId (java.lang.String DecommissionedServerTransactionId);

	/**
	 * Get Decommissioned Server Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDecommissionedServerTransactionId();

    /** Column definition for DecommissionedServerTransactionId */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_DecommissionedServerTransactionId = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "DecommissionedServerTransactionId", null);
    /** Column name DecommissionedServerTransactionId */
    public static final String COLUMNNAME_DecommissionedServerTransactionId = "DecommissionedServerTransactionId";

	/**
	 * Set Mindesthaltbarkeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpirationDate (java.sql.Timestamp ExpirationDate);

	/**
	 * Get Mindesthaltbarkeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getExpirationDate();

    /** Column definition for ExpirationDate */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ExpirationDate = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ExpirationDate", null);
    /** Column name ExpirationDate */
    public static final String COLUMNNAME_ExpirationDate = "ExpirationDate";

	/**
	 * Set Inaktiv Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInactiveReason (java.lang.String InactiveReason);

	/**
	 * Get Inaktiv Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInactiveReason();

    /** Column definition for InactiveReason */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_InactiveReason = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "InactiveReason", null);
    /** Column name InactiveReason */
    public static final String COLUMNNAME_InactiveReason = "InactiveReason";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Decommissioned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDecommissioned (boolean IsDecommissioned);

	/**
	 * Get Decommissioned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDecommissioned();

    /** Column definition for IsDecommissioned */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsDecommissioned = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsDecommissioned", null);
    /** Column name IsDecommissioned */
    public static final String COLUMNNAME_IsDecommissioned = "IsDecommissioned";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Verified Pack.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackVerified (boolean IsPackVerified);

	/**
	 * Get Verified Pack.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackVerified();

    /** Column definition for IsPackVerified */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsPackVerified = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsPackVerified", null);
    /** Column name IsPackVerified */
    public static final String COLUMNNAME_IsPackVerified = "IsPackVerified";

	/**
	 * Set Last Result Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastResultCode (java.lang.String LastResultCode);

	/**
	 * Get Last Result Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastResultCode();

    /** Column definition for LastResultCode */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_LastResultCode = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "LastResultCode", null);
    /** Column name LastResultCode */
    public static final String COLUMNNAME_LastResultCode = "LastResultCode";

	/**
	 * Set Last Result Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastResultMessage (java.lang.String LastResultMessage);

	/**
	 * Get Last Result Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastResultMessage();

    /** Column definition for LastResultMessage */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_LastResultMessage = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "LastResultMessage", null);
    /** Column name LastResultMessage */
    public static final String COLUMNNAME_LastResultMessage = "LastResultMessage";

	/**
	 * Set Chargennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLotNumber (java.lang.String LotNumber);

	/**
	 * Get Chargennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLotNumber();

    /** Column definition for LotNumber */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_LotNumber = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "LotNumber", null);
    /** Column name LotNumber */
    public static final String COLUMNNAME_LotNumber = "LotNumber";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID);

	/**
	 * Get Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Productdata_Result_ID();

    /** Column definition for M_Securpharm_Productdata_Result_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_M_Securpharm_Productdata_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "M_Securpharm_Productdata_Result_ID", null);
    /** Column name M_Securpharm_Productdata_Result_ID */
    public static final String COLUMNNAME_M_Securpharm_Productdata_Result_ID = "M_Securpharm_Productdata_Result_ID";

	/**
	 * Set Verification Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackVerificationCode (java.lang.String PackVerificationCode);

	/**
	 * Get Verification Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackVerificationCode();

    /** Column definition for PackVerificationCode */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_PackVerificationCode = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "PackVerificationCode", null);
    /** Column name PackVerificationCode */
    public static final String COLUMNNAME_PackVerificationCode = "PackVerificationCode";

	/**
	 * Set Verification Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackVerificationMessage (java.lang.String PackVerificationMessage);

	/**
	 * Get Verification Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackVerificationMessage();

    /** Column definition for PackVerificationMessage */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_PackVerificationMessage = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "PackVerificationMessage", null);
    /** Column name PackVerificationMessage */
    public static final String COLUMNNAME_PackVerificationMessage = "PackVerificationMessage";

	/**
	 * Set Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductCode (java.lang.String ProductCode);

	/**
	 * Get Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductCode();

    /** Column definition for ProductCode */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ProductCode = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ProductCode", null);
    /** Column name ProductCode */
    public static final String COLUMNNAME_ProductCode = "ProductCode";

	/**
	 * Set Kodierungskennzeichen.
	 * Kodierungskennzeichen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductCodeType (java.lang.String ProductCodeType);

	/**
	 * Get Kodierungskennzeichen.
	 * Kodierungskennzeichen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductCodeType();

    /** Column definition for ProductCodeType */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ProductCodeType = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ProductCodeType", null);
    /** Column name ProductCodeType */
    public static final String COLUMNNAME_ProductCodeType = "ProductCodeType";

	/**
	 * Set Seriennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerialNumber (java.lang.String SerialNumber);

	/**
	 * Get Seriennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSerialNumber();

    /** Column definition for SerialNumber */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_SerialNumber = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "SerialNumber", null);
    /** Column name SerialNumber */
    public static final String COLUMNNAME_SerialNumber = "SerialNumber";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Productdata_Result.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
