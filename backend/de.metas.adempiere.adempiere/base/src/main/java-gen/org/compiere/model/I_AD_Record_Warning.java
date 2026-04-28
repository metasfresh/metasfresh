package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Record_Warning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Record_Warning 
{

	String Table_Name = "AD_Record_Warning";

//	/** AD_Table_ID=542455 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_ID (int AD_BusinessRule_ID);

	/**
	 * Get Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_ID();

	@Nullable org.compiere.model.I_AD_BusinessRule getAD_BusinessRule();

	void setAD_BusinessRule(@Nullable org.compiere.model.I_AD_BusinessRule AD_BusinessRule);

	ModelColumn<I_AD_Record_Warning, org.compiere.model.I_AD_BusinessRule> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_Record_Warning.class, "AD_BusinessRule_ID", org.compiere.model.I_AD_BusinessRule.class);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

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
	 * Set Warnings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Record_Warning_ID (int AD_Record_Warning_ID);

	/**
	 * Get Warnings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Record_Warning_ID();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_AD_Record_Warning_ID = new ModelColumn<>(I_AD_Record_Warning.class, "AD_Record_Warning_ID", null);
	String COLUMNNAME_AD_Record_Warning_ID = "AD_Record_Warning_ID";

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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_Created = new ModelColumn<>(I_AD_Record_Warning.class, "Created", null);
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
	 * Set Acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAcknowledged (boolean IsAcknowledged);

	/**
	 * Get Acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAcknowledged();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_IsAcknowledged = new ModelColumn<>(I_AD_Record_Warning.class, "IsAcknowledged", null);
	String COLUMNNAME_IsAcknowledged = "IsAcknowledged";

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

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Record_Warning.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMsgText (java.lang.String MsgText);

	/**
	 * Get Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMsgText();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_MsgText = new ModelColumn<>(I_AD_Record_Warning.class, "MsgText", null);
	String COLUMNNAME_MsgText = "MsgText";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_Record_Warning.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Root Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRoot_AD_Table_ID (int Root_AD_Table_ID);

	/**
	 * Get Root Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRoot_AD_Table_ID();

	String COLUMNNAME_Root_AD_Table_ID = "Root_AD_Table_ID";

	/**
	 * Set Root Record.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRoot_Record_ID (int Root_Record_ID);

	/**
	 * Get Root Record.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRoot_Record_ID();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_Root_Record_ID = new ModelColumn<>(I_AD_Record_Warning.class, "Root_Record_ID", null);
	String COLUMNNAME_Root_Record_ID = "Root_Record_ID";

	/**
	 * Set Severity.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeverity (java.lang.String Severity);

	/**
	 * Get Severity.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSeverity();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_Severity = new ModelColumn<>(I_AD_Record_Warning.class, "Severity", null);
	String COLUMNNAME_Severity = "Severity";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Record_Warning, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Record_Warning.class, "Updated", null);
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
