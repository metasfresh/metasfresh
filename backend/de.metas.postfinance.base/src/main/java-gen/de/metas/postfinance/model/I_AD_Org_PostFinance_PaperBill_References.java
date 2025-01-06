package de.metas.postfinance.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Org_PostFinance_PaperBill_References
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Org_PostFinance_PaperBill_References 
{

	String Table_Name = "AD_Org_PostFinance_PaperBill_References";

//	/** AD_Table_ID=542403 */
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
	 * Set Paper Bill Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_PostFinance_PaperBill_References_ID (int AD_Org_PostFinance_PaperBill_References_ID);

	/**
	 * Get Paper Bill Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_PostFinance_PaperBill_References_ID();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_AD_Org_PostFinance_PaperBill_References_ID = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "AD_Org_PostFinance_PaperBill_References_ID", null);
	String COLUMNNAME_AD_Org_PostFinance_PaperBill_References_ID = "AD_Org_PostFinance_PaperBill_References_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_Created = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "Created", null);
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

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reference Position.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReference_Position (java.lang.String Reference_Position);

	/**
	 * Get Reference Position.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReference_Position();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_Reference_Position = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "Reference_Position", null);
	String COLUMNNAME_Reference_Position = "Reference_Position";

	/**
	 * Set Reference Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReference_Type (java.lang.String Reference_Type);

	/**
	 * Get Reference Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReference_Type();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_Reference_Type = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "Reference_Type", null);
	String COLUMNNAME_Reference_Type = "Reference_Type";

	/**
	 * Set Reference Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReference_Value (java.lang.String Reference_Value);

	/**
	 * Get Reference Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReference_Value();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_Reference_Value = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "Reference_Value", null);
	String COLUMNNAME_Reference_Value = "Reference_Value";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Org_PostFinance_PaperBill_References, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Org_PostFinance_PaperBill_References.class, "Updated", null);
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
