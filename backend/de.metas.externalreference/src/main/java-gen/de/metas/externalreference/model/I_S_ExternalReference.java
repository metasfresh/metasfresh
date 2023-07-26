package de.metas.externalreference.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for S_ExternalReference
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_ExternalReference 
{

	String Table_Name = "S_ExternalReference";

//	/** AD_Table_ID=541486 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Created = new ModelColumn<>(I_S_ExternalReference.class, "Created", null);
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
	 * Set External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalReference (java.lang.String ExternalReference);

	/**
	 * Get External reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalReference();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_ExternalReference = new ModelColumn<>(I_S_ExternalReference.class, "ExternalReference", null);
	String COLUMNNAME_ExternalReference = "ExternalReference";

	/**
	 * Set URL in external system.
	 * If a record was synched from an external system, this field can be used to store its URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalReferenceURL (@Nullable java.lang.String ExternalReferenceURL);

	/**
	 * Get URL in external system.
	 * If a record was synched from an external system, this field can be used to store its URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalReferenceURL();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_ExternalReferenceURL = new ModelColumn<>(I_S_ExternalReference.class, "ExternalReferenceURL", null);
	String COLUMNNAME_ExternalReferenceURL = "ExternalReferenceURL";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem (java.lang.String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalSystem();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_ExternalSystem = new ModelColumn<>(I_S_ExternalReference.class, "ExternalSystem", null);
	String COLUMNNAME_ExternalSystem = "ExternalSystem";

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

	ModelColumn<I_S_ExternalReference, Object> COLUMN_IsActive = new ModelColumn<>(I_S_ExternalReference.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Record_ID = new ModelColumn<>(I_S_ExternalReference.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Referenced table ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReferenced_AD_Table_ID (int Referenced_AD_Table_ID);

	/**
	 * Get Referenced table ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getReferenced_AD_Table_ID();

	String COLUMNNAME_Referenced_AD_Table_ID = "Referenced_AD_Table_ID";

	/**
	 * Set Referenced record ID.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReferenced_Record_ID (int Referenced_Record_ID);

	/**
	 * Get Referenced record ID.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getReferenced_Record_ID();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Referenced_Record_ID = new ModelColumn<>(I_S_ExternalReference.class, "Referenced_Record_ID", null);
	String COLUMNNAME_Referenced_Record_ID = "Referenced_Record_ID";

	/**
	 * Set External reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ExternalReference_ID (int S_ExternalReference_ID);

	/**
	 * Get External reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ExternalReference_ID();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_S_ExternalReference_ID = new ModelColumn<>(I_S_ExternalReference.class, "S_ExternalReference_ID", null);
	String COLUMNNAME_S_ExternalReference_ID = "S_ExternalReference_ID";

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

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Type = new ModelColumn<>(I_S_ExternalReference.class, "Type", null);
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

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Updated = new ModelColumn<>(I_S_ExternalReference.class, "Updated", null);
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
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVersion (@Nullable java.lang.String Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVersion();

	ModelColumn<I_S_ExternalReference, Object> COLUMN_Version = new ModelColumn<>(I_S_ExternalReference.class, "Version", null);
	String COLUMNNAME_Version = "Version";
}
