package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Flatrate_RefundConfig_PackingOption
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_RefundConfig_PackingOption 
{

	String Table_Name = "C_Flatrate_RefundConfig_PackingOption";

//	/** AD_Table_ID=542654 */
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
	 * Set Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "C_Flatrate_Conditions_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Flatrate Refund Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID);

	/**
	 * Get Flatrate Refund Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_RefundConfig_ID();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_C_Flatrate_RefundConfig_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "C_Flatrate_RefundConfig_ID", null);
	String COLUMNNAME_C_Flatrate_RefundConfig_ID = "C_Flatrate_RefundConfig_ID";

	/**
	 * Set Refund packaging option.
	 * Packaging a refund condition applies to.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_RefundConfig_PackingOption_ID (int C_Flatrate_RefundConfig_PackingOption_ID);

	/**
	 * Get Refund packaging option.
	 * Packaging a refund condition applies to.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_RefundConfig_PackingOption_ID();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_C_Flatrate_RefundConfig_PackingOption_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "C_Flatrate_RefundConfig_PackingOption_ID", null);
	String COLUMNNAME_C_Flatrate_RefundConfig_PackingOption_ID = "C_Flatrate_RefundConfig_PackingOption_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "Created", null);
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

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "CreatedBy", null);
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

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Packing Material.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packing Material.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackingMaterial_ID();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_M_HU_PackingMaterial_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "M_HU_PackingMaterial_ID", null);
	String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "Updated", null);
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

	ModelColumn<I_C_Flatrate_RefundConfig_PackingOption, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_Flatrate_RefundConfig_PackingOption.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
