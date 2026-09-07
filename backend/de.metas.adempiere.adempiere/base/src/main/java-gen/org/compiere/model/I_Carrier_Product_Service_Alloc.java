package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for Carrier_Product_Service_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_Product_Service_Alloc 
{

	String Table_Name = "Carrier_Product_Service_Alloc";

//	/** AD_Table_ID=542608 */
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
	 * Set Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_ID (int Carrier_Product_ID);

	/**
	 * Get Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_ID();

	ModelColumn<I_Carrier_Product_Service_Alloc, org.compiere.model.I_Carrier_Product> COLUMN_Carrier_Product_ID = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "Carrier_Product_ID", org.compiere.model.I_Carrier_Product.class);
	String COLUMNNAME_Carrier_Product_ID = "Carrier_Product_ID";

	/**
	 * Set Product Carrier Service Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_Service_Alloc_ID (int Carrier_Product_Service_Alloc_ID);

	/**
	 * Get Product Carrier Service Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_Service_Alloc_ID();

	ModelColumn<I_Carrier_Product_Service_Alloc, Object> COLUMN_Carrier_Product_Service_Alloc_ID = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "Carrier_Product_Service_Alloc_ID", null);
	String COLUMNNAME_Carrier_Product_Service_Alloc_ID = "Carrier_Product_Service_Alloc_ID";

	/**
	 * Set Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Service_ID (int Carrier_Service_ID);

	/**
	 * Get Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Service_ID();

	ModelColumn<I_Carrier_Product_Service_Alloc, org.compiere.model.I_Carrier_Service> COLUMN_Carrier_Service_ID = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "Carrier_Service_ID", org.compiere.model.I_Carrier_Service.class);
	String COLUMNNAME_Carrier_Service_ID = "Carrier_Service_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_Product_Service_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "Created", null);
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

	ModelColumn<I_Carrier_Product_Service_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Carrier_Product_Service_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_Product_Service_Alloc.class, "Updated", null);
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
