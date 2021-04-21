package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_PrintFormat
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_PrintFormat 
{

	String Table_Name = "M_Product_PrintFormat";

//	/** AD_Table_ID=541610 */
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
	 * Set Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_PrintFormat_ID();

	org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

	ModelColumn<I_M_Product_PrintFormat, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new ModelColumn<>(I_M_Product_PrintFormat.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_PrintFormat, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_PrintFormat.class, "Created", null);
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

	ModelColumn<I_M_Product_PrintFormat, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_PrintFormat.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product Print Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_PrintFormat_ID (int M_Product_PrintFormat_ID);

	/**
	 * Get Product Print Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_PrintFormat_ID();

	ModelColumn<I_M_Product_PrintFormat, Object> COLUMN_M_Product_PrintFormat_ID = new ModelColumn<>(I_M_Product_PrintFormat.class, "M_Product_PrintFormat_ID", null);
	String COLUMNNAME_M_Product_PrintFormat_ID = "M_Product_PrintFormat_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_PrintFormat, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_PrintFormat.class, "Updated", null);
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
