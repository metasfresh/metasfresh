package de.metas.payment.paypal.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OLCand_Paypal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OLCand_Paypal 
{

	String Table_Name = "C_OLCand_Paypal";

//	/** AD_Table_ID=541605 */
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
	 * Set Orderline Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_ID (int C_OLCand_ID);

	/**
	 * Get Orderline Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_ID();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_C_OLCand_ID = new ModelColumn<>(I_C_OLCand_Paypal.class, "C_OLCand_ID", null);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Set C_OLCand_Paypal.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_Paypal_ID (int C_OLCand_Paypal_ID);

	/**
	 * Get C_OLCand_Paypal.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_Paypal_ID();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_C_OLCand_Paypal_ID = new ModelColumn<>(I_C_OLCand_Paypal.class, "C_OLCand_Paypal_ID", null);
	String COLUMNNAME_C_OLCand_Paypal_ID = "C_OLCand_Paypal_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCand_Paypal.class, "Created", null);
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

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCand_Paypal.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PayPal Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_Token (@Nullable java.lang.String PayPal_Token);

	/**
	 * Get PayPal Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_Token();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_PayPal_Token = new ModelColumn<>(I_C_OLCand_Paypal.class, "PayPal_Token", null);
	String COLUMNNAME_PayPal_Token = "PayPal_Token";

	/**
	 * Set Paypal Transaction-ID .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_Transaction_ID (@Nullable java.lang.String PayPal_Transaction_ID);

	/**
	 * Get Paypal Transaction-ID .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_Transaction_ID();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_PayPal_Transaction_ID = new ModelColumn<>(I_C_OLCand_Paypal.class, "PayPal_Transaction_ID", null);
	String COLUMNNAME_PayPal_Transaction_ID = "PayPal_Transaction_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCand_Paypal, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCand_Paypal.class, "Updated", null);
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
