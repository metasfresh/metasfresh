package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ModCntr_InvoicingGroup_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_InvoicingGroup_Product 
{

	String Table_Name = "ModCntr_InvoicingGroup_Product";

//	/** AD_Table_ID=542360 */
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

	ModelColumn<I_ModCntr_InvoicingGroup_Product, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_InvoicingGroup_Product.class, "Created", null);
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

	ModelColumn<I_ModCntr_InvoicingGroup_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_InvoicingGroup_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_ID (int ModCntr_InvoicingGroup_ID);

	/**
	 * Get Invoice Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_ID();

	de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup();

	void setModCntr_InvoicingGroup(de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup);

	ModelColumn<I_ModCntr_InvoicingGroup_Product, de.metas.contracts.model.I_ModCntr_InvoicingGroup> COLUMN_ModCntr_InvoicingGroup_ID = new ModelColumn<>(I_ModCntr_InvoicingGroup_Product.class, "ModCntr_InvoicingGroup_ID", de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	String COLUMNNAME_ModCntr_InvoicingGroup_ID = "ModCntr_InvoicingGroup_ID";

	/**
	 * Set Rechnungsgruppe Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_Product_ID (int ModCntr_InvoicingGroup_Product_ID);

	/**
	 * Get Rechnungsgruppe Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_Product_ID();

	ModelColumn<I_ModCntr_InvoicingGroup_Product, Object> COLUMN_ModCntr_InvoicingGroup_Product_ID = new ModelColumn<>(I_ModCntr_InvoicingGroup_Product.class, "ModCntr_InvoicingGroup_Product_ID", null);
	String COLUMNNAME_ModCntr_InvoicingGroup_Product_ID = "ModCntr_InvoicingGroup_Product_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
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

	ModelColumn<I_ModCntr_InvoicingGroup_Product, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_InvoicingGroup_Product.class, "Updated", null);
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
