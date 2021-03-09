package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Repair_Consumption_Summary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Repair_Consumption_Summary 
{

	String Table_Name = "C_Project_Repair_Consumption_Summary";

//	/** AD_Table_ID=541565 */
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
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Repair Project Consumption Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Repair_Consumption_Summary_ID (int C_Project_Repair_Consumption_Summary_ID);

	/**
	 * Get Repair Project Consumption Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Repair_Consumption_Summary_ID();

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_C_Project_Repair_Consumption_Summary_ID = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "C_Project_Repair_Consumption_Summary_ID", null);
	String COLUMNNAME_C_Project_Repair_Consumption_Summary_ID = "C_Project_Repair_Consumption_Summary_ID";

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

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "Created", null);
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

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyConsumed (BigDecimal QtyConsumed);

	/**
	 * Get Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyConsumed();

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_QtyConsumed = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "QtyConsumed", null);
	String COLUMNNAME_QtyConsumed = "QtyConsumed";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_QtyReserved = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_Repair_Consumption_Summary, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Repair_Consumption_Summary.class, "Updated", null);
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
