/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate_HeaderAggregation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate_HeaderAggregation extends org.compiere.model.PO implements I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -926257439L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (Properties ctx, int C_Invoice_Candidate_HeaderAggregation_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_HeaderAggregation_ID, trxName);
      /** if (C_Invoice_Candidate_HeaderAggregation_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Invoice_Candidate_HeaderAggregation_ID (0);
			setHeaderAggregationKey (null);
			setInvoicingGroupNo (0);
			setIsSOTrx (false);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Abrechnungsgruppe.
		@param C_Invoice_Candidate_HeaderAggregation_ID Abrechnungsgruppe	  */
	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, Integer.valueOf(C_Invoice_Candidate_HeaderAggregation_ID));
	}

	/** Get Abrechnungsgruppe.
		@return Abrechnungsgruppe	  */
	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kopf-Aggregationsmerkmal.
		@param HeaderAggregationKey Kopf-Aggregationsmerkmal	  */
	@Override
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey)
	{
		set_ValueNoCheck (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	/** Get Kopf-Aggregationsmerkmal.
		@return Kopf-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getHeaderAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey);
	}

	/** Set Header aggregation builder.
		@param HeaderAggregationKeyBuilder_ID Header aggregation builder	  */
	@Override
	public void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID)
	{
		if (HeaderAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, Integer.valueOf(HeaderAggregationKeyBuilder_ID));
	}

	/** Get Header aggregation builder.
		@return Header aggregation builder	  */
	@Override
	public int getHeaderAggregationKeyBuilder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeaderAggregationKeyBuilder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abrechnungsgruppe.
		@param InvoicingGroupNo 
		Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	  */
	@Override
	public void setInvoicingGroupNo (int InvoicingGroupNo)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicingGroupNo, Integer.valueOf(InvoicingGroupNo));
	}

	/** Get Abrechnungsgruppe.
		@return Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	  */
	@Override
	public int getInvoicingGroupNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoicingGroupNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
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
}