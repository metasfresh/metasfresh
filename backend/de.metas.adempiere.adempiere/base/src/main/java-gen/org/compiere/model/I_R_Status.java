package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for R_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_R_Status 
{

	String Table_Name = "R_Status";

//	/** AD_Table_ID=776 */
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
	 * Set CalendarColor.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCalendarColor (@Nullable java.lang.String CalendarColor);

	/**
	 * Get CalendarColor.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCalendarColor();

	ModelColumn<I_R_Status, Object> COLUMN_CalendarColor = new ModelColumn<>(I_R_Status.class, "CalendarColor", null);
	String COLUMNNAME_CalendarColor = "CalendarColor";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_R_Status, Object> COLUMN_Created = new ModelColumn<>(I_R_Status.class, "Created", null);
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

	ModelColumn<I_R_Status, Object> COLUMN_Description = new ModelColumn<>(I_R_Status.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_R_Status, Object> COLUMN_Help = new ModelColumn<>(I_R_Status.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_R_Status, Object> COLUMN_IsActive = new ModelColumn<>(I_R_Status.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_R_Status, Object> COLUMN_IsClosed = new ModelColumn<>(I_R_Status.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_R_Status, Object> COLUMN_IsDefault = new ModelColumn<>(I_R_Status.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Final Close.
	 * Entries with Final Close cannot be re-opened
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFinalClose (boolean IsFinalClose);

	/**
	 * Get Final Close.
	 * Entries with Final Close cannot be re-opened
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFinalClose();

	ModelColumn<I_R_Status, Object> COLUMN_IsFinalClose = new ModelColumn<>(I_R_Status.class, "IsFinalClose", null);
	String COLUMNNAME_IsFinalClose = "IsFinalClose";

	/**
	 * Set "Offen"-Status.
	 * The status is closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOpen (boolean IsOpen);

	/**
	 * Get "Offen"-Status.
	 * The status is closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOpen();

	ModelColumn<I_R_Status, Object> COLUMN_IsOpen = new ModelColumn<>(I_R_Status.class, "IsOpen", null);
	String COLUMNNAME_IsOpen = "IsOpen";

	/**
	 * Set Web Can Update.
	 * Entry can be updated from the Web
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWebCanUpdate (boolean IsWebCanUpdate);

	/**
	 * Get Web Can Update.
	 * Entry can be updated from the Web
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWebCanUpdate();

	ModelColumn<I_R_Status, Object> COLUMN_IsWebCanUpdate = new ModelColumn<>(I_R_Status.class, "IsWebCanUpdate", null);
	String COLUMNNAME_IsWebCanUpdate = "IsWebCanUpdate";

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

	ModelColumn<I_R_Status, Object> COLUMN_Name = new ModelColumn<>(I_R_Status.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Next Status.
	 * Move to next status automatically after timeout
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNext_Status_ID (int Next_Status_ID);

	/**
	 * Get Next Status.
	 * Move to next status automatically after timeout
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNext_Status_ID();

	@Nullable org.compiere.model.I_R_Status getNext_Status();

	void setNext_Status(@Nullable org.compiere.model.I_R_Status Next_Status);

	ModelColumn<I_R_Status, org.compiere.model.I_R_Status> COLUMN_Next_Status_ID = new ModelColumn<>(I_R_Status.class, "Next_Status_ID", org.compiere.model.I_R_Status.class);
	String COLUMNNAME_Next_Status_ID = "Next_Status_ID";

	/**
	 * Set Status.
	 * Request Status
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setR_Status_ID (int R_Status_ID);

	/**
	 * Get Status.
	 * Request Status
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getR_Status_ID();

	ModelColumn<I_R_Status, Object> COLUMN_R_Status_ID = new ModelColumn<>(I_R_Status.class, "R_Status_ID", null);
	String COLUMNNAME_R_Status_ID = "R_Status_ID";

	/**
	 * Set Status-Kategorie.
	 * Request Status Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setR_StatusCategory_ID (int R_StatusCategory_ID);

	/**
	 * Get Status-Kategorie.
	 * Request Status Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getR_StatusCategory_ID();

	org.compiere.model.I_R_StatusCategory getR_StatusCategory();

	void setR_StatusCategory(org.compiere.model.I_R_StatusCategory R_StatusCategory);

	ModelColumn<I_R_Status, org.compiere.model.I_R_StatusCategory> COLUMN_R_StatusCategory_ID = new ModelColumn<>(I_R_Status.class, "R_StatusCategory_ID", org.compiere.model.I_R_StatusCategory.class);
	String COLUMNNAME_R_StatusCategory_ID = "R_StatusCategory_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_R_Status, Object> COLUMN_SeqNo = new ModelColumn<>(I_R_Status.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Timeout in Days.
	 * Timeout in Days to change Status automatically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeoutDays (int TimeoutDays);

	/**
	 * Get Timeout in Days.
	 * Timeout in Days to change Status automatically
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTimeoutDays();

	ModelColumn<I_R_Status, Object> COLUMN_TimeoutDays = new ModelColumn<>(I_R_Status.class, "TimeoutDays", null);
	String COLUMNNAME_TimeoutDays = "TimeoutDays";

	/**
	 * Set Update Status.
	 * Automatically change the status after entry from web
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUpdate_Status_ID (int Update_Status_ID);

	/**
	 * Get Update Status.
	 * Automatically change the status after entry from web
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdate_Status_ID();

	@Nullable org.compiere.model.I_R_Status getUpdate_Status();

	void setUpdate_Status(@Nullable org.compiere.model.I_R_Status Update_Status);

	ModelColumn<I_R_Status, org.compiere.model.I_R_Status> COLUMN_Update_Status_ID = new ModelColumn<>(I_R_Status.class, "Update_Status_ID", org.compiere.model.I_R_Status.class);
	String COLUMNNAME_Update_Status_ID = "Update_Status_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_R_Status, Object> COLUMN_Updated = new ModelColumn<>(I_R_Status.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_R_Status, Object> COLUMN_Value = new ModelColumn<>(I_R_Status.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
