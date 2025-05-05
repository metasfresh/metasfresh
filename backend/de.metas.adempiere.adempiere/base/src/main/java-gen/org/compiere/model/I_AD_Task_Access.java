package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Task_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Task_Access 
{

	String Table_Name = "AD_Task_Access";

//	/** AD_Table_ID=199 */
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
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_AD_Task_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_AD_Task_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set External Process.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get External Process.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Task_ID();

	org.compiere.model.I_AD_Task getAD_Task();

	void setAD_Task(org.compiere.model.I_AD_Task AD_Task);

	ModelColumn<I_AD_Task_Access, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new ModelColumn<>(I_AD_Task_Access.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
	String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Task_Access, Object> COLUMN_Created = new ModelColumn<>(I_AD_Task_Access.class, "Created", null);
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

	ModelColumn<I_AD_Task_Access, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Task_Access.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Read Write.
	 * Feld / Eintrag / Bereich kann gelesen und verändert werden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadWrite (boolean IsReadWrite);

	/**
	 * Get Read Write.
	 * Feld / Eintrag / Bereich kann gelesen und verändert werden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadWrite();

	ModelColumn<I_AD_Task_Access, Object> COLUMN_IsReadWrite = new ModelColumn<>(I_AD_Task_Access.class, "IsReadWrite", null);
	String COLUMNNAME_IsReadWrite = "IsReadWrite";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Task_Access, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Task_Access.class, "Updated", null);
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
