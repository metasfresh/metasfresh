package de.metas.procurement.base.order.impl;

import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.procurement.base
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
/**
 * Generates purchase orders from {@link I_PMM_PurchaseCandidate}s by utilizing {@link OrdersAggregator}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrdersGenerator
{
	public static final OrdersGenerator newInstance()
	{
		return new OrdersGenerator();
	}

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private Iterator<I_PMM_PurchaseCandidate> candidates;

	private OrdersGenerator()
	{
		super();
	}

	public void generate()
	{
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				generate0();
			}
		});
	}

	private void generate0()
	{
		final OrdersCollector ordersCollector = OrdersCollector.newInstance();
		final OrdersAggregator aggregator = OrdersAggregator.newInstance(ordersCollector);

		for (final Iterator<I_PMM_PurchaseCandidate> it = getCandidates(); it.hasNext();)
		{
			final I_PMM_PurchaseCandidate candidateModel = it.next();
			final PurchaseCandidate candidate = PurchaseCandidate.of(candidateModel);
			aggregator.add(candidate);
		}

		aggregator.closeAllGroups();
	}

	public OrdersGenerator setCandidates(final Iterator<I_PMM_PurchaseCandidate> candidates)
	{
		Check.assumeNotNull(candidates, "candidates not null");
		this.candidates = candidates;
		return this;
	}

	public OrdersGenerator setCandidates(final Iterable<I_PMM_PurchaseCandidate> candidates)
	{
		Check.assumeNotNull(candidates, "candidates not null");
		setCandidates(candidates.iterator());
		return this;
	}

	private Iterator<I_PMM_PurchaseCandidate> getCandidates()
	{
		return candidates;
	}
}
