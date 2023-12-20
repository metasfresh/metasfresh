package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_Note
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_Note 
{

	String Table_Name = "GPLR_Report_Note";

//	/** AD_Table_ID=542348 */
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

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_Note.class, "Created", null);
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
	 * Set GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_ID (int GPLR_Report_ID);

	/**
	 * Get GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_ID();

	org.compiere.model.I_GPLR_Report getGPLR_Report();

	void setGPLR_Report(org.compiere.model.I_GPLR_Report GPLR_Report);

	ModelColumn<I_GPLR_Report_Note, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_Note.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set GPLR Report - Note.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_Note_ID (int GPLR_Report_Note_ID);

	/**
	 * Get GPLR Report - Note.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_Note_ID();

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_GPLR_Report_Note_ID = new ModelColumn<>(I_GPLR_Report_Note.class, "GPLR_Report_Note_ID", null);
	String COLUMNNAME_GPLR_Report_Note_ID = "GPLR_Report_Note_ID";

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

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_Note.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_Note = new ModelColumn<>(I_GPLR_Report_Note.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Source.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource (@Nullable java.lang.String Source);

	/**
	 * Get Source.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSource();

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_Source = new ModelColumn<>(I_GPLR_Report_Note.class, "Source", null);
	String COLUMNNAME_Source = "Source";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report_Note, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_Note.class, "Updated", null);
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
