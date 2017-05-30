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
/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for ESR_ImportLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ESR_ImportLine extends org.compiere.model.PO implements I_ESR_ImportLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1690947586L;

    /** Standard Constructor */
    public X_ESR_ImportLine (Properties ctx, int ESR_ImportLine_ID, String trxName)
    {
      super (ctx, ESR_ImportLine_ID, trxName);
      /** if (ESR_ImportLine_ID == 0)
        {
			setESR_Document_Status (null);
// N
			setESR_Import_ID (0);
			setESR_ImportLine_ID (0);
			setESR_IsManual_ReferenceNo (true);
// Y
			setESRLineText (null);
			setIsValid (false);
// N
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_ESR_ImportLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Buchungsdatum.
		@param AccountingDate Buchungsdatum	  */
	@Override
	public void setAccountingDate (java.sql.Timestamp AccountingDate)
	{
		set_Value (COLUMNNAME_AccountingDate, AccountingDate);
	}

	/** Get Buchungsdatum.
		@return Buchungsdatum	  */
	@Override
	public java.sql.Timestamp getAccountingDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_AccountingDate);
	}

	/** Set Konto-Nr..
		@param AccountNo 
		Kontonummer
	  */
	@Override
	public void setAccountNo (java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Konto-Nr..
		@return Kontonummer
	  */
	@Override
	public java.lang.String getAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Geschäftspartner-Schlüssel.
		@param BPartner_Value 
		Suchschlüssel für den Geschäftspartner
	  */
	@Override
	public void setBPartner_Value (java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return Suchschlüssel für den Geschäftspartner
	  */
	@Override
	public java.lang.String getBPartner_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartner_Value);
	}

	@Override
	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BankStatementLine_ID, org.compiere.model.I_C_BankStatementLine.class);
	}

	@Override
	public void setC_BankStatementLine(org.compiere.model.I_C_BankStatementLine C_BankStatementLine)
	{
		set_ValueFromPO(COLUMNNAME_C_BankStatementLine_ID, org.compiere.model.I_C_BankStatementLine.class, C_BankStatementLine);
	}

	/** Set Auszugs-Position.
		@param C_BankStatementLine_ID 
		Position auf einem Bankauszug zu dieser Bank
	  */
	@Override
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Auszugs-Position.
		@return Position auf einem Bankauszug zu dieser Bank
	  */
	@Override
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.banking.model.I_C_BankStatementLine_Ref getC_BankStatementLine_Ref() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BankStatementLine_Ref_ID, de.metas.banking.model.I_C_BankStatementLine_Ref.class);
	}

	@Override
	public void setC_BankStatementLine_Ref(de.metas.banking.model.I_C_BankStatementLine_Ref C_BankStatementLine_Ref)
	{
		set_ValueFromPO(COLUMNNAME_C_BankStatementLine_Ref_ID, de.metas.banking.model.I_C_BankStatementLine_Ref.class, C_BankStatementLine_Ref);
	}

	/** Set Bankstatementline Reference.
		@param C_BankStatementLine_Ref_ID Bankstatementline Reference	  */
	@Override
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, Integer.valueOf(C_BankStatementLine_Ref_ID));
	}

	/** Get Bankstatementline Reference.
		@return Bankstatementline Reference	  */
	@Override
	public int getC_BankStatementLine_Ref_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_Ref_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class);
	}

	@Override
	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class, C_Payment);
	}

	/** Set Zahlung.
		@param C_Payment_ID 
		Payment identifier
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Zahlung.
		@return Payment identifier
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.document.refid.model.I_C_ReferenceNo getC_ReferenceNo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ReferenceNo_ID, de.metas.document.refid.model.I_C_ReferenceNo.class);
	}

	@Override
	public void setC_ReferenceNo(de.metas.document.refid.model.I_C_ReferenceNo C_ReferenceNo)
	{
		set_ValueFromPO(COLUMNNAME_C_ReferenceNo_ID, de.metas.document.refid.model.I_C_ReferenceNo.class, C_ReferenceNo);
	}

	/** Set Reference No.
		@param C_ReferenceNo_ID Reference No	  */
	@Override
	public void setC_ReferenceNo_ID (int C_ReferenceNo_ID)
	{
		if (C_ReferenceNo_ID < 1) 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, Integer.valueOf(C_ReferenceNo_ID));
	}

	/** Get Reference No.
		@return Reference No	  */
	@Override
	public int getC_ReferenceNo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ReferenceNo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Bereits zugeordnet.
		@param ESR_Amount_Balance Bereits zugeordnet	  */
	@Override
	public void setESR_Amount_Balance (java.math.BigDecimal ESR_Amount_Balance)
	{
		throw new IllegalArgumentException ("ESR_Amount_Balance is virtual column");	}

	/** Get Bereits zugeordnet.
		@return Bereits zugeordnet	  */
	@Override
	public java.math.BigDecimal getESR_Amount_Balance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Amount_Balance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ESR Rechnungsnummer.
		@param ESR_DocumentNo 
		Belegnummer der zugeordneten Rechnung
	  */
	@Override
	public void setESR_DocumentNo (java.lang.String ESR_DocumentNo)
	{
		set_Value (COLUMNNAME_ESR_DocumentNo, ESR_DocumentNo);
	}

	/** Get ESR Rechnungsnummer.
		@return Belegnummer der zugeordneten Rechnung
	  */
	@Override
	public java.lang.String getESR_DocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_DocumentNo);
	}

	/** 
	 * ESR_Document_Status AD_Reference_ID=540375
	 * Reference name: ESR_Document_Statuses
	 */
	public static final int ESR_DOCUMENT_STATUS_AD_Reference_ID=540375;
	/** TotallyMatched = T */
	public static final String ESR_DOCUMENT_STATUS_TotallyMatched = "T";
	/** PartiallyMatched = P */
	public static final String ESR_DOCUMENT_STATUS_PartiallyMatched = "P";
	/** NotMatched = N */
	public static final String ESR_DOCUMENT_STATUS_NotMatched = "N";
	/** Set Importstatus.
		@param ESR_Document_Status Importstatus	  */
	@Override
	public void setESR_Document_Status (java.lang.String ESR_Document_Status)
	{

		set_Value (COLUMNNAME_ESR_Document_Status, ESR_Document_Status);
	}

	/** Get Importstatus.
		@return Importstatus	  */
	@Override
	public java.lang.String getESR_Document_Status () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_Document_Status);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	/** Set ESR_Import.
		@param ESR_Import_ID ESR_Import	  */
	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	/** Get ESR_Import.
		@return ESR_Import	  */
	@Override
	public int getESR_Import_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_Import_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ESR_ImportLine.
		@param ESR_ImportLine_ID ESR_ImportLine	  */
	@Override
	public void setESR_ImportLine_ID (int ESR_ImportLine_ID)
	{
		if (ESR_ImportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, Integer.valueOf(ESR_ImportLine_ID));
	}

	/** Get ESR_ImportLine.
		@return ESR_ImportLine	  */
	@Override
	public int getESR_ImportLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_ImportLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungsbetrag.
		@param ESR_Invoice_Grandtotal 
		Gesamtbetrag der zugeordneten Rechnung
	  */
	@Override
	public void setESR_Invoice_Grandtotal (java.math.BigDecimal ESR_Invoice_Grandtotal)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Grandtotal, ESR_Invoice_Grandtotal);
	}

	/** Get Rechnungsbetrag.
		@return Gesamtbetrag der zugeordneten Rechnung
	  */
	@Override
	public java.math.BigDecimal getESR_Invoice_Grandtotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Invoice_Grandtotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Offener Rechnungsbetrag.
		@param ESR_Invoice_Openamt 
		Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	  */
	@Override
	public void setESR_Invoice_Openamt (java.math.BigDecimal ESR_Invoice_Openamt)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Openamt, ESR_Invoice_Openamt);
	}

	/** Get Offener Rechnungsbetrag.
		@return Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	  */
	@Override
	public java.math.BigDecimal getESR_Invoice_Openamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Invoice_Openamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Extern erstellte ESR-Referenz.
		@param ESR_IsManual_ReferenceNo Extern erstellte ESR-Referenz	  */
	@Override
	public void setESR_IsManual_ReferenceNo (boolean ESR_IsManual_ReferenceNo)
	{
		set_Value (COLUMNNAME_ESR_IsManual_ReferenceNo, Boolean.valueOf(ESR_IsManual_ReferenceNo));
	}

	/** Get Extern erstellte ESR-Referenz.
		@return Extern erstellte ESR-Referenz	  */
	@Override
	public boolean isESR_IsManual_ReferenceNo () 
	{
		Object oo = get_Value(COLUMNNAME_ESR_IsManual_ReferenceNo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Importierte ESR-Zeile.
		@param ESRLineText 
		ESR complete line text
	  */
	@Override
	public void setESRLineText (java.lang.String ESRLineText)
	{
		set_Value (COLUMNNAME_ESRLineText, ESRLineText);
	}

	/** Get Importierte ESR-Zeile.
		@return ESR complete line text
	  */
	@Override
	public java.lang.String getESRLineText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRLineText);
	}

	/** 
	 * ESR_Payment_Action AD_Reference_ID=540386
	 * Reference name: ESR_Payment_Action_List
	 */
	public static final int ESR_PAYMENT_ACTION_AD_Reference_ID=540386;
	/** Money_Was_Transfered_Back_to_Partner = B */
	public static final String ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner = "B";
	/** Allocate_Payment_With_Next_Invoice = N */
	public static final String ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice = "N";
	/** Write_Off_Amount = W */
	public static final String ESR_PAYMENT_ACTION_Write_Off_Amount = "W";
	/** Keep_For_Dunning = D */
	public static final String ESR_PAYMENT_ACTION_Keep_For_Dunning = "D";
	/** Fit_Amounts = F */
	public static final String ESR_PAYMENT_ACTION_Fit_Amounts = "F";
	/** Unable_To_Assign_Income = E */
	public static final String ESR_PAYMENT_ACTION_Unable_To_Assign_Income = "E";
	/** Control_Line = C */
	public static final String ESR_PAYMENT_ACTION_Control_Line = "C";
	/** Allocate_Payment_With_Current_Invoice = A */
	public static final String ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice = "A";
	/** Reverse_Booking = R */
	public static final String ESR_PAYMENT_ACTION_Reverse_Booking = "R";
	/** Set ESR_Payment_Action.
		@param ESR_Payment_Action ESR_Payment_Action	  */
	@Override
	public void setESR_Payment_Action (java.lang.String ESR_Payment_Action)
	{

		set_Value (COLUMNNAME_ESR_Payment_Action, ESR_Payment_Action);
	}

	/** Get ESR_Payment_Action.
		@return ESR_Payment_Action	  */
	@Override
	public java.lang.String getESR_Payment_Action () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_Payment_Action);
	}

	/** Set Post-Teilnehmernummer.
		@param ESRPostParticipantNumber 
		Post-Teilnehmernummer
	  */
	@Override
	public void setESRPostParticipantNumber (java.lang.String ESRPostParticipantNumber)
	{
		set_Value (COLUMNNAME_ESRPostParticipantNumber, ESRPostParticipantNumber);
	}

	/** Get Post-Teilnehmernummer.
		@return Post-Teilnehmernummer
	  */
	@Override
	public java.lang.String getESRPostParticipantNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRPostParticipantNumber);
	}

	/** Set ESR Referenznummer.
		@param ESRReferenceNumber 
		Referenznummer inkl. bankinterne Teilnehmernummer
	  */
	@Override
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	/** Get ESR Referenznummer.
		@return Referenznummer inkl. bankinterne Teilnehmernummer
	  */
	@Override
	public java.lang.String getESRReferenceNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRReferenceNumber);
	}

	/** Set Transaktionsart.
		@param ESRTrxType Transaktionsart	  */
	@Override
	public void setESRTrxType (java.lang.String ESRTrxType)
	{
		set_Value (COLUMNNAME_ESRTrxType, ESRTrxType);
	}

	/** Get Transaktionsart.
		@return Transaktionsart	  */
	@Override
	public java.lang.String getESRTrxType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRTrxType);
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gültig.
		@param IsValid 
		Element ist gültig
	  */
	@Override
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Gültig.
		@return Element ist gültig
	  */
	@Override
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Position.
		@param LineNo 
		Zeile Nr.
	  */
	@Override
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Position.
		@return Zeile Nr.
	  */
	@Override
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Org getOrg() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Org_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setOrg(org.compiere.model.I_AD_Org Org)
	{
		set_ValueFromPO(COLUMNNAME_Org_ID, org.compiere.model.I_AD_Org.class, Org);
	}

	/** Set Organisation.
		@param Org_ID 
		Organisatorische Einheit des Mandanten
	  */
	@Override
	public void setOrg_ID (int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Integer.valueOf(Org_ID));
	}

	/** Get Organisation.
		@return Organisatorische Einheit des Mandanten
	  */
	@Override
	public int getOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahldatum.
		@param PaymentDate Zahldatum	  */
	@Override
	public void setPaymentDate (java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	/** Get Zahldatum.
		@return Zahldatum	  */
	@Override
	public java.sql.Timestamp getPaymentDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PaymentDate);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ESR Sektion.
		@param SektionNo ESR Sektion	  */
	@Override
	public void setSektionNo (java.lang.String SektionNo)
	{
		set_Value (COLUMNNAME_SektionNo, SektionNo);
	}

	/** Get ESR Sektion.
		@return ESR Sektion	  */
	@Override
	public java.lang.String getSektionNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SektionNo);
	}
}