package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_AttachmentEntry
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_AttachmentEntry 
{

	String Table_Name = "AD_AttachmentEntry";

//	/** AD_Table_ID=540833 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Attachment.
	 * Attachment for the document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Attachment_ID (int AD_Attachment_ID);

	/**
	 * Get Attachment.
	 * Attachment for the document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Attachment_ID();

	@Nullable org.compiere.model.I_AD_Attachment getAD_Attachment();

	void setAD_Attachment(@Nullable org.compiere.model.I_AD_Attachment AD_Attachment);

	ModelColumn<I_AD_AttachmentEntry, org.compiere.model.I_AD_Attachment> COLUMN_AD_Attachment_ID = new ModelColumn<>(I_AD_AttachmentEntry.class, "AD_Attachment_ID", org.compiere.model.I_AD_Attachment.class);
	String COLUMNNAME_AD_Attachment_ID = "AD_Attachment_ID";

	/**
	 * Set Attachment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_AttachmentEntry_ID();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_AD_AttachmentEntry_ID = new ModelColumn<>(I_AD_AttachmentEntry.class, "AD_AttachmentEntry_ID", null);
	String COLUMNNAME_AD_AttachmentEntry_ID = "AD_AttachmentEntry_ID";

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
	 * Set Binary Data.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	void setBinaryData (@Nullable byte[] BinaryData);

	/**
	 * Get Binary Data.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	@Nullable byte[] getBinaryData();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_BinaryData = new ModelColumn<>(I_AD_AttachmentEntry.class, "BinaryData", null);
	String COLUMNNAME_BinaryData = "BinaryData";

	/**
	 * Set Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContentType (@Nullable java.lang.String ContentType);

	/**
	 * Get Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContentType();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_ContentType = new ModelColumn<>(I_AD_AttachmentEntry.class, "ContentType", null);
	String COLUMNNAME_ContentType = "ContentType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Created = new ModelColumn<>(I_AD_AttachmentEntry.class, "Created", null);
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
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Description = new ModelColumn<>(I_AD_AttachmentEntry.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFileName (java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFileName();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_FileName = new ModelColumn<>(I_AD_AttachmentEntry.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

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

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_AttachmentEntry.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tags.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTags (@Nullable java.lang.String Tags);

	/**
	 * Get Tags.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTags();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Tags = new ModelColumn<>(I_AD_AttachmentEntry.class, "Tags", null);
	String COLUMNNAME_Tags = "Tags";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Type = new ModelColumn<>(I_AD_AttachmentEntry.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_Updated = new ModelColumn<>(I_AD_AttachmentEntry.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_AD_AttachmentEntry, Object> COLUMN_URL = new ModelColumn<>(I_AD_AttachmentEntry.class, "URL", null);
	String COLUMNNAME_URL = "URL";
}
