package de.metas.picking.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Config_V2
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Config_V2 
{

	String Table_Name = "M_Picking_Config_V2";

//	/** AD_Table_ID=541418 */
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

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Config_V2.class, "Created", null);
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

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Config_V2.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Consider Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsConsiderAttributes (boolean IsConsiderAttributes);

	/**
	 * Get Consider Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isConsiderAttributes();

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsConsiderAttributes = new ModelColumn<>(I_M_Picking_Config_V2.class, "IsConsiderAttributes", null);
	String COLUMNNAME_IsConsiderAttributes = "IsConsiderAttributes";

	/**
	 * Set Picking Review Required.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickingReviewRequired (boolean IsPickingReviewRequired);

	/**
	 * Get Picking Review Required.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickingReviewRequired();

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsPickingReviewRequired = new ModelColumn<>(I_M_Picking_Config_V2.class, "IsPickingReviewRequired", null);
	String COLUMNNAME_IsPickingReviewRequired = "IsPickingReviewRequired";

	/**
	 * Set Reserve HUs On Picking Job Start.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReserveHUsOnPickingJobStart (boolean IsReserveHUsOnPickingJobStart);

	/**
	 * Get Reserve HUs On Picking Job Start.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReserveHUsOnPickingJobStart();

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_IsReserveHUsOnPickingJobStart = new ModelColumn<>(I_M_Picking_Config_V2.class, "IsReserveHUsOnPickingJobStart", null);
	String COLUMNNAME_IsReserveHUsOnPickingJobStart = "IsReserveHUsOnPickingJobStart";

	/**
	 * Set Picking configuration (V2).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Config_V2_ID (int M_Picking_Config_V2_ID);

	/**
	 * Get Picking configuration (V2).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Config_V2_ID();

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_M_Picking_Config_V2_ID = new ModelColumn<>(I_M_Picking_Config_V2.class, "M_Picking_Config_V2_ID", null);
	String COLUMNNAME_M_Picking_Config_V2_ID = "M_Picking_Config_V2_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Config_V2, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Config_V2.class, "Updated", null);
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
