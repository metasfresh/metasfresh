/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
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
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Buchende Organisation.
	  * Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Buchende Organisation.
	  * Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException;

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get Ansprechpartner.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column name AmountRefunded */
    public static final String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/** Set AmountRefunded	  */
	public void setAmountRefunded (java.math.BigDecimal AmountRefunded);

	/** Get AmountRefunded	  */
	public java.math.BigDecimal getAmountRefunded();

    /** Column name AmountTendered */
    public static final String COLUMNNAME_AmountTendered = "AmountTendered";

	/** Set AmountTendered	  */
	public void setAmountTendered (java.math.BigDecimal AmountTendered);

	/** Get AmountTendered	  */
	public java.math.BigDecimal getAmountTendered();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/** Set Rechnungspartner.
	  * Business Partner to be invoiced
	  */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/** Get Rechnungspartner.
	  * Business Partner to be invoiced
	  */
	public int getBill_BPartner_ID();

	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException;

	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner);

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/** Set Rechnungsstandort.
	  * Business Partner Location for invoicing
	  */
	public void setBill_Location_ID (int Bill_Location_ID);

	/** Get Rechnungsstandort.
	  * Business Partner Location for invoicing
	  */
	public int getBill_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException;

	public void setBill_Location(org.compiere.model.I_C_BPartner_Location Bill_Location);

    /** Column name Bill_User_ID */
    public static final String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/** Set Rechnungskontakt.
	  * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	public void setBill_User_ID (int Bill_User_ID);

	/** Get Rechnungskontakt.
	  * Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	public int getBill_User_ID();

	public org.compiere.model.I_AD_User getBill_User() throws RuntimeException;

	public void setBill_User(org.compiere.model.I_AD_User Bill_User);

    /** Column name BillToAddress */
    public static final String COLUMNNAME_BillToAddress = "BillToAddress";

	/** Set Abw. Rechnungsadresse	  */
	public void setBillToAddress (java.lang.String BillToAddress);

	/** Get Abw. Rechnungsadresse	  */
	public java.lang.String getBillToAddress();

    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/** Set Anschrift-Text	  */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/** Get Anschrift-Text	  */
	public java.lang.String getBPartnerAddress();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Aktivität.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Aktivität.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/** Set Bankverbindung.
	  * Bank Account of the Business Partner
	  */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/** Get Bankverbindung.
	  * Bank Account of the Business Partner
	  */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException;

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Standort.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Werbemassnahme.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Werbemassnahme.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException;

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column name C_CashLine_ID */
    public static final String COLUMNNAME_C_CashLine_ID = "C_CashLine_ID";

	/** Set Cash Journal Line.
	  * Cash Journal Line
	  */
	public void setC_CashLine_ID (int C_CashLine_ID);

	/** Get Cash Journal Line.
	  * Cash Journal Line
	  */
	public int getC_CashLine_ID();

	public org.compiere.model.I_C_CashLine getC_CashLine() throws RuntimeException;

	public void setC_CashLine(org.compiere.model.I_C_CashLine C_CashLine);

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Kosten.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Kosten.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/** Set Kursart.
	  * Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/** Get Kursart.
	  * Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException;

	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Währung.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Währung.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Belegart.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Belegart.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/** Set Zielbelegart.
	  * Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/** Get Zielbelegart.
	  * Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException;

	public void setC_DocTypeTarget(org.compiere.model.I_C_DocType C_DocTypeTarget);

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Auftrag.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Auftrag.
	  * Order
	  */
	public int getC_Order_ID();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Zahlung.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Zahlung.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException;

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Zahlungskondition.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Zahlungskondition.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column name C_POS_ID */
    public static final String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/** Set POS-Terminal.
	  * Point of Sales Terminal
	  */
	public void setC_POS_ID (int C_POS_ID);

	/** Get POS-Terminal.
	  * Point of Sales Terminal
	  */
	public int getC_POS_ID();

	public org.compiere.model.I_C_POS getC_POS() throws RuntimeException;

	public void setC_POS(org.compiere.model.I_C_POS C_POS);

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Projekt.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Projekt.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/** Set Gebühr	  */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/** Get Gebühr	  */
	public java.math.BigDecimal getChargeAmt();

    /** Column name CompleteOrderDiscount */
    public static final String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

	/** Set Rabatt %.
	  * Abschlag in Prozent
	  */
	public void setCompleteOrderDiscount (java.math.BigDecimal CompleteOrderDiscount);

	/** Get Rabatt %.
	  * Abschlag in Prozent
	  */
	public java.math.BigDecimal getCompleteOrderDiscount();

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (java.lang.String CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public java.lang.String getCopyFrom();

    /** Column name CreateCopy */
    public static final String COLUMNNAME_CreateCopy = "CreateCopy";

	/** Set Create Copy	  */
	public void setCreateCopy (java.lang.String CreateCopy);

	/** Get Create Copy	  */
	public java.lang.String getCreateCopy();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name CreateNewFromProposal */
    public static final String COLUMNNAME_CreateNewFromProposal = "CreateNewFromProposal";

	/** Set Erzeuge Auftrag.
	  * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	  */
	public void setCreateNewFromProposal (java.lang.String CreateNewFromProposal);

	/** Get Erzeuge Auftrag.
	  * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	  */
	public java.lang.String getCreateNewFromProposal();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Buchungsdatum.
	  * Accounting Date
	  */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/** Get Buchungsdatum.
	  * Accounting Date
	  */
	public java.sql.Timestamp getDateAcct();

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Auftragsdatum.
	  * Date of Order
	  */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/** Get Auftragsdatum.
	  * Date of Order
	  */
	public java.sql.Timestamp getDateOrdered();

    /** Column name DatePrinted */
    public static final String COLUMNNAME_DatePrinted = "DatePrinted";

	/** Set Date printed.
	  * Date the document was printed.
	  */
	public void setDatePrinted (java.sql.Timestamp DatePrinted);

	/** Get Date printed.
	  * Date the document was printed.
	  */
	public java.sql.Timestamp getDatePrinted();

    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/** Set Zugesagter Termin.
	  * Date Order was promised
	  */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/** Get Zugesagter Termin.
	  * Date Order was promised
	  */
	public java.sql.Timestamp getDatePromised();

    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/** Set Lieferart.
	  * Defines the timing of Delivery
	  */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/** Get Lieferart.
	  * Defines the timing of Delivery
	  */
	public java.lang.String getDeliveryRule();

    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/** Set Lieferung durch.
	  * How the order will be delivered
	  */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/** Get Lieferung durch.
	  * How the order will be delivered
	  */
	public java.lang.String getDeliveryViaRule();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name DescriptionBottom */
    public static final String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/** Set Schlusstext	  */
	public void setDescriptionBottom (java.lang.String DescriptionBottom);

	/** Get Schlusstext	  */
	public java.lang.String getDescriptionBottom();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Belegverarbeitung.
	  * The targeted status of the document
	  */
	public void setDocAction (java.lang.String DocAction);

	/** Get Belegverarbeitung.
	  * The targeted status of the document
	  */
	public java.lang.String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Belegstatus.
	  * The current status of the document
	  */
	public void setDocStatus (java.lang.String DocStatus);

	/** Get Belegstatus.
	  * The current status of the document
	  */
	public java.lang.String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Beleg Nr..
	  * Document sequence number of the document
	  */
	public void setDocumentNo (java.lang.String DocumentNo);

	/** Get Beleg Nr..
	  * Document sequence number of the document
	  */
	public java.lang.String getDocumentNo();

    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/** Set Lieferempfänger.
	  * Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/** Get Lieferempfänger.
	  * Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID();

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException;

	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner);

    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/** Set Lieferadresse.
	  * Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/** Get Lieferadresse.
	  * Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException;

	public void setDropShip_Location(org.compiere.model.I_C_BPartner_Location DropShip_Location);

    /** Column name DropShip_User_ID */
    public static final String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

	/** Set Lieferkontakt.
	  * Business Partner Contact for drop shipment
	  */
	public void setDropShip_User_ID (int DropShip_User_ID);

	/** Get Lieferkontakt.
	  * Business Partner Contact for drop shipment
	  */
	public int getDropShip_User_ID();

	public org.compiere.model.I_AD_User getDropShip_User() throws RuntimeException;

	public void setDropShip_User(org.compiere.model.I_AD_User DropShip_User);

    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/** Set Frachtbetrag.
	  * Freight Amount 
	  */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/** Get Frachtbetrag.
	  * Freight Amount 
	  */
	public java.math.BigDecimal getFreightAmt();

    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/** Set Frachtkostenberechnung.
	  * Methode zur Frachtkostenberechnung
	  */
	public void setFreightCostRule (java.lang.String FreightCostRule);

	/** Get Frachtkostenberechnung.
	  * Methode zur Frachtkostenberechnung
	  */
	public java.lang.String getFreightCostRule();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Summe Gesamt.
	  * Total amount of document
	  */
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/** Get Summe Gesamt.
	  * Total amount of document
	  */
	public java.math.BigDecimal getGrandTotal();

    /** Column name Incoterm */
    public static final String COLUMNNAME_Incoterm = "Incoterm";

	/** Set Incoterm.
	  * Internationale Handelsklauseln (engl. International Commercial Terms)
	  */
	public void setIncoterm (java.lang.String Incoterm);

	/** Get Incoterm.
	  * Internationale Handelsklauseln (engl. International Commercial Terms)
	  */
	public java.lang.String getIncoterm();

    /** Column name IncotermLocation */
    public static final String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/** Set IncotermLocation.
	  * Anzugebender Ort für Handelsklausel
	  */
	public void setIncotermLocation (java.lang.String IncotermLocation);

	/** Get IncotermLocation.
	  * Anzugebender Ort für Handelsklausel
	  */
	public java.lang.String getIncotermLocation();

    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/** Set Rechnungsstellung.
	  * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	public void setInvoiceRule (java.lang.String InvoiceRule);

	/** Get Rechnungsstellung.
	  * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	public java.lang.String getInvoiceRule();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Freigegeben.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Freigegeben.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsCreditApproved */
    public static final String COLUMNNAME_IsCreditApproved = "IsCreditApproved";

	/** Set Kredit gebilligt.
	  * Credit  has been approved
	  */
	public void setIsCreditApproved (boolean IsCreditApproved);

	/** Get Kredit gebilligt.
	  * Credit  has been approved
	  */
	public boolean isCreditApproved();

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Zugestellt	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Zugestellt	  */
	public boolean isDelivered();

    /** Column name IsDiscountPrinted */
    public static final String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/** Set Rabatte drucken.
	  * Print Discount on Invoice and Order
	  */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/** Get Rabatte drucken.
	  * Print Discount on Invoice and Order
	  */
	public boolean isDiscountPrinted();

    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/** Set Streckengeschäft.
	  * Drop Shipments are sent from the Vendor directly to the Customer
	  */
	public void setIsDropShip (boolean IsDropShip);

	/** Get Streckengeschäft.
	  * Drop Shipments are sent from the Vendor directly to the Customer
	  */
	public boolean isDropShip();

    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/** Set Berechnete Menge.
	  * Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced);

	/** Get Berechnete Menge.
	  * Is this invoiced?
	  */
	public boolean isInvoiced();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Gedruckt.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Gedruckt.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selektiert	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selektiert	  */
	public boolean isSelected();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Selbstbedienung.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Selbstbedienung.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Verkaufs-Transaktion.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Verkaufs-Transaktion.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/** Set Preis inklusive Steuern.
	  * Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/** Get Preis inklusive Steuern.
	  * Tax is included in the price 
	  */
	public boolean isTaxIncluded();

    /** Column name IsTransferred */
    public static final String COLUMNNAME_IsTransferred = "IsTransferred";

	/** Set Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public void setIsTransferred (boolean IsTransferred);

	/** Get Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public boolean isTransferred();

    /** Column name IsUseBillToAddress */
    public static final String COLUMNNAME_IsUseBillToAddress = "IsUseBillToAddress";

	/** Set Benutze abw. Rechnungsadresse	  */
	public void setIsUseBillToAddress (boolean IsUseBillToAddress);

	/** Get Benutze abw. Rechnungsadresse	  */
	public boolean isUseBillToAddress();

    /** Column name IsUseBPartnerAddress */
    public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/** Set Benutze abw. Adresse	  */
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/** Get Benutze abw. Adresse	  */
	public boolean isUseBPartnerAddress();

    /** Column name Link_Order_ID */
    public static final String COLUMNNAME_Link_Order_ID = "Link_Order_ID";

	/** Set Linked Order.
	  * This field links a sales order to the purchase order that is generated from it.
	  */
	public void setLink_Order_ID (int Link_Order_ID);

	/** Get Linked Order.
	  * This field links a sales order to the purchase order that is generated from it.
	  */
	public int getLink_Order_ID();

	public org.compiere.model.I_C_Order getLink_Order() throws RuntimeException;

	public void setLink_Order(org.compiere.model.I_C_Order Link_Order);

    /** Column name M_FreightCategory_ID */
    public static final String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

	/** Set Fracht-Kategorie.
	  * Category of the Freight
	  */
	public void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/** Get Fracht-Kategorie.
	  * Category of the Freight
	  */
	public int getM_FreightCategory_ID();

	public org.compiere.model.I_M_FreightCategory getM_FreightCategory() throws RuntimeException;

	public void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory);

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Preisliste.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Preisliste.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/** Set Preissystem.
	  * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/** Get Preissystem.
	  * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException;

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/** Set Lieferweg.
	  * Method or manner of product delivery
	  */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/** Get Lieferweg.
	  * Method or manner of product delivery
	  */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException;

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Lager.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column name Orderline_includedTab */
    public static final String COLUMNNAME_Orderline_includedTab = "Orderline_includedTab";

	/** Set Orderline_includedTab	  */
	public void setOrderline_includedTab (java.lang.String Orderline_includedTab);

	/** Get Orderline_includedTab	  */
	public java.lang.String getOrderline_includedTab();

    /** Column name OrderType */
    public static final String COLUMNNAME_OrderType = "OrderType";

	/** Set OrderType	  */
	public void setOrderType (java.lang.String OrderType);

	/** Get OrderType	  */
	public java.lang.String getOrderType();

    /** Column name Pay_BPartner_ID */
    public static final String COLUMNNAME_Pay_BPartner_ID = "Pay_BPartner_ID";

	/** Set Payment BPartner.
	  * Business Partner responsible for the payment
	  */
	public void setPay_BPartner_ID (int Pay_BPartner_ID);

	/** Get Payment BPartner.
	  * Business Partner responsible for the payment
	  */
	public int getPay_BPartner_ID();

    /** Column name Pay_Location_ID */
    public static final String COLUMNNAME_Pay_Location_ID = "Pay_Location_ID";

	/** Set Payment Location.
	  * Location of the Business Partner responsible for the payment
	  */
	public void setPay_Location_ID (int Pay_Location_ID);

	/** Get Payment Location.
	  * Location of the Business Partner responsible for the payment
	  */
	public int getPay_Location_ID();

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Zahlungsweise.
	  * How you pay the invoice
	  */
	public void setPaymentRule (java.lang.String PaymentRule);

	/** Get Zahlungsweise.
	  * How you pay the invoice
	  */
	public java.lang.String getPaymentRule();

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Referenz.
	  * Referenz-Nummer des Kunden
	  */
	public void setPOReference (java.lang.String POReference);

	/** Get Referenz.
	  * Referenz-Nummer des Kunden
	  */
	public java.lang.String getPOReference();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Verbucht.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Verbucht.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/** Set Bereitstellungsdatum	  */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/** Get Bereitstellungsdatum	  */
	public java.sql.Timestamp getPreparationDate();

    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/** Set Priorität.
	  * Priority of a document
	  */
	public void setPriorityRule (java.lang.String PriorityRule);

	/** Get Priorität.
	  * Priority of a document
	  */
	public java.lang.String getPriorityRule();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Verarbeiten	  */
	public void setProcessing (boolean Processing);

	/** Get Verarbeiten	  */
	public boolean isProcessing();

    /** Column name PromotionCode */
    public static final String COLUMNNAME_PromotionCode = "PromotionCode";

	/** Set Promotion Code.
	  * User entered promotion code at sales time
	  */
	public void setPromotionCode (java.lang.String PromotionCode);

	/** Get Promotion Code.
	  * User entered promotion code at sales time
	  */
	public java.lang.String getPromotionCode();

    /** Column name Qty_FastInput */
    public static final String COLUMNNAME_Qty_FastInput = "Qty_FastInput";

	/** Set Menge-Schnelleingabe	  */
	public void setQty_FastInput (java.math.BigDecimal Qty_FastInput);

	/** Get Menge-Schnelleingabe	  */
	public java.math.BigDecimal getQty_FastInput();

    /** Column name receivedvia */
    public static final String COLUMNNAME_receivedvia = "receivedvia";

	/** Set Eingegangen via	  */
	public void setreceivedvia (java.lang.String receivedvia);

	/** Get Eingegangen via	  */
	public java.lang.String getreceivedvia();

    /** Column name Ref_DateOrder */
    public static final String COLUMNNAME_Ref_DateOrder = "Ref_DateOrder";

	/** Set Auftragsdatum  (Ref. Auftrag)	  */
	public void setRef_DateOrder (java.sql.Timestamp Ref_DateOrder);

	/** Get Auftragsdatum  (Ref. Auftrag)	  */
	public java.sql.Timestamp getRef_DateOrder();

    /** Column name Ref_Order_ID */
    public static final String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";

	/** Set Referenced Order.
	  * Reference to corresponding Sales/Purchase Order
	  */
	public void setRef_Order_ID (int Ref_Order_ID);

	/** Get Referenced Order.
	  * Reference to corresponding Sales/Purchase Order
	  */
	public int getRef_Order_ID();

	public org.compiere.model.I_C_Order getRef_Order() throws RuntimeException;

	public void setRef_Order(org.compiere.model.I_C_Order Ref_Order);

    /** Column name Ref_Proposal_ID */
    public static final String COLUMNNAME_Ref_Proposal_ID = "Ref_Proposal_ID";

	/** Set Referenz Angebot/Auftrag.
	  * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	  */
	public void setRef_Proposal_ID (int Ref_Proposal_ID);

	/** Get Referenz Angebot/Auftrag.
	  * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	  */
	public int getRef_Proposal_ID();

	public org.compiere.model.I_C_Order getRef_Proposal() throws RuntimeException;

	public void setRef_Proposal(org.compiere.model.I_C_Order Ref_Proposal);

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Vertriebsbeauftragter.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Vertriebsbeauftragter.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/** Set E-Mail senden.
	  * Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail);

	/** Get E-Mail senden.
	  * Enable sending Document EMail
	  */
	public boolean isSendEMail();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Summe Zeilen.
	  * Total of all document lines
	  */
	public void setTotalLines (java.math.BigDecimal TotalLines);

	/** Get Summe Zeilen.
	  * Total of all document lines
	  */
	public java.math.BigDecimal getTotalLines();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/** Set Nutzer 1.
	  * User defined list element #1
	  */
	public void setUser1_ID (int User1_ID);

	/** Get Nutzer 1.
	  * User defined list element #1
	  */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException;

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/** Set Nutzer 2.
	  * User defined list element #2
	  */
	public void setUser2_ID (int User2_ID);

	/** Get Nutzer 2.
	  * User defined list element #2
	  */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException;

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volumen.
	  * Volume of a product
	  */
	public void setVolume (java.math.BigDecimal Volume);

	/** Get Volumen.
	  * Volume of a product
	  */
	public java.math.BigDecimal getVolume();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Gewicht.
	  * Weight of a product
	  */
	public void setWeight (java.math.BigDecimal Weight);

	/** Get Gewicht.
	  * Weight of a product
	  */
	public java.math.BigDecimal getWeight();
}
