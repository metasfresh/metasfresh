package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_PickingSlot_Trx
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_PickingSlot_Trx 
{

	String Table_Name = "M_PickingSlot_Trx";

//	/** AD_Table_ID=540595 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAction (@Nullable java.lang.String Action);

	/**
	 * Get Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAction();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_Action = new ModelColumn<>(I_M_PickingSlot_Trx.class, "Action", null);
	String COLUMNNAME_Action = "Action";

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

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_Created = new ModelColumn<>(I_M_PickingSlot_Trx.class, "Created", null);
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

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_IsActive = new ModelColumn<>(I_M_PickingSlot_Trx.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsUserAction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUserAction (boolean IsUserAction);

	/**
	 * Get IsUserAction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUserAction();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_IsUserAction = new ModelColumn<>(I_M_PickingSlot_Trx.class, "IsUserAction", null);
	String COLUMNNAME_IsUserAction = "IsUserAction";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_PickingSlot_Trx, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_PickingSlot_Trx.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Picking Slot.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PickingSlot_ID (int M_PickingSlot_ID);

	/**
	 * Get Picking Slot.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PickingSlot_ID();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_M_PickingSlot_ID = new ModelColumn<>(I_M_PickingSlot_Trx.class, "M_PickingSlot_ID", null);
	String COLUMNNAME_M_PickingSlot_ID = "M_PickingSlot_ID";

	/**
	 * Set Picking Slot Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PickingSlot_Trx_ID (int M_PickingSlot_Trx_ID);

	/**
	 * Get Picking Slot Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PickingSlot_Trx_ID();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_M_PickingSlot_Trx_ID = new ModelColumn<>(I_M_PickingSlot_Trx.class, "M_PickingSlot_Trx_ID", null);
	String COLUMNNAME_M_PickingSlot_Trx_ID = "M_PickingSlot_Trx_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_Processed = new ModelColumn<>(I_M_PickingSlot_Trx.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_PickingSlot_Trx, Object> COLUMN_Updated = new ModelColumn<>(I_M_PickingSlot_Trx.class, "Updated", null);
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
