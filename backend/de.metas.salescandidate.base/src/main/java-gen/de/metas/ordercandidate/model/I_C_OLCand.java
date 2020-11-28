package de.metas.ordercandidate.model;


/** Generated Interface for C_OLCand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OLCand 
{

    /** TableName=C_OLCand */
    public static final String Table_Name = "C_OLCand";

    /** AD_Table_ID=540244 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Verarbeitungsziel.
	 * Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_DataDestination_ID (int AD_DataDestination_ID);

	/**
	 * Get Verarbeitungsziel.
	 * Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_DataDestination_ID();

    /** Column definition for AD_DataDestination_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_AD_DataDestination_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "AD_DataDestination_ID", null);
    /** Column name AD_DataDestination_ID */
    public static final String COLUMNNAME_AD_DataDestination_ID = "AD_DataDestination_ID";

	/**
	 * Set Eingabequelle.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Eingabequelle.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_InputDataSource_ID();

    /** Column definition for AD_InputDataSource_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_AD_InputDataSource_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "AD_InputDataSource_ID", null);
    /** Column name AD_InputDataSource_ID */
    public static final String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

	/**
	 * Set Statusmeldung.
	 * System-Nachricht
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Note_ID (int AD_Note_ID);

	/**
	 * Get Statusmeldung.
	 * System-Nachricht
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Note_ID();

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Erfasst durch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_EnteredBy_ID (int AD_User_EnteredBy_ID);

	/**
	 * Get Erfasst durch.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_EnteredBy_ID();

    /** Column name AD_User_EnteredBy_ID */
    public static final String COLUMNNAME_AD_User_EnteredBy_ID = "AD_User_EnteredBy_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Rechnungsstandort.
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Rechnungsstandort.
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_ID();

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

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

    /** Column name Bill_User_ID */
    public static final String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Geschäftspartner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_BPartner_Effective_ID (int C_BPartner_Effective_ID);

	/**
	 * Get Geschäftspartner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_BPartner_Effective_ID();

    /** Column name C_BPartner_Effective_ID */
    public static final String COLUMNNAME_C_BPartner_Effective_ID = "C_BPartner_Effective_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Override_ID();

    /** Column name C_BPartner_Override_ID */
    public static final String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_SalesRep_ID();

    /** Column name C_BPartner_SalesRep_ID */
    public static final String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Standort eff..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_BP_Location_Effective_ID (int C_BP_Location_Effective_ID);

	/**
	 * Get Standort eff..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_BP_Location_Effective_ID();

    /** Column name C_BP_Location_Effective_ID */
    public static final String COLUMNNAME_C_BP_Location_Effective_ID = "C_BP_Location_Effective_ID";

	/**
	 * Set Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Location_Override_ID();

    /** Column name C_BP_Location_Override_ID */
    public static final String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Kosten.
	 * Zusätzliche Kosten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Zusätzliche Kosten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

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

    /** Column name C_DocTypeInvoice_ID */
    public static final String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Set Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeOrder_ID (int C_DocTypeOrder_ID);

	/**
	 * Get Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeOrder_ID();

    /** Column name C_DocTypeOrder_ID */
    public static final String COLUMNNAME_C_DocTypeOrder_ID = "C_DocTypeOrder_ID";

	/**
	 * Set Vertragsbedingungen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Vertragsbedingungen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Conditions_ID();

    /** Column definition for C_Flatrate_Conditions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "C_Flatrate_Conditions_ID", null);
    /** Column name C_Flatrate_Conditions_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Auftragskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OLCand_ID (int C_OLCand_ID);

	/**
	 * Get Auftragskandidat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OLCand_ID();

    /** Column definition for C_OLCand_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_C_OLCand_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "C_OLCand_ID", null);
    /** Column name C_OLCand_ID */
    public static final String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";
    
    /**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";
    
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_ID();

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Maßeinheit int..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Internal_ID (int C_UOM_Internal_ID);

	/**
	 * Get Maßeinheit int..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Internal_ID();

    /** Column name C_UOM_Internal_ID */
    public static final String COLUMNNAME_C_UOM_Internal_ID = "C_UOM_Internal_ID";

	/**
	 * Set Kand.-Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateCandidate (java.sql.Timestamp DateCandidate);

	/**
	 * Get Kand.-Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateCandidate();

    /** Column definition for DateCandidate */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DateCandidate = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DateCandidate", null);
    /** Column name DateCandidate */
    public static final String COLUMNNAME_DateCandidate = "DateCandidate";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Zugesagter Termin eff..
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDatePromised_Effective (java.sql.Timestamp DatePromised_Effective);

	/**
	 * Get Zugesagter Termin eff..
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getDatePromised_Effective();

    /** Column definition for DatePromised_Effective */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised_Effective = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DatePromised_Effective", null);
    /** Column name DatePromised_Effective */
    public static final String COLUMNNAME_DatePromised_Effective = "DatePromised_Effective";

	/**
	 * Set Zugesagter Termin abw..
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised_Override (java.sql.Timestamp DatePromised_Override);

	/**
	 * Get Zugesagter Termin abw..
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised_Override();

    /** Column definition for DatePromised_Override */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised_Override = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DatePromised_Override", null);
    /** Column name DatePromised_Override */
    public static final String COLUMNNAME_DatePromised_Override = "DatePromised_Override";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DescriptionBottom = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DescriptionBottom", null);
    /** Column name DescriptionBottom */
    public static final String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Description Header.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescriptionHeader (java.lang.String DescriptionHeader);

	/**
	 * Get Description Header.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescriptionHeader();

    /** Column definition for DescriptionHeader */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_DescriptionHeader = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "DescriptionHeader", null);
    /** Column name DescriptionHeader */
    public static final String COLUMNNAME_DescriptionHeader = "DescriptionHeader";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Lieferempfänger eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDropShip_BPartner_Effective_ID (int DropShip_BPartner_Effective_ID);

	/**
	 * Get Lieferempfänger eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getDropShip_BPartner_Effective_ID();

    /** Column name DropShip_BPartner_Effective_ID */
    public static final String COLUMNNAME_DropShip_BPartner_Effective_ID = "DropShip_BPartner_Effective_ID";

	/**
	 * Set Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_BPartner_ID();

    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Lieferempfänger abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_Override_ID (int DropShip_BPartner_Override_ID);

	/**
	 * Get Lieferempfänger abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_BPartner_Override_ID();

    /** Column name DropShip_BPartner_Override_ID */
    public static final String COLUMNNAME_DropShip_BPartner_Override_ID = "DropShip_BPartner_Override_ID";

	/**
	 * Set Lieferadresse eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDropShip_Location_Effective_ID (int DropShip_Location_Effective_ID);

	/**
	 * Get Lieferadresse eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getDropShip_Location_Effective_ID();

    /** Column name DropShip_Location_Effective_ID */
    public static final String COLUMNNAME_DropShip_Location_Effective_ID = "DropShip_Location_Effective_ID";

	/**
	 * Set Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_Location_ID();

    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Lieferadresse abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_Location_Override_ID (int DropShip_Location_Override_ID);

	/**
	 * Get Lieferadresse abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_Location_Override_ID();

    /** Column name DropShip_Location_Override_ID */
    public static final String COLUMNNAME_DropShip_Location_Override_ID = "DropShip_Location_Override_ID";

	/**
	 * Set Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_User_ID();

    /** Column name DropShip_User_ID */
    public static final String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Externe Datensatz-Kopf-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalHeaderId (java.lang.String ExternalHeaderId);

	/**
	 * Get Externe Datensatz-Kopf-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalHeaderId();

    /** Column definition for ExternalHeaderId */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_ExternalHeaderId = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "ExternalHeaderId", null);
    /** Column name ExternalHeaderId */
    public static final String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set Externe Datensatz-Zeilen-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalLineId (java.lang.String ExternalLineId);

	/**
	 * Get Externe Datensatz-Zeilen-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalLineId();

    /** Column definition for ExternalLineId */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_ExternalLineId = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "ExternalLineId", null);
    /** Column name ExternalLineId */
    public static final String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set Übergabeadresse eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setHandOver_Location_Effective_ID (int HandOver_Location_Effective_ID);

	/**
	 * Get Übergabeadresse eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getHandOver_Location_Effective_ID();

    /** Column name HandOver_Location_Effective_ID */
    public static final String COLUMNNAME_HandOver_Location_Effective_ID = "HandOver_Location_Effective_ID";

	/**
	 * Set Übergabeadresse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Location_ID (int HandOver_Location_ID);

	/**
	 * Get Übergabeadresse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Location_ID();

    /** Column name HandOver_Location_ID */
    public static final String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";

	/**
	 * Set Übergabeadresse abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Location_Override_ID (int HandOver_Location_Override_ID);

	/**
	 * Get Übergabeadresse abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Location_Override_ID();

    /** Column name HandOver_Location_Override_ID */
    public static final String COLUMNNAME_HandOver_Location_Override_ID = "HandOver_Location_Override_ID";

	/**
	 * Set Übergabe an eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setHandOver_Partner_Effective_ID (int HandOver_Partner_Effective_ID);

	/**
	 * Get Übergabe an eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getHandOver_Partner_Effective_ID();

    /** Column name HandOver_Partner_Effective_ID */
    public static final String COLUMNNAME_HandOver_Partner_Effective_ID = "HandOver_Partner_Effective_ID";

	/**
	 * Set Übergabe an.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Partner_ID (int HandOver_Partner_ID);

	/**
	 * Get Übergabe an.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Partner_ID();

    /** Column name HandOver_Partner_ID */
    public static final String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";

	/**
	 * Set Übergabe an abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_Partner_Override_ID (int HandOver_Partner_Override_ID);

	/**
	 * Get Übergabe an abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_Partner_Override_ID();

    /** Column name HandOver_Partner_Override_ID */
    public static final String COLUMNNAME_HandOver_Partner_Override_ID = "HandOver_Partner_Override_ID";

	/**
	 * Set Übergabe-Kontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHandOver_User_ID (int HandOver_User_ID);

	/**
	 * Get Übergabe-Kontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHandOver_User_ID();

    /** Column name HandOver_User_ID */
    public static final String COLUMNNAME_HandOver_User_ID = "HandOver_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_InvoicableQtyBasedOn = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "InvoicableQtyBasedOn", null);
    /** Column name InvoicableQtyBasedOn */
    public static final String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Explicit Product Price Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExplicitProductPriceAttribute (boolean IsExplicitProductPriceAttribute);

	/**
	 * Get Explicit Product Price Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExplicitProductPriceAttribute();

    /** Column definition for IsExplicitProductPriceAttribute */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_IsExplicitProductPriceAttribute = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "IsExplicitProductPriceAttribute", null);
    /** Column name IsExplicitProductPriceAttribute */
    public static final String COLUMNNAME_IsExplicitProductPriceAttribute = "IsExplicitProductPriceAttribute";

	/**
	 * Set Manueller Rabatt.
	 * Ein Rabatt, der von Hand eingetragen wurde, wird vom Provisionssystem nicht überschrieben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManualDiscount (boolean IsManualDiscount);

	/**
	 * Get Manueller Rabatt.
	 * Ein Rabatt, der von Hand eingetragen wurde, wird vom Provisionssystem nicht überschrieben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManualDiscount();

    /** Column definition for IsManualDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_IsManualDiscount = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "IsManualDiscount", null);
    /** Column name IsManualDiscount */
    public static final String COLUMNNAME_IsManualDiscount = "IsManualDiscount";

	/**
	 * Set Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManualPrice();

    /** Column definition for IsManualPrice */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_IsManualPrice = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "IsManualPrice", null);
    /** Column name IsManualPrice */
    public static final String COLUMNNAME_IsManualPrice = "IsManualPrice";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSet_ID();

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

    /** Column definition for M_AttributeSet_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSet>(I_C_OLCand.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSetInstance>(I_C_OLCand.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_HU_PI_Item_Product_Effective_ID (int M_HU_PI_Item_Product_Effective_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_HU_PI_Item_Product_Effective_ID();

    /** Column definition for M_HU_PI_Item_Product_Effective_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_Effective_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "M_HU_PI_Item_Product_Effective_ID", null);
    /** Column name M_HU_PI_Item_Product_Effective_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_Effective_ID = "M_HU_PI_Item_Product_Effective_ID";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "M_HU_PI_Item_Product_ID", null);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_Override_ID (int M_HU_PI_Item_Product_Override_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_Override_ID();

    /** Column definition for M_HU_PI_Item_Product_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_Override_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "M_HU_PI_Item_Product_Override_ID", null);
    /** Column name M_HU_PI_Item_Product_Override_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";

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

    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Produkt eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_Product_Effective_ID (int M_Product_Effective_ID);

	/**
	 * Get Produkt eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_Product_Effective_ID();

    /** Column name M_Product_Effective_ID */
    public static final String COLUMNNAME_M_Product_Effective_ID = "M_Product_Effective_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produkt abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Override_ID (int M_Product_Override_ID);

	/**
	 * Get Produkt abw..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Override_ID();

    /** Column name M_Product_Override_ID */
    public static final String COLUMNNAME_M_Product_Override_ID = "M_Product_Override_ID";

	/**
	 * Set Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID);

	/**
	 * Get Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_Attribute_ID();

    /** Column definition for M_ProductPrice_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_M_ProductPrice_Attribute_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "M_ProductPrice_Attribute_ID", null);
    /** Column name M_ProductPrice_Attribute_ID */
    public static final String COLUMNNAME_M_ProductPrice_Attribute_ID = "M_ProductPrice_Attribute_ID";

	/**
	 * Set Produkt-Preis.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_ID (int M_ProductPrice_ID);

	/**
	 * Get Produkt-Preis.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_ID();

	public org.compiere.model.I_M_ProductPrice getM_ProductPrice();

	public void setM_ProductPrice(org.compiere.model.I_M_ProductPrice M_ProductPrice);

    /** Column definition for M_ProductPrice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_ProductPrice> COLUMN_M_ProductPrice_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_ProductPrice>(I_C_OLCand.class, "M_ProductPrice_ID", org.compiere.model.I_M_ProductPrice.class);
    /** Column name M_ProductPrice_ID */
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, org.compiere.model.I_M_Shipper>(I_C_OLCand.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Dest_ID();

    /** Column name M_Warehouse_Dest_ID */
    public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "POReference", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PresetDateInvoiced = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PresetDateInvoiced", null);
    /** Column name PresetDateInvoiced */
    public static final String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Vorbelegtes Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPresetDateShipped (java.sql.Timestamp PresetDateShipped);

	/**
	 * Get Vorbelegtes Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPresetDateShipped();

    /** Column definition for PresetDateShipped */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PresetDateShipped = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PresetDateShipped", null);
    /** Column name PresetDateShipped */
    public static final String COLUMNNAME_PresetDateShipped = "PresetDateShipped";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Preisdifferenz (imp. - int.).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPriceDifference (java.math.BigDecimal PriceDifference);

	/**
	 * Get Preisdifferenz (imp. - int.).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getPriceDifference();

    /** Column definition for PriceDifference */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PriceDifference = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PriceDifference", null);
    /** Column name PriceDifference */
    public static final String COLUMNNAME_PriceDifference = "PriceDifference";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PriceEntered = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PriceEntered", null);
    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Preis int..
	 * Interner Preis laut Stammdaten
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceInternal (java.math.BigDecimal PriceInternal);

	/**
	 * Get Preis int..
	 * Interner Preis laut Stammdaten
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceInternal();

    /** Column definition for PriceInternal */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_PriceInternal = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "PriceInternal", null);
    /** Column name PriceInternal */
    public static final String COLUMNNAME_PriceInternal = "PriceInternal";

	/**
	 * Set Preiseinheit int..
	 * Interne Preiseinheit laut Stammdaten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice_UOM_Internal_ID (int Price_UOM_Internal_ID);

	/**
	 * Get Preiseinheit int..
	 * Interne Preiseinheit laut Stammdaten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPrice_UOM_Internal_ID();

    /** Column name Price_UOM_Internal_ID */
    public static final String COLUMNNAME_Price_UOM_Internal_ID = "Price_UOM_Internal_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Verpackungskapazität.
	 * Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyItemCapacity (java.math.BigDecimal QtyItemCapacity);

	/**
	 * Get Verpackungskapazität.
	 * Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyItemCapacity();

    /** Column definition for QtyItemCapacity */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_QtyItemCapacity = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "QtyItemCapacity", null);
    /** Column name QtyItemCapacity */
    public static final String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCand, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OLCand, Object>(I_C_OLCand.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
