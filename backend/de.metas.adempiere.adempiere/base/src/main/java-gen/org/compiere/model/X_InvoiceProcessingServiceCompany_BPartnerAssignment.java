/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_InvoiceProcessingServiceCompany_BPartnerAssignment extends org.compiere.model.PO implements I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1282298445L;

    /** Standard Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (Properties ctx, int InvoiceProcessingServiceCompany_BPartnerAssignment_ID, String trxName)
    {
      super (ctx, InvoiceProcessingServiceCompany_BPartnerAssignment_ID, trxName);
      /** if (InvoiceProcessingServiceCompany_BPartnerAssignment_ID == 0)
        {
			setC_BPartner_ID (0);
			setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (0);
			setInvoiceProcessingServiceCompany_ID (0);
        } */
    }

    /** Load Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Invoice Processing Service Company BPartner Assignment.
		@param InvoiceProcessingServiceCompany_BPartnerAssignment_ID Invoice Processing Service Company BPartner Assignment	  */
	@Override
	public void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (int InvoiceProcessingServiceCompany_BPartnerAssignment_ID)
	{
		if (InvoiceProcessingServiceCompany_BPartnerAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, Integer.valueOf(InvoiceProcessingServiceCompany_BPartnerAssignment_ID));
	}

	/** Get Invoice Processing Service Company BPartner Assignment.
		@return Invoice Processing Service Company BPartner Assignment	  */
	@Override
	public int getInvoiceProcessingServiceCompany_BPartnerAssignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_InvoiceProcessingServiceCompany getInvoiceProcessingServiceCompany()
	{
		return get_ValueAsPO(COLUMNNAME_InvoiceProcessingServiceCompany_ID, org.compiere.model.I_InvoiceProcessingServiceCompany.class);
	}

	@Override
	public void setInvoiceProcessingServiceCompany(org.compiere.model.I_InvoiceProcessingServiceCompany InvoiceProcessingServiceCompany)
	{
		set_ValueFromPO(COLUMNNAME_InvoiceProcessingServiceCompany_ID, org.compiere.model.I_InvoiceProcessingServiceCompany.class, InvoiceProcessingServiceCompany);
	}

	/** Set Invoice Processing Service Company.
		@param InvoiceProcessingServiceCompany_ID Invoice Processing Service Company	  */
	@Override
	public void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID)
	{
		if (InvoiceProcessingServiceCompany_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, Integer.valueOf(InvoiceProcessingServiceCompany_ID));
	}

	/** Get Invoice Processing Service Company.
		@return Invoice Processing Service Company	  */
	@Override
	public int getInvoiceProcessingServiceCompany_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceProcessingServiceCompany_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}