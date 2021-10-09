// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Transaction_Detail
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_MD_Candidate_Transaction_Detail extends org.compiere.model.PO implements I_MD_Candidate_Transaction_Detail, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -229618430L;

	/** Standard Constructor */
	public X_MD_Candidate_Transaction_Detail (final Properties ctx, final int MD_Candidate_Transaction_Detail_ID, @Nullable final String trxName)
	{
		super (ctx, MD_Candidate_Transaction_Detail_ID, trxName);
	}

	/** Load Constructor */
	public X_MD_Candidate_Transaction_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance_ResetStock()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ResetStock_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance_ResetStock(final org.compiere.model.I_AD_PInstance AD_PInstance_ResetStock)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ResetStock_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance_ResetStock);
	}

	@Override
	public void setAD_PInstance_ResetStock_ID (final int AD_PInstance_ResetStock_ID)
	{
		if (AD_PInstance_ResetStock_ID < 1)
			set_Value (COLUMNNAME_AD_PInstance_ResetStock_ID, null);
		else
			set_Value (COLUMNNAME_AD_PInstance_ResetStock_ID, AD_PInstance_ResetStock_ID);
	}

	@Override
	public int getAD_PInstance_ResetStock_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ResetStock_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		throw new IllegalArgumentException ("M_InOutLine_ID is virtual column");	}

	@Override
	public int getM_InOutLine_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public org.compiere.model.I_M_Transaction getM_Transaction()
	{
		return get_ValueAsPO(COLUMNNAME_M_Transaction_ID, org.compiere.model.I_M_Transaction.class);
	}

	@Override
	public void setM_Transaction(final org.compiere.model.I_M_Transaction M_Transaction)
	{
		set_ValueFromPO(COLUMNNAME_M_Transaction_ID, org.compiere.model.I_M_Transaction.class, M_Transaction);
	}

	@Override
	public void setM_Transaction_ID (final int M_Transaction_ID)
	{
		if (M_Transaction_ID < 1)
			set_Value (COLUMNNAME_M_Transaction_ID, null);
		else
			set_Value (COLUMNNAME_M_Transaction_ID, M_Transaction_ID);
	}

	@Override
	public int getM_Transaction_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Transaction_ID);
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
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate_RebookedFrom()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_RebookedFrom_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate_RebookedFrom(final de.metas.material.dispo.model.I_MD_Candidate MD_Candidate_RebookedFrom)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_RebookedFrom_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate_RebookedFrom);
	}

	@Override
	public void setMD_Candidate_RebookedFrom_ID (final int MD_Candidate_RebookedFrom_ID)
	{
		if (MD_Candidate_RebookedFrom_ID < 1)
			set_Value (COLUMNNAME_MD_Candidate_RebookedFrom_ID, null);
		else
			set_Value (COLUMNNAME_MD_Candidate_RebookedFrom_ID, MD_Candidate_RebookedFrom_ID);
	}

	@Override
	public int getMD_Candidate_RebookedFrom_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_RebookedFrom_ID);
	}

	@Override
	public void setMD_Candidate_Transaction_Detail_ID (final int MD_Candidate_Transaction_Detail_ID)
	{
		if (MD_Candidate_Transaction_Detail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Transaction_Detail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Transaction_Detail_ID, MD_Candidate_Transaction_Detail_ID);
	}

	@Override
	public int getMD_Candidate_Transaction_Detail_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Transaction_Detail_ID);
	}

	@Override
	public void setMD_Stock_ID (final int MD_Stock_ID)
	{
		if (MD_Stock_ID < 1)
			set_Value (COLUMNNAME_MD_Stock_ID, null);
		else
			set_Value (COLUMNNAME_MD_Stock_ID, MD_Stock_ID);
	}

	@Override
	public int getMD_Stock_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Stock_ID);
	}

	@Override
	public void setMovementQty (final BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	@Override
	public BigDecimal getMovementQty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MovementQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTransactionDate (final @Nullable java.sql.Timestamp TransactionDate)
	{
		set_Value (COLUMNNAME_TransactionDate, TransactionDate);
	}

	@Override
	public java.sql.Timestamp getTransactionDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_TransactionDate);
	}
}