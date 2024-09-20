package de.metas.pos.repository.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_Payment 
{

	String Table_Name = "C_POS_Payment";

//	/** AD_Table_ID=542437 */
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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Amount = new ModelColumn<>(I_C_POS_Payment.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Order_ID (int C_POS_Order_ID);

	/**
	 * Get POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Order_ID();

	de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order();

	void setC_POS_Order(de.metas.pos.repository.model.I_C_POS_Order C_POS_Order);

	ModelColumn<I_C_POS_Payment, de.metas.pos.repository.model.I_C_POS_Order> COLUMN_C_POS_Order_ID = new ModelColumn<>(I_C_POS_Payment.class, "C_POS_Order_ID", de.metas.pos.repository.model.I_C_POS_Order.class);
	String COLUMNNAME_C_POS_Order_ID = "C_POS_Order_ID";

	/**
	 * Set POS Payment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Payment_ID (int C_POS_Payment_ID);

	/**
	 * Get POS Payment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Payment_ID();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_C_POS_Payment_ID = new ModelColumn<>(I_C_POS_Payment.class, "C_POS_Payment_ID", null);
	String COLUMNNAME_C_POS_Payment_ID = "C_POS_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_Payment.class, "Created", null);
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
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalId();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_POS_Payment.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_Payment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set POS Payment Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentMethod (java.lang.String POSPaymentMethod);

	/**
	 * Get POS Payment Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPOSPaymentMethod();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentMethod = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentMethod", null);
	String COLUMNNAME_POSPaymentMethod = "POSPaymentMethod";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_Payment.class, "Updated", null);
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
