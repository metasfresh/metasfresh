package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_DiscountSchemaBreak_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_DiscountSchemaBreak_V 
{

	String Table_Name = "M_DiscountSchemaBreak_V";

//	/** AD_Table_ID=541586 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_DiscountSchemaBreak_V, Object> COLUMN_Created = new ModelColumn<>(I_M_DiscountSchemaBreak_V.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
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

	ModelColumn<I_M_DiscountSchemaBreak_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_DiscountSchemaBreak_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	void setM_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

	ModelColumn<I_M_DiscountSchemaBreak_V, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_M_DiscountSchemaBreak_V.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set M_DiscountSchemaBreak_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchemaBreak_V_ID (int M_DiscountSchemaBreak_V_ID);

	/**
	 * Get M_DiscountSchemaBreak_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchemaBreak_V_ID();

	ModelColumn<I_M_DiscountSchemaBreak_V, Object> COLUMN_M_DiscountSchemaBreak_V_ID = new ModelColumn<>(I_M_DiscountSchemaBreak_V.class, "M_DiscountSchemaBreak_V_ID", null);
	String COLUMNNAME_M_DiscountSchemaBreak_V_ID = "M_DiscountSchemaBreak_V_ID";

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_DiscountSchemaBreak_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_DiscountSchemaBreak_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
