package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Trx_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Trx_Line 
{

	String Table_Name = "M_HU_Trx_Line";

//	/** AD_Table_ID=540515 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Trx_Line.class, "Created", null);
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
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_DateTrx = new ModelColumn<>(I_M_HU_Trx_Line.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUStatus (@Nullable java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUStatus();

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_HUStatus = new ModelColumn<>(I_M_HU_Trx_Line.class, "HUStatus", null);
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

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Trx_Line.class, "IsActive", null);
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

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_ID (int M_HU_Item_ID);

	/**
	 * Get Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item();

	void setM_HU_Item(@Nullable de.metas.handlingunits.model.I_M_HU_Item M_HU_Item);

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "M_HU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
	String COLUMNNAME_M_HU_Item_ID = "M_HU_Item_ID";

	/**
	 * Set HU-Transaktionskopf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID);

	/**
	 * Get HU-Transaktionskopf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Trx_Hdr_ID();

	de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr();

	void setM_HU_Trx_Hdr(de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr);

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Hdr> COLUMN_M_HU_Trx_Hdr_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "M_HU_Trx_Hdr_ID", de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
	String COLUMNNAME_M_HU_Trx_Hdr_ID = "M_HU_Trx_Hdr_ID";

	/**
	 * Set HU Transaction Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID);

	/**
	 * Get HU Transaction Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Trx_Line_ID();

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_M_HU_Trx_Line_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "M_HU_Trx_Line_ID", null);
	String COLUMNNAME_M_HU_Trx_Line_ID = "M_HU_Trx_Line_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Eltern-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_HU_Trx_Line_ID (int Parent_HU_Trx_Line_ID);

	/**
	 * Get Eltern-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_HU_Trx_Line_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line getParent_HU_Trx_Line();

	void setParent_HU_Trx_Line(@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line Parent_HU_Trx_Line);

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_Parent_HU_Trx_Line_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "Parent_HU_Trx_Line_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	String COLUMNNAME_Parent_HU_Trx_Line_ID = "Parent_HU_Trx_Line_ID";

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

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Processed = new ModelColumn<>(I_M_HU_Trx_Line.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

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

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Trx_Line.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Record_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Storno-Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Storno-Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversalLine_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line getReversalLine();

	void setReversalLine(@Nullable de.metas.handlingunits.model.I_M_HU_Trx_Line ReversalLine);

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_ReversalLine_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "ReversalLine_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Trx_Line, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Trx_Line.class, "Updated", null);
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
	 * Set Virtual Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVHU_Item_ID (int VHU_Item_ID);

	/**
	 * Get Virtual Handling Units Item.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getVHU_Item_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Item getVHU_Item();

	void setVHU_Item(@Nullable de.metas.handlingunits.model.I_M_HU_Item VHU_Item);

	ModelColumn<I_M_HU_Trx_Line, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_VHU_Item_ID = new ModelColumn<>(I_M_HU_Trx_Line.class, "VHU_Item_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
	String COLUMNNAME_VHU_Item_ID = "VHU_Item_ID";
}
