package de.metas.invoicecandidate.model;


/** Generated Interface for C_Invoice_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Candidate 
{

    /** TableName=C_Invoice_Candidate */
    public static final String Table_Name = "C_Invoice_Candidate";

    /** AD_Table_ID=540270 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Client>(I_C_Invoice_Candidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Statusmeldung.
	 * System-Nachricht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Note_ID (int AD_Note_ID);

	/**
	 * Get Statusmeldung.
	 * System-Nachricht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Note_ID();

    /** Column definition for AD_Note_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Note> COLUMN_AD_Note_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Note>(I_C_Invoice_Candidate.class, "AD_Note_ID", org.compiere.model.I_AD_Note.class);
    /** Column name AD_Note_ID */
    public static final String COLUMNNAME_AD_Note_ID = "AD_Note_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Org>(I_C_Invoice_Candidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_Table>(I_C_Invoice_Candidate.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Freigabe zur Fakturierung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setApprovalForInvoicing (boolean ApprovalForInvoicing);

	/**
	 * Get Freigabe zur Fakturierung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApprovalForInvoicing();

    /** Column definition for ApprovalForInvoicing */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ApprovalForInvoicing = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "ApprovalForInvoicing", null);
    /** Column name ApprovalForInvoicing */
    public static final String COLUMNNAME_ApprovalForInvoicing = "ApprovalForInvoicing";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column definition for Bill_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner> COLUMN_Bill_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner>(I_C_Invoice_Candidate.class, "Bill_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Name Rechnungspartner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setBill_BPartner_Name (java.lang.String Bill_BPartner_Name);

	/**
	 * Get Name Rechnungspartner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getBill_BPartner_Name();

    /** Column definition for Bill_BPartner_Name */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Bill_BPartner_Name = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Bill_BPartner_Name", null);
    /** Column name Bill_BPartner_Name */
    public static final String COLUMNNAME_Bill_BPartner_Name = "Bill_BPartner_Name";

	/**
	 * Set Rechnungsstandort.
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Rechnungsstandort.
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_ID();

    /** Column definition for Bill_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location> COLUMN_Bill_Location_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location>(I_C_Invoice_Candidate.class, "Bill_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_Override_ID (int Bill_Location_Override_ID);

	/**
	 * Get Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_Override_ID();

    /** Column definition for Bill_Location_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location> COLUMN_Bill_Location_Override_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location>(I_C_Invoice_Candidate.class, "Bill_Location_Override_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name Bill_Location_Override_ID */
    public static final String COLUMNNAME_Bill_Location_Override_ID = "Bill_Location_Override_ID";

	/**
	 * Set Rechnungskontakt.
	 * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Rechnungskontakt.
	 * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_User_ID();

    /** Column definition for Bill_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User> COLUMN_Bill_User_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate.class, "Bill_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Bill_User_ID */
    public static final String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Rechnungskontakt abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_User_ID_Override_ID (int Bill_User_ID_Override_ID);

	/**
	 * Get Rechnungskontakt abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_User_ID_Override_ID();

    /** Column definition for Bill_User_ID_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User> COLUMN_Bill_User_ID_Override_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate.class, "Bill_User_ID_Override_ID", org.compiere.model.I_AD_User.class);
    /** Column name Bill_User_ID_Override_ID */
    public static final String COLUMNNAME_Bill_User_ID_Override_ID = "Bill_User_ID_Override_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Activity>(I_C_Invoice_Candidate.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_SalesRep_ID();

    /** Column definition for C_BPartner_SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner>(I_C_Invoice_Candidate.class, "C_BPartner_SalesRep_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_SalesRep_ID */
    public static final String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Kosten.
	 * Zusätzliche Kosten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Zusätzliche Kosten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Charge>(I_C_Invoice_Candidate.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Kursart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Kursart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

    /** Column definition for C_ConversionType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_ConversionType>(I_C_Invoice_Candidate.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Currency>(I_C_Invoice_Candidate.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Rechnungs-Belegart.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID);

	/**
	 * Get Rechnungs-Belegart.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeInvoice_ID();

    /** Column definition for C_DocTypeInvoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeInvoice_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_DocType>(I_C_Invoice_Candidate.class, "C_DocTypeInvoice_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeInvoice_ID */
    public static final String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Set Rechnungskandidaten-Controller.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ILCandHandler_ID (int C_ILCandHandler_ID);

	/**
	 * Get Rechnungskandidaten-Controller.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ILCandHandler_ID();

	public de.metas.invoicecandidate.model.I_C_ILCandHandler getC_ILCandHandler();

	public void setC_ILCandHandler(de.metas.invoicecandidate.model.I_C_ILCandHandler C_ILCandHandler);

    /** Column definition for C_ILCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_ILCandHandler> COLUMN_C_ILCandHandler_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_ILCandHandler>(I_C_Invoice_Candidate.class, "C_ILCandHandler_ID", de.metas.invoicecandidate.model.I_C_ILCandHandler.class);
    /** Column name C_ILCandHandler_ID */
    public static final String COLUMNNAME_C_ILCandHandler_ID = "C_ILCandHandler_ID";

	/**
	 * Set Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID);

	/**
	 * Get Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Agg_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg();

	public void setC_Invoice_Candidate_Agg(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg);

    /** Column definition for C_Invoice_Candidate_Agg_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg> COLUMN_C_Invoice_Candidate_Agg_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_Agg_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
    /** Column name C_Invoice_Candidate_Agg_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Agg_ID = "C_Invoice_Candidate_Agg_ID";

	/**
	 * Set Abrechnungsgruppe eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_HeaderAggregation_Effective_ID (int C_Invoice_Candidate_HeaderAggregation_Effective_ID);

	/**
	 * Get Abrechnungsgruppe eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_HeaderAggregation_Effective_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Effective();

	public void setC_Invoice_Candidate_HeaderAggregation_Effective(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Effective);

    /** Column definition for C_Invoice_Candidate_HeaderAggregation_Effective_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_Effective_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_Effective_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
    /** Column name C_Invoice_Candidate_HeaderAggregation_Effective_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID = "C_Invoice_Candidate_HeaderAggregation_Effective_ID";

	/**
	 * Set Abrechnungsgruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID);

	/**
	 * Get Abrechnungsgruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_HeaderAggregation_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation();

	public void setC_Invoice_Candidate_HeaderAggregation(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation);

    /** Column definition for C_Invoice_Candidate_HeaderAggregation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
    /** Column name C_Invoice_Candidate_HeaderAggregation_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID = "C_Invoice_Candidate_HeaderAggregation_ID";

	/**
	 * Set Abrechnungsgruppe abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_HeaderAggregation_Override_ID (int C_Invoice_Candidate_HeaderAggregation_Override_ID);

	/**
	 * Get Abrechnungsgruppe abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_HeaderAggregation_Override_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Override();

	public void setC_Invoice_Candidate_HeaderAggregation_Override(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Override);

    /** Column definition for C_Invoice_Candidate_HeaderAggregation_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_Override_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_Override_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
    /** Column name C_Invoice_Candidate_HeaderAggregation_Override_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID = "C_Invoice_Candidate_HeaderAggregation_Override_ID";

	/**
	 * Set Rechnungskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_ID", null);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceSchedule_ID();

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

    /** Column definition for C_InvoiceSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_InvoiceSchedule>(I_C_Invoice_Candidate.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set Auftragspartner.
	 * The partner from C_Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Order_BPartner (int C_Order_BPartner);

	/**
	 * Get Auftragspartner.
	 * The partner from C_Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_Order_BPartner();

    /** Column definition for C_Order_BPartner */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner> COLUMN_C_Order_BPartner = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner>(I_C_Invoice_Candidate.class, "C_Order_BPartner", org.compiere.model.I_C_BPartner.class);
    /** Column name C_Order_BPartner */
    public static final String COLUMNNAME_C_Order_BPartner = "C_Order_BPartner";

	/**
	 * Set Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID);

	/**
	 * Get Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_CompensationGroup_ID();

	public org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup();

	public void setC_Order_CompensationGroup(org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup);

    /** Column definition for C_Order_CompensationGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order_CompensationGroup> COLUMN_C_Order_CompensationGroup_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order_CompensationGroup>(I_C_Invoice_Candidate.class, "C_Order_CompensationGroup_ID", org.compiere.model.I_C_Order_CompensationGroup.class);
    /** Column name C_Order_CompensationGroup_ID */
    public static final String COLUMNNAME_C_Order_CompensationGroup_ID = "C_Order_CompensationGroup_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order>(I_C_Invoice_Candidate.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_OrderLine>(I_C_Invoice_Candidate.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Zahlungsbedingung eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_PaymentTerm_Effective_ID (int C_PaymentTerm_Effective_ID);

	/**
	 * Get Zahlungsbedingung eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_PaymentTerm_Effective_ID();

    /** Column definition for C_PaymentTerm_Effective_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_Effective_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm>(I_C_Invoice_Candidate.class, "C_PaymentTerm_Effective_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_Effective_ID */
    public static final String COLUMNNAME_C_PaymentTerm_Effective_ID = "C_PaymentTerm_Effective_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm>(I_C_Invoice_Candidate.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_Override_ID (int C_PaymentTerm_Override_ID);

	/**
	 * Get Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_Override_ID();

    /** Column definition for C_PaymentTerm_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_Override_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentTerm>(I_C_Invoice_Candidate.class, "C_PaymentTerm_Override_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_Override_ID */
    public static final String COLUMNNAME_C_PaymentTerm_Override_ID = "C_PaymentTerm_Override_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Steuer eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Tax_Effective_ID (int C_Tax_Effective_ID);

	/**
	 * Get Steuer eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Tax_Effective_ID();

    /** Column definition for C_Tax_Effective_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax> COLUMN_C_Tax_Effective_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax>(I_C_Invoice_Candidate.class, "C_Tax_Effective_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_Effective_ID */
    public static final String COLUMNNAME_C_Tax_Effective_ID = "C_Tax_Effective_ID";

	/**
	 * Set Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax>(I_C_Invoice_Candidate.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Steuer abw..
	 * Abweichender Steuersatz
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_Override_ID (int C_Tax_Override_ID);

	/**
	 * Get Steuer abw..
	 * Abweichender Steuersatz
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_Override_ID();

    /** Column definition for C_Tax_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax> COLUMN_C_Tax_Override_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Tax>(I_C_Invoice_Candidate.class, "C_Tax_Override_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_Override_ID */
    public static final String COLUMNNAME_C_Tax_Override_ID = "C_Tax_Override_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM>(I_C_Invoice_Candidate.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Rechnungsdatum.
	 * Datum auf der Rechnung
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Rechnungsdatum.
	 * Datum auf der Rechnung
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Abrechnung ab.
	 * Termin ab dem die Rechnung erstellt werden darf
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateToInvoice (java.sql.Timestamp DateToInvoice);

	/**
	 * Get Abrechnung ab.
	 * Termin ab dem die Rechnung erstellt werden darf
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateToInvoice();

    /** Column definition for DateToInvoice */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateToInvoice", null);
    /** Column name DateToInvoice */
    public static final String COLUMNNAME_DateToInvoice = "DateToInvoice";

	/**
	 * Set Abrechnung ab eff..
	 * Enthält den Wert aus "Abrechnung ab" oder der Überschreibe-Wert aus "Abrechnung ab abw.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDateToInvoice_Effective (java.sql.Timestamp DateToInvoice_Effective);

	/**
	 * Get Abrechnung ab eff..
	 * Enthält den Wert aus "Abrechnung ab" oder der Überschreibe-Wert aus "Abrechnung ab abw.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getDateToInvoice_Effective();

    /** Column definition for DateToInvoice_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice_Effective = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateToInvoice_Effective", null);
    /** Column name DateToInvoice_Effective */
    public static final String COLUMNNAME_DateToInvoice_Effective = "DateToInvoice_Effective";

	/**
	 * Set Abrechnung ab abw..
	 * Überschreibt den regulären Termin ab dem die Rechnung erstellt werden darf
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateToInvoice_Override (java.sql.Timestamp DateToInvoice_Override);

	/**
	 * Get Abrechnung ab abw..
	 * Überschreibt den regulären Termin ab dem die Rechnung erstellt werden darf
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateToInvoice_Override();

    /** Column definition for DateToInvoice_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DateToInvoice_Override", null);
    /** Column name DateToInvoice_Override */
    public static final String COLUMNNAME_DateToInvoice_Override = "DateToInvoice_Override";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescriptionBottom (java.lang.String DescriptionBottom);

	/**
	 * Get Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescriptionBottom();

    /** Column definition for DescriptionBottom */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DescriptionBottom = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DescriptionBottom", null);
    /** Column name DescriptionBottom */
    public static final String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Kopfbeschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescriptionHeader (java.lang.String DescriptionHeader);

	/**
	 * Get Kopfbeschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescriptionHeader();

    /** Column definition for DescriptionHeader */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DescriptionHeader = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "DescriptionHeader", null);
    /** Column name DescriptionHeader */
    public static final String COLUMNNAME_DescriptionHeader = "DescriptionHeader";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Rabatt abw. %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount_Override (java.math.BigDecimal Discount_Override);

	/**
	 * Get Rabatt abw. %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount_Override();

    /** Column definition for Discount_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Discount_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Discount_Override", null);
    /** Column name Discount_Override */
    public static final String COLUMNNAME_Discount_Override = "Discount_Override";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set First ship location.
	 * BParter location of first shipment/receipt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirst_Ship_BPLocation_ID (int First_Ship_BPLocation_ID);

	/**
	 * Get First ship location.
	 * BParter location of first shipment/receipt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFirst_Ship_BPLocation_ID();

    /** Column definition for First_Ship_BPLocation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location> COLUMN_First_Ship_BPLocation_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_BPartner_Location>(I_C_Invoice_Candidate.class, "First_Ship_BPLocation_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name First_Ship_BPLocation_ID */
    public static final String COLUMNNAME_First_Ship_BPLocation_ID = "First_Ship_BPLocation_ID";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationAmtType (java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationAmtType();

    /** Column definition for GroupCompensationAmtType */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationAmtType = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "GroupCompensationAmtType", null);
    /** Column name GroupCompensationAmtType */
    public static final String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationBaseAmt (java.math.BigDecimal GroupCompensationBaseAmt);

	/**
	 * Get Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGroupCompensationBaseAmt();

    /** Column definition for GroupCompensationBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationBaseAmt = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "GroupCompensationBaseAmt", null);
    /** Column name GroupCompensationBaseAmt */
    public static final String COLUMNNAME_GroupCompensationBaseAmt = "GroupCompensationBaseAmt";

	/**
	 * Set Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationPercentage (java.math.BigDecimal GroupCompensationPercentage);

	/**
	 * Get Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGroupCompensationPercentage();

    /** Column definition for GroupCompensationPercentage */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationPercentage = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "GroupCompensationPercentage", null);
    /** Column name GroupCompensationPercentage */
    public static final String COLUMNNAME_GroupCompensationPercentage = "GroupCompensationPercentage";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationType (java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationType();

    /** Column definition for GroupCompensationType */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationType = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "GroupCompensationType", null);
    /** Column name GroupCompensationType */
    public static final String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

	/**
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey();

    /** Column definition for HeaderAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKey = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "HeaderAggregationKey", null);
    /** Column name HeaderAggregationKey */
    public static final String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID);

	/**
	 * Get Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHeaderAggregationKeyBuilder_ID();

    /** Column definition for HeaderAggregationKeyBuilder_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKeyBuilder_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "HeaderAggregationKeyBuilder_ID", null);
    /** Column name HeaderAggregationKeyBuilder_ID */
    public static final String COLUMNNAME_HeaderAggregationKeyBuilder_ID = "HeaderAggregationKeyBuilder_ID";

	/**
	 * Set Kopf-Aggregationsmerkmal (vorgegeben).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey_Calc (java.lang.String HeaderAggregationKey_Calc);

	/**
	 * Get Kopf-Aggregationsmerkmal (vorgegeben).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey_Calc();

    /** Column definition for HeaderAggregationKey_Calc */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKey_Calc = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "HeaderAggregationKey_Calc", null);
    /** Column name HeaderAggregationKey_Calc */
    public static final String COLUMNNAME_HeaderAggregationKey_Calc = "HeaderAggregationKey_Calc";

	/**
	 * Set Abr. Menge basiert auf.
	 * Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Abr. Menge basiert auf.
	 * Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoicableQtyBasedOn();

    /** Column definition for InvoicableQtyBasedOn */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoicableQtyBasedOn = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "InvoicableQtyBasedOn", null);
    /** Column name InvoicableQtyBasedOn */
    public static final String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceRule();

    /** Column definition for InvoiceRule */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Rechnungsstellung eff..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInvoiceRule_Effective (java.lang.String InvoiceRule_Effective);

	/**
	 * Get Rechnungsstellung eff..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getInvoiceRule_Effective();

    /** Column definition for InvoiceRule_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule_Effective = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "InvoiceRule_Effective", null);
    /** Column name InvoiceRule_Effective */
    public static final String COLUMNNAME_InvoiceRule_Effective = "InvoiceRule_Effective";

	/**
	 * Set Rechnungsstellung abw..
	 * Erlaubt es, eine abweichende Rechnungsstellungsregel vorzugeben.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceRule_Override (java.lang.String InvoiceRule_Override);

	/**
	 * Get Rechnungsstellung abw..
	 * Erlaubt es, eine abweichende Rechnungsstellungsregel vorzugeben.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceRule_Override();

    /** Column definition for InvoiceRule_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "InvoiceRule_Override", null);
    /** Column name InvoiceRule_Override */
    public static final String COLUMNNAME_InvoiceRule_Override = "InvoiceRule_Override";

	/**
	 * Set Status Terminplan.
	 * Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceScheduleAmtStatus (java.lang.String InvoiceScheduleAmtStatus);

	/**
	 * Get Status Terminplan.
	 * Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceScheduleAmtStatus();

    /** Column definition for InvoiceScheduleAmtStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceScheduleAmtStatus = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "InvoiceScheduleAmtStatus", null);
    /** Column name InvoiceScheduleAmtStatus */
    public static final String COLUMNNAME_InvoiceScheduleAmtStatus = "InvoiceScheduleAmtStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Erhält EDI-Belege.
	 * Erhält EDI-Belege
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEdiRecipient (boolean IsEdiRecipient);

	/**
	 * Get Erhält EDI-Belege.
	 * Erhält EDI-Belege
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEdiRecipient();

    /** Column definition for IsEdiRecipient */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsEdiRecipient = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsEdiRecipient", null);
    /** Column name IsEdiRecipient */
    public static final String COLUMNNAME_IsEdiRecipient = "IsEdiRecipient";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set IsFreightCost.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFreightCost (boolean IsFreightCost);

	/**
	 * Get IsFreightCost.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFreightCost();

    /** Column definition for IsFreightCost */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsFreightCost = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsFreightCost", null);
    /** Column name IsFreightCost */
    public static final String COLUMNNAME_IsFreightCost = "IsFreightCost";

	/**
	 * Set Group Compensation Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGroupCompensationLine (boolean IsGroupCompensationLine);

	/**
	 * Get Group Compensation Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGroupCompensationLine();

    /** Column definition for IsGroupCompensationLine */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsGroupCompensationLine = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsGroupCompensationLine", null);
    /** Column name IsGroupCompensationLine */
    public static final String COLUMNNAME_IsGroupCompensationLine = "IsGroupCompensationLine";

	/**
	 * Set Strittig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInDispute (boolean IsInDispute);

	/**
	 * Get Strittig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInDispute();

    /** Column definition for IsInDispute */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInDispute = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsInDispute", null);
    /** Column name IsInDispute */
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing);

	/**
	 * Get Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInOutApprovedForInvoicing();

    /** Column definition for IsInOutApprovedForInvoicing */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInOutApprovedForInvoicing = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsInOutApprovedForInvoicing", null);
    /** Column name IsInOutApprovedForInvoicing */
    public static final String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Ist Material-Vorgang.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsMaterialTracking (boolean IsMaterialTracking);

	/**
	 * Get Ist Material-Vorgang.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isMaterialTracking();

    /** Column definition for IsMaterialTracking */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsMaterialTracking = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsMaterialTracking", null);
    /** Column name IsMaterialTracking */
    public static final String COLUMNNAME_IsMaterialTracking = "IsMaterialTracking";

	/**
	 * Set Verpackungsmaterial.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Verpackungsmaterial.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackagingMaterial();

    /** Column definition for IsPackagingMaterial */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsPackagingMaterial = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsPackagingMaterial", null);
    /** Column name IsPackagingMaterial */
    public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrinted();

    /** Column definition for IsPrinted */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTaxIncluded();

    /** Column definition for IsTaxIncluded */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsTaxIncluded", null);
    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Preis inkl. Steuern abw..
	 * Tax is included in the price
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsTaxIncluded_Override (java.lang.String IsTaxIncluded_Override);

	/**
	 * Get Preis inkl. Steuern abw..
	 * Tax is included in the price
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsTaxIncluded_Override();

    /** Column definition for IsTaxIncluded_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsTaxIncluded_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsTaxIncluded_Override", null);
    /** Column name IsTaxIncluded_Override */
    public static final String COLUMNNAME_IsTaxIncluded_Override = "IsTaxIncluded_Override";

	/**
	 * Set zur Verrechnung.
	 * Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsToClear (boolean IsToClear);

	/**
	 * Get zur Verrechnung.
	 * Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isToClear();

    /** Column definition for IsToClear */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsToClear = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsToClear", null);
    /** Column name IsToClear */
    public static final String COLUMNNAME_IsToClear = "IsToClear";

	/**
	 * Set zu Akt..
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsToRecompute (boolean IsToRecompute);

	/**
	 * Get zu Akt..
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isToRecompute();

    /** Column definition for IsToRecompute */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsToRecompute = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "IsToRecompute", null);
    /** Column name IsToRecompute */
    public static final String COLUMNNAME_IsToRecompute = "IsToRecompute";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilen-Aggregationsmerkmal.
	 * Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineAggregationKey (java.lang.String LineAggregationKey);

	/**
	 * Get Zeilen-Aggregationsmerkmal.
	 * Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineAggregationKey();

    /** Column definition for LineAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKey = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "LineAggregationKey", null);
    /** Column name LineAggregationKey */
    public static final String COLUMNNAME_LineAggregationKey = "LineAggregationKey";

	/**
	 * Set Line aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineAggregationKeyBuilder_ID (int LineAggregationKeyBuilder_ID);

	/**
	 * Get Line aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLineAggregationKeyBuilder_ID();

    /** Column definition for LineAggregationKeyBuilder_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKeyBuilder_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "LineAggregationKeyBuilder_ID", null);
    /** Column name LineAggregationKeyBuilder_ID */
    public static final String COLUMNNAME_LineAggregationKeyBuilder_ID = "LineAggregationKeyBuilder_ID";

	/**
	 * Set Aggregations-Zusatz.
	 * Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineAggregationKey_Suffix (java.lang.String LineAggregationKey_Suffix);

	/**
	 * Get Aggregations-Zusatz.
	 * Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineAggregationKey_Suffix();

    /** Column definition for LineAggregationKey_Suffix */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKey_Suffix = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "LineAggregationKey_Suffix", null);
    /** Column name LineAggregationKey_Suffix */
    public static final String COLUMNNAME_LineAggregationKey_Suffix = "LineAggregationKey_Suffix";

	/**
	 * Set Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_InOut>(I_C_Invoice_Candidate.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_Version_ID();

    /** Column definition for M_PriceList_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_PriceList_Version> COLUMN_M_PriceList_Version_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_PriceList_Version>(I_C_Invoice_Candidate.class, "M_PriceList_Version_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_PricingSystem>(I_C_Invoice_Candidate.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_Product_Category_ID();

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_Product_Category>(I_C_Invoice_Candidate.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_Product>(I_C_Invoice_Candidate.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Abgerechneter Betrag.
	 * Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNetAmtInvoiced (java.math.BigDecimal NetAmtInvoiced);

	/**
	 * Get Abgerechneter Betrag.
	 * Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getNetAmtInvoiced();

    /** Column definition for NetAmtInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_NetAmtInvoiced = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "NetAmtInvoiced", null);
    /** Column name NetAmtInvoiced */
    public static final String COLUMNNAME_NetAmtInvoiced = "NetAmtInvoiced";

	/**
	 * Set Abzurechnender Betrag.
	 * Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNetAmtToInvoice (java.math.BigDecimal NetAmtToInvoice);

	/**
	 * Get Abzurechnender Betrag.
	 * Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getNetAmtToInvoice();

    /** Column definition for NetAmtToInvoice */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_NetAmtToInvoice = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "NetAmtToInvoice", null);
    /** Column name NetAmtToInvoice */
    public static final String COLUMNNAME_NetAmtToInvoice = "NetAmtToInvoice";

	/**
	 * Set Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Vorbelegtes Rechnungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPresetDateInvoiced (java.sql.Timestamp PresetDateInvoiced);

	/**
	 * Get Vorbelegtes Rechnungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPresetDateInvoiced();

    /** Column definition for PresetDateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PresetDateInvoiced = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PresetDateInvoiced", null);
    /** Column name PresetDateInvoiced */
    public static final String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Preis Eff. Netto.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual_Net_Effective (java.math.BigDecimal PriceActual_Net_Effective);

	/**
	 * Get Preis Eff. Netto.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual_Net_Effective();

    /** Column definition for PriceActual_Net_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual_Net_Effective = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PriceActual_Net_Effective", null);
    /** Column name PriceActual_Net_Effective */
    public static final String COLUMNNAME_PriceActual_Net_Effective = "PriceActual_Net_Effective";

	/**
	 * Set Einzelpreis abw..
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual_Override (java.math.BigDecimal PriceActual_Override);

	/**
	 * Get Einzelpreis abw..
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual_Override();

    /** Column definition for PriceActual_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PriceActual_Override", null);
    /** Column name PriceActual_Override */
    public static final String COLUMNNAME_PriceActual_Override = "PriceActual_Override";

	/**
	 * Set Preis.
	 * Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceEntered (java.math.BigDecimal PriceEntered);

	/**
	 * Get Preis.
	 * Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceEntered();

    /** Column definition for PriceEntered */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceEntered = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PriceEntered", null);
    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceEntered_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "PriceEntered_Override", null);
    /** Column name PriceEntered_Override */
    public static final String COLUMNNAME_PriceEntered_Override = "PriceEntered_Override";

	/**
	 * Set Preiseinheit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Preiseinheit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPrice_UOM_ID();

    /** Column definition for Price_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM> COLUMN_Price_UOM_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM>(I_C_Invoice_Candidate.class, "Price_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name Price_UOM_ID */
    public static final String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriority (java.lang.String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeitet (System).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed_Calc (boolean Processed_Calc);

	/**
	 * Get Verarbeitet (System).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed_Calc();

    /** Column definition for Processed_Calc */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed_Calc = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Processed_Calc", null);
    /** Column name Processed_Calc */
    public static final String COLUMNNAME_Processed_Calc = "Processed_Calc";

	/**
	 * Set Verarbeitet abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed_Override (java.lang.String Processed_Override);

	/**
	 * Get Verarbeitet abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProcessed_Override();

    /** Column definition for Processed_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Processed_Override", null);
    /** Column name Processed_Override */
    public static final String COLUMNNAME_Processed_Override = "Processed_Override";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktart.
	 * Art von Produkt
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProductType (java.lang.String ProductType);

	/**
	 * Get Produktart.
	 * Art von Produkt
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getProductType();

    /** Column definition for ProductType */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ProductType = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "ProductType", null);
    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Liefermenge.
	 * Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInUOM (java.math.BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Liefermenge.
	 * Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInUOM();

    /** Column definition for QtyDeliveredInUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyDeliveredInUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyDeliveredInUOM", null);
    /** Column name QtyDeliveredInUOM */
    public static final String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyInvoiced", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyInvoicedInUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyInvoicedInUOM", null);
    /** Column name QtyInvoicedInUOM */
    public static final String COLUMNNAME_QtyInvoicedInUOM = "QtyInvoicedInUOM";

	/**
	 * Set Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder);

	/**
	 * Get QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrderedOverUnder();

    /** Column definition for QtyOrderedOverUnder */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyOrderedOverUnder = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyOrderedOverUnder", null);
    /** Column name QtyOrderedOverUnder */
    public static final String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Abzurechnen eff. (Lagereinheit).
	 * Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoice (java.math.BigDecimal QtyToInvoice);

	/**
	 * Get Abzurechnen eff. (Lagereinheit).
	 * Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoice();

    /** Column definition for QtyToInvoice */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoice", null);
    /** Column name QtyToInvoice */
    public static final String COLUMNNAME_QtyToInvoice = "QtyToInvoice";

	/**
	 * Set Zu berechn. Menge vor Qualitätsabzug.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoiceBeforeDiscount (java.math.BigDecimal QtyToInvoiceBeforeDiscount);

	/**
	 * Get Zu berechn. Menge vor Qualitätsabzug.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoiceBeforeDiscount();

    /** Column definition for QtyToInvoiceBeforeDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceBeforeDiscount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoiceBeforeDiscount", null);
    /** Column name QtyToInvoiceBeforeDiscount */
    public static final String COLUMNNAME_QtyToInvoiceBeforeDiscount = "QtyToInvoiceBeforeDiscount";

	/**
	 * Set Zu berechn. Menge In Preiseinheit.
	 * Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoiceInPriceUOM (java.math.BigDecimal QtyToInvoiceInPriceUOM);

	/**
	 * Get Zu berechn. Menge In Preiseinheit.
	 * Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoiceInPriceUOM();

    /** Column definition for QtyToInvoiceInPriceUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInPriceUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoiceInPriceUOM", null);
    /** Column name QtyToInvoiceInPriceUOM */
    public static final String COLUMNNAME_QtyToInvoiceInPriceUOM = "QtyToInvoiceInPriceUOM";

	/**
	 * Set Abzurechnen eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoiceInUOM (java.math.BigDecimal QtyToInvoiceInUOM);

	/**
	 * Get Abzurechnen eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoiceInUOM();

    /** Column definition for QtyToInvoiceInUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoiceInUOM", null);
    /** Column name QtyToInvoiceInUOM */
    public static final String COLUMNNAME_QtyToInvoiceInUOM = "QtyToInvoiceInUOM";

	/**
	 * Set Abzurechnen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoiceInUOM_Calc (java.math.BigDecimal QtyToInvoiceInUOM_Calc);

	/**
	 * Get Abzurechnen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoiceInUOM_Calc();

    /** Column definition for QtyToInvoiceInUOM_Calc */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInUOM_Calc = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoiceInUOM_Calc", null);
    /** Column name QtyToInvoiceInUOM_Calc */
    public static final String COLUMNNAME_QtyToInvoiceInUOM_Calc = "QtyToInvoiceInUOM_Calc";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoice_Override", null);
    /** Column name QtyToInvoice_Override */
    public static final String COLUMNNAME_QtyToInvoice_Override = "QtyToInvoice_Override";

	/**
	 * Set Zu berechn. Menge abw. erl..
	 * Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToInvoice_OverrideFulfilled (java.math.BigDecimal QtyToInvoice_OverrideFulfilled);

	/**
	 * Get Zu berechn. Menge abw. erl..
	 * Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToInvoice_OverrideFulfilled();

    /** Column definition for QtyToInvoice_OverrideFulfilled */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice_OverrideFulfilled = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyToInvoice_OverrideFulfilled", null);
    /** Column name QtyToInvoice_OverrideFulfilled */
    public static final String COLUMNNAME_QtyToInvoice_OverrideFulfilled = "QtyToInvoice_OverrideFulfilled";

	/**
	 * Set Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyWithIssues (java.math.BigDecimal QtyWithIssues);

	/**
	 * Get Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyWithIssues();

    /** Column definition for QtyWithIssues */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyWithIssues = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyWithIssues", null);
    /** Column name QtyWithIssues */
    public static final String COLUMNNAME_QtyWithIssues = "QtyWithIssues";

	/**
	 * Set Minderwertige Menge eff..
	 * Liefermenge, die nicht fakturiert wird soll. Der Wert weicht von "Minderwertige Menge" ab, wenn ein abweichender "Qualitätsabzug %" Wert gesetzt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyWithIssues_Effective (java.math.BigDecimal QtyWithIssues_Effective);

	/**
	 * Get Minderwertige Menge eff..
	 * Liefermenge, die nicht fakturiert wird soll. Der Wert weicht von "Minderwertige Menge" ab, wenn ein abweichender "Qualitätsabzug %" Wert gesetzt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyWithIssues_Effective();

    /** Column definition for QtyWithIssues_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyWithIssues_Effective = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QtyWithIssues_Effective", null);
    /** Column name QtyWithIssues_Effective */
    public static final String COLUMNNAME_QtyWithIssues_Effective = "QtyWithIssues_Effective";

	/**
	 * Set Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityDiscountPercent();

    /** Column definition for QualityDiscountPercent */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QualityDiscountPercent", null);
    /** Column name QualityDiscountPercent */
    public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualitätsabzug % eff..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQualityDiscountPercent_Effective (java.math.BigDecimal QualityDiscountPercent_Effective);

	/**
	 * Get Qualitätsabzug % eff..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQualityDiscountPercent_Effective();

    /** Column definition for QualityDiscountPercent_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent_Effective = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QualityDiscountPercent_Effective", null);
    /** Column name QualityDiscountPercent_Effective */
    public static final String COLUMNNAME_QualityDiscountPercent_Effective = "QualityDiscountPercent_Effective";

	/**
	 * Set Qualitätsabzug % abw..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityDiscountPercent_Override (java.math.BigDecimal QualityDiscountPercent_Override);

	/**
	 * Get Qualitätsabzug % abw..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityDiscountPercent_Override();

    /** Column definition for QualityDiscountPercent_Override */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent_Override = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QualityDiscountPercent_Override", null);
    /** Column name QualityDiscountPercent_Override */
    public static final String COLUMNNAME_QualityDiscountPercent_Override = "QualityDiscountPercent_Override";

	/**
	 * Set Rechnungspositionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityInvoiceLineGroupType (java.lang.String QualityInvoiceLineGroupType);

	/**
	 * Get Rechnungspositionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQualityInvoiceLineGroupType();

    /** Column definition for QualityInvoiceLineGroupType */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityInvoiceLineGroupType = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "QualityInvoiceLineGroupType", null);
    /** Column name QualityInvoiceLineGroupType */
    public static final String COLUMNNAME_QualityInvoiceLineGroupType = "QualityInvoiceLineGroupType";

	/**
	 * Set ReasonDiscount.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReasonDiscount (java.lang.String ReasonDiscount);

	/**
	 * Get ReasonDiscount.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReasonDiscount();

    /** Column definition for ReasonDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ReasonDiscount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "ReasonDiscount", null);
    /** Column name ReasonDiscount */
    public static final String COLUMNNAME_ReasonDiscount = "ReasonDiscount";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status nach Akt..
	 * Informationen des Aktualisierungsprozesses
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSchedulerResult (java.lang.String SchedulerResult);

	/**
	 * Get Status nach Akt..
	 * Informationen des Aktualisierungsprozesses
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSchedulerResult();

    /** Column definition for SchedulerResult */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_SchedulerResult = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "SchedulerResult", null);
    /** Column name SchedulerResult */
    public static final String COLUMNNAME_SchedulerResult = "SchedulerResult";

	/**
	 * Set Restbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSplitAmt (java.math.BigDecimal SplitAmt);

	/**
	 * Get Restbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getSplitAmt();

    /** Column definition for SplitAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_SplitAmt = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "SplitAmt", null);
    /** Column name SplitAmt */
    public static final String COLUMNNAME_SplitAmt = "SplitAmt";

	/**
	 * Set Lagereinheit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setStockingUOM_ID (int StockingUOM_ID);

	/**
	 * Get Lagereinheit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getStockingUOM_ID();

    /** Column definition for StockingUOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM> COLUMN_StockingUOM_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_UOM>(I_C_Invoice_Candidate.class, "StockingUOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name StockingUOM_ID */
    public static final String COLUMNNAME_StockingUOM_ID = "StockingUOM_ID";

	/**
	 * Set Total des Auftrags.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setTotalOfOrder (java.math.BigDecimal TotalOfOrder);

	/**
	 * Get Total des Auftrags.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getTotalOfOrder();

    /** Column definition for TotalOfOrder */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_TotalOfOrder = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "TotalOfOrder", null);
    /** Column name TotalOfOrder */
    public static final String COLUMNNAME_TotalOfOrder = "TotalOfOrder";

	/**
	 * Set Auftrag Total ohne Rabatt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setTotalOfOrderExcludingDiscount (java.math.BigDecimal TotalOfOrderExcludingDiscount);

	/**
	 * Get Auftrag Total ohne Rabatt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getTotalOfOrderExcludingDiscount();

    /** Column definition for TotalOfOrderExcludingDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_TotalOfOrderExcludingDiscount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "TotalOfOrderExcludingDiscount", null);
    /** Column name TotalOfOrderExcludingDiscount */
    public static final String COLUMNNAME_TotalOfOrderExcludingDiscount = "TotalOfOrderExcludingDiscount";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(I_C_Invoice_Candidate.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
