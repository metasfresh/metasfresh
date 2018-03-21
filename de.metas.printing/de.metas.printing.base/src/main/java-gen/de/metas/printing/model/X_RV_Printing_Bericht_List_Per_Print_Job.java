/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Printing_Bericht_List_Per_Print_Job
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Printing_Bericht_List_Per_Print_Job extends org.compiere.model.PO implements I_RV_Printing_Bericht_List_Per_Print_Job, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -417387767L;

    /** Standard Constructor */
    public X_RV_Printing_Bericht_List_Per_Print_Job (Properties ctx, int RV_Printing_Bericht_List_Per_Print_Job_ID, String trxName)
    {
      super (ctx, RV_Printing_Bericht_List_Per_Print_Job_ID, trxName);
      /** if (RV_Printing_Bericht_List_Per_Print_Job_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_RV_Printing_Bericht_List_Per_Print_Job (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Archive getAD_Archive() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	/** Set Archiv.
		@param AD_Archive_ID 
		Archiv für Belege und Berichte
	  */
	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_Value (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	/** Get Archiv.
		@return Archiv für Belege und Berichte
	  */
	@Override
	public int getAD_Archive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Archive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Anschrift-Text.
		@param BPartnerAddress Anschrift-Text	  */
	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	/** Get Anschrift-Text.
		@return Anschrift-Text	  */
	@Override
	public java.lang.String getBPartnerAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
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

	/** Set Firmenname.
		@param Companyname Firmenname	  */
	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_Value (COLUMNNAME_Companyname, Companyname);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	@Override
	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class);
	}

	@Override
	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue)
	{
		set_ValueFromPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class, C_Printing_Queue);
	}

	/** Set Druck-Warteschlangendatensatz.
		@param C_Printing_Queue_ID Druck-Warteschlangendatensatz	  */
	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_Value (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_Value (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	/** Get Druck-Warteschlangendatensatz.
		@return Druck-Warteschlangendatensatz	  */
	@Override
	public int getC_Printing_Queue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Printing_Queue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job getC_Print_Job() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class);
	}

	@Override
	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class, C_Print_Job);
	}

	/** Set Druck-Job.
		@param C_Print_Job_ID Druck-Job	  */
	@Override
	public void setC_Print_Job_ID (int C_Print_Job_ID)
	{
		if (C_Print_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	/** Get Druck-Job.
		@return Druck-Job	  */
	@Override
	public int getC_Print_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_print_job_name.
		@param c_print_job_name c_print_job_name	  */
	@Override
	public void setc_print_job_name (java.lang.String c_print_job_name)
	{
		set_Value (COLUMNNAME_c_print_job_name, c_print_job_name);
	}

	/** Get c_print_job_name.
		@return c_print_job_name	  */
	@Override
	public java.lang.String getc_print_job_name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_c_print_job_name);
	}

	/** Set document.
		@param document document	  */
	@Override
	public void setdocument (java.lang.String document)
	{
		set_Value (COLUMNNAME_document, document);
	}

	/** Get document.
		@return document	  */
	@Override
	public java.lang.String getdocument () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_document);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Vorname.
		@param Firstname 
		Vorname
	  */
	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	/** Get Vorname.
		@return Vorname
	  */
	@Override
	public java.lang.String getFirstname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
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

	/** Set Nachname.
		@param Lastname Nachname	  */
	@Override
	public void setLastname (java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	/** Get Nachname.
		@return Nachname	  */
	@Override
	public java.lang.String getLastname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	/** Set printjob.
		@param printjob printjob	  */
	@Override
	public void setprintjob (java.lang.String printjob)
	{
		set_Value (COLUMNNAME_printjob, printjob);
	}

	/** Get printjob.
		@return printjob	  */
	@Override
	public java.lang.String getprintjob () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_printjob);
	}
}