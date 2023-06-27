package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Menu
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Menu 
{

	String Table_Name = "AD_Menu";

//	/** AD_Table_ID=116 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAction (@Nullable java.lang.String Action);

	/**
	 * Get Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAction();

	ModelColumn<I_AD_Menu, Object> COLUMN_Action = new ModelColumn<>(I_AD_Menu.class, "Action", null);
	String COLUMNNAME_Action = "Action";

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
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Element_ID();

	org.compiere.model.I_AD_Element getAD_Element();

	void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Form_ID();

	@Nullable org.compiere.model.I_AD_Form getAD_Form();

	void setAD_Form(@Nullable org.compiere.model.I_AD_Form AD_Form);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
	String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Menü.
	 * Identifies a Menu
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Menu_ID (int AD_Menu_ID);

	/**
	 * Get Menü.
	 * Identifies a Menu
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Menu_ID();

	ModelColumn<I_AD_Menu, Object> COLUMN_AD_Menu_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Menu_ID", null);
	String COLUMNNAME_AD_Menu_ID = "AD_Menu_ID";

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
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Task_ID();

	@Nullable org.compiere.model.I_AD_Task getAD_Task();

	void setAD_Task(@Nullable org.compiere.model.I_AD_Task AD_Task);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
	String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(@Nullable org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Workbench.
	 * Collection of windows, reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Workbench_ID (int AD_Workbench_ID);

	/**
	 * Get Workbench.
	 * Collection of windows, reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Workbench_ID();

	@Nullable org.compiere.model.I_AD_Workbench getAD_Workbench();

	void setAD_Workbench(@Nullable org.compiere.model.I_AD_Workbench AD_Workbench);

	ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Workbench> COLUMN_AD_Workbench_ID = new ModelColumn<>(I_AD_Menu.class, "AD_Workbench_ID", org.compiere.model.I_AD_Workbench.class);
	String COLUMNNAME_AD_Workbench_ID = "AD_Workbench_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Menu, Object> COLUMN_Created = new ModelColumn<>(I_AD_Menu.class, "Created", null);
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

	ModelColumn<I_AD_Menu, Object> COLUMN_Description = new ModelColumn<>(I_AD_Menu.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_Menu, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Menu.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInternalName (java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInternalName();

	ModelColumn<I_AD_Menu, Object> COLUMN_InternalName = new ModelColumn<>(I_AD_Menu.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_AD_Menu, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Menu.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create new.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateNew (boolean IsCreateNew);

	/**
	 * Get Create new.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateNew();

	ModelColumn<I_AD_Menu, Object> COLUMN_IsCreateNew = new ModelColumn<>(I_AD_Menu.class, "IsCreateNew", null);
	String COLUMNNAME_IsCreateNew = "IsCreateNew";

	/**
	 * Set Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnly();

	ModelColumn<I_AD_Menu, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_AD_Menu.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_AD_Menu, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_AD_Menu.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_AD_Menu, Object> COLUMN_IsSummary = new ModelColumn<>(I_AD_Menu.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

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

	ModelColumn<I_AD_Menu, Object> COLUMN_Name = new ModelColumn<>(I_AD_Menu.class, "Name", null);
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

	ModelColumn<I_AD_Menu, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Menu.class, "Updated", null);
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_ID (int WEBUI_Board_ID);

	/**
	 * Get Board.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_ID();

	ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_Board_ID = new ModelColumn<>(I_AD_Menu.class, "WEBUI_Board_ID", null);
	String COLUMNNAME_WEBUI_Board_ID = "WEBUI_Board_ID";

	/**
	 * Set Browse name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_NameBrowse (@Nullable java.lang.String WEBUI_NameBrowse);

	/**
	 * Get Browse name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWEBUI_NameBrowse();

	ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameBrowse = new ModelColumn<>(I_AD_Menu.class, "WEBUI_NameBrowse", null);
	String COLUMNNAME_WEBUI_NameBrowse = "WEBUI_NameBrowse";

	/**
	 * Set New record name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_NameNew (@Nullable java.lang.String WEBUI_NameNew);

	/**
	 * Get New record name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWEBUI_NameNew();

	ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameNew = new ModelColumn<>(I_AD_Menu.class, "WEBUI_NameNew", null);
	String COLUMNNAME_WEBUI_NameNew = "WEBUI_NameNew";

	/**
	 * Set New record name (breadcrumb).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_NameNewBreadcrumb (@Nullable java.lang.String WEBUI_NameNewBreadcrumb);

	/**
	 * Get New record name (breadcrumb).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWEBUI_NameNewBreadcrumb();

	ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameNewBreadcrumb = new ModelColumn<>(I_AD_Menu.class, "WEBUI_NameNewBreadcrumb", null);
	String COLUMNNAME_WEBUI_NameNewBreadcrumb = "WEBUI_NameNewBreadcrumb";
}
