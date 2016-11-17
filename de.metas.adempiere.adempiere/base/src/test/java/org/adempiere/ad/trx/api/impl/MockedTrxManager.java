package org.adempiere.ad.trx.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.junit.Ignore;

@Ignore
public class MockedTrxManager extends PlainTrxManager
{
	private final List<ITrx> removedTransactions = new ArrayList<ITrx>();

	@Override
	protected ITrx createTrx(String trxName)
	{
		return new MockedTrx(this, trxName);
	}

	@Override
	public boolean remove(ITrx trx)
	{
		final boolean removed = super.remove(trx);
		if (removed)
		{
			removedTransactions.add(trx);
		}
		return removed;
	}

	public List<ITrx> getRemovedTransactions()
	{
		return removedTransactions;
	}

	public ITrx getRemovedTransactionByName(final String trxName)
	{
		return removedTransactions
				.stream()
				.filter(trx -> trxName.equals(trx.getTrxName()))
				.findFirst()
				.orElse(null);
	}
}
