package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_UserProfile_Picking_Job
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_UserProfile_Picking_Job 
{

	String Table_Name = "MobileUI_UserProfile_Picking_Job";

//	/** AD_Table_ID=542464 */
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

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "Created", null);
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
	 * Set Create Shipment Policy.
	 * Create Shipment Policy - Don't Create, Create Draft, Create and Complete
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCreateShipmentPolicy (java.lang.String CreateShipmentPolicy);

	/**
	 * Get Create Shipment Policy.
	 * Create Shipment Policy - Don't Create, Create Draft, Create and Complete
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateShipmentPolicy();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_CreateShipmentPolicy = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "CreateShipmentPolicy", null);
	String COLUMNNAME_CreateShipmentPolicy = "CreateShipmentPolicy";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_Description = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "Description", null);
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

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow Partial Picking Completion.
	 * Users can complete jobs on mobile without picking the full ordered quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowCompletingPartialPickingJob (boolean IsAllowCompletingPartialPickingJob);

	/**
	 * Get Allow Partial Picking Completion.
	 * Users can complete jobs on mobile without picking the full ordered quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowCompletingPartialPickingJob();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsAllowCompletingPartialPickingJob = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsAllowCompletingPartialPickingJob", null);
	String COLUMNNAME_IsAllowCompletingPartialPickingJob = "IsAllowCompletingPartialPickingJob";

	/**
	 * Set Allow new TU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowNewTU (boolean IsAllowNewTU);

	/**
	 * Get Allow new TU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowNewTU();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsAllowNewTU = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsAllowNewTU", null);
	String COLUMNNAME_IsAllowNewTU = "IsAllowNewTU";

	/**
	 * Set Allow picking any HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowPickingAnyHU (boolean IsAllowPickingAnyHU);

	/**
	 * Get Allow picking any HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowPickingAnyHU();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsAllowPickingAnyHU = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsAllowPickingAnyHU", null);
	String COLUMNNAME_IsAllowPickingAnyHU = "IsAllowPickingAnyHU";

	/**
	 * Set Allow picking with no rejected qty reason.
	 * If activated, a smaller quantity may be picked. An additional option then appears in the picking dialog.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowSkippingRejectedReason (boolean IsAllowSkippingRejectedReason);

	/**
	 * Get Allow picking with no rejected qty reason.
	 * If activated, a smaller quantity may be picked. An additional option then appears in the picking dialog.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowSkippingRejectedReason();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsAllowSkippingRejectedReason = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsAllowSkippingRejectedReason", null);
	String COLUMNNAME_IsAllowSkippingRejectedReason = "IsAllowSkippingRejectedReason";

	/**
	 * Set Always split HU.
	 * If checked, the selected HU is always split before picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAlwaysSplitHUsEnabled (boolean IsAlwaysSplitHUsEnabled);

	/**
	 * Get Always split HU.
	 * If checked, the selected HU is always split before picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAlwaysSplitHUsEnabled();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsAlwaysSplitHUsEnabled = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsAlwaysSplitHUsEnabled", null);
	String COLUMNNAME_IsAlwaysSplitHUsEnabled = "IsAlwaysSplitHUsEnabled";

	/**
	 * Set Allow picking TU with catch weight.
	 * If activated and a packing instruction is included in the order line, it is assumed that it is a TU when Catch Weight is picked. Otherwise metasfresh assumes that it is a CU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCatchWeightTUPickingEnabled (boolean IsCatchWeightTUPickingEnabled);

	/**
	 * Get Allow picking TU with catch weight.
	 * If activated and a packing instruction is included in the order line, it is assumed that it is a TU when Catch Weight is picked. Otherwise metasfresh assumes that it is a CU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCatchWeightTUPickingEnabled();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsCatchWeightTUPickingEnabled = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsCatchWeightTUPickingEnabled", null);
	String COLUMNNAME_IsCatchWeightTUPickingEnabled = "IsCatchWeightTUPickingEnabled";

	/**
	 * Set Consider sales order capacity.
	 * If not activated, the quantity allocation for TU Catch Weight is taken from the master data of the product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsConsiderSalesOrderCapacity (boolean IsConsiderSalesOrderCapacity);

	/**
	 * Get Consider sales order capacity.
	 * If not activated, the quantity allocation for TU Catch Weight is taken from the master data of the product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isConsiderSalesOrderCapacity();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsConsiderSalesOrderCapacity = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsConsiderSalesOrderCapacity", null);
	String COLUMNNAME_IsConsiderSalesOrderCapacity = "IsConsiderSalesOrderCapacity";

	/**
	 * Set Pick with LU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickingWithNewLU (boolean IsPickingWithNewLU);

	/**
	 * Get Pick with LU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickingWithNewLU();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsPickingWithNewLU = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsPickingWithNewLU", null);
	String COLUMNNAME_IsPickingWithNewLU = "IsPickingWithNewLU";

	/**
	 * Set Ask User when Over Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowConfirmationPromptWhenOverPick (boolean IsShowConfirmationPromptWhenOverPick);

	/**
	 * Get Ask User when Over Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowConfirmationPromptWhenOverPick();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsShowConfirmationPromptWhenOverPick = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsShowConfirmationPromptWhenOverPick", null);
	String COLUMNNAME_IsShowConfirmationPromptWhenOverPick = "IsShowConfirmationPromptWhenOverPick";

	/**
	 * Set Show Best Before of Last Picked Item .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowLastPickedBestBeforeDateForLines (boolean IsShowLastPickedBestBeforeDateForLines);

	/**
	 * Get Show Best Before of Last Picked Item .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowLastPickedBestBeforeDateForLines();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_IsShowLastPickedBestBeforeDateForLines = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "IsShowLastPickedBestBeforeDateForLines", null);
	String COLUMNNAME_IsShowLastPickedBestBeforeDateForLines = "IsShowLastPickedBestBeforeDateForLines";

	/**
	 * Set Mobile UI Picking Job Options.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_Picking_Job_ID (int MobileUI_UserProfile_Picking_Job_ID);

	/**
	 * Get Mobile UI Picking Job Options.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_Picking_Job_ID();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_MobileUI_UserProfile_Picking_Job_ID = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "MobileUI_UserProfile_Picking_Job_ID", null);
	String COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID = "MobileUI_UserProfile_Picking_Job_ID";

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

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_Name = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Aggregation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickingJobAggregationType (@Nullable java.lang.String PickingJobAggregationType);

	/**
	 * Get Aggregation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPickingJobAggregationType();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_PickingJobAggregationType = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "PickingJobAggregationType", null);
	String COLUMNNAME_PickingJobAggregationType = "PickingJobAggregationType";

	/**
	 * Set Picking line group by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickingLineGroupBy (@Nullable java.lang.String PickingLineGroupBy);

	/**
	 * Get Picking line group by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPickingLineGroupBy();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_PickingLineGroupBy = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "PickingLineGroupBy", null);
	String COLUMNNAME_PickingLineGroupBy = "PickingLineGroupBy";

	/**
	 * Set Picking line sort by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickingLineSortBy (@Nullable java.lang.String PickingLineSortBy);

	/**
	 * Get Picking line sort by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPickingLineSortBy();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_PickingLineSortBy = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "PickingLineSortBy", null);
	String COLUMNNAME_PickingLineSortBy = "PickingLineSortBy";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileUI_UserProfile_Picking_Job, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_UserProfile_Picking_Job.class, "Updated", null);
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
