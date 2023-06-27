package de.metas.invoicecandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_ILCandHandler
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ILCandHandler 
{

	String Table_Name = "C_ILCandHandler";

//	/** AD_Table_ID=540340 */
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
	 * Set Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Invoicecandidate Controller.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ILCandHandler_ID (int C_ILCandHandler_ID);

	/**
	 * Get Invoicecandidate Controller.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ILCandHandler_ID();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_C_ILCandHandler_ID = new ModelColumn<>(I_C_ILCandHandler.class, "C_ILCandHandler_ID", null);
	String COLUMNNAME_C_ILCandHandler_ID = "C_ILCandHandler_ID";

	/**
	 * Set Java Class.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClassname (java.lang.String Classname);

	/**
	 * Get Java Class.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClassname();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Classname = new ModelColumn<>(I_C_ILCandHandler.class, "Classname", null);
	String COLUMNNAME_Classname = "Classname";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Created = new ModelColumn<>(I_C_ILCandHandler.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Description = new ModelColumn<>(I_C_ILCandHandler.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_EntityType = new ModelColumn<>(I_C_ILCandHandler.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

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

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ILCandHandler.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set User in Charge editable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIs_AD_User_InCharge_UI_Setting (boolean Is_AD_User_InCharge_UI_Setting);

	/**
	 * Get User in Charge editable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean is_AD_User_InCharge_UI_Setting();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Is_AD_User_InCharge_UI_Setting = new ModelColumn<>(I_C_ILCandHandler.class, "Is_AD_User_InCharge_UI_Setting", null);
	String COLUMNNAME_Is_AD_User_InCharge_UI_Setting = "Is_AD_User_InCharge_UI_Setting";

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

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Name = new ModelColumn<>(I_C_ILCandHandler.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Tablename.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTableName (java.lang.String TableName);

	/**
	 * Get Tablename.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTableName();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_TableName = new ModelColumn<>(I_C_ILCandHandler.class, "TableName", null);
	String COLUMNNAME_TableName = "TableName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ILCandHandler, Object> COLUMN_Updated = new ModelColumn<>(I_C_ILCandHandler.class, "Updated", null);
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
