/** Generated Model - DO NOT CHANGE */
package de.metas.dunning.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Dunning_Candidate_Invoice_v1
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_C_Dunning_Candidate_Invoice_v1 extends org.compiere.model.PO implements I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1876105953L;

	/** Standard Constructor */
	public X_C_Dunning_Candidate_Invoice_v1 (Properties ctx, int C_Dunning_Candidate_Invoice_v1_ID, String trxName)
	{
		super (ctx, C_Dunning_Candidate_Invoice_v1_ID, trxName);
		/** if (C_Dunning_Candidate_Invoice_v1_ID == 0)
		 {
		 } */
	}

	/** Load Constructor */
	public X_C_Dunning_Candidate_Invoice_v1 (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
	 @param AD_User_ID
	 User within the system - Internal or Business Partner Contact
	 */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
	 @return User within the system - Internal or Business Partner Contact
	 */
	@Override
	public int getAD_User_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
	 @param C_BPartner_Location_ID
	 Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
	 @return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 */
	@Override
	public int getC_BPartner_Location_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
	 @param C_Currency_ID
	 Die Währung für diesen Eintrag
	 */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
	 @return Die Währung für diesen Eintrag
	 */
	@Override
	public int getC_Currency_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	/** Set Mahnung.
	 @param C_Dunning_ID
	 Dunning Rules for overdue invoices
	 */
	@Override
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1)
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else
			set_Value (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Mahnung.
	 @return Dunning Rules for overdue invoices
	 */
	@Override
	public int getC_Dunning_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
	public org.compiere.model.I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoicePaySchedule_ID, org.compiere.model.I_C_InvoicePaySchedule.class);
	}

	@Override
	public void setC_InvoicePaySchedule(org.compiere.model.I_C_InvoicePaySchedule C_InvoicePaySchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoicePaySchedule_ID, org.compiere.model.I_C_InvoicePaySchedule.class, C_InvoicePaySchedule);
	}

	/** Set Zahlungsplan.
	 @param C_InvoicePaySchedule_ID
	 Zahlungsplan
	 */
	@Override
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, Integer.valueOf(C_InvoicePaySchedule_ID));
	}

	/** Get Zahlungsplan.
	 @return Zahlungsplan
	 */
	@Override
	public int getC_InvoicePaySchedule_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePaySchedule_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungsbedingung.
	 @param C_PaymentTerm_ID
	 Die Bedingungen für die Bezahlung dieses Vorgangs
	 */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1)
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
	 @return Die Bedingungen für die Bezahlung dieses Vorgangs
	 */
	@Override
	public int getC_PaymentTerm_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Rechnungsdatum.
	 @param DateInvoiced
	 Datum auf der Rechnung
	 */
	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Rechnungsdatum.
	 @return Datum auf der Rechnung
	 */
	@Override
	public java.sql.Timestamp getDateInvoiced ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Datum Fälligkeit.
	 @param DueDate
	 Datum, zu dem Zahlung fällig wird
	 */
	@Override
	public void setDueDate (java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Datum Fälligkeit.
	 @return Datum, zu dem Zahlung fällig wird
	 */
	@Override
	public java.sql.Timestamp getDueDate ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Dunning Grace Date.
	 @param DunningGrace Dunning Grace Date	  */
	@Override
	public void setDunningGrace (java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace Date.
	 @return Dunning Grace Date	  */
	@Override
	public java.sql.Timestamp getDunningGrace ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningGrace);
	}

	/** Set Summe Gesamt.
	 @param GrandTotal
	 Summe über Alles zu diesem Beleg
	 */
	@Override
	public void setGrandTotal (java.math.BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Summe Gesamt.
	 @return Summe über Alles zu diesem Beleg
	 */
	@Override
	public java.math.BigDecimal getGrandTotal ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			return BigDecimal.ZERO;
		return bd;
	}

	/** Set In Dispute.
	 @param IsInDispute
	 Document is in dispute
	 */
	@Override
	public void setIsInDispute (boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, Boolean.valueOf(IsInDispute));
	}

	/** Get In Dispute.
	 @return Document is in dispute
	 */
	@Override
	public boolean isInDispute ()
	{
		Object oo = get_Value(COLUMNNAME_IsInDispute);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	/** Set Offener Betrag.
	 @param OpenAmt Offener Betrag	  */
	@Override
	public void setOpenAmt (java.math.BigDecimal OpenAmt)
	{
		set_Value (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Offener Betrag.
	 @return Offener Betrag	  */
	@Override
	public java.math.BigDecimal getOpenAmt ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
			return BigDecimal.ZERO;
		return bd;
	}
}