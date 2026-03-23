// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Invoice_Candidate_Recompute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Candidate_Recompute extends org.compiere.model.PO implements I_C_Invoice_Candidate_Recompute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1975136356L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Recompute (final Properties ctx, final int C_Invoice_Candidate_Recompute_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Candidate_Recompute_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_Recompute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public I_C_Invoice_Candidate getC_Invoice_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.class);
	}

	@Override
	public void setC_Invoice_Candidate(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.class, C_Invoice_Candidate);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Recompute_ID (final int C_Invoice_Candidate_Recompute_ID)
	{
		if (C_Invoice_Candidate_Recompute_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Recompute_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Recompute_ID, C_Invoice_Candidate_Recompute_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Recompute_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Recompute_ID);
	}

	@Override
	public void setChunkUUID (final @Nullable String ChunkUUID)
	{
		set_Value (COLUMNNAME_ChunkUUID, ChunkUUID);
	}

	@Override
	public String getChunkUUID()
	{
		return get_ValueAsString(COLUMNNAME_ChunkUUID);
	}
}