package de.metas.rfq.util;

import java.math.BigDecimal;
import java.util.Comparator;

import org.adempiere.util.Services;

import de.metas.rfq.IRfqBL;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class RfqResponseLineQtyByNetAmtComparator implements Comparator<I_C_RfQResponseLineQty>
{
	public static final transient RfqResponseLineQtyByNetAmtComparator instance = new RfqResponseLineQtyByNetAmtComparator();

	private RfqResponseLineQtyByNetAmtComparator()
	{
		super();
	}

	@Override
	public int compare(final I_C_RfQResponseLineQty q1, final I_C_RfQResponseLineQty q2)
	{
		final IRfqBL rfqBL = Services.get(IRfqBL.class);

		if (q1 == null)
		{
			throw new IllegalArgumentException("o1 = null");
		}
		if (q2 == null)
		{
			throw new IllegalArgumentException("o2 = null");
		}

		//
		if (!rfqBL.isValidAmt(q1))
		{
			return -99;
		}
		if (!rfqBL.isValidAmt(q2))
		{
			return +99;
		}

		final BigDecimal net1 = rfqBL.calculateNetAmt(q1);
		if (net1 == null)
		{
			return -9;
		}

		final BigDecimal net2 = rfqBL.calculateNetAmt(q2);
		if (net2 == null)
		{
			return +9;
		}

		return net1.compareTo(net2);
	}
}
