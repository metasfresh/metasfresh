package de.metas.purchasecandidate.material.event;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.quantity.Quantity;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-material-planning
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

@Component
@RequiredArgsConstructor
public class PurchaseCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher;
	@NonNull private final VendorProductInfoService vendorProductInfoService;
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	@NonNull private final PurchaseCandidateRepository purchaseCandidateRepository;

	public List<PurchaseCandidateAdvisedEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		if (!purchaseOrderDemandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final OrgId orgId = supplyRequiredDescriptor.getOrgId();

		final Optional<VendorProductInfo> defaultVendorProductInfo = vendorProductInfoService.getDefaultVendorProductInfo(productId, orgId);
		if (!defaultVendorProductInfo.isPresent())
		{
			Loggables.addLog("Found no VendorProductInfo for productId={} and orgId={}", productId.getRepoId(), orgId.getRepoId());
			return ImmutableList.of();
		}

		final ProductPlanning productPlanning = context.getProductPlanning();

		final PurchaseCandidateAdvisedEvent event = PurchaseCandidateAdvisedEvent.builder()
				.eventDescriptor(supplyRequiredDescriptor.newEventDescriptor())
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.directlyCreatePurchaseCandidate(productPlanning.isCreatePlan())
				.productPlanningId(ProductPlanningId.toRepoId(productPlanning.getId()))
				.vendorId(defaultVendorProductInfo.get().getVendorId().getRepoId())
				.build();

		Loggables.addLog("Created PurchaseCandidateAdvisedEvent");
		return ImmutableList.of(event);
	}

	@Override
	public Quantity handleQuantityDecrease(final @NonNull SupplyRequiredDecreasedEvent event,
										   final Quantity qtyToDistribute)
	{
		final Set<PurchaseCandidateId> candidateIds = getPurchaseCandidateIds(event);
		if (candidateIds.isEmpty())
		{
			return qtyToDistribute;
		}

		final List<PurchaseCandidate> candidates = purchaseCandidateRepository.getAllByIds(candidateIds);

		Quantity remainingQtyToDistribute = qtyToDistribute;

		for (final PurchaseCandidate candidate : candidates)
		{
			remainingQtyToDistribute = doDecreaseQty(candidate, remainingQtyToDistribute);

			if (remainingQtyToDistribute.signum() <= 0)
			{
				return remainingQtyToDistribute.toZero();
			}
		}

		return remainingQtyToDistribute;
	}

	private @NonNull Set<PurchaseCandidateId> getPurchaseCandidateIds(final @NonNull SupplyRequiredDecreasedEvent event)
	{
		final Candidate demandCandidate = candidateRepositoryRetrieval.retrieveById(CandidateId.ofRepoId(event.getSupplyRequiredDescriptor().getDemandCandidateId()));
		return candidateRepositoryWriteService.getSupplyCandidatesForDemand(demandCandidate, CandidateBusinessCase.PURCHASE)
				.stream()
				.map(PurchaseCandidateAdvisedEventCreator::getPurchaseCandidateRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

	@Nullable
	private static PurchaseCandidateId getPurchaseCandidateRepoIdOrNull(final Candidate candidate)
	{
		final PurchaseDetail purchaseDetail = PurchaseDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (purchaseDetail == null)
		{
			return null;
		}
		return PurchaseCandidateId.ofRepoId(purchaseDetail.getPurchaseCandidateRepoId());
	}

	private Quantity doDecreaseQty(final PurchaseCandidate candidate, final Quantity remainingQtyToDistribute)
	{
		if (isCandidateEligibleForBeingDecreased(candidate))
		{
			final Quantity qtyToPurchase = candidate.getQtyToPurchase();
			final Quantity qtyToDecrease = remainingQtyToDistribute.min(qtyToPurchase);
			candidate.setQtyToPurchase(qtyToPurchase.subtract(qtyToDecrease));
			purchaseCandidateRepository.save(candidate);
			return remainingQtyToDistribute.subtract(qtyToDecrease);
		}
		return remainingQtyToDistribute;
	}

	private static boolean isCandidateEligibleForBeingDecreased(final PurchaseCandidate candidate)
	{
		return !candidate.isProcessed()
				&& candidate.getQtyToPurchase().signum() > 0;
	}
}
