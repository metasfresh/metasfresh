package org.compiere.model;


/** Generated Interface for C_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order 
{

    /** TableName=C_Order */
    public static final String Table_Name = "C_Order";

    /** AD_Table_ID=259 */
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

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmountRefunded (java.math.BigDecimal AmountRefunded);

	/**
	 * Get AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmountRefunded();

    /** Column definition for AmountRefunded */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_AmountRefunded = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "AmountRefunded", null);
    /** Column name AmountRefunded */
    public static final String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/**
	 * Set AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmountTendered (java.math.BigDecimal AmountTendered);

	/**
	 * Get AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmountTendered();

    /** Column definition for AmountTendered */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_AmountTendered = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "AmountTendered", null);
    /** Column name AmountTendered */
    public static final String COLUMNNAME_AmountTendered = "AmountTendered";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Rechnungspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setBill_BPartner_Memo (java.lang.String Bill_BPartner_Memo);

	/**
	 * Get Rechnungspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getBill_BPartner_Memo();

    /** Column definition for Bill_BPartner_Memo */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Bill_BPartner_Memo = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Bill_BPartner_Memo", null);
    /** Column name Bill_BPartner_Memo */
    public static final String COLUMNNAME_Bill_BPartner_Memo = "Bill_BPartner_Memo";

	/**
	 * Set Rechnungsstandort.
	 * Business Partner Location for invoicing
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Rechnungsstandort.
	 * Business Partner Location for invoicing
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_ID();

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBillToAddress (java.lang.String BillToAddress);

	/**
	 * Get Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBillToAddress();

    /** Column definition for BillToAddress */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_BillToAddress = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "BillToAddress", null);
    /** Column name BillToAddress */
    public static final String COLUMNNAME_BillToAddress = "BillToAddress";

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
	 * Set Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

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

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_BPartner_Memo (java.lang.String C_BPartner_Memo);

	/**
	 * Get Geschäftspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getC_BPartner_Memo();

    /** Column definition for C_BPartner_Memo */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_C_BPartner_Memo = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "C_BPartner_Memo", null);
    /** Column name C_BPartner_Memo */
    public static final String COLUMNNAME_C_BPartner_Memo = "C_BPartner_Memo";

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
	 * Set Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BP_BankAccount>(I_C_Order.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Campaign>(I_C_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CashLine_ID (int C_CashLine_ID);

	/**
	 * Get Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CashLine_ID();

	public org.compiere.model.I_C_CashLine getC_CashLine();

	public void setC_CashLine(org.compiere.model.I_C_CashLine C_CashLine);

    /** Column definition for C_CashLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_CashLine> COLUMN_C_CashLine_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_CashLine>(I_C_Order.class, "C_CashLine_ID", org.compiere.model.I_C_CashLine.class);
    /** Column name C_CashLine_ID */
    public static final String COLUMNNAME_C_CashLine_ID = "C_CashLine_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeTarget_ID();

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCompleteOrderDiscount (java.math.BigDecimal CompleteOrderDiscount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCompleteOrderDiscount();

    /** Column definition for CompleteOrderDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CompleteOrderDiscount = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "CompleteOrderDiscount", null);
    /** Column name CompleteOrderDiscount */
    public static final String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopyFrom (java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCopyFrom();

    /** Column definition for CopyFrom */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CopyFrom = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "CopyFrom", null);
    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "C_Order_ID", null);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Payment>(I_C_Order.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_POS_ID();

	public org.compiere.model.I_C_POS getC_POS();

	public void setC_POS(org.compiere.model.I_C_POS C_POS);

    /** Column definition for C_POS_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_POS> COLUMN_C_POS_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_POS>(I_C_Order.class, "C_POS_ID", org.compiere.model.I_C_POS.class);
    /** Column name C_POS_ID */
    public static final String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateCopy (java.lang.String CreateCopy);

	/**
	 * Get Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateCopy();

    /** Column definition for CreateCopy */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CreateCopy = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "CreateCopy", null);
    /** Column name CreateCopy */
    public static final String COLUMNNAME_CreateCopy = "CreateCopy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreateNewFromProposal (java.lang.String CreateNewFromProposal);

	/**
	 * Get Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreateNewFromProposal();

    /** Column definition for CreateNewFromProposal */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CreateNewFromProposal = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "CreateNewFromProposal", null);
    /** Column name CreateNewFromProposal */
    public static final String COLUMNNAME_CreateNewFromProposal = "CreateNewFromProposal";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePrinted (java.sql.Timestamp DatePrinted);

	/**
	 * Get Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePrinted();

    /** Column definition for DatePrinted */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DatePrinted = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DatePrinted", null);
    /** Column name DatePrinted */
    public static final String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DeliveryRule", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DescriptionBottom = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DescriptionBottom", null);
    /** Column name DescriptionBottom */
    public static final String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DocAction", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DocStatus", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
	 * Set Lieferempfänger-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDropShip_BPartner_Memo (java.lang.String DropShip_BPartner_Memo);

	/**
	 * Get Lieferempfänger-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getDropShip_BPartner_Memo();

    /** Column definition for DropShip_BPartner_Memo */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DropShip_BPartner_Memo = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "DropShip_BPartner_Memo", null);
    /** Column name DropShip_BPartner_Memo */
    public static final String COLUMNNAME_DropShip_BPartner_Memo = "DropShip_BPartner_Memo";

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
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "FreightAmt", null);
    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "GrandTotal", null);
    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncoterm (java.lang.String Incoterm);

	/**
	 * Get Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIncoterm();

    /** Column definition for Incoterm */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Incoterm = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Incoterm", null);
    /** Column name Incoterm */
    public static final String COLUMNNAME_Incoterm = "Incoterm";

	/**
	 * Set IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncotermLocation (java.lang.String IncotermLocation);

	/**
	 * Get IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIncotermLocation();

    /** Column definition for IncotermLocation */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IncotermLocation = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IncotermLocation", null);
    /** Column name IncotermLocation */
    public static final String COLUMNNAME_IncotermLocation = "IncotermLocation";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCreditApproved (boolean IsCreditApproved);

	/**
	 * Get Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCreditApproved();

    /** Column definition for IsCreditApproved */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsCreditApproved = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsCreditApproved", null);
    /** Column name IsCreditApproved */
    public static final String COLUMNNAME_IsCreditApproved = "IsCreditApproved";

	/**
	 * Set Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDelivered();

    /** Column definition for IsDelivered */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDelivered = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsDelivered", null);
    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/**
	 * Set Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountPrinted();

    /** Column definition for IsDiscountPrinted */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDiscountPrinted = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsDiscountPrinted", null);
    /** Column name IsDiscountPrinted */
    public static final String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDropShip();

    /** Column definition for IsDropShip */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsDropShip", null);
    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvoiced();

    /** Column definition for IsInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsInvoiced = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsInvoiced", null);
    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Auftrag nur mit Vertriebspartner.
	 * Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSalesPartnerRequired (boolean IsSalesPartnerRequired);

	/**
	 * Get Auftrag nur mit Vertriebspartner.
	 * Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSalesPartnerRequired();

    /** Column definition for IsSalesPartnerRequired */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSalesPartnerRequired = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsSalesPartnerRequired", null);
    /** Column name IsSalesPartnerRequired */
    public static final String COLUMNNAME_IsSalesPartnerRequired = "IsSalesPartnerRequired";

	/**
	 * Set Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelected (boolean IsSelected);

	/**
	 * Get Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelected();

    /** Column definition for IsSelected */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSelected = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsSelected", null);
    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsSOTrx", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsTaxIncluded", null);
    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTransferred (boolean IsTransferred);

	/**
	 * Get Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTransferred();

    /** Column definition for IsTransferred */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsTransferred = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsTransferred", null);
    /** Column name IsTransferred */
    public static final String COLUMNNAME_IsTransferred = "IsTransferred";

	/**
	 * Set Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseBillToAddress (boolean IsUseBillToAddress);

	/**
	 * Get Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseBillToAddress();

    /** Column definition for IsUseBillToAddress */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsUseBillToAddress = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsUseBillToAddress", null);
    /** Column name IsUseBillToAddress */
    public static final String COLUMNNAME_IsUseBillToAddress = "IsUseBillToAddress";

	/**
	 * Set Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/**
	 * Get Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseBPartnerAddress();

    /** Column definition for IsUseBPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsUseBPartnerAddress = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "IsUseBPartnerAddress", null);
    /** Column name IsUseBPartnerAddress */
    public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLink_Order_ID (int Link_Order_ID);

	/**
	 * Get Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLink_Order_ID();

	public org.compiere.model.I_C_Order getLink_Order();

	public void setLink_Order(org.compiere.model.I_C_Order Link_Order);

    /** Column definition for Link_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Link_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order>(I_C_Order.class, "Link_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name Link_Order_ID */
    public static final String COLUMNNAME_Link_Order_ID = "Link_Order_ID";

	/**
	 * Set Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCategory_ID();

	public org.compiere.model.I_M_FreightCategory getM_FreightCategory();

	public void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory);

    /** Column definition for M_FreightCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_FreightCategory> COLUMN_M_FreightCategory_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_FreightCategory>(I_C_Order.class, "M_FreightCategory_ID", org.compiere.model.I_M_FreightCategory.class);
    /** Column name M_FreightCategory_ID */
    public static final String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_Shipper>(I_C_Order.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderline_includedTab (java.lang.String Orderline_includedTab);

	/**
	 * Get Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderline_includedTab();

    /** Column definition for Orderline_includedTab */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Orderline_includedTab = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Orderline_includedTab", null);
    /** Column name Orderline_includedTab */
    public static final String COLUMNNAME_Orderline_includedTab = "Orderline_includedTab";

	/**
	 * Set OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderType (java.lang.String OrderType);

	/**
	 * Get OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderType();

    /** Column definition for OrderType */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_OrderType = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "OrderType", null);
    /** Column name OrderType */
    public static final String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPay_BPartner_ID (int Pay_BPartner_ID);

	/**
	 * Get Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPay_BPartner_ID();

    /** Column definition for Pay_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Pay_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Pay_BPartner_ID", null);
    /** Column name Pay_BPartner_ID */
    public static final String COLUMNNAME_Pay_BPartner_ID = "Pay_BPartner_ID";

	/**
	 * Set Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPay_Location_ID (int Pay_Location_ID);

	/**
	 * Get Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPay_Location_ID();

    /** Column definition for Pay_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Pay_Location_ID = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Pay_Location_ID", null);
    /** Column name Pay_Location_ID */
    public static final String COLUMNNAME_Pay_Location_ID = "Pay_Location_ID";

	/**
	 * Set Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "PaymentRule", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "PreparationDate", null);
    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPromotionCode (java.lang.String PromotionCode);

	/**
	 * Get Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPromotionCode();

    /** Column definition for PromotionCode */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PromotionCode = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "PromotionCode", null);
    /** Column name PromotionCode */
    public static final String COLUMNNAME_PromotionCode = "PromotionCode";

	/**
	 * Set Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty_FastInput (java.math.BigDecimal Qty_FastInput);

	/**
	 * Get Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty_FastInput();

    /** Column definition for Qty_FastInput */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Qty_FastInput = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Qty_FastInput", null);
    /** Column name Qty_FastInput */
    public static final String COLUMNNAME_Qty_FastInput = "Qty_FastInput";

	/**
	 * Set Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReceivedVia (java.lang.String ReceivedVia);

	/**
	 * Get Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReceivedVia();

    /** Column definition for ReceivedVia */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_ReceivedVia = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "ReceivedVia", null);
    /** Column name ReceivedVia */
    public static final String COLUMNNAME_ReceivedVia = "ReceivedVia";

	/**
	 * Set Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setRef_DateOrder (java.sql.Timestamp Ref_DateOrder);

	/**
	 * Get Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getRef_DateOrder();

    /** Column definition for Ref_DateOrder */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Ref_DateOrder = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Ref_DateOrder", null);
    /** Column name Ref_DateOrder */
    public static final String COLUMNNAME_Ref_DateOrder = "Ref_DateOrder";

	/**
	 * Set Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_Order_ID (int Ref_Order_ID);

	/**
	 * Get Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_Order_ID();

	public org.compiere.model.I_C_Order getRef_Order();

	public void setRef_Order(org.compiere.model.I_C_Order Ref_Order);

    /** Column definition for Ref_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order>(I_C_Order.class, "Ref_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name Ref_Order_ID */
    public static final String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";

	/**
	 * Set Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_Proposal_ID (int Ref_Proposal_ID);

	/**
	 * Get Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_Proposal_ID();

	public org.compiere.model.I_C_Order getRef_Proposal();

	public void setRef_Proposal(org.compiere.model.I_C_Order Ref_Proposal);

    /** Column definition for Ref_Proposal_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Proposal_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order>(I_C_Order.class, "Ref_Proposal_ID", org.compiere.model.I_C_Order.class);
    /** Column name Ref_Proposal_ID */
    public static final String COLUMNNAME_Ref_Proposal_ID = "Ref_Proposal_ID";

	/**
	 * Set Vertriebspartnercode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesPartnerCode (java.lang.String SalesPartnerCode);

	/**
	 * Get Vertriebspartnercode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSalesPartnerCode();

    /** Column definition for SalesPartnerCode */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_SalesPartnerCode = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "SalesPartnerCode", null);
    /** Column name SalesPartnerCode */
    public static final String COLUMNNAME_SalesPartnerCode = "SalesPartnerCode";

	/**
	 * Set Kundenbetreuer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Kundenbetreuer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSendEMail (boolean SendEMail);

	/**
	 * Get E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSendEMail();

    /** Column definition for SendEMail */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_SendEMail = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "SendEMail", null);
    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Set Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTotalLines (java.math.BigDecimal TotalLines);

	/**
	 * Get Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalLines();

    /** Column definition for TotalLines */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "TotalLines", null);
    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue>(I_C_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
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
    public static final org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue>(I_C_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVolume (java.math.BigDecimal Volume);

	/**
	 * Get Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getVolume();

    /** Column definition for Volume */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Volume = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Volume", null);
    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeight (java.math.BigDecimal Weight);

	/**
	 * Get Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWeight();

    /** Column definition for Weight */
    public static final org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Weight = new org.adempiere.model.ModelColumn<I_C_Order, Object>(I_C_Order.class, "Weight", null);
    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";
}
