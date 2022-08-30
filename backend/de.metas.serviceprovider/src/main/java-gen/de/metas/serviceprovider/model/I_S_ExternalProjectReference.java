package de.metas.serviceprovider.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for S_ExternalProjectReference
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_ExternalProjectReference 
{

	String Table_Name = "S_ExternalProjectReference";

//	/** AD_Table_ID=541466 */
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
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_Created = new ModelColumn<>(I_S_ExternalProjectReference.class, "Created", null);
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
	 * Set External project owner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalProjectOwner (java.lang.String ExternalProjectOwner);

	/**
	 * Get External project owner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalProjectOwner();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalProjectOwner = new ModelColumn<>(I_S_ExternalProjectReference.class, "ExternalProjectOwner", null);
	String COLUMNNAME_ExternalProjectOwner = "ExternalProjectOwner";

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

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalReference = new ModelColumn<>(I_S_ExternalProjectReference.class, "ExternalReference", null);
	String COLUMNNAME_ExternalReference = "ExternalReference";

	/**
	 * Set ExternalReferenceURL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalReferenceURL (@Nullable java.lang.String ExternalReferenceURL);

	/**
	 * Get ExternalReferenceURL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalReferenceURL();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalReferenceURL = new ModelColumn<>(I_S_ExternalProjectReference.class, "ExternalReferenceURL", null);
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

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ExternalSystem = new ModelColumn<>(I_S_ExternalProjectReference.class, "ExternalSystem", null);
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

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_IsActive = new ModelColumn<>(I_S_ExternalProjectReference.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Project type.
	 * Specifies the type of a project. ( e.g. Budget, Development )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectType (java.lang.String ProjectType);

	/**
	 * Get Project type.
	 * Specifies the type of a project. ( e.g. Budget, Development )
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjectType();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_ProjectType = new ModelColumn<>(I_S_ExternalProjectReference.class, "ProjectType", null);
	String COLUMNNAME_ProjectType = "ProjectType";

	/**
	 * Set External project reference Effort ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ExternalProjectReference_Effort_ID (int S_ExternalProjectReference_Effort_ID);

	/**
	 * Get External project reference Effort ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ExternalProjectReference_Effort_ID();

	@Nullable de.metas.serviceprovider.model.I_S_ExternalProjectReference getS_ExternalProjectReference_Effort();

	void setS_ExternalProjectReference_Effort(@Nullable de.metas.serviceprovider.model.I_S_ExternalProjectReference S_ExternalProjectReference_Effort);

	ModelColumn<I_S_ExternalProjectReference, de.metas.serviceprovider.model.I_S_ExternalProjectReference> COLUMN_S_ExternalProjectReference_Effort_ID = new ModelColumn<>(I_S_ExternalProjectReference.class, "S_ExternalProjectReference_Effort_ID", de.metas.serviceprovider.model.I_S_ExternalProjectReference.class);
	String COLUMNNAME_S_ExternalProjectReference_Effort_ID = "S_ExternalProjectReference_Effort_ID";

	/**
	 * Set External project reference ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID);

	/**
	 * Get External project reference ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_ExternalProjectReference_ID();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_S_ExternalProjectReference_ID = new ModelColumn<>(I_S_ExternalProjectReference.class, "S_ExternalProjectReference_ID", null);
	String COLUMNNAME_S_ExternalProjectReference_ID = "S_ExternalProjectReference_ID";

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

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_SeqNo = new ModelColumn<>(I_S_ExternalProjectReference.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_ExternalProjectReference, Object> COLUMN_Updated = new ModelColumn<>(I_S_ExternalProjectReference.class, "Updated", null);
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
