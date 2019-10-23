package org.compiere.model;


/** Generated Interface for C_Invoice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice 
{

    /** TableName=C_Invoice */
    String Table_Name = "C_Invoice";

    /** AD_Table_ID=318 */
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
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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

	org.compiere.model.I_AD_Org getAD_OrgTrx();

	void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
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

	org.compiere.model.I_AD_User getAD_User();

	void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "BPartnerAddress", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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

	org.compiere.model.I_C_BPartner getC_BPartner();

	void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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

	org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_CashLine> COLUMN_C_CashLine_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_CashLine_ID", org.compiere.model.I_C_CashLine.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
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

	org.compiere.model.I_C_ConversionType getC_ConversionType();

	void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column definition for C_ConversionType_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
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

	org.compiere.model.I_C_Currency getC_Currency();

	void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
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

	org.compiere.model.I_C_DocType getC_DocType();

	void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
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

	org.compiere.model.I_C_DocType getC_DocTypeTarget();

	void setC_DocTypeTarget(org.compiere.model.I_C_DocType C_DocTypeTarget);

    /** Column definition for C_DocTypeTarget_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeTarget_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_DocTypeTarget_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeTarget_ID */
    String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DunningLevel_ID();

	org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

    /** Column definition for C_DunningLevel_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
    /** Column name C_DunningLevel_ID */
    String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

    /** Column definition for C_Invoice_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Invoice_ID", null);
    /** Column name C_Invoice_ID */
    String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

