package org.compiere.model;


/** Generated Interface for M_InOutConfirm
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_InOutConfirm 
{

    /** TableName=M_InOutConfirm */
    public static final String Table_Name = "M_InOutConfirm";

    /** AD_Table_ID=727 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_Client>(I_M_InOutConfirm.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_Org>(I_M_InOutConfirm.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Freigabe-Betrag.
	 * Document Approval Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setApprovalAmt (java.math.BigDecimal ApprovalAmt);

	/**
	 * Get Freigabe-Betrag.
	 * Document Approval Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getApprovalAmt();

    /** Column definition for ApprovalAmt */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_ApprovalAmt = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "ApprovalAmt", null);
    /** Column name ApprovalAmt */
    public static final String COLUMNNAME_ApprovalAmt = "ApprovalAmt";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_C_Invoice>(I_M_InOutConfirm.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Bestätigungs-Nr..
	 * Confirmation Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConfirmationNo (java.lang.String ConfirmationNo);

	/**
	 * Get Bestätigungs-Nr..
	 * Confirmation Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfirmationNo();

    /** Column definition for ConfirmationNo */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_ConfirmationNo = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "ConfirmationNo", null);
    /** Column name ConfirmationNo */
    public static final String COLUMNNAME_ConfirmationNo = "ConfirmationNo";

	/**
	 * Set Bestätigungs-Art.
	 * Type of confirmation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfirmType (java.lang.String ConfirmType);

	/**
	 * Get Bestätigungs-Art.
	 * Type of confirmation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfirmType();

    /** Column definition for ConfirmType */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_ConfirmType = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "ConfirmType", null);
    /** Column name ConfirmType */
    public static final String COLUMNNAME_ConfirmType = "ConfirmType";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_User>(I_M_InOutConfirm.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Cancelled.
	 * The transaction was cancelled
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCancelled (boolean IsCancelled);

	/**
	 * Get Cancelled.
	 * The transaction was cancelled
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCancelled();

    /** Column definition for IsCancelled */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_IsCancelled = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "IsCancelled", null);
    /** Column name IsCancelled */
    public static final String COLUMNNAME_IsCancelled = "IsCancelled";

	/**
	 * Set In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInDispute();

    /** Column definition for IsInDispute */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_IsInDispute = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "IsInDispute", null);
    /** Column name IsInDispute */
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_M_InOut>(I_M_InOutConfirm.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Bestätigung Versand/Wareneingang.
	 * Material Shipment or Receipt Confirmation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InOutConfirm_ID (int M_InOutConfirm_ID);

	/**
	 * Get Bestätigung Versand/Wareneingang.
	 * Material Shipment or Receipt Confirmation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InOutConfirm_ID();

    /** Column definition for M_InOutConfirm_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_M_InOutConfirm_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "M_InOutConfirm_ID", null);
    /** Column name M_InOutConfirm_ID */
    public static final String COLUMNNAME_M_InOutConfirm_ID = "M_InOutConfirm_ID";

	/**
	 * Set Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory();

	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

    /** Column definition for M_Inventory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_M_Inventory>(I_M_InOutConfirm.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, Object>(I_M_InOutConfirm.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_InOutConfirm, org.compiere.model.I_AD_User>(I_M_InOutConfirm.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
