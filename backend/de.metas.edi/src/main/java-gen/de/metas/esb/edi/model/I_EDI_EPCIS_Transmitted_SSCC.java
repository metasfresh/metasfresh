package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for EDI_EPCIS_Transmitted_SSCC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_EPCIS_Transmitted_SSCC 
{

	String Table_Name = "EDI_EPCIS_Transmitted_SSCC";

//	/** AD_Table_ID=542624 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_Created = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "Created", null);
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
	 * Set EDI EPCIS Transmitted SSCC.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_EPCIS_Transmitted_SSCC_ID (int EDI_EPCIS_Transmitted_SSCC_ID);

	/**
	 * Get EDI EPCIS Transmitted SSCC.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_EPCIS_Transmitted_SSCC_ID();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_EDI_EPCIS_Transmitted_SSCC_ID = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "EDI_EPCIS_Transmitted_SSCC_ID", null);
	String COLUMNNAME_EDI_EPCIS_Transmitted_SSCC_ID = "EDI_EPCIS_Transmitted_SSCC_ID";

	/**
	 * Set ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ScriptedExportConversion_ID (int ExternalSystem_Config_ScriptedExportConversion_ID);

	/**
	 * Get ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ScriptedExportConversion_ID();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_ExternalSystem_Config_ScriptedExportConversion_ID = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "ExternalSystem_Config_ScriptedExportConversion_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID = "ExternalSystem_Config_ScriptedExportConversion_ID";

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

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSSCC18 (java.lang.String SSCC18);

	/**
	 * Get SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSSCC18();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_SSCC18 = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "SSCC18", null);
	String COLUMNNAME_SSCC18 = "SSCC18";

	/**
	 * Set Transmitted.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTransmitted (java.sql.Timestamp Transmitted);

	/**
	 * Get Transmitted.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getTransmitted();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_Transmitted = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "Transmitted", null);
	String COLUMNNAME_Transmitted = "Transmitted";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_EPCIS_Transmitted_SSCC, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_EPCIS_Transmitted_SSCC.class, "Updated", null);
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
