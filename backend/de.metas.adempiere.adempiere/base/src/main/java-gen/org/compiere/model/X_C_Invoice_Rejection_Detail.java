/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Rejection_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Rejection_Detail extends org.compiere.model.PO implements I_C_Invoice_Rejection_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1520787047L;

    /** Standard Constructor */
    public X_C_Invoice_Rejection_Detail (Properties ctx, int C_Invoice_Rejection_Detail_ID, String trxName)
    {
      super (ctx, C_Invoice_Rejection_Detail_ID, trxName);
      /** if (C_Invoice_Rejection_Detail_ID == 0)
        {
			setC_Invoice_Rejection_Detail_ID (0);
			setIsDone (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Rejection_Detail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zurückweisungsdetail.
		@param C_Invoice_Rejection_Detail_ID Zurückweisungsdetail	  */
	@Override
	public void setC_Invoice_Rejection_Detail_ID (int C_Invoice_Rejection_Detail_ID)
	{
		if (C_Invoice_Rejection_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Rejection_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Rejection_Detail_ID, Integer.valueOf(C_Invoice_Rejection_Detail_ID));
	}

	/** Get Zurückweisungsdetail.
		@return Zurückweisungsdetail	  */
	@Override
	public int getC_Invoice_Rejection_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Rejection_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Klient/in.
		@param Client Klient/in	  */
	@Override
	public void setClient (java.lang.String Client)
	{
		set_Value (COLUMNNAME_Client, Client);
	}

	/** Get Klient/in.
		@return Klient/in	  */
	@Override
	public java.lang.String getClient () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Client);
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Erklärung.
		@param Explanation Erklärung	  */
	@Override
	public void setExplanation (java.lang.String Explanation)
	{
		set_Value (COLUMNNAME_Explanation, Explanation);
	}

	/** Get Erklärung.
		@return Erklärung	  */
	@Override
	public java.lang.String getExplanation () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Explanation);
	}

	/** Set Rechnung Nr.
		@param InvoiceNumber Rechnung Nr	  */
	@Override
	public void setInvoiceNumber (java.lang.String InvoiceNumber)
	{
		set_Value (COLUMNNAME_InvoiceNumber, InvoiceNumber);
	}

	/** Get Rechnung Nr.
		@return Rechnung Nr	  */
	@Override
	public java.lang.String getInvoiceNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceNumber);
	}

	/** Set Rechnungsempfänger.
		@param InvoiceRecipient Rechnungsempfänger	  */
	@Override
	public void setInvoiceRecipient (java.lang.String InvoiceRecipient)
	{
		set_Value (COLUMNNAME_InvoiceRecipient, InvoiceRecipient);
	}

	/** Get Rechnungsempfänger.
		@return Rechnungsempfänger	  */
	@Override
	public java.lang.String getInvoiceRecipient () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRecipient);
	}

	/** Set Erledigt.
		@param IsDone Erledigt	  */
	@Override
	public void setIsDone (boolean IsDone)
	{
		set_Value (COLUMNNAME_IsDone, Boolean.valueOf(IsDone));
	}

	/** Get Erledigt.
		@return Erledigt	  */
	@Override
	public boolean isDone () 
	{
		Object oo = get_Value(COLUMNNAME_IsDone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Telefon.
		@param Phone 
		Beschreibt eine Telefon Nummer
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Telefon.
		@return Beschreibt eine Telefon Nummer
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Grund.
		@param Reason Grund	  */
	@Override
	public void setReason (java.lang.String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Grund.
		@return Grund	  */
	@Override
	public java.lang.String getReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Reason);
	}

	/** Set Sachbearbeiter.
		@param ResponsiblePerson Sachbearbeiter	  */
	@Override
	public void setResponsiblePerson (java.lang.String ResponsiblePerson)
	{
		set_Value (COLUMNNAME_ResponsiblePerson, ResponsiblePerson);
	}

	/** Get Sachbearbeiter.
		@return Sachbearbeiter	  */
	@Override
	public java.lang.String getResponsiblePerson () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponsiblePerson);
	}

	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	@Override
	public void setDateReceived (final @Nullable java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	@Override
	public java.sql.Timestamp getDateReceived()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateReceived);
	}
}