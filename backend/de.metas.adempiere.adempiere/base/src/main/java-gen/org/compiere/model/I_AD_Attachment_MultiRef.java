package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Attachment_MultiRef
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Attachment_MultiRef 
{

	String Table_Name = "AD_Attachment_MultiRef";

//	/** AD_Table_ID=541143 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set AD_Attachment_MultiRef.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Attachment_MultiRef_ID (int AD_Attachment_MultiRef_ID);

	/**
	 * Get AD_Attachment_MultiRef.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Attachment_MultiRef_ID();

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_AD_Attachment_MultiRef_ID = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "AD_Attachment_MultiRef_ID", null);
	String COLUMNNAME_AD_Attachment_MultiRef_ID = "AD_Attachment_MultiRef_ID";

	/**
	 * Set Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_AttachmentEntry_ID();

	org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry();

	void setAD_AttachmentEntry(org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry);

	ModelColumn<I_AD_Attachment_MultiRef, org.compiere.model.I_AD_AttachmentEntry> COLUMN_AD_AttachmentEntry_ID = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "AD_AttachmentEntry_ID", org.compiere.model.I_AD_AttachmentEntry.class);
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_Created = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "Created", null);
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
	 * Set FileName_Override.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName_Override (@Nullable java.lang.String FileName_Override);

	/**
	 * Get FileName_Override.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFileName_Override();

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_FileName_Override = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "FileName_Override", null);
	String COLUMNNAME_FileName_Override = "FileName_Override";

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

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Attachment_MultiRef, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Attachment_MultiRef.class, "Updated", null);
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
