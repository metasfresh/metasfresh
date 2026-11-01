package de.metas.esb.edi.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_EDI_Setting
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_EDI_Setting 
{

	String Table_Name = "C_BPartner_EDI_Setting";

//	/** AD_Table_ID=542610 */
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
	 * Set EDI Setting Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_EDI_Setting_ID (int C_BPartner_EDI_Setting_ID);

	/**
	 * Get EDI Setting Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_EDI_Setting_ID();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_C_BPartner_EDI_Setting_ID = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "C_BPartner_EDI_Setting_ID", null);
	String COLUMNNAME_C_BPartner_EDI_Setting_ID = "C_BPartner_EDI_Setting_ID";

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
	 * Set Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "Created", null);
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
	 * Set "CU per TU" for Undefined Packing Capacity.
	 * "CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEdiDESADVDefaultItemCapacity (@Nullable BigDecimal EdiDESADVDefaultItemCapacity);

	/**
	 * Get "CU per TU" for Undefined Packing Capacity.
	 * "CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getEdiDESADVDefaultItemCapacity();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiDESADVDefaultItemCapacity = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiDESADVDefaultItemCapacity", null);
	String COLUMNNAME_EdiDESADVDefaultItemCapacity = "EdiDESADVDefaultItemCapacity";

	/**
	 * Set EDI-DESADV External System Config.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEdiDESADV_ExternalSystem_Config_ID (int EdiDESADV_ExternalSystem_Config_ID);

	/**
	 * Get EDI-DESADV External System Config.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEdiDESADV_ExternalSystem_Config_ID();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiDESADV_ExternalSystem_Config_ID = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiDESADV_ExternalSystem_Config_ID", null);
	String COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID = "EdiDESADV_ExternalSystem_Config_ID";

	/**
	 * Set EDI ID of the DESADV Recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEdiDesadvRecipientGLN (@Nullable java.lang.String EdiDesadvRecipientGLN);

	/**
	 * Get EDI ID of the DESADV Recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEdiDesadvRecipientGLN();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiDesadvRecipientGLN = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiDesadvRecipientGLN", null);
	String COLUMNNAME_EdiDesadvRecipientGLN = "EdiDesadvRecipientGLN";

	/**
	 * Set EDI-DESADV Sending Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEdiDESADVSendingMode (java.lang.String EdiDESADVSendingMode);

	/**
	 * Get EDI-DESADV Sending Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEdiDESADVSendingMode();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiDESADVSendingMode = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiDESADVSendingMode", null);
	String COLUMNNAME_EdiDESADVSendingMode = "EdiDESADVSendingMode";

	/**
	 * Set EDI-INVOIC External System Config.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEdiINVOIC_ExternalSystem_Config_ID (int EdiINVOIC_ExternalSystem_Config_ID);

	/**
	 * Get EDI-INVOIC External System Config.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEdiINVOIC_ExternalSystem_Config_ID();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiINVOIC_ExternalSystem_Config_ID = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiINVOIC_ExternalSystem_Config_ID", null);
	String COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID = "EdiINVOIC_ExternalSystem_Config_ID";

	/**
	 * Set EDI ID of the INVOIC Recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEdiInvoicRecipientGLN (@Nullable java.lang.String EdiInvoicRecipientGLN);

	/**
	 * Get EDI ID of the INVOIC Recipient.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEdiInvoicRecipientGLN();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiInvoicRecipientGLN = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiInvoicRecipientGLN", null);
	String COLUMNNAME_EdiInvoicRecipientGLN = "EdiInvoicRecipientGLN";

	/**
	 * Set EDI-INVOIC Sending Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEdiINVOICSendingMode (java.lang.String EdiINVOICSendingMode);

	/**
	 * Get EDI-INVOIC Sending Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEdiINVOICSendingMode();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_EdiINVOICSendingMode = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "EdiINVOICSendingMode", null);
	String COLUMNNAME_EdiINVOICSendingMode = "EdiINVOICSendingMode";

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

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEdiDesadvRecipient (boolean IsEdiDesadvRecipient);

	/**
	 * Get EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEdiDesadvRecipient();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_IsEdiDesadvRecipient = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "IsEdiDesadvRecipient", null);
	String COLUMNNAME_IsEdiDesadvRecipient = "IsEdiDesadvRecipient";

	/**
	 * Set EDI INVOIC Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEdiInvoicRecipient (boolean IsEdiInvoicRecipient);

	/**
	 * Get EDI INVOIC Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEdiInvoicRecipient();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_IsEdiInvoicRecipient = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "IsEdiInvoicRecipient", null);
	String COLUMNNAME_IsEdiInvoicRecipient = "IsEdiInvoicRecipient";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_EDI_Setting, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_EDI_Setting.class, "Updated", null);
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
