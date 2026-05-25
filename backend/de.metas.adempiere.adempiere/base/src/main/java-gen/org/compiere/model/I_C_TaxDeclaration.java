package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_TaxDeclaration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_TaxDeclaration 
{

	String Table_Name = "C_TaxDeclaration";

//	/** AD_Table_ID=818 */
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
	 * Set null.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get null.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_C_TaxDeclaration, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_TaxDeclaration.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Period.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	org.compiere.model.I_C_Period getC_Period();

	void setC_Period(org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_C_TaxDeclaration, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_C_TaxDeclaration.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_Created = new ModelColumn<>(I_C_TaxDeclaration.class, "Created", null);
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
	 * Set Tax Declaration.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID);

	/**
	 * Get Tax Declaration.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxDeclaration_ID();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_C_TaxDeclaration_ID = new ModelColumn<>(I_C_TaxDeclaration.class, "C_TaxDeclaration_ID", null);
	String COLUMNNAME_C_TaxDeclaration_ID = "C_TaxDeclaration_ID";

	/**
	 * Set Original Tax Declaration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxDeclaration_Original_ID (int C_TaxDeclaration_Original_ID);

	/**
	 * Get Original Tax Declaration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxDeclaration_Original_ID();

	org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration_Original();

	void setC_TaxDeclaration_Original(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration_Original);

	ModelColumn<I_C_TaxDeclaration, org.compiere.model.I_C_TaxDeclaration> COLUMN_C_TaxDeclaration_Original_ID = new ModelColumn<>(I_C_TaxDeclaration.class, "C_TaxDeclaration_Original_ID", org.compiere.model.I_C_TaxDeclaration.class);
	String COLUMNNAME_C_TaxDeclaration_Original_ID = "C_TaxDeclaration_Original_ID";

	/**
	 * Set Accounting Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_TaxDeclaration.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

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

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_Description = new ModelColumn<>(I_C_TaxDeclaration.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Correction Needed Reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCorrectionNeededReason (@Nullable java.lang.String CorrectionNeededReason);

	/**
	 * Get Correction Needed Reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCorrectionNeededReason();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_CorrectionNeededReason = new ModelColumn<>(I_C_TaxDeclaration.class, "CorrectionNeededReason", null);
	String COLUMNNAME_CorrectionNeededReason = "CorrectionNeededReason";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_DocAction = new ModelColumn<>(I_C_TaxDeclaration.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_TaxDeclaration.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_TaxDeclaration.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

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

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_IsActive = new ModelColumn<>(I_C_TaxDeclaration.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCorrection (boolean IsCorrection);

	/**
	 * Get Is Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIsCorrection();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_IsCorrection = new ModelColumn<>(I_C_TaxDeclaration.class, "IsCorrection", null);
	String COLUMNNAME_IsCorrection = "IsCorrection";

	/**
	 * Set Is Correction Needed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCorrectionNeeded (boolean IsCorrectionNeeded);

	/**
	 * Get Is Correction Needed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIsCorrectionNeeded();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_IsCorrectionNeeded = new ModelColumn<>(I_C_TaxDeclaration.class, "IsCorrectionNeeded", null);
	String COLUMNNAME_IsCorrectionNeeded = "IsCorrectionNeeded";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_Processed = new ModelColumn<>(I_C_TaxDeclaration.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_Processing = new ModelColumn<>(I_C_TaxDeclaration.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_TaxDeclaration, Object> COLUMN_Updated = new ModelColumn<>(I_C_TaxDeclaration.class, "Updated", null);
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
