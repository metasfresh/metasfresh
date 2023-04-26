package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Flatrate_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_Data 
{

	String Table_Name = "C_Flatrate_Data";

//	/** AD_Table_ID=540310 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_Flatrate_DataEntry_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_DataEntry_IncludedT (@Nullable java.lang.String C_Flatrate_DataEntry_IncludedT);

	/**
	 * Get C_Flatrate_DataEntry_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_Flatrate_DataEntry_IncludedT();

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_C_Flatrate_DataEntry_IncludedT = new ModelColumn<>(I_C_Flatrate_Data.class, "C_Flatrate_DataEntry_IncludedT", null);
	String COLUMNNAME_C_Flatrate_DataEntry_IncludedT = "C_Flatrate_DataEntry_IncludedT";

	/**
	 * Set Datenerfassung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Data_ID (int C_Flatrate_Data_ID);

	/**
	 * Get Datenerfassung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Data_ID();

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_C_Flatrate_Data_ID = new ModelColumn<>(I_C_Flatrate_Data.class, "C_Flatrate_Data_ID", null);
	String COLUMNNAME_C_Flatrate_Data_ID = "C_Flatrate_Data_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_Data.class, "Created", null);
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
	 * Set Verarbeitet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasContracts (boolean HasContracts);

	/**
	 * Get Verarbeitet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasContracts();

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_HasContracts = new ModelColumn<>(I_C_Flatrate_Data.class, "HasContracts", null);
	String COLUMNNAME_HasContracts = "HasContracts";

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

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_Data.class, "IsActive", null);
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

	ModelColumn<I_C_Flatrate_Data, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_Data.class, "Updated", null);
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
