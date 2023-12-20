package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_PurchaseOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_PurchaseOrder 
{

	String Table_Name = "GPLR_Report_PurchaseOrder";

//	/** AD_Table_ID=542344 */
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
	 * Set Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartnerName (java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerName();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_BPartnerName = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerValue();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_BPartnerValue = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "BPartnerValue", null);
	String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set BPartner VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartner_VatId (@Nullable java.lang.String BPartner_VatId);

	/**
	 * Get BPartner VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartner_VatId();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_BPartner_VatId = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "BPartner_VatId", null);
	String COLUMNNAME_BPartner_VatId = "BPartner_VatId";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "Created", null);
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
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (@Nullable BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_DocumentNo = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set FEC Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_DocumentNo (@Nullable java.lang.String FEC_DocumentNo);

	/**
	 * Get FEC Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFEC_DocumentNo();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_FEC_DocumentNo = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "FEC_DocumentNo", null);
	String COLUMNNAME_FEC_DocumentNo = "FEC_DocumentNo";

	/**
	 * Set Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setForeignCurrencyCode (java.lang.String ForeignCurrencyCode);

	/**
	 * Get Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getForeignCurrencyCode();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_ForeignCurrencyCode = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "ForeignCurrencyCode", null);
	String COLUMNNAME_ForeignCurrencyCode = "ForeignCurrencyCode";

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

	ModelColumn<I_GPLR_Report_PurchaseOrder, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set GPLR Report - Purchase Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_PurchaseOrder_ID (int GPLR_Report_PurchaseOrder_ID);

	/**
	 * Get GPLR Report - Purchase Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_PurchaseOrder_ID();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_GPLR_Report_PurchaseOrder_ID = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "GPLR_Report_PurchaseOrder_ID", null);
	String COLUMNNAME_GPLR_Report_PurchaseOrder_ID = "GPLR_Report_PurchaseOrder_ID";

	/**
	 * Set Incoterm Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncoterm_Code (@Nullable java.lang.String Incoterm_Code);

	/**
	 * Get Incoterm Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncoterm_Code();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_Incoterm_Code = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "Incoterm_Code", null);
	String COLUMNNAME_Incoterm_Code = "Incoterm_Code";

	/**
	 * Set Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

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

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Payment Term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentTermInfo (java.lang.String PaymentTermInfo);

	/**
	 * Get Payment Term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentTermInfo();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_PaymentTermInfo = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "PaymentTermInfo", null);
	String COLUMNNAME_PaymentTermInfo = "PaymentTermInfo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "Updated", null);
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
	 * Set Vendor Reference No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorReferenceNo (@Nullable java.lang.String VendorReferenceNo);

	/**
	 * Get Vendor Reference No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorReferenceNo();

	ModelColumn<I_GPLR_Report_PurchaseOrder, Object> COLUMN_VendorReferenceNo = new ModelColumn<>(I_GPLR_Report_PurchaseOrder.class, "VendorReferenceNo", null);
	String COLUMNNAME_VendorReferenceNo = "VendorReferenceNo";
}
