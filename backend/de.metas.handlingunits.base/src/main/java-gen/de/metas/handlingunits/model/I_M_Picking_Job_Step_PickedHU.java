package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Picking_Job_Step_PickedHU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Step_PickedHU 
{

	String Table_Name = "M_Picking_Job_Step_PickedHU";

//	/** AD_Table_ID=541936 */
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Picking_Job_Step_PickedHU, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "Created", null);
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

	ModelColumn<I_M_Picking_Job_Step_PickedHU, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_ID();

	de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate();

	void setM_Picking_Candidate(de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate);

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_Picking_Candidate> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "M_Picking_Candidate_ID", de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

	/**
	 * Set Picking Job HU Alternative.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_HUAlternative_ID (int M_Picking_Job_HUAlternative_ID);

	/**
	 * Get Picking Job HU Alternative.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_HUAlternative_ID();

	@Nullable de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative getM_Picking_Job_HUAlternative();

	void setM_Picking_Job_HUAlternative(@Nullable de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative M_Picking_Job_HUAlternative);

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative> COLUMN_M_Picking_Job_HUAlternative_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "M_Picking_Job_HUAlternative_ID", de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative.class);
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

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_Picking_Job> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "M_Picking_Job_ID", de.metas.handlingunits.model.I_M_Picking_Job.class);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

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

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_Picking_Job_Step> COLUMN_M_Picking_Job_Step_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "M_Picking_Job_Step_ID", de.metas.handlingunits.model.I_M_Picking_Job_Step.class);
	String COLUMNNAME_M_Picking_Job_Step_ID = "M_Picking_Job_Step_ID";

	/**
	 * Set Picking Step Picked HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Step_PickedHU_ID (int M_Picking_Job_Step_PickedHU_ID);

	/**
	 * Get Picking Step Picked HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Step_PickedHU_ID();

	ModelColumn<I_M_Picking_Job_Step_PickedHU, Object> COLUMN_M_Picking_Job_Step_PickedHU_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "M_Picking_Job_Step_PickedHU_ID", null);
	String COLUMNNAME_M_Picking_Job_Step_PickedHU_ID = "M_Picking_Job_Step_PickedHU_ID";

	/**
	 * Set Picked HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPicked_HU_ID (int Picked_HU_ID);

	/**
	 * Get Picked HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPicked_HU_ID();

	de.metas.handlingunits.model.I_M_HU getPicked_HU();

	void setPicked_HU(de.metas.handlingunits.model.I_M_HU Picked_HU);

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_HU> COLUMN_Picked_HU_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "Picked_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_Picked_HU_ID = "Picked_HU_ID";

	/**
	 * Set Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_HU_ID (int PickFrom_HU_ID);

	/**
	 * Get Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_HU_ID();

	de.metas.handlingunits.model.I_M_HU getPickFrom_HU();

	void setPickFrom_HU(de.metas.handlingunits.model.I_M_HU PickFrom_HU);

	ModelColumn<I_M_Picking_Job_Step_PickedHU, de.metas.handlingunits.model.I_M_HU> COLUMN_PickFrom_HU_ID = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "PickFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_PickFrom_HU_ID = "PickFrom_HU_ID";

	/**
	 * Set Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyPicked (BigDecimal QtyPicked);

	/**
	 * Get Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPicked();

	ModelColumn<I_M_Picking_Job_Step_PickedHU, Object> COLUMN_QtyPicked = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "QtyPicked", null);
	String COLUMNNAME_QtyPicked = "QtyPicked";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Step_PickedHU, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Step_PickedHU.class, "Updated", null);
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
