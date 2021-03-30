package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Item_Storage_Snapshot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Item_Storage_Snapshot 
{

	String Table_Name = "M_HU_Item_Storage_Snapshot";

//	/** AD_Table_ID=540673 */
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "Created", null);
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

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_ID();

	de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item();

	void setM_HU_Item(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item);

	ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_ID = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
	String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set Handling Units Item Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_Storage_ID (int M_HU_Item_Storage_ID);

	/**
	 * Get Handling Units Item Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_Storage_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Item_Storage getM_HU_Item_Storage();

	void setM_HU_Item_Storage(@Nullable de.metas.handlingunits.model.I_M_HU_Item_Storage M_HU_Item_Storage);

	ModelColumn<I_M_HU_Item_Storage_Snapshot, de.metas.handlingunits.model.I_M_HU_Item_Storage> COLUMN_M_HU_Item_Storage_ID = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_Storage_ID", de.metas.handlingunits.model.I_M_HU_Item_Storage.class);
	String COLUMNNAME_M_HU_Item_Storage_ID = "M_HU_Item_Storage_ID";

	/**
	 * Set Handling Units Item Storage Snapshot.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_Storage_Snapshot_ID (int M_HU_Item_Storage_Snapshot_ID);

	/**
	 * Get Handling Units Item Storage Snapshot.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_Storage_Snapshot_ID();

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_M_HU_Item_Storage_Snapshot_ID = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "M_HU_Item_Storage_Snapshot_ID", null);
	String COLUMNNAME_M_HU_Item_Storage_Snapshot_ID = "M_HU_Item_Storage_Snapshot_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Snapshot_UUID = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "Snapshot_UUID", null);
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

	ModelColumn<I_M_HU_Item_Storage_Snapshot, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Item_Storage_Snapshot.class, "Updated", null);
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
