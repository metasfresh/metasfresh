package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CompensationGroup_ContractSettings_DocType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CompensationGroup_ContractSettings_DocType 
{

	String Table_Name = "C_CompensationGroup_ContractSettings_DocType";

//	/** AD_Table_ID=542651 */
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
	 * Set Compensation group contract settings document type.
	 * Sales or purchase document type this compensation group contract applies to.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_DocType_ID (int C_CompensationGroup_ContractSettings_DocType_ID);

	/**
	 * Get Compensation group contract settings document type.
	 * Sales or purchase document type this compensation group contract applies to.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_DocType_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_C_CompensationGroup_ContractSettings_DocType_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "C_CompensationGroup_ContractSettings_DocType_ID", null);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_DocType_ID = "C_CompensationGroup_ContractSettings_DocType_ID";

	/**
	 * Set Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_ID (int C_CompensationGroup_ContractSettings_ID);

	/**
	 * Get Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, de.metas.contracts.model.I_C_CompensationGroup_ContractSettings> COLUMN_C_CompensationGroup_ContractSettings_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "C_CompensationGroup_ContractSettings_ID", de.metas.contracts.model.I_C_CompensationGroup_ContractSettings.class);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_ID = "C_CompensationGroup_ContractSettings_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_Created = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "Created", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "CreatedBy", null);
	String COLUMNNAME_CreatedBy = "CreatedBy";

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

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "IsActive", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_Updated = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "Updated", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_DocType, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_DocType.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
