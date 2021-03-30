package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Snapshot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Snapshot 
{

	String Table_Name = "M_HU_Snapshot";

//	/** AD_Table_ID=540669 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Snapshot.class, "Created", null);
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
	 * Set Own Palette.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUPlanningReceiptOwnerPM (boolean HUPlanningReceiptOwnerPM);

	/**
	 * Get Own Palette.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isHUPlanningReceiptOwnerPM();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_HUPlanningReceiptOwnerPM = new ModelColumn<>(I_M_HU_Snapshot.class, "HUPlanningReceiptOwnerPM", null);
	String COLUMNNAME_HUPlanningReceiptOwnerPM = "HUPlanningReceiptOwnerPM";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHUStatus (java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHUStatus();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_HUStatus = new ModelColumn<>(I_M_HU_Snapshot.class, "HUStatus", null);
	String COLUMNNAME_HUStatus = "HUStatus";

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

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Snapshot.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLocked (boolean Locked);

	/**
	 * Get Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isLocked();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Locked = new ModelColumn<>(I_M_HU_Snapshot.class, "Locked", null);
	String COLUMNNAME_Locked = "Locked";

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

	ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Snapshot.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item Parent ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_Parent_ID (int M_HU_Item_Parent_ID);

	/**
	 * Get Handling Units Item Parent ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_Parent_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item_Parent();

	void setM_HU_Item_Parent(@Nullable de.metas.handlingunits.model.I_M_HU_Item M_HU_Item_Parent);

	ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_Parent_ID = new ModelColumn<>(I_M_HU_Snapshot.class, "M_HU_Item_Parent_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
	String COLUMNNAME_M_HU_Item_Parent_ID = "M_HU_Item_Parent_ID";

	/**
	 * Set Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_LUTU_Configuration_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	void setM_HU_LUTU_Configuration(@Nullable de.metas.handlingunits.model.I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration);

	ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration> COLUMN_M_HU_LUTU_Configuration_ID = new ModelColumn<>(I_M_HU_Snapshot.class, "M_HU_LUTU_Configuration_ID", de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class);
	String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Version_ID();

	de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();

	void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

	ModelColumn<I_M_HU_Snapshot, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new ModelColumn<>(I_M_HU_Snapshot.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Handling Units (snapshot).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Snapshot_ID (int M_HU_Snapshot_ID);

	/**
	 * Get Handling Units (snapshot).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Snapshot_ID();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_M_HU_Snapshot_ID = new ModelColumn<>(I_M_HU_Snapshot.class, "M_HU_Snapshot_ID", null);
	String COLUMNNAME_M_HU_Snapshot_ID = "M_HU_Snapshot_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSnapshot_UUID (java.lang.String Snapshot_UUID);

	/**
	 * Get Snapshot UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSnapshot_UUID();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Snapshot_UUID = new ModelColumn<>(I_M_HU_Snapshot.class, "Snapshot_UUID", null);
	String COLUMNNAME_Snapshot_UUID = "Snapshot_UUID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Snapshot.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_M_HU_Snapshot, Object> COLUMN_Value = new ModelColumn<>(I_M_HU_Snapshot.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
