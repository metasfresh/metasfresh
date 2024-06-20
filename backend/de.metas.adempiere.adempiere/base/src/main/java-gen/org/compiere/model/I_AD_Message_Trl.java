package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Message_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Message_Trl 
{

	String Table_Name = "AD_Message_Trl";

//	/** AD_Table_ID=119 */
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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAD_Language();

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_AD_Language = new ModelColumn<>(I_AD_Message_Trl.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Message.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Message.
	 * System Message
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Message_ID();

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

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_Created = new ModelColumn<>(I_AD_Message_Trl.class, "Created", null);
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

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Message_Trl.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Translated.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTranslated (boolean IsTranslated);

	/**
	 * Get Translated.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTranslated();

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_IsTranslated = new ModelColumn<>(I_AD_Message_Trl.class, "IsTranslated", null);
	String COLUMNNAME_IsTranslated = "IsTranslated";

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

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_MsgText = new ModelColumn<>(I_AD_Message_Trl.class, "MsgText", null);
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

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_MsgTip = new ModelColumn<>(I_AD_Message_Trl.class, "MsgTip", null);
	String COLUMNNAME_MsgTip = "MsgTip";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Message_Trl, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Message_Trl.class, "Updated", null);
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
