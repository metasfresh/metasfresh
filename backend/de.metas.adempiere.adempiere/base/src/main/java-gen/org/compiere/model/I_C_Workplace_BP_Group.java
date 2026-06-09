package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Workplace_BP_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Workplace_BP_Group 
{

	String Table_Name = "C_Workplace_BP_Group";

//	/** AD_Table_ID=542615 */
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
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_ID();

	ModelColumn<I_C_Workplace_BP_Group, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_C_Workplace_BP_Group.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Workplace_BP_Group, Object> COLUMN_Created = new ModelColumn<>(I_C_Workplace_BP_Group.class, "Created", null);
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
	 * Set Workplace BPartner Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_BP_Group_ID (int C_Workplace_BP_Group_ID);

	/**
	 * Get Workplace BPartner Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_BP_Group_ID();

	ModelColumn<I_C_Workplace_BP_Group, Object> COLUMN_C_Workplace_BP_Group_ID = new ModelColumn<>(I_C_Workplace_BP_Group.class, "C_Workplace_BP_Group_ID", null);
	String COLUMNNAME_C_Workplace_BP_Group_ID = "C_Workplace_BP_Group_ID";

	/**
	 * Set Workplace.
	 * Logical area within a warehouse, to which one or more Workstations can be assigned. An operator logs into exactly one Workplace per shift. The associated warehouse (M_Warehouse_ID) drives the storage location of the work performed there.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 * Logical area within a warehouse, to which one or more Workstations can be assigned. An operator logs into exactly one Workplace per shift. The associated warehouse (M_Warehouse_ID) drives the storage location of the work performed there.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	ModelColumn<I_C_Workplace_BP_Group, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_C_Workplace_BP_Group.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

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

	ModelColumn<I_C_Workplace_BP_Group, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Workplace_BP_Group.class, "IsActive", null);
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

	ModelColumn<I_C_Workplace_BP_Group, Object> COLUMN_Updated = new ModelColumn<>(I_C_Workplace_BP_Group.class, "Updated", null);
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
