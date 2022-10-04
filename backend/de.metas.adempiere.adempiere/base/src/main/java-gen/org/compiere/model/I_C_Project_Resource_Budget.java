package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Project_Resource_Budget
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Resource_Budget 
{

	String Table_Name = "C_Project_Resource_Budget";

//	/** AD_Table_ID=542157 */
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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Resource_Budget_ID (int C_Project_Resource_Budget_ID);

	/**
	 * Get Project Resource Budget.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Resource_Budget_ID();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_C_Project_Resource_Budget_ID = new ModelColumn<>(I_C_Project_Resource_Budget.class, "C_Project_Resource_Budget_ID", null);
	String COLUMNNAME_C_Project_Resource_Budget_ID = "C_Project_Resource_Budget_ID";

	/**
	 * Set UOM for Time.
	 * Standard Unit of Measure for Time
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Time_ID (int C_UOM_Time_ID);

	/**
	 * Get UOM for Time.
	 * Standard Unit of Measure for Time
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Time_ID();

	String COLUMNNAME_C_UOM_Time_ID = "C_UOM_Time_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Resource_Budget.class, "Created", null);
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
	 * Set Finish Plan.
	 * Planned Finish Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateFinishPlan (java.sql.Timestamp DateFinishPlan);

	/**
	 * Get Finish Plan.
	 * Planned Finish Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateFinishPlan();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_DateFinishPlan = new ModelColumn<>(I_C_Project_Resource_Budget.class, "DateFinishPlan", null);
	String COLUMNNAME_DateFinishPlan = "DateFinishPlan";

	/**
	 * Set Start Plan.
	 * Planned Start Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateStartPlan (java.sql.Timestamp DateStartPlan);

	/**
	 * Get Start Plan.
	 * Planned Start Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateStartPlan();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_DateStartPlan = new ModelColumn<>(I_C_Project_Resource_Budget.class, "DateStartPlan", null);
	String COLUMNNAME_DateStartPlan = "DateStartPlan";

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

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_Description = new ModelColumn<>(I_C_Project_Resource_Budget.class, "Description", null);
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

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Project_Resource_Budget.class, "ExternalId", null);
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

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Resource_Budget.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Planned Amount.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedAmt (BigDecimal PlannedAmt);

	/**
	 * Get Planned Amount.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedAmt();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_Project_Resource_Budget.class, "PlannedAmt", null);
	String COLUMNNAME_PlannedAmt = "PlannedAmt";

	/**
	 * Set Planned Duration.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedDuration (BigDecimal PlannedDuration);

	/**
	 * Get Planned Duration.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedDuration();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_PlannedDuration = new ModelColumn<>(I_C_Project_Resource_Budget.class, "PlannedDuration", null);
	String COLUMNNAME_PlannedDuration = "PlannedDuration";

	/**
	 * Set Price / Time UOM.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPricePerTimeUOM (BigDecimal PricePerTimeUOM);

	/**
	 * Get Price / Time UOM.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPricePerTimeUOM();

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_PricePerTimeUOM = new ModelColumn<>(I_C_Project_Resource_Budget.class, "PricePerTimeUOM", null);
	String COLUMNNAME_PricePerTimeUOM = "PricePerTimeUOM";

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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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

	ModelColumn<I_C_Project_Resource_Budget, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Resource_Budget.class, "Updated", null);
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
