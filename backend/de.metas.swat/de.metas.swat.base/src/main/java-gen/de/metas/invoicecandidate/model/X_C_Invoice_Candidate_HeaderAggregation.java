// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Invoice_Candidate_HeaderAggregation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Candidate_HeaderAggregation extends org.compiere.model.PO implements I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1847450598L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (final Properties ctx, final int C_Invoice_Candidate_HeaderAggregation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Candidate_HeaderAggregation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_ID (final int C_Invoice_Candidate_HeaderAggregation_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, C_Invoice_Candidate_HeaderAggregation_ID);
	}

	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID);
	}

	@Override
	public void setHeaderAggregationKey (final java.lang.String HeaderAggregationKey)
	{
		set_ValueNoCheck (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	@Override
	public java.lang.String getHeaderAggregationKey() 
	{
		return get_ValueAsString(COLUMNNAME_HeaderAggregationKey);
	}

	@Override
	public void setHeaderAggregationKeyBuilder_ID (final int HeaderAggregationKeyBuilder_ID)
	{
		if (HeaderAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, HeaderAggregationKeyBuilder_ID);
	}

	@Override
	public int getHeaderAggregationKeyBuilder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HeaderAggregationKeyBuilder_ID);
	}

	@Override
	public void setInvoicingGroupNo (final int InvoicingGroupNo)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicingGroupNo, InvoicingGroupNo);
	}

	@Override
	public int getInvoicingGroupNo() 
	{
		return get_ValueAsInt(COLUMNNAME_InvoicingGroupNo);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}