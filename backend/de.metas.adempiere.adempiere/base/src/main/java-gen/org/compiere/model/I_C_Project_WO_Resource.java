package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_Resource
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_Resource 
{

	String Table_Name = "C_Project_WO_Resource";

//	/** AD_Table_ID=542161 */
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
	 * Set Assign From.
	 * Assign resource from
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignDateFrom (@Nullable java.sql.Timestamp AssignDateFrom);

	/**
	 * Get Assign From.
	 * Assign resource from
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssignDateFrom();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_AssignDateFrom = new ModelColumn<>(I_C_Project_WO_Resource.class, "AssignDateFrom", null);
	String COLUMNNAME_AssignDateFrom = "AssignDateFrom";

	/**
	 * Set Assign To.
	 * Assign resource until
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignDateTo (@Nullable java.sql.Timestamp AssignDateTo);

	/**
	 * Get Assign To.
	 * Assign resource until
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssignDateTo();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_AssignDateTo = new ModelColumn<>(I_C_Project_WO_Resource.class, "AssignDateTo", null);
	String COLUMNNAME_AssignDateTo = "AssignDateTo";

	/**
	 * Set Budget Project.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBudget_Project_ID (int Budget_Project_ID);

	/**
	 * Get Budget Project.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBudget_Project_ID();

	String COLUMNNAME_Budget_Project_ID = "Budget_Project_ID";

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
	 * Set Project Resource Budget.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_Resource_Budget_ID (int C_Project_Resource_Budget_ID);

	/**
	 * Get Project Resource Budget.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_Resource_Budget_ID();

	@Nullable org.compiere.model.I_C_Project_Resource_Budget getC_Project_Resource_Budget();

	void setC_Project_Resource_Budget(@Nullable org.compiere.model.I_C_Project_Resource_Budget C_Project_Resource_Budget);

	ModelColumn<I_C_Project_WO_Resource, org.compiere.model.I_C_Project_Resource_Budget> COLUMN_C_Project_Resource_Budget_ID = new ModelColumn<>(I_C_Project_WO_Resource.class, "C_Project_Resource_Budget_ID", org.compiere.model.I_C_Project_Resource_Budget.class);
	String COLUMNNAME_C_Project_Resource_Budget_ID = "C_Project_Resource_Budget_ID";

	/**
	 * Set Project Resource.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Resource_ID (int C_Project_WO_Resource_ID);

	/**
	 * Get Project Resource.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Resource_ID();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_C_Project_WO_Resource_ID = new ModelColumn<>(I_C_Project_WO_Resource.class, "C_Project_WO_Resource_ID", null);
	String COLUMNNAME_C_Project_WO_Resource_ID = "C_Project_WO_Resource_ID";

	/**
	 * Set Project Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Step_ID (int C_Project_WO_Step_ID);

	/**
	 * Get Project Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Step_ID();

	org.compiere.model.I_C_Project_WO_Step getC_Project_WO_Step();

	void setC_Project_WO_Step(org.compiere.model.I_C_Project_WO_Step C_Project_WO_Step);

	ModelColumn<I_C_Project_WO_Resource, org.compiere.model.I_C_Project_WO_Step> COLUMN_C_Project_WO_Step_ID = new ModelColumn<>(I_C_Project_WO_Resource.class, "C_Project_WO_Step_ID", org.compiere.model.I_C_Project_WO_Step.class);
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

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_Resource.class, "Created", null);
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

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_Description = new ModelColumn<>(I_C_Project_WO_Resource.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDuration (BigDecimal Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDuration();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_Duration = new ModelColumn<>(I_C_Project_WO_Resource.class, "Duration", null);
	String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDurationUnit (java.lang.String DurationUnit);

	/**
	 * Get Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDurationUnit();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_DurationUnit = new ModelColumn<>(I_C_Project_WO_Resource.class, "DurationUnit", null);
	String COLUMNNAME_DurationUnit = "DurationUnit";

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

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Project_WO_Resource.class, "ExternalId", null);
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

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_Resource.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set All day.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllDay (boolean IsAllDay);

	/**
	 * Get All day.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllDay();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_IsAllDay = new ModelColumn<>(I_C_Project_WO_Resource.class, "IsAllDay", null);
	String COLUMNNAME_IsAllDay = "IsAllDay";

	/**
	 * Set Resolved hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setResolvedHours (int ResolvedHours);

	/**
	 * Get Resolved hours.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getResolvedHours();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_ResolvedHours = new ModelColumn<>(I_C_Project_WO_Resource.class, "ResolvedHours", null);
	String COLUMNNAME_ResolvedHours = "ResolvedHours";

	/**
	 * Set Resource Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setResourceType (java.lang.String ResourceType);

	/**
	 * Get Resource Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getResourceType();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_ResourceType = new ModelColumn<>(I_C_Project_WO_Resource.class, "ResourceType", null);
	String COLUMNNAME_ResourceType = "ResourceType";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_Resource.class, "Updated", null);
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
	 * Set TARGET person hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setWOPlannedPersonDurationHours (@Nullable BigDecimal WOPlannedPersonDurationHours);

	/**
	 * Get TARGET person hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getWOPlannedPersonDurationHours();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_WOPlannedPersonDurationHours = new ModelColumn<>(I_C_Project_WO_Resource.class, "WOPlannedPersonDurationHours", null);
	String COLUMNNAME_WOPlannedPersonDurationHours = "WOPlannedPersonDurationHours";

	/**
	 * Set TARGET facility hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setWOPlannedResourceDurationHours (@Nullable BigDecimal WOPlannedResourceDurationHours);

	/**
	 * Get TARGET facility hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getWOPlannedResourceDurationHours();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_WOPlannedResourceDurationHours = new ModelColumn<>(I_C_Project_WO_Resource.class, "WOPlannedResourceDurationHours", null);
	String COLUMNNAME_WOPlannedResourceDurationHours = "WOPlannedResourceDurationHours";

	/**
	 * Set Test facility group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOTestFacilityGroupName (@Nullable java.lang.String WOTestFacilityGroupName);

	/**
	 * Get Test facility group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOTestFacilityGroupName();

	ModelColumn<I_C_Project_WO_Resource, Object> COLUMN_WOTestFacilityGroupName = new ModelColumn<>(I_C_Project_WO_Resource.class, "WOTestFacilityGroupName", null);
	String COLUMNNAME_WOTestFacilityGroupName = "WOTestFacilityGroupName";
}
