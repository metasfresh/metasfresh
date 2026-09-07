package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_MFG_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_MFG_Config 
{

	String Table_Name = "MobileUI_MFG_Config";

//	/** AD_Table_ID=542397 */
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

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_MFG_Config.class, "Created", null);
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

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Finished goods: allow receiving to LU.
	 * Offer load-unit (pallet/LU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowFinishedGoodsReceiveToLU (boolean IsAllowFinishedGoodsReceiveToLU);

	/**
	 * Get Finished goods: allow receiving to LU.
	 * Offer load-unit (pallet/LU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowFinishedGoodsReceiveToLU();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsAllowFinishedGoodsReceiveToLU = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsAllowFinishedGoodsReceiveToLU", null);
	String COLUMNNAME_IsAllowFinishedGoodsReceiveToLU = "IsAllowFinishedGoodsReceiveToLU";

	/**
	 * Set Finished goods: allow receiving to TU.
	 * Offer transport-unit (TU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowFinishedGoodsReceiveToTU (boolean IsAllowFinishedGoodsReceiveToTU);

	/**
	 * Get Finished goods: allow receiving to TU.
	 * Offer transport-unit (TU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowFinishedGoodsReceiveToTU();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsAllowFinishedGoodsReceiveToTU = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsAllowFinishedGoodsReceiveToTU", null);
	String COLUMNNAME_IsAllowFinishedGoodsReceiveToTU = "IsAllowFinishedGoodsReceiveToTU";

	/**
	 * Set No Raw Material Check.
	 * Allows scanning and issuing HUs that are not in the manufacturing issue plan
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowIssuingAnyHU (boolean IsAllowIssuingAnyHU);

	/**
	 * Get No Raw Material Check.
	 * Allows scanning and issuing HUs that are not in the manufacturing issue plan
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowIssuingAnyHU();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsAllowIssuingAnyHU = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsAllowIssuingAnyHU", null);
	String COLUMNNAME_IsAllowIssuingAnyHU = "IsAllowIssuingAnyHU";

	/**
	 * Set Allow receiving without a packing instruction.
	 * Offer the 'No Packing Item' packing instruction as a receiving target for the production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowReceiveWithoutPackingItem (boolean IsAllowReceiveWithoutPackingItem);

	/**
	 * Get Allow receiving without a packing instruction.
	 * Offer the 'No Packing Item' packing instruction as a receiving target for the production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowReceiveWithoutPackingItem();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsAllowReceiveWithoutPackingItem = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsAllowReceiveWithoutPackingItem", null);
	String COLUMNNAME_IsAllowReceiveWithoutPackingItem = "IsAllowReceiveWithoutPackingItem";

	/**
	 * Set Best Before Date editable.
	 * Allows editing the Best-Before-Date (MHD) when receiving finished goods in mobile manufacturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBestBeforeDateEditable (boolean IsBestBeforeDateEditable);

	/**
	 * Get Best Before Date editable.
	 * Allows editing the Best-Before-Date (MHD) when receiving finished goods in mobile manufacturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBestBeforeDateEditable();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsBestBeforeDateEditable = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsBestBeforeDateEditable", null);
	String COLUMNNAME_IsBestBeforeDateEditable = "IsBestBeforeDateEditable";

	/**
	 * Set Capture catch weight.
	 * Capture the catch weight of a catch-weight product at production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCaptureCatchWeightAtReceipt (boolean IsCaptureCatchWeightAtReceipt);

	/**
	 * Get Capture catch weight.
	 * Capture the catch weight of a catch-weight product at production receipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCaptureCatchWeightAtReceipt();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsCaptureCatchWeightAtReceipt = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsCaptureCatchWeightAtReceipt", null);
	String COLUMNNAME_IsCaptureCatchWeightAtReceipt = "IsCaptureCatchWeightAtReceipt";

	/**
	 * Set Lot Number editable.
	 * Allows editing the Lot Number when receiving finished goods in mobile manufacturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsLotNumberEditable (boolean IsLotNumberEditable);

	/**
	 * Get Lot Number editable.
	 * Allows editing the Lot Number when receiving finished goods in mobile manufacturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isLotNumberEditable();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsLotNumberEditable = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsLotNumberEditable", null);
	String COLUMNNAME_IsLotNumberEditable = "IsLotNumberEditable";

	/**
	 * Set Workstation Scan Required.
	 * User must scan a workstation QR code before starting manufacturing work. Only orders for the assigned workstation are displayed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsScanResourceRequired (boolean IsScanResourceRequired);

	/**
	 * Get Workstation Scan Required.
	 * User must scan a workstation QR code before starting manufacturing work. Only orders for the assigned workstation are displayed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isScanResourceRequired();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsScanResourceRequired = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsScanResourceRequired", null);
	String COLUMNNAME_IsScanResourceRequired = "IsScanResourceRequired";

	/**
	 * Set Finished goods: skip receiving-target step.
	 * Skip the new-Gebinde / scan-existing screen for the finished good and go straight to the packing instruction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSkipFinishedGoodsReceiveTargetStep (boolean IsSkipFinishedGoodsReceiveTargetStep);

	/**
	 * Get Finished goods: skip receiving-target step.
	 * Skip the new-Gebinde / scan-existing screen for the finished good and go straight to the packing instruction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSkipFinishedGoodsReceiveTargetStep();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsSkipFinishedGoodsReceiveTargetStep = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsSkipFinishedGoodsReceiveTargetStep", null);
	String COLUMNNAME_IsSkipFinishedGoodsReceiveTargetStep = "IsSkipFinishedGoodsReceiveTargetStep";

	/**
	 * Set MobileUI Manufacturing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_MFG_Config_ID (int MobileUI_MFG_Config_ID);

	/**
	 * Get MobileUI Manufacturing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_MFG_Config_ID();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_MobileUI_MFG_Config_ID = new ModelColumn<>(I_MobileUI_MFG_Config.class, "MobileUI_MFG_Config_ID", null);
	String COLUMNNAME_MobileUI_MFG_Config_ID = "MobileUI_MFG_Config_ID";

	/**
	 * Set Receive Unit Type.
	 * Determines whether the receive quantity is entered in CU or TU
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReceiveUnitType (java.lang.String ReceiveUnitType);

	/**
	 * Get Receive Unit Type.
	 * Determines whether the receive quantity is entered in CU or TU
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReceiveUnitType();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_ReceiveUnitType = new ModelColumn<>(I_MobileUI_MFG_Config.class, "ReceiveUnitType", null);
	String COLUMNNAME_ReceiveUnitType = "ReceiveUnitType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_MFG_Config.class, "Updated", null);
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
