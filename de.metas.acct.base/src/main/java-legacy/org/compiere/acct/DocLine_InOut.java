package org.compiere.acct;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DocLine_InOut extends DocLine<Doc_InOut>
{
	/** Outside Processing */
	private int m_PP_Cost_Collector_ID = 0;

	public DocLine_InOut(final I_M_InOutLine inoutLine, final Doc_InOut doc)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);
	}

	public final int getPP_Cost_Collector_ID()
	{
		return m_PP_Cost_Collector_ID;
	}

	public final int setPP_Cost_Collector_ID(int PP_Cost_Collector_ID)
	{
		return m_PP_Cost_Collector_ID;
	}

	/**
	 * @return order's org if defined, else doc line's org
	 */
	public final int getOrder_Org_ID()
	{
		final int C_OrderLine_ID = getC_OrderLine_ID();
		if (C_OrderLine_ID > 0)
		{
			final String sql = "SELECT AD_Org_ID FROM C_OrderLine WHERE C_OrderLine_ID=?";
			final int AD_Org_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, C_OrderLine_ID);
			if (AD_Org_ID > 0)
			{
				return AD_Org_ID;
			}
		}
		return getAD_Org_ID();
	}

}
