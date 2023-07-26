package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_OrderLine_HU_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_OrderLine_HU_Candidate 
{

	String Table_Name = "DD_OrderLine_HU_Candidate";

//	/** AD_Table_ID=540686 */
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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "Created", null);
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
	 * Set Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_HU_Candidate_ID (int DD_OrderLine_HU_Candidate_ID);

	/**
	 * Get Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_HU_Candidate_ID();

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_DD_OrderLine_HU_Candidate_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_HU_Candidate_ID", null);
	String COLUMNNAME_DD_OrderLine_HU_Candidate_ID = "DD_OrderLine_HU_Candidate_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "Updated", null);
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
