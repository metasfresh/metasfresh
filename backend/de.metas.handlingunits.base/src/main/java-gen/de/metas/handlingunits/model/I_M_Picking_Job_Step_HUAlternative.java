package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_Step_HUAlternative
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Step_HUAlternative 
{

	String Table_Name = "M_Picking_Job_Step_HUAlternative";

//	/** AD_Table_ID=541927 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "Created", null);
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

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Picking Job HU Alternative.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_HUAlternative_ID (int M_Picking_Job_HUAlternative_ID);

	/**
	 * Get Picking Job HU Alternative.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_HUAlternative_ID();

	de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative getM_Picking_Job_HUAlternative();

	void setM_Picking_Job_HUAlternative(de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative M_Picking_Job_HUAlternative);

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative> COLUMN_M_Picking_Job_HUAlternative_ID = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "M_Picking_Job_HUAlternative_ID", de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative.class);
	String COLUMNNAME_M_Picking_Job_HUAlternative_ID = "M_Picking_Job_HUAlternative_ID";

	/**
	 * Set Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_ID (int M_Picking_Job_ID);

	/**
	 * Get Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_ID();

	de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job();

	void setM_Picking_Job(de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job);

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, de.metas.handlingunits.model.I_M_Picking_Job> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "M_Picking_Job_ID", de.metas.handlingunits.model.I_M_Picking_Job.class);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

	/**
	 * Set Picking Job Step Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Step_HUAlternative_ID (int M_Picking_Job_Step_HUAlternative_ID);

	/**
	 * Get Picking Job Step Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Step_HUAlternative_ID();

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, Object> COLUMN_M_Picking_Job_Step_HUAlternative_ID = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "M_Picking_Job_Step_HUAlternative_ID", null);
	String COLUMNNAME_M_Picking_Job_Step_HUAlternative_ID = "M_Picking_Job_Step_HUAlternative_ID";

	/**
	 * Set Picking Job Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Step_ID (int M_Picking_Job_Step_ID);

	/**
	 * Get Picking Job Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Step_ID();

	de.metas.handlingunits.model.I_M_Picking_Job_Step getM_Picking_Job_Step();

	void setM_Picking_Job_Step(de.metas.handlingunits.model.I_M_Picking_Job_Step M_Picking_Job_Step);

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, de.metas.handlingunits.model.I_M_Picking_Job_Step> COLUMN_M_Picking_Job_Step_ID = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "M_Picking_Job_Step_ID", de.metas.handlingunits.model.I_M_Picking_Job_Step.class);
	String COLUMNNAME_M_Picking_Job_Step_ID = "M_Picking_Job_Step_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Step_HUAlternative, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Step_HUAlternative.class, "Updated", null);
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
