/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Prt_Bericht_Statistik_List_Per_Org
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Prt_Bericht_Statistik_List_Per_Org extends org.compiere.model.PO implements I_RV_Prt_Bericht_Statistik_List_Per_Org, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1216426387L;

    /** Standard Constructor */
    public X_RV_Prt_Bericht_Statistik_List_Per_Org (Properties ctx, int RV_Prt_Bericht_Statistik_List_Per_Org_ID, String trxName)
    {
      super (ctx, RV_Prt_Bericht_Statistik_List_Per_Org_ID, trxName);
      /** if (RV_Prt_Bericht_Statistik_List_Per_Org_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_RV_Prt_Bericht_Statistik_List_Per_Org (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Straße und Nr..
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_ValueNoCheck (COLUMNNAME_Address1, Address1);
	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Anschrift-Text.
		@param BPartnerAddress Anschrift-Text	  */
	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress, BPartnerAddress);
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

	/** Set Firmenname.
		@param Companyname Firmenname	  */
	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_ValueNoCheck (COLUMNNAME_Companyname, Companyname);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
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
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
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

	@Override
	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class);
	}

	@Override
	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class, C_Print_Job_Instructions);
	}

	/** Set Druck-Job Anweisung.
		@param C_Print_Job_Instructions_ID Druck-Job Anweisung	  */
	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	/** Get Druck-Job Anweisung.
		@return Druck-Job Anweisung	  */
	@Override
	public int getC_Print_Job_Instructions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Job_Instructions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_print_job_name.
		@param c_print_job_name c_print_job_name	  */
	@Override
	public void setc_print_job_name (java.lang.String c_print_job_name)
	{
		set_ValueNoCheck (COLUMNNAME_c_print_job_name, c_print_job_name);
	}

	/** Get c_print_job_name.
		@return c_print_job_name	  */
	@Override
	public java.lang.String getc_print_job_name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_c_print_job_name);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	/** Set Druckpaket.
		@param C_Print_Package_ID Druckpaket	  */
	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	/** Get Druckpaket.
		@return Druckpaket	  */
	@Override
	public int getC_Print_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Package_ID);
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
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Rechnungsdatum.
		@return Datum auf der Rechnung
	  */
	@Override
	public java.sql.Timestamp getDateInvoiced () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set document.
		@param document document	  */
	@Override
	public void setdocument (java.lang.String document)
	{
		set_ValueNoCheck (COLUMNNAME_document, document);
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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
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
		set_ValueNoCheck (COLUMNNAME_Firstname, Firstname);
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
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
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
		set_ValueNoCheck (COLUMNNAME_Lastname, Lastname);
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
		set_ValueNoCheck (COLUMNNAME_printjob, printjob);
	}

	/** Get printjob.
		@return printjob	  */
	@Override
	public java.lang.String getprintjob () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_printjob);
	}
}