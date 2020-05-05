package de.metas.acct.model;


/** Generated Interface for Fact_Acct_Summary
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fact_Acct_Summary 
{

    /** TableName=Fact_Acct_Summary */
    public static final String Table_Name = "Fact_Acct_Summary";

    /** AD_Table_ID=53203 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount();

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_Summary.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Client>(I_Fact_Acct_Summary.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Org>(I_Fact_Acct_Summary.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_Org>(I_Fact_Acct_Summary.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Haben (Year to Date).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr_YTD (java.math.BigDecimal AmtAcctCr_YTD);

	/**
	 * Get Haben (Year to Date).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr_YTD();

    /** Column definition for AmtAcctCr_YTD */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_AmtAcctCr_YTD = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "AmtAcctCr_YTD", null);
    /** Column name AmtAcctCr_YTD */
    public static final String COLUMNNAME_AmtAcctCr_YTD = "AmtAcctCr_YTD";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Soll (Year to Date).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr_YTD (java.math.BigDecimal AmtAcctDr_YTD);

	/**
	 * Get Soll (Year to Date).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr_YTD();

    /** Column definition for AmtAcctDr_YTD */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_AmtAcctDr_YTD = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "AmtAcctDr_YTD", null);
    /** Column name AmtAcctDr_YTD */
    public static final String COLUMNNAME_AmtAcctDr_YTD = "AmtAcctDr_YTD";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_AcctSchema>(I_Fact_Acct_Summary.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Activity>(I_Fact_Acct_Summary.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_BPartner>(I_Fact_Acct_Summary.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Campaign>(I_Fact_Acct_Summary.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocFrom_ID();

	public org.compiere.model.I_C_Location getC_LocFrom();

	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom);

    /** Column definition for C_LocFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Location>(I_Fact_Acct_Summary.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocFrom_ID */
    public static final String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocTo_ID();

	public org.compiere.model.I_C_Location getC_LocTo();

	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo);

    /** Column definition for C_LocTo_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Location>(I_Fact_Acct_Summary.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocTo_ID */
    public static final String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Period>(I_Fact_Acct_Summary.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Project>(I_Fact_Acct_Summary.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectPhase_ID();

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	public void setC_ProjectPhase(org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

    /** Column definition for C_ProjectPhase_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ProjectPhase>(I_Fact_Acct_Summary.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
    /** Column name C_ProjectPhase_ID */
    public static final String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectTask_ID();

	public org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	public void setC_ProjectTask(org.compiere.model.I_C_ProjectTask C_ProjectTask);

    /** Column definition for C_ProjectTask_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ProjectTask>(I_Fact_Acct_Summary.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
    /** Column name C_ProjectTask_ID */
    public static final String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion);

    /** Column definition for C_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_SalesRegion>(I_Fact_Acct_Summary.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SubAcct_ID (int C_SubAcct_ID);

	/**
	 * Get Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SubAcct_ID();

	public org.compiere.model.I_C_SubAcct getC_SubAcct();

	public void setC_SubAcct(org.compiere.model.I_C_SubAcct C_SubAcct);

    /** Column definition for C_SubAcct_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_SubAcct> COLUMN_C_SubAcct_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_SubAcct>(I_Fact_Acct_Summary.class, "C_SubAcct_ID", org.compiere.model.I_C_SubAcct.class);
    /** Column name C_SubAcct_ID */
    public static final String COLUMNNAME_C_SubAcct_ID = "C_SubAcct_ID";

	/**
	 * Set Jahr.
	 * Kalenderjahr
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Year_ID (int C_Year_ID);

	/**
	 * Get Jahr.
	 * Kalenderjahr
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year();

	public void setC_Year(org.compiere.model.I_C_Year C_Year);

    /** Column definition for C_Year_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Year> COLUMN_C_Year_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_Year>(I_Fact_Acct_Summary.class, "C_Year_ID", org.compiere.model.I_C_Year.class);
    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_User>(I_Fact_Acct_Summary.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGL_Budget_ID (int GL_Budget_ID);

	/**
	 * Get Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGL_Budget_ID();

	public org.compiere.model.I_GL_Budget getGL_Budget();

	public void setGL_Budget(org.compiere.model.I_GL_Budget GL_Budget);

    /** Column definition for GL_Budget_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_GL_Budget> COLUMN_GL_Budget_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_GL_Budget>(I_Fact_Acct_Summary.class, "GL_Budget_ID", org.compiere.model.I_GL_Budget.class);
    /** Column name GL_Budget_ID */
    public static final String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_M_Product>(I_Fact_Acct_Summary.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Report Cube.
	 * Define reporting cube for pre-calculation of summary accounting data.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPA_ReportCube_ID (int PA_ReportCube_ID);

	/**
	 * Get Report Cube.
	 * Define reporting cube for pre-calculation of summary accounting data.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPA_ReportCube_ID();

	public org.compiere.model.I_PA_ReportCube getPA_ReportCube();

	public void setPA_ReportCube(org.compiere.model.I_PA_ReportCube PA_ReportCube);

    /** Column definition for PA_ReportCube_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_PA_ReportCube> COLUMN_PA_ReportCube_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_PA_ReportCube>(I_Fact_Acct_Summary.class, "PA_ReportCube_ID", org.compiere.model.I_PA_ReportCube.class);
    /** Column name PA_ReportCube_ID */
    public static final String COLUMNNAME_PA_ReportCube_ID = "PA_ReportCube_ID";

	/**
	 * Set PostingType.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get PostingType.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_AD_User>(I_Fact_Acct_Summary.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_Summary.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User List 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get User List 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, org.compiere.model.I_C_ElementValue>(I_Fact_Acct_Summary.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserElement1_ID (int UserElement1_ID);

	/**
	 * Get User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUserElement1_ID();

    /** Column definition for UserElement1_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_UserElement1_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "UserElement1_ID", null);
    /** Column name UserElement1_ID */
    public static final String COLUMNNAME_UserElement1_ID = "UserElement1_ID";

	/**
	 * Set User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserElement2_ID (int UserElement2_ID);

	/**
	 * Get User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUserElement2_ID();

    /** Column definition for UserElement2_ID */
    public static final org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object> COLUMN_UserElement2_ID = new org.adempiere.model.ModelColumn<I_Fact_Acct_Summary, Object>(I_Fact_Acct_Summary.class, "UserElement2_ID", null);
    /** Column name UserElement2_ID */
    public static final String COLUMNNAME_UserElement2_ID = "UserElement2_ID";
}
