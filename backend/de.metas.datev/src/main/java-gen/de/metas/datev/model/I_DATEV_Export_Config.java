package de.metas.datev.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for DATEV_Export_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DATEV_Export_Config 
{

	String Table_Name = "DATEV_Export_Config";

//	/** AD_Table_ID=542597 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Beraternummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAdvisorNumber (java.lang.String AdvisorNumber);

	/**
	 * Get Beraternummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAdvisorNumber();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_AdvisorNumber = new ModelColumn<>(I_DATEV_Export_Config.class, "AdvisorNumber", null);
	String COLUMNNAME_AdvisorNumber = "AdvisorNumber";

	/**
	 * Set Sachkontenrahmen.
	 * Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChartOfAccounts (java.lang.String ChartOfAccounts);

	/**
	 * Get Sachkontenrahmen.
	 * Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getChartOfAccounts();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_ChartOfAccounts = new ModelColumn<>(I_DATEV_Export_Config.class, "ChartOfAccounts", null);
	String COLUMNNAME_ChartOfAccounts = "ChartOfAccounts";

	/**
	 * Set Sachkontenlänge.
	 * Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChartOfAccountsNumberLength (int ChartOfAccountsNumberLength);

	/**
	 * Get Sachkontenlänge.
	 * Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getChartOfAccountsNumberLength();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_ChartOfAccountsNumberLength = new ModelColumn<>(I_DATEV_Export_Config.class, "ChartOfAccountsNumberLength", null);
	String COLUMNNAME_ChartOfAccountsNumberLength = "ChartOfAccountsNumberLength";

	/**
	 * Set ClientNumber.
	 * Bereich 1-99999
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClientNumber (java.lang.String ClientNumber);

	/**
	 * Get ClientNumber.
	 * Bereich 1-99999
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClientNumber();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_ClientNumber = new ModelColumn<>(I_DATEV_Export_Config.class, "ClientNumber", null);
	String COLUMNNAME_ClientNumber = "ClientNumber";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_Created = new ModelColumn<>(I_DATEV_Export_Config.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DATEV Export Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDATEV_Export_Config_ID (int DATEV_Export_Config_ID);

	/**
	 * Get DATEV Export Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDATEV_Export_Config_ID();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_DATEV_Export_Config_ID = new ModelColumn<>(I_DATEV_Export_Config.class, "DATEV_Export_Config_ID", null);
	String COLUMNNAME_DATEV_Export_Config_ID = "DATEV_Export_Config_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_DATEV_Export_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DATEV_Export_Config, Object> COLUMN_Updated = new ModelColumn<>(I_DATEV_Export_Config.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
