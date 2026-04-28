// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_QtyDetails
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Candidate_QtyDetails extends org.compiere.model.PO implements I_MD_Candidate_QtyDetails, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1818023286L;

    /** Standard Constructor */
    public X_MD_Candidate_QtyDetails (final Properties ctx, final int MD_Candidate_QtyDetails_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Candidate_QtyDetails_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_QtyDetails (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDetail_MD_Candidate_ID (final int Detail_MD_Candidate_ID)
	{
		if (Detail_MD_Candidate_ID < 1) 
			set_Value (COLUMNNAME_Detail_MD_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_Detail_MD_Candidate_ID, Detail_MD_Candidate_ID);
	}

	@Override
	public int getDetail_MD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Detail_MD_Candidate_ID);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(final de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	@Override
	public void setMD_Candidate_ID (final int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_Value (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public void setMD_Candidate_QtyDetails_ID (final int MD_Candidate_QtyDetails_ID)
	{
		if (MD_Candidate_QtyDetails_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_QtyDetails_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_QtyDetails_ID, MD_Candidate_QtyDetails_ID);
	}

	@Override
	public int getMD_Candidate_QtyDetails_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_QtyDetails_ID);
	}

	@Override
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setStock_MD_Candidate_ID (final int Stock_MD_Candidate_ID)
	{
		if (Stock_MD_Candidate_ID < 1) 
			set_Value (COLUMNNAME_Stock_MD_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_Stock_MD_Candidate_ID, Stock_MD_Candidate_ID);
	}

	@Override
	public int getStock_MD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Stock_MD_Candidate_ID);
	}
}