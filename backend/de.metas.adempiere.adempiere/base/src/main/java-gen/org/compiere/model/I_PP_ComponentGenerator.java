package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_ComponentGenerator
 *  @author metasfresh (generated) 
 */
public interface I_PP_ComponentGenerator 
{

	String Table_Name = "PP_ComponentGenerator";

//	/** AD_Table_ID=541554 */
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
	 * Set AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get AD_JavaClass.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_JavaClass_ID();

	ModelColumn<I_PP_ComponentGenerator, Object> COLUMN_AD_JavaClass_ID = new ModelColumn<>(I_PP_ComponentGenerator.class, "AD_JavaClass_ID", null);
	String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

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
	 * Set Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Sequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getAD_Sequence();

	void setAD_Sequence(@Nullable org.compiere.model.I_AD_Sequence AD_Sequence);

	ModelColumn<I_PP_ComponentGenerator, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ID = new ModelColumn<>(I_PP_ComponentGenerator.class, "AD_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_ComponentGenerator, Object> COLUMN_Created = new ModelColumn<>(I_PP_ComponentGenerator.class, "Created", null);
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

	ModelColumn<I_PP_ComponentGenerator, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_ComponentGenerator.class, "IsActive", null);
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
	 * Set PP_ComponentGenerator.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_ComponentGenerator_ID (int PP_ComponentGenerator_ID);

	/**
	 * Get PP_ComponentGenerator.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_ComponentGenerator_ID();

	ModelColumn<I_PP_ComponentGenerator, Object> COLUMN_PP_ComponentGenerator_ID = new ModelColumn<>(I_PP_ComponentGenerator.class, "PP_ComponentGenerator_ID", null);
	String COLUMNNAME_PP_ComponentGenerator_ID = "PP_ComponentGenerator_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_ComponentGenerator, Object> COLUMN_Updated = new ModelColumn<>(I_PP_ComponentGenerator.class, "Updated", null);
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
