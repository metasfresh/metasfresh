package de.metas.acct.model;


/** Generated Interface for Fact_Acct_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fact_Acct_Log 
{

    /** TableName=Fact_Acct_Log */
    public static final String Table_Name = "Fact_Acct_Log";

    /** AD_Table_ID=540695 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_Client>(I_Fact_Acct_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_Org>(I_Fact_Acct_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr);

	/**
	 * Get Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr();

    /** Column definition for AmtAcctCr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr);

	/**
	 * Get Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr();

    /** Column definition for AmtAcctDr */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_AcctSchema>(I_Fact_Acct_Log.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kontenart.
	 * Kontenart
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/**
	 * Get Kontenart.
	 * Kontenart
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ElementValue_ID();

	public org.compiere.model.I_C_ElementValue getC_ElementValue();

	public void setC_ElementValue(org.compiere.model.I_C_ElementValue C_ElementValue);

    /** Column definition for C_ElementValue_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_ElementValue> COLUMN_C_ElementValue_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_Log.class, "C_ElementValue_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/**
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_C_Period>(I_Fact_Acct_Log.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_User>(I_Fact_Acct_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFact_Acct_ID();

	public org.compiere.model.I_Fact_Acct getFact_Acct();

	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct);

    /** Column definition for Fact_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_Fact_Acct> COLUMN_Fact_Acct_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_Fact_Acct>(I_Fact_Acct_Log.class, "Fact_Acct_ID", org.compiere.model.I_Fact_Acct.class);
    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * Die Art des gebuchten Betrages dieser Transaktion
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessingTag (java.lang.String ProcessingTag);

	/**
	 * Get Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProcessingTag();

    /** Column definition for ProcessingTag */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_ProcessingTag = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "ProcessingTag", null);
    /** Column name ProcessingTag */
    public static final String COLUMNNAME_ProcessingTag = "ProcessingTag";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, Object>(I_Fact_Acct_Log.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_Log, org.compiere.model.I_AD_User>(I_Fact_Acct_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
