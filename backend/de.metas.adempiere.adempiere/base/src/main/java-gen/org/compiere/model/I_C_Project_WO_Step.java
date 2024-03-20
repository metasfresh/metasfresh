package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_Step 
{

	String Table_Name = "C_Project_WO_Step";

//	/** AD_Table_ID=542159 */
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
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Step.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Step_ID (int C_Project_WO_Step_ID);

	/**
	 * Get Project Step.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Step_ID();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_C_Project_WO_Step_ID = new ModelColumn<>(I_C_Project_WO_Step.class, "C_Project_WO_Step_ID", null);
	String COLUMNNAME_C_Project_WO_Step_ID = "C_Project_WO_Step_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_Step.class, "Created", null);
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
	 * Set Date End.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateEnd (@Nullable java.sql.Timestamp DateEnd);

	/**
	 * Get Date End.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateEnd();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_DateEnd = new ModelColumn<>(I_C_Project_WO_Step.class, "DateEnd", null);
	String COLUMNNAME_DateEnd = "DateEnd";

	/**
	 * Set Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStart (@Nullable java.sql.Timestamp DateStart);

	/**
	 * Get Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStart();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_DateStart = new ModelColumn<>(I_C_Project_WO_Step.class, "DateStart", null);
	String COLUMNNAME_DateStart = "DateStart";

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

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_Description = new ModelColumn<>(I_C_Project_WO_Step.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Project_WO_Step.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_Step.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manually locked.
	 * Start date and end date cannot be moved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManuallyLocked (boolean IsManuallyLocked);

	/**
	 * Get Manually locked.
	 * Start date and end date cannot be moved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManuallyLocked();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_IsManuallyLocked = new ModelColumn<>(I_C_Project_WO_Step.class, "IsManuallyLocked", null);
	String COLUMNNAME_IsManuallyLocked = "IsManuallyLocked";

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

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_Name = new ModelColumn<>(I_C_Project_WO_Step.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Project_WO_Step.class, "SeqNo", null);
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

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_Step.class, "Updated", null);
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
	 * Set ACTUAL facility hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWOActualFacilityHours (int WOActualFacilityHours);

	/**
	 * Get ACTUAL facility hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWOActualFacilityHours();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOActualFacilityHours = new ModelColumn<>(I_C_Project_WO_Step.class, "WOActualFacilityHours", null);
	String COLUMNNAME_WOActualFacilityHours = "WOActualFacilityHours";

	/**
	 * Set ACTUAL person hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWOActualManHours (int WOActualManHours);

	/**
	 * Get ACTUAL person hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWOActualManHours();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOActualManHours = new ModelColumn<>(I_C_Project_WO_Step.class, "WOActualManHours", null);
	String COLUMNNAME_WOActualManHours = "WOActualManHours";

	/**
	 * Set Delivery date.
	 * Delivery date to test facility
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWODeliveryDate (@Nullable java.sql.Timestamp WODeliveryDate);

	/**
	 * Get Delivery date.
	 * Delivery date to test facility
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWODeliveryDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WODeliveryDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WODeliveryDate", null);
	String COLUMNNAME_WODeliveryDate = "WODeliveryDate";

	/**
	 * Set Due Date.
	 * Due date for test step
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWODueDate (@Nullable java.sql.Timestamp WODueDate);

	/**
	 * Get Due Date.
	 * Due date for test step
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWODueDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WODueDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WODueDate", null);
	String COLUMNNAME_WODueDate = "WODueDate";

	/**
	 * Set Findings created.
	 * Date on which the report was created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOFindingsCreatedDate (@Nullable java.sql.Timestamp WOFindingsCreatedDate);

	/**
	 * Get Findings created.
	 * Date on which the report was created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOFindingsCreatedDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOFindingsCreatedDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WOFindingsCreatedDate", null);
	String COLUMNNAME_WOFindingsCreatedDate = "WOFindingsCreatedDate";

	/**
	 * Set Findings released.
	 * Date on which the report was released.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOFindingsReleasedDate (@Nullable java.sql.Timestamp WOFindingsReleasedDate);

	/**
	 * Get Findings released.
	 * Date on which the report was released.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOFindingsReleasedDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOFindingsReleasedDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WOFindingsReleasedDate", null);
	String COLUMNNAME_WOFindingsReleasedDate = "WOFindingsReleasedDate";

	/**
	 * Set Partial report date.
	 * Date of the partial report
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOPartialReportDate (@Nullable java.sql.Timestamp WOPartialReportDate);

	/**
	 * Get Partial report date.
	 * Date of the partial report
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOPartialReportDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOPartialReportDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WOPartialReportDate", null);
	String COLUMNNAME_WOPartialReportDate = "WOPartialReportDate";

	/**
	 * Set TARGET person hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWOPlannedPersonDurationHours (int WOPlannedPersonDurationHours);

	/**
	 * Get TARGET person hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWOPlannedPersonDurationHours();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOPlannedPersonDurationHours = new ModelColumn<>(I_C_Project_WO_Step.class, "WOPlannedPersonDurationHours", null);
	String COLUMNNAME_WOPlannedPersonDurationHours = "WOPlannedPersonDurationHours";

	/**
	 * Set TARGET facility hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWOPlannedResourceDurationHours (int WOPlannedResourceDurationHours);

	/**
	 * Get TARGET facility hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWOPlannedResourceDurationHours();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOPlannedResourceDurationHours = new ModelColumn<>(I_C_Project_WO_Step.class, "WOPlannedResourceDurationHours", null);
	String COLUMNNAME_WOPlannedResourceDurationHours = "WOPlannedResourceDurationHours";

	/**
	 * Set WO Step ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setWO_Step_ExternalId (@Nullable java.lang.String WO_Step_ExternalId);

	/**
	 * Get WO Step ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getWO_Step_ExternalId();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WO_Step_ExternalId = new ModelColumn<>(I_C_Project_WO_Step.class, "WO_Step_ExternalId", null);
	String COLUMNNAME_WO_Step_ExternalId = "WO_Step_ExternalId";

	/**
	 * Set State.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWOStepStatus (java.lang.String WOStepStatus);

	/**
	 * Get State.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWOStepStatus();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOStepStatus = new ModelColumn<>(I_C_Project_WO_Step.class, "WOStepStatus", null);
	String COLUMNNAME_WOStepStatus = "WOStepStatus";

	/**
	 * Set TARGET end date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOTargetEndDate (@Nullable java.sql.Timestamp WOTargetEndDate);

	/**
	 * Get TARGET end date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOTargetEndDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOTargetEndDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WOTargetEndDate", null);
	String COLUMNNAME_WOTargetEndDate = "WOTargetEndDate";

	/**
	 * Set TARGET start date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOTargetStartDate (@Nullable java.sql.Timestamp WOTargetStartDate);

	/**
	 * Get TARGET start date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOTargetStartDate();

	ModelColumn<I_C_Project_WO_Step, Object> COLUMN_WOTargetStartDate = new ModelColumn<>(I_C_Project_WO_Step.class, "WOTargetStartDate", null);
	String COLUMNNAME_WOTargetStartDate = "WOTargetStartDate";
}
