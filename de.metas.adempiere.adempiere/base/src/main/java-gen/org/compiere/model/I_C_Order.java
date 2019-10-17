package org.compiere.model;


/** Generated Interface for C_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order 
{

    /** TableName=C_Order */
    String Table_Name = "C_Order";

    /** AD_Table_ID=259 */
    int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

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
	int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

    /** Column definition for AD_OrgTrx_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

    /** Column definition for AD_User_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmountRefunded (java.math.BigDecimal AmountRefunded);

	/**
	 * Get AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getAmountRefunded();

    /** Column definition for AmountRefunded */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_AmountRefunded = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AmountRefunded", null);
    /** Column name AmountRefunded */
    String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/**
	 * Set AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmountTendered (java.math.BigDecimal AmountTendered);

	/**
	 * Get AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getAmountTendered();

    /** Column definition for AmountTendered */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_AmountTendered = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "AmountTendered", null);
    /** Column name AmountTendered */
    String COLUMNNAME_AmountTendered = "AmountTendered";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

    /** Column definition for Bill_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner> COLUMN_Bill_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Bill_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Bill_BPartner_ID */
    String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Rechnungspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setBill_BPartner_Memo (java.lang.String Bill_BPartner_Memo);

	/**
	 * Get Rechnungspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated java.lang.String getBill_BPartner_Memo();

    /** Column definition for Bill_BPartner_Memo */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Bill_BPartner_Memo = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Bill_BPartner_Memo", null);
    /** Column name Bill_BPartner_Memo */
    String COLUMNNAME_Bill_BPartner_Memo = "Bill_BPartner_Memo";

	/**
	 * Set Rechnungsstandort.
	 * Business Partner Location for invoicing
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Rechnungsstandort.
	 * Business Partner Location for invoicing
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

    /** Column definition for Bill_Location_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner_Location> COLUMN_Bill_Location_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Bill_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name Bill_Location_ID */
    String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Rechnungskontakt.
	 * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Rechnungskontakt.
	 * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

    /** Column definition for Bill_User_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_Bill_User_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Bill_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Bill_User_ID */
    String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBillToAddress (java.lang.String BillToAddress);

	/**
	 * Get Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getBillToAddress();

    /** Column definition for BillToAddress */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_BillToAddress = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "BillToAddress", null);
    /** Column name BillToAddress */
    String COLUMNNAME_BillToAddress = "BillToAddress";

	/**
	 * Set Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	org.compiere.model.I_C_Activity getC_Activity();

	void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

    /** Column definition for C_BPartner_Location_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setC_BPartner_Memo (java.lang.String C_BPartner_Memo);

	/**
	 * Get Geschäftspartner-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated java.lang.String getC_BPartner_Memo();

    /** Column definition for C_BPartner_Memo */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_C_BPartner_Memo = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_BPartner_Memo", null);
    /** Column name C_BPartner_Memo */
    String COLUMNNAME_C_BPartner_Memo = "C_BPartner_Memo";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CashLine_ID (int C_CashLine_ID);

	/**
	 * Get Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CashLine_ID();

	org.compiere.model.I_C_CashLine getC_CashLine();

	void setC_CashLine(org.compiere.model.I_C_CashLine C_CashLine);

    /** Column definition for C_CashLine_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_CashLine> COLUMN_C_CashLine_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_CashLine_ID", org.compiere.model.I_C_CashLine.class);
    /** Column name C_CashLine_ID */
    String COLUMNNAME_C_CashLine_ID = "C_CashLine_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	org.compiere.model.I_C_Charge getC_Charge();

	void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ConversionType_ID();

    /** Column definition for C_ConversionType_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

    /** Column definition for C_DocType_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

    /** Column definition for C_DocTypeTarget_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeTarget_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_DocTypeTarget_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeTarget_ID */
    String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

    /** Column definition for C_Order_ID */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Order_ID", null);
    /** Column name C_Order_ID */
    String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	org.compiere.model.I_C_Payment getC_Payment();

	void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_ID();

	org.compiere.model.I_C_POS getC_POS();

	void setC_POS(org.compiere.model.I_C_POS C_POS);

    /** Column definition for C_POS_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_POS> COLUMN_C_POS_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_POS_ID", org.compiere.model.I_C_POS.class);
    /** Column name C_POS_ID */
    String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	org.compiere.model.I_C_Project getC_Project();

	void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompleteOrderDiscount (java.math.BigDecimal CompleteOrderDiscount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getCompleteOrderDiscount();

    /** Column definition for CompleteOrderDiscount */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CompleteOrderDiscount = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "CompleteOrderDiscount", null);
    /** Column name CompleteOrderDiscount */
    String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCopyFrom();

    /** Column definition for CopyFrom */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CopyFrom = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "CopyFrom", null);
    /** Column name CopyFrom */
    String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateCopy (java.lang.String CreateCopy);

	/**
	 * Get Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateCopy();

    /** Column definition for CreateCopy */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CreateCopy = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "CreateCopy", null);
    /** Column name CreateCopy */
    String COLUMNNAME_CreateCopy = "CreateCopy";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

    /** Column definition for Created */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Created", null);
    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

    /** Column definition for CreatedBy */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateNewFromProposal (java.lang.String CreateNewFromProposal);

	/**
	 * Get Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateNewFromProposal();

    /** Column definition for CreateNewFromProposal */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_CreateNewFromProposal = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "CreateNewFromProposal", null);
    /** Column name CreateNewFromProposal */
    String COLUMNNAME_CreateNewFromProposal = "CreateNewFromProposal";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DateAcct", null);
    /** Column name DateAcct */
    String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DateOrdered", null);
    /** Column name DateOrdered */
    String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePrinted (java.sql.Timestamp DatePrinted);

	/**
	 * Get Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDatePrinted();

    /** Column definition for DatePrinted */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DatePrinted = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DatePrinted", null);
    /** Column name DatePrinted */
    String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DatePromised", null);
    /** Column name DatePromised */
    String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

    /** Column definition for Description */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Description", null);
    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/**
	 * Set Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom (java.lang.String DescriptionBottom);

	/**
	 * Get Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescriptionBottom();

    /** Column definition for DescriptionBottom */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DescriptionBottom = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DescriptionBottom", null);
    /** Column name DescriptionBottom */
    String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

    /** Column definition for DocAction */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DocAction", null);
    /** Column name DocAction */
    String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DocStatus", null);
    /** Column name DocStatus */
    String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DocumentNo", null);
    /** Column name DocumentNo */
    String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_ID();

    /** Column definition for DropShip_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner> COLUMN_DropShip_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DropShip_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name DropShip_BPartner_ID */
    String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Lieferempfänger-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setDropShip_BPartner_Memo (java.lang.String DropShip_BPartner_Memo);

	/**
	 * Get Lieferempfänger-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated java.lang.String getDropShip_BPartner_Memo();

    /** Column definition for DropShip_BPartner_Memo */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_DropShip_BPartner_Memo = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DropShip_BPartner_Memo", null);
    /** Column name DropShip_BPartner_Memo */
    String COLUMNNAME_DropShip_BPartner_Memo = "DropShip_BPartner_Memo";

	/**
	 * Set Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_ID();

    /** Column definition for DropShip_Location_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_BPartner_Location> COLUMN_DropShip_Location_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DropShip_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name DropShip_Location_ID */
    String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_User_ID();

    /** Column definition for DropShip_User_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_DropShip_User_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "DropShip_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name DropShip_User_ID */
    String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

	/**
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "FreightAmt", null);
    /** Column name FreightAmt */
    String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFreightCostRule();

    /** Column definition for FreightCostRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_FreightCostRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "FreightCostRule", null);
    /** Column name FreightCostRule */
    String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "GrandTotal", null);
    /** Column name GrandTotal */
    String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncoterm (java.lang.String Incoterm);

	/**
	 * Get Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getIncoterm();

    /** Column definition for Incoterm */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Incoterm = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Incoterm", null);
    /** Column name Incoterm */
    String COLUMNNAME_Incoterm = "Incoterm";

	/**
	 * Set IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (java.lang.String IncotermLocation);

	/**
	 * Get IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getIncotermLocation();

    /** Column definition for IncotermLocation */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IncotermLocation = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IncotermLocation", null);
    /** Column name IncotermLocation */
    String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

    /** Column definition for InvoiceRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

    /** Column definition for IsActive */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsActive", null);
    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

    /** Column definition for IsApproved */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsApproved", null);
    /** Column name IsApproved */
    String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreditApproved (boolean IsCreditApproved);

	/**
	 * Get Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreditApproved();

    /** Column definition for IsCreditApproved */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsCreditApproved = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsCreditApproved", null);
    /** Column name IsCreditApproved */
    String COLUMNNAME_IsCreditApproved = "IsCreditApproved";

	/**
	 * Set Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDelivered();

    /** Column definition for IsDelivered */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDelivered = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsDelivered", null);
    /** Column name IsDelivered */
    String COLUMNNAME_IsDelivered = "IsDelivered";

	/**
	 * Set Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountPrinted();

    /** Column definition for IsDiscountPrinted */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDiscountPrinted = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsDiscountPrinted", null);
    /** Column name IsDiscountPrinted */
    String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

    /** Column definition for IsDropShip */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsDropShip", null);
    /** Column name IsDropShip */
    String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiced();

    /** Column definition for IsInvoiced */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsInvoiced = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsInvoiced", null);
    /** Column name IsInvoiced */
    String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

    /** Column definition for IsPrinted */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsPrinted", null);
    /** Column name IsPrinted */
    String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelected (boolean IsSelected);

	/**
	 * Get Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelected();

    /** Column definition for IsSelected */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSelected = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsSelected", null);
    /** Column name IsSelected */
    String COLUMNNAME_IsSelected = "IsSelected";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelfService();

    /** Column definition for IsSelfService */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsSelfService", null);
    /** Column name IsSelfService */
    String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

    /** Column definition for IsSOTrx */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

    /** Column definition for IsTaxIncluded */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsTaxIncluded", null);
    /** Column name IsTaxIncluded */
    String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTransferred (boolean IsTransferred);

	/**
	 * Get Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTransferred();

    /** Column definition for IsTransferred */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsTransferred = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsTransferred", null);
    /** Column name IsTransferred */
    String COLUMNNAME_IsTransferred = "IsTransferred";

	/**
	 * Set Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBillToAddress (boolean IsUseBillToAddress);

	/**
	 * Get Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBillToAddress();

    /** Column definition for IsUseBillToAddress */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsUseBillToAddress = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsUseBillToAddress", null);
    /** Column name IsUseBillToAddress */
    String COLUMNNAME_IsUseBillToAddress = "IsUseBillToAddress";

	/**
	 * Set Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/**
	 * Get Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBPartnerAddress();

    /** Column definition for IsUseBPartnerAddress */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_IsUseBPartnerAddress = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "IsUseBPartnerAddress", null);
    /** Column name IsUseBPartnerAddress */
    String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLink_Order_ID (int Link_Order_ID);

	/**
	 * Get Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLink_Order_ID();

	org.compiere.model.I_C_Order getLink_Order();

	void setLink_Order(org.compiere.model.I_C_Order Link_Order);

    /** Column definition for Link_Order_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Link_Order_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Link_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name Link_Order_ID */
    String COLUMNNAME_Link_Order_ID = "Link_Order_ID";

	/**
	 * Set Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCategory_ID();

	org.compiere.model.I_M_FreightCategory getM_FreightCategory();

	void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory);

    /** Column definition for M_FreightCategory_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_FreightCategory> COLUMN_M_FreightCategory_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_FreightCategory_ID", org.compiere.model.I_M_FreightCategory.class);
    /** Column name M_FreightCategory_ID */
    String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

    /** Column definition for M_PriceList_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

    /** Column definition for M_PricingSystem_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

    /** Column definition for M_Product_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

    /** Column definition for M_Warehouse_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderline_includedTab (java.lang.String Orderline_includedTab);

	/**
	 * Get Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getOrderline_includedTab();

    /** Column definition for Orderline_includedTab */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Orderline_includedTab = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Orderline_includedTab", null);
    /** Column name Orderline_includedTab */
    String COLUMNNAME_Orderline_includedTab = "Orderline_includedTab";

	/**
	 * Set OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderType (java.lang.String OrderType);

	/**
	 * Get OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getOrderType();

    /** Column definition for OrderType */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_OrderType = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "OrderType", null);
    /** Column name OrderType */
    String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPay_BPartner_ID (int Pay_BPartner_ID);

	/**
	 * Get Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPay_BPartner_ID();

    /** Column definition for Pay_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Pay_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Pay_BPartner_ID", null);
    /** Column name Pay_BPartner_ID */
    String COLUMNNAME_Pay_BPartner_ID = "Pay_BPartner_ID";

	/**
	 * Set Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPay_Location_ID (int Pay_Location_ID);

	/**
	 * Get Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPay_Location_ID();

    /** Column definition for Pay_Location_ID */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Pay_Location_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Pay_Location_ID", null);
    /** Column name Pay_Location_ID */
    String COLUMNNAME_Pay_Location_ID = "Pay_Location_ID";

	/**
	 * Set Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "PaymentRule", null);
    /** Column name PaymentRule */
    String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getPOReference();

    /** Column definition for POReference */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "POReference", null);
    /** Column name POReference */
    String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Verbucht.
	 * Posting status
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

    /** Column definition for Posted */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Posted", null);
    /** Column name Posted */
    String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "PreparationDate", null);
    /** Column name PreparationDate */
    String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "PriorityRule", null);
    /** Column name PriorityRule */
    String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

    /** Column definition for Processed */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Processed", null);
    /** Column name Processed */
    String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

    /** Column definition for Processing */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Processing", null);
    /** Column name Processing */
    String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPromotionCode (java.lang.String PromotionCode);

	/**
	 * Get Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getPromotionCode();

    /** Column definition for PromotionCode */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_PromotionCode = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "PromotionCode", null);
    /** Column name PromotionCode */
    String COLUMNNAME_PromotionCode = "PromotionCode";

	/**
	 * Set Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_FastInput (java.math.BigDecimal Qty_FastInput);

	/**
	 * Get Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getQty_FastInput();

    /** Column definition for Qty_FastInput */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Qty_FastInput = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Qty_FastInput", null);
    /** Column name Qty_FastInput */
    String COLUMNNAME_Qty_FastInput = "Qty_FastInput";

	/**
	 * Set Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceivedVia (java.lang.String ReceivedVia);

	/**
	 * Get Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getReceivedVia();

    /** Column definition for ReceivedVia */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_ReceivedVia = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "ReceivedVia", null);
    /** Column name ReceivedVia */
    String COLUMNNAME_ReceivedVia = "ReceivedVia";

	/**
	 * Set Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setRef_DateOrder (java.sql.Timestamp Ref_DateOrder);

	/**
	 * Get Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated java.sql.Timestamp getRef_DateOrder();

    /** Column definition for Ref_DateOrder */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Ref_DateOrder = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Ref_DateOrder", null);
    /** Column name Ref_DateOrder */
    String COLUMNNAME_Ref_DateOrder = "Ref_DateOrder";

	/**
	 * Set Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Order_ID (int Ref_Order_ID);

	/**
	 * Get Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Order_ID();

	org.compiere.model.I_C_Order getRef_Order();

	void setRef_Order(org.compiere.model.I_C_Order Ref_Order);

    /** Column definition for Ref_Order_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Order_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Ref_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name Ref_Order_ID */
    String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";

	/**
	 * Set Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Proposal_ID (int Ref_Proposal_ID);

	/**
	 * Get Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Proposal_ID();

	org.compiere.model.I_C_Order getRef_Proposal();

	void setRef_Proposal(org.compiere.model.I_C_Order Ref_Proposal);

    /** Column definition for Ref_Proposal_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Proposal_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Ref_Proposal_ID", org.compiere.model.I_C_Order.class);
    /** Column name Ref_Proposal_ID */
    String COLUMNNAME_Ref_Proposal_ID = "Ref_Proposal_ID";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

    /** Column definition for SalesRep_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSendEMail (boolean SendEMail);

	/**
	 * Get E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSendEMail();

    /** Column definition for SendEMail */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_SendEMail = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "SendEMail", null);
    /** Column name SendEMail */
    String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Set Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalLines (java.math.BigDecimal TotalLines);

	/**
	 * Get Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getTotalLines();

    /** Column definition for TotalLines */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "TotalLines", null);
    /** Column name TotalLines */
    String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Updated", null);
    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

    /** Column definition for UpdatedBy */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    org.adempiere.model.ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVolume (java.math.BigDecimal Volume);

	/**
	 * Get Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getVolume();

    /** Column definition for Volume */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Volume = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Volume", null);
    /** Column name Volume */
    String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWeight (java.math.BigDecimal Weight);

	/**
	 * Get Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getWeight();

    /** Column definition for Weight */
    org.adempiere.model.ModelColumn<I_C_Order, Object> COLUMN_Weight = new org.adempiere.model.ModelColumn<>(I_C_Order.class, "Weight", null);
    /** Column name Weight */
    String COLUMNNAME_Weight = "Weight";
}
