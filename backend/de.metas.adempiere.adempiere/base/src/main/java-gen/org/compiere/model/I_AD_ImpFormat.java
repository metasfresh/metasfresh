package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_ImpFormat
 *  @author metasfresh (generated) 
 */
public interface I_AD_ImpFormat 
{

	String Table_Name = "AD_ImpFormat";

//	/** AD_Table_ID=381 */
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
	 * Set Import Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_ImpFormat_ID (int AD_ImpFormat_ID);

	/**
	 * Get Import Format.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_ImpFormat_ID();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_AD_ImpFormat_ID = new ModelColumn<>(I_AD_ImpFormat.class, "AD_ImpFormat_ID", null);
	String COLUMNNAME_AD_ImpFormat_ID = "AD_ImpFormat_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_Created = new ModelColumn<>(I_AD_ImpFormat.class, "Created", null);
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

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_Description = new ModelColumn<>(I_AD_ImpFormat.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set File Charset.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFileCharset (java.lang.String FileCharset);

	/**
	 * Get File Charset.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFileCharset();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_FileCharset = new ModelColumn<>(I_AD_ImpFormat.class, "FileCharset", null);
	String COLUMNNAME_FileCharset = "FileCharset";

	/**
	 * Set Format.
	 * Format of the data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFormatType (java.lang.String FormatType);

	/**
	 * Get Format.
	 * Format of the data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFormatType();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_FormatType = new ModelColumn<>(I_AD_ImpFormat.class, "FormatType", null);
	String COLUMNNAME_FormatType = "FormatType";

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

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_ImpFormat.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsManualImport.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualImport (boolean IsManualImport);

	/**
	 * Get IsManualImport.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualImport();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_IsManualImport = new ModelColumn<>(I_AD_ImpFormat.class, "IsManualImport", null);
	String COLUMNNAME_IsManualImport = "IsManualImport";

	/**
	 * Set Multi Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMultiLine (boolean IsMultiLine);

	/**
	 * Get Multi Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMultiLine();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_IsMultiLine = new ModelColumn<>(I_AD_ImpFormat.class, "IsMultiLine", null);
	String COLUMNNAME_IsMultiLine = "IsMultiLine";

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

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_Name = new ModelColumn<>(I_AD_ImpFormat.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_Processing = new ModelColumn<>(I_AD_ImpFormat.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Skip First N Rows.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSkipFirstNRows (int SkipFirstNRows);

	/**
	 * Get Skip First N Rows.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSkipFirstNRows();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_SkipFirstNRows = new ModelColumn<>(I_AD_ImpFormat.class, "SkipFirstNRows", null);
	String COLUMNNAME_SkipFirstNRows = "SkipFirstNRows";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_ImpFormat, Object> COLUMN_Updated = new ModelColumn<>(I_AD_ImpFormat.class, "Updated", null);
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
