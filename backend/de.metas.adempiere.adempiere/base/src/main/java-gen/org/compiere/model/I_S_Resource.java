package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for S_Resource
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_S_Resource
{

	String Table_Name = "S_Resource";

//	/** AD_Table_ID=487 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";


	/**
	 * Set Capacity Per Production Cycle.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCapacityPerProductionCycle (@Nullable BigDecimal CapacityPerProductionCycle);

	/**
	 * Get Capacity Per Production Cycle.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCapacityPerProductionCycle();

	ModelColumn<I_S_Resource, Object> COLUMN_CapacityPerProductionCycle = new ModelColumn<>(I_S_Resource.class, "CapacityPerProductionCycle", null);
	String COLUMNNAME_CapacityPerProductionCycle = "CapacityPerProductionCycle";

	/**
	 * Set Unit of measurement.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCapacityPerProductionCycle_UOM_ID (int CapacityPerProductionCycle_UOM_ID);

	/**
	 * Get Unit of measurement.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCapacityPerProductionCycle_UOM_ID();

	String COLUMNNAME_CapacityPerProductionCycle_UOM_ID = "CapacityPerProductionCycle_UOM_ID";

	/**
	 * Set Chargeable Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeableQty (@Nullable BigDecimal ChargeableQty);

	/**
	 * Get Chargeable Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeableQty();

	ModelColumn<I_S_Resource, Object> COLUMN_ChargeableQty = new ModelColumn<>(I_S_Resource.class, "ChargeableQty", null);
	String COLUMNNAME_ChargeableQty = "ChargeableQty";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_Resource, Object> COLUMN_Created = new ModelColumn<>(I_S_Resource.class, "Created", null);
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
	 * Set Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	@Nullable org.compiere.model.I_C_Workplace getC_Workplace();

	void setC_Workplace(@Nullable org.compiere.model.I_C_Workplace C_Workplace);

	ModelColumn<I_S_Resource, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_S_Resource.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

	/**
	 * Set Daily Capacity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDailyCapacity (@Nullable BigDecimal DailyCapacity);

	/**
	 * Get Daily Capacity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDailyCapacity();

	ModelColumn<I_S_Resource, Object> COLUMN_DailyCapacity = new ModelColumn<>(I_S_Resource.class, "DailyCapacity", null);
	String COLUMNNAME_DailyCapacity = "DailyCapacity";

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

	ModelColumn<I_S_Resource, Object> COLUMN_Description = new ModelColumn<>(I_S_Resource.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_S_Resource, Object> COLUMN_InternalName = new ModelColumn<>(I_S_Resource.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_S_Resource, Object> COLUMN_IsActive = new ModelColumn<>(I_S_Resource.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Available.
	 * Resource is available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAvailable (boolean IsAvailable);

	/**
	 * Get Available.
	 * Resource is available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAvailable();

	ModelColumn<I_S_Resource, Object> COLUMN_IsAvailable = new ModelColumn<>(I_S_Resource.class, "IsAvailable", null);
	String COLUMNNAME_IsAvailable = "IsAvailable";

	/**
	 * Set Manufacturing Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsManufacturingResource (boolean IsManufacturingResource);

	/**
	 * Get Manufacturing Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isManufacturingResource();

	ModelColumn<I_S_Resource, Object> COLUMN_IsManufacturingResource = new ModelColumn<>(I_S_Resource.class, "IsManufacturingResource", null);
	String COLUMNNAME_IsManufacturingResource = "IsManufacturingResource";

	/**
	 * Set Manufacturing Resource Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturingResourceType (@Nullable java.lang.String ManufacturingResourceType);

	/**
	 * Get Manufacturing Resource Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManufacturingResourceType();

	ModelColumn<I_S_Resource, Object> COLUMN_ManufacturingResourceType = new ModelColumn<>(I_S_Resource.class, "ManufacturingResourceType", null);
	String COLUMNNAME_ManufacturingResourceType = "ManufacturingResourceType";

	/**
	 * Set Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMRP_Exclude (@Nullable java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMRP_Exclude();

	ModelColumn<I_S_Resource, Object> COLUMN_MRP_Exclude = new ModelColumn<>(I_S_Resource.class, "MRP_Exclude", null);
	String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

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

	ModelColumn<I_S_Resource, Object> COLUMN_Name = new ModelColumn<>(I_S_Resource.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set % Utilization.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercentUtilization (BigDecimal PercentUtilization);

	/**
	 * Get % Utilization.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentUtilization();

	ModelColumn<I_S_Resource, Object> COLUMN_PercentUtilization = new ModelColumn<>(I_S_Resource.class, "PercentUtilization", null);
	String COLUMNNAME_PercentUtilization = "PercentUtilization";

	/**
	 * Set Planning Horizon.
	 * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlanningHorizon (int PlanningHorizon);

	/**
	 * Get Planning Horizon.
	 * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPlanningHorizon();

	ModelColumn<I_S_Resource, Object> COLUMN_PlanningHorizon = new ModelColumn<>(I_S_Resource.class, "PlanningHorizon", null);
	String COLUMNNAME_PlanningHorizon = "PlanningHorizon";

	/**
	 * Set Queuing Time.
	 * Queue time is the time a job waits at a work center before begin handled.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQueuingTime (@Nullable BigDecimal QueuingTime);

	/**
	 * Get Queuing Time.
	 * Queue time is the time a job waits at a work center before begin handled.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQueuingTime();

	ModelColumn<I_S_Resource, Object> COLUMN_QueuingTime = new ModelColumn<>(I_S_Resource.class, "QueuingTime", null);
	String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Test facility group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_HumanResourceTestGroup_ID (int S_HumanResourceTestGroup_ID);

	/**
	 * Get Test facility group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_HumanResourceTestGroup_ID();

	@Nullable org.compiere.model.I_S_HumanResourceTestGroup getS_HumanResourceTestGroup();

	void setS_HumanResourceTestGroup(@Nullable org.compiere.model.I_S_HumanResourceTestGroup S_HumanResourceTestGroup);

	ModelColumn<I_S_Resource, org.compiere.model.I_S_HumanResourceTestGroup> COLUMN_S_HumanResourceTestGroup_ID = new ModelColumn<>(I_S_Resource.class, "S_HumanResourceTestGroup_ID", org.compiere.model.I_S_HumanResourceTestGroup.class);
	String COLUMNNAME_S_HumanResourceTestGroup_ID = "S_HumanResourceTestGroup_ID";

	/**
	 * Set Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_Group_ID (int S_Resource_Group_ID);

	/**
	 * Get Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_Group_ID();

	String COLUMNNAME_S_Resource_Group_ID = "S_Resource_Group_ID";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	ModelColumn<I_S_Resource, Object> COLUMN_S_Resource_ID = new ModelColumn<>(I_S_Resource.class, "S_Resource_ID", null);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Resource Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_ResourceType_ID (int S_ResourceType_ID);

	/**
	 * Get Resource Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_ResourceType_ID();

	String COLUMNNAME_S_ResourceType_ID = "S_ResourceType_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_Resource, Object> COLUMN_Updated = new ModelColumn<>(I_S_Resource.class, "Updated", null);
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

	ModelColumn<I_S_Resource, Object> COLUMN_Value = new ModelColumn<>(I_S_Resource.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWaitingTime (@Nullable BigDecimal WaitingTime);

	/**
	 * Get Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWaitingTime();

	ModelColumn<I_S_Resource, Object> COLUMN_WaitingTime = new ModelColumn<>(I_S_Resource.class, "WaitingTime", null);
	String COLUMNNAME_WaitingTime = "WaitingTime";
}
