package org.compiere.model;


/** Generated Interface for M_MatchPO
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_MatchPO 
{

    /** TableName=M_MatchPO */
    public static final String Table_Name = "M_MatchPO";

    /** AD_Table_ID=473 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_Client>(I_M_MatchPO.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_Org>(I_M_MatchPO.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

    /** Column definition for C_InvoiceLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_C_InvoiceLine>(I_M_MatchPO.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_C_OrderLine>(I_M_MatchPO.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_User>(I_M_MatchPO.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Vorgangsdatum.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Vorgangsdatum.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

    /** Column definition for DateTrx */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "DateTrx", null);
    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "DocumentNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_AttributeSetInstance>(I_M_MatchPO.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_InOutLine>(I_M_MatchPO.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Abgleich Bestellung.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_MatchPO_ID (int M_MatchPO_ID);

	/**
	 * Get Abgleich Bestellung.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_MatchPO_ID();

    /** Column definition for M_MatchPO_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_M_MatchPO_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "M_MatchPO_ID", null);
    /** Column name M_MatchPO_ID */
    public static final String COLUMNNAME_M_MatchPO_ID = "M_MatchPO_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_M_Product>(I_M_MatchPO.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Price Match Difference.
	 * Difference between Purchase and Invoice Price per matched line
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceMatchDifference (java.math.BigDecimal PriceMatchDifference);

	/**
	 * Get Price Match Difference.
	 * Difference between Purchase and Invoice Price per matched line
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceMatchDifference();

    /** Column definition for PriceMatchDifference */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_PriceMatchDifference = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "PriceMatchDifference", null);
    /** Column name PriceMatchDifference */
    public static final String COLUMNNAME_PriceMatchDifference = "PriceMatchDifference";

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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_MatchPO, Object>(I_M_MatchPO.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_MatchPO, org.compiere.model.I_AD_User>(I_M_MatchPO.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
