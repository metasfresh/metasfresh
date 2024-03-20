package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Doc_Approval_Strategy_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_Approval_Strategy_Line 
{

	String Table_Name = "C_Doc_Approval_Strategy_Line";

//	/** AD_Table_ID=542381 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Approval Strategy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Approval_Strategy_ID (int C_Doc_Approval_Strategy_ID);

	/**
	 * Get Document Approval Strategy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Approval_Strategy_ID();

	org.compiere.model.I_C_Doc_Approval_Strategy getC_Doc_Approval_Strategy();

	void setC_Doc_Approval_Strategy(org.compiere.model.I_C_Doc_Approval_Strategy C_Doc_Approval_Strategy);

	ModelColumn<I_C_Doc_Approval_Strategy_Line, org.compiere.model.I_C_Doc_Approval_Strategy> COLUMN_C_Doc_Approval_Strategy_ID = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "C_Doc_Approval_Strategy_ID", org.compiere.model.I_C_Doc_Approval_Strategy.class);
	String COLUMNNAME_C_Doc_Approval_Strategy_ID = "C_Doc_Approval_Strategy_ID";

	/**
	 * Set Document Approval Strategy Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Approval_Strategy_Line_ID (int C_Doc_Approval_Strategy_Line_ID);

	/**
	 * Get Document Approval Strategy Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Approval_Strategy_Line_ID();

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_C_Doc_Approval_Strategy_Line_ID = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "C_Doc_Approval_Strategy_Line_ID", null);
	String COLUMNNAME_C_Doc_Approval_Strategy_Line_ID = "C_Doc_Approval_Strategy_Line_ID";

	/**
	 * Set Position.
	 * Job Position
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Job Position
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Job_ID();

	@Nullable org.compiere.model.I_C_Job getC_Job();

	void setC_Job(@Nullable org.compiere.model.I_C_Job C_Job);

	ModelColumn<I_C_Doc_Approval_Strategy_Line, org.compiere.model.I_C_Job> COLUMN_C_Job_ID = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "C_Job_ID", org.compiere.model.I_C_Job.class);
	String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "Created", null);
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

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is Project Manager Set.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsProjectManagerSet (@Nullable java.lang.String IsProjectManagerSet);

	/**
	 * Get Is Project Manager Set.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsProjectManagerSet();

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_IsProjectManagerSet = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "IsProjectManagerSet", null);
	String COLUMNNAME_IsProjectManagerSet = "IsProjectManagerSet";

	/**
	 * Set Minimum Amt.
	 * Minimum Amount in Document Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMinimumAmt (@Nullable BigDecimal MinimumAmt);

	/**
	 * Get Minimum Amt.
	 * Minimum Amount in Document Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMinimumAmt();

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_MinimumAmt = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "MinimumAmt", null);
	String COLUMNNAME_MinimumAmt = "MinimumAmt";

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

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Check Supervisor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupervisorCheckStrategy (@Nullable java.lang.String SupervisorCheckStrategy);

	/**
	 * Get Check Supervisor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupervisorCheckStrategy();

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_SupervisorCheckStrategy = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "SupervisorCheckStrategy", null);
	String COLUMNNAME_SupervisorCheckStrategy = "SupervisorCheckStrategy";

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

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_Type = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "Type", null);
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

	ModelColumn<I_C_Doc_Approval_Strategy_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_Approval_Strategy_Line.class, "Updated", null);
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
