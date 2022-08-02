package de.metas.procurement.base.order.async;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.impl.OrdersGenerator;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Generates purchase orders from {@link I_PMM_PurchaseCandidate} by invoking {@link OrdersGenerator}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PMM_GenerateOrders extends WorkpackageProcessorAdapter
{
	public static PMM_GenerateOrdersEnqueuer prepareEnqueuing()
	{
		return new PMM_GenerateOrdersEnqueuer();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final List<I_PMM_PurchaseCandidate> candidates = retrieveItems();
		if (candidates.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @PMM_PurchaseCandidate_ID@");
		}
		OrdersGenerator.newInstance()
				.setCandidates(candidates)
				.generate();

		return Result.SUCCESS;
	}

	private List<I_PMM_PurchaseCandidate> retrieveItems()
	{
		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();
		return Services.get(IQueueDAO.class).retrieveAllItemsSkipMissing(workpackage, I_PMM_PurchaseCandidate.class);
	}

	@Override
	public boolean isAllowRetryOnError()
	{
		// no, mainly because we want to prevent: if something fails and user will re-enqueue the candidates, nothing will happen because they were enqueued here.
		return false;
	}
}
