// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_OrderLine_HU_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_OrderLine_HU_Candidate extends org.compiere.model.PO implements I_DD_OrderLine_HU_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 368342671L;

    /** Standard Constructor */
    public X_DD_OrderLine_HU_Candidate (final Properties ctx, final int DD_OrderLine_HU_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, DD_OrderLine_HU_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_OrderLine_HU_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDD_OrderLine_HU_Candidate_ID (final int DD_OrderLine_HU_Candidate_ID)
	{
		if (DD_OrderLine_HU_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_HU_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_HU_Candidate_ID, DD_OrderLine_HU_Candidate_ID);
	}

	@Override
	public int getDD_OrderLine_HU_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_HU_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}
}