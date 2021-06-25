package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for WEBUI_Board
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_Board 
{

	String Table_Name = "WEBUI_Board";

//	/** AD_Table_ID=540824 */
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
	 * Set Dynamische Validierung.
	 * Regel für die  dynamische Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Dynamische Validierung.
	 * Regel für die  dynamische Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	void setAD_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

	ModelColumn<I_WEBUI_Board, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_WEBUI_Board.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_WEBUI_Board, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_Board.class, "Created", null);
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

	ModelColumn<I_WEBUI_Board, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_Board.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_WEBUI_Board, Object> COLUMN_Name = new ModelColumn<>(I_WEBUI_Board.class, "Name", null);
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

	ModelColumn<I_WEBUI_Board, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_Board.class, "Updated", null);
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

	/**
	 * Set Board.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_ID (int WEBUI_Board_ID);

	/**
	 * Get Board.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_ID();

	ModelColumn<I_WEBUI_Board, Object> COLUMN_WEBUI_Board_ID = new ModelColumn<>(I_WEBUI_Board.class, "WEBUI_Board_ID", null);
	String COLUMNNAME_WEBUI_Board_ID = "WEBUI_Board_ID";
}
