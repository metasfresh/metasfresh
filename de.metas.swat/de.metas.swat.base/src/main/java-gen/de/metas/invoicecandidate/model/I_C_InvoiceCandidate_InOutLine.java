package de.metas.invoicecandidate.model;


/** Generated Interface for C_InvoiceCandidate_InOutLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_InvoiceCandidate_InOutLine 
{

    /** TableName=C_InvoiceCandidate_InOutLine */
    public static final String Table_Name = "C_InvoiceCandidate_InOutLine";

    /** AD_Table_ID=540579 */
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_Client>(I_C_InvoiceCandidate_InOutLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_Org>(I_C_InvoiceCandidate_InOutLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_InvoiceCandidate_InOutLine.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set C_InvoiceCandidate_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceCandidate_InOutLine_ID (int C_InvoiceCandidate_InOutLine_ID);

	/**
	 * Get C_InvoiceCandidate_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceCandidate_InOutLine_ID();

    /** Column definition for C_InvoiceCandidate_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_C_InvoiceCandidate_InOutLine_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "C_InvoiceCandidate_InOutLine_ID", null);
    /** Column name C_InvoiceCandidate_InOutLine_ID */
    public static final String COLUMNNAME_C_InvoiceCandidate_InOutLine_ID = "C_InvoiceCandidate_InOutLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_User>(I_C_InvoiceCandidate_InOutLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_C_UOM>(I_C_InvoiceCandidate_InOutLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing);

	/**
	 * Get Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isInOutApprovedForInvoicing();

    /** Column definition for IsInOutApprovedForInvoicing */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_IsInOutApprovedForInvoicing = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "IsInOutApprovedForInvoicing", null);
    /** Column name IsInOutApprovedForInvoicing */
    public static final String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_M_InOutLine>(I_C_InvoiceCandidate_InOutLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Geliefert Catch.
	 * Tatsächlich gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInUOM_Catch (java.math.BigDecimal QtyDeliveredInUOM_Catch);

	/**
	 * Get Geliefert Catch.
	 * Tatsächlich gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInUOM_Catch();

    /** Column definition for QtyDeliveredInUOM_Catch */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Catch = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Catch", null);
    /** Column name QtyDeliveredInUOM_Catch */
    public static final String COLUMNNAME_QtyDeliveredInUOM_Catch = "QtyDeliveredInUOM_Catch";

	/**
	 * Set Geliefert Nominal.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInUOM_Nominal (java.math.BigDecimal QtyDeliveredInUOM_Nominal);

	/**
	 * Get Geliefert Nominal.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInUOM_Nominal();

    /** Column definition for QtyDeliveredInUOM_Nominal */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Nominal = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Nominal", null);
    /** Column name QtyDeliveredInUOM_Nominal */
    public static final String COLUMNNAME_QtyDeliveredInUOM_Nominal = "QtyDeliveredInUOM_Nominal";

	/**
	 * Set Geliefert abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInUOM_Override (java.math.BigDecimal QtyDeliveredInUOM_Override);

	/**
	 * Get Geliefert abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInUOM_Override();

    /** Column definition for QtyDeliveredInUOM_Override */
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Override = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Override", null);
    /** Column name QtyDeliveredInUOM_Override */
    public static final String COLUMNNAME_QtyDeliveredInUOM_Override = "QtyDeliveredInUOM_Override";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, Object>(I_C_InvoiceCandidate_InOutLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_AD_User>(I_C_InvoiceCandidate_InOutLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
