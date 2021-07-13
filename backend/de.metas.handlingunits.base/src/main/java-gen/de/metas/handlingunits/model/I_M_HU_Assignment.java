package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Assignment 
{

	String Table_Name = "M_HU_Assignment";

//	/** AD_Table_ID=540569 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Assignment.class, "Created", null);
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

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Assignment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Transfer Packing Materials.
	 * Shall we transfer packing materials along with the HU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTransferPackingMaterials (boolean IsTransferPackingMaterials);

	/**
	 * Get Transfer Packing Materials.
	 * Shall we transfer packing materials along with the HU
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTransferPackingMaterials();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_IsTransferPackingMaterials = new ModelColumn<>(I_M_HU_Assignment.class, "IsTransferPackingMaterials", null);
	String COLUMNNAME_IsTransferPackingMaterials = "IsTransferPackingMaterials";

	/**
	 * Set M_HU_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Assignment_ID (int M_HU_Assignment_ID);

	/**
	 * Get M_HU_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Assignment_ID();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_M_HU_Assignment_ID = new ModelColumn<>(I_M_HU_Assignment.class, "M_HU_Assignment_ID", null);
	String COLUMNNAME_M_HU_Assignment_ID = "M_HU_Assignment_ID";

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

	ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Assignment.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set LU.
	 * Loading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_LU_HU_ID (int M_LU_HU_ID);

	/**
	 * Get LU.
	 * Loading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_LU_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_LU_HU();

	void setM_LU_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_LU_HU);

	ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_LU_HU_ID = new ModelColumn<>(I_M_HU_Assignment.class, "M_LU_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_LU_HU_ID = "M_LU_HU_ID";

	/**
	 * Set TU.
	 * Trading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_TU_HU_ID (int M_TU_HU_ID);

	/**
	 * Get TU.
	 * Trading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_TU_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_TU_HU();

	void setM_TU_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_TU_HU);

	ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_M_TU_HU_ID = new ModelColumn<>(I_M_HU_Assignment.class, "M_TU_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_TU_HU_ID = "M_TU_HU_ID";

	/**
	 * Set Produkte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProducts (@Nullable java.lang.String Products);

	/**
	 * Get Produkte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getProducts();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_Products = new ModelColumn<>(I_M_HU_Assignment.class, "Products", null);
	String COLUMNNAME_Products = "Products";

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

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Assignment.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_Record_ID = new ModelColumn<>(I_M_HU_Assignment.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Assignment, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Assignment.class, "Updated", null);
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
	 * Set CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVHU_ID (int VHU_ID);

	/**
	 * Get CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getVHU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getVHU();

	void setVHU(@Nullable de.metas.handlingunits.model.I_M_HU VHU);

	ModelColumn<I_M_HU_Assignment, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new ModelColumn<>(I_M_HU_Assignment.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_VHU_ID = "VHU_ID";
}
