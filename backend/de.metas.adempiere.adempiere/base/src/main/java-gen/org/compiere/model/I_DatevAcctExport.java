package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for DatevAcctExport
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DatevAcctExport 
{

	String Table_Name = "DatevAcctExport";

//	/** AD_Table_ID=541522 */
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
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	org.compiere.model.I_C_Period getC_Period();

	void setC_Period(org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_DatevAcctExport, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_DatevAcctExport.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
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

	ModelColumn<I_DatevAcctExport, Object> COLUMN_Created = new ModelColumn<>(I_DatevAcctExport.class, "Created", null);
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
	 * Set Datev Accounting Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatevAcctExport_ID (int DatevAcctExport_ID);

	/**
	 * Get Datev Accounting Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDatevAcctExport_ID();

	ModelColumn<I_DatevAcctExport, Object> COLUMN_DatevAcctExport_ID = new ModelColumn<>(I_DatevAcctExport.class, "DatevAcctExport_ID", null);
	String COLUMNNAME_DatevAcctExport_ID = "DatevAcctExport_ID";

	/**
	 * Set Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportBy_ID (int ExportBy_ID);

	/**
	 * Get Export by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExportBy_ID();

	String COLUMNNAME_ExportBy_ID = "ExportBy_ID";

	/**
	 * Set Export date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportDate (@Nullable java.sql.Timestamp ExportDate);

	/**
	 * Get Export date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getExportDate();

	ModelColumn<I_DatevAcctExport, Object> COLUMN_ExportDate = new ModelColumn<>(I_DatevAcctExport.class, "ExportDate", null);
	String COLUMNNAME_ExportDate = "ExportDate";

	/**
	 * Set Export Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportType (java.lang.String ExportType);

	/**
	 * Get Export Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportType();

	ModelColumn<I_DatevAcctExport, Object> COLUMN_ExportType = new ModelColumn<>(I_DatevAcctExport.class, "ExportType", null);
	String COLUMNNAME_ExportType = "ExportType";

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

	ModelColumn<I_DatevAcctExport, Object> COLUMN_IsActive = new ModelColumn<>(I_DatevAcctExport.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_DatevAcctExport, Object> COLUMN_Processed = new ModelColumn<>(I_DatevAcctExport.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DatevAcctExport, Object> COLUMN_Updated = new ModelColumn<>(I_DatevAcctExport.class, "Updated", null);
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
