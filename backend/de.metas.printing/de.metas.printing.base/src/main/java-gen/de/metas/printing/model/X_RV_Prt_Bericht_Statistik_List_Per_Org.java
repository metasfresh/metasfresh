/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Prt_Bericht_Statistik_List_Per_Org
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Prt_Bericht_Statistik_List_Per_Org extends org.compiere.model.PO implements I_RV_Prt_Bericht_Statistik_List_Per_Org, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2010361134L;

    /** Standard Constructor */
    public X_RV_Prt_Bericht_Statistik_List_Per_Org (Properties ctx, int RV_Prt_Bericht_Statistik_List_Per_Org_ID, String trxName)
    {
      super (ctx, RV_Prt_Bericht_Statistik_List_Per_Org_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_Prt_Bericht_Statistik_List_Per_Org (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_ValueNoCheck (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_ValueNoCheck (COLUMNNAME_Companyname, Companyname);
	}

	@Override
	public java.lang.String getCompanyname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job getC_Print_Job()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class);
	}

	@Override
	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_ID, de.metas.printing.model.I_C_Print_Job.class, C_Print_Job);
	}

	@Override
	public void setC_Print_Job_ID (int C_Print_Job_ID)
	{
		if (C_Print_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	@Override
	public int getC_Print_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class);
	}

	@Override
	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class, C_Print_Job_Instructions);
	}

	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	@Override
	public int getC_Print_Job_Instructions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Instructions_ID);
	}

	@Override
	public void setc_print_job_name (java.lang.String c_print_job_name)
	{
		set_ValueNoCheck (COLUMNNAME_c_print_job_name, c_print_job_name);
	}

	@Override
	public java.lang.String getc_print_job_name() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_c_print_job_name);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	@Override
	public int getC_Print_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Package_ID);
	}

	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setdocument (java.lang.String document)
	{
		set_ValueNoCheck (COLUMNNAME_document, document);
	}

	@Override
	public java.lang.String getdocument() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_document);
	}

	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_ValueNoCheck (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	@Override
	public void setGrandTotal (java.math.BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public java.math.BigDecimal getGrandTotal() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLastname (java.lang.String Lastname)
	{
		set_ValueNoCheck (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	@Override
	public void setprintjob (java.lang.String printjob)
	{
		set_ValueNoCheck (COLUMNNAME_printjob, printjob);
	}

	@Override
	public java.lang.String getprintjob() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_printjob);
	}
}