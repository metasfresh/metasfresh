package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_User_Assigned_Role
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_User_Assigned_Role 
{

	String Table_Name = "C_User_Assigned_Role";

//	/** AD_Table_ID=541777 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_User_Assigned_Role, Object> COLUMN_Created = new ModelColumn<>(I_C_User_Assigned_Role.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set User Role Assignments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_User_Assigned_Role_ID (int C_User_Assigned_Role_ID);

	/**
	 * Get User Role Assignments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_User_Assigned_Role_ID();

	ModelColumn<I_C_User_Assigned_Role, Object> COLUMN_C_User_Assigned_Role_ID = new ModelColumn<>(I_C_User_Assigned_Role.class, "C_User_Assigned_Role_ID", null);
	String COLUMNNAME_C_User_Assigned_Role_ID = "C_User_Assigned_Role_ID";

	/**
	 * Set User Role.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_User_Role_ID (int C_User_Role_ID);

	/**
	 * Get User Role.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_User_Role_ID();

	org.compiere.model.I_C_User_Role getC_User_Role();

	void setC_User_Role(org.compiere.model.I_C_User_Role C_User_Role);

	ModelColumn<I_C_User_Assigned_Role, org.compiere.model.I_C_User_Role> COLUMN_C_User_Role_ID = new ModelColumn<>(I_C_User_Assigned_Role.class, "C_User_Role_ID", org.compiere.model.I_C_User_Role.class);
	String COLUMNNAME_C_User_Role_ID = "C_User_Role_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_User_Assigned_Role, Object> COLUMN_IsActive = new ModelColumn<>(I_C_User_Assigned_Role.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_User_Assigned_Role, Object> COLUMN_Updated = new ModelColumn<>(I_C_User_Assigned_Role.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
