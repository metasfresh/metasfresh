/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.distribution.ddordercandidate.material_dispo;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddordercandidate.DDOrderCandidateDataFactory;
import de.metas.material.planning.ddordercandidate.DDOrderCandidateDemandMatcher;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DDOrderCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final DDOrderCandidateDemandMatcher demandMatcher;
	@NonNull private final DDOrderCandidateDataFactory ddOrderCandidateDataFactory;
	@NonNull private final DDOrderCandidateService ddOrderCandidateService;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public List<DDOrderCandidateAdvisedEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		if (!demandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final ProductPlanning productPlanningData = context.getProductPlanning();

		return ddOrderCandidateDataFactory.create(supplyRequiredDescriptor, context)
				.stream()
				.map(ddOrderCandidate -> toDDOrderCandidateAdvisedEvent(ddOrderCandidate, supplyRequiredDescriptor, productPlanningData))
				.collect(ImmutableList.toImmutableList());
	}

	private static DDOrderCandidateAdvisedEvent toDDOrderCandidateAdvisedEvent(
			@NonNull final DDOrderCandidateData ddOrderCandidate,
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final ProductPlanning productPlanningData)
	{
		final MinMaxDescriptor fromWarehouseMinMaxDescriptor = ddOrderCandidate.getFromWarehouseMinMaxDescriptor();
		final boolean isDDOrderCreationRequiredByReplenish = fromWarehouseMinMaxDescriptor != null && fromWarehouseMinMaxDescriptor.isHighPriority();
		return DDOrderCandidateAdvisedEvent.builder()
				.eventDescriptor(supplyRequiredDescriptor.newEventDescriptor())
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.ddOrderCandidate(ddOrderCandidate)
				.advisedToCreateDDOrder(productPlanningData.isCreatePlan() || isDDOrderCreationRequiredByReplenish)
				.pickIfFeasible(productPlanningData.isPickDirectlyIfFeasible())
				.build();
	}

	@Override
	public Quantity handleQuantityDecrease(final @NonNull SupplyRequiredDecreasedEvent event,
										   final Quantity qtyToDistribute)
	{
		final Set<DDOrderCandidateId> candidateIds = getDDOrderCandidateIds(event);
		if (candidateIds.isEmpty())
		{
			return qtyToDistribute;
		}

		final Collection<DDOrderCandidate> candidates = ddOrderCandidateService.getByIds(candidateIds);

		Quantity remainingQtyToDistribute = qtyToDistribute;

		for (final DDOrderCandidate candidate : candidates)
		{
			remainingQtyToDistribute = doDecreaseQty(candidate, remainingQtyToDistribute);

			if (remainingQtyToDistribute.signum() <= 0)
			{
				return remainingQtyToDistribute.toZero();
			}
		}

		return remainingQtyToDistribute;
	}

	private @NonNull Set<DDOrderCandidateId> getDDOrderCandidateIds(final @NonNull SupplyRequiredDecreasedEvent event)
	{
		final Candidate demandCandidate = candidateRepositoryRetrieval.retrieveById(CandidateId.ofRepoId(event.getSupplyRequiredDescriptor().getDemandCandidateId()));
		return candidateRepositoryWriteService.getSupplyCandidatesForDemand(demandCandidate, CandidateBusinessCase.DISTRIBUTION)
				.stream()
				.map(DDOrderCandidateAdvisedEventCreator::getDDOrderCandidateIdOrNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

	@Nullable
	private static DDOrderCandidateId getDDOrderCandidateIdOrNull(@NonNull final Candidate candidate)
	{
		final DistributionDetail distributionDetail = DistributionDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (distributionDetail == null)
		{
			return null;
		}
		final DDOrderRef ddOrderRef = distributionDetail.getDdOrderRef();
		if (ddOrderRef == null)
		{
			return null;
		}
		return DDOrderCandidateId.ofRepoId(ddOrderRef.getDdOrderCandidateId());
	}

	private Quantity doDecreaseQty(final DDOrderCandidate ddOrderCandidate, final Quantity remainingQtyToDistribute)
	{
		if (isCandidateEligibleForBeingDecreased(ddOrderCandidate))
		{
			final Quantity qtyToDecrease = remainingQtyToDistribute.min(ddOrderCandidate.getQtyToProcess());
			ddOrderCandidate.setQtyEntered(ddOrderCandidate.getQtyEntered().subtract(qtyToDecrease));
			ddOrderCandidateService.save(ddOrderCandidate);
			return remainingQtyToDistribute.subtract(qtyToDecrease);
		}
		return remainingQtyToDistribute;
	}

	private static boolean isCandidateEligibleForBeingDecreased(final DDOrderCandidate ddOrderCandidate)
	{
		return !ddOrderCandidate.isProcessed()
				&& ddOrderCandidate.getQtyToProcess().signum() > 0;
	}
}
