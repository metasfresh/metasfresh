package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PI_GRAI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_PI_GRAI 
{

	String Table_Name = "M_HU_PI_GRAI";

//	/** AD_Table_ID=542611 */
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

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PI_GRAI.class, "Created", null);
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
	 * Set GRAI Asset Type.
	 * Owner's internal crate-model code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGRAI_AssetType (java.lang.String GRAI_AssetType);

	/**
	 * Get GRAI Asset Type.
	 * Owner's internal crate-model code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGRAI_AssetType();

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_GRAI_AssetType = new ModelColumn<>(I_M_HU_PI_GRAI.class, "GRAI_AssetType", null);
	String COLUMNNAME_GRAI_AssetType = "GRAI_AssetType";

	/**
	 * Set GS1 Company Prefix.
	 * GS1 company prefix of the crate owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGRAI_CompanyPrefix (java.lang.String GRAI_CompanyPrefix);

	/**
	 * Get GS1 Company Prefix.
	 * GS1 company prefix of the crate owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGRAI_CompanyPrefix();

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_GRAI_CompanyPrefix = new ModelColumn<>(I_M_HU_PI_GRAI.class, "GRAI_CompanyPrefix", null);
	String COLUMNNAME_GRAI_CompanyPrefix = "GRAI_CompanyPrefix";

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

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PI_GRAI.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set GRAI Packing Instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_GRAI_ID (int M_HU_PI_GRAI_ID);

	/**
	 * Get GRAI Packing Instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_GRAI_ID();

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_M_HU_PI_GRAI_ID = new ModelColumn<>(I_M_HU_PI_GRAI.class, "M_HU_PI_GRAI_ID", null);
	String COLUMNNAME_M_HU_PI_GRAI_ID = "M_HU_PI_GRAI_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_ID();

	ModelColumn<I_M_HU_PI_GRAI, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_HU_PI_ID = new ModelColumn<>(I_M_HU_PI_GRAI.class, "M_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PI_GRAI, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PI_GRAI.class, "Updated", null);
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
