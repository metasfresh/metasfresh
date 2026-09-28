package de.metas.contracts.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CompensationGroup_ContractSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CompensationGroup_ContractSettings 
{

	String Table_Name = "C_CompensationGroup_ContractSettings";

//	/** AD_Table_ID=542650 */
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
	 * Set Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_ID (int C_CompensationGroup_ContractSettings_ID);

	/**
	 * Get Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_C_CompensationGroup_ContractSettings_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "C_CompensationGroup_ContractSettings_ID", null);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_ID = "C_CompensationGroup_ContractSettings_ID";

	/**
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
	String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_Created = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "Created", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "CreatedBy", null);
	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_Description = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_Name = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_Updated = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "Updated", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
