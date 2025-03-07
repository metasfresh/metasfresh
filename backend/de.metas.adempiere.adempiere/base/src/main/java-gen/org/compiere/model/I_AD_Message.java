package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Message
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Message 
{

	String Table_Name = "AD_Message";

//	/** AD_Table_ID=109 */
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
	 * Set Message.
	 * System Message
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Message.
	 * System Message
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Message_ID();

	ModelColumn<I_AD_Message, Object> COLUMN_AD_Message_ID = new ModelColumn<>(I_AD_Message.class, "AD_Message_ID", null);
	String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Message, Object> COLUMN_Created = new ModelColumn<>(I_AD_Message.class, "Created", null);
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

	ModelColumn<I_AD_Message, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Message.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Error Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorCode (@Nullable java.lang.String ErrorCode);

	/**
	 * Get Error Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorCode();

	ModelColumn<I_AD_Message, Object> COLUMN_ErrorCode = new ModelColumn<>(I_AD_Message.class, "ErrorCode", null);
	String COLUMNNAME_ErrorCode = "ErrorCode";

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

	ModelColumn<I_AD_Message, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Message.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMsgText (java.lang.String MsgText);

	/**
	 * Get Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMsgText();

	ModelColumn<I_AD_Message, Object> COLUMN_MsgText = new ModelColumn<>(I_AD_Message.class, "MsgText", null);
	String COLUMNNAME_MsgText = "MsgText";

	/**
	 * Set Message Tip.
	 * Additional tip or help for this message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMsgTip (@Nullable java.lang.String MsgTip);

	/**
	 * Get Message Tip.
	 * Additional tip or help for this message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMsgTip();

	ModelColumn<I_AD_Message, Object> COLUMN_MsgTip = new ModelColumn<>(I_AD_Message.class, "MsgTip", null);
	String COLUMNNAME_MsgTip = "MsgTip";

	/**
	 * Set Message Type.
	 * Type of message (Informational, Menu or Error)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMsgType (java.lang.String MsgType);

	/**
	 * Get Message Type.
	 * Type of message (Informational, Menu or Error)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMsgType();

	ModelColumn<I_AD_Message, Object> COLUMN_MsgType = new ModelColumn<>(I_AD_Message.class, "MsgType", null);
	String COLUMNNAME_MsgType = "MsgType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Message, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Message.class, "Updated", null);
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

	ModelColumn<I_AD_Message, Object> COLUMN_Value = new ModelColumn<>(I_AD_Message.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