//	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    String COLUMNNAME_ChargeAmt = "ChargeAmt";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CopyFrom = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CopyFrom", null);
    /** Column name CopyFrom */
    String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Nachbelastung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateAdjustmentCharge (java.lang.String CreateAdjustmentCharge);

	/**
	 * Get Nachbelastung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateAdjustmentCharge();

    /** Column definition for CreateAdjustmentCharge */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CreateAdjustmentCharge = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreateAdjustmentCharge", null);
    /** Column name CreateAdjustmentCharge */
    String COLUMNNAME_CreateAdjustmentCharge = "CreateAdjustmentCharge";

	/**
	 * Set Erzeuge Gutschrift.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateCreditMemo (java.lang.String CreateCreditMemo);

	/**
	 * Get Erzeuge Gutschrift.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateCreditMemo();

    /** Column definition for CreateCreditMemo */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CreateCreditMemo = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreateCreditMemo", null);
    /** Column name CreateCreditMemo */
    String COLUMNNAME_CreateCreditMemo = "CreateCreditMemo";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Created", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CreateDta.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateDta (java.lang.String CreateDta);

	/**
	 * Get CreateDta.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateDta();

    /** Column definition for CreateDta */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CreateDta = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreateDta", null);
    /** Column name CreateDta */
    String COLUMNNAME_CreateDta = "CreateDta";

	/**
	 * Set Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateFrom (java.lang.String CreateFrom);

	/**
	 * Get Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateFrom();

    /** Column definition for CreateFrom */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CreateFrom = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreateFrom", null);
    /** Column name CreateFrom */
    String COLUMNNAME_CreateFrom = "CreateFrom";

	/**
	 * Set Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditMemoReason (java.lang.String CreditMemoReason);

	/**
	 * Get Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreditMemoReason();

    /** Column definition for CreditMemoReason */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_CreditMemoReason = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "CreditMemoReason", null);
    /** Column name CreditMemoReason */
    String COLUMNNAME_CreditMemoReason = "CreditMemoReason";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DateAcct", null);
    /** Column name DateAcct */
    String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Rechnungsdatum.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Rechnungsdatum.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DateOrdered", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DatePrinted = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DatePrinted", null);
    /** Column name DatePrinted */
    String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

    /** Column definition for Description */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Description", null);
    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/**
	 * Set DescriptionBottom.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom (java.lang.String DescriptionBottom);

	/**
	 * Get DescriptionBottom.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescriptionBottom();

    /** Column definition for DescriptionBottom */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DescriptionBottom = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DescriptionBottom", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DocAction", null);
    /** Column name DocAction */
    String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Dokument Basis Typ.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Dokument Basis Typ.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DocBaseType", null);
    /** Column name DocBaseType */
    String COLUMNNAME_DocBaseType = "DocBaseType";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DocStatus", null);
    /** Column name DocStatus */
    String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DocumentNo", null);
    /** Column name DocumentNo */
    String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningGrace (java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDunningGrace();

    /** Column definition for DunningGrace */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_DunningGrace = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "DunningGrace", null);
    /** Column name DunningGrace */
    String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGenerateTo (java.lang.String GenerateTo);

	/**
	 * Get Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getGenerateTo();

    /** Column definition for GenerateTo */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_GenerateTo = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "GenerateTo", null);
    /** Column name GenerateTo */
    String COLUMNNAME_GenerateTo = "GenerateTo";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "GrandTotal", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Incoterm = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Incoterm", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IncotermLocation = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IncotermLocation", null);
    /** Column name IncotermLocation */
    String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Invoice_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoice_includedTab (java.lang.String Invoice_includedTab);

	/**
	 * Get Invoice_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoice_includedTab();

    /** Column definition for Invoice_includedTab */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Invoice_includedTab = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Invoice_includedTab", null);
    /** Column name Invoice_includedTab */
    String COLUMNNAME_Invoice_includedTab = "Invoice_includedTab";

	/**
	 * Set Inkasso-Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceCollectionType (java.lang.String InvoiceCollectionType);

	/**
	 * Get Inkasso-Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceCollectionType();

    /** Column definition for InvoiceCollectionType */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_InvoiceCollectionType = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "InvoiceCollectionType", null);
    /** Column name InvoiceCollectionType */
    String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsActive", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsApproved", null);
    /** Column name IsApproved */
    String COLUMNNAME_IsApproved = "IsApproved";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsDiscountPrinted = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsDiscountPrinted", null);
    /** Column name IsDiscountPrinted */
    String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInDispute();

    /** Column definition for IsInDispute */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsInDispute = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsInDispute", null);
    /** Column name IsInDispute */
    String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Gezahlt.
	 * The document is paid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPaid (boolean IsPaid);

	/**
	 * Get Gezahlt.
	 * The document is paid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPaid();

    /** Column definition for IsPaid */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsPaid = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsPaid", null);
    /** Column name IsPaid */
    String COLUMNNAME_IsPaid = "IsPaid";

	/**
	 * Set Pay Schedule valid.
	 * Is the Payment Schedule is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPayScheduleValid (boolean IsPayScheduleValid);

	/**
	 * Get Pay Schedule valid.
	 * Is the Payment Schedule is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPayScheduleValid();

    /** Column definition for IsPayScheduleValid */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsPayScheduleValid = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsPayScheduleValid", null);
    /** Column name IsPayScheduleValid */
    String COLUMNNAME_IsPayScheduleValid = "IsPayScheduleValid";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsPrinted", null);
    /** Column name IsPrinted */
    String COLUMNNAME_IsPrinted = "IsPrinted";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsSelfService", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsSOTrx", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsTaxIncluded", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsTransferred = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsTransferred", null);
    /** Column name IsTransferred */
    String COLUMNNAME_IsTransferred = "IsTransferred";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_IsUseBPartnerAddress = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "IsUseBPartnerAddress", null);
    /** Column name IsUseBPartnerAddress */
    String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	org.compiere.model.I_M_PriceList getM_PriceList();

	void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_RMA_ID (int M_RMA_ID);

	/**
	 * Get Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_RMA_ID();

	org.compiere.model.I_M_RMA getM_RMA();

	void setM_RMA(org.compiere.model.I_M_RMA M_RMA);

    /** Column definition for M_RMA_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_M_RMA> COLUMN_M_RMA_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "M_RMA_ID", org.compiere.model.I_M_RMA.class);
    /** Column name M_RMA_ID */
    String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

	/**
	 * Set Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "PaymentRule", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "POReference", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Posted", null);
    /** Column name Posted */
    String COLUMNNAME_Posted = "Posted";

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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Processed", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Processing", null);
    /** Column name Processing */
    String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenced Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Invoice_ID (int Ref_Invoice_ID);

	/**
	 * Get Referenced Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Invoice_ID();

	org.compiere.model.I_C_Invoice getRef_Invoice();

	void setRef_Invoice(org.compiere.model.I_C_Invoice Ref_Invoice);

    /** Column definition for Ref_Invoice_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Invoice> COLUMN_Ref_Invoice_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Ref_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name Ref_Invoice_ID */
    String COLUMNNAME_Ref_Invoice_ID = "Ref_Invoice_ID";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	org.compiere.model.I_C_Invoice getReversal();

	void setReversal(org.compiere.model.I_C_Invoice Reversal);

    /** Column definition for Reversal_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_Invoice> COLUMN_Reversal_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Reversal_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name Reversal_ID */
    String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	org.compiere.model.I_AD_User getSalesRep();

	void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_SendEMail = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "SendEMail", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "TotalLines", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "Updated", null);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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
    org.adempiere.model.ModelColumn<I_C_Invoice, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set UserFlag.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserFlag (java.lang.String UserFlag);

	/**
	 * Get UserFlag.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getUserFlag();

    /** Column definition for UserFlag */
    org.adempiere.model.ModelColumn<I_C_Invoice, Object> COLUMN_UserFlag = new org.adempiere.model.ModelColumn<>(I_C_Invoice.class, "UserFlag", null);
    /** Column name UserFlag */
    String COLUMNNAME_UserFlag = "UserFlag";
}
