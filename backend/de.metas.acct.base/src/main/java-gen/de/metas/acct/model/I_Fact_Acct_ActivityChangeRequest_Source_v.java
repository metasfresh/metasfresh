package de.metas.acct.model;


/** Generated Interface for Fact_Acct_ActivityChangeRequest_Source_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fact_Acct_ActivityChangeRequest_Source_v 
{

    /** TableName=Fact_Acct_ActivityChangeRequest_Source_v */
    public static final String Table_Name = "Fact_Acct_ActivityChangeRequest_Source_v";

    /** AD_Table_ID=540702 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Konto.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Konto.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount();

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Client>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Org>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_Table>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr);

	/**
	 * Get Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr();

    /** Column definition for AmtAcctCr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr);

	/**
	 * Get Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr();

    /** Column definition for AmtAcctDr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Ausgangsforderung.
	 * Ausgangsbetrag Forderung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr);

	/**
	 * Get Ausgangsforderung.
	 * Ausgangsbetrag Forderung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceCr();

    /** Column definition for AmtSourceCr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_AmtSourceCr = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AmtSourceCr", null);
    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Ausgangsverbindlichkeit.
	 * Ausgangsbetrag Verbindlichkeit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr);

	/**
	 * Get Ausgangsverbindlichkeit.
	 * Ausgangsbetrag Verbindlichkeit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceDr();

    /** Column definition for AmtSourceDr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_AmtSourceDr = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "AmtSourceDr", null);
    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_AcctSchema>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Activity>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_BPartner>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Currency>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_DocType>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_Period>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_C_UOM>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_User>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

    /** Column definition for DateTrx */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "DateTrx", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Dokument Basis Typ.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Dokument Basis Typ.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFact_Acct_ID();

	public org.compiere.model.I_Fact_Acct getFact_Acct();

	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct);

    /** Column definition for Fact_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_Fact_Acct> COLUMN_Fact_Acct_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_Fact_Acct>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Fact_Acct_ID", org.compiere.model.I_Fact_Acct.class);
    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/**
	 * Set Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMandatoryActivity (boolean IsMandatoryActivity);

	/**
	 * Get Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMandatoryActivity();

    /** Column definition for IsMandatoryActivity */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_IsMandatoryActivity = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "IsMandatoryActivity", null);
    /** Column name IsMandatoryActivity */
    public static final String COLUMNNAME_IsMandatoryActivity = "IsMandatoryActivity";

	/**
	 * Set Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine_ID (int Line_ID);

	/**
	 * Get Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine_ID();

    /** Column definition for Line_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Line_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Line_ID", null);
    /** Column name Line_ID */
    public static final String COLUMNNAME_Line_ID = "Line_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_M_Product>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubLine_ID (int SubLine_ID);

	/**
	 * Get SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSubLine_ID();

    /** Column definition for SubLine_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_SubLine_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "SubLine_ID", null);
    /** Column name SubLine_ID */
    public static final String COLUMNNAME_SubLine_ID = "SubLine_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, Object>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_ActivityChangeRequest_Source_v, org.compiere.model.I_AD_User>(I_Fact_Acct_ActivityChangeRequest_Source_v.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
