package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for PostFinance_BPartner_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PostFinance_BPartner_Config 
{

	String Table_Name = "PostFinance_BPartner_Config";

//	/** AD_Table_ID=542387 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PostFinance_BPartner_Config, Object> COLUMN_Created = new ModelColumn<>(I_PostFinance_BPartner_Config.class, "Created", null);
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

	ModelColumn<I_PostFinance_BPartner_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_PostFinance_BPartner_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PostFinance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_BPartner_Config_ID (int PostFinance_BPartner_Config_ID);

	/**
	 * Get PostFinance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPostFinance_BPartner_Config_ID();

	ModelColumn<I_PostFinance_BPartner_Config, Object> COLUMN_PostFinance_BPartner_Config_ID = new ModelColumn<>(I_PostFinance_BPartner_Config.class, "PostFinance_BPartner_Config_ID", null);
	String COLUMNNAME_PostFinance_BPartner_Config_ID = "PostFinance_BPartner_Config_ID";

	/**
	 * Set Customer eBill ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Receiver_eBillId (java.lang.String PostFinance_Receiver_eBillId);

	/**
	 * Get Customer eBill ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostFinance_Receiver_eBillId();

	ModelColumn<I_PostFinance_BPartner_Config, Object> COLUMN_PostFinance_Receiver_eBillId = new ModelColumn<>(I_PostFinance_BPartner_Config.class, "PostFinance_Receiver_eBillId", null);
	String COLUMNNAME_PostFinance_Receiver_eBillId = "PostFinance_Receiver_eBillId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PostFinance_BPartner_Config, Object> COLUMN_Updated = new ModelColumn<>(I_PostFinance_BPartner_Config.class, "Updated", null);
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
