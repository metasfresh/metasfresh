// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Invoice_Candidate_Agg
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Candidate_Agg extends org.compiere.model.PO implements I_C_Invoice_Candidate_Agg, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1212598553L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Agg (final Properties ctx, final int C_Invoice_Candidate_Agg_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Candidate_Agg_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_Agg (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Agg_ID (final int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Agg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public void setClassname (final @Nullable java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return get_ValueAsString(COLUMNNAME_Classname);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class);
	}

	@Override
	public void setM_ProductGroup(final de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class, M_ProductGroup);
	}

	@Override
	public void setM_ProductGroup_ID (final int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductGroup_ID, M_ProductGroup_ID);
	}

	@Override
	public int getM_ProductGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductGroup_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}