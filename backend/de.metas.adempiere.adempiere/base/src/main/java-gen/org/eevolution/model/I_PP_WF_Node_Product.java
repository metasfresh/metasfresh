package org.eevolution.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Generated Interface for PP_WF_Node_Product
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_PP_WF_Node_Product
{

	String Table_Name = "PP_WF_Node_Product";

	//	/** AD_Table_ID=53016 */
	//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_ConfigurationLevel = new ModelColumn<>(I_PP_WF_Node_Product.class, "ConfigurationLevel", null);
	String COLUMNNAME_ConfigurationLevel = "ConfigurationLevel";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_Created = new ModelColumn<>(I_PP_WF_Node_Product.class, "Created", null);
	String COLUMNNAME_Created = "Created";
	String COLUMNNAME_CreatedBy = "CreatedBy";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_EntityType = new ModelColumn<>(I_PP_WF_Node_Product.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_WF_Node_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_IsSubcontracting = new ModelColumn<>(I_PP_WF_Node_Product.class, "IsSubcontracting", null);
	String COLUMNNAME_IsSubcontracting = "IsSubcontracting";
	String COLUMNNAME_M_Product_ID = "M_Product_ID";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_PP_WF_Node_Product_ID = new ModelColumn<>(I_PP_WF_Node_Product.class, "PP_WF_Node_Product_ID", null);
	String COLUMNNAME_PP_WF_Node_Product_ID = "PP_WF_Node_Product_ID";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_Qty = new ModelColumn<>(I_PP_WF_Node_Product.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_SeqNo = new ModelColumn<>(I_PP_WF_Node_Product.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_Specification = new ModelColumn<>(I_PP_WF_Node_Product.class, "Specification", null);
	String COLUMNNAME_Specification = "Specification";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_Updated = new ModelColumn<>(I_PP_WF_Node_Product.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
	ModelColumn<I_PP_WF_Node_Product, Object> COLUMN_Yield = new ModelColumn<>(I_PP_WF_Node_Product.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID(int AD_WF_Node_ID);

	/**
	 * Get Configuration LEVEL.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getConfigurationLevel();

	/**
	 * Set Configuration LEVEL.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConfigurationLevel(@Nullable java.lang.String ConfigurationLevel);

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
	 * Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
	 * Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType(java.lang.String EntityType);

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	/**
	 * Set Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubcontracting(boolean IsSubcontracting);

	/**
	 * Get Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubcontracting();

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID(int M_Product_ID);

	/**
	 * Get Manufacturing Products.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_WF_Node_Product_ID();

	/**
	 * Set Manufacturing Products.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_WF_Node_Product_ID(int PP_WF_Node_Product_ID);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty(@Nullable BigDecimal Qty);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
	 * lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	/**
	 * Set SeqNo.
	 * Method of ordering records;
	 * lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo(int SeqNo);

	/**
	 * Get Specification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getSpecification();

	/**
	 * Set Specification.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSpecification(@Nullable java.lang.String Specification);

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getYield();

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setYield(@Nullable BigDecimal Yield);
}
