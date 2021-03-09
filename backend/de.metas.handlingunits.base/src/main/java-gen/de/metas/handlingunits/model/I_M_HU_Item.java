package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Item 
{

	String Table_Name = "M_HU_Item";

//	/** AD_Table_ID=540506 */
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

	ModelColumn<I_M_HU_Item, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Item.class, "Created", null);
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

	ModelColumn<I_M_HU_Item, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Item.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Item Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setItemType (@Nullable java.lang.String ItemType);

	/**
	 * Get Item Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getItemType();

	ModelColumn<I_M_HU_Item, Object> COLUMN_ItemType = new ModelColumn<>(I_M_HU_Item.class, "ItemType", null);
	String COLUMNNAME_ItemType = "ItemType";

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

	ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Item.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_ID();

	ModelColumn<I_M_HU_Item, Object> COLUMN_M_HU_Item_ID = new ModelColumn<>(I_M_HU_Item.class, "M_HU_Item_ID", null);
	String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set Packing Material.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packing Material.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackingMaterial_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PackingMaterial getM_HU_PackingMaterial();

	void setM_HU_PackingMaterial(@Nullable de.metas.handlingunits.model.I_M_HU_PackingMaterial M_HU_PackingMaterial);

	ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU_PackingMaterial> COLUMN_M_HU_PackingMaterial_ID = new ModelColumn<>(I_M_HU_Item.class, "M_HU_PackingMaterial_ID", de.metas.handlingunits.model.I_M_HU_PackingMaterial.class);
	String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Set Packing Instruction Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID);

	/**
	 * Get Packing Instruction Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI_Item getM_HU_PI_Item();

	void setM_HU_PI_Item(@Nullable de.metas.handlingunits.model.I_M_HU_PI_Item M_HU_PI_Item);

	ModelColumn<I_M_HU_Item, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_HU_PI_Item_ID = new ModelColumn<>(I_M_HU_Item.class, "M_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	String COLUMNNAME_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_M_HU_Item, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Item.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Item, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Item.class, "Updated", null);
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
