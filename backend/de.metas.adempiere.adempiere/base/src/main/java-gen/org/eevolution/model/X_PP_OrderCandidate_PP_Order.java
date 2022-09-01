/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_OrderCandidate_PP_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_OrderCandidate_PP_Order extends org.compiere.model.PO implements I_PP_OrderCandidate_PP_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 734709354L;

    /** Standard Constructor */
    public X_PP_OrderCandidate_PP_Order (final Properties ctx, final int PP_OrderCandidate_PP_Order_ID, @Nullable final String trxName)
    {
      super (ctx, PP_OrderCandidate_PP_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_OrderCandidate_PP_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class);
	}

	@Override
	public void setPP_Order_Candidate(final org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class, PP_Order_Candidate);
	}

	@Override
	public void setPP_Order_Candidate_ID (final int PP_Order_Candidate_ID)
	{
		if (PP_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, PP_Order_Candidate_ID);
	}

	@Override
	public int getPP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setPP_OrderCandidate_PP_Order_ID (final int PP_OrderCandidate_PP_Order_ID)
	{
		if (PP_OrderCandidate_PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_OrderCandidate_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_OrderCandidate_PP_Order_ID, PP_OrderCandidate_PP_Order_ID);
	}

	@Override
	public int getPP_OrderCandidate_PP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_OrderCandidate_PP_Order_ID);
	}

	@Override
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}