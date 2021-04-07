package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Commission_Fact
 *  @author metasfresh (generated) 
 */
public interface I_C_Commission_Fact 
{

	String Table_Name = "C_Commission_Fact";

//	/** AD_Table_ID=541407 */
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
	 * Set C_Commission_Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Fact_ID (int C_Commission_Fact_ID);

	/**
	 * Get C_Commission_Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Fact_ID();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_C_Commission_Fact_ID = new ModelColumn<>(I_C_Commission_Fact.class, "C_Commission_Fact_ID", null);
	String COLUMNNAME_C_Commission_Fact_ID = "C_Commission_Fact_ID";

	/**
	 * Set Commission share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Share_ID (int C_Commission_Share_ID);

	/**
	 * Get Commission share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Share_ID();

	de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share();

	void setC_Commission_Share(de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share);

	ModelColumn<I_C_Commission_Fact, de.metas.contracts.commission.model.I_C_Commission_Share> COLUMN_C_Commission_Share_ID = new ModelColumn<>(I_C_Commission_Fact.class, "C_Commission_Share_ID", de.metas.contracts.commission.model.I_C_Commission_Share.class);
	String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

	/**
	 * Set Commission settlement candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Commission_ID (int C_Invoice_Candidate_Commission_ID);

	/**
	 * Get Commission settlement candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Commission_ID();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_C_Invoice_Candidate_Commission_ID = new ModelColumn<>(I_C_Commission_Fact.class, "C_Invoice_Candidate_Commission_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_Commission_ID = "C_Invoice_Candidate_Commission_ID";

	/**
	 * Set State.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Fact_State (java.lang.String Commission_Fact_State);

	/**
	 * Get State.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCommission_Fact_State();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_Commission_Fact_State = new ModelColumn<>(I_C_Commission_Fact.class, "Commission_Fact_State", null);
	String COLUMNNAME_Commission_Fact_State = "Commission_Fact_State";

	/**
	 * Set Timestamp.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommissionFactTimestamp (java.lang.String CommissionFactTimestamp);

	/**
	 * Get Timestamp.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCommissionFactTimestamp();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_CommissionFactTimestamp = new ModelColumn<>(I_C_Commission_Fact.class, "CommissionFactTimestamp", null);
	String COLUMNNAME_CommissionFactTimestamp = "CommissionFactTimestamp";

	/**
	 * Set Provisionspunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommissionPoints (BigDecimal CommissionPoints);

	/**
	 * Get Provisionspunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommissionPoints();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_CommissionPoints = new ModelColumn<>(I_C_Commission_Fact.class, "CommissionPoints", null);
	String COLUMNNAME_CommissionPoints = "CommissionPoints";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_Created = new ModelColumn<>(I_C_Commission_Fact.class, "Created", null);
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

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Commission_Fact.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Commission_Fact, Object> COLUMN_Updated = new ModelColumn<>(I_C_Commission_Fact.class, "Updated", null);
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
