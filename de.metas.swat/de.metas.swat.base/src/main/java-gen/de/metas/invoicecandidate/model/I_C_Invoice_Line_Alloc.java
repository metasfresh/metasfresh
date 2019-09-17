package de.metas.invoicecandidate.model;


/** Generated Interface for C_Invoice_Line_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Line_Alloc 
{

    /** TableName=C_Invoice_Line_Alloc */
    public static final String Table_Name = "C_Invoice_Line_Alloc";

    /** AD_Table_ID=540321 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_Client>(I_C_Invoice_Line_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_Org>(I_C_Invoice_Line_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID);

	/**
	 * Get Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Agg_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg();

	public void setC_Invoice_Candidate_Agg(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg);

    /** Column definition for C_Invoice_Candidate_Agg_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg> COLUMN_C_Invoice_Candidate_Agg_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Candidate_Agg_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
    /** Column name C_Invoice_Candidate_Agg_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Agg_ID = "C_Invoice_Candidate_Agg_ID";

	/**
	 * Set Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Rechnungskandidat - Rechungszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Line_Alloc_ID (int C_Invoice_Line_Alloc_ID);

	/**
	 * Get Rechnungskandidat - Rechungszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Line_Alloc_ID();

    /** Column definition for C_Invoice_Line_Alloc_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_C_Invoice_Line_Alloc_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Line_Alloc_ID", null);
    /** Column name C_Invoice_Line_Alloc_ID */
    public static final String COLUMNNAME_C_Invoice_Line_Alloc_ID = "C_Invoice_Line_Alloc_ID";

	/**
	 * Set Rechnungsposition.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Rechnungsposition.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

    /** Column definition for C_InvoiceLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_C_InvoiceLine>(I_C_Invoice_Line_Alloc.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_User>(I_C_Invoice_Line_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_C_UOM>(I_C_Invoice_Line_Alloc.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/**
	 * Set Preis Abw..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceEntered_Override (java.math.BigDecimal PriceEntered_Override);

	/**
	 * Get Preis Abw..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceEntered_Override();

    /** Column definition for PriceEntered_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_PriceEntered_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "PriceEntered_Override", null);
    /** Column name PriceEntered_Override */
    public static final String COLUMNNAME_PriceEntered_Override = "PriceEntered_Override";

	/**
	 * Set Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Abgerechnet.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoicedInUOM (java.math.BigDecimal QtyInvoicedInUOM);

	/**
	 * Get Abgerechnet.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoicedInUOM();

    /** Column definition for QtyInvoicedInUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyInvoicedInUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "QtyInvoicedInUOM", null);
    /** Column name QtyInvoicedInUOM */
    public static final String COLUMNNAME_QtyInvoicedInUOM = "QtyInvoicedInUOM";

	/**
	 * Set Zu berechn. Menge abw..
	 * Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoice_Override (java.math.BigDecimal QtyToInvoice_Override);

	/**
	 * Get Zu berechn. Menge abw..
	 * Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoice_Override();

    /** Column definition for QtyToInvoice_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyToInvoice_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "QtyToInvoice_Override", null);
    /** Column name QtyToInvoice_Override */
    public static final String COLUMNNAME_QtyToInvoice_Override = "QtyToInvoice_Override";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, Object>(I_C_Invoice_Line_Alloc.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_AD_User>(I_C_Invoice_Line_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
