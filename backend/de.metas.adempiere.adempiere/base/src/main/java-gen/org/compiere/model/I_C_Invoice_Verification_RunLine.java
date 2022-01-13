package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Invoice_Verification_RunLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Verification_RunLine 
{

	String Table_Name = "C_Invoice_Verification_RunLine";

//	/** AD_Table_ID=541665 */
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
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_Invoice_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	@Deprecated
	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_Invoice_Verification_RunLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_InvoiceLine_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	@Deprecated
	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_Invoice_Verification_RunLine, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_InvoiceLine_Tax_ID (int C_InvoiceLine_Tax_ID);

	/**
	 * Get Tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_InvoiceLine_Tax_ID();

	String COLUMNNAME_C_InvoiceLine_Tax_ID = "C_InvoiceLine_Tax_ID";

	/**
	 * Set Invoice Verification Run.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_Run_ID (int C_Invoice_Verification_Run_ID);

	/**
	 * Get Invoice Verification Run.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_Run_ID();

	org.compiere.model.I_C_Invoice_Verification_Run getC_Invoice_Verification_Run();

	void setC_Invoice_Verification_Run(org.compiere.model.I_C_Invoice_Verification_Run C_Invoice_Verification_Run);

	ModelColumn<I_C_Invoice_Verification_RunLine, org.compiere.model.I_C_Invoice_Verification_Run> COLUMN_C_Invoice_Verification_Run_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_Invoice_Verification_Run_ID", org.compiere.model.I_C_Invoice_Verification_Run.class);
	String COLUMNNAME_C_Invoice_Verification_Run_ID = "C_Invoice_Verification_Run_ID";

	/**
	 * Set Invoice Verification RunLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_RunLine_ID (int C_Invoice_Verification_RunLine_ID);

	/**
	 * Get Invoice Verification RunLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_RunLine_ID();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_C_Invoice_Verification_RunLine_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_Invoice_Verification_RunLine_ID", null);
	String COLUMNNAME_C_Invoice_Verification_RunLine_ID = "C_Invoice_Verification_RunLine_ID";

	/**
	 * Set Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_Set_ID (int C_Invoice_Verification_Set_ID);

	/**
	 * Get Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_Set_ID();

	@Nullable org.compiere.model.I_C_Invoice_Verification_Set getC_Invoice_Verification_Set();

	void setC_Invoice_Verification_Set(@Nullable org.compiere.model.I_C_Invoice_Verification_Set C_Invoice_Verification_Set);

	ModelColumn<I_C_Invoice_Verification_RunLine, org.compiere.model.I_C_Invoice_Verification_Set> COLUMN_C_Invoice_Verification_Set_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_Invoice_Verification_Set_ID", org.compiere.model.I_C_Invoice_Verification_Set.class);
	String COLUMNNAME_C_Invoice_Verification_Set_ID = "C_Invoice_Verification_Set_ID";

	/**
	 * Set Invoice Verification Element.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_SetLine_ID (int C_Invoice_Verification_SetLine_ID);

	/**
	 * Get Invoice Verification Element.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_SetLine_ID();

	@Nullable org.compiere.model.I_C_Invoice_Verification_SetLine getC_Invoice_Verification_SetLine();

	void setC_Invoice_Verification_SetLine(@Nullable org.compiere.model.I_C_Invoice_Verification_SetLine C_Invoice_Verification_SetLine);

	ModelColumn<I_C_Invoice_Verification_RunLine, org.compiere.model.I_C_Invoice_Verification_SetLine> COLUMN_C_Invoice_Verification_SetLine_ID = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "C_Invoice_Verification_SetLine_ID", org.compiere.model.I_C_Invoice_Verification_SetLine.class);
	String COLUMNNAME_C_Invoice_Verification_SetLine_ID = "C_Invoice_Verification_SetLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "Created", null);
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

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Text snippet OK.
	 * Indicates if the text snippet of the tax assigned during the run is the rate same that the invoice line's tax has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxBoilerPlateMatch (boolean IsTaxBoilerPlateMatch);

	/**
	 * Get Text snippet OK.
	 * Indicates if the text snippet of the tax assigned during the run is the rate same that the invoice line's tax has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxBoilerPlateMatch();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_IsTaxBoilerPlateMatch = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "IsTaxBoilerPlateMatch", null);
	String COLUMNNAME_IsTaxBoilerPlateMatch = "IsTaxBoilerPlateMatch";

	/**
	 * Set Tax OK.
	 * Indicates if the tax assigned during the run is the same that the invoice line has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIdMatch (boolean IsTaxIdMatch);

	/**
	 * Get Tax OK.
	 * Indicates if the tax assigned during the run is the same that the invoice line has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIdMatch();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_IsTaxIdMatch = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "IsTaxIdMatch", null);
	String COLUMNNAME_IsTaxIdMatch = "IsTaxIdMatch";

	/**
	 * Set Tax rate OK.
	 * Indicates if the rate of the tax assigned during the run is the rate same that the invoice line's tax has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxRateMatch (boolean IsTaxRateMatch);

	/**
	 * Get Tax rate OK.
	 * Indicates if the rate of the tax assigned during the run is the rate same that the invoice line's tax has.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxRateMatch();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_IsTaxRateMatch = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "IsTaxRateMatch", null);
	String COLUMNNAME_IsTaxRateMatch = "IsTaxRateMatch";

	/**
	 * Set Assigned tax.
	 * Tax that was assigned to the invoice line during the verifiycation run.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRun_Tax_ID (int Run_Tax_ID);

	/**
	 * Get Assigned tax.
	 * Tax that was assigned to the invoice line during the verifiycation run.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRun_Tax_ID();

	String COLUMNNAME_Run_Tax_ID = "Run_Tax_ID";

	/**
	 * Set Tax log.
	 * Log-Messages written by metasfresh during the assignment of the tax.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRun_Tax_Lookup_Log (@Nullable java.lang.String Run_Tax_Lookup_Log);

	/**
	 * Get Tax log.
	 * Log-Messages written by metasfresh during the assignment of the tax.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRun_Tax_Lookup_Log();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_Run_Tax_Lookup_Log = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "Run_Tax_Lookup_Log", null);
	String COLUMNNAME_Run_Tax_Lookup_Log = "Run_Tax_Lookup_Log";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Verification_RunLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Verification_RunLine.class, "Updated", null);
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
