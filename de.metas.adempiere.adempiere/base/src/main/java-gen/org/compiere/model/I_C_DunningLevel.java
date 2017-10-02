package org.compiere.model;


/** Generated Interface for C_DunningLevel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningLevel 
{

    /** TableName=C_DunningLevel */
    public static final String Table_Name = "C_DunningLevel";

    /** AD_Table_ID=331 */
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_Client>(I_C_DunningLevel.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_Org>(I_C_DunningLevel.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning();

	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning);

    /** Column definition for C_Dunning_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_C_Dunning>(I_C_DunningLevel.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningLevel_ID();

    /** Column definition for C_DunningLevel_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_C_DunningLevel_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "C_DunningLevel_ID", null);
    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

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

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_C_PaymentTerm>(I_C_DunningLevel.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Mahngebühr.
	 * Indicates if fees will be charged for overdue invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setChargeFee (boolean ChargeFee);

	/**
	 * Get Mahngebühr.
	 * Indicates if fees will be charged for overdue invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isChargeFee();

    /** Column definition for ChargeFee */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_ChargeFee = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "ChargeFee", null);
    /** Column name ChargeFee */
    public static final String COLUMNNAME_ChargeFee = "ChargeFee";

	/**
	 * Set Mahnzinsen.
	 * Indicates if interest will be charged on overdue invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setChargeInterest (boolean ChargeInterest);

	/**
	 * Get Mahnzinsen.
	 * Indicates if interest will be charged on overdue invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isChargeInterest();

    /** Column definition for ChargeInterest */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_ChargeInterest = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "ChargeInterest", null);
    /** Column name ChargeInterest */
    public static final String COLUMNNAME_ChargeInterest = "ChargeInterest";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_User>(I_C_DunningLevel.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Days after due date.
	 * Days after due date to dun (if negative days until due)
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDaysAfterDue (java.math.BigDecimal DaysAfterDue);

	/**
	 * Get Days after due date.
	 * Days after due date to dun (if negative days until due)
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDaysAfterDue();

    /** Column definition for DaysAfterDue */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_DaysAfterDue = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "DaysAfterDue", null);
    /** Column name DaysAfterDue */
    public static final String COLUMNNAME_DaysAfterDue = "DaysAfterDue";

	/**
	 * Set Tage zwischen Mahnungen.
	 * Days between sending dunning notices
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDaysBetweenDunning (int DaysBetweenDunning);

	/**
	 * Get Tage zwischen Mahnungen.
	 * Days between sending dunning notices
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDaysBetweenDunning();

    /** Column definition for DaysBetweenDunning */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_DaysBetweenDunning = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "DaysBetweenDunning", null);
    /** Column name DaysBetweenDunning */
    public static final String COLUMNNAME_DaysBetweenDunning = "DaysBetweenDunning";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Dunning Print Format.
	 * Print Format for printing Dunning Letters
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunning_PrintFormat_ID (int Dunning_PrintFormat_ID);

	/**
	 * Get Dunning Print Format.
	 * Print Format for printing Dunning Letters
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDunning_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getDunning_PrintFormat();

	public void setDunning_PrintFormat(org.compiere.model.I_AD_PrintFormat Dunning_PrintFormat);

    /** Column definition for Dunning_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_PrintFormat> COLUMN_Dunning_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_PrintFormat>(I_C_DunningLevel.class, "Dunning_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name Dunning_PrintFormat_ID */
    public static final String COLUMNNAME_Dunning_PrintFormat_ID = "Dunning_PrintFormat_ID";

	/**
	 * Set Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFeeAmt (java.math.BigDecimal FeeAmt);

	/**
	 * Get Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFeeAmt();

    /** Column definition for FeeAmt */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_FeeAmt = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "FeeAmt", null);
    /** Column name FeeAmt */
    public static final String COLUMNNAME_FeeAmt = "FeeAmt";

	/**
	 * Set Interest in percent.
	 * Percentage interest to charge on overdue invoices
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInterestPercent (java.math.BigDecimal InterestPercent);

	/**
	 * Get Interest in percent.
	 * Percentage interest to charge on overdue invoices
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getInterestPercent();

    /** Column definition for InterestPercent */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_InterestPercent = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "InterestPercent", null);
    /** Column name InterestPercent */
    public static final String COLUMNNAME_InterestPercent = "InterestPercent";

	/**
	 * Set Collection Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceCollectionType (java.lang.String InvoiceCollectionType);

	/**
	 * Get Collection Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceCollectionType();

    /** Column definition for InvoiceCollectionType */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_InvoiceCollectionType = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "InvoiceCollectionType", null);
    /** Column name InvoiceCollectionType */
    public static final String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDeliveryStop (boolean IsDeliveryStop);

	/**
	 * Get Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDeliveryStop();

    /** Column definition for IsDeliveryStop */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsDeliveryStop = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsDeliveryStop", null);
    /** Column name IsDeliveryStop */
    public static final String COLUMNNAME_IsDeliveryStop = "IsDeliveryStop";

	/**
	 * Set Kredit Stop.
	 * Set the business partner to credit stop
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSetCreditStop (boolean IsSetCreditStop);

	/**
	 * Get Kredit Stop.
	 * Set the business partner to credit stop
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSetCreditStop();

    /** Column definition for IsSetCreditStop */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsSetCreditStop = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsSetCreditStop", null);
    /** Column name IsSetCreditStop */
    public static final String COLUMNNAME_IsSetCreditStop = "IsSetCreditStop";

	/**
	 * Set Set Payment Term.
	 * Set the payment term of the Business Partner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSetPaymentTerm (boolean IsSetPaymentTerm);

	/**
	 * Get Set Payment Term.
	 * Set the payment term of the Business Partner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSetPaymentTerm();

    /** Column definition for IsSetPaymentTerm */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsSetPaymentTerm = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsSetPaymentTerm", null);
    /** Column name IsSetPaymentTerm */
    public static final String COLUMNNAME_IsSetPaymentTerm = "IsSetPaymentTerm";

	/**
	 * Set Show All Due.
	 * Show/print all due invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShowAllDue (boolean IsShowAllDue);

	/**
	 * Get Show All Due.
	 * Show/print all due invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShowAllDue();

    /** Column definition for IsShowAllDue */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsShowAllDue = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsShowAllDue", null);
    /** Column name IsShowAllDue */
    public static final String COLUMNNAME_IsShowAllDue = "IsShowAllDue";

	/**
	 * Set Show Not Due.
	 * Show/print all invoices which are not due (yet).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShowNotDue (boolean IsShowNotDue);

	/**
	 * Get Show Not Due.
	 * Show/print all invoices which are not due (yet).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShowNotDue();

    /** Column definition for IsShowNotDue */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsShowNotDue = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsShowNotDue", null);
    /** Column name IsShowNotDue */
    public static final String COLUMNNAME_IsShowNotDue = "IsShowNotDue";

	/**
	 * Set Is Statement.
	 * Dunning Level is a definition of a statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStatement (boolean IsStatement);

	/**
	 * Get Is Statement.
	 * Dunning Level is a definition of a statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStatement();

    /** Column definition for IsStatement */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsStatement = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsStatement", null);
    /** Column name IsStatement */
    public static final String COLUMNNAME_IsStatement = "IsStatement";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWriteOff();

    /** Column definition for IsWriteOff */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_IsWriteOff = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "IsWriteOff", null);
    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Notiz.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/**
	 * Set Notiz Header.
	 * Optional weitere Information für ein Dokument
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNoteHeader (java.lang.String NoteHeader);

	/**
	 * Get Notiz Header.
	 * Optional weitere Information für ein Dokument
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNoteHeader();

    /** Column definition for NoteHeader */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_NoteHeader = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "NoteHeader", null);
    /** Column name NoteHeader */
    public static final String COLUMNNAME_NoteHeader = "NoteHeader";

	/**
	 * Set Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrintName (java.lang.String PrintName);

	/**
	 * Get Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintName();

    /** Column definition for PrintName */
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_PrintName = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "PrintName", null);
    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DunningLevel, Object>(I_C_DunningLevel.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DunningLevel, org.compiere.model.I_AD_User>(I_C_DunningLevel.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
