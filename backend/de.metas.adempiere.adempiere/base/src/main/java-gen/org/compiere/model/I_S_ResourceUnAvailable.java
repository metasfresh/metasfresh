package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for S_ResourceUnAvailable
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_ResourceUnAvailable 
{

	String Table_Name = "S_ResourceUnAvailable";

//	/** AD_Table_ID=482 */
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

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_Created = new ModelColumn<>(I_S_ResourceUnAvailable.class, "Created", null);
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
	 * Set Datum von.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateFrom (java.sql.Timestamp DateFrom);

	/**
	 * Get Datum von.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateFrom();

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_DateFrom = new ModelColumn<>(I_S_ResourceUnAvailable.class, "DateFrom", null);
	String COLUMNNAME_DateFrom = "DateFrom";

	/**
	 * Set Datum bis.
	 * End date of a date range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateTo (@Nullable java.sql.Timestamp DateTo);

	/**
	 * Get Datum bis.
	 * End date of a date range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateTo();

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_DateTo = new ModelColumn<>(I_S_ResourceUnAvailable.class, "DateTo", null);
	String COLUMNNAME_DateTo = "DateTo";

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

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_Description = new ModelColumn<>(I_S_ResourceUnAvailable.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_IsActive = new ModelColumn<>(I_S_ResourceUnAvailable.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Nicht verfügbar.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_ResourceUnAvailable_ID (int S_ResourceUnAvailable_ID);

	/**
	 * Get Nicht verfügbar.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_ResourceUnAvailable_ID();

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_S_ResourceUnAvailable_ID = new ModelColumn<>(I_S_ResourceUnAvailable.class, "S_ResourceUnAvailable_ID", null);
	String COLUMNNAME_S_ResourceUnAvailable_ID = "S_ResourceUnAvailable_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_ResourceUnAvailable, Object> COLUMN_Updated = new ModelColumn<>(I_S_ResourceUnAvailable.class, "Updated", null);
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
