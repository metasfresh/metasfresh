/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestone;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneId;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneObserver;
import de.metas.async.asyncbatchmilestone.AsyncBathMilestoneService;
import de.metas.async.asyncbatchmilestone.MilestoneName;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.async.C_OLCandToOrderEnqueuer;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static de.metas.async.Async_Constants.C_OlCandProcessor_ID_Default;

@Service
public class OrderService
{
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AsyncBathMilestoneService asyncBathMilestoneService;
	private final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver;
	private final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer;
	private final OLCandRepository olCandRepo;

	public OrderService(
			@NonNull final AsyncBathMilestoneService asyncBathMilestoneService,
			@NonNull final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver,
			@NonNull final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer,
			@NonNull final OLCandRepository olCandRepo)
	{
		this.asyncBathMilestoneService = asyncBathMilestoneService;
		this.asyncBatchMilestoneObserver = asyncBatchMilestoneObserver;
		this.olCandToOrderEnqueuer = olCandToOrderEnqueuer;
		this.olCandRepo = olCandRepo;
	}

	@NonNull
	public Set<OrderId> generateOrderSync(
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final Set<OLCandId> olCandIds)
	{

		final AsyncBatchMilestone asyncBatchMilestone = AsyncBatchMilestone.builder()
				.asyncBatchId(asyncBatchId)
				.orgId(Env.getOrgId())
				.milestoneName(MilestoneName.SALES_ORDER_CREATION)
				.build();

		final AsyncBatchMilestoneId milestoneId = asyncBathMilestoneService.save(asyncBatchMilestone).getIdNotNull();

		asyncBatchMilestoneObserver.observeOn(milestoneId);

		trxManager.runInNewTrx(
				() -> olCandToOrderEnqueuer.enqueue(C_OlCandProcessor_ID_Default, asyncBatchId));

		asyncBatchMilestoneObserver.waitToBeProcessed(milestoneId);

		return olCandDAO.getOrderIdsByOLCandIds(olCandIds);
	}

	@NonNull
	public List<I_C_OLCand> getOLCands(
			@NonNull final IdentifierString identifierString,
			@NonNull final String externalHeaderId)
	{
		final OLCandQuery olCandQuery = OLCandQuery.builder()
				.externalHeaderId(externalHeaderId)
				.inputDataSourceName(identifierString.asInternalName())
				.build();

		return olCandRepo.getByQuery(olCandQuery)
				.stream()
				.map(OLCand::unbox)
				.collect(ImmutableList.toImmutableList());
	}
}
