package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_InventoryLine_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InventoryLine_HU 
{

	String Table_Name = "M_InventoryLine_HU";

//	/** AD_Table_ID=541345 */
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

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_Created = new ModelColumn<>(I_M_InventoryLine_HU.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InventoryLine_HU.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_InventoryLine_HU, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_InventoryLine_HU.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	org.compiere.model.I_M_Inventory getM_Inventory();

	void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

	ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new ModelColumn<>(I_M_InventoryLine_HU.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set M_InventoryLine_HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_HU_ID (int M_InventoryLine_HU_ID);

	/**
	 * Get M_InventoryLine_HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_HU_ID();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_M_InventoryLine_HU_ID = new ModelColumn<>(I_M_InventoryLine_HU.class, "M_InventoryLine_HU_ID", null);
	String COLUMNNAME_M_InventoryLine_HU_ID = "M_InventoryLine_HU_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_ID();

	org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine);

	ModelColumn<I_M_InventoryLine_HU, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new ModelColumn<>(I_M_InventoryLine_HU.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
	String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBook (@Nullable BigDecimal QtyBook);

	/**
	 * Get Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBook();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyBook = new ModelColumn<>(I_M_InventoryLine_HU.class, "QtyBook", null);
	String COLUMNNAME_QtyBook = "QtyBook";

	/**
	 * Set Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyCount (@Nullable BigDecimal QtyCount);

	/**
	 * Get Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCount();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyCount = new ModelColumn<>(I_M_InventoryLine_HU.class, "QtyCount", null);
	String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInternalUse (@Nullable BigDecimal QtyInternalUse);

	/**
	 * Get Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInternalUse();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_QtyInternalUse = new ModelColumn<>(I_M_InventoryLine_HU.class, "QtyInternalUse", null);
	String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

	/**
	 * Set Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRenderedQRCode (@Nullable java.lang.String RenderedQRCode);

	/**
	 * Get Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRenderedQRCode();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_RenderedQRCode = new ModelColumn<>(I_M_InventoryLine_HU.class, "RenderedQRCode", null);
	String COLUMNNAME_RenderedQRCode = "RenderedQRCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InventoryLine_HU, Object> COLUMN_Updated = new ModelColumn<>(I_M_InventoryLine_HU.class, "Updated", null);
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
