/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Printing_Bericht_List_Per_Print_Job
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_Printing_Bericht_List_Per_Print_Job extends org.compiere.model.PO implements I_RV_Printing_Bericht_List_Per_Print_Job, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1758524476L;

    /** Standard Constructor */
    public X_RV_Printing_Bericht_List_Per_Print_Job (Properties ctx, int RV_Printing_Bericht_List_Per_Print_Job_ID, String trxName)
    {
      super (ctx, RV_Printing_Bericht_List_Per_Print_Job_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_Printing_Bericht_List_Per_Print_Job (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_Value (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
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
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_Value (COLUMNNAME_Companyname, Companyname);
	}

	@Override
	public java.lang.String getCompanyname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	@Override
	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue()
	{
		return get_ValueAsPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class);
	}

	@Override
	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue)
	{
		set_ValueFromPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class, C_Printing_Queue);
	}

	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_Value (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_Value (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	@Override
	public int getC_Printing_Queue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_ID);
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
			set_Value (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	@Override
	public int getC_Print_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_ID);
	}

	@Override
	public void setc_print_job_name (java.lang.String c_print_job_name)
	{
		set_Value (COLUMNNAME_c_print_job_name, c_print_job_name);
	}

	@Override
	public java.lang.String getc_print_job_name() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_c_print_job_name);
	}

	@Override
	public void setdocument (java.lang.String document)
	{
		set_Value (COLUMNNAME_document, document);
	}

	@Override
	public java.lang.String getdocument() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_document);
	}

	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	@Override
	public void setGrandTotal (java.math.BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
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
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	@Override
	public void setprintjob (java.lang.String printjob)
	{
		set_Value (COLUMNNAME_printjob, printjob);
	}

	@Override
	public java.lang.String getprintjob() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_printjob);
	}
}