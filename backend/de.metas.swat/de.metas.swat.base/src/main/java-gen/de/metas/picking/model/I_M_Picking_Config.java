package de.metas.picking.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Config
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Config 
{

	String Table_Name = "M_Picking_Config";

//	/** AD_Table_ID=540873 */
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

	ModelColumn<I_M_Picking_Config, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Config.class, "Created", null);
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

	ModelColumn<I_M_Picking_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsAllowOverdelivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowOverdelivery (boolean IsAllowOverdelivery);

	/**
	 * Get IsAllowOverdelivery.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowOverdelivery();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_IsAllowOverdelivery = new ModelColumn<>(I_M_Picking_Config.class, "IsAllowOverdelivery", null);
	String COLUMNNAME_IsAllowOverdelivery = "IsAllowOverdelivery";

	/**
	 * Set IsAutoProcess.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoProcess (boolean IsAutoProcess);

	/**
	 * Get IsAutoProcess.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoProcess();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_IsAutoProcess = new ModelColumn<>(I_M_Picking_Config.class, "IsAutoProcess", null);
	String COLUMNNAME_IsAutoProcess = "IsAutoProcess";

	/**
	 * Set Forbid Aggregation of CUs for diff. orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsForbidAggCUsForDifferentOrders (boolean IsForbidAggCUsForDifferentOrders);

	/**
	 * Get Forbid Aggregation of CUs for diff. orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isForbidAggCUsForDifferentOrders();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_IsForbidAggCUsForDifferentOrders = new ModelColumn<>(I_M_Picking_Config.class, "IsForbidAggCUsForDifferentOrders", null);
	String COLUMNNAME_IsForbidAggCUsForDifferentOrders = "IsForbidAggCUsForDifferentOrders";

	/**
	 * Set Picking configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Config_ID (int M_Picking_Config_ID);

	/**
	 * Get Picking configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Config_ID();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_M_Picking_Config_ID = new ModelColumn<>(I_M_Picking_Config.class, "M_Picking_Config_ID", null);
	String COLUMNNAME_M_Picking_Config_ID = "M_Picking_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Config.class, "Updated", null);
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

	/**
	 * Set Picking terminal view profile.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_PickingTerminal_ViewProfile (java.lang.String WEBUI_PickingTerminal_ViewProfile);

	/**
	 * Get Picking terminal view profile.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWEBUI_PickingTerminal_ViewProfile();

	ModelColumn<I_M_Picking_Config, Object> COLUMN_WEBUI_PickingTerminal_ViewProfile = new ModelColumn<>(I_M_Picking_Config.class, "WEBUI_PickingTerminal_ViewProfile", null);
	String COLUMNNAME_WEBUI_PickingTerminal_ViewProfile = "WEBUI_PickingTerminal_ViewProfile";
}
