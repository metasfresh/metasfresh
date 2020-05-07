/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for InvoiceProcessingServiceCompany
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_InvoiceProcessingServiceCompany extends org.compiere.model.PO implements I_InvoiceProcessingServiceCompany, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -352004575L;

    /** Standard Constructor */
    public X_InvoiceProcessingServiceCompany (Properties ctx, int InvoiceProcessingServiceCompany_ID, String trxName)
    {
      super (ctx, InvoiceProcessingServiceCompany_ID, trxName);
      /** if (InvoiceProcessingServiceCompany_ID == 0)
        {
			setFeePercentageOfGrandTotal (BigDecimal.ZERO);
			setInvoiceProcessingServiceCompany_ID (0);
			setServiceCompany_BPartner_ID (0);
			setServiceFee_Product_ID (0);
			setServiceInvoice_DocType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_InvoiceProcessingServiceCompany (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Fee Percentage of Invoice Grand Total.
		@param FeePercentageOfGrandTotal Fee Percentage of Invoice Grand Total	  */
	@Override
	public void setFeePercentageOfGrandTotal (java.math.BigDecimal FeePercentageOfGrandTotal)
	{
		set_Value (COLUMNNAME_FeePercentageOfGrandTotal, FeePercentageOfGrandTotal);
	}

	/** Get Fee Percentage of Invoice Grand Total.
		@return Fee Percentage of Invoice Grand Total	  */
	@Override
	public java.math.BigDecimal getFeePercentageOfGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FeePercentageOfGrandTotal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Service Company.
		@param ServiceCompany_BPartner_ID Service Company	  */
	@Override
	public void setServiceCompany_BPartner_ID (int ServiceCompany_BPartner_ID)
	{
		if (ServiceCompany_BPartner_ID < 1) 
			set_Value (COLUMNNAME_ServiceCompany_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceCompany_BPartner_ID, Integer.valueOf(ServiceCompany_BPartner_ID));
	}

	/** Get Service Company.
		@return Service Company	  */
	@Override
	public int getServiceCompany_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ServiceCompany_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Service Fee Product.
		@param ServiceFee_Product_ID Service Fee Product	  */
	@Override
	public void setServiceFee_Product_ID (int ServiceFee_Product_ID)
	{
		if (ServiceFee_Product_ID < 1) 
			set_Value (COLUMNNAME_ServiceFee_Product_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceFee_Product_ID, Integer.valueOf(ServiceFee_Product_ID));
	}

	/** Get Service Fee Product.
		@return Service Fee Product	  */
	@Override
	public int getServiceFee_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ServiceFee_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Service Invoice Doc Type.
		@param ServiceInvoice_DocType_ID Service Invoice Doc Type	  */
	@Override
	public void setServiceInvoice_DocType_ID (int ServiceInvoice_DocType_ID)
	{
		if (ServiceInvoice_DocType_ID < 1) 
			set_Value (COLUMNNAME_ServiceInvoice_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceInvoice_DocType_ID, Integer.valueOf(ServiceInvoice_DocType_ID));
	}

	/** Get Service Invoice Doc Type.
		@return Service Invoice Doc Type	  */
	@Override
	public int getServiceInvoice_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ServiceInvoice_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}