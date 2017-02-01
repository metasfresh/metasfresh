package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IDocumentCollector;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.allocation.IAllocationResult;

public final class PPOrderBOMLineHUTrxListener implements IHUTrxListener
{
	public static final PPOrderBOMLineHUTrxListener instance = new PPOrderBOMLineHUTrxListener();

	private PPOrderBOMLineHUTrxListener()
	{
		super();
	}

	@Override
	public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		createIssueCostCollectors(huContext, loadResults);
	}

	private final void createIssueCostCollectors(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		final HUIssueCostCollectorBuilder issueCostCollectorsBuilder = new HUIssueCostCollectorBuilder(huContext);

		//
		// Iterate HU Transactions and build up Issue Cost Collector Candidates
		for (final IAllocationResult loadResult : loadResults)
		{
			final List<IHUTransaction> huTransactions = loadResult.getTransactions();
			for (final IHUTransaction huTransaction : huTransactions)
			{
				issueCostCollectorsBuilder.addHUTransaction(huTransaction);
			}
		}

		//
		// Create Issue Cost Collectors from collected candidates
		final List<I_PP_Cost_Collector> costCollectors = issueCostCollectorsBuilder.createCostCollectors();

		//
		// Add generated cost collectors to HUContext's Document Collector
		final IDocumentCollector documentsCollector = huContext.getDocumentCollector();
		documentsCollector.addDocuments(costCollectors);
	}
}
