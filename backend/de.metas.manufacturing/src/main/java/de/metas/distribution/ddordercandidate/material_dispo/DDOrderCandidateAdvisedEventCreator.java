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
import de.metas.material.planning.PlanningUsage;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddordercandidate.DDOrderCandidateDataFactory;
import de.metas.material.planning.event.SupplyAdvice;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DDOrderCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final DDOrderCandidateDataFactory ddOrderCandidateDataFactory;
	@NonNull private final DDOrderCandidateService ddOrderCandidateService;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull
	@Override
	public PlanningUsage getPlanningUsage()
	{
		return PlanningUsage.DISTRIBUTION;
	}

	@NonNull
	@Override
	public SupplyAdvice createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context,
			@NonNull final Quantity remainingQty)
	{
		final ProductPlanning productPlanningData = context.getProductPlanning();

		// The factory now clips each candidate's qty by the source warehouse's ATP
		// (via SourceWarehouseAvailableQtyProvider). What it returns is what DD can actually
		// deliver; anything short of remainingQty is the leftover that the next advisor
		// (manufacturing / purchasing) needs to cover. See me03#28877.
		final ImmutableList<DDOrderCandidateData> candidates = ImmutableList.copyOf(
				ddOrderCandidateDataFactory.create(supplyRequiredDescriptor, context, remainingQty));

		final ImmutableList<DDOrderCandidateAdvisedEvent> events = candidates.stream()
				.map(ddOrderCandidate -> toDDOrderCandidateAdvisedEvent(ddOrderCandidate, supplyRequiredDescriptor, productPlanningData))
				.collect(ImmutableList.toImmutableList());

		final Quantity consumedQty = sumCandidateQtysInStockUOM(candidates, context.getProductId(), remainingQty);
		return SupplyAdvice.of(events, consumedQty);
	}

	/**
	 * @return the sum of {@code candidates[*].qty} (each candidate stores qty in product stock UOM).
	 * Result is expressed in the {@code remainingQty} UOM, and defensively capped at
	 * {@code remainingQty} in case the factory overshoots due to transferPercent rounding.
	 * When the list is empty, returns zero in the {@code remainingQty} UOM.
	 */
	@NonNull
	private Quantity sumCandidateQtysInStockUOM(
			@NonNull final ImmutableList<DDOrderCandidateData> candidates,
			@NonNull final ProductId productId,
			@NonNull final Quantity remainingQty)
	{
		if (candidates.isEmpty())
		{
			return remainingQty.toZero();
		}
		BigDecimal sumBD = BigDecimal.ZERO;
		for (final DDOrderCandidateData candidate : candidates)
		{
			sumBD = sumBD.add(candidate.getQty());
		}
		// Candidate qty is already in product stock UOM (DDOrderCandidateDataFactory calls
		// uomConversionBL.convertToProductUOM(...) before setting it). Wrap + convert to the
		// advisor-contract UOM (remainingQty's UOM, which also is product stock UOM today but
		// we go through the conversion BL to be safe against future divergence).
		final Quantity sumInStockUOM = Quantitys.of(sumBD, productId);
		final Quantity sumInRemainingUOM = uomConversionBL.convertQuantityTo(sumInStockUOM, productId, remainingQty.getUomId());
		return sumInRemainingUOM.min(remainingQty);
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
