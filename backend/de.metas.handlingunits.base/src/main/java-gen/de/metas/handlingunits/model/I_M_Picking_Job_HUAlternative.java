package de.metas.handlingunits.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_HUAlternative
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_HUAlternative 
{

	String Table_Name = "M_Picking_Job_HUAlternative";

//	/** AD_Table_ID=541926 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Picking_Job_HUAlternative, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "Created", null);
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

	ModelColumn<I_M_Picking_Job_HUAlternative, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Picking Job HU Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_HUAlternative_ID (int M_Picking_Job_HUAlternative_ID);

	/**
	 * Get Picking Job HU Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_HUAlternative_ID();

	ModelColumn<I_M_Picking_Job_HUAlternative, Object> COLUMN_M_Picking_Job_HUAlternative_ID = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "M_Picking_Job_HUAlternative_ID", null);
	String COLUMNNAME_M_Picking_Job_HUAlternative_ID = "M_Picking_Job_HUAlternative_ID";

	/**
	 * Set Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_ID (int M_Picking_Job_ID);

	/**
	 * Get Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_ID();

	de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job();

	void setM_Picking_Job(de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job);

	ModelColumn<I_M_Picking_Job_HUAlternative, de.metas.handlingunits.model.I_M_Picking_Job> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "M_Picking_Job_ID", de.metas.handlingunits.model.I_M_Picking_Job.class);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

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
	 * Set Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_HU_ID (int PickFrom_HU_ID);

	/**
	 * Get Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_HU_ID();

	de.metas.handlingunits.model.I_M_HU getPickFrom_HU();

	void setPickFrom_HU(de.metas.handlingunits.model.I_M_HU PickFrom_HU);

	ModelColumn<I_M_Picking_Job_HUAlternative, de.metas.handlingunits.model.I_M_HU> COLUMN_PickFrom_HU_ID = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "PickFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_PickFrom_HU_ID = "PickFrom_HU_ID";

	/**
	 * Set Pick From Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Locator_ID (int PickFrom_Locator_ID);

	/**
	 * Get Pick From Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Locator_ID();

	String COLUMNNAME_PickFrom_Locator_ID = "PickFrom_Locator_ID";

	/**
	 * Set Pick From Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Warehouse_ID (int PickFrom_Warehouse_ID);

	/**
	 * Get Pick From Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Warehouse_ID();

	String COLUMNNAME_PickFrom_Warehouse_ID = "PickFrom_Warehouse_ID";

	/**
	 * Set Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyAvailable (BigDecimal QtyAvailable);

	/**
	 * Get Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyAvailable();

	ModelColumn<I_M_Picking_Job_HUAlternative, Object> COLUMN_QtyAvailable = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "QtyAvailable", null);
	String COLUMNNAME_QtyAvailable = "QtyAvailable";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_HUAlternative, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_HUAlternative.class, "Updated", null);
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
