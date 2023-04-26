package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_Quality_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Quality_Attribute 
{

	String Table_Name = "M_Quality_Attribute";

//	/** AD_Table_ID=541952 */
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

	ModelColumn<I_M_Quality_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_M_Quality_Attribute.class, "Created", null);
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

	ModelColumn<I_M_Quality_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Quality_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Quality Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Quality_Attribute_ID (int M_Quality_Attribute_ID);

	/**
	 * Get Quality Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Quality_Attribute_ID();

	ModelColumn<I_M_Quality_Attribute, Object> COLUMN_M_Quality_Attribute_ID = new ModelColumn<>(I_M_Quality_Attribute.class, "M_Quality_Attribute_ID", null);
	String COLUMNNAME_M_Quality_Attribute_ID = "M_Quality_Attribute_ID";

	/**
	 * Set Quality.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Quality_ID (int M_Quality_ID);

	/**
	 * Get Quality.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Quality_ID();

	@Nullable org.compiere.model.I_M_Quality getM_Quality();

	void setM_Quality(@Nullable org.compiere.model.I_M_Quality M_Quality);

	ModelColumn<I_M_Quality_Attribute, org.compiere.model.I_M_Quality> COLUMN_M_Quality_ID = new ModelColumn<>(I_M_Quality_Attribute.class, "M_Quality_ID", org.compiere.model.I_M_Quality.class);
	String COLUMNNAME_M_Quality_ID = "M_Quality_ID";

	/**
	 * Set Quality Attribute.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityAttribute (@Nullable java.lang.String QualityAttribute);

	/**
	 * Get Quality Attribute.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQualityAttribute();

	ModelColumn<I_M_Quality_Attribute, Object> COLUMN_QualityAttribute = new ModelColumn<>(I_M_Quality_Attribute.class, "QualityAttribute", null);
	String COLUMNNAME_QualityAttribute = "QualityAttribute";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Quality_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_M_Quality_Attribute.class, "Updated", null);
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
