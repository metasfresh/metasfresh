package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_UserProfile_MFG
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_UserProfile_MFG 
{

	String Table_Name = "MobileUI_UserProfile_MFG";

//	/** AD_Table_ID=542263 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "Created", null);
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

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Finished goods: allow receiving to LU.
	 * Offer load-unit (pallet/LU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowFinishedGoodsReceiveToLU (@Nullable java.lang.String IsAllowFinishedGoodsReceiveToLU);

	/**
	 * Get Finished goods: allow receiving to LU.
	 * Offer load-unit (pallet/LU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsAllowFinishedGoodsReceiveToLU();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsAllowFinishedGoodsReceiveToLU = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsAllowFinishedGoodsReceiveToLU", null);
	String COLUMNNAME_IsAllowFinishedGoodsReceiveToLU = "IsAllowFinishedGoodsReceiveToLU";

	/**
	 * Set Finished goods: allow receiving to TU.
	 * Offer transport-unit (TU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowFinishedGoodsReceiveToTU (@Nullable java.lang.String IsAllowFinishedGoodsReceiveToTU);

	/**
	 * Get Finished goods: allow receiving to TU.
	 * Offer transport-unit (TU) targets for the finished-goods production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsAllowFinishedGoodsReceiveToTU();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsAllowFinishedGoodsReceiveToTU = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsAllowFinishedGoodsReceiveToTU", null);
	String COLUMNNAME_IsAllowFinishedGoodsReceiveToTU = "IsAllowFinishedGoodsReceiveToTU";

	/**
	 * Set No Raw Material Check.
	 * Allows scanning and issuing HUs that are not in the manufacturing issue plan
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowIssuingAnyHU (@Nullable java.lang.String IsAllowIssuingAnyHU);

	/**
	 * Get No Raw Material Check.
	 * Allows scanning and issuing HUs that are not in the manufacturing issue plan
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsAllowIssuingAnyHU();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsAllowIssuingAnyHU = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsAllowIssuingAnyHU", null);
	String COLUMNNAME_IsAllowIssuingAnyHU = "IsAllowIssuingAnyHU";

	/**
	 * Set Allow receiving without a packing instruction.
	 * Offer the 'No Packing Item' packing instruction as a receiving target for the production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowReceiveWithoutPackingItem (@Nullable java.lang.String IsAllowReceiveWithoutPackingItem);

	/**
	 * Get Allow receiving without a packing instruction.
	 * Offer the 'No Packing Item' packing instruction as a receiving target for the production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsAllowReceiveWithoutPackingItem();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsAllowReceiveWithoutPackingItem = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsAllowReceiveWithoutPackingItem", null);
	String COLUMNNAME_IsAllowReceiveWithoutPackingItem = "IsAllowReceiveWithoutPackingItem";

	/**
	 * Set Capture catch weight.
	 * Capture the catch weight of a catch-weight product at production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCaptureCatchWeightAtReceipt (@Nullable java.lang.String IsCaptureCatchWeightAtReceipt);

	/**
	 * Get Capture catch weight.
	 * Capture the catch weight of a catch-weight product at production receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsCaptureCatchWeightAtReceipt();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsCaptureCatchWeightAtReceipt = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsCaptureCatchWeightAtReceipt", null);
	String COLUMNNAME_IsCaptureCatchWeightAtReceipt = "IsCaptureCatchWeightAtReceipt";

	/**
	 * Set Workstation Scan Required.
	 * User must scan a workstation QR code before starting manufacturing work. Only orders for the assigned workstation are displayed.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsScanResourceRequired (@Nullable java.lang.String IsScanResourceRequired);

	/**
	 * Get Workstation Scan Required.
	 * User must scan a workstation QR code before starting manufacturing work. Only orders for the assigned workstation are displayed.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsScanResourceRequired();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsScanResourceRequired = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsScanResourceRequired", null);
	String COLUMNNAME_IsScanResourceRequired = "IsScanResourceRequired";

	/**
	 * Set Finished goods: skip receiving-target step.
	 * Skip the new-Gebinde / scan-existing screen for the finished good and go straight to the packing instruction.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSkipFinishedGoodsReceiveTargetStep (@Nullable java.lang.String IsSkipFinishedGoodsReceiveTargetStep);

	/**
	 * Get Finished goods: skip receiving-target step.
	 * Skip the new-Gebinde / scan-existing screen for the finished good and go straight to the packing instruction.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsSkipFinishedGoodsReceiveTargetStep();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_IsSkipFinishedGoodsReceiveTargetStep = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "IsSkipFinishedGoodsReceiveTargetStep", null);
	String COLUMNNAME_IsSkipFinishedGoodsReceiveTargetStep = "IsSkipFinishedGoodsReceiveTargetStep";

	/**
	 * Set Mobile UI User Profile - Manufacturing.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_MFG_ID (int MobileUI_UserProfile_MFG_ID);

	/**
	 * Get Mobile UI User Profile - Manufacturing.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_MFG_ID();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_MobileUI_UserProfile_MFG_ID = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "MobileUI_UserProfile_MFG_ID", null);
	String COLUMNNAME_MobileUI_UserProfile_MFG_ID = "MobileUI_UserProfile_MFG_ID";

	/**
	 * Set Receive Unit Type.
	 * Determines whether the receive quantity is entered in CU or TU
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiveUnitType (@Nullable java.lang.String ReceiveUnitType);

	/**
	 * Get Receive Unit Type.
	 * Determines whether the receive quantity is entered in CU or TU
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiveUnitType();

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_ReceiveUnitType = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "ReceiveUnitType", null);
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

	ModelColumn<I_MobileUI_UserProfile_MFG, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_UserProfile_MFG.class, "Updated", null);
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
