package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_Product_HazardSymbol
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_HazardSymbol 
{

	String Table_Name = "M_Product_HazardSymbol";

//	/** AD_Table_ID=541965 */
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

	ModelColumn<I_M_Product_HazardSymbol, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_HazardSymbol.class, "Created", null);
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

	ModelColumn<I_M_Product_HazardSymbol, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_HazardSymbol.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Hazard Symbol.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HazardSymbol_ID (int M_HazardSymbol_ID);

	/**
	 * Get Hazard Symbol.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HazardSymbol_ID();

	@Nullable org.compiere.model.I_M_HazardSymbol getM_HazardSymbol();

	void setM_HazardSymbol(@Nullable org.compiere.model.I_M_HazardSymbol M_HazardSymbol);

	ModelColumn<I_M_Product_HazardSymbol, org.compiere.model.I_M_HazardSymbol> COLUMN_M_HazardSymbol_ID = new ModelColumn<>(I_M_Product_HazardSymbol.class, "M_HazardSymbol_ID", org.compiere.model.I_M_HazardSymbol.class);
	String COLUMNNAME_M_HazardSymbol_ID = "M_HazardSymbol_ID";

	/**
	 * Set Product Hazard Symbol.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_HazardSymbol_ID (int M_Product_HazardSymbol_ID);

	/**
	 * Get Product Hazard Symbol.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_HazardSymbol_ID();

	ModelColumn<I_M_Product_HazardSymbol, Object> COLUMN_M_Product_HazardSymbol_ID = new ModelColumn<>(I_M_Product_HazardSymbol.class, "M_Product_HazardSymbol_ID", null);
	String COLUMNNAME_M_Product_HazardSymbol_ID = "M_Product_HazardSymbol_ID";

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
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_HazardSymbol, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_HazardSymbol.class, "Updated", null);
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
