package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_AttributeSet_IncludedTab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_AttributeSet_IncludedTab 
{

	String Table_Name = "M_AttributeSet_IncludedTab";

//	/** AD_Table_ID=542453 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_AttributeSet_IncludedTab, Object> COLUMN_Created = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "Created", null);
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

	ModelColumn<I_M_AttributeSet_IncludedTab, Object> COLUMN_IsActive = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

	ModelColumn<I_M_AttributeSet_IncludedTab, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Included Tab.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_IncludedTab_ID (int M_AttributeSet_IncludedTab_ID);

	/**
	 * Get Included Tab.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_IncludedTab_ID();

	ModelColumn<I_M_AttributeSet_IncludedTab, Object> COLUMN_M_AttributeSet_IncludedTab_ID = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "M_AttributeSet_IncludedTab_ID", null);
	String COLUMNNAME_M_AttributeSet_IncludedTab_ID = "M_AttributeSet_IncludedTab_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_M_AttributeSet_IncludedTab, Object> COLUMN_Name = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_AttributeSet_IncludedTab, Object> COLUMN_Updated = new ModelColumn<>(I_M_AttributeSet_IncludedTab.class, "Updated", null);
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
