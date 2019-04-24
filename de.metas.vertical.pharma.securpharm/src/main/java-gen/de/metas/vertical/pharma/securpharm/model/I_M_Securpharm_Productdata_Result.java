package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Productdata_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Productdata_Result 
{

    /** TableName=M_Securpharm_Productdata_Result */
	String Table_Name = "M_Securpharm_Productdata_Result";

    /** AD_Table_ID=541349 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */
    /** Column definition for AD_Client_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Client>(I_M_Securpharm_Productdata_Result.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    /** Column definition for AD_Org_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_Org>(I_M_Securpharm_Productdata_Result.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    /** Column definition for Created */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "Created", null);
    /** Column name Created */
	String COLUMNNAME_Created = "Created";
    /** Column definition for CreatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Productdata_Result.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
	String COLUMNNAME_CreatedBy = "CreatedBy";
    /** Column definition for ExpirationDate */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ExpirationDate = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ExpirationDate", null);
    /** Column name ExpirationDate */
	String COLUMNNAME_ExpirationDate = "ExpirationDate";
    /** Column definition for hasActiveStatus */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_hasActiveStatus = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "hasActiveStatus", null);
    /** Column name hasActiveStatus */
	String COLUMNNAME_hasActiveStatus = "hasActiveStatus";
    /** Column definition for InactiveReason */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_InactiveReason = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "InactiveReason", null);
    /** Column name InactiveReason */
	String COLUMNNAME_InactiveReason = "InactiveReason";
    /** Column definition for IsActive */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsActive", null);
    /** Column name IsActive */
	String COLUMNNAME_IsActive = "IsActive";
    /** Column definition for IsError */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "IsError", null);
    /** Column name IsError */
	String COLUMNNAME_IsError = "IsError";
    /** Column definition for LotNumber */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_LotNumber = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "LotNumber", null);
    /** Column name LotNumber */
	String COLUMNNAME_LotNumber = "LotNumber";
    /** Column definition for M_HU_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
	String COLUMNNAME_M_HU_ID = "M_HU_ID";
    /** Column definition for M_Securpharm_Productdata_Result_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_M_Securpharm_Productdata_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "M_Securpharm_Productdata_Result_ID", null);
    /** Column name M_Securpharm_Productdata_Result_ID */
	String COLUMNNAME_M_Securpharm_Productdata_Result_ID = "M_Securpharm_Productdata_Result_ID";
    /** Column definition for ProductCode */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ProductCode = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ProductCode", null);
    /** Column name ProductCode */
	String COLUMNNAME_ProductCode = "ProductCode";
    /** Column definition for ProductCodeType */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_ProductCodeType = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "ProductCodeType", null);
    /** Column name ProductCodeType */
	String COLUMNNAME_ProductCodeType = "ProductCodeType";
    /** Column definition for RequestEndTime */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_RequestEndTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "RequestEndTime", null);
    /** Column name RequestEndTime */
	String COLUMNNAME_RequestEndTime = "RequestEndTime";
    /** Column definition for RequestStartTime */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_RequestStartTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "RequestStartTime", null);
    /** Column name RequestStartTime */
	String COLUMNNAME_RequestStartTime = "RequestStartTime";
    /** Column definition for RequestUrl */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_RequestUrl = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "RequestUrl", null);
    /** Column name RequestUrl */
	String COLUMNNAME_RequestUrl = "RequestUrl";
    /** Column definition for SerialNumber */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_SerialNumber = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "SerialNumber", null);
    /** Column name SerialNumber */
	String COLUMNNAME_SerialNumber = "SerialNumber";
    /** Column definition for TransactionIDClient */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_TransactionIDClient = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "TransactionIDClient", null);
    /** Column name TransactionIDClient */
	String COLUMNNAME_TransactionIDClient = "TransactionIDClient";
    /** Column definition for TransactionIDServer */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_TransactionIDServer = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "TransactionIDServer", null);
    /** Column name TransactionIDServer */
	String COLUMNNAME_TransactionIDServer = "TransactionIDServer";
    /** Column definition for Updated */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, Object>(I_M_Securpharm_Productdata_Result.class, "Updated", null);
    /** Column name Updated */
	String COLUMNNAME_Updated = "Updated";
    /** Column definition for UpdatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Productdata_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Productdata_Result.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	/**
	 * Get Mindesthaltbarkeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getExpirationDate();

	/**
	 * Set Mindesthaltbarkeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpirationDate(java.sql.Timestamp ExpirationDate);

	/**
	 * Set Aktiv Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void sethasActiveStatus(boolean hasActiveStatus);

	/**
	 * Get Aktiv Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean ishasActiveStatus();

	/**
	 * Get Inaktiv Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getInactiveReason();

	/**
	 * Set Inaktiv Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInactiveReason(java.lang.String InactiveReason);

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError(boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	/**
	 * Get Chargennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getLotNumber();

	/**
	 * Set Chargennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLotNumber(java.lang.String LotNumber);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID(int M_HU_ID);

	/**
	 * Get SecurPharm product data result.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Securpharm_Productdata_Result_ID();

	/**
	 * Set SecurPharm product data result.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Securpharm_Productdata_Result_ID(int M_Securpharm_Productdata_Result_ID);

	/**
	 * Get Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductCode();

	/**
	 * Set Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductCode(java.lang.String ProductCode);

	/**
	 * Get Kodierungskennzeichen.
	 * Kodierungskennzeichen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductCodeType();

	/**
	 * Set Kodierungskennzeichen.
	 * Kodierungskennzeichen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductCodeType(java.lang.String ProductCodeType);

	/**
	 * Get Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRequestEndTime();

	/**
	 * Set Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestEndTime(java.sql.Timestamp RequestEndTime);

	/**
	 * Get Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRequestStartTime();

	/**
	 * Set Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestStartTime(java.sql.Timestamp RequestStartTime);

	/**
	 * Get Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRequestUrl();

	/**
	 * Set Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestUrl(java.lang.String RequestUrl);

	/**
	 * Get Seriennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getSerialNumber();

	/**
	 * Set Seriennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSerialNumber(java.lang.String SerialNumber);

	/**
	 * Get TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTransactionIDClient();

	/**
	 * Set TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTransactionIDClient(java.lang.String TransactionIDClient);

	/**
	 * Get TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getTransactionIDServer();

	/**
	 * Set TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransactionIDServer(java.lang.String TransactionIDServer);

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();
}
