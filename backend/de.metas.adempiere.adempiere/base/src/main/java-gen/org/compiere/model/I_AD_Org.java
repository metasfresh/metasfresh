package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Org
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Org 
{

	String Table_Name = "AD_Org";

//	/** AD_Table_ID=155 */
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
	 * Set Replizierung - Strategie.
	 * Data Replication Strategy
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID);

	/**
	 * Get Replizierung - Strategie.
	 * Data Replication Strategy
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_ReplicationStrategy_ID();

	@Nullable org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy();

	void setAD_ReplicationStrategy(@Nullable org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy);

	ModelColumn<I_AD_Org, org.compiere.model.I_AD_ReplicationStrategy> COLUMN_AD_ReplicationStrategy_ID = new ModelColumn<>(I_AD_Org.class, "AD_ReplicationStrategy_ID", org.compiere.model.I_AD_ReplicationStrategy.class);
	String COLUMNNAME_AD_ReplicationStrategy_ID = "AD_ReplicationStrategy_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Org, Object> COLUMN_Created = new ModelColumn<>(I_AD_Org.class, "Created", null);
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

	ModelColumn<I_AD_Org, Object> COLUMN_Description = new ModelColumn<>(I_AD_Org.class, "Description", null);
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

	ModelColumn<I_AD_Org, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Org.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set One-Stop Shop (OSS).
	 * Needs to be ticked if the deliveries to foreign EU countries since the begin of 2020 exceed a volumn of 10000 euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEUOneStopShop (boolean IsEUOneStopShop);

	/**
	 * Get One-Stop Shop (OSS).
	 * Needs to be ticked if the deliveries to foreign EU countries since the begin of 2020 exceed a volumn of 10000 euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEUOneStopShop();

	ModelColumn<I_AD_Org, Object> COLUMN_IsEUOneStopShop = new ModelColumn<>(I_AD_Org.class, "IsEUOneStopShop", null);
	String COLUMNNAME_IsEUOneStopShop = "IsEUOneStopShop";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_AD_Org, Object> COLUMN_IsSummary = new ModelColumn<>(I_AD_Org.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

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

	ModelColumn<I_AD_Org, Object> COLUMN_Name = new ModelColumn<>(I_AD_Org.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Org, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Org.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_AD_Org, Object> COLUMN_Value = new ModelColumn<>(I_AD_Org.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
