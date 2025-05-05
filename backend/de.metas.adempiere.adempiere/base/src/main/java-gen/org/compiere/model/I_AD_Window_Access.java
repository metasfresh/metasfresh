package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Window_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Window_Access 
{

	String Table_Name = "AD_Window_Access";

//	/** AD_Table_ID=201 */
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

	ModelColumn<I_AD_Window_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_AD_Window_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_Window_Access, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Window_Access.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Window_Access, Object> COLUMN_Created = new ModelColumn<>(I_AD_Window_Access.class, "Created", null);
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

	ModelColumn<I_AD_Window_Access, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Window_Access.class, "IsActive", null);
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

	ModelColumn<I_AD_Window_Access, Object> COLUMN_IsReadWrite = new ModelColumn<>(I_AD_Window_Access.class, "IsReadWrite", null);
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

	ModelColumn<I_AD_Window_Access, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Window_Access.class, "Updated", null);
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
