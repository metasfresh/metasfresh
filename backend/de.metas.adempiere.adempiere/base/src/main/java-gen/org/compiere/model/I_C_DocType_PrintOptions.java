package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_DocType_PrintOptions
 *  @author metasfresh (generated) 
 */
public interface I_C_DocType_PrintOptions 
{

	String Table_Name = "C_DocType_PrintOptions";

//	/** AD_Table_ID=541551 */
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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Document Type Printing Options.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_PrintOptions_ID (int C_DocType_PrintOptions_ID);

	/**
	 * Get Document Type Printing Options.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_PrintOptions_ID();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_C_DocType_PrintOptions_ID = new ModelColumn<>(I_C_DocType_PrintOptions.class, "C_DocType_PrintOptions_ID", null);
	String COLUMNNAME_C_DocType_PrintOptions_ID = "C_DocType_PrintOptions_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_Created = new ModelColumn<>(I_C_DocType_PrintOptions.class, "Created", null);
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

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_Description = new ModelColumn<>(I_C_DocType_PrintOptions.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Flavor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentFlavor (@Nullable java.lang.String DocumentFlavor);

	/**
	 * Get Flavor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentFlavor();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_DocumentFlavor = new ModelColumn<>(I_C_DocType_PrintOptions.class, "DocumentFlavor", null);
	String COLUMNNAME_DocumentFlavor = "DocumentFlavor";

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

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_IsActive = new ModelColumn<>(I_C_DocType_PrintOptions.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Print Logo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPRINTER_OPTS_IsPrintLogo (boolean PRINTER_OPTS_IsPrintLogo);

	/**
	 * Get Print Logo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPRINTER_OPTS_IsPrintLogo();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_PRINTER_OPTS_IsPrintLogo = new ModelColumn<>(I_C_DocType_PrintOptions.class, "PRINTER_OPTS_IsPrintLogo", null);
	String COLUMNNAME_PRINTER_OPTS_IsPrintLogo = "PRINTER_OPTS_IsPrintLogo";

	/**
	 * Set Print Totals.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPRINTER_OPTS_IsPrintTotals (boolean PRINTER_OPTS_IsPrintTotals);

	/**
	 * Get Print Totals.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPRINTER_OPTS_IsPrintTotals();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_PRINTER_OPTS_IsPrintTotals = new ModelColumn<>(I_C_DocType_PrintOptions.class, "PRINTER_OPTS_IsPrintTotals", null);
	String COLUMNNAME_PRINTER_OPTS_IsPrintTotals = "PRINTER_OPTS_IsPrintTotals";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_DocType_PrintOptions, Object> COLUMN_Updated = new ModelColumn<>(I_C_DocType_PrintOptions.class, "Updated", null);
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
